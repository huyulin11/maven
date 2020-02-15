package com.kaifantech.bean.msg.agv;

import java.util.List;

public class SeerAgvMsgAll1Bean {
	private Double x;// 机器人的 x 坐标, 单位 m
	private Double y;// 机器人的 y 坐标, 单位 m
	private Double angle;// 机器人的 angle 坐标, 单位 rad
	private Double confidence;// 机器人的定位置信度, 范围 [0,1]
	private String current_station;// 机器人当前所在站点的
									// ID（该判断比较严格，机器人必须很靠近某一个站点(<50cm)，否则为空字符，即不处于任何站点）
	private String last_station;// 机器人上一个所在站点的 ID
	private Double vx;// 机器人在机器人坐标系的 x 方向速度, 单位 m/s
	private Double vy;// 机器人在机器人坐标系的 y 方向速度, 单位 m/s
	private Double w;// 机器人在机器人坐标系的角速度, 单位 rad/s
	private Double steer;// 单舵轮机器人当前的舵轮角度 rad
	private Double spin;// 托盘机器人的托盘角度 rad
	private Double r_vx;// 机器人在机器人坐标系的 x 方向接收到的速度, 单位 m/s
	private Double r_vy;// 机器人在机器人坐标系的 y 方向收到的速度, 单位 m/s
	private Double r_w;// 机器人在机器人坐标系的收到的角速度(即顺时针转为负, 逆时针转为正), 单位 rad/s
	private Double r_steer;// 单舵轮机器人收到的舵轮角度 rad
	private Double r_spin;// 托盘机器人的收到托盘转动速度 rad/s
	private boolean blocked;// 机器人是否被阻挡
	private Integer block_reason;// 被阻挡的原因, 0 = Ultrasonic (超声), 1 = Laser (激光),
									// 2 = Fallingdown (防跌落传感器), 3 = Collision
									// (碰撞传感器), 4 = Infrared (红外传感器), 5 =
									// Lock（锁车开关），6 = 动态障碍物， 7 = 虚拟激光点，8 = 3D 相机
	private Double block_x;// 最近障碍物位置的 x 坐标, 单位 m
	private Double block_y;// 最近障碍物位置的 y 坐标, 单位 m
	private Integer block_ultrasonic_id;// 发生阻挡的超声 id 号, 仅当 block_reason = 0
										// 时有意义
	private Integer block_di;// 发生阻挡的 DI 的 id 号, 仅当 block_reason = 2、3、4 时有意义
	private boolean slowed;// 机器人是否减速
	private Integer slow_reason;// 减速的原因, 0 = Ultrasonic (超声), 1 = Laser (激光), 2
								// = Fallingdown (防跌落传感器), 3 = Collision
								// (碰撞传感器), 4 = Infrared (红外传感器), 5 =
								// Lock（锁车开关），6 = 动态障碍物， 7 = 虚拟激光点，8 = 3D 相机
	private Double slow_x;// 最近的导致减速的障碍物位置的 x 坐标, 单位 m
	private Double slow_y;// 最近的导致减速的障碍物位置的 y 坐标, 单位 m
	private Integer slow_ultrasonic_id;// 发生减速的超声 id 号, 仅当 slow_reason = 0 时有意义
	private Integer slow_di;// 发生减速的 DI 的 id 号, 仅当 slow_reason = 2、3、4 时有意义
	private boolean brake;// 机器人是否抱闸, 如果该字段不存在, 说明机器人没有抱闸功能
	private List<Double> lasers;// 激光点云数据
	private List<List<Double>> path;// 按顺序的路径点数据, 依次连接就是机器人规划的路径
	private List<String> area_ids;// 机器人所在区域 id 的数组(由于地图上的区域是可以重叠的,
									// 所以机器人可能同时在多个区域)，数组可能为空
	private boolean emergency;// true 表示急停按钮处于激活状态(按下), false 表示急停按钮处于非激活状态(拔起)
	private boolean driver_emc;// true 表示驱动器发生急停, false 驱动器发生未急停
	private Double yaw;// 偏航角，单位：rad
	private Double roll;// 滚转角，单位：rad
	private Double pitch;// 俯仰角，单位：rad
	private List<Object> ultrasonic_nodes;// 超声节点数据
	private List<Object> pgvs;// 二维码识别数据
	private Integer task_status;// 0 = NONE, 1 = WAITING, 2 = RUNNING, 3 =
								// SUSPENDED, 4 = COMPLETED, 5 = FAILED, 6 =
								// CANCELED
	private Integer task_type;// 导航类型, 0 = 没有导航, 1 = 自由导航到任意点, 2 = 自由导航到站点, 3 =
								// 路径导航到站点, 5 = 钻货架, 6 = 跟随, 7 = 平动转动, 8 = 磁条导航,
								// 100 = 其他
	private String target_id;// 当前导航要去的站点, 仅当 task_type 为 2 或 3 时该字段有效,
								// task_status 为 RUNNING 时说明正在去这个站点, task_status
								// 为 COMPLETED 时说明已经到达这个站点, task_status 为 FAILED
								// 时说明去这个站点失败, task_status 为 SUSPENDED
								// 说明去这个站点的导航暂停
	private List<Double> target_point;// 当前导航要去的坐标点, 为一个包含三个元素的数组, 分别为在世界坐标系中的
										// x, y, r 坐标, 仅当 task_type 为 1 时该字段有效,
										// task_status 为 RUNNING 时说明正在去这个坐标点,
										// task_status 为 COMPLETED 时说明已经到达这个坐标点,
										// task_status 为 FAILED 时说明去这个坐标点失败,
										// task_status 为 SUSPENDED 说明去这个坐标点的导航暂停
	private List<String> finished_path;// 当前导航路径上已经经过的站点, 为站点的数组, 仅当 task_type 为
										// 3 或 4 时该字段有效点。如果是路径导航，
										// 这里会列出所有已经经过的中间点
	private List<String> unfinished_path;// 当前导航路径上尚未经过的站点, 为站点的数组, 仅当 task_type
											// 为 3 或 4 时该字段有效。如果是路径导航，
											// 这里会列出所有尚未经过的中间点
	private String roboroute_target;// 调度系统 RoboRoute 让机器人去的最终目标站点 ID
	private List<Object> fatals;// 报警码 Fatal 的数组, 所有出现的 Fatal 报警都会出现在数据中
	private List<Object> errors;// 报警码 Error 的数组, 所有出现的 Error 报警都会出现在数据中
	private List<Object> warnings;// 报警码 Warning 的数组, 所有出现的 Warning 报警都会出现在数据中
	private List<Object> notices;// 报警码 Warning 的数组, 所有出现的 Notice 报警都会出现在数据中
	private Integer ret_code;// API 错误码
	private String err_msg;// 错误信息

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getAngle() {
		return angle;
	}

