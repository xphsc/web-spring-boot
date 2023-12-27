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
package cn.xphsc.web.common.lang.id;



import cn.xphsc.web.common.exception.Exceptions;
import java.net.InetAddress;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 分布式高效有序 ID
 * @since 1.1.6
 */
public class Sequences {

    private static byte LAST_IP;

    private static final SequenceIdWorker ID_WORKER;

    static {
        ID_WORKER = new SequenceIdWorker(getLastAddress(), 0x000000FF & getLastAddress());
    }

    /**
     * 生成id
     */
    public static Long nextId() {
        return ID_WORKER.nextId();
    }

    /**
     * 用 IP 地址最后几个字节标识
     * <p>
     * e.g: 192.168.1.30 -> 30
     *
     * @return last IP
     */
    private static byte getLastAddress() {
        if (LAST_IP != 0) {
            return LAST_IP;
        }
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            byte[] addressByte = inetAddress.getAddress();
            LAST_IP = addressByte[addressByte.length - 1];
        } catch (Exception e) {
            throw Exceptions.runtime("unknown host exception", e);
        }
        return (byte) Math.abs(LAST_IP % 3);
    }

}
