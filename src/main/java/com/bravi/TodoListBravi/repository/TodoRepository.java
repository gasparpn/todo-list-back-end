package com.bravi.TodoListBravi.repository;

import com.bravi.TodoListBravi.model.TodoItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {
}
