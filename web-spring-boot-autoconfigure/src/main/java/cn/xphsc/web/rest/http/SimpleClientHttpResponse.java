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
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class SimpleClientHttpResponse extends AbstractClientHttpResponse {
    private final HttpURLConnection connection;
    private HttpHeaders headers;
    private InputStream responseStream;

    SimpleClientHttpResponse(HttpURLConnection connection) {
        this.connection = connection;
    }

    public int getRawStatusCode() throws IOException {
        return this.connection.getResponseCode();
    }

    public String getStatusText() throws IOException {
        return this.connection.getResponseMessage();
    }

    public HttpHeaders getHeaders() {
        if (this.headers == null) {
            this.headers = new HttpHeaders();
            String name = this.connection.getHeaderFieldKey(0);
            if (StringUtils.hasLength(name)) {
                this.headers.add(name, this.connection.getHeaderField(0));
            }

            int i = 1;

            while(true) {
                name = this.connection.getHeaderFieldKey(i);
                if (!StringUtils.hasLength(name)) {
                    break;
                }

                this.headers.add(name, this.connection.getHeaderField(i));
                ++i;
            }
        }

        return this.headers;
    }

    public InputStream getBody() throws IOException {
        InputStream errorStream = this.connection.getErrorStream();
        this.responseStream = errorStream != null ? errorStream : this.connection.getInputStream();
        return this.responseStream;
    }

    public void close() {
        if (this.responseStream != null) {
            try {
                StreamUtils.drain(this.responseStream);
                this.responseStream.close();
            } catch (IOException var2) {
            }
        }

    }
}
