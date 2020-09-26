package com.vee.scheduler.todo.controller;

import com.vee.scheduler.todo.model.Todo;
import com.vee.scheduler.todo.repo.TodoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(todoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable String id){
        Optional<Todo> optTodo = todoRepository.findById(id);
        if(!optTodo.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(optTodo.get());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Todo todo) throws URISyntaxException {
        Todo savedTodo = todoRepository.save(todo);
        return ResponseEntity.created(new URI("/api/todo/" + savedTodo.getId())).body(savedTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Todo todo){
        Optional<Todo> optUpdatedTodo = todoRepository.findById(id)
                .map(existingTodo -> {
                    BeanUtils.copyProperties(todo, existingTodo, "id");
                    return existingTodo;
                });
        if(optUpdatedTodo.isPresent()) {
            try {
                Todo updatedTodo  = todoRepository.save(optUpdatedTodo.get());
                return ResponseEntity.ok().body(updatedTodo);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        todoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
