package com.ytao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytao.common.pojo.EasyUIDataGridResult;
import com.ytao.common.pojo.TaotaoResult;
import com.ytao.pojo.TbItem;
import com.ytao.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(int page, int rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	

	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult addItem(TbItem item, String desc) {
		item.setImage("it is null");
		System.out.println("进入controller"+item.toString()+"  desc:"+desc);
		TaotaoResult result = itemService.addItem(item, desc);
		return result;
	}
}
