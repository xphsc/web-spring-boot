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
package cn.xphsc.web.common.spring.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.IOException;
import java.util.stream.Stream;
/**
 * {@link }
 * @author <a href="xiongpeih@163.com">huipei.x</a>
 * @description:
 * @since 1.0.0
 */
class ConditionalOnResourceCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        final AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(ConditionalOnResource.class.getName()));
        final String[] locations = attributes.getStringArray("value");

        if (locations.length == 0) {
            return false;
        }

        final ResourceLoader loader = context.getResourceLoader();
        final ConditionalOnResource.Existence existence = attributes.getEnum("existence");
        final ConditionalOnResource.Type type = attributes.getEnum("type");

        boolean c1 = false;
        switch (existence) {
            case ANY:
                c1 = convert(loader, locations).anyMatch(Resource::exists);
                break;
            case ALL:
                c1 = convert(loader, locations).allMatch(Resource::exists);
                break;
            case NONE:
                c1 = convert(loader, locations).noneMatch(Resource::exists);
                break;
        }

        if (!c1) {return false;}

        boolean c2 = false;
        switch (type) {
            case ANY:
                c2 = true;
                break;
            case FILE:
                c2 = convert(loader, locations)
                        .filter(Resource::exists)
                        .allMatch(this::isFile);
                break;
            case DIRECTORY:
                c2 = convert(loader, locations)
                        .filter(Resource::exists)
                        .allMatch(this::isDirectory);
                break;
        }

        return c2;
    }

    private Stream<Resource> convert(ResourceLoader resourceLoader, String[] locations) {
        return Stream.of(locations).map(resourceLoader::getResource);
    }

    private boolean isFile(Resource resource) {
        try {
            return resource.getFile().isFile();
        } catch (IOException e) {
            return false;
        }
    }

    private boolean isDirectory(Resource resource) {
        try {
            return resource.getFile().isDirectory();
        } catch (IOException e) {
            return false;
        }
    }
}
