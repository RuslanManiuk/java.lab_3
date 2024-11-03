package BattleArena;

import java.io.Serializable;

public class Arena {
    private static final long serialVersionUID = 1L;
    private String name;
    private double energyEffect;
    private double accuracyEffect;
    private int armorEffect;

    public Arena(String name, double energyEffect, double accuracyEffect, int armorEffect){
        this.name = name;
        this.energyEffect = energyEffect;
        this.accuracyEffect = accuracyEffect;
        this.armorEffect = armorEffect;
    }

    public String getName(){
        return name;
    }

    public double getEnergyEffect(){
        return energyEffect;
    }

    public double getAccuracyEffect(){
        return accuracyEffect;
    }

    public int getArmorEffect(){
        return armorEffect;
    }
}
