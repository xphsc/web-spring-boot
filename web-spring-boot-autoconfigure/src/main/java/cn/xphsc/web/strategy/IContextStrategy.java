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
package cn.xphsc.web.strategy;


import cn.xphsc.web.strategy.interfaces.ContextStrategyConstraint;
import cn.xphsc.web.strategy.interfaces.ContextStrategyEnum;
import cn.xphsc.web.strategy.interfaces.ContextStrategyStruct;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description: Policy interface to obtain the corresponding actual policy
 * @since 1.1.6
 */
public interface IContextStrategy {

  <T extends Enum<?> & ContextStrategyEnum<T>,U extends ContextStrategyConstraint<T>> U distribute(
          T strategy, Class<U> type);


  default   <T extends Enum<?> & ContextStrategyEnum<T>,U extends ContextStrategyConstraint<T>> U distribute(
          ContextStrategyStruct<T, U> iStrategyStruct){
    return distribute(iStrategyStruct.getEnum(), iStrategyStruct.getType());
  }

}
