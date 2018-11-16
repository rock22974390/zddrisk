package com.zdd.risk.service;

import com.zdd.risk.utils.FormatUtil;
import com.zdd.risk.utils.PathUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @author 租无忧科技有限公司
 * @createtime 2016年9月26日下午8:47:14
 */
public class ConfigService implements  IConfigService{

	/** constants:系统常量 */
	private static final Map<String, String> constants = new HashMap<String, String>();
	/** charSet:默认编码 */
	public static String charSet = "GBK";
	public static String charSet_Def = "UTF-8";
	public static String contextPath;
	public static String WebRoot;

    @Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.loadPropertyFile(filterConfig.getInitParameter("propertyFile"), constants);
		log("====加载配置文件[" + constants.toString() + "]====");// 加载配置文件时请隐藏敏感信息
		charSet = FormatUtil.isEmpty(constants.get("char.set")) ? charSet : constants.get("char.set");
		contextPath = filterConfig.getServletContext().getContextPath();
		WebRoot = filterConfig.getServletContext().getRealPath("/");
		log("====根目录：" + WebRoot);
		log("====初始化完成====");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rep;
		request.setCharacterEncoding(charSet);
		response.setCharacterEncoding(charSet);
		response.setContentType("text/plain; charset=utf-8");
		// String target = request.getRequestURI().substring(contextPathLength);
		chain.doFilter(request, response);

	}

	@Override
	public void loadPropertyFile(String file, Map<String, String> constants) {
		if (FormatUtil.isEmpty(file)) {
			log("[警告]未设置配置文件：propertyFile");
			return;
		}
		int index = file.indexOf(":");
		String propPath = "";
		if (index < 0) {
			propPath = PathUtil.getWebRootPath() + "/"
					+ ((file.charAt(0) == '/') || (file.charAt(0) == '\\') ? "WEB-INF" : "") + file;
		} else if (file.substring(0, index).equalsIgnoreCase("classpath")) {
			propPath = PathUtil.getRootClassPath() + "/" + file.substring(index + 1, file.length());
		} else if (file.substring(0, index).equalsIgnoreCase("file")) {
			propPath = "/" + file.substring(index + 1, file.length());
		}
		propPath = propPath.replaceAll("\\\\", "/");
		propPath = propPath.replaceAll("/+", "/");
		try {
			InputStream is = new FileInputStream(new File(propPath));
			Properties prop = new Properties();
			prop.load(is);
			for (Entry<Object, Object> entry : prop.entrySet()) {
				constants.put(entry.getKey().toString(), entry.getValue().toString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	private void log(String msg) {
		System.out.println(getTime() + "\t: " + msg);
	}

	@Override
	public Map<String, String> getConstants() {
		return constants;
	}
	@Override
	public String getContextPath() {
		return contextPath;
	}
	@Override
	public String getWebRoot() {
		return WebRoot;
	}

}
