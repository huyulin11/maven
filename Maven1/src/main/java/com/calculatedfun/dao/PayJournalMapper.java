package com.calculatedfun.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.PayJournal;
import com.calculatedfun.dto.PayJournalExample;

public interface PayJournalMapper {
    int countByExample(PayJournalExample example);

    int deleteByExample(PayJournalExample example);
    
    int deleteByPrimaryKey(String payid);

    int insert(PayJournal record);

    int insertSelective(PayJournal record);

    List<PayJournal> selectByExample(PayJournalExample example);

    PayJournal selectByPrimaryKey(String payid);

    int updateByExampleSelective(@Param("record") PayJournal record, @Param("example") PayJournalExample example);

    int updateByExample(@Param("record") PayJournal record, @Param("example") PayJournalExample example);

    int updateByPrimaryKeySelective(PayJournal record);

    int updateByPrimaryKey(PayJournal record);
}