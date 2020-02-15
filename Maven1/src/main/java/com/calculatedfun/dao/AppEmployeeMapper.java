package com.calculatedfun.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.calculatedfun.bean.PostInfo;
import com.calculatedfun.bean.ResourceBean;
import com.calculatedfun.bean.StockOutSel;
import com.calculatedfun.bean.StockSel;
import com.calculatedfun.dto.Employee;
import com.calculatedfun.dto.EmployeeMenu;
import com.calculatedfun.dto.EmployeeRole;
import com.calculatedfun.dto.LogisticsPack;
import com.calculatedfun.dto.LogisticsTransfer;
import com.calculatedfun.dto.PayRequestReceive;
import com.calculatedfun.dto.Request;
import com.calculatedfun.dto.RequestLogistics;
import com.calculatedfun.dto.Stock;
import com.calculatedfun.dto.StockOut;
import com.calculatedfun.dto.Users;

public interface AppEmployeeMapper {
	public List<ResourceBean> selectAllResourcess();

	public Employee selectEmployee(@Param("employeeId") int employeeId);

	public Employee selectEmployeeByuserid(@Param("userid") int userid);

	public List<Integer> getEmpAuthorization(@Param("roleArr") String[] roleArr);

	public List<EmployeeMenu> getEmployeeMenu(@Param("empAuthList") List<Integer> empAuthList);

	public void updatepassword(@Param("employeeId") int employeeId, @Param("password") String password);

	List<EmployeeRole> getEmployeeRole();

	int insert(Employee record);

	List<Employee> selectAllEmployee(@Param("page") int page, @Param("pagesize") int pageSize);

	int countEmployee();

	int SearchDelflag(@Param("id") int id);

	int UpdateDelflag(@Param("id") String id, @Param("delflag") String delflag);

	public int getStockListCount(Stock stock);

	public List<StockSel> selectStockSelList(StockSel stockSel);

	public List<StockSel> selectStockList(StockSel stockSel);

	public int updateSendtimes(@Param("stockno") String stockno);

	public String getInternalstates(@Param("requestcode") String rquestcode);

	public int updateStock(Stock stock);

	public int insertStock(Stock stock);

	public Request getAcceptidRequest(@Param("requestcode") String requestcode);

	public List<Request> getAcceptidRequests(@Param("requestcode") String[] requestcode);

	public int updateRequestOwnid(@Param("requestcode") String Istockno, @Param("ownid") String ownid);

	public String getpaystatusByreqcode(@Param("requestcode") String requestcode);

	public int updateRequestStates(@Param("requestcode") String requestcode,
			@Param("beforeInteStates") String beforeInteStates, @Param("afterInteStates") String afterInteStates);

	public int updateReqstaBypaystatus(@Param("requestcode") String requestcode, @Param("paystatus") String paystatus,
			@Param("reqstatus") String reqstatus);

	public LogisticsPack getAcceptidPack(@Param("packno") String packno);

	public int updatePackOwnid(@Param("packno") String packno, @Param("ownid") String ownid);

	public int insertLogTransfer(LogisticsTransfer logTransfer);

	public int getStockOutListCount(StockOut stockOut);

	public List<StockOut> selectStockOutList(StockOutSel stockOutSel);

	public int insertStockOut(StockOut stockOut);

	public int confirmStockOut(StockOut stockOut);

	public int confirmStock(@Param("stockno") Integer stockno, @Param("type") String type);

	public String getpostion(@Param("stockno") Integer stockno, @Param("type") String type);

	public int deleStockbyStockno(@Param("stockno") Integer stockno, @Param("type") String type);

	public StockOut getStockOutById(@Param("id") String id);

	public List<Request> getCostbyRequestids(@Param("requestIdArr") String[] requestIdArr,
			@Param("requeststatus") String requeststatus);

	public List<PayRequestReceive> getPayReceiveList(@Param("requestIdArr") String[] requestIdArr);

	public int financialReceiveStatus(@Param("requestIdArr") String[] requestIdArr);

	/**
	 * 根据当前requeststatus，requestcode来修改requeststatus
	 * 
	 * @param beforeStatus
	 *            当前requeststatus值
	 * @param afterStatus
	 *            要修改成requeststatus值
	 * @param requestcode
	 *            关键字申请单号
	 * @return
	 */
	public int updateRequestStatus(@Param("beforeStatus") String beforeStatus, @Param("afterStatus") String afterStatus,
			@Param("requestcode") String requestcode);

	public Stock getStockbystockno(@Param("stockno") String stockno);

	public RequestLogistics getRequestLogistics(@Param("requestcode") String requestcode);

	public int updateRequestlogistic(RequestLogistics requestlogistic);

	public int insertRequestlogistic(RequestLogistics requestlogistic);

	public int insertRequestlogistics(List<RequestLogistics> requestlogistic);

	public Users gerUser(@Param("userid") String userid);

	/**
	 * 清除有关此物品的其他出库申请
	 * 
	 * @param goodsid
	 * @param type
	 * @param status
	 * @return
	 */
	public int delOthersStockOut(@Param("goodsid") String goodsid, @Param("type") String type,
			@Param("status") String status);

	/**
	 * 通过申请单号获取回邮单信息
	 * 
	 * @param requestcode
	 * @return
	 */
	public PostInfo getPostInfoByReqCode(@Param("requestcode") String requestcode,
			@Param("employeeid") String employeeid);

	public String getReqCurStatus(@Param("requestcode") String requestcode);

	public List<EmployeeRole> selectAllEmployeeRole();
}