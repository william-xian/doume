package com.doume.max.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringBean;

import com.doume.max.dao.BusinessDao;
import com.doume.max.dao.CommentDao;
import com.doume.max.dao.CreditDao;
import com.doume.max.dao.CustomerDao;
import com.doume.max.dao.DealDao;
import com.doume.max.dao.ProductDao;
import com.doume.max.dao.LoginLogDao;
import com.doume.max.dao.MessageDao;
import com.doume.max.dao.OrdersDao;
import com.doume.max.dao.UserDao;
import com.doume.max.entity.BHomePage;
import com.doume.max.entity.Business;
import com.doume.max.entity.Comment;
import com.doume.max.entity.Credit;
import com.doume.max.entity.Customer;
import com.doume.max.entity.Deal;
import com.doume.max.entity.FailedDeal;
import com.doume.max.entity.Product;
import com.doume.max.entity.Location;
import com.doume.max.entity.Message;
import com.doume.max.entity.Orders;
import com.doume.max.entity.User;
import com.doume.max.exception.UserExistException;

public class BusinessServiceTest extends BaseServiceTest{

	@SpringBean("businessService")
	private static BusinessService businessService;
	@SpringBean("userDao")
	private static UserDao userDao;
	@SpringBean("dealDao")
	private static DealDao dealDao;
	@SpringBean("ordersDao")
	private static OrdersDao ordersDao;
	@SpringBean("creditDao")
	private static CreditDao creditDao;
	@SpringBean("productDao")
	private static ProductDao productDao;
	@SpringBean("customerDao")
	private static CustomerDao customerDao;
	@SpringBean("businessDao")
	private static BusinessDao businessDao;
	@SpringBean("loginLogDao")
	private static LoginLogDao loginLogDao;
	@SpringBean("messageDao")
	private static MessageDao messageDao;
	@SpringBean("commentDao")
	private static CommentDao commentDao;
	@AfterClass
	public static void clearData()
	{

		for(Comment cmm:commentDao.loadAll())
		{
			commentDao.remove(cmm);
		}
		for(Credit c:creditDao.loadAll()){
			creditDao.remove(c);
		}
		for(Message msg:messageDao.loadAll()){
			messageDao.remove(msg);
		}
		for(Orders o:ordersDao.loadAll()){
			ordersDao.remove(o);
		}
		for(Product g:productDao.loadAll()){
			productDao.remove(g);
		}
		for(Business b:businessDao.loadAll()){
			businessDao.remove(b);
		}
		for(User u:userDao.loadAll()){
			userDao.remove(u);
		}
	}
	@Test
	@DataSet("/dataSetXml/service/BusinessService/update.xml")
    @ExpectedDataSet("/dataSetXml/service/BusinessService/ExpectedUpdate.xml")
	public void update() throws UserExistException
	{
		Business business = new Business();
		business.setUserId(8000L);
		business.setbName("Fish2");
		business.setPhoneno("0987654321");
		business.setInformation("Sell Fish");
		business.setBalance(60);
		business.setPpm(0);
		BHomePage bhp = new BHomePage();
		business.setHome(bhp);
		Location location = new Location();
		location.setLat(2.0);
		location.setLng(1.0);
		business.setLocation(location);
		businessService.update("test/",business);
	}
	@Test
	@DataSet("/dataSetXml/service/BusinessService/datainit.xml")
    @ExpectedDataSet("/dataSetXml/service/BusinessService/ExpectedAddProduct.xml")
	public void addProduct() throws IOException
	{
		Product g = new Product();
		g.setSellerId(8000L);
		g.setName("new Fish");
		g.setProductType(1L);
		g.setPrice(10);
		g.setCreditPrice(10);
		g.setRetCredit(1);
		g.setDescription("fresh fish");
		g.setScore(0);
		g.setMedia(null);
		boolean rs = businessService.addProduct("target/", g);
		assertTrue(rs);
	}

