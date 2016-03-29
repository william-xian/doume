package com.doume.max.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.doume.max.entity.Product;
import com.doume.max.entity.Orders;

public class OrdersDaoTest extends BaseDaoTest {
	@SpringBean("productDao")
	private static ProductDao productDao;
	@SpringBean("ordersDao")
	private static OrdersDao ordersDao;
	private static Orders[] data;
	private static Product[] productData;

	@BeforeClass
	public static void initData() {
		data = new Orders[4];
		productData = new Product[4];
		for (int i = 0; i < productData.length; i++) {
			Product product = new Product();
			product.setCreditPrice(100);
			product.setProductType(1L<<i);
			product.setName("info" + i);
			productData[i] = product;
		}
	}

	@AfterClass
	public static void clearData() {
	}
	@Before
	public void saveData() {
		for (Product g : productData) {
			productDao.save(g);
			System.out.println("save-product:" + g);
		}
		
		for (int i = 0; i < data.length; i++) {
			data[i] = new Orders();
			for (int t = 0; t <= i; t++) {
				data[i].addProduct(productData[t], t + 1);
			}
			data[i].setBuyerId(i % 3 + 1L);
			data[i].setSellerId(i % 2 + 1L);
			data[i].setOrdersStatus(Orders.CONFIRM_CUS);
		}
		
		for (Orders d : data) {
			ordersDao.save(d);
			System.out.println("save-data" + d);
		}
	}

	@After
	public void removeData() {
		for (Orders d : data) {
			System.out.println("remove-data->" + d);
			ordersDao.remove(d);
		}
		for (Product g : productData) {
			System.out.println("remove-Product->" + g);
			productDao.remove(g);
		}
	}
	@Test
	public void findBy() {	
		List<Orders> rs;
		rs = ordersDao.findByBuyer(1L);
		assertTrue(rs.size() >= 2);
		rs = ordersDao.findByBuyer(2L);
		assertTrue(rs.size() >= 1);
		rs = ordersDao.findBySeller(1L);
		assertTrue(rs.size() >= 2);
	}

	@Test
	public void findByIds() {
		Long[] ids = new Long[1024];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = i + 1L;
		}
		for(int i = 0; i < data.length; i++)
		{
			ids[i] = data[i].getOrdersId();
		}
		List<Orders> rs = ordersDao.findByIds(ids);
		assertTrue(rs.size() >= data.length);
	}
}
