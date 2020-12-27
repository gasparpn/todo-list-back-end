package com.bravi.TodoListBravi.service;

import com.bravi.TodoListBravi.model.TodoItem;

import java.util.List;
import java.util.Optional;

public interface ITodoService {

    List<TodoItem> findAll();
    Optional<TodoItem> findById(Long id);
    TodoItem save(TodoItem todoItem);
    void delete(Long id);
}
