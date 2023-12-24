package com.moh.yehia.organization.service.filter;

import com.moh.yehia.organization.service.constant.AppConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class UserContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        UserContextHolder.getContext()
                .setCorrelationId(httpServletRequest.getHeader(AppConstants.CORRELATION_ID));
        UserContextHolder.getContext()
                .setUserId(httpServletRequest.getHeader(AppConstants.USER_ID));
        log.info("UserContextFilter =>{}", UserContextHolder.getContext());
        filterChain.doFilter(httpServletRequest, servletResponse);
    }
}
