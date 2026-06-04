package com.taskmanager;

import com.taskmanager.repository.TaskRepository;
import com.taskmanager.service.TaskManager;
import com.taskmanager.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        TaskRepository repository = new TaskRepository();
        TaskManager taskManager = new TaskManager(repository);
        ConsoleUI ui = new ConsoleUI(taskManager);
        ui.start();
    }
}
