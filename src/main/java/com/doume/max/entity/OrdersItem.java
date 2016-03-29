package com.doume.max.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrdersItem extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long oid;
	@ManyToOne
	@JoinColumn(name="productId",referencedColumnName="productId")
	private Product product;
	private Boolean isUsingCredit = false;
	private Integer count;
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getValue() {
		return count;
	}
	public void setValue(Integer value) {
		this.count = value;
	}
	public Boolean isUsingCredit() {
		return isUsingCredit;
	}
	
	public Boolean getIsUsingCredit() {
		return isUsingCredit;
	}
	public void setIsUsingCredit(Boolean isUsingCredit) {
		this.isUsingCredit = isUsingCredit;
	}
	public void usingCredit() {
		this.isUsingCredit = true;
	}
	public void useMoney()
	{
		this.isUsingCredit = false;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
