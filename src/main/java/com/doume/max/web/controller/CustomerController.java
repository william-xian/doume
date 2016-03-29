package com.doume.max.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.mortbay.jetty.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.doume.max.cons.CommonConstant;
import com.doume.max.entity.Business;
import com.doume.max.entity.Comment;
import com.doume.max.entity.Credit;
import com.doume.max.entity.Customer;
import com.doume.max.entity.Deal;
import com.doume.max.entity.Message;
import com.doume.max.entity.Orders;
import com.doume.max.entity.OrdersItem;
import com.doume.max.entity.Product;
import com.doume.max.entity.ProductComment;
import com.doume.max.entity.SearchItem;
import com.doume.max.entity.User;
import com.doume.max.exception.UserExistException;
import com.doume.max.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {
	protected static final Logger logger = Logger.getLogger(CustomerController.class);
	@Autowired
	protected CustomerService cusService;
	@RequestMapping("/getHome")
	public ModelAndView getHome() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("OM", new Orders());
		mav.setViewName("/customer/home");
		return mav;
	}
	@RequestMapping("/getLogin")
	public ModelAndView getLogin(){
		ModelAndView mav = new ModelAndView();
		User user = new User();
		user.setCustomer();
		user.setUserId(0L);
		mav.addObject("OM", user);
		mav.setViewName("/customer/login");
		return mav;
	}
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpSession session,
			@ModelAttribute("OM") User user) {
		ModelAndView mav = new ModelAndView();
		User host = null;
		if (user.getUserName() != null) {
			host = cusService.loginByUserName(user.getUserName(),
					user.getPassword());
		} else if (user.getOpenId() != null) {
			host = cusService.loginByOpenId(user.getUserName(),
					user.getPassword());
		}
		if (host != null) {
			Customer cus = cusService.getCustomer(host.getUserId());
			session.setAttribute(CommonConstant.USER_ME, host);
			session.setAttribute(CommonConstant.CUSTOMER_ME, cus);
			mav.addObject("OM", new Object[] { host});
			mav.setViewName("/customer/home");
			return mav;
		} else {
			user.setUserName("");
			user.setPassword("");
			mav.addObject(CommonConstant.USER_ME, user);
			mav.setViewName("/customer/login");
			return mav;
		}
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute(CommonConstant.USER_ME);
		return "forward:/index.jsp";
	}
	@RequestMapping(value = "/getRegister", method=RequestMethod.GET)
	public ModelAndView getRegister(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("OM",new User());
		mav.setViewName("/customer/register");
		return mav;
	}
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public ModelAndView register(@Valid @ModelAttribute("OM") User user,
			BindingResult bindingResult){
		ModelAndView mav = new ModelAndView();
		try {
			user.setCustomer();
			cusService.register(user);
			mav.addObject("OM", user);
			mav.setViewName("/customer/home");
		} catch (UserExistException e) {
			user.setUserName("");
			mav.addObject("OM", user);
			mav.setViewName("/customer/register");
		}
		return mav;
	}
	@RequestMapping(value="/private",method=RequestMethod.GET)
	public ModelAndView getPrivate(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<Orders> orders = cusService.getOrders(host.getUserId());
		List<Message> msges = cusService.getMessage(host.getUserId(), Message.ALL);
		List<SearchItem> fav = cusService.getEnshrine(host.getUserId());
		List<Comment> cmms = cusService.getEmptyComment(host.getUserId());
		mav.addObject("OM", new Object[]{orders,msges,fav,cmms});
		mav.setViewName("/customer/private");
		return mav;
	}
	@RequestMapping(value="/more",method=RequestMethod.GET)
	public ModelAndView getMore(){
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public ModelAndView getUser(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		mav.addObject("OM", host);
		mav.setViewName("/customer/updateUser");
		return mav;
	}
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)
	public ModelAndView updateUser(HttpSession session,
			@Valid @ModelAttribute("OM") User user,
			BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		if (bindingResult.hasErrors()) {
			mav.addObject(bindingResult);
			return mav;
		} else {
			try {
				User host = (User)session.getAttribute(CommonConstant.USER_ME);
				user.setUserId(host.getUserId());
				host = cusService.update(user);
				session.setAttribute(CommonConstant.USER_ME, host);
				mav.addObject("OM", user);
			} catch (UserExistException e) {
				mav.addObject("OM", "UserExistException");
			}
			return mav;
		}
	}
	@RequestMapping(value="/getCustomer",method=RequestMethod.GET)
	public ModelAndView getCustomer(HttpSession session){
		ModelAndView mav = new ModelAndView();
		Customer cus = (Customer)session.getAttribute(CommonConstant.CUSTOMER_ME);
		mav.addObject("OM", cus);
		mav.setViewName("/customer/updateCustomer");
		return mav;
	}
	@RequestMapping(value="/updateCustomer",method=RequestMethod.POST)
	public ModelAndView updateCustomer(HttpSession session,
			@Valid @ModelAttribute("OM") Customer customer,
			BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		if (bindingResult.hasErrors()) {
			return mav;
		}
		cusService.update(customer);
		session.setAttribute(CommonConstant.CUSTOMER_ME, customer);
		mav.addObject("OM", customer);
		mav.setViewName("/customer/updateCustomer");
		return mav;
	}
	@RequestMapping(value="/getSendMessage/{receiverId}",method=RequestMethod.POST)
	public ModelAndView getSendMessage(HttpSession session,@PathVariable Long receiverId){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		Message msg = new Message();
		msg.setSenderId(host.getUserId());
		msg.setReceiverId(receiverId);
		mav.addObject("OM", msg);
		mav.setViewName("/customer/sendMessage");
		return mav;
	}
	@RequestMapping(value="/sendMessage",method=RequestMethod.POST)
	public ModelAndView sendMessage(@ModelAttribute("OM") Message msg){
		ModelAndView mav = new ModelAndView();
		cusService.sendMessage(msg.getSenderId(),msg.getReceiverId(),Message.ACTUAL,
				msg.getSubject(),msg.getContent());
		mav.addObject("OM",msg);
		mav.setViewName("/customer/sendMessage");
		return mav;
	}
	@RequestMapping(value="/sendMessage",method=RequestMethod.GET)
	public ModelAndView sendMessage(HttpSession session,String receivers,String subject,String content)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/customer/sendMessage");
		if(receivers==null||content==null)return mav;
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		if(receivers.equals("AllMembers")){
			cusService.sendMessage(host.getUserId(),null,Message.TOUSER|Message.RECEIVER_DEL,subject,content);
		}else if(receivers.matches("[0-9,]*")){
			String[] ss = receivers.split(",");
			for(String sid:ss){
				cusService.sendMessage(host.getUserId(),Long.valueOf(sid),Message.UNREAD,subject,content);
			}
		}
		return mav;
	}
	@RequestMapping(value="/removeMessage/{msgId}",method=RequestMethod.GET)
	public ModelAndView removeMessage(HttpSession session,@PathVariable Long msgId)
	{
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		ModelAndView mav = new ModelAndView();
		cusService.removeMessage(host.getUserId(), msgId);
		Message msg = new Message();
		msg.setHead("Result of RMMSG");
		msg.setMsgDate(new Date());
		msg.setSubject("Remove");
		msg.setContent("OK");
		mav.addObject("OM",msg);
		mav.setViewName("/system/message");
		return mav;
	}
	@RequestMapping(value="/addOrders",method=RequestMethod.POST)
	public ModelAndView addOrders(HttpSession session,Orders orders){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		cusService.addOrders(host.getUserId(), orders);
		mav.addObject("OM", HttpStatus.OK);
		return mav;
	}
	@RequestMapping(value="/getOrders")
	public ModelAndView getOrders(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<Orders> orders = cusService.getOrders(host.getUserId());
		mav.addObject("OM", orders);
		mav.setViewName("/customer/orders");
		return mav;
	}
	@RequestMapping(value="/getTempOrders",method=RequestMethod.GET)
	public ModelAndView getMenu(HttpSession session){
		ModelAndView mav = new ModelAndView();
		Orders orders = (Orders)session.getAttribute(CommonConstant.ORDERS_TEMP);
		if(orders == null){
			orders = new Orders();
			session.setAttribute(CommonConstant.ORDERS_TEMP, orders);
		}
		mav.addObject("OM",orders);
		mav.setViewName("/customer/tmpOrders");
		return mav;
	}
	@RequestMapping(value="/submitTempOrders",method=RequestMethod.GET)
	public ModelAndView submitOrders(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		Orders orders = (Orders)session.getAttribute(CommonConstant.ORDERS_TEMP);
		Message msg = new Message();
		if(orders != null){
			cusService.addOrders(host.getUserId(), orders);
			msg.setMsgDate(new Date());
			msg.setHead("submitOrders");
			msg.setSubject("SubmitOrders->OK");
			msg.setContent("");
		}else{
			msg.setMsgDate(new Date());
			msg.setHead("submitOrders");
			msg.setSubject("SubmitOrders->Error");
			msg.setContent("Your Orders is empty.");
		}
		mav.addObject("OM", msg);
		mav.setViewName("/system/message");
		return mav;
	}
	@RequestMapping(value="/changeOrdersItem/{cmd}/{pid}/{count}",method=RequestMethod.GET)
	public ModelAndView changeOrdersItem(HttpSession session,@PathVariable String cmd,@PathVariable Long pid,@PathVariable Integer count){
		ModelAndView mav = new ModelAndView();
		Orders orders = (Orders)session.getAttribute(CommonConstant.ORDERS_TEMP);
		if(orders == null){
			orders = new Orders();
			session.setAttribute(CommonConstant.ORDERS_TEMP, orders);
		}
		Product product = cusService.getProductById(pid);
		if(cmd.equals("add")){
			orders.addProduct(product, count);
		}else if(cmd.equals("del"))
		{
			if(pid==null||count==null){
				orders.setItems(null);
			}else{
				orders.delProduct(pid);
			}
		}else if(cmd.equals("set")){
			orders.resetProduct(pid, count);
		}else if(cmd.equals("sub")){
			OrdersItem  oi = orders.findItems(pid);
			oi.setCount(oi.getCount()- count);
			if(oi.getCount() < 0){
				orders.delProduct(pid);
			}
		}
		mav.addObject("OM", orders);
		mav.setViewName("/customer/tmpOrders");
		return mav;
	}
	@RequestMapping(value="/confirmOrders/{ordersId}")
	public ModelAndView confirmOrders(HttpSession session,@PathVariable Long ordersId){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		Credit credit = cusService.confirmOrders(host.getUserId(), ordersId);
		Message msg = new Message();
		msg.setHead("Result of ConfirmOrders");
		msg.setSubject("Credit");
		msg.setMsgDate(new Date());
		msg.setContent(credit.toString());
		mav.addObject("OM", msg);
		mav.setViewName("/system/message");
		return mav;
	}
	@RequestMapping(value="/refuseOrders/{ordersId}",method=RequestMethod.GET)
	public ModelAndView refuseOrders(HttpSession session,@PathVariable Long ordersId){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		cusService.refuseOrders(host.getUserId(), ordersId);
		Message msg = new Message();
		msg.setHead("RefuseOrders");
		msg.setSubject("Result of RefuseOrders");
		msg.setMsgDate(new Date());
		msg.setContent("");
		mav.addObject("OM", msg);
		mav.setViewName("/system/message");
		return mav;
	}
	@RequestMapping(value="/cancelOrders/{ordersId}",method=RequestMethod.GET)
	public ModelAndView cancelOrders(HttpSession session,@PathVariable Long ordersId){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		Orders orders = cusService.cancelOrders(host.getUserId(), ordersId);
		Message msg = new Message();
		msg.setHead("CancelOrders");
		msg.setSubject("Result of CancelOrders->OK");
		msg.setMsgDate(new Date());
		msg.setContent(orders.toString());
		mav.addObject("OM", msg);
		mav.setViewName("/system/message");
		return mav;
	}
	@RequestMapping(value="/getCredits")
	public ModelAndView getCredits(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<Credit> credits = cusService.getCredits(host.getUserId());
		mav.addObject("OM", credits);
		mav.setViewName("/customer/credit");
		return mav;
	}
	/*
	 * TODO
	 */
	@RequestMapping(value="/addEnshrines")
	public ModelAndView addEnshrines(HttpSession session,SearchItem item)
	{
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<SearchItem> result = cusService.getEnshrine(host.getUserId());
		mav.addObject("OM", result);
		return mav;
	}
	/*
	 * TODO
	 */
	@RequestMapping(value="/getEnshrines")
	public ModelAndView getEnshrines(HttpSession session)
	{
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<SearchItem> result = cusService.getEnshrine(host.getUserId());
		mav.addObject("OM", result);
		return mav;
	}
	@RequestMapping(value="/getEmptyComment")
	public ModelAndView getEmptyComment(HttpSession session)
	{
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<Comment> result = cusService.getEmptyComment(host.getUserId());
		mav.addObject("OM", result);
		return mav;
	}
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/getDeals",method=RequestMethod.GET)
	public ModelAndView getDeals(HttpSession session,Date date)
	{
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		if(date==null){
			date = new Date();
			date.setDate(1);
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
		}
		List<Deal>  deals = cusService.getDeals(host.getUserId(),date);
		mav.addObject("OM",deals);
		mav.setViewName("/customer/deal");
		return mav;
	}
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public ModelAndView searchItem(String key,Long btype){
		ModelAndView mav = new ModelAndView();
		List<Business>  bus = null;
		if(key==null&&btype==null){
			bus = cusService.searchBusiness();
		}else{
			bus = cusService.searchBusiness(key);
		}
		mav.addObject("OM", bus);
		return mav;
	}
	@RequestMapping(value="/searchProduct/{type}/{min}/{max}")
	public ModelAndView searchProduct(Long type,Integer min,Integer max){
		ModelAndView mav = new ModelAndView();
		List<Product>  rs = cusService.searchProductBy(null, type, min, max);
		mav.addObject("OM", rs);
		mav.setViewName("/customer/product");
		return mav;
	}
	@RequestMapping(value="/searchProduct/{key}",method=RequestMethod.GET)
	public ModelAndView searchProduct(@PathVariable String key){
		ModelAndView mav = new ModelAndView();
		List<Product> rs = cusService.searchProductByNameLike(key);
		mav.addObject("OM", rs);
		mav.setViewName("/customer/product");
		return mav;
	}
	@RequestMapping(value="/getComment/{cmmId}",method=RequestMethod.GET)
	public ModelAndView getComment(@PathVariable Long cmmId){
		ModelAndView mav = new ModelAndView();
		Comment cmm = cusService.getComment(cmmId);
		System.out.println("Comment:" + cmm);
		if(cmm instanceof ProductComment){
			mav.addObject("OM", cmm);
			mav.setViewName("/customer/comment");
		}
		return mav;
	}
	@RequestMapping(value="/putComment",method=RequestMethod.POST)
	public ModelAndView putComment(Comment cmm){
		ModelAndView mav = new ModelAndView();
		Comment db = cusService.getComment(cmm.getCmmId());
		if(cmm.getScore()==null){
			db.setScore(Comment.GOOD);
		}
		db.setContent(cmm.getContent());
		cusService.updateComment(db);
		mav.addObject("OM", db);
		mav.setViewName("/customer/comment");
		return mav;
	}
	@RequestMapping(value="/getProduct/{productId}",method=RequestMethod.GET)
	public ModelAndView getProduct(@PathVariable Long productId){
		ModelAndView mav = new ModelAndView();
		Product product = cusService.getProductById(productId);
		List<Comment> cmms = cusService.getCommentByTarTblTargetId("ProductComment", productId);
		mav.addObject("OM", new Object[]{product,cmms});
		mav.setViewName("/customer/showProduct");
		return mav;
	}
	@RequestMapping(value="/getBusiness/{userId}",method=RequestMethod.GET)
	public ModelAndView getBusiness(@PathVariable Long userId){
		ModelAndView mav = new ModelAndView();
		Business bus = cusService.getBusinessById(userId);
		List<Product> product = cusService.searchProductBy(new Long[]{userId}, null, null, null);
		mav.addObject("OM", new Object[]{bus,product});
		mav.setViewName("/customer/showBusiness");
		return mav;
	}
}
