package com.kaifantech.mappings;

import com.kaifantech.bean.de.AllRequestBean;

public interface DeDataMapper extends AppBaseMapper<Object> {
	public int addBean(AllRequestBean bean);
}
