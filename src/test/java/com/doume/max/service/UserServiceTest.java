package com.doume.max.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.spring.annotation.SpringBean;

import com.doume.max.dao.AdministratorDao;
import com.doume.max.dao.BusinessDao;
import com.doume.max.dao.CommentDao;
import com.doume.max.dao.CustomerDao;
import com.doume.max.dao.DealDao;
import com.doume.max.dao.ProductDao;
import com.doume.max.dao.LoginLogDao;
import com.doume.max.dao.MessageDao;
import com.doume.max.dao.UserDao;
import com.doume.max.entity.Administrator;
import com.doume.max.entity.Business;
import com.doume.max.entity.Comment;
import com.doume.max.entity.Customer;
import com.doume.max.entity.Deal;
import com.doume.max.entity.Product;
import com.doume.max.entity.LoginLog;
import com.doume.max.entity.Message;
import com.doume.max.entity.User;
import com.doume.max.exception.UserExistException;

public class UserServiceTest extends BaseServiceTest{

	@SpringBean("userService")
	private static UserService userService;
	@SpringBean("userDao")
	private static UserDao userDao;
	@SpringBean("productDao")
	private static ProductDao productDao;
	@SpringBean("loginLogDao")
	private static LoginLogDao loginLogDao;
	@SpringBean("dealDao")
	private static DealDao dealDao;
	@SpringBean("commentDao")
	private static CommentDao commentDao;
	@SpringBean("messageDao")
	private static MessageDao messageDao;
	@SpringBean("administratorDao")
	private static AdministratorDao adminDao;
	@SpringBean("businessDao")
	private static BusinessDao businessDao;
	@SpringBean("customerDao")
	private static CustomerDao customerDao;
	public static void initData(){
	}
	
	@AfterClass
	public static void clearData(){
		for(Customer cus:customerDao.loadAll()){
			customerDao.remove(cus);
		}
		for(Business b:businessDao.loadAll()){
			businessDao.remove(b);
		}
		for(Administrator adm:adminDao.loadAll()){
			adminDao.remove(adm);
		}
		for(User usr:userDao.loadAll()){
			userDao.remove(usr);
		}
		for(LoginLog ll:loginLogDao.loadAll()){
			loginLogDao.remove(ll);
		}
		for(Deal d:dealDao.loadAll()){
			dealDao.remove(d);
		}
		for(Product g:productDao.loadAll())
		{
			productDao.remove(g);
		}
		for(Comment c:commentDao.loadAll())
		{
			commentDao.remove(c);
		}
		for(Message msg:messageDao.loadAll())
		{
			messageDao.remove(msg);
		}
	}
	
	@Test(expected=UserExistException.class)
    @ExpectedDataSet("/dataSetXml/service/UserService/ExpectedRegister.xml")
	public void register() throws UserExistException{
		User admin = new User();
		admin.setAdmin();
		admin.setUserName("admin");
		admin.setPassword("123456");
		userService.register(admin);
		User bus = new User();
		bus.setBusiness();
		bus.setUserName("business");
		bus.setPassword("123456");
		userService.register(bus);
		User cus = new User();
		cus.setCustomer();
		cus.setUserName("customer");
		cus.setPassword("123456");
		userService.register(cus);
		userService.register(cus);
	}
	@Test
    @DataSet("/dataSetXml/service/UserService/login.xml")
	public void login(){
		User usr = new User();
		usr.setUserName("fanli");
		usr.setOpenId("fanli");
		usr.setCustomer();
		usr.setPasswordMD5("123456");
		User user = userService.loginByUserName(usr.getUserName(), "123456");
		assertEquals(usr.getOpenId(),user.getOpenId());
		user = userService.loginByUserName("fanli", "123456xx");
		assertNull(user);
		user = userService.loginByUserName("fanlixx", "123456");
		assertNull(user);
	}
	@Test
	public void loginSuccess()
	{
		User user = userService.loginByUserName("fanli", "123456");
		LoginLog log = userService.loginSuccess(user);
		assertNotNull(log);
		loginLogDao.remove(log);
	}
	@Test
    @DataSet("/dataSetXml/service/UserService/datainit.xml")
    @ExpectedDataSet("/dataSetXml/service/UserService/ExpectedUpdate.xml")
	public void update() throws UserExistException{
		User user = userService.loginByUserName("fanli", "123456");
		assertNotNull(user);
		user.setUserName("FanLi2");
		user.setPassword("654321");
		userService.update(user);
		for(User u : userDao.loadAll()){
			System.out.println("userUpdate:-"+u);
		}
	}
	
	@Test
    @DataSet("/dataSetXml/service/UserService/datainit.xml")
    @ExpectedDataSet("/dataSetXml/service/UserService/ExpectedAddDeal.xml")
	public void addDeal()
	{
		Deal d = userService.addDeal(8000L, 9000L, 6000L, "dealMsg");
		assertNotNull(d);
	}
	@Test
    @DataSet("/dataSetXml/service/UserService/datainit.xml")
    @ExpectedDataSet("/dataSetXml/service/UserService/addFailedDeal.xml")
	public void addFailedDeal()
	{
		Deal d = userService.addFailedDeal(8000L, 9000L, 6000L, "dealMsg");
		assertNotNull(d);
	}
	@Test
    @DataSet("/dataSetXml/service/UserService/datainit.xml")
    @ExpectedDataSet("/dataSetXml/service/UserService/ExpectedAddBusinessComment.xml")
	public void addBusinessComment()
	{
		Comment c  = userService.addBusinessComment(9000L, 8000L, Comment.GOOD, "Name", "Good");
		assertNotNull(c);
	}
	@Test
    @DataSet("/dataSetXml/service/UserService/datainit.xml")
	@ExpectedDataSet("/dataSetXml/service/UserService/addProductComment.xml")
	public void addProductComment()
	{
		Comment c  = userService.addProductComment(9000L, 8000L, Comment.GOOD, "Name", "Good");
		assertNotNull(c);
	}
	@Test
    @DataSet("/dataSetXml/service/UserService/datainit.xml")
	@ExpectedDataSet("/dataSetXml/service/UserService/addNewsComment.xml")
	public void addNewsComment()
	{
		Comment c  = userService.addNewsComment(9000L, 8000L, Comment.GOOD, "Name", "Good");
		assertNotNull(c);
	}
	@Test
    @DataSet("/dataSetXml/service/UserService/datainit.xml")
	@ExpectedDataSet("/dataSetXml/service/UserService/ExpectedSendMessage.xml")
	public void sendMessage()
	{
		Message msg = userService.sendMessage(9000L, 8000L, Message.ACTUAL,"actualMsg", "content");
		assertNotNull(msg);
	}
	@Test
	@DataSet("/dataSetXml/service/UserService/getActualMessage.xml")
	public void getActualMessage()
	{
		List<Message> rs = userService.getMessage(9000L,Message.ACTUAL);
		for(Message m:rs)
		{
			assertTrue((m.getMsgType()&Message.ACTUAL)!=0);
		}
	}
	@Test
    @DataSet("/dataSetXml/service/UserService/datainit.xml")
	public void getAllUsers(){
		List<User> rs = userService.getAllUsers();
		assertEquals(2,rs.size());
	}

	@Test
	@DataSet("/dataSetXml/service/UserService/removeMessage.xml")
	@ExpectedDataSet("/dataSetXml/service/UserService/ExpectedRemoveMessage.xml")
	public void removeMessage()
	{
		userService.removeMessage(9000L, 1L);
		userService.removeMessage(8000L, 2L);
		userService.removeMessage(8000L, 3L);
		userService.removeMessage(9000L, 3L);
	}
}
