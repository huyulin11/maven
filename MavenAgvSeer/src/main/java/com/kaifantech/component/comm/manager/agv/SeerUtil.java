package com.kaifantech.component.comm.manager.agv;

import com.calculatedfun.util.AppTool;
import com.kaifantech.util.hex.AppByteUtil;

public class SeerUtil {
	public static String cmd(int cmd) {
		return cmd(cmd, null);
	}

	public static String cmd(String cmd) {
		return PREFIX_WHEN_SEND_TASK + cmd + TASK_NAME_SUFFIX;
	}

	public static String cmd(int cmd, Object json) {
		return PREFIX_WHEN_SEND_TASK
				+ (AppTool.isNull(json) ? "00000000" : AppByteUtil.intToHex8(json.toString().length())) + cmd
				+ TASK_NAME_SUFFIX + (AppTool.isNull(json) ? "" : AppByteUtil.strToHex(json.toString()));
	}

	public static final String PREFIX_WHEN_SEND_TASK = "5A010001";
	public static final String TASK_NAME_SUFFIX = "000000";

	public static final int ROBOT_STATUS_INFO_REQ = 1000;// 查询机器人信息
	public static final int ROBOT_STATUS_RUN_REQ = 1002;// 查询机器人的运行状态信息(如运行时间、里程等)
	public static final int ROBOT_STATUS_LOC_REQ = 1004;// 查询机器人位置
	public static final int ROBOT_STATUS_SPEED_REQ = 1005;// 查询机器人速度
	public static final int ROBOT_STATUS_BLOCK_REQ = 1006;// 查询机器人被阻挡状态
	public static final int ROBOT_STATUS_BATTERY_REQ = 1007;// 查询机器人电池状态
	public static final int ROBOT_STATUS_BRAKE_REQ = 1008;// 查询机器人抱闸状态
	public static final int ROBOT_STATUS_LASER_REQ = 1009;// 查询机器人激光点云数据
	public static final int ROBOT_STATUS_PATH_REQ = 1010;// 查询机器人路径数据
	public static final int ROBOT_STATUS_AREA_REQ = 1011;// 查询机器人当前所在区域
	public static final int ROBOT_STATUS_EMERGENCY_REQ = 1012;// 查询机器人急停状态
	public static final int ROBOT_STATUS_IO_REQ = 1013;// 查询机器人 I/O 数据
	public static final int ROBOT_STATUS_IMU_REQ = 1014;// 查询机器人 IMU 数据
	public static final int ROBOT_STATUS_RFID_REQ = 1015;// 查询 RFID 数据
	public static final int ROBOT_STATUS_ULTRASONIC_REQ = 1016;// 查询机器人的超声传感器数据
	public static final int ROBOT_STATUS_PGV_REQ = 1017;// 查询二维码数据(PGV)
	public static final int ROBOT_STATUS_ENCODER_REQ = 1018;// 查询编码器脉冲值
	public static final int ROBOT_STATUS_TASK_REQ = 1020;// 查询机器人导航状态, 导航站点,
															// 导航相关路径等
	public static final int ROBOT_STATUS_RELOC_REQ = 1021;// 查询机器人重定位状态
	public static final int ROBOT_STATUS_LOADMAP_REQ = 1022;// 查询机器人地图载入状态
	public static final int ROBOT_STATUS_SLAM_REQ = 1025;// 查询机器人扫图状态
	public static final int ROBOT_STATUS_JACK_REQ = 1027;// 查询顶﻿升机构状态
	public static final int ROBOT_STATUS_FORK_REQ = 1028;// 查询货叉(叉车)状态
	public static final int ROBOT_STATUS_ROLLER_REQ = 1029;// 查询辊筒(皮带)状态
	public static final int ROBOT_STATUS_DISPATCH_REQ = 1030;// 查询机器人当前的调度状态
	public static final int ROBOT_STATUS_HOOK_REQ = 1031;// 查询牵引机构状态
	public static final int ROBOT_STATUS_ALARM_REQ = 1050;// 查询机器人告警状态
	public static final int ROBOT_STATUS_ALL1_REQ = 1100;// 查询批量数据1
	public static final int ROBOT_STATUS_ALL2_REQ = 1101;// 查询批量数据2
	public static final int ROBOT_STATUS_ALL3_REQ = 1102;// 查询批量数据3
	public static final int ROBOT_STATUS_MAP_REQ = 1300;// 查询机器人载入的地图以及储存的地图
	public static final int ROBOT_STATUS_STATION = 1301;// 查询机器人当前载入地图中的站点信息
	public static final int ROBOT_STATUS_PARAMS_REQ = 1400;// 查询机器人参数
	public static final int ROBOT_STATUS_INFO_RES = 11000;// 对 1000 请求的响应
	public static final int ROBOT_STATUS_RUN_RES = 11002;// 对 1002 请求的响应
	public static final int ROBOT_STATUS_LOC_RES = 11004;// 对 1004 请求的响应
	public static final int ROBOT_STATUS_SPEED_RES = 11005;// 对 1005 请求的响应
	public static final int ROBOT_STATUS_BLOCK_RES = 11006;// 对 1006 请求的响应
	public static final int ROBOT_STATUS_BATTERY_RES = 11007;// 对 1007 请求的响应
	public static final int ROBOT_STATUS_BRAKE_RES = 11008;// 对 1008 请求的响应
	public static final int ROBOT_STATUS_LASER_RES = 11009;// 对 1009 请求的响应
	public static final int ROBOT_STATUS_PATH_RES = 11010;// 对 1010 请求的响应
	public static final int ROBOT_STATUS_AREA_RES = 11011;// 对 1011 请求的响应
	public static final int ROBOT_STATUS_EMERGENCY_RES = 11012;// 对 1012 请求的响应
	public static final int ROBOT_STATUS_IO_RES = 11013;// 对 1013 请求的响应
	public static final int ROBOT_STATUS_IMU_RES = 11014;// 对 1014 请求的响应
	public static final int ROBOT_STATUS_RFID_RES = 11015;// 对 1015 请求的响应
	public static final int ROBOT_STATUS_ULTRASONIC_RES = 11016;// 对 1016 请求的响应
	public static final int ROBOT_STATUS_PGV_RES = 11017;// 对 1017 请求的响应
	public static final int ROBOT_STATUS_ENCODER_RES = 11018;// 对 1018 请求的响应
	public static final int ROBOT_STATUS_TASK_RES = 11020;// 对 1020 请求的响应
	public static final int ROBOT_STATUS_RELOC_RES = 11021;// 对 1021 请求的响应
	public static final int ROBOT_STATUS_LOADMAP_RES = 11022;// 对 1022 请求的响应
	public static final int ROBOT_STATUS_SLAM_RES = 11025;// 对 1025 请求的响应
	public static final int ROBOT_STATUS_JACK_RES = 11027;// 对 1027 请求的响应
	public static final int ROBOT_STATUS_FORK_RES = 11028;// 对 1028 请求的响应
	public static final int ROBOT_STATUS_ROLLER_RES = 11029;// 对 1029 请求的响应
	public static final int ROBOT_STATUS_DISPATCH_RES = 11030;// 对 1030 请求的响应
	public static final int ROBOT_STATUS_HOOK_RES = 11031;// 对 1031 请求的响应
	public static final int ROBOT_STATUS_ALARM_RES = 11050;// 对 1050 请求的响应
	public static final int ROBOT_STATUS_ALL1_RES = 11100;// 对 1100 请求的响应
	public static final int ROBOT_STATUS_ALL2_RES = 11101;// 对 1101 请求的响应
	public static final int ROBOT_STATUS_ALL3_RES = 11102;// 对 1102 请求的响应
	public static final int ROBOT_STATUS_MAP_RES = 11300;// 对 1300 请求的响应
	public static final int ROBOT_STATUS_STATION_RES = 11301;// 对 1301 请求的响应
	public static final int ROBOT_STATUS_PARAMS_RES = 11400;// 对 1400 请求的响应
	public static final int ROBOT_CONTROL_STOP_REQ = 2000;// 停止开环运动
	public static final int ROBOT_CONTROL_RELOC_REQ = 2002;// 重定位
	public static final int ROBOT_CONTROL_COMFIRMLOC_REQ = 2003;// 确认定位正确
	public static final int ROBOT_CONTROL_CANCELRELOC_REQ = 2004;// 取消重定位
	public static final int ROBOT_CONTROL_MOTION_REQ = 2010;// 开环运动
	public static final int ROBOT_CONTROL_LOADMAP_REQ = 2022;// 切换载入的地图
	public static final int ROBOT_CONTROL_STOP_RES = 12000;// 对 2000 请求的响应
	public static final int ROBOT_CONTROL_RELOC_RES = 12002;// 对 2002 请求的响应
	public static final int ROBOT_CONTROL_COMFIRMLOC_RES = 12003;// 对 2003 请求的响应
	public static final int ROBOT_CONTROL_CANCELRELOC_RES = 12004;// 对 2004
																	// 请求的响应
	public static final int ROBOT_CONTROL_MOTION_RES = 12010;// 对 2010 请求的响应
	public static final int ROBOT_CONTROL_LOADMAP_RES = 12022;// 对 2022 请求的响应
	public static final int ROBOT_TASK_PAUSE_REQ = 3001;// 暂停当前导航
	public static final int ROBOT_TASK_RESUME_REQ = 3002;// 继续当前导航
	public static final int ROBOT_TASK_CANCEL_REQ = 3003;// 取消当前导航
	public static final int ROBOT_TASK_GOTARGET_REQ = 3051;// 路径导航(根据地图上站点及路径进行导航)
	public static final int ROBOT_TASK_TARGET_PATH_REQ = 3053;// 获取路径导航的路径
	public static final int ROBOT_TASK_TRANSLATE_REQ = 3055;// 平动，以固定速度直线运动固定距离
	public static final int ROBOT_TASK_TURN_REQ = 3056;// 转动，以固定角速度旋转固定角度
	public static final int ROBOT_TASK_GOINTO_SHELF_REQ = 3063;// 顶升钻货架
	public static final int ROBOT_TASK_GOTARGETLIST_REQ = 3066;// 指定路径导航(根据路径导航序列进行导航)
	public static final int ROBOT_TASK_CLEARTARGETLIST_REQ = 3067;// 清除导航路径
	public static final int ROBOT_TASK_TARGET_FOLLOW_REQ = 3070;// 目标跟随
	public static final int ROBOT_TASK_UWB_FOLLOW_REQ = 3071;// UWB 跟随
	public static final int ROBOT_TASK_PAUSE_RES = 13001;// 对 3001 请求的响应
	public static final int ROBOT_TASK_RESUME_RES = 13002;// 对 3002 请求的响应
	public static final int ROBOT_TASK_CANCEL_RES = 13003;// 对 3003 请求的响应
	public static final int ROBOT_TASK_GOTARGET_RES = 13051;// 对 3051 请求的响应
	public static final int ROBOT_TASK_TARGET_PATH_RES = 13053;// 对 3053 请求的响应
	public static final int ROBOT_TASK_TRANSLATE_RES = 13055;// 对 3055 请求的响应
	public static final int ROBOT_TASK_TURN_RES = 13056;// 对 3056 请求的响应
	public static final int ROBOT_TASK_GOINTO_SHELF_RES = 13063;// 对 3063 请求的响应
	public static final int ROBOT_TASK_GOTARGETLIST_RES = 13066;// 对 3066 请求的响应
	public static final int ROBOT_TASK_CLEARTARGETLIST_RES = 13067;// 对 3067
																	// 请求的响应
	public static final int ROBOT_TASK_TARGET_FOLLOW_RES = 13070;// 对 3070 请求的响应
	public static final int ROBOT_TASK_UWB_FOLLOW_RES = 13071;// 对 3071 请求的响应
	public static final int ROBOT_CONFIG_REQUIRE_REQ = 4001;// 回收控制权
	public static final int ROBOT_CONFIG_RELEASE_REQ = 4002;// 释放控制权
	public static final int ROBOT_CONFIG_SRC_REQUIRE_REQ = 4003;// SRC 获得控制
	public static final int ROBOT_CONFIG_SRC_RELEASE_REQ = 4004;// SRC 释放控制
	public static final int ROBOT_CONFIG_UPLOADMAP_REQ = 4010;// 上传地图到机器人
	public static final int ROBOT_CONFIG_DOWNLOADMAP_REQ = 4011;// 从机器人下载地图
	public static final int ROBOT_CONFIG_REMOVEMAP_REQ = 4012;// 删除机器人上的地图
	public static final int ROBOT_CONFIG_ULTRASONIC_REQ = 4130;// 配置超声
	public static final int ROBOT_CONFIG_DI_REQ = 4140;// 配置 DI
	public static final int ROBOT_CONFIG_ADDOBSTACLE_REQ = 4350;// 插入动态障碍物(机器人坐标系)
	public static final int ROBOT_CONFIG_ADDGOBSTACLE_REQ = 4351;// 插入动态障碍物(世界坐标系)
	public static final int ROBOT_CONFIG_REMOVEOBSTACLE_REQ = 4352;// 移除动态障碍物
	public static final int ROBOT_CONFIG_CLEAR_GOODSSHAPE_REQ = 4356;// 清除货架描述文件
	public static final int ROBOT_CONFIG_SET_SHELFSHAPE_REQ = 4357;// 设置货架描述文件
	public static final int ROBOT_CONFIG_SETERROR_REQ = 4800;// 设置第三方 Error
	public static final int ROBOT_CONFIG_CLEARERROR_REQ = 4801;// 清除第三方 Error
	public static final int ROBOT_CONFIG_SETWARNING_REQ = 4802;// 设置第三方 Warning
	public static final int ROBOT_CONFIG_CLEARWARNING_REQ = 4803;// 清除第三方
																	// Warning
	public static final int ROBOT_CONFIG_REQUIRE_RES = 14001;// 对 4001 请求的响应
	public static final int ROBOT_CONFIG_RELEASE_RES = 14002;// 对 4002 请求的响应
	public static final int ROBOT_CONFIG_SRC_REQUIRE_RES = 14003;// 对 4003 请求的响应
	public static final int ROBOT_CONFIG_SRC_RELEASE_RES = 14004;// 对 4004 请求的响应
	public static final int ROBOT_CONFIG_UPLOADMAP_RES = 14010;// 对 4010 请求的响应
	public static final int ROBOT_CONFIG_DOWNLOADMAP_RES = 14011;// 对 4011 请求的响应
	public static final int ROBOT_CONFIG_REMOVEMAP_RES = 14012;// 对 4012 请求的响应
	public static final int ROBOT_CONFIG_ULTRASONIC_RES = 14130;// 对 4130 请求的响应
	public static final int ROBOT_CONFIG_DI_RES = 14140;// 对 4140 请求的响应
	public static final int ROBOT_CONFIG_ADDOBSTACLE_RES = 14350;// 对 4350 请求的响应
	public static final int ROBOT_CONFIG_ADDGOBSTACLE_RES = 14351;// 对 4351
																	// 请求的响应
	public static final int ROBOT_CONFIG_REMOVEOBSTACLE_RES = 14352;// 对 4352
																	// 请求的响应
	public static final int ROBOT_CONFIG_CLEAR_GOODSSHAPE_RES = 14356;// 对 4356
																		// 请求的响应
	public static final int ROBOT_CONFIG_SET_SHELFSHAPE_RES = 14357;// 对 4357
																	// 请求的响应
	public static final int ROBOT_CONFIG_SETERROR_RES = 14800;// 对 4800 请求的响应
	public static final int ROBOT_CONFIG_CLEARERROR_RES = 14801;// 对 4801 请求的响应
	public static final int ROBOT_CONFIG_SETWARNING_RES = 14802;// 对 4802 请求的响应
	public static final int ROBOT_CONFIG_CLEARWARNING_RES = 14803;// 对 4803
																	// 请求的响应
	public static final int ROBOT_OTHER_PLAY_AUDIO_REQ = 6000;// 播放音频
	public static final int ROBOT_OTHER_SETDO_REQ = 6001;// 设置 DO
	public static final int ROBOT_OTHER_SETDOS_REQ = 6002;// 批量设置 DO
	public static final int ROBOT_OTHER_SETRELAY_REQ = 6003;// 设置继电器
	public static final int ROBOT_OTHER_SOFTEMC_REQ = 6004;// 软急停
	public static final int ROBOT_OTHER_SETCHARGINGRELAY_REQ = 6005;// 设置充电继电器
	public static final int ROBOT_OTHER_PAUSE_AUDIO_REQ = 6010;// 暂停播放音频
	public static final int ROBOT_OTHER_RESUME_AUDIO_REQ = 6011;// 继续播放音频
	public static final int ROBOT_OTHER_STOP_AUDIO_REQ = 6012;// 停止播放音频
	public static final int ROBOT_OTHER_SETVDI_REQ = 6020;// 设置虚拟 DI
	public static final int ROBOT_OTHER_AUDIO_LIST_REQ = 6033;// 获取音频文件列表
	public static final int ROBOT_OTHER_SET_FORK_HEIGHT_REQ = 6040;// 设置货叉高度
	public static final int ROBOT_OTHER_STOP_FORK_REQ = 6041;// 停止货叉
	public static final int ROBOT_OTHER_SET_FORK_FORWARD_REQ = 6042;// 设置货叉前移后退
	public static final int ROBOT_OTHER_WRITE_PERIPHERAL_DATA_REQ = 6049;// 写入外设用户自定义数据
	public static final int ROBOT_OTHER_ROLLER_FRONT_ROLL_REQ = 6051;// 辊筒(皮带)向前滚动
	public static final int ROBOT_OTHER_ROLLER_BACK_ROLL_REQ = 6052;// 辊筒(皮带)向后滚动
	public static final int ROBOT_OTHER_ROLLER_LEFT_ROLL_REQ = 6053;// 辊筒(皮带)向左滚动
	public static final int ROBOT_OTHER_ROLLER_RIGHT_ROLL_REQ = 6054;// 辊筒(皮带)向右滚动
	public static final int ROBOT_OTHER_ROLLER_FRONT_LOAD_REQ = 6055;// 辊筒(皮带)前上料
	public static final int ROBOT_OTHER_ROLLER_FRONT_UNLOAD_REQ = 6056;// 辊筒(皮带)前下料
	public static final int ROBOT_OTHER_ROLLER_FRONT_PRE_LOAD_REQ = 6057;// 辊筒(皮带)前预上料
	public static final int ROBOT_OTHER_ROLLER_BACK_LOAD_REQ = 6058;// 辊筒(皮带)后上料
	public static final int ROBOT_OTHER_ROLLER_BACK_UNLOAD_REQ = 6059;// 辊筒(皮带)后下料
	public static final int ROBOT_OTHER_ROLLER_BACK_PRE_LOAD_REQ = 6060;// 辊筒(皮带)后预上料
	public static final int ROBOT_OTHER_ROLLER_LEFT_LOAD_REQ = 6061;// 辊筒(皮带)左上料
	public static final int ROBOT_OTHER_ROLLER_LEFT_UNLOAD_REQ = 6062;// 辊筒(皮带)左下料
	public static final int ROBOT_OTHER_ROLLER_RIGHT_LOAD_REQ = 6063;// 辊筒(皮带)右上料
	public static final int ROBOT_OTHER_ROLLER_RIGHT_UNLOAD_REQ = 6064;// 辊筒(皮带)右下料
	public static final int ROBOT_OTHER_ROLLER_LEFT_PRE_LOAD_REQ = 6065;// 辊筒(皮带)左预上料
	public static final int ROBOT_OTHER_ROLLER_RIGHT_PRE_LOAD_REQ = 6066;// 辊筒(皮带)右预上料
	public static final int ROBOT_OTHER_ROLLER_STOP_REQ = 6067;// 辊筒(皮带)停止
	public static final int ROBOT_OTHER_ROLLER_LEFT_RIGHT_INVERSE_REQ = 6068;// 辊筒(皮带)左右相反
	public static final int ROBOT_OTHER_ROLLER_FRONT_BACK_INVERSE_REQ = 6069;// 辊筒(皮带)前后相反
	public static final int ROBOT_OTHER_JACK_LOAD_REQ = 6070;// 顶升机构上升
	public static final int ROBOT_OTHER_JACK_UNLOAD_REQ = 6071;// 顶升机构下降
	public static final int ROBOT_OTHER_JACK_STOP_REQ = 6072;// 顶升机构停止
	public static final int ROBOT_OTHER_JACK_SET_HEIGHT_REQ = 6073;// 顶升定高
	public static final int ROBOT_OTHER_HOOK_PRE_CLAMP_REQ = 6080;// 牵引预夹取
	public static final int ROBOT_OTHER_HOOK_ADJUST_TO_RECOGNIZED_REQ = 6081;// 牵引调整至识别位置
	public static final int ROBOT_OTHER_HOOK_CLAMP_REQ = 6082;// 牵引夹取
	public static final int ROBOT_OTHER_HOOK_DETACH_REQ = 6083;// 牵引脱离
	public static final int ROBOT_OTHER_HOOK_RESET_REQ = 6084;// 牵引复位
	public static final int ROBOT_OTHER_HOOK_STOP_REQ = 6085;// 牵引停止
	public static final int ROBOT_OTHER_SLAM_REQ = 6100;// 开始扫描地图
	public static final int ROBOT_OTHER_ENDSLAM_REQ = 6101;// 停止扫描地图
	public static final int ROBOT_OTHER_SET_MOTORS_REQ = 6200;// 电机调试
	public static final int ROBOT_OTHER_PLAY_AUDIO_RES = 16000;// 对 6000 请求的响应
	public static final int ROBOT_OTHER_SETDO_RES = 16001;// 对 6001 请求的响应
	public static final int ROBOT_OTHER_SETDOS_RES = 16002;// 对 6002 请求的响应
	public static final int ROBOT_OTHER_SETRELAY_RES = 16003;// 对 6003 请求的响应
	public static final int ROBOT_OTHER_SOFTEMC_RES = 16004;// 对 6004 请求的响应
	public static final int ROBOT_OTHER_SETCHARGINGRELAY_RES = 16005;// 对 6005
																		// 请求的响应
	public static final int ROBOT_OTHER_PAUSE_AUDIO_RES = 16010;// 对 6010 请求的响应
	public static final int ROBOT_OTHER_RESUME_AUDIO_RES = 16011;// 对 6011 请求的响应
	public static final int ROBOT_OTHER_STOP_AUDIO_RES = 16012;// 对 6012 请求的响应
	public static final int ROBOT_OTHER_SETVDI_RES = 16020;// 对 6020 请求的响应
	public static final int ROBOT_OTHER_AUDIO_LIST_RES = 16033;// 对 6033 请求的响应
	public static final int ROBOT_OTHER_SET_FORK_HEIGHT_RES = 16040;// 对 6040
																	// 请求的响应
	public static final int ROBOT_OTHER_STOP_FORK_RES = 16041;// 对 6041 请求的响应
	public static final int ROBOT_OTHER_SET_FORK_FORWARD_RES = 16042;// 对 16042
																		// 请求的响应
	public static final int ROBOT_OTHER_WRITE_PERIPHERAL_DATA_RES = 16049;// 对
																			// 6049
																			// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_FRONT_ROLL_RES = 16051;// 对 6051
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_BACK_ROLL_RES = 16052;// 对 6051
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_LEFT_ROLL_RES = 16053;// 对 6053
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_RIGHT_ROLL_RES = 16054;// 对 6054
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_FRONT_LOAD_RES = 16055;// 对 6055
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_FRONT_UNLOAD_RES = 16056;// 对
																		// 6056
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_FRONT_PRE_LOAD_RES = 16057;// 对
																			// 6057
																			// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_BACK_LOAD_RES = 16058;// 对 6058
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_BACK_UNLOAD_RES = 16059;// 对 6059
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_BACK_PRE_LOAD_RES = 16060;// 对
																			// 6060
																			// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_LEFT_LOAD_RES = 16061;// 对 6061
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_LEFT_UNLOAD_RES = 16062;// 对 6062
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_RIGHT_LOAD_RES = 16063;// 对 6063
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_RIGHT_UNLOAD_RES = 16064;// 对
																		// 6064
																		// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_LEFT_PRE_LOAD_RES = 16065;// 对
																			// 6065
																			// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_RIGHT_PRE_LOAD_RES = 16066;// 对
																			// 6066
																			// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_STOP_RES = 16067;// 对 6067 请求的响应
	public static final int ROBOT_OTHER_ROLLER_LEFT_RIGHT_INVERSE_RES = 16068;// 对
																				// 6068
																				// 请求的响应
	public static final int ROBOT_OTHER_ROLLER_FRONT_BACK_INVERSE_RES = 16069;// 对
																				// 6069
																				// 请求的响应
	public static final int ROBOT_OTHER_JACK_LOAD_RES = 16070;// 对 6070 请求的响应
	public static final int ROBOT_OTHER_JACK_UNLOAD_RES = 16071;// 对 6071 请求的响应
	public static final int ROBOT_OTHER_JACK_STOP_RES = 16072;// 对 6072 请求的响应
	public static final int ROBOT_OTHER_JACK_SET_HEIGHT_RES = 16073;// 对 6073
																	// 请求的响应
	public static final int ROBOT_OTHER_HOOK_PRE_CLAMP_RES = 16080;// 对 6080
																	// 请求的响应
	public static final int ROBOT_OTHER_HOOK_ADJUST_TO_RECOGNIZED_RES = 16081;// 对
																				// 6081
																				// 请求的响应
	public static final int ROBOT_OTHER_HOOK_CLAMP_RES = 16082;// 对 6082 请求的响应
	public static final int ROBOT_OTHER_HOOK_DETACH_RES = 16083;// 对 6083 请求的响应
	public static final int ROBOT_OTHER_HOOK_RESET_RES = 16084;// 对 6084 请求的响应
	public static final int ROBOT_OTHER_HOOK_STOP_RES = 16085;// 对 6085 请求的响应
	public static final int ROBOT_OTHER_SLAM_RES = 16100;// 对 6100 请求的响应
	public static final int ROBOT_OTHER_ENDSLAM_RES = 16101;// 对 6101 请求的响应
	public static final int ROBOT_OTHER_SET_MOTORS_RES = 16200;// 对 6200 请求的响应
}
