package net.runelite.client.plugins.microbot.example;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.Script;
import net.runelite.client.plugins.microbot.globval.enums.InterfaceTab;
import net.runelite.client.plugins.microbot.util.antiban.Rs2Antiban;
import net.runelite.client.plugins.microbot.util.bank.Rs2Bank;
import net.runelite.client.plugins.microbot.util.camera.Rs2Camera;
import net.runelite.client.plugins.microbot.util.combat.Rs2Combat;
import net.runelite.client.plugins.microbot.util.dialogues.Rs2Dialogue;
import net.runelite.client.plugins.microbot.util.gameobject.Rs2GameObject;
import net.runelite.client.plugins.microbot.util.grandexchange.Rs2GrandExchange;
import net.runelite.client.plugins.microbot.util.grounditem.Rs2GroundItem;
import net.runelite.client.plugins.microbot.util.inventory.Rs2Inventory;
import net.runelite.client.plugins.microbot.util.keyboard.Rs2Keyboard;
import net.runelite.client.plugins.microbot.util.math.Rs2Random;
import net.runelite.client.plugins.microbot.util.npc.Rs2Npc;
import net.runelite.client.plugins.microbot.util.npc.Rs2NpcModel;
import net.runelite.client.plugins.microbot.util.player.Rs2Player;
import net.runelite.client.plugins.microbot.util.tabs.Rs2Tab;
import net.runelite.client.plugins.microbot.util.walker.Rs2Walker;
import net.runelite.client.plugins.microbot.util.widget.Rs2Widget;
import net.runelite.client.plugins.microbot.util.woodcutting.Rs2Woodcutting;

import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static java.awt.event.KeyEvent.VK_SPACE;



public class ExampleScript extends Script {
    private Client client;
    private ActivityManager activityManager = new ActivityManager();
    public static boolean test = false;
    public boolean changedStyle = false;
    public boolean changedStyle1 = false;
    public boolean changedStyle2 = false;
    public boolean sold = true;
    public boolean prikirsta = true;
    public boolean wheattt = true;
    public boolean stylius = true;
    int ctikslas = 10;
    WorldPoint LCow = new WorldPoint(3253, 3288, 0);
    WorldPoint Chick = new WorldPoint(3233, 3295, 0);
    WorldPoint Wood = new WorldPoint(3164, 3401, 0);
    WorldPoint Mining = new WorldPoint(3182, 3371, 0);
    WorldPoint Mining2 = new WorldPoint(3226, 3147, 0);
    WorldPoint smith_bank = new WorldPoint(3095, 3495, 0);
    WorldPoint smith = new WorldPoint(3108, 3499, 0);
    WorldPoint smithh = new WorldPoint(3187, 3425, 0);
    WorldPoint fish = new WorldPoint(3242, 3152, 0);
    WorldPoint fireplace = new WorldPoint(3133, 3220, 0);

