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
package cn.xphsc.web.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class XssUtils {
/**
* 使用自带的basicWithImages 白名单
* 允许的便签有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
* strike,strong,sub,sup,u,ul,img
* 以及a标签的href,img标签的src,align,alt,height,width,title属性
*/
private static final  Safelist whitelist=Safelist.basicWithImages();
/** 配置过滤化参数,不对代码进行格式化 */
private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
static {
// 富文本编辑时一些样式是使用style来进行实现的
// 比如红色字体 style="color:red;"
// 所以需要给所有标签添加style属性
 whitelist.addAttributes(":all", "style");
}

public static String clean(String content) {

  return Jsoup.clean(content, "", whitelist, outputSettings);
}


    public static boolean valid(String content) {
        return Jsoup.isValid(content, whitelist);
    }



}
