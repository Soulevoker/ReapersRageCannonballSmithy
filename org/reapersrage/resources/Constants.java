/*
 * Copyright (c) 2012. Reapers Rage LLC.
 */

package org.reapersrage.reaperscbsmithy.resources;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 * Created by IntelliJ IDEA.
 * User: Kyle
 * Date: 4/29/12
 * Time: 9:47 PM
 */
public class Constants {
    public final static Widget BANK_CHAT_WIDGET = new Widget(1186);
    public final static Widget SMITH_WIDGET = new Widget(905);
    public final static WidgetChild BALL_WIDGET = new WidgetChild(SMITH_WIDGET, 14);
    public final static WidgetChild BAR_WIDGET = new WidgetChild(SMITH_WIDGET, 18);
    public final static int STEEL_BAR_ID = 2353;
    public final static int[] FURNACE_IDS = {26814, 11666};
    public final static int BALL_ID = 2;
    public final static int IRON_ORE_ID = 440;
    public final static int COAL_ID = 453;

    //Declares The path in Alkharid from the Bank to Furnace
    public static final Tile[] AKBTOF = {
            new Tile(3269, 3167, 0), new Tile(3274, 3167, 0),
            new Tile(3277, 3171, 0), new Tile(3279, 3176, 0),
            new Tile(3280, 3181, 0), new Tile(3281, 3186, 0),
            new Tile(3276, 3186, 0)
    };

    //Declares The path in Edgeville from the Bank to Furnace
    public static final Tile[] EVBTOF = {
            new Tile(3095, 3496, 0), new Tile(3100, 3498, 0),
            new Tile(3105, 3499, 0)
    };

    public static final Tile[] PPFTOB = {
            new Tile(3684, 3478, 0), new Tile(3681, 3478, 0), new Tile(3681, 3475, 0),
            new Tile(3684, 3473, 0), new Tile(3687, 3473, 0), new Tile(3690, 3471, 0),
            new Tile(3689, 3468, 0)
    };

    //Declares the Bank areas
    public final static Area PPBANKAREA = new Area(new Tile(3681, 3479, 0), new Tile(3684, 3472, 0), new Tile(3691, 3472, 0), new Tile(3691, 3460, 0),
            new Tile(3684, 3460, 0), new Tile(3684, 3472, 0));
    public final static Area EVBANKAREA = new Area(new Tile(3089, 3500, 0), new Tile(3098, 3500, 0), new Tile(3098, 3487, 0),
            new Tile(3089, 3487, 0));

    public static final Area AKBANKAREA = new Area(new Tile(3267, 3174, 0), new Tile(3267, 3160, 0), new Tile(3273, 3160, 0),
            new Tile(3273, 3174, 0), new Tile(3267, 3174, 0));

    //Declares Port Phastymas' Furnace area so we know when to open the door
    public static final Area PPFURNACEAREA = new Area(new Tile(3680, 3483, 0), new Tile(3690, 3483, 0), new Tile(3690, 3475, 0),
            new Tile(3680, 3475, 0), new Tile(3680, 3483, 0));

    //Bank Tiles Denoted by first two characters
    public final static Tile AKFURNACETILE = new Tile(3276, 3186, 0);
    public final static Tile EVFURNACETILE = new Tile(3108, 3500, 0);
    public final static Tile PPFURNACETILE = new Tile(3686, 3478, 0);
    public final static Tile AKBANKTILE = new Tile(3269, 3167, 0);
    public final static Tile EVBANKTILE = new Tile(3097, 3496, 0);
    public final static Tile PPBANKTILE = new Tile(3690, 3466, 0);

    public static enum SMITHING_STAGES {
        ONE_STAGE, TWO_STAGES
    }

    public static enum BANK {
        PORTP(PPBANKAREA),
        AKBANK(AKBANKAREA),
        EVBANK(EVBANKAREA);
        private Area bankArea;

        private BANK(Area bankArea) {
            this.bankArea = bankArea;
        }

        public Area getArea() {
            return this.bankArea;
        }
    }

    /*public static SceneObject furnace() {
        return SceneEntities.getNearest(new Filter<SceneObject>() {
            public boolean accept(SceneObject entity) {
                for (int i = 0; i < Constants.FURNACE_IDS.length; i++) {
                    if (entity.getId() == Constants.FURNACE_IDS[i])
                        return true;
                }
                return false;
            }
        });
    }

    private enum WIDGETS {
        MAKE_ALL(905, 14);
        private int id;
        private int childId;

        private WIDGETS(int id, int childId) {
            this.id = id;
            this.childId = childId;
        }

        public int getId() {
            return this.id;
        }

        public int getChildId() {
            return this.childId;
        }

        public WidgetChild get() {
            return Widgets.get(this.id, this.childId);
        }
    }*/
}
