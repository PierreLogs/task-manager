package com.taskmanager.repository;

import com.taskmanager.model.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TaskRepository {

    private final Map<String, Task> tasks = new LinkedHashMap<>();
    private static final Path DATA_DIR = Paths.get("data");
    private static final Path DATA_FILE = DATA_DIR.resolve("tasks.csv");

    public TaskRepository() {
        loadFromFile();
    }

    public void save(Task task) {
        tasks.put(task.getId(), task);
        saveToFile();
    }

    public Optional<Task> findById(String id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public List<Task> findByStatus(Status status) {
        return tasks.values().stream()
                .filter(t -> t.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Task> findByPriority(Priority priority) {
        return tasks.values().stream()
                .filter(t -> t.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public List<Task> findByCategory(Category category) {
        return tasks.values().stream()
                .filter(t -> t.getCategory() == category)
                .collect(Collectors.toList());
    }

    public List<Task> searchByTitle(String keyword) {
        return tasks.values().stream()
                .filter(t -> t.getTitle().toLowerCase()
                        .contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Task> findOverdue() {
        return tasks.values().stream()
                .filter(Task::isOverdue)
                .collect(Collectors.toList());
    }

    public boolean delete(String id) {
        Task removed = tasks.remove(id);
        if (removed != null) {
            saveToFile();
            return true;
        }
        return false;
    }

    public int count() {
        return tasks.size();
    }

    public void clear() {
        tasks.clear();
        try {
            Files.deleteIfExists(DATA_FILE);
        } catch (IOException e) {
            // ignore
        }
    }

    private void loadFromFile() {
        try {
            if (!Files.exists(DATA_FILE)) return;
            List<String> lines = Files.readAllLines(DATA_FILE);
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length < 9) continue;
                Task task = new Task(
                        parts[0], parts[1], parts[2],
                        Status.valueOf(parts[3]),
                        Priority.valueOf(parts[4]),
                        Category.valueOf(parts[5]),
                        parts[6].isEmpty() ? null : LocalDate.parse(parts[6]),
                        LocalDateTime.parse(parts[7]),
                        LocalDateTime.parse(parts[8])
                );
                tasks.put(task.getId(), task);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try {
            Files.createDirectories(DATA_DIR);
            List<String> lines = tasks.values().stream()
                    .map(t -> String.join("|",
                            t.getId(), t.getTitle(), t.getDescription(),
                            t.getStatus().name(), t.getPriority().name(),
                            t.getCategory().name(),
                            t.getDueDate() != null ? t.getDueDate().toString() : "",
                            t.getCreatedAt().toString(),
                            t.getUpdatedAt().toString()
                    ))
                    .collect(Collectors.toList());
            Files.write(DATA_FILE, lines);
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }
}
