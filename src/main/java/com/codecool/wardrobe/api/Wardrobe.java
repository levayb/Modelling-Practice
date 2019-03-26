package com.codecool.wardrobe.api;

import com.codecool.wardrobe.api.exeptions.InvalidIdExeption;

import java.util.HashMap;
import java.util.Map;

public class Wardrobe {
    private static int id;
    private Map<Integer, Hanger> wardrobe = new HashMap<>();
    private int maxSize;
    private int currentHangerSize;
    private static int nextId = 0;

    public Wardrobe(Map<Integer, Hanger> wardrobe, int maxSize, int currentHangerSize) {
        this.wardrobe = wardrobe;
        this.maxSize = maxSize;
        this.currentHangerSize = currentHangerSize;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Wardrobe.id = id;
    }

    public Map<Integer, Hanger> getWardrobe() {
        return wardrobe;
    }

    public void setWardrobe(Map<Integer, Hanger> wardrobe) {
        this.wardrobe = wardrobe;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getCurrentHangerSize() {
        return currentHangerSize;
    }

    public void setCurrentHangerSize(int currentHangerSize) {
        this.currentHangerSize = currentHangerSize;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Wardrobe.nextId = nextId;
    }

    public void addMultyHanger() {
        Hanger hanger = new Multihanger(++nextId, null, null);
        wardrobe.put(hanger.getId(), hanger);
    }

    public void addSingleHanger() {
        Hanger hanger = new SingleHanger(++nextId, null);
        wardrobe.put(hanger.getId(), hanger);
    }

    public Hanger RemoveHangerById(int inputId) throws InvalidIdExeption {
        for (Hanger hanger : getWardrobe().values()) {
            if (hanger.getId() == inputId) {
                wardrobe.remove(hanger);
                return hanger;
            }
        }
        throw new InvalidIdExeption("This ID is not exist");
    }

    public Hanger findHangerById(int inputId) throws InvalidIdExeption {
        for (Hanger hanger : getWardrobe().values()) {
            if (hanger.getId() == inputId) {
                return hanger;
            }
        }
        throw new InvalidIdExeption("This ID is not exist");
    }

    @Override
    public String toString() {
        return "Wardrobe{" +
                "Id" + id +
                "wardrobe=" + wardrobe +
                ", maxSize=" + maxSize +
                ", currentHangerSize=" + currentHangerSize +
                '}';
    }
}
