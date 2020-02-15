package com.kaifantech.component.dao.alloc;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.kaifantech.bean.wms.alloc.AllocInventoryInfoBean;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class AllocInventoryDao extends BaseDao {
	private String FROM_SQL = AppTables.ALLOCATION_INVENTORY_INFO + " i WHERE 1=1 ";

	public List<AllocInventoryInfoBean> getList() {
		return (List<AllocInventoryInfoBean>) jdbcTemplate().query("SELECT i.* FROM " + FROM_SQL,
				new BeanPropertyRowMapper<AllocInventoryInfoBean>(AllocInventoryInfoBean.class));
	}
}
