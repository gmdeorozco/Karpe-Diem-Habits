package com.KarpeDiemHabits.TasksServer.entities;

public enum TaskCategory {
    LORD("LORD"),
    HEALTH("HEALTH"),
    FAMILY("FAMILY"),
    MARRIAGE("MARRIAGE"),
    WORK("WORK"),
    CHESS("CHESS"),
    HOME("HOME");

    private String code;
    TaskCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
