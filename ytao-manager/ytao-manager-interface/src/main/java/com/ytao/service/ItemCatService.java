package com.ytao.service;

import java.util.List;


import com.ytao.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	List<EasyUITreeNode> getItemCatList(long parentId);
}
