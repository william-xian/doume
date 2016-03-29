package com.doume.max.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.spring.annotation.SpringBean;

import com.doume.max.dao.AdministratorDao;
import com.doume.max.dao.BusinessDao;
import com.doume.max.dao.DealDao;
import com.doume.max.dao.ProductDao;
import com.doume.max.dao.LoginLogDao;
import com.doume.max.dao.UserDao;
import com.doume.max.entity.Administrator;
import com.doume.max.entity.Business;
import com.doume.max.entity.Deal;
import com.doume.max.entity.Product;
import com.doume.max.entity.User;
import com.doume.max.exception.UserExistException;


public class AdministratorServiceTest extends BaseServiceTest{
	@SpringBean("administratorService")
	private static AdministratorService adminService;
	@SpringBean("userDao")
	private static UserDao userDao;
	@SpringBean("businessDao")
	private static BusinessDao businessDao;
	@SpringBean("administratorDao")
	private static AdministratorDao adminDao;
	@SpringBean("loginLogDao")
	private static LoginLogDao loginLogDao;
	@SpringBean("dealDao")
	private static DealDao dealDao;
	@SpringBean("productDao")
	private static ProductDao productDao;
	@After
	public void clearData(){
		for(User u:userDao.loadAll()){
			userDao.remove(u);
		}
		for(Administrator a:adminDao.loadAll()){
			adminDao.remove(a);
		}
		for(Business business:businessDao.loadAll()){
			businessDao.remove(business);
		}
		for(Deal d:dealDao.loadAll()){
			dealDao.remove(d);
		}
		for(Product g:productDao.loadAll())
		{
			productDao.remove(g);
		}
	}
	@Test
	@DataSet("/dataSetXml/service/AdministratorService/update.xml")
	@ExpectedDataSet("/dataSetXml/service/AdministratorService/ExpectedUpdate.xml")
	public void update() throws UserExistException
	{
		Administrator admin = adminDao.get(1L);
		Business bus = businessDao.get(8000L);
		admin.addBusiness(bus);
		adminService.update(admin);
	}
	
	@Test
    @DataSet("/dataSetXml/service/AdministratorService/addBusiness.xml")
	public void addBusiness()
	{
		Date begin = new Date();
		Administrator admin = adminService.getAdmin(7000L);
		assertNotNull(admin);
		Business business = businessDao.get(8000L);
		assertNotNull(business);
		adminService.addBusiness(admin, business);
		Business dbb = admin.getBlist().iterator().next();
		ReflectionAssert.assertReflectionEquals(business, dbb);

		List<Deal> deals = dealDao.findByBuyerId(admin.getUserId(),begin);
		Deal deal = deals.get(0);
		assertEquals(admin.getUserId(),deal.getBuyerId());
		assertEquals(new Long(0L),deal.getSellerId());		
	}

	@Test
    @DataSet("/dataSetXml/service/AdministratorService/datainit.xml")
	public void lock_unlock_Business()
	{
		Date date =new Date();	
    	Administrator admin = adminService.getAdmin(7000L);
		adminService.lockBusiness(admin, 8000L, "Money");
		Business business = businessDao.get(8000L);	
		assertTrue(business.isLocked());

		List<Deal> deals = dealDao.findBySellerId(admin.getUserId());
		Deal deal = deals.get(0);
		System.out.println("deal:" + deal);
		assertEquals(business.getUserId(),deal.getBuyerId());
		assertEquals(admin.getUserId(),deal.getSellerId());
		dealDao.remove(deal);
		adminService.unlockBusiness(admin, 8000L, "Money");
		business = businessDao.get(8000L);
		assertTrue(!business.isLocked());
		deals = dealDao.findByBuyerId(business.getUserId(),date);
		deal = deals.get(0);
		assertEquals(business.getUserId(),deal.getBuyerId());
		assertEquals(admin.getUserId(),deal.getSellerId());
	}
	
	@SuppressWarnings("deprecation")
	@Test
    @DataSet("/dataSetXml/service/AdministratorService/datainit.xml")
	public void payBusiness()
	{
		Date date = new Date();
		date.setDate(1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
    	Administrator admin = adminService.getAdmin(7000L);
		adminService.payBusiness(admin, 8000L, 100);
		Business business = businessDao.get(8000L);
		assertEquals(new Integer(130),business.getBalance());
		List<Deal> deals = dealDao.findByBuyerId(8000L,date);
		Deal deal = deals.get(0);
		assertEquals(admin.getUserId(),deal.getSellerId());
	}
}
