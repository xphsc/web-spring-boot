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
package cn.xphsc.web.common.lang.function;



import cn.xphsc.web.utils.ObjectUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * {@link if}
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.6
 */
public class StreamIf<T> {

    private T t;

    private Field field;

    private boolean self=false;

    private final List<Boolean> elseFlags=new ArrayList<>();

    private boolean isElse=false;

    public static class Builder<T>{

        private final StreamIf<T> target;

        public Builder() {
            this.target = new StreamIf<>();
        }

        public Builder<T> bean(T bean){
            target.t = bean;
            return this;
        }

        public Builder<T> field(Field field){
            target.field = field;
            return this;
        }

        public StreamIf<T> build(){
            return target;
        }
    }



    public StreamIf<T> ifNotEmpty() {
        try {
                if(null!=this.field){
                    this.field.setAccessible(true);
                    self= null!=this.field.get(t) && ""!=this.field.get(t);
                } else{
                    self=false;
                }


        } catch (IllegalAccessException e) {
            self=false;
        }
        return this;
    }


    public StreamIf<T> ifEmpty() {
        try {
            if(null!=this.field){
                this.field.setAccessible(true);
                self= null==this.field.get(t) || ""==this.field.get(t);
            }else{
                if(ObjectUtils.isEmpty(t)&&!self){
                    self=true;
                }else{
                    self=false;
                }

             }
        } catch (IllegalAccessException e) {
            self=false;
        }
        return this;
    }


    public StreamIf<T> ifNotEmpty(String name) {
        Field[] fields = t.getClass().getDeclaredFields();
        List<Field> list= Arrays.stream(fields).filter(f->f.getName().equals(name)).collect(Collectors.toList());
        if(list.size()!=0){
            this.field=list.get(0);
            try {
                if(null!=this.field){
                    this.field.setAccessible(true);
                    self= null!=this.field.get(t) && ""!=this.field.get(t);
                }else{
                        self=false;
                }
            } catch (IllegalAccessException e) {
                self=false;
            }
        }else {self=false;}
        return this;
    }


    public StreamIf<T> ifEmpty(String name) {
        Field[] fields = t.getClass().getDeclaredFields();
        List<Field> list= Arrays.stream(fields).filter(f->f.getName().equals(name)).collect(Collectors.toList());
        if(list.size()!=0){
            this.field=list.get(0);
            try {
                if(null!=this.field){
                    this.field.setAccessible(true);
                    self= null==this.field.get(t) || ""==this.field.get(t);;
                }else{
                        self=false;
                }
            } catch (IllegalAccessException e) {
                self=false;
            }
        }else {self=false;}
        return this;
    }

    public StreamIf<T> ifNotEmpty(Field field) {
        this.field=field;
        try {
            if(null!=this.field){
                this.field.setAccessible(true);
                self= null!=this.field.get(t) && ""!=this.field.get(t);
            }else{
                self=false;
            }
        } catch (IllegalAccessException e) {
            self=false;
        }
        return this;
    }


    public StreamIf<T> ifEmpty(Field field) {
        this.field=field;
        try {
            if(null!=this.field){
                this.field.setAccessible(true);
                self= null==this.field.get(t) || ""==this.field.get(t);;
            }else{
                self=false;
            }
        } catch (IllegalAccessException e) {
            self=false;
        }
        return this;
    }

    public StreamIf<T> IF(PredicateInterface<T> predicate) {
        self=predicate.satisfy(t);
        return this;
    }


    public StreamIf<T> elseIf(PredicateInterface<T> predicate) {
        predicate.satisfy(t);
        return this;
    }

    public StreamIf<T> ELSE() {
        isElse=true;
        elseFlags.add(this.self);
        this.self=!this.self;
        return this;
    }



    public StreamIf<T> ifSetString(String str){
        if(isElse){
            if(elseFlags.stream().parallel().noneMatch(flag-> flag)){
                if(self&&null!=field){
                    field.setAccessible(true);
                    try {
                        field.set(t,str);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                isElse=false;
            }
        }else {
            if(self&&null!=field){
                field.setAccessible(true);
                try {
                    field.set(t,str);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }


    public <R> StreamIf<T> ifInstanceof(Class<R> r) {
        if(t != null){
            self= t.getClass().equals(r);
        }else {self=false;}
        return this;
    }


    public T bean() {
        return t;
    }


    public void end() {
    }



    public StreamIf<T> DO(Do<T> ifDo) {
        if(isElse){
            if(elseFlags.stream().parallel().noneMatch(flag-> flag)&&self){
                ifDo.DO(t);
            }
            isElse=false;
        }else {
            if(self){
                ifDo.DO(t);
            }
        }
        return this;
    }


    public StreamIf<T> elseDo(Do<T> ifDo) {
        if(!self){
            ifDo.DO(t);
        }
        return this;
    }

}
