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
package cn.xphsc.web.boot.sqlInjection.filter;

import cn.xphsc.web.boot.sqlInjection.SqlInjectionRequestWrapper;
import cn.xphsc.web.common.enums.ExceptionEnum;
import cn.xphsc.web.boot.sqlInjection.exception.SqlInjectionException;
import cn.xphsc.web.common.response.ResultMapper;
import cn.xphsc.web.common.servlet.HttpServletStreamBuilder;
import cn.xphsc.web.utils.JacksonUtils;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */

public class SqlInjectionFilter implements Filter {
    private  boolean parameterEabled ;
    public List<String> excludes = new ArrayList<>(10);
    public static  final String POST="post";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(handleExcludeURL(req)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if(POST.equalsIgnoreCase(req.getMethod()) && req.getHeader(HttpHeaders.CONTENT_TYPE).contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        SqlInjectionRequestWrapper sqlInjectionRequestWrapper = new SqlInjectionRequestWrapper((HttpServletRequest) servletRequest,parameterEabled);
        try {
            filterChain.doFilter(sqlInjectionRequestWrapper, servletResponse);
        } catch (Exception e) {
            servletRequest.setCharacterEncoding("UTF-8");
            servletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter printWriter = servletResponse.getWriter();
           Map result=null;
            if(StringUtils.isNotBlank(e.getMessage())){
                if(e.getMessage().contains(SqlInjectionException.class.getName())){
                    result=ResultMapper.builder().mapping("code",ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getCode())
                            .mapping("message", ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getName()).build();
                }else if(e.getMessage().contains(String.valueOf(ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getCode()))){
                    result=ResultMapper.builder().mapping("code",ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getCode())
                            .mapping("message", ExceptionEnum.SQL_KEYWORDS_EXCEPTION.getName()).build();
                }
            }

            if(result!=null){
                printWriter.append(JacksonUtils.toJSONString(result) );
                printWriter.close();
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        String temp = arg0.getInitParameter("excludes");
        String filterparameterEabled = arg0.getInitParameter("parameterEabled");
        parameterEabled =Boolean.parseBoolean(filterparameterEabled) ;
        if (temp != null) {
            String[] url = temp.split(",");
            for (int i = 0; url != null && i < url.length; i++) {
                excludes.add(url[i]);
            }
        }
    }

    @Override
    public void destroy() {

    }
    private boolean handleExcludeURL(HttpServletRequest request) {
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        String url = request.getRequestURI();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

}


