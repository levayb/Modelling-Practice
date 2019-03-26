package com.codecool.wardrobe.api;

import java.util.List;

public class Wardrobe {
    private static int id;
    private List<Hanger> hangers;
    private int maxSize;

    public Wardrobe(List<Hanger> hangers, int maxSize) {
        this.hangers = hangers;
        this.maxSize = maxSize;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Wardrobe.id = id;
    }

    public List<Hanger> getHangers() {
        return hangers;
    }

    public void setHangers(List<Hanger> hangers) {
        this.hangers = hangers;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }



    @Override
    public String toString() {
        return  "Wardrobe{" + id +
                "hangers=" + hangers +
                "maxSize=" + maxSize +
                '}';
    }
}
