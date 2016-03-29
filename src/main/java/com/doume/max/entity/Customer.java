package com.doume.max.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;

@Entity
public class Customer extends BaseEntity{
	private static final long serialVersionUID = 1L;
	@Id
	private Long userId;
	private String name;
	@Pattern(regexp = ".{1,6}")
	private String sex;
	@Temporal(value=TemporalType.TIMESTAMP)  
	private Date birthday;
	@Temporal(value=TemporalType.DATE)
	private Date grade;
	private String contact;
	private String addr;
	private String mediaId;
	@OneToMany(cascade={CascadeType.ALL})
	private List<Product> enshrine;
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="productId")
	private Product product;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getGrade() {
		return grade;
	}
	public void setGrade(Date grade) {
		this.grade = grade;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public List<Product> getEnshrine() {
		return enshrine;
	}
	public void setEnshrine(List<Product> enshrine) {
		this.enshrine = enshrine;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
}
