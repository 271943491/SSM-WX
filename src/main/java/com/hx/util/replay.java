package com.hx.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.hx.service.impl.message;

public class replay {

	// 提供天气查询和快递查询功能
	public String msg(message msg) throws DocumentException, IOException {

		JedisUtil j = new JedisUtil();

		String ToUserName = msg.getToUserName();
		String FromUserName = msg.getFromUserName();
		String MsgType = msg.getMsgType();
		String Content = msg.getContent();

		Content = Content.trim();

		if (Content.equals("1")) {

			// 将用户指令2保存到redis
			j.setValue(FromUserName, "1");

			Content = "请输入您想查询的城市";

		} else if (Content.equals("2")) {

			// 将用户指令2保存到redis
			j.setValue(FromUserName, "2");

			Content = "请输入您想查询的快递单号，如：973750441329";

		} else {

			// 如果用户发送的不是1或2，则查询用户之前发送的指令
			String userKey = j.getValue(FromUserName);

			if (userKey != null && userKey.equals("1")) {

				Content = URLEncoder.encode(Content, "utf-8");

				String url = "http://api.36wu.com/Weather/GetWeather?district=" + Content
						+ "&authkey=ae3fcd6fa6094a74acf696176e97bcf6&format=json";

				// 调用接口查询天气
				String data = new get().sendGet(url);

				Map<Boolean, String> map = new praseJson().praseTQ(data);
				Boolean flag = map.keySet().iterator().next();
				Content = map.get(flag);

				// 如果查询成功，则删除用户发送的指令
				if (flag) {
					j.deleteValue(FromUserName);
				}

			} else if (userKey != null && userKey.equals("2")) {

				if (Content.matches("[1-9]*")) {

					String url = "http://api.36wu.com/Express/GetExpressInfo?postid=" + Content
							+ "&authkey=ae3fcd6fa6094a74acf696176e97bcf6&format=json";

					// 调用接口查询快递
					String data = new get().sendGet(url);

					Map<Boolean, String> map = new praseJson().praseKD(data);
					Boolean flag = map.keySet().iterator().next();
					Content = map.get(flag);

					// 如果查询成功，则删除用户发送的指令
					if (flag) {
						j.deleteValue(FromUserName);
					}
				} else {
					Content = "您输入的快递单号不正确,请重新输入";
				}

			} else {
				Content = "您发送的内容不正确，请重试。查询天气回复1,查询快递回复2。";
			}
		}

		long unixtime = System.currentTimeMillis() / 1000;

		Document documentOut = DocumentHelper.createDocument();
		Element catalogElement = documentOut.addElement("xml");
		Element e1 = catalogElement.addElement("ToUserName");
		Element e2 = catalogElement.addElement("FromUserName");
		Element e3 = catalogElement.addElement("CreateTime");
		Element e4 = catalogElement.addElement("MsgType");
		Element e5 = catalogElement.addElement("Content");
		e1.setText(FromUserName);
		e2.setText(ToUserName);
		e3.setText(String.valueOf(unixtime));
		e4.setText(MsgType);
		e5.setText(Content);
		String replayMsg = documentOut.asXML().replaceAll("^<\\?.*", "");
		return replayMsg;
	}

	// 回复提示消息
	public String msg(message msg, String notice) throws DocumentException, IOException {

		String ToUserName = msg.getToUserName();
		String FromUserName = msg.getFromUserName();
		String MsgType = "text";
		String Content = notice;
		Content = Content.trim();

		long unixtime = System.currentTimeMillis() / 1000;

		Document documentOut = DocumentHelper.createDocument();
		Element catalogElement = documentOut.addElement("xml");
		Element e1 = catalogElement.addElement("ToUserName");
		Element e2 = catalogElement.addElement("FromUserName");
		Element e3 = catalogElement.addElement("CreateTime");
		Element e4 = catalogElement.addElement("MsgType");
		Element e5 = catalogElement.addElement("Content");
		e1.setText(FromUserName);
		e2.setText(ToUserName);
		e3.setText(String.valueOf(unixtime));
		e4.setText(MsgType);
		e5.setText(Content);
		String replayMsg = documentOut.asXML().replaceAll("^<\\?.*", "");

		return replayMsg;
	}

	// 用户关注后，给用户发送感谢关注提示语
	public String follow(message msg) throws DocumentException, IOException {

		String ToUserName = msg.getToUserName();
		String FromUserName = msg.getFromUserName();
		String MsgType = "text";
		String Content = "感谢您的关注，此公众号只为您提供自助查询服务。查询天气回复1,查询快递回复2。";

		long unixtime = System.currentTimeMillis() / 1000;

		Document documentOut = DocumentHelper.createDocument();
		Element catalogElement = documentOut.addElement("xml");
		Element e1 = catalogElement.addElement("ToUserName");
		Element e2 = catalogElement.addElement("FromUserName");
		Element e3 = catalogElement.addElement("CreateTime");
		Element e4 = catalogElement.addElement("MsgType");
		Element e5 = catalogElement.addElement("Content");
		e1.setText(FromUserName);
		e2.setText(ToUserName);
		e3.setText(String.valueOf(unixtime));
		e4.setText(MsgType);
		e5.setText(Content);
		String replayMsg = documentOut.asXML().replaceAll("^<\\?.*", "");

		return replayMsg;
	}
}
