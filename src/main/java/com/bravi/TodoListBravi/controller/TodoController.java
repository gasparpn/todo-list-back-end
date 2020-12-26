package com.bravi.TodoListBravi.controller;

import com.bravi.TodoListBravi.constantes.Status;
import com.bravi.TodoListBravi.model.TodoItem;
import com.bravi.TodoListBravi.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<TodoItem> findAll(){
        return todoRepository.findAll();
    }

    @PostMapping
    public TodoItem save(@Valid @NotNull @RequestBody TodoItem todoItem){
        return todoRepository.save(todoItem);
    }

    @PutMapping
    public TodoItem update(@Valid @NotNull @RequestBody TodoItem todoItem){
        return todoRepository.save(todoItem);
    }

    @DeleteMapping(value = "/{id}")
    public Optional<TodoItem> delete(@PathVariable Long id){
        Optional<TodoItem> todoItem = this.verifyItemBeforeDelete(id);
        todoRepository.deleteById(id);
        return todoItem;
    }

    public Optional<TodoItem> verifyItemBeforeDelete(Long id) {
        Optional<TodoItem> todoItem = todoRepository.findById(id);
        if (!todoItem.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Item not found"
            );
        }
        else {
            Status statusItem = todoItem.get().getStatus();
            if (statusItem != Status.DONE){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Can't delete a task that is not completed"
                );
            }
        }
        return todoItem;
    }
}
