package com.zdd.risk.service;

import com.zdd.risk.utils.FormatUtil;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

/**
 * @author 租无忧科技有限公司
 */
public interface IConfigService {
    public void init(FilterConfig filterConfig) throws ServletException ;
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain)
            throws IOException, ServletException ;
    public void loadPropertyFile(String file, Map<String, String> constants) ;
    public Map<String, String> getConstants();
    public String getContextPath();
    public String getWebRoot();
}
