package cn.ac.trimps.brulee.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ac.trimps.brulee.service.HomeService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("api")
public class HomeController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	HomeService homeService;

	@GetMapping("/index_list/data")
	public Map<String, Object> listData(HttpServletRequest request, HttpServletResponse response) {
		log.info("list...");
		System.out.println("list....");
		Map<String, Object> res = new HashMap<>();
		 
		Map<String, Object> data = homeService.buildListData();
		
		res.put("data", data);
		res.put("code", 0);
		
		return res;
	}

}
