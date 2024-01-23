import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static int healing = 20;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 99};
    public static int[] heroesDamage = {10, 15, 20, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "HEALER"};
    public static int roundNumber;

    public static void main(String[] args) {
        showStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int health : heroesHealth) {
            if (health > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void playRound() {
        roundNumber++;
        bossAttacks();
        chooseBossDefence();
        heroesAttack();
        healHeroes(); // Add healing after attacks
        showStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = calculateDamage(i);
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
            }
        }
    }

    public static int calculateDamage(int heroIndex) {
        int damage = heroesDamage[heroIndex];
        if (bossDefence == heroesAttackType[heroIndex]) {
            Random random = new Random();
            int coeff = random.nextInt(9) + 2;
            damage *= coeff;
            System.out.println("----> Critical damage: " + damage);
        }
        return damage;
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage;
                }
            }
        }
    }

    public static void healHeroes() {
        int healerIndex = findHealerIndex();
        if (healerIndex != -1) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i != healerIndex && heroesHealth[i] > 0) {
                    heroesHealth[i] += healing;
                    System.out.println(heroesAttackType[healerIndex] + " heals " +
                            heroesAttackType[i] + " for " + healing + " health.");
                }
            }
        }
    }

    public static int findHealerIndex() {
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesAttackType[i].equals("HEALER") && heroesHealth[i] > 0) {
                return i;
            }
        }
        return -1; // No active healer found
    }

    public static void showStatistics() {
        System.out.println("ROUND " + roundNumber + " ----------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}