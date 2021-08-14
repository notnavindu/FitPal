package com.unicodedev.models;

public class Exercise {
    private String name;
    private int repetition, sets;

    public Exercise(String name, int repetition, int sets) {
        this.name = name;
        this.repetition = repetition;
        this.sets = sets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
