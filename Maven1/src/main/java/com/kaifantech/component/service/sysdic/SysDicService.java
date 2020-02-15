package com.kaifantech.component.service.sysdic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.calculatedfun.util.AppTool;
import com.kaifantech.bean.dic.SysDic;
import com.kaifantech.bean.dic.SysDicBean;
import com.kaifantech.component.dao.SysDicDao;
import com.kaifantech.entity.SysDicDataFormMap;
import com.kaifantech.init.sys.dao.BaseSqlConstants;
import com.kaifantech.mappings.SysDicDataMapper;

@Service
public class SysDicService {

	@Autowired
	private SysDicDao sysDictionaryInfoDao;

	private List<SysDicBean> sysDictionaryInfoBeanList;

	@Inject
	private SysDicDataMapper mapper;

	public List<SysDic> getSysDictionaries(SysDic sysDictionary) {
		return mapper.getSysDictionaries(sysDictionary);
	}

	public SysDic getSysDictionary(SysDic sysDictionary) {
		List<SysDic> list = getSysDictionaries(sysDictionary);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return new SysDic();
	}

	public Object getDicValue(Object dicType, Object dicKey) {
		if (AppTool.isAnyNull(dicType, dicKey)) {
			return "";
		}
		List<SysDic> list = getSysDictionaries(new SysDic(dicType, dicKey));
		if (list != null && list.size() > 0) {
			return list.get(0).getValue();
		}
		return "";
	}

	public Object getDicKey(Object dicType, Object dicValue) {
		if (AppTool.isAnyNull(dicType, dicValue)) {
			return "";
		}
		SysDic param = new SysDic(dicType);
		param.setValue(dicValue);
		List<SysDic> list = getSysDictionaries(param);
		if (list != null && list.size() > 0) {
			return list.get(0).getKey();
		}
		return "";
	}

	public Object getDicAlias(Object dicType, Object dicKey) {
		List<SysDic> list = getSysDictionaries(new SysDic(dicType, dicKey));
		if (list != null && list.size() > 0) {
			return list.get(0).getAlias();
		}
		return "";
	}

	public List<SysDic> getAllSysDictionaries() {
		return mapper.getAllDictionaries();
	}

	public List<SysDic> getSysDictionaries(String type) {
		return mapper.getSysDictionaries(new SysDic(type, null));
	}

	@SuppressWarnings({ "unchecked" })
	public void doAddEntity(String dictype, SysDicDataFormMap formMap) throws Exception {
		Map<String, SysDicDataFormMap> list = (Map<String, SysDicDataFormMap>) formMap.get(BaseSqlConstants.LIST);
		for (SysDicDataFormMap value : list.values()) {
			String status = value.getStr("_status");
			value.set("dictype", dictype);
			if ("DELETE".equals(status)) {
				mapper.delete(value);
			} else {
				value.setIfNull("sortflag", "0");
				value.set("json", JSONObject.toJSON(value).toString());
				mapper.add(value);
			}
		}
	}

	private List<SysDicBean> getAllSysDicBean() {
		if (sysDictionaryInfoBeanList == null || sysDictionaryInfoBeanList.size() == 0) {
			sysDictionaryInfoBeanList = sysDictionaryInfoDao.getSysDicBeanList(null);
		}
		return sysDictionaryInfoBeanList;
	}

	public List<SysDicBean> getAllSysDicBeanByTypeCode(String typecode) {
		List<SysDicBean> list = new ArrayList<SysDicBean>();
		getAllSysDicBean().stream().filter((s) -> typecode.equals(s.getTypeCode())).forEach(list::add);
		return list;
	}

	public List<SysDicBean> getAllAreaList() {
		return getAllSysDicBeanByTypeCode("WEIWEI_AREA");
	}
}
