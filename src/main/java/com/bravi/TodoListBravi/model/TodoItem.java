package com.bravi.TodoListBravi.model;

import com.bravi.TodoListBravi.constantes.Status;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;


@Entity
public class TodoItem {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String title;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime scheduledAt;
    private LocalDateTime scheduledFor;


    public TodoItem() {
    }

    public TodoItem(Long id, String title, Status status, LocalDateTime scheduledAt, LocalDateTime scheduledFor) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.scheduledAt = scheduledAt;
        this.scheduledFor = scheduledFor;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public LocalDateTime getScheduledFor() {
        return scheduledFor;
    }

    public void setScheduledFor(LocalDateTime scheduledFor) {
        this.scheduledFor = scheduledFor;
    }
}