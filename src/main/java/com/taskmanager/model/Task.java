package com.taskmanager.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Task {

    private final String id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Category category;
    private LocalDate dueDate;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(String title, String description, Priority priority,
                Category category, LocalDate dueDate) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.status = Status.PENDING;
        this.priority = priority;
        this.category = category;
        this.dueDate = dueDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Task(String id, String title, String description, Status status,
                Priority priority, Category category, LocalDate dueDate,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.category = category;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Status getStatus() { return status; }
    public Priority getPriority() { return priority; }
    public Category getCategory() { return category; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
        this.updatedAt = LocalDateTime.now();
    }

    public void setCategory(Category category) {
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOverdue() {
        return dueDate != null && dueDate.isBefore(LocalDate.now())
                && status != Status.COMPLETED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | %s | Vence: %s%s",
                status.getDisplayName().charAt(0),
                title,
                priority.getDisplayName(),
                category.getDisplayName(),
                dueDate != null ? dueDate : "—",
                isOverdue() ? " ⚠ VENCIDA" : "");
    }
}
