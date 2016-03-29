package com.doume.max.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.mortbay.jetty.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.doume.max.entity.Product;
import com.doume.max.entity.User;
import com.doume.max.exception.UserExistException;
import com.doume.max.service.BusinessService;

@Controller
@RequestMapping("/business")
public class BusinessController extends BaseController {
	protected static final Logger logger = Logger.getLogger(BusinessController.class);
	@Autowired
	protected BusinessService  busService;
	@RequestMapping(value="/getLogin")
	public ModelAndView getLogin(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("OM", new User());
		mav.setViewName("/business/login");
		return mav;
	}
	@RequestMapping(value="/login")
	public ModelAndView login(HttpSession session,@Valid @ModelAttribute("OM") User user,BindingResult bindingResult){
		ModelAndView mav = new ModelAndView();
		if(bindingResult.hasErrors())
		{
			mav.addObject("OM",bindingResult.getFieldErrors().toString());
		}else
		{
			User host = null;
			if(user.getUserName()!=null){
				host = busService.loginByUser(user.getUserName(), user.getPassword());
			}
			if((host != null) && host.isBusiness())
			{
				Business bus = busService.getBusiness(host.getUserId());
				session.setAttribute(CommonConstant.USER_ME, host);
				session.setAttribute(CommonConstant.BUSINESS_ME, bus);
				return getOrders(session);
			}else{
				bindingResult.addError(new ObjectError("userName","error"));
				bindingResult.addError(new ObjectError("password","or error"));
				mav.addObject("OM", new User());
				mav.setViewName("/business/login");
			}
		}
		return mav;
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session){
		session.removeAttribute(CommonConstant.USER_ME);
		return "forward:/index.jsp";
	}
	@RequestMapping(value="/getHome",method=RequestMethod.GET)
	public ModelAndView getHome(HttpSession session)
	{
		return getOrders(session);
	}
	@RequestMapping(value="/getPrivate",method=RequestMethod.GET)
	public ModelAndView getPrivate(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<Message> msges = busService.getMessage(host.getUserId(), Message.ALL);
		List<Product> product = busService.getProductBySeller(host.getUserId());
		List<Deal> deals = busService.getDeals(host.getUserId());
		List<Comment> sug = busService.getCommentByTarTblTargetId("BusinessComment", host.getUserId());
		mav.addObject("OM",new Object[]{msges,product,deals,sug});
		mav.setViewName("/business/private");
		return mav;
	}
	@RequestMapping(value="/getMore",method=RequestMethod.GET)
	public ModelAndView getMore(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/business/more");
		return mav;
	}	
	@RequestMapping(value="/getUser",method=RequestMethod.GET)
	public ModelAndView getUser(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		mav.addObject("OM",host);
		mav.setViewName("/business/updateUser");
		return mav;
	}
	@RequestMapping(value="/updateUser",method=RequestMethod.POST)	
	public ModelAndView updateUser(HttpSession session,@Valid @ModelAttribute("OM")User user,BindingResult bindingResult){
		ModelAndView mav = new ModelAndView();
		if(bindingResult.hasErrors()){
			mav.addObject("OM", user);
		}else{
			try {
				User host =(User)session.getAttribute(CommonConstant.USER_ME);
				host.setUserName(user.getUserName());
				host.setPassword(user.getPassword());
				host = busService.update(host);
				mav.addObject("OM", host);
			} catch (UserExistException e) {
				mav.addObject("OM", "UserExistException");
			}
		}
		return mav;
	}
	@RequestMapping(value="/getBusiness",method=RequestMethod.GET)
	public ModelAndView getBusiness(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		Business bus = busService.getBusiness(host.getUserId());
		mav.addObject("OM",bus);
		mav.setViewName("/business/updateBusiness");
		return mav;
	}
	@RequestMapping(value="/updateBusiness",method=RequestMethod.POST)
	public ModelAndView updateBusiness(HttpSession session,@Valid @ModelAttribute("OM") Business business,BindingResult bindingResult){
		ModelAndView mav = new ModelAndView();
		if(bindingResult.hasErrors()){
			return mav;
		}
		try {
			String rootDir = rpe.getRealPath("");
			User host = (User)session.getAttribute(CommonConstant.USER_ME);
			business.setUserId(host.getUserId());
			Business bus = busService.update(rootDir,business);
			session.setAttribute(CommonConstant.BUSINESS_ME, bus);
		} catch (UserExistException e) {
			e.printStackTrace();
			mav.addObject("OM",e);
		}
		return mav;
	}
	@RequestMapping(value="/getProduct/{productId}",method=RequestMethod.GET)
	public ModelAndView updateProduct(@PathVariable Long productId){
		ModelAndView mav = new ModelAndView();
		Product product = busService.getProductById(productId);
		mav.addObject("OM", product);
		mav.setViewName("/business/updateProduct");
		return mav;
	}
	@RequestMapping(value="/updateProduct",method=RequestMethod.POST)
	public ModelAndView updateProduct(HttpServletRequest request,
			@Valid @ModelAttribute("OM") Product product) {
		ModelAndView mav = new ModelAndView();
		String rootDir = this.getAppbaseUrl(request, "/");
		User host = (User)request.getAttribute(CommonConstant.USER_ME);
		product.setSellerId(host.getUserId());
		Message msg = new Message();
		msg.setMsgDate(new Date());
		msg.setHead("UpdateProduct");
		if (product.getProductId() == null) {
			if (busService.addProduct(rootDir, product)) {
				msg.setSubject("Result:");
				msg.setContent(HttpStatus.OK);
			} else {
				msg.setSubject("Result Error:");
				msg.setContent("Lack of Capacity!");
			}
		} else {
			if (busService.updateProduct(rootDir, product)) {
				msg.setSubject("Result:");
				msg.setContent(HttpStatus.OK);
			} else {
				msg.setSubject("Result Error:");
				msg.setContent(HttpStatus.Expectation_Failed);
			}
		}
		mav.addObject("OM", msg);
		mav.setViewName("/system/message");
		return mav;
	}
	@RequestMapping(value="/getMembers",method=RequestMethod.GET)
	public ModelAndView getMembers(HttpSession session)
	{
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<Customer> result = busService.getMembers(host.getUserId());
		mav.addObject("OM",result);
		mav.setViewName("/business/members");
		return mav;
	}
	@RequestMapping(value="/getSendMessage/{receiverId}")
	public ModelAndView getSendMessage(HttpSession session,@PathVariable Long receiverId){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		Message msg = new Message();
		msg.setSenderId(host.getUserId());
		msg.setReceiverId(receiverId);
		mav.addObject("OM", msg);
		mav.setViewName("/business/sendMessage");
		return mav;
	}
	@RequestMapping(value="/sendMessage",method=RequestMethod.POST)
	public ModelAndView sendMessage(@ModelAttribute("OM") Message msg){
		ModelAndView mav = new ModelAndView();
		busService.sendMessage(msg.getSenderId(),msg.getReceiverId(),Message.ACTUAL,
				msg.getSubject(),msg.getContent());
		mav.addObject("OM",msg);
		mav.setViewName("/business/sendMessage");
		return mav;
	}
	@RequestMapping(value="/sendMessage",method=RequestMethod.GET)
	public ModelAndView sendMessage(HttpSession session,String receivers,String subject,String content)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/business/sendMessage");
		if(receivers==null||content==null)return mav;
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		if(receivers.equals("AllMembers")){
			busService.sendMessage(host.getUserId(),null,Message.TOUSER|Message.RECEIVER_DEL,subject,content);
		}else if(receivers.matches("[0-9,]*")){
			String[] ss = receivers.split(",");
			for(String sid:ss){
				busService.sendMessage(host.getUserId(),Long.valueOf(sid),Message.UNREAD,subject,content);
			}
		}
		return mav;
	}
	@RequestMapping(value="/removeMessage/{msgId}",method=RequestMethod.GET)
	public ModelAndView removeMessage(HttpSession session,@PathVariable Long msgId)
	{
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		ModelAndView mav = new ModelAndView();
		busService.removeMessage(host.getUserId(), msgId);
		mav.addObject("OM",HttpStatus.OK);
		return mav;
	}
	@RequestMapping(value="/getOrders",method=RequestMethod.GET)
	public ModelAndView getOrders(HttpSession session){
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		ModelAndView mav = new ModelAndView();
		List<Orders> orders = busService.getOrders(host.getUserId());
		mav.addObject("OM",orders);
		mav.setViewName("/business/home");
		return mav;
	}
	@RequestMapping(value="/getOrders/{ordersId}",method=RequestMethod.GET)
	public ModelAndView getOrders(@PathVariable Long ordersId){
		ModelAndView mav = new ModelAndView();
		Orders order = busService.getOrdersById(ordersId);
		mav.addObject("OM",order);
		mav.setViewName("/business/orders");
		return mav;
	}
	@RequestMapping(value="/addOrders")
	public ModelAndView addOrders(HttpSession session,Orders orders){
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		ModelAndView mav = new ModelAndView();
		busService.addOrders(host.getUserId(),orders);
		List<Orders> rs = busService.getOrders(host.getUserId());
		mav.addObject("OM",rs);
		mav.setViewName("/business/orders");
		return mav;
	}
	@RequestMapping(value="/confirmOrders/{ordersId}",method=RequestMethod.GET)
	public ModelAndView confirmOrders(HttpSession session,@PathVariable Long ordersId){
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		busService.confirmOrders(host.getUserId(), ordersId);
		return getOrders(session);
	}
	@RequestMapping(value="/refuseOrders/{ordersId}",method=RequestMethod.GET)
	public ModelAndView refuseOrders(HttpSession session,@PathVariable Long ordersId){
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		busService.refuseOrders(host.getUserId(), ordersId);
		return getOrders(session);
	}
	@RequestMapping(value="/addCredit",method=RequestMethod.GET)
	public ModelAndView addCredit(HttpSession session,String userName,Integer value){
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		ModelAndView mav = new ModelAndView();
		if(userName==null||value==null){
			mav.setViewName("/business/addCredit");
			return mav;
		}
		Message msg = new Message();
		msg.setMsgDate(new Date());
		msg.setHead("AddCredit");
		User usr = busService.getUserByName(userName);
		if(usr!=null)
		{
			Credit credit = new Credit();
			credit.setSellerId(host.getUserId());
			credit.setBuyerId(usr.getUserId());
			credit.setValue(value);
			Credit rs = busService.addCredit(credit);
			msg.setSubject("Result:");
			msg.setContent(rs.toString());
		}else{
			msg.setSubject("Error:");
			msg.setContent(userName + " isn't existing.");
		}
		mav.addObject("OM",msg);
		mav.setViewName("/system/message");
		return mav;
	}
	@RequestMapping(value="/subCredit")
	public ModelAndView subCredit(HttpSession session,String userName,Integer value){
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		ModelAndView mav = new ModelAndView();
		if(userName==null||value==null){
			mav.setViewName("/business/subCredit");
			return mav;
		}
		Message msg = new Message();
		msg.setMsgDate(new Date());
		msg.setHead("SubCredit");
		User usr = busService.getUserByName(userName);
		if(usr!=null)
		{
			Credit credit = new Credit();
			credit.setSellerId(host.getUserId());
			credit.setBuyerId(usr.getUserId());
			credit.setValue(value);

			Orders orders = busService.subCredit(credit);
			msg.setSubject("Result:");
			msg.setContent(orders.toString());
		}else{
			msg.setSubject("Error:");
			msg.setContent(userName + " isn't existing.");
		}
		mav.addObject("OM",msg);
		mav.setViewName("/system/message");
		return mav;
	}
	@RequestMapping(value="/addMember",method=RequestMethod.GET)
	public ModelAndView addMember(HttpSession session,String userName){
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		ModelAndView mav = new ModelAndView();
		if(userName==null){
			mav.setViewName("/business/addMember");
			return mav;
		}
		Customer customer = busService.addMember(host.getUserId(), userName);
		if (customer != null) {
			return getMembers(session);
		} else {
			Message msg = new Message();
			msg.setMsgDate(new Date());
			msg.setHead("AddMember");
			msg.setSubject("Result:");
			msg.setContent(userName + " is non existing.");
			mav.setViewName("/system/message");
			return mav;
		}
	}
	@RequestMapping(value="/getDeals")
	public ModelAndView getDeals(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<Deal> deals= busService.getDeals(host.getUserId());
		mav.addObject("OM", deals);
		return mav;
	}
	@RequestMapping(value="/getProduct")
	public ModelAndView getProduct(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<Product> product= busService.getProductBySeller(host.getUserId());
		mav.addObject("OM", product);
		return mav;
	}
	@RequestMapping(value="/getSuggestion")
	public ModelAndView getSuggestion(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<Comment> sugg= busService.getCommentByTarTblTargetId("BusinessComment", host.getUserId());
		mav.addObject("OM", sugg);
		return mav;
	}
	@RequestMapping(value="/removeSuggestion/{sugId}")
	public ModelAndView removeSuggestion(HttpSession session,@PathVariable Long sugId){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		busService.removeSuggestion(host.getUserId(),sugId);
		mav.addObject("OM", HttpStatus.OK);
		return mav;
	}
}
