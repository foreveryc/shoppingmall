package com.ytao.search.service;

import com.ytao.common.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryString,int page,int rows) throws Exception;
}
