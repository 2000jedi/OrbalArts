package com.moscovin.orbal.items.Seipth;

public enum SeipthEnum {
    FIRE,
    WATER,
    WIND,
    EARTH,
    DARK;

    public int toInt() {
        switch (this) {
            case WATER:
                return 0;
            case FIRE:
                return 1;
            case WIND:
                return 2;
            case EARTH:
                return 3;
            case DARK:
                return 4;
        }
        return -1;
    }

    public static String toString(int i) {
        switch (i) {
            case 0:
                return "Water";
            case 1:
                return "Fire";
            case 2:
                return "Wind";
            case 3:
                return "Earth";
            case 4:
                return "Dark";
        }
        throw new Error("Undefined Seipth Type");
    }
}
