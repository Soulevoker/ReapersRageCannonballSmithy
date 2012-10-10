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

import static org.reapersrage.reaperscbsmithy.script.ReapersCBSmithy.status;

/**
 * Created by IntelliJ IDEA.
 * User: Kyle
 * Date: 5/2/12
 * Time: 10:30 PM
 */
public class BankingForBars extends Node {

    @Override
    public boolean activate() {
        return Inventory.getCount(Constants.STEEL_BAR_ID) < 1 && Bank.getNearest().isOnScreen();
    }

    @Override
    public void execute() {
        status = "Banking";
        if (Constants.BANK_CHAT_WIDGET.validate()) {
            Widgets.clickContinue();
            return;
        }
        if (Bank.open()) {
            if (Bank.getItemCount(true, Constants.STEEL_BAR_ID) < 1) {
                Bank.depositInventory();
                Task.sleep(500, 1000);
                ReapersCBSmithy.getInstance().shutdown();
            }
            if (Inventory.getCount() > 0) {
                //Deposit
                Bank.depositInventory();
            } else {
                //Withdraw
                Bank.withdraw(Constants.STEEL_BAR_ID, 0);
                Task.sleep(400);
            }
        } else {
            //Open Bank
            Bank.open();
        }
    }
}
