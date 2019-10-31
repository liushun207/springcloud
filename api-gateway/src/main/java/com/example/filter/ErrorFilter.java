package com.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * ErrorFilter
 * @author liushun
 * @since JDK 1.8
 **/
@Log4j2
@Component
public class ErrorFilter extends ZuulFilter {
    /**
     * 过滤器类型
     *  pre：可以在请求被路由之前调用(匹配找到转发的目标)
     *  route：在路由请求时候被调用(请求转发)
     *  post：在route和error过滤器之后被调用
     * 	error：处理请求时发生错误时被调用
     */
    @Override
    public String filterType() {
        return "error";
    }

    /**
     * 优先级为0，数字越大，优先级越低
     */
    @Override
    public int filterOrder() {
        return 10;
    }

    /**
     * 是否执行该过滤器，此处为true，说明需要过滤
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器具体逻辑
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();

        log.error("thisis a ErrorFiler : {}", throwable.getCause().getMessage());

        return null;
    }
}
