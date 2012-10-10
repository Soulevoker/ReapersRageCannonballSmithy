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
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class SmithBars extends Node {
    SceneObject furnace;

    @Override
    public boolean activate() {
        furnace = SceneEntities.getNearest(Constants.FURNACE_IDS);
        return !Players.getLocal().isMoving() && furnace.isOnScreen() && Inventory.getCount(Constants.IRON_ORE_ID) > 0 && Inventory.getCount(Constants.COAL_ID) > 1 && !Bank.isOpen();
    }

    @Override
    public void execute() {
        Item ironOre = Inventory.getItem(Constants.IRON_ORE_ID);
        if (furnace != null) {
            if (Constants.SMITH_WIDGET.validate()) {
                if (Constants.BAR_WIDGET.validate()) {
                    if (Constants.BAR_WIDGET.interact("Make")) {
                        Mouse.move(Random.nextInt(0, 515), Random.nextInt(51, 370));
                        final Timer timer = new Timer(2000);
                        while (timer.isRunning() && !getContainer().isTerminated()) {
                            if (Players.getLocal().getAnimation() != -1) {
                                timer.reset();
                            }
                            if (Inventory.getCount(Constants.IRON_ORE_ID) < 1 || Inventory.getCount(Constants.COAL_ID) < 2) {
                                break;
                            }
                        }
                        System.out.println("Outside timer");
                        Task.sleep(Random.nextInt(100, 250));
                    }
                } else {
                    furnace.interact("Smelt");
                    Task.sleep(Random.nextInt(500, 920));
                }
            } else {
                furnace.interact("Smelt");
                Task.sleep(Random.nextInt(500, 920));
            }
        }
    }
}
