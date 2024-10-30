package com.example.todo_api.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class beanTest2 {

    @Autowired
    private MyBean myBean;

    @Autowired
    private MySubBean mySubBean;


    @Test
    public void dependencyInjection() {

        System.out.println(myBean.getMySubBean());
        System.out.println(mySubBean);


    }
}
