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
package cn.xphsc.web.tree;




import cn.xphsc.web.tree.method.TreeNodeMethod;
import java.util.*;

/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Object tree node construction
 * @since 1.0.0
 */
public class ObjectTreeNode {

  private TreeNodeMethod treeNodeMethod;


  public ObjectTreeNode(){
  }
  public static ObjectTreeNode.Builder builder(){
    return  new ObjectTreeNode.Builder();
  }
  private ObjectTreeNode(ObjectTreeNode.Builder builder){
    treeNodeMethod=new TreeNodeMethod(builder.packages);
  }
  public static class Builder {
    private String packages;


    public ObjectTreeNode.Builder packages(String packages) {
    this.packages=packages;
      return this;
    }

    public ObjectTreeNode build() {
      return new ObjectTreeNode(this);
    }
  }


  public  <T> List<T> treeNode(List<T> sourceList,  Class<T> clazz){
     return treeNodeMethod.objectTreeNode(sourceList,clazz);
  }


}
