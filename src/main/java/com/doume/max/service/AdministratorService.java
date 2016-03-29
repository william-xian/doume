package com.doume.max.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.maven.shared.utils.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.doume.max.dao.AdministratorDao;
import com.doume.max.dao.ProductDao;
import com.doume.max.entity.Administrator;
import com.doume.max.entity.Business;
import com.doume.max.entity.Comment;
import com.doume.max.entity.Product;
import com.doume.max.entity.News;
import com.doume.max.entity.User;

@Service
public class AdministratorService extends UserService{

	@Autowired
	private AdministratorDao adminDao;
	@Autowired
	private ProductDao productDao;
	public Administrator getAdmin(Long id)
	{
		return adminDao.get(id);
	}
	public Business getBusiness(Long userId){
		return businessDao.get(userId);
	}
	public void addBusiness(Administrator admin,Business business)
	{
		admin.addBusiness(business);
		adminDao.update(admin);
		this.addDeal(User.SYSTEM.getUserId(), admin.getUserId(), Product.UNIT.getProductId(), "addBusiness");
	}
	public void update(Administrator admin){
		adminDao.update(admin);
	}
	public Business updateBusiness(Administrator admin,Business newData)
	{
		Business oldData = businessDao.get(newData.getUserId());
		if(oldData != null && !oldData.equals(newData))
		{
			oldData.setAddr(newData.getAddr());
			oldData.getHome().setWithoutId(newData.getHome());
			oldData.setBalance(newData.getBalance());
			oldData.setbName(newData.getbName());
			oldData.setbType(newData.getbType());
			oldData.setPpm(newData.getPpm());
			oldData.setPhoneno(newData.getPhoneno());
			oldData.setInformation(newData.getInformation());
			oldData.setCapacity(newData.getCapacity());
			oldData.setUsed(newData.getUsed());
			oldData.getLocation().setWithoutId(newData.getLocation());
			oldData.setWeight(newData.getWeight());
			businessDao.update(oldData);
			addDeal(admin.getUserId(), oldData.getUserId(), Product.UNIT.getProductId(),
					oldData.authorityInfo() + " --> " + newData.authorityInfo());
			return oldData;
		}
		return null;
	}
	public void payBusiness(Administrator admin,Long bId,Integer money)
	{
		Business bus = businessDao.get(bId);
		if(bus != null) {
			bus.setBalance(bus.getBalance() + money);
			businessDao.update(bus);
			this.addDeal(admin.getUserId(), bId, Product.UNIT.getProductId(), "businessPay:" + money);
		}
	}
	public void lockBusiness(Administrator admin,Long bId,String reason)
	{
		Business bus = businessDao.get(bId);
		if(bus != null) {
			bus.lock();
			businessDao.update(bus);
			this.addDeal(admin.getUserId(), bId, Product.UNIT.getProductId(), "lock:" + reason);
		}
	}
	public void unlockBusiness(Administrator admin,Long bId,String reason)
	{
		Business bus = businessDao.get(bId);
		if(bus != null) {
			bus.unlock();
			businessDao.update(bus);
			this.addDeal(admin.getUserId(), bId, Product.UNIT.getProductId(), "unlock:" + reason);
		}
	}
	public void deleteComment(Administrator admin,Long cmmId){
		Comment cmm = commentDao.get(cmmId);
		if(cmm!=null)
		{
			commentDao.remove(cmm);
		}
	}
	public List<Product> searchProduct(String conditions) {
		return productDao.findByNameLike("%"+conditions+"%");
	}
	
	private String saveImage(CommonsMultipartFile cmf,String root,String identify){
		if(cmf==null){
	        String mediaId = identify + ".png";
	        File defaultProduct = new File(root+"/images/0/product.png");
	        File media = new File(root + "/images/" + mediaId);
	        try {
				FileUtils.copyFile(defaultProduct, media);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        return mediaId;
		}else{
			String ofn =cmf.getOriginalFilename();
			String mediaId = identify+ ofn.substring(ofn.lastIndexOf('.'));
	        String filePath = root + "/images/" +mediaId;
	        File file = new File(filePath);
	        try {
	        	file.getParentFile().mkdir();
	        	file.createNewFile();
	        	cmf.transferTo(file);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	return null;
	        }
	        return mediaId;
		}
	}
	public Product uploadProduct(String rootDir,Product product) {
		System.out.println("being");
		Business bus = businessDao.get(product.getSellerId());
		System.out.println("end:"+bus);
		if(bus!=null && bus.getCapacity()>bus.getUsed()){
			String mediaId = saveImage(product.getMedia(),rootDir,bus.getUserId()+"/" + bus.getUsed());
	        product.setMediaId(mediaId);
	        bus.setUsed(bus.getUsed() + 1);
	        businessDao.update(bus);
	        productDao.save(product);
	        return product;
		}
        return null;
	}
	public Product updateProduct(String rootDir,Product product) {
		Business bus = businessDao.get(product.getSellerId());
		System.out.println("end:" + bus);
		Product db = productDao.get(product.getProductId());
		if(bus!=null&&db!=null&&db.getSellerId().equals(product.getSellerId())){
			db.setCreditPrice(product.getCreditPrice());
			db.setDescription(product.getDescription());
			db.setName(product.getName());
			db.setProductType(product.getProductType());
			db.setPrice(product.getPrice());
			db.setRetCredit(product.getRetCredit());
			db.setSellerId(product.getSellerId());
			if(product.getMedia()!=null){
		        String filePath = rootDir + "/images/" + db.getMediaId();
		        File file = new File(filePath);
		        try {
		        	product.getMedia().transferTo(file);
		        } catch (Exception e) {
		        	e.printStackTrace();
		        	return null;
		        }
			}
	        productDao.update(db);
	        return db;
		}
        return null;
	}
	public void putNews(String rootDir, News news) {
		if(news.getNewsDate()==null){
			news.setNewsDate(new Date());
		}
		newsDao.save(news);
		String mediaId = saveImage(news.getFile(),rootDir,"news/" + news.getNewsId());
		news.setMediaId(mediaId);
		newsDao.update(news);
	}
	public void removeNews(Long newsId) {
		newsDao.remove(newsDao.get(newsId));
		commentDao.removeByTarget("NewsComment", newsId);
	}
	public Product getProduct(Long productId) {
		return productDao.get(productId);
	}
}
