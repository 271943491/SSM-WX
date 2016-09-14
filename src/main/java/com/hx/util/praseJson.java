package com.hx.util;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class praseJson {

	// 解析快递的返回信息
	public Map<Boolean, String> praseKD(String body) {

		Map<Boolean, String> result = new HashMap<Boolean, String>();

		JSONObject jo = JSONObject.fromObject(body);

		String code = jo.get("status").toString();

		String data = "";

		if (code.equals("200")) {

			JSONArray ja = jo.getJSONArray("data");

			for (int i = 0; i < ja.size(); i++) {
				JSONObject j = ja.getJSONObject(i);
				data = data + j.get("acceptTime") + "\n" + j.get("remark") + "\n";
			}

			result.put(true, data);
		} else {
			data = "查询不到该快递单号的记录，请核对信息是否正确！";
			result.put(false, data);
		}

		return result;
	}

	// 解析天气的返回信息
	public Map<Boolean, String> praseTQ(String body) {

		Map<Boolean, String> result = new HashMap<Boolean, String>();

		JSONObject jo = JSONObject.fromObject(body);

		String code = jo.get("status").toString();

		String data = "";

		if (code.equals("200")) {

			JSONObject j = jo.getJSONObject("data");

			data = data + j.get("city") + "，" + j.get("weather") + "，最高温度" + j.get("maxTemp") + "，最低温度"
					+ j.get("minTemp") + "，" + j.get("windDirection") + j.get("windForce");

			result.put(true, data);
		} else {
			data = "查询不到该地区的天气情况，请检查您的输入";
			result.put(false, data);
		}

		return result;
	}

	public String getQDCompany(String name) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("安捷快递", "anjelex");
		map.put("AAE全球专递", "aaeweb");
		map.put("安信达快递", "anxinda");
		map.put("阿里物流", "alp");
		map.put("彪记快递", "pewkee");
		map.put("百福东方", "ees");
		map.put("CCES快递", "cces");
		map.put("德邦物流", "deppon");
		map.put("大田陆运", "dtw");
		map.put("D速快递", "dexp");
		map.put("DPEX", "dpex");
		map.put("DHL", "dhl");
		map.put("EMS", "ems");
		map.put("FEDEX", "fedex");
		map.put("飞康达物流", "fkdv");
		map.put("凤凰快递", "phoenixexp");
		map.put("港中能达物流", "nd56");
		map.put("广东邮政物流", "gdems");
		map.put("GLS快递", "cngls");
		map.put("共速达物流", "gsd");
		map.put("海航天天快递", "ttkdex");
		map.put("恒路物流", "henglu");
		map.put("华夏龙物流", "chinadragon");
		map.put("汇通快运", "htky");
		map.put("华慧快递", "bhtexp");
		map.put("佳吉快运", "jiaji");
		map.put("急先达", "joust");
		map.put("加运美", "tms");
		map.put("佳怡物流", "jiayi");
		map.put("京广速递", "kke");
		map.put("快捷速递", "fast");
		map.put("蓝镖快递", "bluedart");
		map.put("龙邦物流", "lbex");
		map.put("联昊通物流", "lts");
		map.put("民航快递", "cae");
		map.put("明亮物流", "szml56");
		map.put("配思货运", "peisi");
		map.put("全际通物流", "quanjt");
		map.put("全日通快递", "qrt");
		map.put("全晨快递", "qckd");
		map.put("全一快递", "apex");
		map.put("盛辉物流", "shenghui");
		map.put("申通快递", "sto");
		map.put("盛丰物流", "sfwl");
		map.put("速尔物流", "sure");
		map.put("顺丰速运", "sf");
		map.put("上大物流", "sundapost");
		map.put("苏粤货运", "suyue");
		map.put("圣安物流", "szsa56");
		map.put("天地华宇", "hoau");
		map.put("TNT", "tnt");
		map.put("天天快递", "ttkdex");
		map.put("UPS", "ups");
		map.put("伍圆速递", "f5xm");
		map.put("文捷航空速递", "gzwenjie");
		map.put("万象物流", "wxwl");
		map.put("万家物流", "manco");
		map.put("新邦物流", "xbwl");
		map.put("鑫飞鸿物流快递", "xfhong");
		map.put("星晨急便", "stars");
		map.put("信丰物流", "xfexpress");
		map.put("圆通速递", "yto");
		map.put("韵达快运", "yunda");
		map.put("运通快递", "ytexpress");
		map.put("越丰物流", "yfexpress");
		map.put("远成物流", "ycgwl");
		map.put("源伟丰快递", "ywfex");
		map.put("亚风速递", "yf");
		map.put("一邦速递", "ebon");
		map.put("优速物流", "uc56");
		map.put("元智捷诚快递", "yjkd");
		map.put("原飞航物流", "yfhex");
		map.put("源安达", "yadex");
		map.put("宅急送", "zjs");
		map.put("中邮物流", "cnpl");
		map.put("中铁物流", "ztky");
		map.put("中国东方", "（COE）	coe");
		map.put("中通速递", "zto");
		map.put("中铁快运", "cre");

		if (map.containsKey(name)) {
			return map.get(name);
		} else {
			return null;
		}
	}
}
