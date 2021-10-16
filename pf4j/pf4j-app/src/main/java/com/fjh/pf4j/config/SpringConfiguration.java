/*
 * Copyright (C) 2012-present the original author or authors.
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
package com.fjh.pf4j.config;

import com.fjh.pf4j.api.MessageProvider;
import com.fjh.pf4j.demo.Greetings;
import com.fjh.pf4j.demo.HelloMessageProvider;
import org.pf4j.spring.SpringPluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author Decebal Suiu
 */
@Configuration
public class SpringConfiguration {

    @Bean
    public SpringPluginManager pluginManager() {
        return new SpringPluginManager();
    }

    @Bean
    @DependsOn("pluginManager")
    public Greetings greetings() {
        return new Greetings();
    }

    @Bean
    public MessageProvider messageProvider() {
        return new HelloMessageProvider();
    }
}