	@Test
    @DataSet("/dataSetXml/service/BusinessService/getOwnProduct.xml")
	public void getOwnProduct(){
		List<Product>  rs = businessService.getOwnProduct(8000L);
		assertEquals(3,rs.size());
	}
	@Test
    @DataSet("/dataSetXml/service/BusinessService/orders.xml")
	public void acceptOrders(){
		businessService.acceptOrders(8000L, 1L);
		List<Orders> orders = ordersDao.findBySeller(8000L);
		Orders o = orders.get(0);
		assertEquals(Orders.ACCEPT,o.getOrdersStatus());
		ordersDao.remove(o);
		List<Message> rs = messageDao.findByReceiver(9000L);
		assertEquals(1, rs.size());
		messageDao.remove(rs.get(0));
	}
	
	@Test
    @DataSet("/dataSetXml/service/BusinessService/orders.xml")
	public void refuseOrders(){
		businessService.refuseOrders(8000L, 1L);
		Orders orders = ordersDao.get(1L);
		assertNull(orders);
		List<Message> rs = messageDao.findByReceiver(9000L);
		assertEquals(1, rs.size());
		messageDao.remove(rs.get(0));
		List<Deal> fdrs = dealDao.findByBuyerId(9000L,new Date());
		for(Deal fd:fdrs){
			assertTrue(fd instanceof FailedDeal);
			dealDao.remove(fd);
		}
	}
	
	@Test
    @DataSet("/dataSetXml/service/BusinessService/orders.xml")
	public void confirmOrders()
	{
		System.out.println("hello");
		businessService.confirmOrders(8000L, 1L);
		Orders orders = ordersDao.get(1L);
		assertNull(orders);
		List<Message> rs = messageDao.findByReceiver(9000L);
		assertEquals(1, rs.size());
		messageDao.remove(rs.get(0));
		
		List<Deal> fdrs = dealDao.findByBuyerId(9000L,new Date());
		for(Deal fd:fdrs)
		{
			assertTrue(fd instanceof Deal);
			dealDao.remove(fd);
		}

		Credit credit = creditDao.find(8000L, 9000L);
		System.out.println("credit:-->" + credit);
		
		assertEquals(new Integer(30),credit.getValue());
		creditDao.remove(credit);
		List<Comment> cmms = commentDao.findByUserIdEmpty(9000L);
		assertEquals(2,cmms.size());
		for(Comment cmm:cmms)
		{
			commentDao.remove(cmm);
		}
	}

	@Test
	public void addCredits_getCredits()
	{
		Credit c = new Credit();
		c.setSellerId(8000L);
		c.setBuyerId(6000L);
		c.setValue(0);
		businessService.addCredit(c);
		List<Credit> rs  = businessService.getCredits(8000L);
		assertEquals(1,rs.size());
		creditDao.remove(c);
	}

	@Test
    @DataSet("/dataSetXml/service/BusinessService/vipUsers.xml")
	public void addVipUser_getVipUser()
	{
		List<User> users = new LinkedList<User>();
		users.add(userDao.get(9100L));
		users.add(userDao.get(9101L));
		users.add(userDao.get(9102L));
		List<Customer> cs = new LinkedList<Customer>();
		cs.add(customerDao.get(9100L));
		cs.add(customerDao.get(9101L));
		cs.add(customerDao.get(9102L));
		for(User u:users){
			businessService.addMember(8000L, u.getUserName());
		}
		
		List<Customer> rsVip = businessService.getMembers(8000L);
		assertEquals(cs.size(),rsVip.size());
		List<Credit> rs  = businessService.getCredits(8000L);
		for(Credit c:rs)
		{
			creditDao.remove(c);
		}
		for(User u:users)
		{
			userDao.remove(u);
		}
		for(Customer c: cs)
		{
			customerDao.remove(c);
		}
	}

	@Test
    @DataSet("/dataSetXml/service/BusinessService/subCredit.xml")
	public void subCredits()
	{
		Credit credit = new Credit();
		credit.setSellerId(8001L);
		credit.setBuyerId(9000L);
		credit.setValue(30);
		Orders orders = businessService.subCredit(credit);
		assertNull(orders);
		credit.setSellerId(8000L);
		orders = businessService.subCredit(credit);
		assertNotNull(orders);
	}
}
