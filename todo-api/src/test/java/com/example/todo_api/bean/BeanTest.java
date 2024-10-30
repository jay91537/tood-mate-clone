package com.example.todo_api.bean;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanTest {

    ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    public void getAllBeanTest() {
        //스프링 컨테이너를 설정 파일 정보를 이용해 생성하고, 스프링 컨테이너 안에 있는 모든 빈을 조회하는 테스트
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // context 안에 MyBean이 들어있는지 검증
        Assertions.assertThat(context.getBeanDefinitionNames()).contains("myBean");
    }

    @Test
    public void getOneBeanTest() {
        MyBean mybean1 = context.getBean(MyBean.class);
        MyBean mybean2 = context.getBean(MyBean.class);


        System.out.println(mybean1);
        System.out.println(mybean2);

        Assertions.assertThat(mybean1).isSameAs(mybean2);
    }

    @Test
    public void dependencyInjection() {
        MyBean myBean = context.getBean(MyBean.class);
        MySubBean mySubBean = context.getBean(MySubBean.class);

        System.out.println(myBean.getMySubBean());
        System.out.println(mySubBean);


    }




}
