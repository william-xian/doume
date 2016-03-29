package com.doume.max.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doume.max.dao.BusinessDao;
import com.doume.max.dao.CreditDao;
import com.doume.max.dao.DealDao;
import com.doume.max.dao.OrdersDao;
import com.doume.max.dao.ProductDao;
import com.doume.max.entity.Business;
import com.doume.max.entity.Comment;
import com.doume.max.entity.Credit;
import com.doume.max.entity.Customer;
import com.doume.max.entity.Deal;
import com.doume.max.entity.Message;
import com.doume.max.entity.Orders;
import com.doume.max.entity.OrdersItem;
import com.doume.max.entity.Product;
import com.doume.max.entity.SearchItem;

@Service
@Transactional
public class CustomerService extends UserService{
	@Autowired
	private CreditDao creditDao;
	@Autowired
	private OrdersDao ordersDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private DealDao dealDao;
	@Autowired
	private BusinessDao busDao;

	public List<Business> searchBusiness(String key){
		return busDao.findNameLike(key);
	}
	public List<Business> searchBusiness(){
		return busDao.findByWeight();
	}

	public List<Product> searchProductByNameLike(String key){
		return productDao.findByNameLike(key);
	}

	public List<Product> searchProductBy(Long userIds[],Long type,Integer min,Integer max){
		return productDao.find(userIds, type, min, max);
	}
	
	public void update(Customer customer)
	{
		customerDao.update(customer);
	}
	
	public List<Orders> getOrders(Long userId) {
		return ordersDao.findByBuyer(userId);
	}
	
	public void addOrders(Long userId,Orders orders) {
		Long sellerId = orders.getSellerId();
		Credit credit = creditDao.find(sellerId, userId);
		Integer creditValue;
		if(credit!=null){
			creditValue = credit.getValue();
		}else{
			creditValue = 0;
		}
		StringBuffer msgContent = new StringBuffer(orders.getOrdersInfo());
		Integer curCredit = orders.getTotalCredit();
		msgContent.append("CurrentCredit:"+ curCredit);
		msgContent.append("LastCredit:" + creditValue);
		if(creditValue + curCredit >= 0)
		{
			msgContent.append("TotalCredit:" + creditValue+curCredit);
			ordersDao.save(orders);
		}else
		{
			msgContent.append("TotalCredit:" + creditValue+curCredit);
			msgContent.append("Your credit not enough!");
		}
		sendMessage(userId,sellerId,Message.ACTUAL|Message.UNREAD,
				"NewOrders:" , msgContent.toString());
	}

	public Orders cancelOrders(Long userId,Long ordersId) {
		Orders dbOrders = ordersDao.get(ordersId);
		if(dbOrders.getBuyerId().equals(userId))
		{
			if(dbOrders.isBusConfrimed()){
				return dbOrders;
			}else{
				ordersDao.remove(dbOrders);
			}
		}
		return null;
	}
	
	public void refuseOrders(Long userId,Long ordersId) {
		Orders dbOrders = ordersDao.get(ordersId);
		StringBuffer sb  = new StringBuffer();
		for(OrdersItem o:dbOrders.getItems())
		{
			Product product = o.getProduct();
			addFailedDeal(product.getSellerId(),userId,product.getProductId(),"Refuse:" + product.getName() +" * " + o.getValue());
			sb.append(product.getName() +" * " + o.getValue()+";");
		}
		ordersDao.remove(dbOrders);
		sendMessage(userId,dbOrders.getSellerId(),Message.UNREAD,
				"Refuse:" , sb.toString());
	}
	
	/*
	 * 扣除积分时，必须由客户确认
	 */
	public Credit confirmOrders(Long userId,Long ordersId) {
		Orders dbOrders = ordersDao.get(ordersId);
		Credit credit = creditDao.find(dbOrders.getSellerId(), userId);
		if(credit == null){
			return null;
		}
		StringBuffer msgContent = new StringBuffer(dbOrders.getOrdersInfo());
		Integer curCredit = dbOrders.getTotalCredit();
		System.out.println("curCredit=" + curCredit);
		msgContent.append("CurrentCredit:"+ curCredit);
		msgContent.append("LastCredit:" + credit);
		if(curCredit<0)
		{
			for(OrdersItem o:dbOrders.getItems())
			{
				Product product = o.getProduct();
				addDeal(product.getSellerId(),userId,product.getProductId(),
						"Confrim:#[" + product.getProductId() +"] * " + o.getValue());
				product.setSelledCount(product.getSelledCount() + o.getValue());
				productDao.update(product);
				addProductComment(userId,product.getProductId(),null,product.getName(),null);
				msgContent.append("\n");
			}
			credit.setValue(credit.getValue() + curCredit);
			msgContent.append("TotalCredit:" + credit.getValue());
			creditDao.update(credit);
			ordersDao.remove(dbOrders);
			sendMessage(userId,dbOrders.getSellerId(),Message.UNREAD,
					"ConfirmOrders:" , msgContent.toString());
		}else
		{
			sendMessage(userId,dbOrders.getSellerId(),Message.UNREAD,
					"PermissionDenied:" , msgContent.toString());
		}
		return credit;
	}
	
	public List<Credit> getCredits(Long userId) {
		return creditDao.findByBuyerId(userId);
	}
	public List<Deal> getDeals(Long userId,Date date) {
		return dealDao.findByBuyerId(userId,date);
	}
	public List<Message> getRecv(Long userId) {
		return messageDao.findByReceiver(userId);
	}
	public List<Message> getSent(Long userId) {
		return messageDao.findBySender(userId);
	}
	public List<Comment> getEmptyComment(Long userId)
	{
		return commentDao.findByUserIdEmpty(userId);
	}
	public void removeComment(Long userId,Long cmmId)
	{
		Comment cmm = commentDao.get(cmmId);
		if(cmm!=null&&cmm.getUserId().equals(userId))
		{
			commentDao.remove(cmm);
		}
	}
	public Customer getCustomer(Long userId) {
		return customerDao.get(userId);
	}
	/*
	 * TODO
	 */
	public List<SearchItem> getEnshrine(Long userId) {
		return null;
	}
	public void updateComment(Comment cmm) {
		commentDao.update(cmm);
	}
}
