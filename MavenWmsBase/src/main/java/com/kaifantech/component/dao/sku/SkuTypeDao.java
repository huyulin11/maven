package com.kaifantech.component.dao.sku;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.kaifantech.bean.wms.sku.SkuTypeBean;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class SkuTypeDao extends BaseDao {
	public List<SkuTypeBean> getAllList() {
		return (List<SkuTypeBean>) jdbcTemplate().query("SELECT * FROM " + AppTables.SKU_TYPE + " where delflag=0 ",
				new BeanPropertyRowMapper<SkuTypeBean>(SkuTypeBean.class));
	}
}
