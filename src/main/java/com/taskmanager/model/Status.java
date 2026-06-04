package com.taskmanager.model;

public enum Status {
    PENDING("Pendiente"),
    IN_PROGRESS("En Progreso"),
    COMPLETED("Completada"),
    CANCELLED("Cancelada");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
