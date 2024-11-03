package BattleWeapon;

import java.io.Serializable;

public abstract class Weapon implements Serializable{
    private String name;
    private int damage;

    public Weapon(String name, int damage){
        this.name = name;
        this.damage = damage;
    }

    public String getName(){
        return name;
    }

    public int getDamage(){
        return damage;
    }
}
