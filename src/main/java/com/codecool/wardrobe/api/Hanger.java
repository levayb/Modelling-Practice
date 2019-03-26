package com.codecool.wardrobe.api;

public abstract class Hanger {

    protected int id;

    public Hanger(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract boolean isEmpty(boolean value);

}
