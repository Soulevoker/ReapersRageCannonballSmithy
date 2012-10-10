/*
 * Copyright (c) 2012. Reapers Rage LLC.
 */

package org.reapersrage.reaperscbsmithy.jobs;

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.reapersrage.reaperscbsmithy.resources.Constants;
import org.reapersrage.reaperscbsmithy.resources.Variables;
import org.reapersrage.reaperscbsmithy.script.ReapersCBSmithy;

import java.awt.*;


/**
 * Created by IntelliJ IDEA.
 * User: Kyle
 * Date: 5/1/12
 * Time: 10:37 PM
 */
public class WalkToBank extends Node implements PaintListener {

    SceneObject portPDoor;

    @Override
    public boolean activate() {
        if (Inventory.getCount(Constants.STEEL_BAR_ID) < 1 || (Inventory.getCount(Constants.IRON_ORE_ID) < 1 && Inventory.getCount(Constants.COAL_ID) < 2)) {
            if (!Variables.bank.getArea().contains(Players.getLocal())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute() {
        portPDoor = SceneEntities.getAt(new Tile(3684, 3476, 0));
        ReapersCBSmithy.status = "Walking to Bank";
        while (Players.getLocal().isMoving()) {
            Task.sleep(150);
        }
        /*if (Calculations.distanceTo(Constants.AKBANKTILE) > 10) {
            Walking.findPath(Constants.AKBANKTILE).traverse();
            return;
        }*/
        switch (Variables.bank) {
            case PORTP:
                if (Constants.PPFURNACEAREA.contains(Players.getLocal())) {
                    if (portPDoor != null) {
                        if (portPDoor.isOnScreen()) {
                            if (portPDoor.interact("Open")) {
                                Task.sleep(700, 1250);

                            }
                        } else {
                            Camera.turnTo(portPDoor);
                        }
                    } else {
                        Walking.walk(Constants.PPBANKTILE);
                    }
                }
                break;
            case AKBANK:
                Walking.walk(Constants.AKBANKTILE);
                //Walking.newTilePath(akBank.getPath()).traverse();
                break;
            case EVBANK:
                Walking.walk(Constants.EVBANKTILE);
                //Walking.findPath(Constants.EVBANKTILE).traverse();
                break;
        }
    }

    @Override
    public void onRepaint(Graphics graphics) {
        if (portPDoor != null && portPDoor.isOnScreen()) {
            graphics.setColor(new Color(80, 157, 255, 30));
            //portPDoor.draw(graphics);
            portPDoor.getModel().draw(graphics);
        }
    }
}
