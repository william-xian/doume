package com.doume.max.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doume.max.entity.Business;
import com.doume.max.entity.Product;
import com.doume.max.entity.News;
import com.doume.max.dao.BusinessDao;
import com.doume.max.dao.ProductDao;
import com.doume.max.dao.NewsDao;

@Service
public class SearchService {
	@Autowired
	private BusinessDao businessDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private NewsDao newsDao;
	
	public List<Business> searchBusinessNameLike(String bName)
	{
		List<Business> rs = businessDao.findNameLike("%"+bName +"%");
		return rs;
	}
	public List<Business> searchBusinessType(Long bType)
	{
		List<Business> rs = businessDao.findByType(bType);
		return rs;
	}
	public Map<Business, Double> searchBusinessByLocation(double lng,double lat)
	{
		return businessDao.findByLocation(lng, lat);
	}
	
	public List<Product> searchProduct(Long[] sellerIds,Long productType,Integer minPrice,Integer maxPrice)
	{
		List<Product> rs = productDao.find(sellerIds, productType, minPrice, maxPrice);
		return rs;
	}
	public List<News> searchNews(Long newsType)
	{
		List<News> rs = newsDao.findByType(newsType);
		return rs;
	}
}
