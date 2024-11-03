package Droid;

import BattleWeapon.Weapon;

public abstract class Droid {
    protected String name;
    protected int health;
    protected int damage;
    protected int speed;
    protected Weapon weapon;
    protected int armor;
    protected double accuracy;
    protected double energy;
    protected int regeneration;
    protected int attacksMade;
    protected int damageTaken;

    public Droid(String name, int health, int damage,int speed, Weapon weapon, int armor, double accuracy, int energy, int regeneration){
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.weapon = weapon;
        this.armor = armor;
        this.accuracy = accuracy;
        this.energy = energy;
        this.regeneration = regeneration;
    }

    public boolean isAlive(){
        return health > 0;
    }

    public void regenerateEnergy(){
        energy += regeneration;
        if(energy > 100){
            energy = 100;
        }
    }

    public void displayCharacteristics() {
        System.out.printf("| %-17s | %-10d | %-7d | %-14d |%n", name, health, attacksMade, damageTaken);
    }


    // Геттери і сеттери
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor=armor;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double maxAccuracy) {
        this.accuracy = maxAccuracy;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }


    public int getAttacksMade() {
        return attacksMade;
    }

    public void setAttacksMade(int attacksMade) {
        this.attacksMade = attacksMade;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(int damageTaken) {
        this.damageTaken = damageTaken;
    }

    public boolean isMedic() {
        return false;
    }




}
