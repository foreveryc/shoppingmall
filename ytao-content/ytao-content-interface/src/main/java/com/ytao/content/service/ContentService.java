package com.ytao.content.service;

import java.util.List;

import com.ytao.common.pojo.TaotaoResult;
import com.ytao.pojo.TbContent;

public interface ContentService {
	TaotaoResult addContent(TbContent content);
	List<TbContent> getContentByCid(long cid);
}
