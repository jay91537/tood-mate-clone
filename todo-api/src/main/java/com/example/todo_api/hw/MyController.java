package com.example.todo_api.hw;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MyController {

    @Autowired
    private MyService myService;

    public void controllerMethod() {
        System.out.println("controller");
        myService.serviceMethod();
    }

}
