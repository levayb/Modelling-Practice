package com.codecool.wardrobe.api;

public class Multihanger extends Hanger {
    private Cloth upper;
    private Cloth lower;

    public Multihanger() {
        upper = null;
        lower = null;
    }

    public Cloth getUpper() {
        return upper;
    }

    public void setUpper(Cloth upper) {
        this.upper = upper;
    }

    public Cloth getLower() {
        return lower;
    }

    public void setLower(Cloth lower) {
        this.lower = lower;
    }

    @Override
    public boolean isEmpty(boolean value) {
        return value;
    }



}
