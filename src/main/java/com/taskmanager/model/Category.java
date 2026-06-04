package com.taskmanager.model;

public enum Category {
    WORK("Trabajo"),
    PERSONAL("Personal"),
    STUDY("Estudio"),
    HEALTH("Salud"),
    FINANCE("Finanzas"),
    OTHER("Otro");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
