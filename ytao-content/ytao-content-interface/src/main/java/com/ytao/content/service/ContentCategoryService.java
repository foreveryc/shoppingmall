package com.ytao.content.service;

import java.util.List;

import com.ytao.common.pojo.EasyUITreeNode;
import com.ytao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	List<EasyUITreeNode> getContentCategoryList(long parentId);
	public TaotaoResult addContentCategory(Long parentId, String name);
	
}
