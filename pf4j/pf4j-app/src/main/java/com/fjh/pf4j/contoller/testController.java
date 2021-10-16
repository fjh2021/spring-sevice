package com.fjh.pf4j.contoller;

import com.fjh.pf4j.demo.Greetings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class testController {

    @Autowired
    private Greetings greetings;

    @GetMapping("greetings")
    public void test(){
        greetings.printGreetings();
    }
}
