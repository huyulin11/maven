package com.kaifantech.init.sys.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.calculatedfun.util.AppSet;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;

public class BaseSqlJoint extends BaseSqlConstants {
	public static String join(Object... objs) {
		return AppTool.join(objs);
	}

	public static String joinSql(Object... objs) {
		return AppTool.join(" ", objs);
	}

	public static boolean isSpecialKey(String key) {
		if (SPECICAL_KEYS.contains(key)) {
			return true;
		}
		return false;
	}

	public static void removeSpecialKey(FormMap formMap) {
		for (String key : SPECICAL_KEYS) {
			formMap.remove(key);
		}
	}

	public static String colsInfo(Object table) {
		if (UdfDbs.DB_TYPE.equals(DbTypes.MYSQL))
			return SHOW_COLUMNS_FROM + table;
		else {
			String[] tableArr = table.toString().trim().split("\\.");
			return "SELECT" // + " 表名 = case when a.colorder=1 then d.name else
							// '' end,"
					// + " 表说明 = case when a.colorder=1 then isnull(f.value,'')
					// else '' end," + " 字段序号 = a.colorder,"
					+ " Field     = a.name,"
					// + " 标识 = case when COLUMNPROPERTY(
					// a.id,a.name,'IsIdentity')=1 then '√'else '' end," + " "
					+ i("KEY")
					+ "       = case when exists(SELECT 1 FROM sysobjects where xtype='PK' and parent_obj=a.id and name in ("
					+ " SELECT name FROM sysindexes WHERE indid in( SELECT indid FROM sysindexkeys WHERE id = a.id AND colid=a.colid))) then 'PRI' else '' end"
					// + " 类型 = b.name," + " 占用字节数 = a.length,"
					// + " 长度 = COLUMNPROPERTY(a.id,a.name,'PRECISION'),"
					// + " 小数位数 =
					// isnull(COLUMNPROPERTY(a.id,a.name,'Scale'),0),"
					// + " 允许空 = case when a.isnullable=1 then '√'else '' end,"
					// + " 默认值 = isnull(e.text,''),"
					// + " 字段说明 = isnull(g.[value],'')"
					+ " FROM " + " syscolumns a" + " left join " + " systypes b" + " on " + " a.xusertype=b.xusertype"
					+ " inner join " + " sysobjects d" + " on "
					+ " a.id=d.id  and d.xtype='U' and  d.name<>'dtproperties'" + " left join " + " syscomments e"
					+ " on " + " a.cdefault=e.id" + " left join " + " sys.extended_properties   g" + " on "
					+ " a.id=G.major_id and a.colid=g.minor_id" + " left join" + " sys.extended_properties f" + " on "
					+ " d.id=f.major_id and f.minor_id=0 " + " where " + " d.name='" + tableArr[1] + "'" + " order by "
					+ " a.id,a.colorder";
		}
	}

	public static String join(String separator, List<?> list) {
		return AppSet.join(separator, list);
	}

	public static String delete(String table) {
		String sql = "delete from " + table + " ";
		return sql;
	}

	public static String deleteLogic(String table) {
		String sql = "update " + table + "  set delflag = 1 ";
		return sql;
	}

	public static String deleteSub(String table) {
		String sql = "update " + table + "  set delflag = 2 ";
		return sql;
	}

	public static String s(Object objs) {
		return str(objs);
	}

	public static String str(Object objs) {
		if (objs == null)
			return "";
		return "'" + objs.toString() + "'";
	}

	public static String idtf(Object objs) {
		if (objs == null)
			return " ";
		return IDTF_L + objs.toString() + IDTF_R;
	}

	public static String i(Object objs) {
		return idtf(objs);
	}

	public static String item(Object alias, Object objs) {
		if (objs == null)
			return " ";
		return (AppTool.isNull(alias) ? "" : (alias + ".")) + i(objs.toString());
	}

	public static String selectFrom(Object... objs) {
		if (objs == null)
			return "";
		return SELECT_FROM + AppTool.join(",", objs);
	}

	public static String replace(Object table) {
		return REPLACE_INTO + table;
	}

	public static String insert(Object table) {
		return INSERT_INTO + table;
	}

	public static String where(Object... objs) {
		if (objs == null)
			return "";
		return join(" " + WHERE + " ", AppTool.join(AND, objs));
	}

	public static String where(List<Object> list) {
		if (AppTool.isNull(list))
			return "";
		Object[] objs = list.toArray();
		return where(objs);
	}

