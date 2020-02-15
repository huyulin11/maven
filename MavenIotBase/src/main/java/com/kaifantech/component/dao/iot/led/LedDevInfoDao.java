package com.kaifantech.component.dao.iot.led;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.led.LedDevInfoBean;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;

@Service
public class LedDevInfoDao extends BaseDao {
	private List<LedDevInfoBean> ledList = null;

	private List<LedDevInfoBean> getLedDevInfoBeanList(String whereSQL) {
		return (List<LedDevInfoBean>) jdbcTemplate().query(
				"SELECT * FROM " + AppTables.LED_DEV_INFO + " where delflag=0 "
						+ (AppTool.isNullStr(whereSQL) ? "" : whereSQL),
				new BeanPropertyRowMapper<LedDevInfoBean>(LedDevInfoBean.class));
	}

	private Integer getCount(String whereSQL) {
		return jdbcTemplate().queryForObject("SELECT count(*) FROM " + AppTables.LED_DEV_INFO + " where delflag=0 "
				+ (AppTool.isNullStr(whereSQL) ? "" : whereSQL), Integer.class);
	}

	public List<LedDevInfoBean> getAllLedDevInfoBean() {
		if (ledList == null) {
			ledList = getLedDevInfoBeanList(null);
		}
		return ledList;
	}

	public LedDevInfoBean getLedDevInfoBean(Integer columnId) {
		if (getAllLedDevInfoBean() != null && getAllLedDevInfoBean().size() > 0) {
			try {
				return getAllLedDevInfoBean().stream().filter((bean) -> columnId.equals(bean.getColumnId())).iterator()
						.next();
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	public Integer getAllLedDevInfoBeanCount(Integer columnId) {
		return getCount(" and columnId= " + columnId);
	}

	public List<LedDevInfoBean> getAllocsBean(Integer columnId) {
		return getLedDevInfoBeanList(" and columnId= " + columnId);
	}

	public int insert(Integer columnId, String ip) {
		return jdbcTemplate().update("insert into  " + AppTables.LED_DEV_INFO + " (  " + i("columnId") + ",  "
				+ i("ip") + ") VALUES  (" + s(columnId) + "," + s(ip) + ")");
	}
}
