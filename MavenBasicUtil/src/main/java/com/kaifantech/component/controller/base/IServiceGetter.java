package com.kaifantech.component.controller.base;

import com.kaifantech.mappings.AppBaseMapper;

public interface IServiceGetter {
	abstract AppBaseMapper<?> getMapper();

}