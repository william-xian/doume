package com.doume.max.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.doume.max.entity.Product;
import com.doume.max.dao.ProductDao;

public class ProductDaoTest extends BaseDaoTest{
	@SpringBean("productDao")
	private static ProductDao dao;
	private static Product[] data;

	@BeforeClass
	public static void initData() {
		Long[] type = new Long[]{1L,2L,4L,8L};
		data = new Product[9];
		for(int i = 0; i < data.length; i++)
		{
			data[i] = new Product();
			//data[i].setProductId(i + 1L);
			data[i].unlock();
			data[i].setSellerId(i%3 + 1L);
			data[i].setProductType(type[i%4]);
			data[i].setDescription("info" + i);
			data[i].setMediaId("mediaId" + i);
			data[i].setPrice(100-(i%3));
			data[i].setCreditPrice(100-(i%3) );
			data[i].setRetCredit(10+i);
			data[i].setName("product" + i%4);
			data[i].setSelledCount(i);
			data[i].setScore(i+ 10);
			data[i].setScoreCount(i+1);
		}
	}

	@AfterClass
	public static void clearData() {
	}
	@Before
	public void saveData() {
		for (Product d : data) {
			dao.save(d);
		}
	}

	@After
	public void removeData() {
		for (Product d : data) {
			dao.remove(d);
		}
	}
	@Test
	public void findBySellers()
	{
		List<Product> rs = dao.find(null, Product.ALL, 0, 0);
		assertEquals(data.length, rs.size());
		
		rs = dao.find(new Long[]{}, Product.ALL, 0, 0);
		assertEquals(data.length, rs.size());
		
		rs = dao.find(new Long[]{new Long(1L)}, Product.ALL, 0, 0);
		assertEquals(rs.size(), 3);
		
		rs = dao.find(new Long[]{new Long(1L),new Long(3L)}, Product.ALL, 0, 0);
		assertEquals(rs.size(), 6);
	}
	@Test
	public void findByType()
	{
		List<Product> rs;
		rs = dao.find(null, 1L, null, null);
		assertEquals(3,rs.size());
		rs = dao.find(null, 4L, null, null);
		assertEquals(2,rs.size());
		rs = dao.find(null, 4L|1L, null, null);
		assertEquals(5,rs.size());
	}
	
	@Test
	public void lockBusiness()
	{
		dao.switchLockBySellerId(1L);
		List<Product> rs = dao.find(new Long[]{1L}, Product.ALL, 0, 0);
		assertEquals(rs.size(), 0);
		dao.switchLockBySellerId(1L);
		rs = dao.find(new Long[]{1L}, Product.ALL, 0, 0);
		assertEquals(rs.size(), 3);
	}
	
	@Test
	public void findByPrice()
	{
		List<Product> rs = dao.find(null, Product.ALL, 100, 0);
		assertEquals(rs.size(), 3);
		
		rs = dao.find(null, Product.ALL, 99, null);
		assertEquals(rs.size(), 6);
		rs = dao.find(null, Product.ALL, 98, 0);
		assertEquals(rs.size(), 9);

		rs = dao.find(null, Product.ALL, 0, 99);
		assertEquals(rs.size(), 6);

		rs = dao.find(null, Product.ALL, 99, 99);
		assertEquals(rs.size(), 3);
		rs = dao.find(null, Product.ALL, 98, 99);
		assertEquals(rs.size(), 6);
	}
	
	@Test
	public void findMixCondition()
	{
		List<Product> rs = dao.find(new Long[]{1L,2L,3L}, Product.ALL, 98, 100);
		assertEquals(rs.size(), 9);
		rs = dao.find(new Long[]{1L,3L}, Product.ALL, 98, 100);
		assertEquals(rs.size(), 6);

		rs = dao.find(new Long[]{1L,3L}, 1|2|4L, 98, 100);
		assertEquals(rs.size(), 5);

		rs = dao.find(new Long[]{1L,3L}, 1|2|4L, 99, 100);
		assertEquals(rs.size(), 2);
	}
	
	@Test
	public void findByScore()
	{
		List<Product> rs;
		rs = dao.findByScore(Product.ALL);
		for(int i = 0; i < data.length; i++)
		{
			assertBeanEquals(rs.get(i),data[data.length-1-i]);
		}
	}

	@Test
	public void findBySelledCount()
	{		
		List<Product> rs;
		rs = dao.findBySelledCount(Product.ALL);
		for(int i = 0; i < data.length; i++)
		{
			assertBeanEquals(rs.get(i),data[data.length-1-i]);
		}
	}
	@Test
	public void findByNameLike()
	{
		List<Product> rs;
		rs = dao.findByNameLike("product%");
		assertEquals(data.length,rs.size());
	}
	@Test
	public void findByCredit()
	{
		List<Product> rs;
		rs = dao.findByCredit(1L, 100);
		Product[] exp = new Product[]{data[0],data[3],data[6]};
		for(int i =0; i < exp.length; i++)
		{
			assertBeanEquals(rs.get(i),exp[i]);
		}
	}
}
