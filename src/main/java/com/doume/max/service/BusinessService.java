package com.doume.max.service;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.doume.max.dao.CreditDao;
import com.doume.max.dao.OrdersDao;
import com.doume.max.dao.ProductDao;
import com.doume.max.entity.Business;
import com.doume.max.entity.BusinessComment;
import com.doume.max.entity.Comment;
import com.doume.max.entity.Credit;
import com.doume.max.entity.Customer;
import com.doume.max.entity.Deal;
import com.doume.max.entity.Message;
import com.doume.max.entity.Orders;
import com.doume.max.entity.OrdersItem;
import com.doume.max.entity.Product;
import com.doume.max.entity.User;
import com.doume.max.exception.UserExistException;

@Service
public class BusinessService  extends UserService{
	@Autowired
	private OrdersDao ordersDao;
	@Autowired
	private CreditDao creditDao;
	@Autowired
	private ProductDao productDao;
	public Business update(String rootDir,Business business) throws UserExistException{
		Business db = businessDao.get(business.getUserId());
		if(db!=null&&!db.equals(business)){
			db.setAddr(business.getAddr());
			db.setbName(business.getbName());
			db.getLocation().setWithoutId(business.getLocation());
			db.setInformation(business.getInformation());
			db.setPhoneno(business.getPhoneno());
			CommonsMultipartFile media = business.getHome().getMedia();
			if( media!=null && !media.isEmpty()){
				int idx = media.getOriginalFilename().lastIndexOf('.');
				String suffix = media.getOriginalFilename().substring(idx);
		        String filePath = rootDir + "/images/" + db.getUserId() + "/home" + suffix;
		        db.getHome().setMediaId(db.getUserId()+"/home" + suffix);
		        File file = new File(filePath);
		        try {
		        	media.transferTo(file);
		        } catch (Exception e) {
		        	e.printStackTrace();
		        	return null;
		        }
			}
			businessDao.update(db);
			return db;
		}
		return null;
	}
	public Customer addMember(Long businessId, String userName) {
		User usr = userDao.findByUserName(userName);
		if(usr!=null&&usr.isCustomer()){
			Credit credit = new Credit();
			credit.setSellerId(businessId);
			credit.setBuyerId(usr.getUserId());
			credit.setValue(0);
			creditDao.addCredit(credit);
			return customerDao.get(usr.getUserId());
		}
		return null;
	}
	public List<Customer> getMembers(Long businessId) {
		List<Credit> credits = getCredits(businessId);
		return getMembers(credits);
	}
	/*
	 * TODO 信息屏蔽
	 */
	private List<Customer> getMembers(List<Credit> credits)
	{
		List<Long> ids = new LinkedList<Long>();
		for(Credit c:credits)
		{
			ids.add(c.getBuyerId());
		}
		Long args[] = ids.toArray(new Long[0]);
		if(args.length>0){
			return customerDao.findByIds(args);
		}else{
			return new ArrayList<Customer>(0);
		}
	}
	public List<Credit> getCredits(Long userId) {
		return creditDao.findBySellerId(userId);
	}
	public Credit addCredit(Credit credit) {
		Credit rs = creditDao.addCredit(credit);
		return rs;
	}
	public List<Product> getOwnProduct(Long userId) {
		return productDao.find(new Long[]{userId}, Product.ALL, null, null);
	}
	public boolean addProduct(String rootDir,Product product) {
		Business bus = businessDao.get(product.getSellerId());
		if(bus!=null && bus.getCapacity()>bus.getUsed())
		{
	        product.setMediaId(bus.getUserId()+"/" + bus.getUsed());
	        String filePath = rootDir +product.getMediaId() ;
	        File file = new File(filePath);
	        try {
	        	file.getParentFile().mkdir();
	        	file.createNewFile();
	        	if(product.getMedia()!=null)
	        	{
		        	product.getMedia().transferTo(file);
	        	}
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return false;
	        }
	        bus.setUsed(bus.getUsed() + 1);
	        businessDao.update(bus);
	        productDao.save(product);
	        return true;
		}
        return false;
	}
	public boolean updateProduct(String rootDir, Product product) {
		Business bus = businessDao.get(product.getSellerId());
		Product db = productDao.get(product.getProductId());
		if(bus!=null&&db!=null&&db.getSellerId().equals(product.getSellerId()))
		{
			product.setMediaId(product.getMediaId());
	        String filePath = rootDir + product.getMediaId() ;
	        File file = new File(filePath);
	        try {
	        	file.getParentFile().mkdir();
	        	file.createNewFile();
	        	product.getMedia().transferTo(file);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return false;
	        }
	        productDao.save(product);
	        return true;
		}
        return false;
	}
	public Orders subCredit(Credit credit)
	{
		Credit old = creditDao.find(credit.getSellerId(), credit.getBuyerId());
		if(old != null&&old.getValue() >= credit.getValue())
		{
			Orders orders = new Orders();
			orders.setBuyerId(credit.getBuyerId());
			orders.setSellerId(credit.getSellerId());
			orders.usingCredit();
			orders.addProduct(Product.UNIT, credit.getValue());
			ordersDao.save(orders);
			return orders;
		}else
		{
			return null;
		}
	}
	
