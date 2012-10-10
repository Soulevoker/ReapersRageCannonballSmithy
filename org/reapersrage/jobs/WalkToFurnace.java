/*
 * Copyright (c) 2012. Reapers Rage LLC.
 */

package org.reapersrage.reaperscbsmithy.jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.reapersrage.reaperscbsmithy.resources.Constants;
import org.reapersrage.reaperscbsmithy.resources.Variables;

import static org.reapersrage.reaperscbsmithy.script.ReapersCBSmithy.*;

/**
 * Created by IntelliJ IDEA.
 * User: Kyle
 * Date: 5/4/12
 * Time: 2:53 PM
 */
public class WalkToFurnace extends Node {

    SceneObject furnace = SceneEntities.getNearest(Constants.FURNACE_IDS);


    @Override
    public boolean activate() {
        return (Inventory.getCount(Constants.STEEL_BAR_ID) > 1 || (Inventory.getCount(Constants.IRON_ORE_ID) > 1 && Inventory.getCount(Constants.COAL_ID) > 2))
                && (Calculations.distanceTo(furnace.getLocation()) > 4 | !furnace.isOnScreen());
    }

    @Override
    public void execute() {
        SceneObject PORTPDOOR = SceneEntities.getAt(new Tile(3684, 3476, 0));
        status = "Walking to Furnace";
        if (!isInventoryNotChanged()) {
            setNotChanged(true);
        }
        switch (Variables.bank) {
            case AKBANK:
                if (furnace.isOnScreen()) {
                    interrupt();
                }
                if (Players.getLocal().isMoving()) {
                    Task.sleep(150);
                }
                Walking.newTilePath(Constants.AKBTOF).randomize(2, 2).traverse();
                break;
            case EVBANK:
                if (furnace.isOnScreen()) {
                    interrupt();
                }
                if (Players.getLocal().isMoving()) {
                    Task.sleep(150);
                }
                Walking.walk(Constants.EVFURNACETILE);
                break;
            case PORTP:
                if (PORTPDOOR != null) {
                    Walking.walk(new Tile(3684, 3476, 0));
                    PORTPDOOR.interact("Open");
                    Task.sleep(700, 1000);
                } else {
                    Walking.walk(Constants.PPFURNACETILE);
                }
                break;
        }
    }
}

