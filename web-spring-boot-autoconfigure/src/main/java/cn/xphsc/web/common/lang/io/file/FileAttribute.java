/*
 * Copyright (c) 2023 huipei.x
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
package cn.xphsc.web.common.lang.io.file;

import cn.xphsc.web.common.lang.io.Files;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: 文件属性
 * @since 2.0.0
 */
public class FileAttribute implements Serializable {

    private static final long serialVersionUID = 90456092839304098L;

    public FileAttribute(Path path, BasicFileAttributes attr) {
        this.path = path;
        this.attr = attr;
        this.filePath = path.toString();
        this.fileName = Files.getFileName(filePath);
        this.size = attr.size();
        this.symbolicLink = attr.isSymbolicLink();
        this.directory = attr.isDirectory();
        this.regularFile = attr.isRegularFile();
        this.other = attr.isOther();
        this.createTime = attr.creationTime().toMillis();
        this.accessTime = attr.lastAccessTime().toMillis();
        this.modifiedTime = attr.lastModifiedTime().toMillis();
    }

    /**
     * attr
     */
    private final BasicFileAttributes attr;

    /**
     * path
     */
    private Path path;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 是否为连接文件
     */
    private boolean symbolicLink;

    /**
     * 是否为文件夹
     */
    private boolean directory;

    /**
     * 是否为常规文件
     */
    private boolean regularFile;

    /**
     * 是否为其他文件
     */
    private boolean other;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 最后访问时间
     */
    private long accessTime;

    /**
     * 最后修改时间
     */
    private long modifiedTime;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public BasicFileAttributes getAttr() {
        return attr;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isSymbolicLink() {
        return symbolicLink;
    }

    public void setSymbolicLink(boolean symbolicLink) {
        this.symbolicLink = symbolicLink;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public boolean isRegularFile() {
        return regularFile;
    }

    public void setRegularFile(boolean regularFile) {
        this.regularFile = regularFile;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public File getFile() {
        return path.toFile();
    }

    public URI getUri() {
        return path.toUri();
    }

    @Override
    public String toString() {
        return filePath;
    }

}
