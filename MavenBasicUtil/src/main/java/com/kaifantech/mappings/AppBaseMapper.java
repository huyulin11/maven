package com.kaifantech.mappings;

import java.util.List;

public interface AppBaseMapper<T> {
	public List<T> findByPage(T formMap);

	public List<T> findByWhere(T formMap);

	public List<T> findByNames(T formMap);

	public List<T> findByAttribute(String key, String value, Class<T> clazz);

	public T findOne(T formMap);

	public T findbyFrist(String key, String value, Class<?> clazz);

	public void addEntity(Object formMap) throws Exception;

	public void editEntity(Object formMap) throws Exception;

	public int updateStatus(Object formMap) throws Exception;

	public void batchSave(List<?> formMap) throws Exception;

	public void batchInsert(List<?> formMap) throws Exception;

	public int deleteByNames(Object formMap) throws Exception;

	public int deleteByLogic(Object formMap) throws Exception;

	public int deleteBySub(Object formMap) throws Exception;

	public int deleteBySure(Object formMap) throws Exception;

	public void deleteByAttribute(String key, String value, Class<?> clazz) throws Exception;

	/** 清空表操作，慎用 */
	public void truncate(T formMap);
}
