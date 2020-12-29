package com.bravi.TodoListBravi.controller;

import com.bravi.TodoListBravi.model.TodoItem;
import com.bravi.TodoListBravi.service.ITodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value="/todo")
public class TodoController {

    @Autowired
    private ITodoService todoService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<TodoItem> findAll(){
        return todoService.findAll();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public TodoItem save(@Valid @NotNull @RequestBody TodoItem todoItem){
        return todoService.save(todoItem);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping
    public TodoItem update(@Valid @NotNull @RequestBody TodoItem todoItem){
        return todoService.save(todoItem);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        todoService.delete(id);
    }

}
