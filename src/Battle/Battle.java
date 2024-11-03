package Battle;

import Droid.*;
import BattleArena.Arena;
import utils.ConsoleColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import io.*;

public class Battle {
    private List<Droid> teamA;
    private List<Droid> teamB;
    private Arena arena;
    private static BattleLog battleLog; // Додано для зберігання журналу бою

    public Battle(List<Droid> teamA, List<Droid> teamB, Arena arena) {
        this.teamA = new ArrayList<>(teamA);
        this.teamB = new ArrayList<>(teamB);
        this.arena = arena;
        this.battleLog = new BattleLog(); // Ініціалізація журналу
    }

    public void start() {
        System.out.printf(ConsoleColors.BOLD + ConsoleColors.BLUE + "🏟️ Бій на арені: %s%n" + ConsoleColors.RESET, arena.getName());
        battleLog.addEntry("🏟️ Бій на арені: " + arena.getName());

        List<Droid> teamAfight = new ArrayList<>(teamA);
        List<Droid> teamBfight = new ArrayList<>(teamB);

        // Застосовуємо ефекти арени
        applyArenaEffects();

        while (!teamAfight.isEmpty() && !teamBfight.isEmpty()) {
            teamRound(teamAfight, teamBfight);
            teamRound(teamBfight, teamAfight);
            Healing(teamAfight);
            Healing(teamBfight);

            displayTeamStats();

        }

        announceWinner();
        battleLog.saveLog("battle_log.ser"); // Зберегти журнал бою після завершення
    }

    private void applyArenaEffects() {
        System.out.println(ConsoleColors.YELLOW + "Застосовуються ефекти арени: " + ConsoleColors.RESET);
        for (Droid droid : teamA) {
            applyEffectToDroid(droid);
        }
        for (Droid droid : teamB) {
            applyEffectToDroid(droid);
        }
    }

    private void applyEffectToDroid(Droid droid) {
        double initialEnergy = droid.getEnergy();
        double initialAccuracy = droid.getAccuracy();
        int initialArmor = droid.getArmor();

        droid.setEnergy(droid.getEnergy() + arena.getEnergyEffect());  // Ефект енергії
        droid.setAccuracy(droid.getAccuracy() + arena.getAccuracyEffect());  // Ефект точності
        droid.setArmor(droid.getArmor() + arena.getArmorEffect());  // Ефект броні

        // Виводимо інформацію про зміни
        System.out.printf(ConsoleColors.YELLOW + "⚙️ Ефекти арени '%s' для дроїда %s:%n", arena.getName(), droid.getName());
        System.out.printf(ConsoleColors.CYAN + "   🔋 Енергія: %.2f -> %.2f%n", initialEnergy, droid.getEnergy());
        System.out.printf(ConsoleColors.CYAN + "   🎯 Точність: %.2f -> %.2f%n", initialAccuracy, droid.getAccuracy());
        System.out.printf(ConsoleColors.CYAN + "   🛡️ Броня: %d -> %d%n" + ConsoleColors.RESET, initialArmor, droid.getArmor());
    }

    private void teamRound(List<Droid> attackers, List<Droid> defenders) {
        for (Droid attacker : attackers) {
            if (!attacker.isAlive()) continue;

            int enemyIndex = selectEnemy(defenders);
            Droid enemy = defenders.get(enemyIndex);

            attack(attacker, enemy);

            if (!enemy.isAlive()) {
                System.out.println(ConsoleColors.RED + "🔥 " + enemy.getName() + " знищений!" + ConsoleColors.RESET);
                defenders.remove(enemy);
                battleLog.addEntry("🔥 " + enemy.getName() + " знищений!");
            }
            attacker.regenerateEnergy();
        }
    }

    private void Healing(List<Droid> team) {
        for (Droid droid : team) {
            if (droid.isMedic() && droid.isAlive()) {
                healTeam(droid, team);
            }
        }
    }

    private void healTeam(Droid medic, List<Droid> team) {
        for (Droid ally : team) {
            if (ally != medic && ally.isAlive()) {
                ally.setHealth(Math.min(100, ally.getHealth() + 10)); // Лікування на 10 одиниць
                System.out.printf(ConsoleColors.GREEN + "❤️ %s лікує %s, відновлюючи 10 здоров'я! Здоров'я %s: %d%n" + ConsoleColors.RESET,
                        medic.getName(), ally.getName(), ally.getName(), ally.getHealth());
                battleLog.addEntry(medic.getName() + " лікує " + ally.getName() + ", відновлюючи 10 здоров'я! Здоров'я: " + ally.getHealth());
            }
        }
    }

    private void displayTeamStats() {
        System.out.println("\n" + ConsoleColors.UNDERLINE + "Статистика дроїдів після раунду:" + ConsoleColors.RESET);
        System.out.println("+-------------------+------------+---------+----------------+");
        System.out.printf("| %-17s | %-10s | %-7s | %-14s |%n", "Дроїд", "Здоров'я", "Атак", "Шкода отримана");
        System.out.println("+-------------------+------------+---------+----------------+");

        for (Droid droid : teamA) {
            droid.displayCharacteristics();
        }
        for (Droid droid : teamB) {
            droid.displayCharacteristics();
        }

        System.out.println("+-------------------+------------+---------+----------------+");
    }

    private void announceWinner() {
        if (teamA.isEmpty()) {
            System.out.println(ConsoleColors.GREEN + "🏆 Команда B виграла!" + ConsoleColors.RESET);
            battleLog.addEntry("🏆 Команда B виграла!");
        } else {
            System.out.println(ConsoleColors.GREEN + "🏆 Команда A виграла!" + ConsoleColors.RESET);
            battleLog.addEntry("🏆 Команда A виграла!");
        }
    }

    public void attack(Droid attacker, Droid enemy) {
        if (attacker.getEnergy() >= 10) {
            if (Math.random() < attacker.getAccuracy()) {
                int inflictedDamage = Math.max(0, attacker.getDamage() + attacker.getWeapon().getDamage() - enemy.getArmor());
                enemy.setHealth(Math.max(0, enemy.getHealth() - inflictedDamage));
                attacker.setAttacksMade(attacker.getAttacksMade() + 1);
                enemy.setDamageTaken(enemy.getDamageTaken() + inflictedDamage);
                System.out.printf(ConsoleColors.RED + "💥 %s атакує %s, завдаючи %d шкоди! Здоров'я %s: %d%n" + ConsoleColors.RESET,
                        attacker.getName(), enemy.getName(), inflictedDamage, enemy.getName(), enemy.getHealth());
                battleLog.addEntry(attacker.getName() + " атакує " + enemy.getName() + ", завдаючи " + inflictedDamage + " шкоди!");
            } else {
                System.out.printf(ConsoleColors.YELLOW + "❌ %s промахується по %s!%n" + ConsoleColors.RESET, attacker.getName(), enemy.getName());
                battleLog.addEntry(attacker.getName() + " промахується по " + enemy.getName() + "!");
            }
            attacker.setEnergy(attacker.getEnergy() - 10);
        } else {
            System.out.printf(ConsoleColors.YELLOW + "%s не має достатньо енергії для атаки!%n", attacker.getName());
            battleLog.addEntry(attacker.getName() + " не має достатньо енергії для атаки!");
        }
    }

    private int selectEnemy(List<Droid> enemies) {
        if (enemies.isEmpty()) {
            throw new IllegalArgumentException("Список ворогів порожній!");
        }
        Random random = new Random();
        int index = random.nextInt(enemies.size()); // size() дає позитивне значення
        return index;
    }

}
