package Droid;

import BattleWeapon.Weapon;
import java.io.Serializable;

public class HeavyDroid extends Droid implements Serializable{
    public HeavyDroid(String name, Weapon weapon) {
        super(name, 150, 10, 20, weapon, 10, 0.7, 100, 10);
    }
}
