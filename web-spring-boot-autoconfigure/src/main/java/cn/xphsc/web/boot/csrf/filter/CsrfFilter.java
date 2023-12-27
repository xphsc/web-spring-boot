/*
 * Copyright (c) 2021 huipei.x
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xphsc.web.boot.csrf.filter;

import cn.xphsc.web.common.enums.ExceptionEnum;
import cn.xphsc.web.common.response.ResultMapper;
import cn.xphsc.web.utils.Collects;
import cn.xphsc.web.utils.JacksonUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class CsrfFilter implements Filter {

    public List<String> refererDomain = new ArrayList<>(2);


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String referer = req.getHeader("Referer");
        if(referer==null){
            referer=req.getHeader("referer") ;
        }
        String host = req.getServerName();

        if (referer == null||referer.length()==0) {
            printWriter(resp);
            return;
        }
        java.net.URL url = null;
        try {
            url = new java.net.URL(referer);
        } catch (MalformedURLException e) {
            printWriter(resp);
            return;
        }
        if (!host.equals(url.getHost())) {
            if (Collects.isNotEmpty(refererDomain)) {
                for (String  domain: refererDomain) {
                    if (domain.equals(url.getHost())) {
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                }
            }
            printWriter(resp);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private  void printWriter( HttpServletResponse httpServletResponse){
        PrintWriter printWriter = null;
        try {
            printWriter = httpServletResponse.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
       Map result= ResultMapper.builder().mapping("status", ExceptionEnum.CSRF_EXCEPTION.getCode())
                .mapping("message", ExceptionEnum.CSRF_EXCEPTION.getName()).build();
        if(result!=null){
            printWriter.append(JacksonUtils.toJSONString(result) );
            printWriter.close();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String temp = filterConfig.getInitParameter("refererDomain");
        if (temp != null) {
            String[] url = temp.split(",");
            for (int i = 0; url != null && i < url.length; i++) {
                refererDomain.add(url[i]);
            }
        }
    }
}
