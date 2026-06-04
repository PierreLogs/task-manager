package com.taskmanager.ui;

import com.taskmanager.model.*;
import com.taskmanager.service.TaskManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final TaskManager taskManager;
    private final Scanner scanner;
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConsoleUI(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            showMenu();
            String option = scanner.nextLine().trim();
            if (option.equals("0")) {
                System.out.println("¡Hasta luego!");
                break;
            }
            processOption(option);
        }
    }

    private void showMenu() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("         GESTOR DE TAREAS");
        System.out.println("═══════════════════════════════════");
        System.out.println("  1. Crear tarea");
        System.out.println("  2. Listar todas");
        System.out.println("  3. Buscar tarea");
        System.out.println("  4. Actualizar estado");
        System.out.println("  5. Eliminar tarea");
        System.out.println("  6. Filtrar por estado");
        System.out.println("  7. Filtrar por prioridad");
        System.out.println("  8. Tareas vencidas");
        System.out.println("  9. Estadísticas");
        System.out.println("───────────────────────────────────");
        System.out.println("  0. Salir");
        System.out.println("═══════════════════════════════════");
        System.out.print("Elige una opción: ");
    }

    private void processOption(String option) {
        switch (option) {
            case "1" -> createTask();
            case "2" -> listAll();
            case "3" -> searchTask();
            case "4" -> updateStatus();
            case "5" -> deleteTask();
            case "6" -> filterByStatus();
            case "7" -> filterByPriority();
            case "8" -> showOverdue();
            case "9" -> showStats();
            default -> System.out.println("Opción inválida");
        }
    }

    private void createTask() {
        System.out.print("Título: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("El título es obligatorio");
            return;
        }
        System.out.print("Descripción: ");
        String description = scanner.nextLine().trim();
        Priority priority = readEnum(Priority.values(),
                "Prioridad (1=Baja 2=Media 3=Alta 4=Crítica): ");
        Category category = readEnum(Category.values(),
                "Categoría (1=Trabajo 2=Personal 3=Estudio 4=Salud 5=Finanzas 6=Otro): ");
        LocalDate dueDate = readDate("Vence (dd/MM/yyyy, vacío = sin fecha): ");

        Task task = taskManager.createTask(title, description, priority, category, dueDate);
        System.out.println("✅ Creada: " + task.getTitle());
    }

    private void listAll() {
        List<Task> tasks = taskManager.listAll();
        if (tasks.isEmpty()) {
            System.out.println("No hay tareas");
            return;
        }
        tasks.forEach(System.out::println);
    }

    private void searchTask() {
        System.out.print("Buscar: ");
        String keyword = scanner.nextLine().trim();
        List<Task> results = taskManager.search(keyword);
        if (results.isEmpty()) {
            System.out.println("Sin resultados para: " + keyword);
        } else {
            results.forEach(System.out::println);
        }
    }

    private void updateStatus() {
        String id = askForId();
        if (id == null) return;
        Status status = readEnum(Status.values(),
                "Estado (1=Pendiente 2=En Progreso 3=Completada 4=Cancelada): ");
        if (taskManager.updateStatus(id, status)) {
            System.out.println("✅ Estado actualizado");
        } else {
            System.out.println("ID no encontrado");
        }
    }

    private void deleteTask() {
        String id = askForId();
        if (id == null) return;
        System.out.print("¿Eliminar? (s/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            if (taskManager.deleteTask(id)) {
                System.out.println("✅ Eliminada");
            } else {
                System.out.println("ID no encontrado");
            }
        }
    }

    private void filterByStatus() {
        Status status = readEnum(Status.values(),
                "Estado (1=Pendiente 2=En Progreso 3=Completada 4=Cancelada): ");
        List<Task> tasks = taskManager.listByStatus(status);
        if (tasks.isEmpty()) {
            System.out.println("Sin tareas en " + status.getDisplayName());
        } else {
            tasks.forEach(System.out::println);
        }
    }

    private void filterByPriority() {
        Priority priority = readEnum(Priority.values(),
                "Prioridad (1=Baja 2=Media 3=Alta 4=Crítica): ");
        List<Task> tasks = taskManager.listByPriority(priority);
        if (tasks.isEmpty()) {
            System.out.println("Sin tareas con prioridad " + priority.getDisplayName());
        } else {
            tasks.forEach(System.out::println);
        }
    }

    private void showOverdue() {
        List<Task> overdue = taskManager.listOverdue();
        if (overdue.isEmpty()) {
            System.out.println("🎉 No hay vencidas");
        } else {
            System.out.println("⚠ VENCIDAS:");
            overdue.forEach(System.out::println);
        }
    }

    private void showStats() {
        System.out.println("\n── ESTADÍSTICAS ──");
        System.out.println("Total: " + taskManager.getTotalTasks());
        for (Status s : Status.values()) {
            int count = taskManager.listByStatus(s).size();
            System.out.println("  " + s.getDisplayName() + ": " + count);
        }
    }

    private String askForId() {
        System.out.print("ID de la tarea: ");
        String id = scanner.nextLine().trim();
        return id.isEmpty() ? null : id;
    }

    private <T extends Enum<T>> T readEnum(T[] values, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int opt = Integer.parseInt(scanner.nextLine().trim());
                if (opt >= 1 && opt <= values.length) {
                    return values[opt - 1];
                }
                System.out.println("Opción 1-" + values.length);
            } catch (NumberFormatException e) {
                System.out.println("Número inválido");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return null;
            try {
                return LocalDate.parse(input, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Formato: dd/MM/yyyy");
            }
        }
    }
}