    public boolean run(ExampleConfig config) {
        Microbot.enableAutoRunOn = false;


        mainScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                if (!Microbot.isLoggedIn()) return;
                if (!super.run()) return;
                long startTime = System.currentTimeMillis();
                Random random = new Random();

                int attackLevel = Rs2Player.getRealSkillLevel(Skill.ATTACK);
                int strengthLevel = Rs2Player.getRealSkillLevel(Skill.STRENGTH);
                int defenceLevel = Rs2Player.getRealSkillLevel(Skill.DEFENCE);
                int woodlevel = Rs2Player.getRealSkillLevel(Skill.WOODCUTTING);
                int mininglevel = Rs2Player.getRealSkillLevel(Skill.MINING);
                int fishinglevel = Rs2Player.getRealSkillLevel(Skill.FISHING);
                int firelevel = Rs2Player.getRealSkillLevel(Skill.FIREMAKING);
                boolean isInCombat = Rs2Player.isInCombat();
                boolean isAnimating = Rs2Player.isAnimating();
                boolean isMoving = Rs2Player.isMoving();


                int randomOffset = ThreadLocalRandom.current().nextInt(-5, 5);
                int randomSleep = ThreadLocalRandom.current().nextInt(100, 3000);
                if (activityManager.shouldSwitchActivity()) {
                    activityManager.chooseNewActivity();
                }

                switch (activityManager.getCurrentActivity()) {
                    case COMBAT:
                        System.out.println("Combat");
                        combat(attackLevel, randomSleep, randomOffset, defenceLevel, strengthLevel, isAnimating, isInCombat, isMoving);
                        break;
                    case MINING:
                        System.out.println("Mining");
                        mining(randomSleep, randomOffset, mininglevel, isAnimating, isMoving, isInCombat);
                        break;
                    case WOODCUTTING:
                        System.out.println("Woodcutting");
                        wood(woodlevel, randomSleep, randomOffset, isAnimating, isMoving);
                        break;
                    case MAGIC:
                        System.out.println("Magic");
                        magic(isInCombat, randomSleep, randomOffset, isAnimating, isMoving, attackLevel);
                        break;
                    case BANKING:
                        System.out.println("SELLING");
                        sell(randomSleep);
                        break;
                     case BUYING:
                        System.out.println("BUYING");
                        buy(randomSleep);
                        break;
                    case QUESTING:
                        System.out.println("Questing");
                        doQuest(randomOffset);
                        break;
                    case SMITHING:
                        System.out.println("Smithing");
                        smith(randomOffset);
                        break;
                    case AXES:
                        System.out.println("Making axes");
                        smithing(randomOffset);
                        break;
                    case FISHING:
                        System.out.println("Fishing");
                        fishing(randomOffset, fishinglevel, isAnimating, isMoving);
                        break;
                    case FIREMAKING:
                        System.out.println("Firemaking");
                        firemaking(randomOffset, firelevel, isAnimating, isMoving, randomSleep);
                        break;

                }
                sleepRandom(500, 1500);

                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println("Total time for loop " + totalTime);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
        return true;
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }
    private void sleepRandom(int min, int max) {
        try {
            Thread.sleep(new Random().nextInt(max - min) + min);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public Rs2NpcModel getAvailableChicken() {
        Rs2NpcModel chicken = Rs2Npc.getNpc("Chicken");
        if (chicken != null && (chicken.getId() == 1173 || chicken.getId() == 1174)) {
            return chicken;
        }
        return null;
    }
    public Rs2NpcModel getAvailableCow() {
        Rs2NpcModel cow = Rs2Npc.getNpc("Cow");
        if (cow != null && (cow.getId() == 2790 || cow.getId() == 2791 || cow.getId() == 2792 || cow.getId() == 2793 || cow.getId() == 2794)) {
            return cow;
        }
        return null;
    }
    public Rs2NpcModel getAvailableSheep() {
        Rs2NpcModel sheep = Rs2Npc.getNpc("Sheep");
        if (sheep != null && (sheep.getId() == 2693 || sheep.getId() == 2694 || sheep.getId() == 2699 || sheep.getId() == 2787 || sheep.getId() == 2786 || sheep.getId() == 2699)) {
            return sheep;
        }
        return null;
    }
    public void changeCombatStyle(WidgetInfo style) {
        if (Rs2Tab.getCurrentTab() != InterfaceTab.COMBAT) {
            Rs2Tab.switchToCombatOptionsTab();
            sleepUntil(() -> Rs2Tab.getCurrentTab() == InterfaceTab.COMBAT);
        }
        if (Rs2Tab.getCurrentTab() == InterfaceTab.COMBAT) {
            Rs2Combat.setAttackStyle(style);
            sleep(150, 300);
            Rs2Tab.switchToInventoryTab();
        }
    }
    public boolean Irankis(int irankioid) {
        if (Rs2Player.hasPlayerEquippedItem(Rs2Player.getLocalPlayer(), irankioid)) {
            return true;
        } else {
            return false;
        }
    }
    public void pulti(Rs2NpcModel animal) {
        Rs2Npc.attack(animal);
    }
    public void uzsidet(int Iuzsideti, int randomSleep, String pavadinimas, int okaying) {
        if (Rs2Inventory.contains(Iuzsideti)) {
            Rs2Inventory.equip(Iuzsideti);
        } else if (!Rs2Player.hasPlayerEquippedItem(Rs2Player.getLocalPlayer(), Iuzsideti)) {
            Rs2Bank.walkToBank();
            if (!Rs2Bank.isOpen()) {
                Rs2Bank.openBank();
                sleep(randomSleep);
            }
            if (Rs2Bank.isOpen()) {
                Rs2Bank.depositAll();
                sleep(randomSleep);
                if (Rs2Bank.hasItem(Iuzsideti)) {
                    Rs2Bank.withdrawOne(Iuzsideti);
                    sleep(randomSleep);
                    sleep(randomSleep);
                    Rs2Bank.closeBank();
                } else if (Rs2Bank.hasBankItem("Coins", 2000)) {
                    if (Rs2Bank.isOpen()) {
                        System.out.println("Coins");
                        sleep(randomSleep);
                        Rs2Bank.withdrawAll("Coins");
                        sleep(randomSleep);
                        Rs2Bank.closeBank();
                    }
                    Rs2GrandExchange.walkToGrandExchange();
                    if (!Rs2GrandExchange.isOpen()) {
                        Rs2GrandExchange.openExchange();
                        System.out.println("Grand");
                        Rs2GrandExchange.buyItem(pavadinimas, pavadinimas, okaying, 1);
                        sleep(randomSleep);
                        Rs2GrandExchange.collectToInventory();
                        sleep(randomSleep);
                        Rs2GrandExchange.closeExchange();
                        sleep(randomSleep);

                    }
                } else {
                    activityManager.chooseNewActivity();
                }
            }


        }
    }
    public void skirsti(String Ikirsti, int randomSleep) {
        if (!Rs2Inventory.isFull()) {
            Rs2GameObject.interact(Ikirsti);
        } else {
            Rs2Bank.walkToBank();
            if (!Rs2Bank.isOpen()) {
                Rs2Bank.openBank();
                sleep(randomSleep);
            }
            if (Rs2Bank.isOpen()) {
                Rs2Bank.depositAll();
                sleep(randomSleep);
                sleep(randomSleep);
                sleep(randomSleep);
                Rs2Bank.closeBank();
            }
        }
    }
    public void kirsti(int Ikirsti, int randomSleep) {
        if (!Rs2Inventory.isFull()) {
            Rs2GameObject.interact(Ikirsti);
        } else {
            Rs2Bank.walkToBank();
            if (!Rs2Bank.isOpen()) {
                Rs2Bank.openBank();
                sleep(randomSleep);
            }
            if (Rs2Bank.isOpen()) {
                Rs2Bank.depositAll();
                sleep(randomSleep);
                sleep(randomSleep);
                sleep(randomSleep);
                Rs2Bank.closeBank();
            }
        }
    }
    public boolean atstumas(WorldPoint point, int skirtumas) {
        if (Rs2Player.getWorldLocation().distanceTo(point) >= skirtumas) {
            return true;
        } else {
            return false;
        }
    }
    public void combat(int attackLevel, int randomSleep, int randomOffset, int defenceLevel, int strengthLevel, boolean isAnimating, boolean isInCombat, boolean isMoving) {
        if (attackLevel < 11) {
            if (Irankis(1277)) {
                if (!isInCombat) {
                    WorldPoint randomLChick = new WorldPoint(Chick.getX() + randomOffset, Chick.getY() + randomOffset, 0);
                    if (!atstumas(randomLChick, 12)) {
                        Rs2NpcModel chicken = getAvailableChicken();
                        if (chicken != null && !isAnimating && !isMoving) {
                            Rs2Walker.walkTo(chicken.getWorldLocation());
                            pulti(chicken);
                            sleep(randomSleep);
                        }
                    } else {
                        Rs2Walker.walkTo(randomLChick);
                    }
                }
            } else {
                uzsidet(1277, randomSleep, "Bronze sword", 2000);
            }
        } else if (attackLevel < 50) {
            if (Irankis(1327)) {
                if (stylius) {
                    changeCombatStyle(WidgetInfo.COMBAT_STYLE_THREE);
                    stylius = false;
                }
                if (!isInCombat) {
                    WorldPoint randomLCow = new WorldPoint(LCow.getX() + randomOffset, LCow.getY() + randomOffset, 0);
                    if (!atstumas(randomLCow, 13)) {
                        Rs2NpcModel cow = getAvailableCow();
                        if (cow != null && !isAnimating && !isMoving) {
                            Rs2Walker.walkTo(cow.getWorldLocation());
                            pulti(cow);
                            sleep(randomSleep);
                        }
                    } else {
                        Rs2Walker.walkTo(randomLCow);
                    }
                }
            } else {
                uzsidet(1327, randomSleep, "Black scimitar", 3000);

            }
        } else {
            activityManager.chooseNewActivity();
        }

        if (attackLevel >= ctikslas && !changedStyle) {
            changeCombatStyle(WidgetInfo.COMBAT_STYLE_TWO);
            changedStyle = true;
        }
        if (strengthLevel >= ctikslas && !changedStyle1) {
            changeCombatStyle(WidgetInfo.COMBAT_STYLE_FOUR);
            changedStyle1 = true;
        }
        if (defenceLevel >= ctikslas && !changedStyle2) {
            changeCombatStyle(WidgetInfo.COMBAT_STYLE_ONE);
            changedStyle2 = true;
        }

    }
    public void wood(int woodlevel, int randomSleep, int randomOffset, boolean isAnimating, boolean isMoving) {
        if (woodlevel < 30) {
            if (Irankis(1351)) {
                WorldPoint randomWood = new WorldPoint(Wood.getX() + randomOffset, Wood.getY() + randomOffset, 0);
                if (!atstumas(randomWood, 20)) {
                    if (!isAnimating && !isMoving) {
                        kirsti(1276, randomSleep);
                    }
                } else {
                    Rs2Walker.walkTo(randomWood);
                }

            } else {
                uzsidet(1351, randomSleep, "Bronze axe", 500);
            }
        } else if (woodlevel < 36) {
            if (Irankis(1351)) {
                WorldPoint randomWillow = new WorldPoint(2961 + randomOffset, 3196 + randomOffset, 0);
                if (!atstumas(randomWillow, 20)) {
                    if (!isAnimating && !isMoving) {
                        skirsti("Willow tree", randomSleep);
                    }
                } else {
                    Rs2Walker.walkTo(randomWillow);
                }

            } else {
                uzsidet(1351, randomSleep, "Bronze axe", 500);
            }
        } else {
            activityManager.chooseNewActivity();
        }

    }
    public void sell(int randomSleep) {

        Rs2GrandExchange.walkToGrandExchange();

        if (!Rs2Bank.isOpen()) {
            Rs2Bank.openBank();
            sleep(randomSleep);
            if (Rs2Bank.hasItem(1511)) {
                Rs2Bank.setWithdrawAsNote();
                sleep(randomSleep);
                Rs2Bank.withdrawAll(1511);
                sleep(randomSleep);
                Rs2Bank.closeBank();
                sleep(randomSleep);

                if (!Rs2GrandExchange.isOpen()) {
                    Rs2GrandExchange.openExchange();
                    sleep(3000, 8000);

                    if (Rs2Inventory.contains(1512)) {
                        int price = Rs2GrandExchange.getSellPrice(1511);
                        Rs2GrandExchange.sellItem(1512, 9999, price);
                        sleep(5000);
                    }

                    while (!Rs2GrandExchange.hasSoldOffer()) {
                        sleep(2000);
                    }

                    Rs2GrandExchange.collectToInventory();
                    sleep(randomSleep);
                    Rs2GrandExchange.closeExchange();
                    sleep(randomSleep);
                    sold = true;
                    activityManager.chooseNewActivity();

                }
            } else {
                activityManager.chooseNewActivity();
            }
        }


    }
    public void buy(int randomSleep) {

        activityManager.chooseNewActivity();

    }
    public void magic(boolean isInCombat, int randomSleep, int randomOffset, boolean isAnimating, boolean isMoving, int attackLevel) {
        activityManager.chooseNewActivity();
    }
    public void mining(int randomSleep, int randomOffset, int mininglevel, boolean isAnimating, boolean isMoving, boolean isInCombat) {
        if (mininglevel < 41) {
            if (Irankis(1265)) {
                WorldPoint ministrant = new WorldPoint(Mining.getX() + randomOffset, Mining.getY() + randomOffset, 0);
                WorldPoint ministrant2 = new WorldPoint(Mining2.getX() + randomOffset, Mining2.getY() + randomOffset, 0);
                if (!atstumas(ministrant2, 20) && mininglevel < 15) {
                    if (!isAnimating && !isMoving && mininglevel < 10) {
                        skirsti("Tin rocks", randomSleep);
                    }
                    if (!isAnimating && !isMoving && mininglevel >= 10) {
                        skirsti("Copper rocks", randomSleep);
                    }
                } else if (!atstumas(ministrant, 20) && mininglevel >= 15) {
                    if (!isAnimating && !isMoving && mininglevel < 20) {
                        skirsti("Iron rocks", randomSleep);
                    } else if (!isAnimating && !isMoving && !isInCombat) {
                        skirsti("Silver rocks", randomSleep);
                    }
                    else if(isInCombat){
                        activityManager.chooseNewActivity();
                    }
                } else if (atstumas(ministrant2, 20) && mininglevel < 15) {
                    Rs2Walker.walkTo(ministrant2);
                } else {
                    Rs2Walker.walkTo(ministrant);
                }

            } else {
                uzsidet(1265, randomSleep, "Bronze pickaxe", 400);
            }
        } else {
            activityManager.chooseNewActivity();
        }
    }
    public void doQuest(int randomOffset) {
        if (qState(Rs2Player.getQuestState(Quest.COOKS_ASSISTANT).toString(), QuestState.NOT_STARTED.toString())) {
            if (Rs2Inventory.contains(1925) && !Rs2Inventory.contains(1927)) {
                WorldPoint milk = new WorldPoint(3255, 3272, 0);
                if (atstumas(milk, 4)) {
                    Rs2Walker.walkTo(milk);
                } else {
                    Rs2GameObject.interact("Dairy cow");
                    sleep(300, 800);
                    sleep(300, 800);
                    sleep(300, 800);
                    sleep(300, 800);
                    sleep(300, 800);
                }
            } else if (!Rs2Inventory.contains(1925) && !Rs2Inventory.contains(1927)) {
                Rs2Bank.walkToBank();
                if (!Rs2Bank.isOpen()) {
                    Rs2Bank.openBank();
                    sleep(300, 800);
                    Rs2Bank.depositAll();
                    sleep(300, 800);
                    Rs2Bank.setWithdrawAsItem();
                    sleep(300, 800);
                    Rs2Bank.withdrawOne(1925);
                    sleep(300, 800);
                    Rs2Bank.withdrawOne(1931);
                    sleep(300, 800);
                    Rs2Bank.closeBank();
                }
            }
            if (Rs2Inventory.contains(1927) && !Rs2Inventory.contains(1944)) {
                WorldPoint egg = new WorldPoint(3231, 3297, 0);
                if (atstumas(egg, 4)) {
                    Rs2Walker.walkTo(egg);
                    sleep(300, 800);
                    Rs2GroundItem.loot(1944);
                }
            }
            if (Rs2Inventory.contains(1927) && Rs2Inventory.contains(1944) && !Rs2Inventory.contains(1947) && wheattt) {
                WorldPoint wheat = new WorldPoint(3162, 3292, 0);
                if (!atstumas(wheat, 5)) {
                    Rs2GameObject.interact(15507, "Pick");
                    sleep(300, 800);
                    sleep(300, 800);
                    sleep(300, 800);
                    wheattt = false;
                } else {
                    Rs2Walker.walkTo(wheat);
                }
            }
            if (Rs2Inventory.contains(1927) && Rs2Inventory.contains(1944) && !wheattt && !Rs2Inventory.contains(1933)) {
                WorldPoint malunas = new WorldPoint(3165, 3308, 2);
                WorldPoint malunas2 = new WorldPoint(3166, 3305, 0);
                if (Rs2Inventory.contains(1947)) {
                    if (atstumas(malunas, 3)) {
                        Rs2Walker.walkTo(malunas);
                    } else {
                        Rs2GameObject.interact(24961);
                        sleep(3000, 8000);
                        sleep(3000, 8000);
                        Rs2GameObject.interact(24964);
                    }
                } else if (!Rs2Inventory.contains(1947)) {
                    if (atstumas(malunas2, 3)) {
                        Rs2Walker.walkTo(malunas2);
                    } else {
                        Rs2GameObject.interact(1781);
                    }
                }

            }
            if (Rs2Inventory.contains(1933)) {
                WorldPoint zmogus = new WorldPoint(3207, 3213, 0);
                if (atstumas(zmogus, 4)) {
                    Rs2Walker.walkTo(zmogus);
                    sleep(300, 800);
                    Rs2Npc.interact(4626);
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickOption("What's wrong?");
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickOption("Yes.");
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);

                }
            }
        } else if (qState(Rs2Player.getQuestState(Quest.SHEEP_SHEARER).toString(), QuestState.NOT_STARTED.toString())) {
            if (!Rs2Inventory.contains(1735)) {
                WorldPoint farmeris = new WorldPoint(3189, 3273, 0);
                WorldPoint rfarmeris = new WorldPoint(farmeris.getX() + randomOffset, farmeris.getY() + randomOffset, 0);
                Rs2NpcModel fermer = Rs2Npc.getNpc(732);
                if (atstumas(farmeris, 5) && Rs2Inventory.isEmpty()) {
                    Rs2Walker.walkTo(rfarmeris);
                    sleep(300, 800);
                    Rs2Walker.walkTo(fermer.getWorldLocation());
                    sleep(300, 800);
                    Rs2Npc.interact(fermer);
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickOption("I'm looking for a quest.");
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickOption("Yes.");
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);

                } else if (!Rs2Inventory.isEmpty()) {
                    Rs2Bank.walkToBank();
                    if (!Rs2Bank.isOpen()) {
                        Rs2Bank.openBank();
                        sleep(300, 800);
                        Rs2Bank.depositAll();
                        sleep(300, 800);
                        Rs2Bank.closeBank();
                    }
                }
            }
            if (Rs2Inventory.contains(1735) && !(Rs2Inventory.count(1737) == 20) && !Rs2Inventory.contains(1759)) {
                WorldPoint avis = new WorldPoint(3201, 3263, 0);
                if (atstumas(avis, 12)) {
                    Rs2Walker.walkTo(avis);
                } else if (Rs2Inventory.count(1737) < 20) {
                    Rs2NpcModel sheep = getAvailableSheep();
                    Rs2Npc.interact(sheep);
                    sleep(300, 800);
                }
            }
            if (Rs2Inventory.count(1737) == 20) {
                WorldPoint kazkas = new WorldPoint(3209, 3213, 1);
                if (atstumas(kazkas, 3)) {
                    Rs2Walker.walkTo(kazkas);
                } else {
                    GameObject spinningWheel = Rs2GameObject.get("Spinning wheel"); // Find the nearest spinning wheel

                    if (spinningWheel != null) {
                        Rs2GameObject.interact("Spinning wheel", "Spin");
                        sleep(3000, 8000);
                        Rs2Keyboard.keyPress(VK_SPACE);
                        sleep(30000, 80000);
                    }
                }
            }
            if (Rs2Inventory.contains(1759)) {
                WorldPoint farmeris = new WorldPoint(3189, 3273, 0);
                WorldPoint rfarmeris = new WorldPoint(farmeris.getX() + randomOffset, farmeris.getY() + randomOffset, 0);
                Rs2NpcModel fermer = Rs2Npc.getNpc(732);
                if (atstumas(farmeris, 8)) {
                    Rs2Walker.walkTo(rfarmeris);
                } else {
                    Rs2Walker.walkTo(fermer.getWorldLocation());
                    sleep(300, 800);
                    Rs2Npc.interact(732);
                    sleep(300, 800);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                    Rs2Dialogue.clickContinue();
                    sleep(3000, 8000);
                }
            }
        } else {
            activityManager.chooseNewActivity();
        }
    }
    public boolean qState(String state, String state1) {
        return state.equals(state1);
    }
    public void smith(int randomOffset) {
        WorldPoint randomSmith = new WorldPoint(smith_bank.getX() + randomOffset, smith_bank.getY() + randomOffset, 0);

        WorldPoint randoSmith = new WorldPoint(smith.getX() + randomOffset, smith.getY() + randomOffset, 0);
        if (!atstumas(randomSmith, 20)) {
            if (!Rs2Inventory.contains(436) && !Rs2Inventory.contains(438)) {
                if(Rs2Bank.isOpen()) {
                    if (Rs2Bank.hasItem(436) && Rs2Bank.hasItem(438)) {
                        Rs2Bank.depositAll();
                        sleep(300, 3000);
                        Rs2Bank.withdrawX(436, 14);
                        sleep(300, 3000);
                        Rs2Bank.withdrawX(438, 14);
                        sleep(300, 3000);
                    }
                    else{
                        activityManager.chooseNewActivity();
                    }
                }
                else {
                    Rs2Bank.walkToBankAndUseBank();
                }
            } else if (Rs2Inventory.contains(436) && Rs2Inventory.contains(438)) {
                GameObject furnace = Rs2GameObject.get("Furnace");
                Rs2Walker.walkTo(randoSmith);

                if (furnace != null) {
                    Rs2GameObject.interact("Furnace", "Smelt");
                    sleep(3000, 8000);
                    Rs2Keyboard.keyPress(VK_SPACE);
                    sleep(40000, 50000);
                }
            }
            else if(!Rs2Inventory.contains(436) && !Rs2Inventory.contains(438)){
                activityManager.chooseNewActivity();
            }
        } else {
            Rs2Walker.walkTo(randomSmith);
        }

    }
    public void smithing(int randomOffset) {
        if(Rs2Inventory.contains(2347)){
            if(Rs2Inventory.contains(2349)){
                Rs2Walker.walkTo(smithh);
                sleep(300, 800);
                GameObject Anvil = Rs2GameObject.get("Anvil");
                if (Anvil != null) {
                    Rs2GameObject.interact("Anvil", "Smith");
                    sleep(3000, 8000);
                    Rs2Widget.clickWidget(20447246);
                    sleep(40000, 50000);
                }

            }
            if(!Rs2Inventory.contains(2349)){
                Rs2Bank.walkToBank();
                if(Rs2Bank.isOpen()){
                    if(Rs2Bank.hasItem(2349)) {
                        sleep(300, 1000);
                        Rs2Bank.depositAllExcept(2347);
                        sleep(300, 1000);
                        Rs2Bank.withdrawX(2349, 27);
                    }
                    else{
                        activityManager.chooseNewActivity();
                    }
                }
                else{
                    Rs2Bank.openBank();
                }
            }
        }
        else{
            if(Rs2Bank.isOpen()){
                if(Rs2Bank.hasItem(2347)) {
                    Rs2Bank.withdrawOne(2347);
                    sleep(300, 1000);
                    Rs2Bank.closeBank();
                }
                else if(Rs2Bank.hasBankItem("Coins", 500)){
                    Rs2Bank.closeBank();
                    sleep(300, 8000);
                    Rs2GrandExchange.walkToGrandExchange();
                    if (!Rs2GrandExchange.isOpen()) {
                        Rs2GrandExchange.openExchange();
                        sleep(300, 8000);
                        Rs2GrandExchange.buyItem("hammer","hammer", 500,1);
                        sleep(3000, 8000);
                        Rs2GrandExchange.collectToInventory();
                        sleep(300, 1000);
                        Rs2GrandExchange.closeExchange();
                        sleep(300, 1000);
                    }

                }
                else{
                    activityManager.chooseNewActivity();
                }
            }
            else{
                Rs2Bank.walkToBank();
                sleep(300,800);
                Rs2Bank.openBank();
            }

        }
    }
    public void fishing(int randomOffset, int fishinglevel, boolean isAnimating, boolean isMoving) {
         if(fishinglevel < 20){
             if(Rs2Inventory.contains(303) && !Rs2Inventory.isFull()) {
                 WorldPoint randofish = new WorldPoint(fish.getX() + randomOffset, fish.getY() + randomOffset, 0);
                 if(!atstumas(randofish, 12)){
                     if(!isAnimating && !isMoving){
                         Rs2Npc.interact(1530);
                         sleep(100, 1000);
                     }
                 }
                 else{
                     Rs2Walker.walkTo(randofish);
                 }
             }
             else{
                if(!Rs2Bank.isOpen()){
                    Rs2Bank.walkToBank();
                    sleep(100, 1000);
                    Rs2Bank.openBank();
                }
                else{
                    if(Rs2Inventory.contains(303)){
                        Rs2Bank.depositAllExcept(303);
                        sleep(100, 1000);
                        Rs2Bank.closeBank();
                    }
                    else{
                        Rs2Bank.depositAll();
                        sleep(100, 1000);
                        Rs2Bank.withdrawOne(303);
                        sleep(100, 1000);
                        Rs2Bank.closeBank();
                    }

                }
             }

    }
         else{
             activityManager.chooseNewActivity();
         }

    }
    public void firemaking(int randomOffset, int firelevel, boolean isAnimating, boolean isMoving, int randomsleep){
if(firelevel < 30){
    if(Irankis(1351)){
    if(Rs2Inventory.contains(590) && !Rs2Inventory.isFull()){
        WorldPoint randomfire = new WorldPoint(fireplace.getX() + randomOffset, fireplace.getY() + randomOffset, 0);
        if (!atstumas(randomfire, 20)) {
            if (!isAnimating && !isMoving && !Rs2Inventory.contains(1511)) {
                kirsti(1276, randomsleep);
            }
            else if(!isAnimating && !isMoving){
                GameObject lauzas = Rs2GameObject.get("Fire");
                if(!Rs2Player.getWorldLocation().equals(lauzas.getWorldLocation())) {
                    Rs2Inventory.interact(590, "Use");
                    sleep(randomsleep);
                    Rs2Inventory.interact(1511);
                }
                else {
                    Rs2Walker.walkTo(randomfire);
                }
                }
        } else {


            Rs2Walker.walkTo(randomfire);
        }
    }
    else{
        if(!Rs2Bank.isOpen()){
            Rs2Bank.walkToBank();
            sleep(100, 1000);
            Rs2Bank.openBank();
        }
        else{
            if(Rs2Inventory.contains(590)){
                Rs2Bank.depositAllExcept(590);
                sleep(100, 1000);
                Rs2Bank.closeBank();
            }
            else{
                Rs2Bank.depositAll();
                sleep(100, 1000);
                Rs2Bank.withdrawOne(590);
                sleep(100, 1000);
                Rs2Bank.closeBank();
            }

        }
    }
    }
    else{
        uzsidet(1351, 500, "Bronze axe", 400);
    }
}
else {
    activityManager.chooseNewActivity();
}
    }
}



