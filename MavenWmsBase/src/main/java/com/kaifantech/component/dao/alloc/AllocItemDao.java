package com.kaifantech.component.dao.alloc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.calculatedfun.util.AppSet;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.wms.alloc.AllocItemInfoBean;
import com.kaifantech.init.sys.dao.AppTables;
import com.kaifantech.init.sys.dao.BaseDao;
import com.kaifantech.init.sys.params.AppCtrlParameters;

@Service
public class AllocItemDao extends BaseDao {
	private static final String KEY_TABLE = AppTables.ALLOCATION_ITEM_INFO;

	private static String FROM_SQL = KEY_TABLE + " i " + " WHERE 1=1 " + " AND i." + i("delflag") + " = 0 ";
	private static String ORDERBY_SQL = " order by i.id ";
	private static String ORDERBY_SQL_SHOW = " order by id ";

	private List<AllocItemInfoBean> getList(String whereSQL, String orderBySQL) {
		return getList(whereSQL, orderBySQL, null);
	}

	private List<AllocItemInfoBean> getList(String whereSQL, String orderBySQL, String limitSQL) {
		String sql = "SELECT i.* FROM " + FROM_SQL + (AppTool.isNullStr(whereSQL) ? "" : whereSQL) + orderBySQL
				+ (AppTool.isNullStr(limitSQL) ? "" : limitSQL);
		return (List<AllocItemInfoBean>) jdbcTemplate().query(sql,
				new BeanPropertyRowMapper<AllocItemInfoBean>(AllocItemInfoBean.class));
	}

	private List<AllocItemInfoBean> getList(String whereSQL) {
		return getList(whereSQL, ORDERBY_SQL);
	}

	private Integer getCount(String whereSQL) {
		return jdbcTemplate().queryForObject(
				"SELECT count(*) FROM " + FROM_SQL + (AppTool.isNullStr(whereSQL) ? "" : whereSQL) + ORDERBY_SQL,
				Integer.class);
	}

	public List<AllocItemInfoBean> getAllocs() {
		return getList(null);
	}

	public AllocItemInfoBean getEmptyOne(Object warehouse, int seq) {
		List<AllocItemInfoBean> list = getList(
				(AND + " i.whid=" + s(warehouse) + " ") + AND + or("i.status=1", "i.status=3"),
				" order by " + "i.status,0+i.num,text ", limitRows(seq, seq + 1));
		return AppSet.first(list);
	}

	public List<AllocItemInfoBean> getAllocsBy(Integer areaId) {
		return getList(" and r.areaId= " + areaId);
	}

	public Integer getAllocsCountBy(Integer areaId) {
		return getCount(" and r.areaId= " + areaId);
	}

	public Integer getAllocsCountBy(Integer areaId, Integer rowId, Integer colId, Integer zId) {
		return getCount(" and r.areaId= " + areaId + " and r.colId= " + colId + " and r.rowId= " + rowId
				+ " and r.zId= " + zId);
	}

	public List<AllocItemInfoBean> getAllocsBy(Integer areaId, Integer colId) {
		return getList(" and r.areaId= " + areaId + " and r.colId= " + colId);
	}

	public List<AllocItemInfoBean> getAllocsByColId(Integer areaId, Integer colId) {
		return getList(" and r.areaId=" + areaId + " and r.colId= " + colId);
	}

	public List<AllocItemInfoBean> getAllocsToShow(Integer areaId) {
		return getList("and r.areaId= " + areaId, ORDERBY_SQL_SHOW);
	}

	public AllocItemInfoBean getAllocationInfoBean(Integer id) {
		List<AllocItemInfoBean> allocationInfoBeanList = getList(" and i.id = " + id);
		if (allocationInfoBeanList == null || allocationInfoBeanList.size() <= 0) {
			return null;
		}
		return allocationInfoBeanList.get(0);
	}

	public AllocItemInfoBean getAllocationInfoBean(String allocItemName) {
		List<AllocItemInfoBean> allocationInfoBeanList = getList(" and i.text = " + s(allocItemName));
		if (allocationInfoBeanList == null || allocationInfoBeanList.size() <= 0 || allocationInfoBeanList.size() > 1) {
			return null;
		}
		return allocationInfoBeanList.get(0);
	}

	public AllocItemInfoBean getAllocationInfoBean(Integer areaId, Integer rowId, Integer colId, Integer zId) {
		List<AllocItemInfoBean> allocationInfoBeanList = getList(" and r.areaId = " + areaId + " and  r.rowId = "
				+ rowId + " and  r.colId = " + colId + " and  r.zId = " + zId);
		if (allocationInfoBeanList == null || allocationInfoBeanList.size() <= 0) {
			return null;
		}
		return allocationInfoBeanList.get(0);
	}

