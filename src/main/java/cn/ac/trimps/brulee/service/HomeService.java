package cn.ac.trimps.brulee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

	public Map<String, Object> buildListData() {
		Map<String, Object> res =  new HashMap<>();

		List<Object> topbar = new ArrayList<>();
		Map<String, Object>  topbar1 = new HashMap<>();
		Map<String, Object>  topbar2 = new HashMap<>();
		Map<String, Object>  topbar3 = new HashMap<>();
		topbar1.put("id", 1);
		topbar1.put("name", "推荐");
		topbar2.put("id", 2);
		topbar2.put("name", "运动");
		topbar3.put("id", 3);
		topbar3.put("name", "服饰");
		topbar.add(topbar1);
		topbar.add(topbar2);
		topbar.add(topbar3);

		List<Object> data = new ArrayList<>();
		Map<String, Object>  data1 = new HashMap<>();
		Map<String, Object>  data2 = new HashMap<>();
		Map<String, Object>  data3 = new HashMap<>();
		
		res.put("topBar", topbar);
		res.put("data", data);

		return res;
	}

}
