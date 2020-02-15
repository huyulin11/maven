package com.kaifantech.component.controller.base;

import com.calculatedfun.util.FormMap;
import com.kaifantech.mappings.AppBaseMapper;

public interface IBaseGetter<TT extends FormMap> {
	AppBaseMapper<TT> getMapper();
}