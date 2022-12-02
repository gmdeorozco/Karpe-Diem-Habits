package com.KarpeDiemHabits.ChallengesServer.entities;

public enum ChallengeCategory {
    LORD("LORD"),
    HEALTH("HEALTH"),
    FAMILY("FAMILY"),
    MARRIAGE("MARRIAGE"),
    WORK("WORK"),
    CHESS("CHESS"),
    HOME("HOME")
    FUN("FUN");

    private String code;
    ChallengeCategory(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
