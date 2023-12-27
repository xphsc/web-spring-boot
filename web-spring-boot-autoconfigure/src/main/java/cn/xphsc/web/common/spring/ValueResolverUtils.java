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
package cn.xphsc.web.common.spring;


import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringValueResolver;
import java.util.Properties;
import static cn.xphsc.web.common.lang.constant.Constants.*;


/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.1.6
 */
public class ValueResolverUtils implements EmbeddedValueResolverAware {

    private static final String DOLLAR_PREFIX = DOLLAR_LEFT_BRACE;
    private static final String DOLLAR_SUFFIX = RIGHT_BRACE;
    private static final String HASH_PREFIX = HASH_LEFT_BRACE;
    private static final String HASH_SUFFIX = RIGHT_BRACE;

    private static final PropertyPlaceholderHelper dollarHelper = new PropertyPlaceholderHelper(DOLLAR_PREFIX, DOLLAR_SUFFIX);
    private static final PropertyPlaceholderHelper hashHelper = new PropertyPlaceholderHelper(HASH_PREFIX, HASH_SUFFIX);

    private StringValueResolver stringValueResolver;

    public static String resolveDollarPlaceholder(String str, Properties properties) {
        return dollarHelper.replacePlaceholders(str, properties);
    }

    public static String resolveHashPlaceholder(String str, Properties properties) {
        return hashHelper.replacePlaceholders(str, properties);
    }

    public String resolveDollarPlaceholderFromContext(String str) {
        return dollarHelper.replacePlaceholders(str, new PropertyPlaceholderHelper.PlaceholderResolver() {
            @Override
            public String resolvePlaceholder(String placeholderName) {
                return stringValueResolver.resolveStringValue(DOLLAR_PREFIX + placeholderName + DOLLAR_SUFFIX);
            }
        });
    }

    public String resolveHashPlaceholderFromContext(String str) {
        return hashHelper.replacePlaceholders(str, new PropertyPlaceholderHelper.PlaceholderResolver() {
            @Override
            public String resolvePlaceholder(String placeholderName) {
                return stringValueResolver.resolveStringValue(DOLLAR_PREFIX + placeholderName + DOLLAR_SUFFIX);
            }
        });
    }

    public String resolveFromContext(String key) {
        if (key == null || key.length() == 0) {
            return null;
        }
        return stringValueResolver.resolveStringValue(key);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;
    }
}
