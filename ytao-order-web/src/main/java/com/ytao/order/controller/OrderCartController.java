package com.ytao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ytao.common.pojo.TaotaoResult;
import com.ytao.common.utils.CookieUtils;
import com.ytao.common.utils.JsonUtils;
import com.ytao.order.pojo.OrderInfo;
import com.ytao.order.service.OrderService;
import com.ytao.pojo.TbItem;
import com.ytao.pojo.TbUser;

@Controller
public class OrderCartController {

	@Value("${CART_KEY}")
	private String CART_KEY;

	@Autowired
	private OrderService orderService;
	/**
	 * 展示订单确认页面
	 * <p>Title: showOrderCart</p>
	 * <p>Description: </p>
	 * @param request
	 * @return
	 */
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request) {
		//用户必须是登录状态
		//取用户id
		TbUser user = (TbUser) request.getAttribute("user");
		System.out.println(user.getUsername());
		//根据用户信息取收货地址列表，使用静态数据。
		//把收货地址列表取出传递给页面
		//从cookie中取购物车商品列表展示到页面
		List<TbItem> cartList = getCartItemList(request);
		request.setAttribute("cartList", cartList);
		//返回逻辑视图
		return "order-cart";
	}
	
	private List<TbItem> getCartItemList(HttpServletRequest request) {
		//从cookie中取购物车商品列表
		String json = CookieUtils.getCookieValue(request, CART_KEY, true);
		if (StringUtils.isBlank(json)) {
			//如果没有内容，返回一个空的列表
			return new ArrayList<>();
		}
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}
	
	@RequestMapping(value="/order/create", method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, Model model) {
		//生成订单
		TaotaoResult result = orderService.createOrder(orderInfo);
		//返回逻辑视图
		model.addAttribute("orderId", result.getData().toString());
		model.addAttribute("payment", orderInfo.getPayment());
		//预计送达时间，预计三天后送达
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		
		return "success";
	}
}
