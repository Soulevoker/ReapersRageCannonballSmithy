/*
 * Copyright (c) Reapers' Rage 2005-2012.
 */

package org.reapersrage.reaperscbsmithy.jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.reapersrage.reaperscbsmithy.resources.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: Lennie
 * Date: 10/8/12
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class SmithCannonballs extends Node {
    SceneObject furnace;

    @Override
    public boolean activate() {
        furnace = SceneEntities.getNearest(Constants.FURNACE_IDS);
        if (!Players.getLocal().isMoving() && furnace.isOnScreen() && !Bank.isOpen() && Inventory.getCount(Constants.STEEL_BAR_ID) > 0) {
            if (Inventory.getCount(Constants.IRON_ORE_ID) <= 1 && Inventory.getCount(Constants.COAL_ID) < 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        Item bar = Inventory.getItem(Constants.STEEL_BAR_ID);
        if (furnace != null) {
            if (Constants.SMITH_WIDGET.validate()) {
                if (Constants.BALL_WIDGET.interact("Make")) {
                    Mouse.move(Random.nextInt(0, 515), Random.nextInt(51, 370));
                    final Timer timer = new Timer(2000);
                    while (timer.isRunning() && !getContainer().isShutdown()) {
                        if (Players.getLocal().getAnimation() != -1) {
                            timer.reset();
                        }
                        if (Inventory.getCount(Constants.STEEL_BAR_ID) < 1) {
                            break;
                        }
                        Task.sleep(Random.nextInt(100, 250));
                    }
                }
            } else {
                if (Inventory.getSelectedItem() != bar) {
                    Inventory.selectItem(bar);
                    Task.sleep(Random.nextInt(350, 500));
                    furnace.interact("Use");
                    Task.sleep(Random.nextInt(500, 950));
                } else {
                    furnace.interact("Use");
                    Task.sleep(Random.nextInt(200, 950));
                }
            }
        }
    }
}
