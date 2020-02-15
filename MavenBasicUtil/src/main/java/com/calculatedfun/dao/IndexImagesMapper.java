package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.IndexImages;
import com.calculatedfun.dto.IndexImagesExample;

public interface IndexImagesMapper {
	int countByExample(@Param("imagestype") String example);

	int deleteByExample(IndexImagesExample example);

	int deleteByPrimaryKey(String id);

	int insert(IndexImages record);

	int insertSelective(IndexImages record);

	List<IndexImages> selectByExample(@Param("flag") String flag);

	List<IndexImages> selectByType(@Param("imagestype") String imagestype);

	List<IndexImages> selectByid(@Param("id") String id);

	IndexImages selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") IndexImages record,
			@Param("example") IndexImagesExample example);

	int updateByExample(@Param("record") IndexImages record,
			@Param("example") IndexImagesExample example);

	int updateByPrimaryKeySelective(IndexImages record);

	int updateByPrimaryKey(IndexImages record);
}