	public int update(AllocItemInfoBean bean, String from, String target) {
		String jsonStr = bean.getJson();
		List<String> sets = Arrays.asList(("" + i("status") + "=" + target),
				("skuId= " + (!AppTool.isNull(bean.getSkuId()) ? bean.getSkuId() : 0)), ("num= " + bean.getNum()),
				(!AppTool.isNull(jsonStr) ? " " + i("json") + "= " + s(jsonStr) : null));
		String sql = "update " + KEY_TABLE + set(sets);
		sql += AppCtrlParameters.isCheckFromStatusWhenUpdateAlloc()
				? where("" + i("status") + "=" + from, " id= " + bean.getId()) : where(" id= " + bean.getId());
		return jdbcTemplate().update(sql);
	}

	public int updateSku(Integer id, Integer skuId) {
		return jdbcTemplate().update("update " + KEY_TABLE + " set skuId= " + skuId + " where id= " + id);
	}

	public int updateNum(Integer id, String from, String target, Integer num) {
		return updateNum(id, from, target, null, num);
	}

	public int updateNum(Integer id, String fromStatus, String targetStatus, Integer skuId, Integer num) {
		return jdbcTemplate().update("update " + KEY_TABLE + " set " + i("status") + "=" + targetStatus + " ,num= "
				+ num + (AppTool.isNull(skuId) ? "" : (" ,skuId= " + skuId)) + " where " + i("status") + "="
				+ fromStatus + " and id= " + id);
	}

	public int updateNum(Integer id, Integer skuId, Integer num) {
		return jdbcTemplate().update("update " + KEY_TABLE + " set num= " + num
				+ (AppTool.isNull(skuId) ? "" : (" ,skuId= " + skuId)) + " where id= " + id);
	}

	public int updateNum(Integer id, Integer num) {
		return updateNum(id, null, num);
	}

	public int updateText(Integer id, String text) {
		return jdbcTemplate()
				.update("update " + KEY_TABLE + " set " + i("text") + "=" + s(text) + " where id= " + id);
	}

	public int updateSite(Integer id, Integer siteid) {
		return jdbcTemplate()
				.update("update " + KEY_TABLE + " set " + i("siteid") + "=" + s(siteid) + " where id= " + id);
	}

	public int updateSite() {
		return jdbcTemplate().update("UPDATE  " + KEY_TABLE + "  t1 " + "JOIN (SELECT t." + i("id")
				+ ", 0+ SUBSTR(t." + i("text") + ",1,2) pai,0+ SUBSTR(t." + i("text") + ",4,2) ceng " + "FROM  "
				+ KEY_TABLE + "  t) t2 " + "ON t1.id=t2.id "
				+ "SET siteid=(CASE WHEN pai=1 THEN 41+ceng*2 ELSE 70+23*(FLOOR((pai-2)/2))+ceng*2 END)");
	}

	public int updateColumnId(Integer id, Integer columnId) {
		return jdbcTemplate()
				.update("update " + KEY_TABLE + " set " + i("columnId") + "=" + columnId + " where id= " + id);
	}

	public int insert(Integer areaId, Integer rowId, Integer colId, Integer zId, Integer columnId, String text,
			Integer environment) {
		return jdbcTemplate().update("insert into  " + KEY_TABLE + " ( " + i("environment") + ", " + i("areaId")
				+ ",  " + i("rowId") + ",  " + i("colId") + ",  " + i("zId") + ", columnId,  " + i("text")
				+ ") VALUES  (" + s(environment) + "," + s(areaId) + "," + s(rowId) + "," + s(colId) + ","
				+ s(zId) + "," + s(columnId) + "," + s(text) + ")");
	}

	public int insert(String text) {
		return jdbcTemplate()
				.update("insert into  " + KEY_TABLE + " (  " + i("text") + ") VALUES  (" + s(text) + ")");
	}

	public void insert(List<Map<String, Object>> list) {
		int j = 1;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> bean = list.get(i);
			sb.append("(" + s(bean.get("name")) + "),");
			if (!(i <= j * 1000 && i < list.size() - 1) && sb.length() > 1) {
				String sql = "insert into " + KEY_TABLE + " (" + i("text") + ") VALUES "
						+ AppTool.trimComma(sb.toString());
				jdbcTemplate().update(sql);
				sb.setLength(0);
				j++;
			}
		}
	}

	public void truncate() {
		jdbcTemplate().update("truncate table " + KEY_TABLE);
	}
}
