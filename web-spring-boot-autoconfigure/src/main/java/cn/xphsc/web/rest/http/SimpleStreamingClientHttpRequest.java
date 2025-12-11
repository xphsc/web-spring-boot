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
import org.springframework.http.client.AbstractClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public  class SimpleStreamingClientHttpRequest extends AbstractClientHttpRequest {
    private final HttpURLConnection connection;
    private final int chunkSize;
    private OutputStream body;
    private final boolean outputStreaming;

    public SimpleStreamingClientHttpRequest(HttpURLConnection connection, int chunkSize, boolean outputStreaming) {
        this.connection = connection;
        this.chunkSize = chunkSize;
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

    protected OutputStream getBodyInternal(HttpHeaders headers) throws IOException {
        if (this.body == null) {
            if (this.outputStreaming) {
                int contentLength = (int)headers.getContentLength();
                if (contentLength >= 0) {
                    this.connection.setFixedLengthStreamingMode(contentLength);
                } else {
                    this.connection.setChunkedStreamingMode(this.chunkSize);
                }
            }

           SimpleBufferingClientHttpRequest.addHeaders(this.connection, headers);
            this.connection.connect();
            this.body = this.connection.getOutputStream();
        }

        return StreamUtils.nonClosing(this.body);
    }

    protected ClientHttpResponse executeInternal(HttpHeaders headers) throws IOException {
        try {
            if (this.body != null) {
                this.body.close();
            } else {
                SimpleBufferingClientHttpRequest.addHeaders(this.connection, headers);
                this.connection.connect();
                this.connection.getResponseCode();
            }
        } catch (IOException var3) {
        }

        return new SimpleClientHttpResponse(this.connection);
    }
}
