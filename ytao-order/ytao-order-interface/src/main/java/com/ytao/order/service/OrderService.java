package com.ytao.order.service;

import com.ytao.common.pojo.TaotaoResult;
import com.ytao.order.pojo.OrderInfo;

public interface OrderService {
	TaotaoResult createOrder(OrderInfo orderInfo);
}
