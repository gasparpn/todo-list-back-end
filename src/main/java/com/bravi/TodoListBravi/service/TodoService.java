package com.bravi.TodoListBravi.service;

import com.bravi.TodoListBravi.constants.Status;
import com.bravi.TodoListBravi.model.TodoItem;
import com.bravi.TodoListBravi.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService implements ITodoService{

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<TodoItem> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public TodoItem save(TodoItem todoItem) {
        this.verifyScheduledDateTask(todoItem);
        return todoRepository.save(todoItem);
    }

    public void verifyScheduledDateTask(TodoItem todoItem) {
        if(todoItem.getScheduledFor().isBefore(todoItem.getScheduledAt())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "TEST"
            );
        }
    }

    @Override
    public Optional<TodoItem> findById(Long id) {
        return todoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        this.verifyItemBeforeDelete(id);
        todoRepository.deleteById(id);
    }

    public void verifyItemBeforeDelete(Long id) {
        Optional<TodoItem> todoItem = this.findById(id);
        if (!todoItem.isPresent()) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "Task not found" );
        }
        else {
            Status statusItem = todoItem.get().getStatus();
            if (statusItem != Status.DONE){
                throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Can't delete a task that is not completed" );
            }
        }
    }
}
