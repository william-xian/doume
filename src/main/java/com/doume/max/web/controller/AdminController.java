package com.doume.max.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.doume.max.cons.CommonConstant;
import com.doume.max.entity.Administrator;
import com.doume.max.entity.Business;
import com.doume.max.entity.Message;
import com.doume.max.entity.News;
import com.doume.max.entity.Product;
import com.doume.max.entity.User;
import com.doume.max.exception.UserExistException;
import com.doume.max.service.AdministratorService;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	protected static final Logger logger = Logger.getLogger(AdminController.class);
	@Autowired
	protected AdministratorService adminService;
	@RequestMapping(value="/getLogin",method=RequestMethod.GET)
	public ModelAndView getLogin(){
		ModelAndView mav = new ModelAndView();
		User user = new User();
		user.setUserId(0L);
		mav.addObject("OM", user);
		mav.setViewName("/admin/login");
		return mav;
	}
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView login(HttpSession session,@Valid @ModelAttribute("OM") User user,BindingResult bindingResult){
		ModelAndView mav = new ModelAndView();
		User host = adminService.loginByUser(user.getUserName(), user.getPassword());/* 此处有意为之，用户名和和OpenId都可以 */
		if(host != null){
			adminService.loginSuccess(host);
			Administrator admin = adminService.getAdmin(host.getUserId());
			session.setAttribute(CommonConstant.USER_ME, host);
			session.setAttribute(CommonConstant.ADMIN_ME, admin);
			mav.addObject("OM", admin.getBlist());
			mav.setViewName("/admin/home");
		} else {
			user.setUserName("");
			user.setPassword("");
			mav.addObject(CommonConstant.USER_ME, user);
			mav.setViewName("/admin/login");
			return mav;
		}

		return mav;
	}
	@RequestMapping(value="/getHome",method=RequestMethod.GET)
	public ModelAndView getHome(HttpSession session){
		ModelAndView mav = new ModelAndView();
		Administrator admin = (Administrator)session.getAttribute(CommonConstant.ADMIN_ME);
		mav.addObject("OM", admin.getBlist());
		mav.setViewName("/admin/home");	
		return mav;
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session){
		session.removeAttribute(CommonConstant.USER_ME);
		return "forward:/index.jsp";
	}
	@RequestMapping(value="/getPutUser/{userId}",method=RequestMethod.GET)
	public ModelAndView getPutUser(@PathVariable("userId") Long userId){
		ModelAndView mav = new ModelAndView();
		User u =  null;
		if(userId!=0){
			u = adminService.getUser(userId);
		}
		if(u==null){
			u = new User();
			u.setUserId(0L);
		}
		mav.addObject("OM", u);
		mav.setViewName("/admin/putUser");
		return mav;
	}
	@RequestMapping(value="/putUser",method=RequestMethod.POST)
	public ModelAndView putUser(HttpSession session,@Valid @ModelAttribute("OM") User user,
			@RequestParam Boolean locked, BindingResult bindingResult) {

		Administrator admin = (Administrator)session.getAttribute(CommonConstant.ADMIN_ME);
		ModelAndView mav = new ModelAndView();
		try {
			if (!bindingResult.hasErrors()) {
				if (locked) {
					adminService.lockBusiness(admin, user.getUserId(), "");
				} else {
					adminService.unlockBusiness(admin, user.getUserId(), "");
				}
				if (user.getUserId().equals(0L)) {
					adminService.register(user);
					if(user.isBusiness()){
						Business business = adminService.getBusiness(user.getUserId());
						adminService.addBusiness(admin, business);
						mav.addObject("OM", business);
						mav.setViewName("/admin/updateBusiness");
						return mav;
					}
				} else {
					user = adminService.update(user);
				}
				mav.addObject("OM", user);
			}
		} catch (UserExistException e) {
			ObjectError error = new ObjectError("userName",
					"UserExistException");
			bindingResult.addError(error);
		}
		return mav;
	}
	@RequestMapping(value="/getUpdateBusiness/{userId}",method=RequestMethod.GET)
	public ModelAndView getUpdateBusiness(@PathVariable("userId") Long userId){
		ModelAndView mav = new ModelAndView();
		if(userId !=0 ){
			Business b = adminService.getBusiness(userId);
			if(b==null)b = new Business();
			mav.addObject("OM", b);
		}else{
			mav.addObject("OM", new Business());
		}
		mav.setViewName("/admin/updateBusiness");
		return mav;
	}
	@RequestMapping(value="/updateBusiness",method=RequestMethod.POST)
	public ModelAndView updateBusiness(HttpSession session,@Valid @ModelAttribute("OM") Business business,BindingResult bindingResult){
		ModelAndView mav = new ModelAndView();
		if(!bindingResult.hasErrors()){
			Administrator admin = (Administrator)session.getAttribute(CommonConstant.ADMIN_ME);
			Business b = adminService.updateBusiness(admin, business);
			if(b!=null)business = b;
			mav.addObject("OM", b);
		}
		return mav;
	}
	@RequestMapping(value="/getProduct",method=RequestMethod.GET)
	public ModelAndView getProduct(String productSearch){
		ModelAndView mav = new ModelAndView();
		List<Product>  result = adminService.searchProduct(productSearch);
		mav.addObject("OM", result);
		mav.setViewName("/admin/product");
		return mav;
	}
	@RequestMapping(value="/getPutProduct/{productId}",method=RequestMethod.GET)
	public ModelAndView getPutProduct(@PathVariable("productId") Long productId){
		ModelAndView mav = new ModelAndView();
		Product product = adminService.getProduct(productId);
		if(product == null){
			product = new Product();
			product.setProductId(0L);
		}
		mav.addObject("OM", product);
		mav.setViewName("/admin/putProduct");
		return mav;
	}
	@RequestMapping(value="/putProduct",method=RequestMethod.POST)
	public ModelAndView putProduct(@Valid @ModelAttribute("OM") Product product,
			BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		if (!bindingResult.hasErrors()) {
			String rootDir = rpe.getRealPath("");
			Product rs = null;
			if (product.getProductId() != null && !product.getProductId().equals(0L)) {
				rs = adminService.updateProduct(rootDir, product);
			} else {
				rs = adminService.uploadProduct(rootDir, product);
			}
			if (rs != null) {
				rs = product;
			}
		}
		mav.addObject("OM", product);
		return mav;
	}
	@RequestMapping(value="/getNews",method=RequestMethod.GET)
	public ModelAndView getNews(HttpSession session,String keyword){
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		List<Message> recv = adminService.getReceivedMessage(host.getUserId());
		List<Message> sent = adminService.getSentMessage(host.getUserId());
		List<News> news = adminService.findNews(keyword);
		mav.addObject("OM", new Object[]{recv,sent,news});
		mav.setViewName("/admin/news");
		return mav;
	}
	@RequestMapping(value="/getPutNews/{newsId}",method=RequestMethod.GET)
	public ModelAndView getPutNews(@PathVariable("newsId") Long newsId){
		ModelAndView mav = new ModelAndView();
		News news = adminService.getNews(newsId);
		if(news==null){
			news = new News();
		}
		mav.addObject("OM", news);
		mav.setViewName("/admin/putNews");
		return mav;
	}
	@RequestMapping(value="/putNews")
	public ModelAndView putNews(@ModelAttribute("OM") News news)
	{
		ModelAndView mav = new ModelAndView();
		String rootDir= rpe.getRealPath("");
		adminService.putNews(rootDir,news);
		if(news != null)
			mav.addObject("OM", news);
		else mav.addObject("OM", new News());
		return mav;
	}
	@RequestMapping(value="/removeNews/{newsId}",method=RequestMethod.GET)
	public ModelAndView removeNews(@PathVariable("newsId") Long newsId)
	{
		ModelAndView mav = new ModelAndView();
		adminService.removeNews(newsId);Message msg = new Message();
		msg.setMsgDate(new Date());
		msg.setHead("RemoveNews");
		msg.setSubject("Result:");
		msg.setContent("OK");
		mav.addObject("OM",msg);
		mav.setViewName("/system/message");
		return mav;
	}
	@RequestMapping(value="/members")
	public ModelAndView getMembers(HttpSession session)
	{
		ModelAndView mav = new ModelAndView();
		Administrator admin = (Administrator)session.getAttribute(CommonConstant.ADMIN_ME);
		mav.addObject("OM",admin.getBlist());
		return mav;
	}
	@RequestMapping(value="/searchProduct")
	public ModelAndView searchProduct(String conditions){
		ModelAndView mav = new ModelAndView();
		mav.addObject("OM",adminService.searchProduct(conditions));
		return mav;
	}
	@RequestMapping(value="/getCommentByProductId/{productId}",method=RequestMethod.GET)
	public ModelAndView getCommentByProduct(@PathVariable("productId")Long productId){
		ModelAndView mav = new ModelAndView();
		mav.addObject("OM",adminService.getCommentByTarTblTargetId("ProductComment",productId));
		mav.setViewName("/admin/productComment");
		return mav;
	}
	@RequestMapping(value="/removeComment/{cmmId}")
	public ModelAndView removeComment(HttpSession session,@PathVariable("cmmId")Long cmmId)
	{
		Administrator admin = (Administrator)session.getAttribute(CommonConstant.ADMIN_ME);
		adminService.deleteComment(admin, cmmId);
		ModelAndView mav = new ModelAndView();
		Message msg = new Message();
		msg.setMsgDate(new Date());
		msg.setHead("RemoveComment");
		msg.setSubject("Result:");
		msg.setContent("OK");
		mav.addObject("OM",msg);
		mav.setViewName("/system/message");
		return mav;
	}
	@RequestMapping(value="/sendMessage")
	public ModelAndView sendMessage(HttpSession session,String receivers,String subject,String content)
	{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/admin/sendMessage");
		if(receivers==null||content==null)return mav;
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		if(receivers.equals("AllUsers")){
			adminService.sendMessage(host.getUserId(),null,Message.TOUSER|Message.RECEIVER_DEL,subject,content);
		}else  if(receivers.equals("AllBusienss")){
			adminService.sendMessage(host.getUserId(),null,Message.TOBUSINESS|Message.RECEIVER_DEL,subject,content);
		}else if(receivers.matches("[0-9,]*")){
			String[] ss = receivers.split(",");
			for(String sid:ss){
				adminService.sendMessage(host.getUserId(),Long.valueOf(sid),Message.UNREAD,subject,content);
			}
		}
		return mav;
	}
	@RequestMapping(value="/removeMessage/{msgId}")
	public ModelAndView removeMessage(HttpSession session,@PathVariable Long msgId)
	{
		ModelAndView mav = new ModelAndView();
		User host = (User)session.getAttribute(CommonConstant.USER_ME);
		adminService.removeMessage(host.getUserId(), msgId);
		Message msg = new Message();
		msg.setMsgDate(new Date());
		msg.setHead("RemoveComment");
		msg.setSubject("Result:");
		msg.setContent("OK");
		mav.addObject("OM",msg);
		mav.setViewName("/system/message");
		return mav;
	}
}