	public void setAngle(Double angle) {
		this.angle = angle;
	}

	public Double getConfidence() {
		return confidence;
	}

	public void setConfidence(Double confidence) {
		this.confidence = confidence;
	}

	public String getCurrent_station() {
		return current_station;
	}

	public void setCurrent_station(String current_station) {
		this.current_station = current_station;
	}

	public String getLast_station() {
		return last_station;
	}

	public void setLast_station(String last_station) {
		this.last_station = last_station;
	}

	public Double getVx() {
		return vx;
	}

	public void setVx(Double vx) {
		this.vx = vx;
	}

	public Double getVy() {
		return vy;
	}

	public void setVy(Double vy) {
		this.vy = vy;
	}

	public Double getW() {
		return w;
	}

	public void setW(Double w) {
		this.w = w;
	}

	public Double getSteer() {
		return steer;
	}

	public void setSteer(Double steer) {
		this.steer = steer;
	}

	public Double getSpin() {
		return spin;
	}

	public void setSpin(Double spin) {
		this.spin = spin;
	}

	public Double getR_vx() {
		return r_vx;
	}

	public void setR_vx(Double r_vx) {
		this.r_vx = r_vx;
	}

	public Double getR_vy() {
		return r_vy;
	}

	public void setR_vy(Double r_vy) {
		this.r_vy = r_vy;
	}

	public Double getR_w() {
		return r_w;
	}

	public void setR_w(Double r_w) {
		this.r_w = r_w;
	}

	public Double getR_steer() {
		return r_steer;
	}

	public void setR_steer(Double r_steer) {
		this.r_steer = r_steer;
	}

	public Double getR_spin() {
		return r_spin;
	}

