package cn.ac.trimps.brulee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	JdbcTemplate jdbcTemplate;
	

}
