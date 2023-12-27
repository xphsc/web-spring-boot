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
package cn.xphsc.web.log.entity;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public enum  ActionType {
    /**
     *
     */
    LOGIN("登录"),
    ADD("新增"),
    SELECT("查询"),
    UPDATE("修改"),
    DELETE("删除"),
    VIEW("浏览"),
    UPLOAD("上传"),
    DOWNLOAD("下载"),
    IMPORT("导入"),
    EXPORT("导出"),
    SIGNOUT("退出"),
    AUTO("");
    private String name;
    ActionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "ActionType{" +
                "name='" + name + '\'' +
                '}';
    }
}
