package Droid;

import BattleWeapon.Weapon;
import java.io.Serializable;

public class HealerDroid extends Droid implements Serializable{
    private static final long serialVersionUID = 1L;
    public HealerDroid(String name,Weapon weapon){
        super(name, 80, 10, 50, weapon, 3, 0.9, 100, 15);
    }

    public boolean isMedic(){
        return true;
    }
}
