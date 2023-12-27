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
package cn.xphsc.web.common.servlet;

import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class HttpServletStreamBuilder {


    //用于将流保存下来
    private static byte[] streamBody;

    public static byte[] streamBody(HttpServletRequest request) {
        try {
            streamBody = StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    return streamBody;
    }


    public static byte[] of(HttpServletRequest request) {
       if(request!=null){
           byte[] bytes = new byte[0];
           try {
               bytes = inputStream2Byte(request.getInputStream());
           } catch (IOException e) {
               e.printStackTrace();
           }
           if (bytes.length == 0 && RequestMethod.POST.name().equals(request.getMethod())) {
               //从ParameterMap获取参数，并保存以便多次获取
               bytes = request.getParameterMap().entrySet().stream()
                       .map(entry -> {
                           String result;
                           String[] value = entry.getValue();
                           if (value != null && value.length > 1) {
                               result = Arrays.stream(value).map(s ->
                                       entry.getKey() + "=" + s)
                                       .collect(Collectors.joining("&"));

                           }
                           else {

                               result = entry.getKey() + "=" + value[0];
                           }

                           return result;
                       }).collect(Collectors.joining("&")).getBytes();
           }  return bytes;

       }
        return null;
    }

    private static byte[] inputStream2Byte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] bytes = new byte[BUFFER_SIZE];
        int length;
        while ((length = inputStream.read(bytes, 0, BUFFER_SIZE)) != -1) {
            outputStream.write(bytes, 0, length);
        }
        return outputStream.toByteArray();
    }

    public static String inputStreamForString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        }

        String string = stringBuilder.toString();
        return string;
    }


    private static final int BUFFER_SIZE = 4096;
}
