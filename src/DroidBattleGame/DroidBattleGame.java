package DroidBattleGame;

import Droid.*;
import Battle.*;
import BattleArena.*;
import io.BattleLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DroidBattleGame {
    private List<Droid> droids = new ArrayList<>();
    private List<Arena> arenas = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private DroidManager droidManager;
    private BattleManager battleManager;

    public DroidBattleGame() {
        droidManager = new DroidManager(droids, scanner);
        battleManager = new BattleManager(droids, arenas, scanner);
        initializeArenas();
    }

    private void initializeArenas() {
        arenas.add(new ForestArena());
        arenas.add(new SpaceArena());
        arenas.add(new CityArena());
        arenas.add(new VolcanoArena());
    }

    public void gameMenu(){
        boolean gameMenuOver = false;

        do{
            System.out.println("==============================================================");
            System.out.println("|                     Гра: Битва Дроїдів                     |");
            System.out.println("==============================================================");
            System.out.println("|      1. Створити Дроїда                                    |");
            System.out.println("|      2. Показати список вибраних дроїдів                   |");
            System.out.println("|      3. Бій 1 на 1                                         |");
            System.out.println("|      4. Бій команда на команду                             |");
            System.out.println("|      5. Завантажити і запустити бій                        |");
            System.out.println("|      6. Вийти з гри                                        |");
            System.out.println("===============================================================");
            System.out.print("--> ");
            int choice = scanner.nextInt();

            switch(choice){
                case 1 -> droidManager.createDroid();
                case 2 -> droidManager.showDroid();
                case 3 -> battleManager.oneOnOneBattle();
                case 4 -> battleManager.teamBattle();
                case 5 ->  {
                    BattleLog battleLog = new BattleLog();
                    battleLog.loadLog("battle_log.ser"); // Завантаження журналу
                    battleLog.displayLog(); // Вивід журналу;
                }
                case 6 -> gameMenuOver = true;
                default -> System.out.println("Неправильний вибір.");
            }

        } while (!gameMenuOver);
    }
}
