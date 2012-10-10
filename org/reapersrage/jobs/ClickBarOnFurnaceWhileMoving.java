package org.reapersrage.reaperscbsmithy.jobs;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.reapersrage.reaperscbsmithy.resources.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: Lennie
 * Date: 10/8/12
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClickBarOnFurnaceWhileMoving extends Node {

    SceneObject furnace;

    @Override
    public boolean activate() {
        furnace = SceneEntities.getNearest(Constants.FURNACE_IDS);
        return Players.getLocal().isMoving() && furnace != null && furnace.isOnScreen();
    }

    @Override
    public void execute() {
        Item bar = Inventory.getItem(Constants.STEEL_BAR_ID);
        if (bar != null) {
            if (Inventory.getSelectedItem() != bar) {
                Inventory.selectItem(bar);
                furnace.interact("Smelt");
            } else {
                furnace.interact("Smelt");
            }
            Task.sleep(Random.nextInt(500, 1200));
        }
    }
}
