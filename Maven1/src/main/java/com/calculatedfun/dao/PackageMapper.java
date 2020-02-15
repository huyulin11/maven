package com.calculatedfun.dao;

import java.util.List;

import com.calculatedfun.bean.PackageInfoBean;
import com.calculatedfun.bean.PackageRelationBean;
import com.calculatedfun.bean.RequestBean;

public interface PackageMapper {
	
	int insertPackage(PackageInfoBean packageInfo);
	
	int updatePackage(PackageInfoBean packageInfo);
	
	List<PackageInfoBean> selectPackage(String packageid);
	
	int insertPackageRelation(PackageRelationBean packageRelation);
	
	int updatePackageRelation(PackageRelationBean packageRelation);
	
	int changePackage(RequestBean request);
	
	int doPackage(RequestBean request);

}
