package com.doume.max.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.doume.max.entity.Product;
import com.doume.max.cons.MUtils;
import com.doume.max.dao.ProductDao;

@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao{

	private class SqlCon_Objs{
		public String sql_con = "";
		public List<Object> objs = new ArrayList<Object>();
	}
	
	private SqlCon_Objs getConditionJudge(Long productType,Integer minPrice,Integer maxPrice)
	{
		if(productType==null)productType = Product.ALL;
		SqlCon_Objs result = new SqlCon_Objs();
		result.sql_con = "(bitand(productType,?) != 0)";
		result.objs.add(productType);
		if(minPrice!=null && minPrice !=0)
		{
			if(result.sql_con.equals("")){
				result.sql_con = "(price >= ?)";
			}else
			{
				result.sql_con += " and (price >= ?)";
			}
			result.objs.add(minPrice);
		}
		if(maxPrice!=null&&maxPrice!=0)
		{
			if(result.sql_con.equals("")){
				result.sql_con = "(price <= ?)";
			}else
			{
				result.sql_con += " and (price <= ?)";
			}
			result.objs.add(maxPrice);
		}
		return result;
	}
	
	/*
	 * TODO S 数据量很大时 可能造成系统瘫痪［内存不够，查询超时］。
	 * 
	 */
	@Override
	public List<Product> find(Long[] sellerIds, Long productType,
			Integer minPrice, Integer maxPrice) {
		String sql=null;
		String sql_set="from Product ";
		SqlCon_Objs sqlCon_Objs=getConditionJudge(productType,minPrice,maxPrice);
		String sql_sellers = MUtils.arrayJoin(sellerIds, ",");
		if(!sql_sellers.equals(""))
		{
			sql = sql_set + " where  sellerId in (" + sql_sellers + ") and " + sqlCon_Objs.sql_con;
		}else
		{
			sql = sql_set + " where " +sqlCon_Objs.sql_con;
		}
		return  find(sql, sqlCon_Objs.objs.toArray());
	}

	@Override
	public List<Product> findByNameLike(String name) {
		final String hql = "from Product where productType!=0 and name like ? "
				+ " order by length(replace(name,?,''))";
		return find(hql,name,name);
	}

	@Override
	public List<Product> findBySelledCount(Long productType) {
		final String hql = "from Product where bitand(productType,?)!=0 "
				+ " order by selledCount desc";
		return find(hql,productType);
	}

	@Override
	public List<Product> findByScore(Long productType) {
		final String hql = "from Product where bitand(productType,?)!=0 "
				+ " order by score/scoreCount";
		return find(hql,productType);
	}

	@Override
	public List<Product> findByCredit(Long sellerId, Integer creditValue) {
		final String hql = "from Product where productType!=0" 
				+ " and sellerId=? and ?>=creditPrice"
				+ " order by (?-creditPrice)";
		return findByPage(hql,0,20,sellerId,creditValue,creditValue);
	}

	@Override
	public void switchLockBySellerId(Long sellerId) {
		final String sql = "update Product "
				+ "set productType=productType^locked,"
				+ "locked=productType^locked,"
				+ "productType=productType^locked"
				+ " where sellerId=?";
		this.jdbcTemplate.update(sql, sellerId);
	}
}
