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
package com.fjh.pf4j.demo;

import com.fjh.pf4j.api.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Decebal Suiu
 */
@Component
public class Greetings {

    public List<String> printGreetings() {
        Map<String, Greeting> greetingMap = SpringContextHolder.getBean(Greeting.class);
        System.out.println(String.format("Found %d extensions for extension point '%s'", greetingMap.size(), Greeting.class.getName()));
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Greeting> stringGreetingEntry : greetingMap.entrySet()) {
            String res = stringGreetingEntry.getValue().getGreeting();
            list.add(res);
            System.out.println(">>> " + stringGreetingEntry.getValue().getGreeting());
        }

        return list;
    }

}
