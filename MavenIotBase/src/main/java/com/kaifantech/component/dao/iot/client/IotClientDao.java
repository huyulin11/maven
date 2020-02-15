package com.kaifantech.component.dao.iot.client;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.kaifantech.bean.iot.client.IotClientBean;
import com.kaifantech.init.sys.dao.AppSystemDao;
import com.kaifantech.init.sys.dao.AppTables;

@Service
public class IotClientDao extends AppSystemDao {
	private static final String KEY_TABLE = AppTables.IOT_CLIENT_INFO;
	private static final String SELECT_STR = selectFrom(KEY_TABLE);

	public List<IotClientBean> getList() {
		String sql = SELECT_STR + " a  WHERE a.delflag=0  ";
		return jdbcTemplate().query(sql, new BeanPropertyRowMapper<IotClientBean>(IotClientBean.class));
	}

	public IotClientBean getBean(Integer iotClientId) {
		String sql = SELECT_STR + " a  WHERE a.delflag=0 AND id= " + iotClientId;
		List<IotClientBean> fl = jdbcTemplate().query(sql,
				new BeanPropertyRowMapper<IotClientBean>(IotClientBean.class));
		return (fl == null || fl.size() > 1) ? null : fl.get(0);
	}

	public int gotoCharge(Integer iotClientId) {
		return charge(iotClientId, 1);
	}

	public int backFromCharge(Integer iotClientId) {
		return charge(iotClientId, 0);
	}

	private int charge(Integer iotClientId, int oneForChargeAnd0ForLeave) {
		String sql = "update " + KEY_TABLE + set("inCharge= " + oneForChargeAnd0ForLeave) + where("id=" + iotClientId);
		return jdbcTemplate().update(sql);
	}

	public void gotIt(int iotClientId) {
		jdbcTemplate().execute("update " + KEY_TABLE + " set " + " gotIt = " + s(1) + " where " + " id= "
				+ s(iotClientId) + " and gotIt = " + s(0));
	}

	public void changeNextXaxis(int iotClientId, int nextXaxis, int nextLocation) {
		jdbcTemplate().execute("update " + KEY_TABLE + " set " + " nextXaxis = " + s(nextXaxis) + ","
				+ " nextLocation = " + s(nextLocation) + " where " + " id= " + s(iotClientId));
	}

	public void changeCurrentXaxis(int iotClientId, int currentXaxis, int currentLocation) {
		jdbcTemplate().execute("update " + KEY_TABLE + " set " + "inCurrentXaxis=1" + " where " + " id= "
				+ s(iotClientId) + " and currentXaxis=" + currentXaxis + " and inCurrentXaxis!=1");
		jdbcTemplate().execute("update " + KEY_TABLE + " set " + " lastXaxis = currentXaxis" + " where " + " id= "
				+ s(iotClientId) + " and currentXaxis!=" + currentXaxis);
		jdbcTemplate().execute("update " + KEY_TABLE + " set " + " currentXaxis = " + s(currentXaxis) + ","
				+ " currentLocation = " + s(currentLocation) + "," + "inCurrentXaxis=1" + " where " + " id= "
				+ s(iotClientId) + " and currentXaxis!=" + currentXaxis);
	}

	public void leaveCurrentXaxis(int iotClientId) {
		jdbcTemplate().execute("update " + KEY_TABLE + " set " + "inCurrentXaxis=0" + " where " + " id= "
				+ s(iotClientId) + " and inCurrentXaxis!=0");
	}

	public void takeIt(int iotClientId) {
		jdbcTemplate().execute("update " + KEY_TABLE + " set " + " gotIt = " + s(2) + " where " + " id= "
				+ s(iotClientId) + " and gotIt = " + s(1));
	}
}
