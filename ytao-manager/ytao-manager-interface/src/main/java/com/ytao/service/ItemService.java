package com.ytao.service;

import com.ytao.common.pojo.EasyUIDataGridResult;
import com.ytao.common.pojo.TaotaoResult;
import com.ytao.pojo.TbItem;
import com.ytao.pojo.TbItemDesc;

public interface ItemService {
	TbItem getItemById(long itemId);
	EasyUIDataGridResult getItemList(int page,int rows);
	TaotaoResult addItem(TbItem item,String desc);
	TbItemDesc getItemDescById(long itemId);
	
}
