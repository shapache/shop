package cn.ac.trimps.brulee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import cn.ac.trimps.brulee.service.HomeService;
import cn.ac.trimps.brulee.util.JwtToken;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api")
public class HomeController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	HomeService homeService;

	/**
	 * 首页数据
	 */
	@GetMapping("/index_list/data")
	public Map<String, Object> listData(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> res = new HashMap<>();
		Map<String, Object> data = homeService.buildListData();
		res.put("data", data);
		res.put("code", 0);
		return res;
	}

	/**
	 * 首页分类数据
	 */
	@GetMapping("/index_list/{id}/data/{page}")
	public Map<String, Object> listDataPage(@PathVariable(value = "id", required = true) int id,
			@PathVariable(value = "page", required = true) int page, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> res = new HashMap<>();
		Map<String, Object> data = homeService.buildListDataPage(id, page);
		res.put("data", data);
		res.put("code", 0);
		return res;
	}

	/**
	 * 搜索数据详情
	 */
	@GetMapping("/goods/id")
	public Map<String, Object> goodsDetail(@PathParam(value = "id") int id, HttpServletRequest request,
			HttpServletResponse response) {
		log.info("id:{},{}", id, request.getAttribute("id"));
		Map<String, Object> res = new HashMap<>();
		List<Map<String, Object>> data = homeService.goodsDetail(id);
		res.put("data", data);
		res.put("code", 0);

		return res;
	}

	/**
	 * 需传入token,查询个人购物车列表
	 */
	@PostMapping("/selectCart")
	public Map<String, Object> selectCart(HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		String token=request.getHeader("token");
		String phone = JwtToken.verifyToken(token);
		
		log.info("selectCart Token...{},{}", request.getHeader("token"),phone);
		List<Map<String, Object>> data = homeService.selectCart(phone);
		res.put("data", data);
		res.put("code", 0);
		return res;
	}

	/**
	 * 需传入token,查询个人地址
	 */
	@PostMapping("/selectAddress")
	public Map<String, Object> selectAddress(HttpServletRequest request) {
		Map<String, Object> res = new HashMap<>();
		String token=request.getHeader("token");
		String phone = JwtToken.verifyToken(token);
		List<Map<String, Object>> data = homeService.selectAddress(phone);
		res.put("data", data);
		res.put("code", 0);
		return res;
	}
	/**
	 * 添加到购物车
	 */
	@PostMapping("/addCart")
	public Map<String, Object> addCart(HttpServletRequest request,@RequestBody JsonNode jsonNode) {
		Map<String, Object> res = new HashMap<>();
		String token=request.getHeader("token");
		String phone = JwtToken.verifyToken(token);
		String goods_id = jsonNode.path("goods_id").asText();
		int num = jsonNode.path("num").asInt();
		
		Map<String, Object> data = homeService.addCart(phone,goods_id,num);
		res.put("data", data);
		res.put("code", 0);
		return res;
	}
	
}
