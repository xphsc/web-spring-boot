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



import org.springframework.http.HttpMethod;
import org.springframework.http.client.*;
import javax.net.ssl.*;
import java.io.IOException;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class HttpClientHttpRequestFactory implements ClientHttpRequestFactory, AsyncClientHttpRequestFactory {
    private Proxy proxy;
    private boolean bufferRequestBody = true;
    private int chunkSize = 4096;
    private int connectTimeout = -1;
    private int readTimeout = -1;
    private boolean outputStreaming = true;
    private boolean ignoreCertificate;
    private static SSLSocketFactory factory;
    private static final TrustingHostnameVerifier
            TRUSTING_HOSTNAME_VERIFIER = new TrustingHostnameVerifier();
    public HttpClientHttpRequestFactory() {
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }
    public void ignoreCertificate(Boolean ignoreCertificate) {
        this.ignoreCertificate = ignoreCertificate;
    }
    public void setBufferRequestBody(boolean bufferRequestBody) {
        this.bufferRequestBody = bufferRequestBody;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setOutputStreaming(boolean outputStreaming) {
        this.outputStreaming = outputStreaming;
    }


    public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
        HttpURLConnection connection = this.openConnection(uri.toURL(), this.proxy);
        this.prepareConnection(connection, httpMethod.name());
        return (ClientHttpRequest)(this.bufferRequestBody ? new SimpleBufferingClientHttpRequest(connection, this.outputStreaming) : new SimpleStreamingClientHttpRequest(connection, this.chunkSize, this.outputStreaming));
    }


    protected HttpURLConnection openConnection(URL url, Proxy proxy) throws IOException {
        URLConnection urlConnection = proxy != null ? url.openConnection(proxy) : url.openConnection();
        if (!HttpURLConnection.class.isInstance(urlConnection)) {
            throw new IllegalStateException("HttpURLConnection required for [" + url + "] but got: " + urlConnection);
        } else {
            return (HttpURLConnection)urlConnection;
        }
    }

    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        if (this.connectTimeout >= 0) {
            connection.setConnectTimeout(this.connectTimeout);
        }
        if (this.readTimeout >= 0) {
            connection.setReadTimeout(this.readTimeout);
        }
        if (this.ignoreCertificate) {
            try {
                relaxHostChecking(connection);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }

        }
        connection.setDoInput(true);
        if ("GET".equals(httpMethod)) {
            connection.setInstanceFollowRedirects(true);
        } else {
            connection.setInstanceFollowRedirects(false);
        }

        if (!"POST".equals(httpMethod) && !"PUT".equals(httpMethod) && !"PATCH".equals(httpMethod) && !"DELETE".equals(httpMethod)) {
            connection.setDoOutput(false);
        } else {
            connection.setDoOutput(true);
        }



        connection.setRequestMethod(httpMethod);
    }

    @Override
    public AsyncClientHttpRequest createAsyncRequest(URI uri, HttpMethod httpMethod) throws IOException {
        return null;
    }
    public static void relaxHostChecking(HttpURLConnection conn)
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection httpsConnection = (HttpsURLConnection) conn;
            SSLSocketFactory factory = prepFactory(httpsConnection);
            httpsConnection.setSSLSocketFactory(factory);
            httpsConnection.setHostnameVerifier(TRUSTING_HOSTNAME_VERIFIER);
        }
    }

    static synchronized SSLSocketFactory
    prepFactory(HttpsURLConnection httpsConnection)
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        if (factory == null) {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{ new AlwaysTrustManager() }, null);
            factory = ctx.getSocketFactory();
        }
        return factory;
    }

    private static final class TrustingHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static class AlwaysTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { }
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { }
        public X509Certificate[] getAcceptedIssuers() { return null; }
    }

}
