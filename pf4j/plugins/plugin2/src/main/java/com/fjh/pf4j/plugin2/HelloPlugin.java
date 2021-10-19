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
package com.fjh.pf4j.plugin2;

import com.fjh.pf4j.api.Greeting;
import com.fjh.pf4j.api.MessageProvider;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * A very simple plugin.
 *
 * @author Decebal Suiu
 */
public class HelloPlugin extends Plugin {

    public HelloPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }


    @Override
    public void start() {
        System.out.println("HelloPlugin.start()");
    }

    @Override
    public void stop() {
        System.out.println("HelloPlugin.stop()");
        super.stop(); // to close applicationContext
    }

    @Extension(ordinal = 1)
    public static class HelloGreeting implements Greeting {

        private final MessageProvider messageProvider;

        @Autowired
        private StringRedisTemplate stringRedisTemplate;

        @Autowired
        public HelloGreeting(final MessageProvider messageProvider) {
            this.messageProvider = messageProvider;
        }

        @Override
        public String getGreeting() {
            System.out.printf(" hello plugin's redis :" + stringRedisTemplate.opsForValue().get("key_123"));
//            return messageProvider.getMessage();
            return ">>>  new  hello redis :" + stringRedisTemplate.opsForValue().get("key_123");

        }

    }

}
