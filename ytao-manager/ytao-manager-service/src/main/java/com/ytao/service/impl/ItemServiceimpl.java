package com.ytao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ytao.common.pojo.EasyUIDataGridResult;
import com.ytao.common.pojo.TaotaoResult;
import com.ytao.common.utils.IDUtils;
import com.ytao.common.utils.JsonUtils;
import com.ytao.jedis.JedisClient;
import com.ytao.mapper.TbItemDescMapper;
import com.ytao.mapper.TbItemMapper;
import com.ytao.pojo.TbItem;
import com.ytao.pojo.TbItemDesc;
import com.ytao.pojo.TbItemExample;
import com.ytao.service.ItemService;

@Service
public class ItemServiceimpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource(name="itemAddtopic")
	private Destination destination;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ITEM_INFO}")
	private String ITEM_INFO;
	@Value("${TIEM_EXPIRE}")
	private Integer TIEM_EXPIRE;
	
	
	
	
	@Override
	public TbItem getItemById(long itemId) {
		//查询数据库之前先查询缓存
				try {
					String json = jedisClient.get(ITEM_INFO + ":" + itemId  + ":BASE");
					if (StringUtils.isNotBlank(json)) {
						// 把json数据转换成pojo
						TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
						return tbItem;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				//缓存中没有查询数据库
				TbItem item = itemMapper.selectByPrimaryKey(itemId);
				try {
					//把查询结果添加到缓存
					jedisClient.set(ITEM_INFO + ":" + itemId  + ":BASE", JsonUtils.objectToJson(item));
					//设置过期时间，提高缓存的利用率
					jedisClient.expire(ITEM_INFO + ":" + itemId  + ":BASE", TIEM_EXPIRE);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return item;
	}
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemExample example=new TbItemExample();
		List<TbItem> list=itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(list);
		EasyUIDataGridResult result=new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		//生成商品id
				final long itemId = IDUtils.genItemId();
				//补全item的属性
				item.setId(itemId);
				//商品状态，1-正常，2-下架，3-删除
				item.setStatus((byte) 1);
				item.setCreated(new Date());
				item.setUpdated(new Date());
				System.out.println("商品"+item.toString());
				//向商品表插入数据
				itemMapper.insert(item);
				//创建一个商品描述表对应的pojo
				TbItemDesc itemDesc = new TbItemDesc();
				//补全pojo的属性
				itemDesc.setItemId(itemId);
				itemDesc.setItemDesc(desc);
				itemDesc.setUpdated(new Date());
				itemDesc.setCreated(new Date());
				//向商品描述表插入数据
				System.out.println("第二商品："+itemDesc);
				itemDescMapper.insert(itemDesc);
				//向Activemq发送商品添加消息
				jmsTemplate.send(destination, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						//发送商品id
						TextMessage textMessage = session.createTextMessage(itemId + "");
						return textMessage;
					}
				});
				//返回结果
				return TaotaoResult.ok();
	}
	@Override
	public TbItemDesc getItemDescById(long itemId) {
		//查询数据库之前先查询缓存
		try {
			String json = jedisClient.get(ITEM_INFO + ":" + itemId  + ":DESC");
			if (StringUtils.isNotBlank(json)) {
				// 把json数据转换成pojo
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//缓存中没有查询数据库
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		 
		try {
			//把查询结果添加到缓存
			jedisClient.set(ITEM_INFO + ":" + itemId  + ":DESC", JsonUtils.objectToJson(itemDesc));
			//设置过期时间，提高缓存的利用率
			jedisClient.expire(ITEM_INFO + ":" + itemId  + ":DESC", TIEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}
