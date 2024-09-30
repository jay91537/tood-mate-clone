package com.example.todo_api.hw;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class MyRepository {
    public void repositoryMethod() {
        System.out.println("repository");
    }
}
