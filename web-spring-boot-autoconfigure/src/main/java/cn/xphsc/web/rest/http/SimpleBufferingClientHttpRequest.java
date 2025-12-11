/*
 * Copyright (c) 2022 huipei.x
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
package cn.xphsc.web.rest.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class SimpleBufferingClientHttpRequest  extends AbstractBufferingClientHttpRequest {
    private final HttpURLConnection connection;
    private final boolean outputStreaming;

    SimpleBufferingClientHttpRequest(HttpURLConnection connection, boolean outputStreaming) {
        this.connection = connection;
        this.outputStreaming = outputStreaming;
    }

    public HttpMethod getMethod() {
        return HttpMethod.resolve(this.connection.getRequestMethod());
    }

    public String getMethodValue() {
        return null;
    }


    public URI getURI() {
        try {
            return this.connection.getURL().toURI();
        } catch (URISyntaxException var2) {
            throw new IllegalStateException("Could not get HttpURLConnection URI: " + var2.getMessage(), var2);
        }
    }

    protected ClientHttpResponse executeInternal(HttpHeaders headers, byte[] bufferedOutput) throws IOException {
        addHeaders(this.connection, headers);
        if (HttpMethod.DELETE == this.getMethod() && bufferedOutput.length == 0) {
            this.connection.setDoOutput(false);
        }

        if (this.connection.getDoOutput() && this.outputStreaming) {
            this.connection.setFixedLengthStreamingMode(bufferedOutput.length);
        }

        this.connection.connect();
        if (this.connection.getDoOutput()) {
            FileCopyUtils.copy(bufferedOutput, this.connection.getOutputStream());
        } else {
            this.connection.getResponseCode();
        }

        return new SimpleClientHttpResponse(this.connection);
    }

    static void addHeaders(HttpURLConnection connection, HttpHeaders headers) {
        Iterator var2 = headers.entrySet().iterator();

        while(true) {
            while(var2.hasNext()) {
                Map.Entry<String, List<String>> entry = (Map.Entry)var2.next();
                String headerName = (String)entry.getKey();
                if ("Cookie".equalsIgnoreCase(headerName)) {
                    String headerValue = StringUtils.collectionToDelimitedString((Collection)entry.getValue(), "; ");
                    connection.setRequestProperty(headerName, headerValue);
                } else {
                    Iterator var5 = ((List)entry.getValue()).iterator();

                    while(var5.hasNext()) {
                        String headerValue = (String)var5.next();
                        String actualHeaderValue = headerValue != null ? headerValue : "";
                        connection.addRequestProperty(headerName, actualHeaderValue);
                    }
                }
            }

            return;
        }
    }}
