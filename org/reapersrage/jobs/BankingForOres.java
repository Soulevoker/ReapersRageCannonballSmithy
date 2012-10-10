/*
 * Copyright (c) Reapers' Rage 2005-2012.
 */

package org.reapersrage.reaperscbsmithy.jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.reapersrage.reaperscbsmithy.resources.Constants;
import org.reapersrage.reaperscbsmithy.script.ReapersCBSmithy;

/**
 * Created with IntelliJ IDEA.
 * User: Lennie
 * Date: 10/8/12
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BankingForOres extends Node {
    @Override
    public boolean activate() {
        return (Inventory.getCount(Constants.STEEL_BAR_ID) < 1 || (Inventory.getCount(Constants.IRON_ORE_ID) < 1 && Inventory.getCount(Constants.COAL_ID) < 2))
                && Bank.getNearest().isOnScreen();
    }

    @Override
    public void execute() {
        int ironCount = Inventory.getCount(Constants.IRON_ORE_ID);
        if (Constants.BANK_CHAT_WIDGET.validate()) {
            Widgets.clickContinue();
            return;
        }

        if (Bank.open()) {
            if (Bank.getItemCount(true, Constants.IRON_ORE_ID) < 1 || Bank.getItemCount(true, Constants.COAL_ID) < 2) {
                System.out.println("Not enough Materials!");
                Bank.depositInventory();
                Task.sleep(500, 100);
                ReapersCBSmithy.getInstance().stop();
            }
            if (Inventory.getCount(Constants.COAL_ID) > 18) {
                Bank.deposit(Constants.COAL_ID, Bank.Amount.ALL);
            }
            Task.sleep(600);
            //Withdraw
            //if (Inventory.getCount(Constants.BALL_ID) == 0) {
/*                Bank.deposit(Constants.IRON_ORE_ID, Bank.Amount.ALL);
                Bank.withdraw(Constants.IRON_ORE_ID, 9);
                Task.sleep(500);
                Bank.withdraw(Constants.COAL_ID, 18);
            } else {*/
            if (ironCount > 9) {
                Bank.deposit(Constants.IRON_ORE_ID, ironCount);
            }
            Bank.withdraw(Constants.IRON_ORE_ID, 9 - ironCount);
            Task.sleep(500);
            Bank.withdraw(Constants.COAL_ID, Bank.Amount.ALL);
            // }

        } else {
            //Open Bank
            Bank.open();
            Task.sleep(600);
        }
    }
}