	public void setR_spin(Double r_spin) {
		this.r_spin = r_spin;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Integer getBlock_reason() {
		return block_reason;
	}

	public void setBlock_reason(Integer block_reason) {
		this.block_reason = block_reason;
	}

	public Double getBlock_x() {
		return block_x;
	}

	public void setBlock_x(Double block_x) {
		this.block_x = block_x;
	}

	public Double getBlock_y() {
		return block_y;
	}

	public void setBlock_y(Double block_y) {
		this.block_y = block_y;
	}

	public Integer getBlock_ultrasonic_id() {
		return block_ultrasonic_id;
	}

	public void setBlock_ultrasonic_id(Integer block_ultrasonic_id) {
		this.block_ultrasonic_id = block_ultrasonic_id;
	}

	public Integer getBlock_di() {
		return block_di;
	}

	public void setBlock_di(Integer block_di) {
		this.block_di = block_di;
	}

	public boolean isSlowed() {
		return slowed;
	}

	public void setSlowed(boolean slowed) {
		this.slowed = slowed;
	}

	public Integer getSlow_reason() {
		return slow_reason;
	}

	public void setSlow_reason(Integer slow_reason) {
		this.slow_reason = slow_reason;
	}

	public Double getSlow_x() {
		return slow_x;
	}

	public void setSlow_x(Double slow_x) {
		this.slow_x = slow_x;
	}

	public Double getSlow_y() {
		return slow_y;
	}

	public void setSlow_y(Double slow_y) {
		this.slow_y = slow_y;
	}

	public Integer getSlow_ultrasonic_id() {
		return slow_ultrasonic_id;
	}

	public void setSlow_ultrasonic_id(Integer slow_ultrasonic_id) {
		this.slow_ultrasonic_id = slow_ultrasonic_id;
	}

	public Integer getSlow_di() {
		return slow_di;
	}

	public void setSlow_di(Integer slow_di) {
		this.slow_di = slow_di;
	}

	public boolean isBrake() {
		return brake;
	}

	public void setBrake(boolean brake) {
		this.brake = brake;
	}

	public List<Double> getLasers() {
		return lasers;
	}

	public void setLasers(List<Double> lasers) {
		this.lasers = lasers;
	}

	public List<List<Double>> getPath() {
		return path;
	}

	public void setPath(List<List<Double>> path) {
		this.path = path;
	}

	public List<String> getArea_ids() {
		return area_ids;
	}

	public void setArea_ids(List<String> area_ids) {
		this.area_ids = area_ids;
	}

	public boolean isEmergency() {
		return emergency;
	}

	public void setEmergency(boolean emergency) {
		this.emergency = emergency;
	}

	public boolean isDriver_emc() {
		return driver_emc;
	}

	public void setDriver_emc(boolean driver_emc) {
		this.driver_emc = driver_emc;
	}

	public Double getYaw() {
		return yaw;
	}

	public void setYaw(Double yaw) {
		this.yaw = yaw;
	}

	public Double getRoll() {
		return roll;
	}

	public void setRoll(Double roll) {
		this.roll = roll;
	}

	public Double getPitch() {
		return pitch;
	}

	public void setPitch(Double pitch) {
		this.pitch = pitch;
	}

	public List<Object> getUltrasonic_nodes() {
		return ultrasonic_nodes;
	}

	public void setUltrasonic_nodes(List<Object> ultrasonic_nodes) {
		this.ultrasonic_nodes = ultrasonic_nodes;
	}

	public List<Object> getPgvs() {
		return pgvs;
	}

	public void setPgvs(List<Object> pgvs) {
		this.pgvs = pgvs;
	}

	public Integer getTask_status() {
		return task_status;
	}

	public void setTask_status(Integer task_status) {
		this.task_status = task_status;
	}

	public Integer getTask_type() {
		return task_type;
	}

	public void setTask_type(Integer task_type) {
		this.task_type = task_type;
	}

	public String getTarget_id() {
		return target_id;
	}

	public void setTarget_id(String target_id) {
		this.target_id = target_id;
	}

	public List<Double> getTarget_point() {
		return target_point;
	}

	public void setTarget_point(List<Double> target_point) {
		this.target_point = target_point;
	}

	public List<String> getFinished_path() {
		return finished_path;
	}

	public void setFinished_path(List<String> finished_path) {
		this.finished_path = finished_path;
	}

	public List<String> getUnfinished_path() {
		return unfinished_path;
	}

	public void setUnfinished_path(List<String> unfinished_path) {
		this.unfinished_path = unfinished_path;
	}

	public String getRoboroute_target() {
		return roboroute_target;
	}

	public void setRoboroute_target(String roboroute_target) {
		this.roboroute_target = roboroute_target;
	}

	public List<Object> getFatals() {
		return fatals;
	}

	public void setFatals(List<Object> fatals) {
		this.fatals = fatals;
	}

	public List<Object> getErrors() {
		return errors;
	}

	public void setErrors(List<Object> errors) {
		this.errors = errors;
	}

	public List<Object> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<Object> warnings) {
		this.warnings = warnings;
	}

	public List<Object> getNotices() {
		return notices;
	}

	public void setNotices(List<Object> notices) {
		this.notices = notices;
	}

	public Integer getRet_code() {
		return ret_code;
	}

	public void setRet_code(Integer ret_code) {
		this.ret_code = ret_code;
	}

	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

}
