package com.kaifantech.component.dao.sku;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.kaifantech.bean.wms.sku.SkuInfoBean;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class SkuInfoDao extends BaseDao {
	public List<SkuInfoBean> getAllList() {
		return (List<SkuInfoBean>) jdbcTemplate().query("SELECT * FROM " + AppTables.SKU_INFO + " where delflag=0 ",
				new BeanPropertyRowMapper<SkuInfoBean>(SkuInfoBean.class));
	}
}
