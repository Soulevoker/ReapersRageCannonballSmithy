/*
 * Copyright (c) Reapers' Rage 2005-2012.
 */

package org.reapersrage.reaperscbsmithy.jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.reapersrage.reaperscbsmithy.resources.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: Lennie
 * Date: 10/8/12
 * Time: 11:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClickOreOnFurnaceWhileMoving extends Node {
    SceneObject furnace;

    @Override
    public boolean activate() {
        furnace = SceneEntities.getNearest(Constants.FURNACE_IDS);
        if (Players.getLocal().isMoving()) {
            if (furnace != null && furnace.isOnScreen() && Inventory.getCount(Constants.IRON_ORE_ID) > 1 && Inventory.getCount(Constants.COAL_ID) > 2 && !Constants.SMITH_WIDGET.validate()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        furnace.interact("Smelt");
        Task.sleep(Random.nextInt(700, 1350));
    }
}
