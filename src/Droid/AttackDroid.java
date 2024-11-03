package Droid;

import BattleWeapon.Weapon;
import java.io.Serializable;

public class AttackDroid extends Droid implements Serializable{
    private static final long serialVersionUID = 1L;
    public AttackDroid(String name ,Weapon weapon){
        super(name,100,20,40, weapon, 5, 0.8, 100, 20);
    }
}
