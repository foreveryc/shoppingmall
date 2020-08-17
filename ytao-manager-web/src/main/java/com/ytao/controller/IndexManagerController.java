package com.ytao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytao.common.pojo.TaotaoResult;
import com.ytao.search.service.SearchItemService;

@Controller
public class IndexManagerController {
	@Autowired
	private SearchItemService searchItemService;

	@RequestMapping("/index/import")
	@ResponseBody
	public TaotaoResult importIndex() {
		System.out.println("进入controller");
		TaotaoResult taotaoResult = searchItemService.importItemsToIndex();
		return taotaoResult;
	}

}
