package com.hx.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Hex;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hx.service.impl.message;
import com.hx.util.getAccessToken;
import com.hx.util.post;
import com.hx.util.replay;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin")
public class WXController {

	public static final String TOKEN = "youzy";

	// 微信接入 http://wwwx.ittun.com/admin/wx.do
	@RequestMapping(value = "/wx.do", method = RequestMethod.GET)
	protected void accessWX(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数
			String signature = request.getParameter("signature");// 微信加密签名（token、timestamp、nonce。）
			String timestamp = request.getParameter("timestamp");// 时间戳
			String nonce = request.getParameter("nonce");// 随机数
			String echostr = request.getParameter("echostr");// 随机字符串
			PrintWriter out = response.getWriter();
			// 将token、timestamp、nonce三个参数进行字典序排序
			String[] params = new String[] { TOKEN, timestamp, nonce };
			Arrays.sort(params);
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			String clearText = params[0] + params[1] + params[2];
			String algorithm = "SHA-1";
			String sign = new String(
					Hex.encodeHex(MessageDigest.getInstance(algorithm).digest((clearText).getBytes()), true));
			// 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
			if (signature.equals(sign)) {
				out.print(echostr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/wx.do", method = RequestMethod.POST)
	protected void accessMsg(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			message msg = new message();
			InputStream stream = request.getInputStream();

			SAXReader saxreader = new SAXReader();
			Document document = saxreader.read(stream);
			Element root = document.getRootElement();

			Iterator<Element> it = root.elementIterator();
			while (it.hasNext()) {
				Element element = (Element) it.next();

				// 获取消息接收方
				if (element.getName().equals("ToUserName")) {
					msg.setToUserName(element.getText());
					System.out.println("ToUserName: " + msg.getToUserName());
				}
				// 获取消息发送方
				if (element.getName().equals("FromUserName")) {
					msg.setFromUserName(element.getText());
					System.out.println("FromUserName: " + msg.getFromUserName());
				}
				// 获取消息类型
				if (element.getName().equals("MsgType")) {
					msg.setMsgType(element.getText());
					System.out.println("MsgType: " + msg.getMsgType());
				}
				// 获取消息内容
				if (element.getName().equals("Content")) {
					msg.setContent(element.getText());
					System.out.println("Content: " + msg.getContent());
				}
				// 获取消息Id
				if (element.getName().equals("MsgId")) {
					msg.setMsgId(element.getText());
					System.out.println("MsgId: " + msg.getMsgId());
				}
				// 获取事件类型
				if (element.getName().equals("Event")) {
					msg.setEvent(element.getText());
					System.out.println("Event: " + msg.getEvent());
				}
			}

			if (msg.getMsgType().equals("text")) {
				String replayMsg = new replay().msg(msg);
				System.out.println(replayMsg);
				PrintWriter out = response.getWriter();
				out.print(replayMsg);
			} else if (msg.getMsgType().equals("event") && msg.getEvent().equals("subscribe")) {
				String replayMsg = new replay().follow(msg);
				PrintWriter out = response.getWriter();
				out.print(replayMsg);
			} else {
				String replayMsg = new replay().msg(msg, "您发送的内容不正确，请重试。查询天气回复1,查询快递回复2。");
				PrintWriter out = response.getWriter();
				out.print(replayMsg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 添加微信自定义菜单 http://127.0.0.1:8080/admin/addMenu.json
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addMenu.json", method = RequestMethod.GET)
	@ResponseBody
	public Object addMenu() {

		// 发送get请求，获取accesstoken
		String accesstoken = new getAccessToken().getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accesstoken;

		// JSON格式数据解析对象
		JSONObject jo = new JSONObject();
		JSONObject jo1 = new JSONObject();
		JSONObject jo2 = new JSONObject();

		// 构造请求消息体
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("type", "scancode_waitmsg");
		map1.put("name", "扫码带提示");
		map1.put("key", "rselfmenu_0_0");
		map1.put("sub_button", "[]");

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("type", "scancode_push");
		map2.put("name", "扫码推事件");
		map2.put("key", "rselfmenu_0_1");
		map2.put("sub_button", "[]");

		List<Map> list1 = new ArrayList<Map>();
		list1.add(map1);
		list1.add(map2);

		// 将Map转换为JSONArray数据
		JSONArray ja1 = JSONArray.fromObject(list1);
		jo1.put("name", "扫一扫");
		jo1.put("sub_button", ja1);

		// Map<String, String> map5 = new HashMap<String, String>();
		// map5.put("type", "pic_sysphoto");
		// map5.put("name", "系统拍照发图");
		// map5.put("key", "rselfmenu_1_0");
		// map5.put("sub_button", "[]");

		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("type", "pic_photo_or_album");
		map6.put("name", "拍照或者相册发图");
		map6.put("key", "rselfmenu_1_1");
		map6.put("sub_button", "[]");

		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("type", "pic_weixin");
		map7.put("name", "微信相册发图");
		map7.put("key", "rselfmenu_1_2");
		map7.put("sub_button", "[]");

		List<Map> list3 = new ArrayList<Map>();
		/* list3.add(map5); */
		list3.add(map6);
		list3.add(map7);

		JSONArray ja2 = JSONArray.fromObject(list3);
		jo2.put("name", "发图片");
		jo2.put("sub_button", ja2);

		JSONArray array1 = new JSONArray();
		array1.add(jo1);
		array1.add(jo2);
		jo.put("button", array1);
		System.out.println(jo.toString());

		String result = new post().sendPost(url, jo.toString());

		System.out.println(result);

		Map<String, String> map = new HashMap<String, String>();

		map.put("result", result);

		return map;
	}
}