package com.example.todo_api.todo;

import com.example.todo_api.todo.dto.TodoCreateRequest;
import com.example.todo_api.todo.dto.TodoResponse;
import com.example.todo_api.todo.dto.TodoUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

//@RESPONSEBODY
//@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;


    @PostMapping
    // @RequestMapping(method = RequestMethod.POST, path = "/todo")
    public ResponseEntity<Void> createTodo(@RequestBody @Valid TodoCreateRequest request) throws Exception {
        Long todoId = todoService.createTodo(request.getContent(), request.getMemberId());
        //todoService.createTodo(request);
        return ResponseEntity.created(URI.create("/todo/" + todoId)).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<Todo>> getTodoList(@RequestBody @Valid Long memberId) throws Exception {
        List<Todo> todoList = todoService.getTodoList(memberId);
        return ResponseEntity.ok().body(todoList);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) throws Exception{
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(@PathVariable Long todoId, @RequestBody @Valid TodoUpdateRequest request) throws Exception {
        todoService.updateTodo(todoId, request.getMemberId(), request.getUpdateContent());
        return ResponseEntity.ok().build();
    }

}
