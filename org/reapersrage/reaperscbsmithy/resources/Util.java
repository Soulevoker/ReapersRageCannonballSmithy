/*
 * Copyright (c) 2012. Reapers Rage LLC.
 */

package org.reapersrage.reaperscbsmithy.resources;

import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Kyle
 * Date: 5/1/12
 * Time: 10:29 PM
 */
public class Util {

    public static int random(int range) {
        return (int) (Math.random() * (range + 1));
    }

    public void log(String taskName, Level level, String message) {
        Logger logger = Logger.getLogger(taskName);
        logger.log(level, message);
    }

    /**
     * @param itemId passes the item id to the method that you want the price for
     * @return the itemId's price
     */
    public static int getItemPrice(int itemId) {
        String price = "0";
        String line;
        try {
            //Open the Runescape Grandexchange website and lookup the item
            URL itemUrl = new URL("http://services.runescape.com/m=itemdb_rs/viewitem.ws?obj=" + itemId);
            //load the site's stream
            BufferedReader br = new BufferedReader(new InputStreamReader(itemUrl.openStream()));
            //loop until the current guide price is reached
            while ((line = br.readLine()) != null) {
                if (line.contains("Current guide price:")) {
                    break;
                }
            }
            //get the price and finally remove ',' and </td> so we can get a numeric value
            price = br.readLine().substring(4).replace(",", "").replace("</td>", "");
            //print the value to the console. Mainly for debugging
            System.out.println("Item " + itemId + " costs " + price + " per unit.");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(price);
    }

    /**
     * Returns an array representing the state of the inventory.<br>
     * This function will always return an array of length 28.
     *
     * @param cached if true opens the inventory tab, if false it uses the last seen representation of the inventory.
     * @return an array representing the inventory.
     */
    public static Item[] getAllItems(final boolean cached) {
        final Item[] items = new Item[28];
        final WidgetChild inventoryWidget = getWidget(cached);
        if (inventoryWidget != null) {
            final WidgetChild[] inventoryChildren = inventoryWidget.getChildren();
            if (inventoryChildren.length >= items.length) {
                for (int i = 0; i < items.length; i++) {
                    final WidgetChild wc = inventoryChildren[i];
                    items[i] = (wc == null || wc.getChildId() == -1) ? null : new Item(wc);
                }
            }
        }
        return items;
    }

    public static final int WIDGET = 679;
    public static final int WIDGET_BANK = 763;
    public static final int WIDGET_PRICE_CHECK = 204;
    public static final int WIDGET_EQUIPMENT_BONUSES = 670;
    public static final int WIDGET_EXCHANGE = 644;
    public static final int WIDGET_SHOP = 621;
    public static final int WIDGET_DUNGEONEERING_SHOP = 957;
    public static final int WIDGET_BEAST_OF_BURDEN_STORAGE = 665;

    public static final int[] ALT_WIDGETS = {
            WIDGET_BANK,
            WIDGET_PRICE_CHECK, WIDGET_EQUIPMENT_BONUSES,
            WIDGET_EXCHANGE, WIDGET_SHOP, WIDGET_DUNGEONEERING_SHOP,
            WIDGET_BEAST_OF_BURDEN_STORAGE
    };

    public static WidgetChild getWidget(final boolean cached) {
        for (final int widget : ALT_WIDGETS) {
            WidgetChild inventory = Widgets.get(widget, 0);
            if (inventory != null && inventory.getAbsoluteX() > 50) {
                return inventory;
            }
        }
        if (!cached) {
            Tabs.INVENTORY.open(true);
        }
        return Widgets.get(WIDGET, 0);
    }
}
