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
package cn.xphsc.web.common;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public interface WebBeanTemplate {


    String PREFIX = "web";
    String TRUE = "true";
    String FALSE = "false";
    String ENABLED = "enabled";;
    String  XSS_PREFIX=PREFIX+".xss";
    String  I18N_PREFIX=PREFIX+".i18n";
    String  I18N_VALIDATOR_PREFIX=I18N_PREFIX+".validator";
      String  CORS_PREFIX=PREFIX+".cors";
    String  RESPONSE_PREFIX=PREFIX+".response";
   String  VALIDATION_PREFIX=PREFIX+".validation";
     String SQLINJECTION_PREFIX=PREFIX+".sqlinjection";
        String JDK8TIME_PREFIX=PREFIX+".jdk8time";
    String CESTOMEXCRPTION_PREFIX=PREFIX+".exception";
     String  CSRF_PREFIX=PREFIX+".csrf";
    String SENSITIVE_PREFIX=PREFIX+".sensitive";
    String DICT_PREFIX=PREFIX+".dict";
    String LOG_PREFIX=PREFIX+".log";
    String CRYPRO_PREFIX=PREFIX+".crypto";
    String THREAD_POOL_PREFIX=PREFIX+".threadpool";
    String EVENT_PREFIX=PREFIX+".event";
    String TREE_PREFIX=PREFIX+".tree";
    String REST_PREFIX=PREFIX+".rest";
    String REST_LOADBALANCER_PREFIX=REST_PREFIX+".loadbalancer";
    String ENV_ENCRYPT_PREFIX=PREFIX+".envencrypt";
     String  STRATEGY_PREFIX=PREFIX+".strategy";
    String  PROXY_PREFIX=PREFIX+".proxy";
     int XSS_PREFIX_ORDER=Integer.MAX_VALUE-1;
     String VALIDATION_PREFIX_ORDER="${"+VALIDATION_PREFIX+".order:-9999}";
     int SQLINJECTION_PREFIX_ORDER = -10000;
    int CESTOMEXCRPTION_PREFIX_ORDER=9998;
     int CSRF_PREFIX_ORDER = Integer.MAX_VALUE-3;
    int  SENSITIVE_PREFIX_ORDER=1;
     int LOG_PREFIX_ORDER=Integer.MAX_VALUE+1;
    int CRYPRO_PREFIX_ORDER=Integer.MAX_VALUE+2;
    int TREE_PREFIX_ORDER = Integer.MAX_VALUE-10;

}
