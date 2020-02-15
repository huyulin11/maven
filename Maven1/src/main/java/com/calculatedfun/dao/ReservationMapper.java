package com.calculatedfun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.calculatedfun.dto.Reservation;
import com.calculatedfun.dto.ReservationRecord;
import com.calculatedfun.dto.ReservationType;

public interface ReservationMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Reservation record);

	int insertReservationRecord(ReservationRecord reservationRecord);

	Reservation selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Reservation record);

	int updateByPrimaryKey(Reservation record);

	List<Reservation> getReservationByUserid(String userid);

	ReservationRecord selectReservationRecordByRequestcode(Integer requestcode);

	int deleteReservationRecordByRequestcode(Integer requestcode);

	List<ReservationType> getReservationTypeByUserid(
			@Param("userid") String userid, @Param("opType") String opType,
			@Param("requestcode") String requestcode,
			@Param("entitytype") String entitytype);

	/** 判断电子券是否存在 */
	int checkCouponIsExist(
			@Param("reservationtypeid") String reservationtypeid,
			@Param("couponid") String couponid);

	/** 判断电子券是否使用 */
	int checkCouponHasUesd(
			@Param("reservationtypeid") String reservationtypeid,
			@Param("couponid") String couponid,
			@Param("requestcode") Integer requestcode);

	/** 获得活动的使用次数 */
	int getReservationTypeUesdTimes(
			@Param("reservationtypeid") String reservationtypeid,
			@Param("couponid") String couponid, @Param("userid") String userid,
			@Param("requestcode") Integer requestcode);

}