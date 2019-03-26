package com.codecool.wardrobe.api;

public class SingleHanger extends Hanger {
    private Cloth cloth;

    public SingleHanger(int id, Cloth cloth) {
        super(id);
        this.cloth = cloth;
    }

    public Cloth getCloth() {
        return cloth;
    }

    public void setCloth(Cloth cloth) {
        this.cloth = cloth;
    }

    @Override
    public boolean isEmpty(boolean value) {
        return value;
    }
}