	public void acceptOrders(Long userId,Long ordersId) {
		Orders dbOrders = ordersDao.get(ordersId);
		if(dbOrders==null)return;
		if(!dbOrders.isCanceled())
		{
			dbOrders.accept();
			ordersDao.update(dbOrders);
			sendMessage(userId,dbOrders.getBuyerId(),
					Message.ACTUAL|Message.UNREAD,
					"accept:",dbOrders.getOrdersInfo());
		}else
		{
			sendMessage(User.SYSTEM.getUserId(),dbOrders.getBuyerId(),Message.ACTUAL,
					"accept:",dbOrders.getOrdersInfo());
		}
	}

	public void refuseOrders(Long userId,Long ordersId) {
		Orders dbOrders = ordersDao.get(ordersId);
		if(dbOrders==null)return;
		StringBuffer sb  = new StringBuffer(dbOrders.getOrdersInfo());
		for(OrdersItem o:dbOrders.getItems())
		{
			Product product = o.getProduct();
			sb.append(product.getName() +" * " + o.getValue()+";");
			addFailedDeal(userId,dbOrders.getBuyerId(),product.getProductId(),"Refuse:" + product.getName() +" * " + o.getValue());
		}
		ordersDao.remove(dbOrders);
		sendMessage(userId,dbOrders.getBuyerId(),Message.ACTUAL|Message.UNREAD,
				"Refuse:",sb.toString());
	}
	/*
	 * 需要收钱时 商家可以确认
	 */
	public void confirmOrders(Long userId,Long ordersId) {
		Orders dbOrders = ordersDao.get(ordersId);
		if(dbOrders==null)return;
		Credit credit = creditDao.find(userId, dbOrders.getBuyerId());
		if(credit==null)
		{
			credit = new Credit();
			credit.setSellerId(userId);
			credit.setBuyerId(dbOrders.getBuyerId());
			credit.setValue(0);
			credit = creditDao.addCredit(credit);
		}
		Integer curCredit = dbOrders.getTotalCredit();
		StringBuffer msgContent = new StringBuffer(dbOrders.getOrdersInfo());
		msgContent.append("CurrentCredit:"+ curCredit);
		msgContent.append("LastCredit:" + credit.getValue());
		if(curCredit>0)
		{
			for(OrdersItem o:dbOrders.getItems())
			{
				Product product = o.getProduct();
				System.out.println("product:" + o);
				product.setSelledCount(product.getSelledCount() + o.getValue());
				productDao.update(product);
				addDeal(userId,dbOrders.getBuyerId(),product.getProductId(),
						"Confrim:#[" + product.getProductId() +"] * " + o.getValue());
				addProductComment(dbOrders.getBuyerId(),product.getProductId(),null,product.getName(),null);
			}
			credit.setValue(credit.getValue() + curCredit);
			msgContent.append("TotalCredit:" + credit.getValue());
			creditDao.update(credit);
			ordersDao.remove(dbOrders);
			sendMessage(userId,dbOrders.getBuyerId(),Message.ACTUAL|Message.UNREAD,
					"ConfirmOrders:" , msgContent.toString());
		}else
		{
			sendMessage(userId,dbOrders.getBuyerId(),Message.ACTUAL|Message.UNREAD,
					"PermissionDenied:" , msgContent.toString());
		}
	}

	public Business getBusiness(Long userId) {
		return businessDao.get(userId);
	}
	public List<Orders> getOrders(Long sellerId) {
		return ordersDao.findBySeller(sellerId);
	}
	public void addOrders(Long sellerId,Orders orders){
		orders.setSellerId(sellerId);
		ordersDao.save(orders);
	}
	public List<Deal> getDeals(Long sellerId) {
		return dealDao.findBySellerId(sellerId);
	}
	public List<Product> getProductBySeller(Long sellerId) {
		return productDao.find(new Long[]{sellerId}, Product.ALL, null, null);
	}
	public Product getProductById(Long productId) {
		return productDao.get(productId);
	}
	public void removeSuggestion(Long userId, Long sugId) {
		Comment cmm = commentDao.get(sugId);
		if(cmm instanceof BusinessComment){
			if(cmm.getTargetId().equals(userId)){
				commentDao.remove(cmm);
			}
		}
	}
}
