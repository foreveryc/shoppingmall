package com.ytao.sso.service;

import com.ytao.common.pojo.TaotaoResult;
import com.ytao.pojo.TbUser;

public interface UserService {

	TaotaoResult checkData(String data, int type);
	TaotaoResult register(TbUser user);
	TaotaoResult login(String username, String password);
	TaotaoResult getUserByToken(String token);
}
