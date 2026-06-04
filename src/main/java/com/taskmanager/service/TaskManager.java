package com.taskmanager.service;

import com.taskmanager.model.*;
import com.taskmanager.repository.TaskRepository;
import java.time.LocalDate;
import java.util.List;

public class TaskManager {

    private final TaskRepository repository;

    public TaskManager(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(String title, String description, Priority priority,
                           Category category, LocalDate dueDate) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        Task task = new Task(title.trim(), description != null ? description.trim() : "",
                priority, category, dueDate);
        repository.save(task);
        return task;
    }

    public boolean updateStatus(String id, Status newStatus) {
        return repository.findById(id)
                .map(task -> {
                    task.setStatus(newStatus);
                    repository.save(task);
                    return true;
                })
                .orElse(false);
    }

    public boolean updatePriority(String id, Priority newPriority) {
        return repository.findById(id)
                .map(task -> {
                    task.setPriority(newPriority);
                    repository.save(task);
                    return true;
                })
                .orElse(false);
    }

    public boolean updateTitle(String id, String newTitle) {
        if (newTitle == null || newTitle.isBlank()) return false;
        return repository.findById(id)
                .map(task -> {
                    task.setTitle(newTitle.trim());
                    repository.save(task);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteTask(String id) {
        return repository.delete(id);
    }

    public List<Task> listAll() {
        return repository.findAll();
    }

    public List<Task> listByStatus(Status status) {
        return repository.findByStatus(status);
    }

    public List<Task> listByPriority(Priority priority) {
        return repository.findByPriority(priority);
    }

    public List<Task> listByCategory(Category category) {
        return repository.findByCategory(category);
    }

    public List<Task> search(String keyword) {
        return repository.searchByTitle(keyword);
    }

    public List<Task> listOverdue() {
        return repository.findOverdue();
    }

    public int getTotalTasks() {
        return repository.count();
    }
}
