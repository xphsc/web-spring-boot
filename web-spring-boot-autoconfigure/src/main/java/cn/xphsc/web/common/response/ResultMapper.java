package cn.xphsc.web.common.response;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link }
 *
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
public class ResultMapper extends HashMap {


    public static ResultMapper.Builder builder(){
        return  new ResultMapper.Builder();
    }
    private ResultMapper(ResultMapper.Builder builder){
        this.putAll(builder.result);


    }
    public static class Builder {
        Map result = null;
        public Builder(){
            result= new HashMap(3);
        }




        public ResultMapper.Builder mapping(String key,Object  value) {
            result.put(key,value);
            return this;
        }

        public ResultMapper build() {
            return new ResultMapper(this);
        }
    }
}
