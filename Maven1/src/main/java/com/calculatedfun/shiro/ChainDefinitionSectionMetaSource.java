package com.calculatedfun.shiro;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;

import com.kaifantech.entity.ResFormMap;
import com.kaifantech.mappings.AppResMapper;
import com.kaifantech.util.log.AppFileLogger;

public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {
	@Inject
	private AppResMapper resourcesMapper;

	private String filterChainDefinitions = null;

	public Ini.Section getObject() throws Exception {
		AppFileLogger.warning("RESOURCE INIT---资源初始化");
		Ini ini = new Ini();
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		ResFormMap form = new ResFormMap();
		List<ResFormMap> lists = resourcesMapper.findByWhere(form);
		for (HashMap<String, Object> resources : lists) {
			if ("4".equals(resources.get("type") + "")) {
				section.put(resources.get("resUrl") + "", "authc");
			} else if (StringUtils.isNotEmpty(resources.get("resUrl") + "")
					&& StringUtils.isNotEmpty(resources.get("resKey") + "")) {
				String permission = "perms[" + resources.get("resKey") + "]";
				AppFileLogger.warning(permission);
				section.put(resources.get("resUrl") + "", permission);
			}

		}
		section.put("/**", "authc");
		return section;
	}

	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	public Class<?> getObjectType() {
		return this.getClass();
	}

	public boolean isSingleton() {
		return false;
	}
}
