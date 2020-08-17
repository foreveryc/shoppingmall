package com.ytao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytao.common.pojo.EasyUITreeNode;
import com.ytao.mapper.TbItemCatMapper;
import com.ytao.pojo.TbItemCat;
import com.ytao.pojo.TbItemCatExample;
import com.ytao.pojo.TbItemCatExample.Criteria;

import com.ytao.service.ItemCatService;

@Service
public class ItemCatServiceimpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		
		List<EasyUITreeNode> resultList=new ArrayList<EasyUITreeNode>();
		for(TbItemCat tbItemCat:list) {
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;

	}

}
