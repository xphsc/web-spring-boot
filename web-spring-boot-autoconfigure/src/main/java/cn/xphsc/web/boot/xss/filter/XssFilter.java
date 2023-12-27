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
package cn.xphsc.web.boot.xss.filter;





import cn.xphsc.web.boot.xss.XssHttpServletRequestWrapper;
import cn.xphsc.web.boot.xss.exception.XssException;
import cn.xphsc.web.common.enums.ExceptionEnum;
import cn.xphsc.web.common.response.ResultMapper;
import cn.xphsc.web.utils.JacksonUtils;
import cn.xphsc.web.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: xss注入过滤器
 * @since 1.0.0
 */
public class XssFilter implements Filter {

    /**
     * 是否过滤富文本内容
     */
    private  boolean parameterEabled ;

    public List<String> excludes = new ArrayList<>(10);
    public static  final String POST="post";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        if (handleExcludeURL(req)) {
            filterChain.doFilter(request, response);
            return;
        }
        if(POST.equalsIgnoreCase(req.getMethod()) && req.getHeader(HttpHeaders.CONTENT_TYPE).contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            filterChain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request, parameterEabled);
        try {
            filterChain.doFilter(xssRequest, response);
        } catch (Exception e) {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter printWriter = response.getWriter();
            Map result=null;
            if(StringUtils.isNotBlank(e.getMessage())) {
                if (e.getMessage().contains(XssException.class.getName())) {
                    result = ResultMapper.builder().mapping("code", ExceptionEnum.XSS_EXCEPTION.getCode())
                            .mapping("message", ExceptionEnum.XSS_EXCEPTION.getName()).build();
                } else if (e.getMessage().contains(String.valueOf(ExceptionEnum.XSS_EXCEPTION.getCode()))) {
                    result = ResultMapper.builder().mapping("code", ExceptionEnum.XSS_EXCEPTION.getCode())
                            .mapping("message", ExceptionEnum.XSS_EXCEPTION.getName()).build();
                }
            }
            if(result!=null){
                printWriter.append(JacksonUtils.toJSONString(result) );
                printWriter.close();
            }
        }
    }

    private boolean handleExcludeURL(HttpServletRequest request) {
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String filterparameterEabled = filterConfig.getInitParameter("parameterEabled");
            parameterEabled =Boolean.parseBoolean(filterparameterEabled) ;
        String temp = filterConfig.getInitParameter("excludes");
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

}
