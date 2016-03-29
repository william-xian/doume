package com.doume.max.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Orders extends BaseEntity{
	private static final long serialVersionUID = 1L;
	public final static Integer DEAFULT = 0;
	public final static Integer MONEY = 0;
	public final static Integer CREDIT = 1;
	public final static Integer CANCEL = 2;
	public final static Integer ACCEPT = 4;
	public final static Integer REFUSE = 8;
	public final static Integer CONFIRM_BUS = 16;
	public final static Integer CONFIRM_CUS = 32;
	public final static Integer SUB_CREDIT = 64;
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long   ordersId;
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	private List<OrdersItem> items;
	private Integer ordersStatus;
	private String ordersInfo;
	private Integer totalMoney;
	private Integer totalCredit;
	private Long sellerId;
	private Long buyerId;
	public Orders(){
		ordersStatus = DEAFULT;
	}
	public Long getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(Long ordersId) {
		this.ordersId = ordersId;
	}
	public List<OrdersItem> getItems() {
		return items;
	}
	public void setItems(List<OrdersItem> ordersItems) {
		this.items = ordersItems;
	}
	public void addProduct(Product product,Integer count) {
		if(this.items==null){
			items = new LinkedList<OrdersItem>();
		}
		OrdersItem orderItem=null;
		for(OrdersItem oi:items){
			if(oi.getProduct().getProductId().equals(product.getProductId())){
				orderItem = oi;
				break;
			}
		}
		if(orderItem == null){
			orderItem = new OrdersItem();
			orderItem.setProduct(product);
			orderItem.setValue(count);
		}else{
			orderItem.setCount(orderItem.getCount() + count);
		}
		items.add(orderItem);
	}
	@Transient
	public OrdersItem findItems(Long pid){
		for(int i = 0; i < items.size(); i++){
			if(items.get(i).getProduct().getProductId().equals(pid)){
				return items.get(i);
			}
		}
		return null;
	}
	
	public void delProduct(Long pid){
		OrdersItem oi = findItems(pid);
		if(oi!=null&&items!=null){
			items.remove(oi);
		}
	}
	public void resetProduct(Long pid,Integer count){
		OrdersItem oi = findItems(pid);
		if(oi!=null){
			oi.setCount(count);
		}
	}
	@Transient
	public boolean isCanceled()
	{
		return (ordersStatus&CANCEL) != 0;
	}
	public void cancel()
	{
		ordersStatus=ordersStatus|CANCEL;
	}
	@Transient
	public boolean isAccept()
	{
		return (ordersStatus&ACCEPT) != 0;
	}
	public void accept()
	{
		ordersStatus=ordersStatus|ACCEPT;
	}
	public void usingMoney()
	{
		ordersStatus = ordersStatus|(~CREDIT);
	}
	@Transient 
	public boolean isUsingMoney()
	{
		return (ordersStatus&CREDIT) == 0;
	}
	@Transient 
	public boolean isUsingCredit()
	{
		return (ordersStatus&CREDIT) != 0;
	}
	public void usingCredit()
	{
		ordersStatus = ordersStatus|CREDIT;
	}
	public void busConfrim(){
		ordersStatus=ordersStatus|CONFIRM_BUS;
	}
	public void cusConfrim(){
		ordersStatus=ordersStatus|CONFIRM_CUS;
	}
	@Transient
	public boolean isBusConfrimed()
	{
		return (ordersStatus&CONFIRM_BUS) == CONFIRM_BUS;
	}
	@Transient
	public boolean isCusConfrimed()
	{
		return (ordersStatus&CONFIRM_CUS) == CONFIRM_CUS;
	}
	public Integer getOrdersStatus() {
		return ordersStatus;
	}
	public void setOrdersStatus(Integer ordersStatus) {
		this.ordersStatus = ordersStatus;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	public Integer getTotalCredit()
	{
		if(totalCredit==null)
		{
			proOrdersInfo();
		}
		return totalCredit;
	}
	public Integer getTotalMoney(){
		if(totalMoney==null){
			proOrdersInfo();
		}
		return totalMoney;
	}
	public void proOrdersInfo()
	{
		StringBuffer msgContent = new StringBuffer();
		Integer curCredit = 0;
		Integer curMoney = 0;
		for(OrdersItem o:getItems())
		{
			Product product = o.getProduct();
			curMoney += product.getPrice();
			msgContent.append(product.getName()+",");
			if(o.isUsingCredit())
			{
				msgContent.append("-" + product.getCreditPrice()+" * "+ o.getValue() + ",");
				curCredit -= product.getCreditPrice() * o.getValue();
			}else
			{
				msgContent.append("+" + product.getRetCredit()+" * "+ o.getValue() + ",");
				curCredit += product.getRetCredit() * o.getValue();
			}
			msgContent.append("\n");
		}
		msgContent.append("CurrentCredit:"+ curCredit);
		this.totalCredit = curCredit;
		this.totalMoney = curMoney;
		this.ordersInfo = msgContent.toString();
	}

	public String getOrdersInfo() {
		if(ordersInfo ==null)
		{
			proOrdersInfo();
		}
		return ordersInfo;
	}
	
}
