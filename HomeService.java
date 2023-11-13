package cn.ac.trimps.brulee.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ac.trimps.brulee.mapper.GoodsMapper;
import cn.ac.trimps.brulee.mapper.TopBarMapper;
import cn.ac.trimps.brulee.pojo.Commodity;
import cn.ac.trimps.brulee.pojo.Goods;
import cn.ac.trimps.brulee.pojo.Swiper;
import cn.ac.trimps.brulee.pojo.TopBar;

@Service
public class HomeService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Map<String, Object> buildListData() {
		Map<String, Object> res = new HashMap<>();
		Swiper swiper = new Swiper();
		Commodity commodity = new Commodity();

		String topbarSql = "SELECT * from topbar ORDER BY id";
		String goodsSql = "SELECT * FROM `goods` WHERE `group`=1";

		List<TopBar> topbarList = jdbcTemplate.query(topbarSql, new TopBarMapper());
		List<Goods> goodsList = jdbcTemplate.query(goodsSql, new GoodsMapper());
		res.put("topBar", topbarList);

		for (Goods goods : goodsList) {
			if (goods.getType().equals("swiperList")) {
				swiper.getData().add(goods);
			} else if (goods.getType().equals("commodityList")) {
				commodity.getData().add(goods);
			}
		}

		List<Object> data = new ArrayList<>();
		data.add(swiper);
		data.add(commodity);

		res.put("data", data);
		return res;
	}

	public Map<String, Object> buildListDataPage(int id, int page) {
		Map<String, Object> res = new HashMap<>();
		Commodity commodity = new Commodity();

		String goodsSql = "SELECT * FROM `goods` WHERE `group`=?";
		List<Goods> goodsList = jdbcTemplate.query(goodsSql, new GoodsMapper());

		for (Goods goods : goodsList) {
			if (goods.getType().equals("commodityList")) {
				commodity.getData().add(goods);
			}
		}
		List<Object> data = new ArrayList<>();
		data.add(commodity);
		res.put("data", data);
		return res;
	}

	public List<Map<String, Object>> goodsDetail(int id) {
		List<Map<String, Object>> res = new ArrayList<>();
		String sql = "select * from goods_search where id=" + id + "";
		res = jdbcTemplate.queryForList(sql);
		return res;
	}

	public List<Map<String, Object>> selectCart(String phone) {
		List<Map<String, Object>> map = new ArrayList<>();
		String sql = "SELECT gd.* FROM goods_cart gd, user u WHERE gd.uId=u.id and u.phone=" + phone;
		map = jdbcTemplate.queryForList(sql);
		return map;
	}

	public List<Map<String, Object>> selectAddress(String phone) {
		List<Map<String, Object>> map = new ArrayList<>();
		String sql = "SELECT ad.* FROM address ad, user u WHERE ad.userId=u.id and u.phone=" + phone;
		map = jdbcTemplate.queryForList(sql);
		return map;
	}

	public Map<String, Object> goodsSearch(int id) {
		Map<String, Object> map = new HashMap<>();
		String sql = "select * from goods_search where id=" + id + "";
		map = jdbcTemplate.queryForMap(sql);
		return map;
	}

	public Map<String, Object> addCart(String phone, String goods_id, int num) {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> goods = new ArrayList<>();
		String sql_query = "select * from goods_cart gc, user u where gc.uId=u.id and gc.goods_id =" + goods_id
				+ " and u.phone=" + phone;
		String sql_update = "update goods_cart set num = ? where id = ?";
		String sql_add = "insert into goods_cart (uId,goods_id,name,imgUrl,pprice,num) values (\"'+userId+'\",\"'+goods_id+'\",\"'+name+'\",\"'+imgUrl+'\",\"'+pprice+'\",\"'+num+'\")";

		goods = jdbcTemplate.queryForList(sql_query);

		if (goods.size() > 0) {// 已经添加过
			jdbcTemplate.update(sql_update);
		} else {// 没有加入过
			jdbcTemplate.queryForObject(sql_add, Integer.class);
		}

		return map;
	}
}
