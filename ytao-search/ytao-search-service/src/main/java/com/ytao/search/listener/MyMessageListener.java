package com.ytao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener{

	@Override
	public void onMessage(Message arg0) {
		try {
			//接收到消息
			TextMessage textMessage = (TextMessage) arg0;
			String text = textMessage.getText();
			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
