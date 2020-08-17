package com.ytao.search.mapper;

import java.util.List;

import com.ytao.common.pojo.SearchItem;

public interface SearchItemMapper {

	List<SearchItem> getItemList();
	SearchItem getItemById(long itemId);
}
