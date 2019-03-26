package com.codecool.wardrobe.api;

import com.codecool.wardrobe.api.*;
import com.codecool.wardrobe.api.exeptions.HangerIsEmptyExeption;
import com.codecool.wardrobe.api.exeptions.HangerIsFullExeption;
import com.codecool.wardrobe.api.exeptions.InvalidClothTypeException;
import com.codecool.wardrobe.api.exeptions.IsNotUpperExeption;

import java.util.List;

public class User {
    private String name;
    private List<Cloth> cloths;
    private List<Hanger> hangers;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Cloth> getCloths() {
        return cloths;
    }

    public void setCloths(List<Cloth> cloths) {
        this.cloths = cloths;
    }

    public List<Hanger> getHangers() {
        return hangers;
    }

    public void setHangers(List<Hanger> hangers) {
        this.hangers = hangers;
    }

    public void addClothToSingleHanger(Cloth cloth, SingleHanger singleHanger) throws HangerIsFullExeption, IsNotUpperExeption {
        if (singleHanger.isEmpty(true) && cloth.getType().equals(ClothType.UPPER)) {
            singleHanger.setCloth(cloth);
            singleHanger.isEmpty(false);
        } else if (!cloth.getType().equals(ClothType.UPPER)) {
            throw new IsNotUpperExeption("Onli upper clothing permitted!4!4!");
        } else {
            throw new HangerIsFullExeption("Hanger is full");
        }
    }

    public void addClothToMultyHanger(Cloth cloth, Multihanger multihanger) throws HangerIsFullExeption, InvalidClothTypeException {
        if (cloth.getType().equals(ClothType.UPPER)) {
            if (multihanger.isEmpty(false)) {
                throw new HangerIsFullExeption("Upper space is taken");
            } else {
                multihanger.setUpper(cloth);
            }

        } else if (cloth.getType().equals(ClothType.LOWER)) {
            if (multihanger.isEmpty(false)) {
                throw new HangerIsFullExeption("Lower space is taken");
            } else {
                multihanger.setLower(cloth);
            }
        } else {
            throw new InvalidClothTypeException("There is no such type!");
        }
    }

    public void removeCLothFormSingleHanger(Cloth cloth, SingleHanger singleHanger) throws HangerIsEmptyExeption {
        if (singleHanger.isEmpty(false)) {
            singleHanger.setCloth(null);
            cloths.add(cloth);
        } else {
            throw new HangerIsEmptyExeption("Hanger is empty");
        }
    }

    public void removeUpperFormMultyHanger(Cloth cloth, Multihanger multihanger) throws HangerIsEmptyExeption {
        if (multihanger.getUpper().equals(cloth)) {
            cloths.add(cloth);
            multihanger.setUpper(null);
        } else {
            throw new HangerIsEmptyExeption("There are no clothes on the upper section.");
        }
    }

    public void removeLowerFromMultyHanger(Cloth cloth, Multihanger multihanger) throws HangerIsEmptyExeption {
        if (multihanger.getLower().equals(cloth)) {
            cloths.add(cloth);
            multihanger.setLower(null);
        } else {
            throw new HangerIsEmptyExeption("There are no clothes on the lower section.");
        }
    }
}
