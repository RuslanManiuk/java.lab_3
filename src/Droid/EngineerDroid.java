package Droid;

import BattleWeapon.Weapon;
import java.io.Serializable;

public class EngineerDroid extends Droid implements Serializable{
    private static final long serialVersionUID = 1L;
    public EngineerDroid(String name, Weapon weapon) {
        super(name, 90, 15, 60, weapon, 6, 0.85, 100, 25);
    }
}
