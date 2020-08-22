package com.company;

import com.sun.deploy.security.SelectableSecurityManager;

import java.util.Random;

public class Main {

    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefenceType = "";
    public static int[] heroesHealth = {250, 260, 270, 300, 400, 240};
    public static int[] heroesDamage = {20, 15, 25, 5, 10};
    public static String[] heroesAttackType = {"Physical",
            "Magical", "Kinetic", "Medical", "Golem", "Lucky"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        System.out.println("Game start");
        printStatistics();
        while (!isGameFinished()) {
            roundNumber++;
            System.out.println("Round: " + roundNumber);
            round();
        }
    }

    public static void changeBossDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length); //0,1,2
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefenceType);
    }

    public static void healingHeroes() {
        if (heroesHealth[3]>0){
            Random m = new Random();
            int healQuantity = (m.nextInt(2)+1)*10;//10,20
            for (int i = 0; i <heroesHealth.length ; i++) {
                if (heroesHealth[i] < 100 && heroesHealth[i] > 0   && heroesHealth[3] != heroesHealth[i]) {
                    heroesHealth[i] = heroesHealth[i] + healQuantity;
                    System.out.println("Medic heals " + heroesAttackType[i] + " " + healQuantity);
                    break;

                }
            }
        }
    }

    public static void golemDefence() {
        int defenseValue = bossDamage / 5;
        if (heroesHealth[4]>0){
            for (int i = 0; i < heroesHealth.length ; i++) {
                if (heroesHealth[i]>0 && heroesHealth[4] != heroesHealth[i]){
                    heroesHealth[i] = heroesHealth[i] + defenseValue;
                    heroesHealth[4] = heroesHealth[4] - defenseValue;
                }

            }
        }

    }

    public static void lucky(){
        if (heroesHealth[5]>0){
            Random l =new Random();
            boolean defenseLucky = l.nextBoolean();
            if (defenseLucky){
                heroesHealth[5] =heroesHealth[5] + bossDamage;
                System.out.println("Boss is miss to Lucky");
            }



        }


    }

    public static void round() {
        changeBossDefence();
        heroesHit();
        if (bossHealth > 0) {
            bossHits();
        }
        healingHeroes();
        golemDefence();
        lucky();
        printStatistics();
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] < bossDamage) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0) {
                if (bossHealth > 0) {
                    if (bossDefenceType == heroesAttackType[i]) {
                        Random r = new Random();
                        int coef = r.nextInt(8) + 2; //2,3,4,5,6,7,8,9
                        if (bossHealth - heroesDamage[i] * coef < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamage[i] * coef;
                        }
                        System.out.println("Critical attack: "
                                + (heroesDamage[i] * coef));
                    } else {
                        if (bossHealth - heroesDamage[i] < 0) {
                            bossHealth = 0;
                        } else {
                            bossHealth = bossHealth - heroesDamage[i];
                        }
                    }
                } else {
                    break;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("__________________");
        System.out.println("Boss health: " + bossHealth);
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i]
                    + " Health: " + heroesHealth[i]);
        }
        System.out.println("__________________");
    }

}
