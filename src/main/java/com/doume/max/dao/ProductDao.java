package com.doume.max.dao;

import java.util.List;

import com.doume.max.entity.Product;

/*
 * 商品
 */
public interface ProductDao extends BaseDao<Product>{
	public void switchLockBySellerId(Long sellerId);
	public List<Product> find(Long[] sellerIds,Long productType,Integer minPrice,Integer maxPrice);
	public List<Product> findByNameLike(String name);
	public List<Product> findBySelledCount(Long productType);
	public List<Product> findByScore(Long productType);
	public List<Product> findByCredit(Long sellerId,Integer creditValue);
}
