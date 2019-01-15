package com.moscovin.orbal.orbment;

import com.moscovin.orbal.items.Seipth.SeipthEnum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class OrbalPropsData{
    public boolean isDown;

    public int STR;
    public double STRMult;
    public int CON;
    public double CONMult;
    public int AGL;
    public double AGLMult;
    public int INT;
    public double INTMult;

    public int fire;
    public int water;
    public int wind;
    public int earth;
    public int dark;

    public double MaxMana;
    public double curMana;
    public double ManaMult = 0;
    public double HealthMult = 0;
    public double MaxHealth;
    public double curHealth;
    public double manaRecover = 0.1;
    public double healthRecover = 0;

    public int team = 0;

    public String[] seipths = {"","","","","","",""};

    public OrbalPropsData() {
        this(20, 20, 20, 80, 0, 0, 0, 0, 0, 0, 1);
    }

    public OrbalPropsData(int agl, int str, int con, int _int, int _fire, int _water, int _wind, int _earth, int _dark, double manaMult, double healthMult){
        AGL = agl; // agility
        AGLMult = agl;
        STR = str; // strength
        STRMult = str;
        CON = con; // constitution = VIT
        CONMult = con;
        INT = _int; // intelligence
        INTMult = _int;

        fire = _fire;
        water = _water;
        wind = _wind;
        earth = _earth;
        dark = _dark;

        ManaMult = manaMult;
        HealthMult = healthMult;

        MaxHealth = (100 + STR * 0.1 + CON * 4) * (1 + HealthMult);
        MaxMana = (40 + INT * 3) * (1 + ManaMult);
        curHealth = MaxHealth;

        manaRecover = 0.1;
        healthRecover = 0;

        tick();
        recoverMana();
    }

    public void tick() {
        // TODO: isDown, prevent death
        MaxHealth = (100 + STR * 0.1 + CON * 4) * (1 + HealthMult);
        MaxMana = (40 + INT * 3) * (1 + ManaMult);
        if (curMana + manaRecover < MaxMana) {
            curMana += manaRecover;
        } else {
            curMana = MaxMana;
        }

        heal(healthRecover);
    }

    public int ATK() {
        return (int)((40 + curSTR() * 2) * (1 + curSTR() / 100.0));
    }

    public int MATK() {
        return (int)((30 + curINT() * 0.5) * (1 + curINT() / 100));
    }

    public int DEF() {
        return (int)(60 + curSTR() * 1.5 + curCON() * 2);
    }

    public int MDEF() {
        return 50 + curINT() + curCON() * 2;
    }

    public boolean isCritical() {
        return Math.random() < (100 + curAGL() * 5) / 1000;
    }


    public double corr() {
        return 1- DEF() / (100 + DEF());
    }

    public double mcorr() {
        return 1 - MDEF() / (100 + MDEF());
    }

    public double seipthCorrection(SeipthEnum seipth) {
        switch (seipth) {
            case FIRE:
                return 1 - fire * 20d / (300 + fire * 10);
            case WATER:
                return 1 - water * 20d / (300 + water * 10);
            case WIND:
                return 1 - wind * 20d / (300 + wind * 10);
            case EARTH:
                return 1 - earth * 20d / (300 + earth * 10);
            case DARK:
                return 1;
            default:
                throw new Error("Seipth Type Not Found");
        }
    }

    public double physicMult() {
        if (isCritical()) {
            return 1.5;
        } else {
            return 1;
        }
    }

    public double magicMult(SeipthEnum seipth) {
        double base = 1;
        if (isCritical()) {
            base = 1.5;
        }
        switch (seipth) {
            case FIRE:
                base += fire / 35d;
                break;
            case WATER:
                base += water / 35d;
                break;
            case WIND:
                base += wind / 35d;
                break;
            case EARTH:
                base += earth / 35d;
                break;
            case DARK:
                base += dark / 35d;
                break;
            default:
                throw new Error("Seipth Type Not Found");
        }
        return base;
    }

    public void getDamage(double value) {
        curHealth -= value;
        if (curHealth < 0){
            isDown = true;
            curHealth = MaxHealth;
            curMana = MaxMana;
        }
    }

    public void heal(double value) {
        if (curHealth + value > MaxHealth) {
            curHealth = MaxHealth;
        } else {
            curHealth += value;
        }
    }

    public void recoverMana() {
        curMana = MaxMana;
    }

    public int curSTR() {
        return (int)(STR * STRMult);
    }

    public int curINT() {
        return (int)(INT * INTMult);
    }

    public int curCON() {
        return (int)(CON * CONMult);
    }

    public int curAGL() {
        return (int)(AGL * AGLMult);
    }

    public boolean takeMana(double cnt) {
        if (curMana < cnt) {
            return false;
        } else {
            curMana -= cnt;
            return true;
        }
    }

    public OrbalPropsData(EntityPlayer player) {
        this(OrbalConfig.getTag(player));
    }

    public OrbalPropsData(NBTTagCompound nbt) {
        AGL = nbt.getInteger("AGL");
        STR = nbt.getInteger("STR");
        CON = nbt.getInteger("CON");
        INT = nbt.getInteger("INT");
        fire = nbt.getInteger("fire");
        water = nbt.getInteger("water");
        wind = nbt.getInteger("wind");
        earth = nbt.getInteger("earth");
        dark = nbt.getInteger("dark");
        AGLMult = nbt.getDouble("cAGL");
        STRMult = nbt.getDouble("cSTR");
        CONMult = nbt.getDouble("cCON");
        INTMult = nbt.getDouble("cINT");
        curMana = nbt.getDouble("mana");
        MaxMana = nbt.getDouble("mmana");
        ManaMult = nbt.getDouble("manam");
        curHealth = nbt.getDouble("health");
        MaxHealth = nbt.getDouble("mhealth");
        HealthMult = nbt.getDouble("healthm");
        manaRecover = nbt.getDouble("manar");
        healthRecover = nbt.getDouble("healthr");

        team = nbt.getInteger("team");

        isDown = nbt.getBoolean("down");

        for (int i=0; i < 7; ++i) {
            seipths[i] = nbt.getString("seipth" + i);
        }
    }

    public NBTTagCompound toNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("AGL", AGL);
        nbt.setInteger("STR", STR);
        nbt.setInteger("CON", CON);
        nbt.setInteger("INT", INT);
        nbt.setInteger("fire", fire);
        nbt.setInteger("water", water);
        nbt.setInteger("wind", wind);
        nbt.setInteger("earth", earth);
        nbt.setInteger("dark", dark);
        nbt.setDouble("cAGL", AGLMult);
        nbt.setDouble("cSTR", STRMult);
        nbt.setDouble("cCON", CONMult);
        nbt.setDouble("cINT", INTMult);
        nbt.setDouble("mana", curMana);
        nbt.setDouble("mmana", MaxMana);
        nbt.setDouble("manam", ManaMult);
        nbt.setDouble("health", curHealth);
        nbt.setDouble("mhealth", MaxHealth);
        nbt.setDouble("healthm", HealthMult);
        nbt.setDouble("manar", manaRecover);
        nbt.setDouble("healthr", healthRecover);

        nbt.setInteger("team", team);

        nbt.setBoolean("down", isDown);

        for (int i=0; i < 7; ++i) {
            nbt.setString("seipth" + i, seipths[i]);
        }
        return nbt;
    }
}