	public static String whereOr(Object... objs) {
		if (objs == null)
			return "";
		return join(" " + WHERE + " ", AppTool.join(OR, objs));
	}

	public static String and(Object... objs) {
		return join("(", AppTool.join(AND, objs), ")");
	}

	public static String and(List<Object> list) {
		if (AppTool.isNull(list))
			return "";
		Object[] objs = list.toArray();
		return and(objs);
	}

	public static String or(Object... objs) {
		return join("(", AppTool.join(OR, objs), ")");
	}

	public static String or(List<Object> list) {
		if (AppTool.isNull(list))
			return "";
		Object[] objs = list.toArray();
		return or(objs);
	}

	public static String in(String field, Object... objs) {
		Object[] objss = objs.clone();
		for (int i = 0; i < objs.length; i++) {
			Object tmp = objss[i];
			objss[i] = field + "=" + (tmp instanceof String && !tmp.toString().startsWith("'") ? s(tmp) : tmp);
		}
		return join("(", AppTool.join(OR, objss), ")");
	}

	public static String in(String field, List<Object> objs) {
		Object[] objss = new Object[objs.size()];
		objs.toArray(objss);
		return in(field, objss);
	}

	public static String notin(String field, Object... objs) {
		Object[] objss = objs.clone();
		for (int i = 0; i < objs.length; i++) {
			Object tmp = objss[i];
			objss[i] = field + "!=" + (tmp instanceof String && !tmp.toString().startsWith("'") ? s(tmp) : tmp);
		}
		return join("(", AppTool.join(AND, objss), ")");
	}

	public static String notin(String field, List<Object> objs) {
		Object[] objss = new Object[objs.size()];
		objs.toArray(objss);
		return notin(field, objss);
	}

	public static String set(Map<?, ?> fields) {
		List<String> sets = new ArrayList<>();
		for (Entry<?, ?> item : fields.entrySet()) {
			sets.add(join(item.getKey(), "=", s(item.getValue())));
		}
		return set(sets);
	}

	public static String set(List<String> sets) {
		String[] objss = new String[sets.size()];
		sets.toArray(objss);
		return set(objss);
	}

	public static String set(String... sets) {
		Object[] objs = sets;
		return SET + AppTool.join(",", objs);
	}

	public static String orderByIgnoreIdtf(Object... objs) {
		if (objs == null)
			return "";
		return " " + join(ORDER_BY, AppTool.join(",", objs), " ");
	}

	public static String orderBy(Object... objs) {
		if (objs == null)
			return "";
		return " " + join(ORDER_BY, joinOrderBy(",", IDTF_L, IDTF_R, objs), " ");
	}

	public static String joinOrderBy(Object separator, Object left, Object right, Object... objs) {
		if (null == objs || "".equals(objs.toString()) || objs.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Object obj : objs) {
			if (AppTool.isNull(obj)) {
				continue;
			}
			String[] cols = obj.toString().trim().split(",");
			for (String col : cols) {
				if (sb.length() != 0) {
					sb.append(separator);
				}
				col = col.trim();
				String prefix = "";
				String suffix = "";
				if (col.indexOf(" ") > 0) {
					String[] ss = col.split(" ");
					col = ss[0];
					suffix = ss[1];
					if (col.indexOf(".") > 0) {
						String[] sss = col.split("\\.");
						if (sss.length >= 2) {
							prefix = sss[0] + ".";
							col = sss[1];
						} else {
							col = sss[0];
						}
					}
				}
				sb.append(prefix);
				sb.append(left);
				sb.append(col);
				sb.append(right);
				sb.append(suffix);
			}
		}
		return sb.toString();
	}

	public static String orderBy(List<Object> list) {
		if (AppTool.isNull(list))
			return "";
		Object[] objs = list.toArray();
		return orderBy(objs);
	}

	public static String limitRows(Integer rows) {
		if (rows == null || rows <= 0)
			return "";
		return UdfDbs.DB_TYPE.equals(DbTypes.MYSQL) ? join(" LIMIT 0,", rows)
				: join(" offset 0 rows fetch next  ", rows, " rows only");
	}

	public static String limitRows(Object offset, Integer rows) {
		if (rows == null || rows <= 0)
			return "";
		return UdfDbs.DB_TYPE.equals(DbTypes.MYSQL) ? join(" LIMIT ", offset, ",", rows)
				: join(" offset ", offset, " rows fetch next  ", rows, " rows only");
	}
}
