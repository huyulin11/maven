package com.calculatedfun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.calculatedfun.bean.WalletQueryBuilder;
import com.calculatedfun.bean.rechargerecord;
import com.calculatedfun.dto.WalletJournal;

public interface WalletJournalMapper {

	int insertWalletJournal(WalletJournal walletJournal);

	/** 通过订单号检查订单是否处理 */
	List<WalletJournal> checkWallet_journal(@Param("id") String id);

	WalletJournal selectWallet_journal(@Param("id") String id,
			@Param("userid") String userid);

	int updateWallet_journal(@Param("id") String id,
			@Param("userid") String userid);

	List<rechargerecord> selectWalletJournal(@Param("userid") String userid);

	List<WalletJournal> getWalletJournal(WalletQueryBuilder wqb);
}