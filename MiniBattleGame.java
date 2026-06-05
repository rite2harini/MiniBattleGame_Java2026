import java.util.Scanner;

class Player {

    private String name;
    private int health;

    Player(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void reduceHealth(int damage) {
        health -= damage;
    }

    public void increaseHealth(int value) {
        health += value;
    }
}

public class MiniBattleGame {

    boolean defending = false;

    void attack(Player enemy) {

        System.out.println("\n⚔ PLAYER ATTACKS: " + enemy.getName() + " ⚔");

        enemy.reduceHealth(20);

        if (enemy.getHealth() <= 0) {
            System.out.println(enemy.getName() + " has been defeated!");
        } else {
            System.out.println(enemy.getName() +
                    " Remaining Health: " +
                    enemy.getHealth());
        }
    }

    void defend() {

        System.out.println("\n Player is defending!");
        defending = true;
    }

    void heal(Player player) {

        System.out.println("\n💚 Player healed by 10 points!");

        if (player.getHealth() <= 90) {
            player.increaseHealth(10);
        }

        System.out.println(player.getName() +
                " Health: " +
                player.getHealth());
    }

    void enemyAttack(Player player) {

        System.out.println("\n👾 Enemy attacks!");

        if (defending) {
            System.out.println("Defense successful! Only 10 damage taken.");
            player.reduceHealth(10);
            defending = false;
        } else {
            System.out.println("No defense! 20 damage taken.");
            player.reduceHealth(20);
        }

        System.out.println(player.getName() +
                " Health: " +
                player.getHealth());
    }

    boolean allEnemiesDefeated(Player[] enemies) {

        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].getHealth() > 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        MiniBattleGame game = new MiniBattleGame();

        Player player = new Player("Hero", 100);

        // ARRAY OF ENEMIES
        Player[] enemies = new Player[3];

        enemies[0] = new Player("Dragon", 100);
        enemies[1] = new Player("Zombie", 80);
        enemies[2] = new Player("Vampire", 120);

        while (player.getHealth() > 0) {

            System.out.println("\n===== HERO STATUS =====");
            System.out.println("Hero Health: " + player.getHealth());

            System.out.println("\n===== ENEMIES =====");

            for (int i = 0; i < enemies.length; i++) {

                if (enemies[i].getHealth() > 0) {
                    System.out.println(i + " -> " +
                            enemies[i].getName() +
                            " Health: " +
                            enemies[i].getHealth());
                } else {
                    System.out.println(i + " -> " +
                            enemies[i].getName() +
                            " (DEFEATED)");
                }
            }

            if (game.allEnemiesDefeated(enemies)) {
                System.out.println("\n All enemies defeated!");
                break;
            }

            System.out.println("\n===== MENU =====");
            System.out.println("1. Attack");
            System.out.println("2. Defend");
            System.out.println("3. Heal");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = -1;

            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Enter numbers only.");
                sc.nextLine(); // clear buffer
                continue;
            }

            switch (choice) {

                case 1:

                    System.out.print("Choose enemy (0-2): ");

                    int enemyChoice = -1;

                    try {
                        enemyChoice = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Invalid enemy input!");
                        sc.nextLine();
                        continue;
                    }

                    if (enemyChoice >= 0 &&
                        enemyChoice < enemies.length) {

                        Player target = enemies[enemyChoice];

                        if (target.getHealth() <= 0) {
                            System.out.println("Enemy already defeated!");
                        } else {
                            game.attack(target);

                            if (target.getHealth() > 0) {
                                game.enemyAttack(player);
                            }
                        }

                    } else {
                        System.out.println("Invalid enemy choice!");
                    }

                    break;

                case 2:

                    game.defend();
                    game.enemyAttack(player);
                    break;

                case 3:

                    game.heal(player);
                    break;

                case 4:

                    System.out.println("Game exited.");
                    sc.close();
                    return;

                default:

                    System.out.println("Invalid choice!");
            }
        }

        System.out.println("\n===== GAME OVER =====");

        if (player.getHealth() <= 0) {
            System.out.println(" Player Lost!");
        } else {
            System.out.println(" Player Won!");
        }

        sc.close();
    }
}
