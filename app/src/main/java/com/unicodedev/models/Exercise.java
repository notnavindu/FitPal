package com.unicodedev.models;

public class Exercise {

    String name;
    long repetition, sets;

    public Exercise() {
    }

    public Exercise(String name, long repetition, long sets) {
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

    public long getRepetition() {
        return repetition;
    }

    public void setRepetition(long repetition) {
        this.repetition = repetition;
    }

    public long getSets() {
        return sets;
    }

    public void setSets(long sets) {
        this.sets = sets;
    }
}
