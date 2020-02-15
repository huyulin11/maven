package com.calculatedfun.dao;

import java.util.List;

import com.calculatedfun.dto.RequestMainOpLogBean;

public interface RequestMainOpLogMapper {

	int insert(RequestMainOpLogBean requestMainOpLog);

	List<RequestMainOpLogBean> select(RequestMainOpLogBean requestMainOpLog);
}