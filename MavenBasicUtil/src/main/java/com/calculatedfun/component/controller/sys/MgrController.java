package com.calculatedfun.component.controller.sys;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.calculatedfun.component.service.sys.ResService;
import com.calculatedfun.erp.util.AppSession;
import com.calculatedfun.util.AppTool;
import com.calculatedfun.util.FormMap;
import com.calculatedfun.util.msg.AppMsg;
import com.kaifantech.component.controller.base.BaseController;
import com.kaifantech.entity.RoleFormMap;

@Controller
@RequestMapping("/")
public class MgrController extends BaseController<FormMap> {
	@Inject
	private ResService resService;

	public static final String LOGIN_PAGE = "/agvLogin";
	public static final String AGV_PAGE = "/agvIndex";
	public static final String AGV_WMS_PAGE = "/agvWms";

	@RequestMapping("agvIndex")
	public String agvIndex(Model model) throws Exception {
		return "/agvIndex";
	}

	@RequestMapping("agvWms")
	public String agvWms(Model model) throws Exception {
		return "/agvWms";
	}

	@RequestMapping("manager")
	public String manager(Model model) throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String domain = request.getHeader("Host");
		if (domain.startsWith("192.168") || domain.startsWith("127.0.0.1")
				|| domain.toLowerCase().startsWith("localhost")) {
			request.setAttribute("selfNet", "true");
		} else {
			request.setAttribute("selfNet", "false");
		}
		model.addAttribute("menuTree", resService.getMenuTree());
		return "/manager";
	}

	@RequestMapping("index")
	public String index(Model model) throws Exception {
		model.addAttribute("menuTree", resService.getMenuTree());
		return "/manager";
	}

	@RequestMapping("welcome")
	public String welcome(Model model) throws Exception {
		return "/welcome";
	}

	@RequestMapping("menu")
	public String menu(Model model) {
		return "/framework/menu";
	}

	@RequestMapping("getRole")
	@ResponseBody
	public String getRole() {
		List<RoleFormMap> roles = AppSession.getUserRoleList();

		boolean wmsFlag = false;
		for (RoleFormMap role : roles) {
			if ("admin".equals(role.get("roleKey"))) {
				return "admin";
			}
			if ("weiwei-wms".equals(role.get("roleKey"))) {
				wmsFlag = true;
			}
		}
		if (wmsFlag) {
			return "weiwei-wms";
		} else {
			return "";
		}
	}

	@RequestMapping("findAuthority")
	@ResponseBody
	public List<String> findAuthority() {
		return null;
	}

	@RequestMapping("download")
	public void download(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		String ctxPath = request.getSession().getServletContext().getRealPath("/") + "\\" + "filezip\\";
		String downLoadPath = ctxPath + fileName;
		try {
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		SecurityUtils.getSubject().logout();
		return LOGIN_PAGE;
	}

	@RequestMapping("checkLogin")
	@ResponseBody
	public AppMsg checkLogin() {
		try {
			Object userId = AppSession.getUserId();
			if (AppTool.isNull(userId))
				return AppMsg.fail();
			return AppMsg.success();
		} catch (Exception e) {
			return AppMsg.fail();
		}
	}

	@RequestMapping("currentUser")
	@ResponseBody
	public Object currentUser() {
		return AppSession.getUserName();
	}

	@RequestMapping("docs")
	@ResponseBody
	public String docs() {
		return "帮助文档正在建设中……";
	}

	@RequestMapping("tips")
	@ResponseBody
	public String tips() {
		return "提醒功能正在建设中……";
	}
}
