package com.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * RequestFilter
 * @author liushun
 * @since JDK 1.8
 **/
@Log4j2
public class RequestFilter extends ZuulFilter {

    private static final String JWT_TOKEN = "LES-TOKEN=";

    /**
     * 过滤器类型
     * pre：可以在请求被路由之前调用
     * route：在路由请求时候被调用
     * post：在route和error过滤器之后被调用
     * error：处理请求时发生错误时被调用
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 优先级为0，数字越大，优先级越低
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行该过滤器，此处为true，说明需要过滤
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }

    /**
     * 设置敏感头信息
     */
    @Override
    public Object run() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String accessToken = request.getParameter("access_token");
        if(StringUtils.isEmpty(accessToken)) {
            String authorization = request.getHeader("Authorization");
            if(!StringUtils.isEmpty(authorization) && authorization.contains("Bearer")) {
                accessToken = StringUtils.replace(authorization, "Bearer", "").trim();
            }
        }
        if(StringUtils.isEmpty(accessToken)) {
            accessToken = getCookieValue(request, JWT_TOKEN);
        }
        if(!StringUtils.isEmpty(accessToken)) {
            final RequestContext ctx = RequestContext.getCurrentContext();
            ctx.addZuulRequestHeader("Authorization", "Bearer " + accessToken);
        }
        log.info("--->> requestURL:{}", request.getRequestURI());
        log.info("--->> accessToken:{}", accessToken);
        return null;
    }

    protected String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}