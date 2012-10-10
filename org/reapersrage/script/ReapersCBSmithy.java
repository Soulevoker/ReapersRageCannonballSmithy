/*
 * Copyright (c) 2012. Reapers Rage LLC.
 */

package org.reapersrage.reaperscbsmithy.script;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.net.GeItem;
import org.powerbot.game.api.wrappers.node.Item;
import org.reapersrage.reaperscbsmithy.jobs.*;
import org.reapersrage.reaperscbsmithy.resources.Constants;
import org.reapersrage.reaperscbsmithy.resources.Variables;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by IntelliJ IDEA.
 * User: Kyle
 * Date: 4/29/12
 * Time: 9:48 PM
 */

@Manifest(authors = {"Soulevoker"},
        name = "ReapersCBSmithy",
        description = "Smiths cannonballs with timely precision.\nUpdated: 30/9 Version 1.14!",
        version = 1.14,
        website = "http://www.powerbot.org/community/topic/781764-reapers-rage-cannonball-smithy/",
        topic = 817109)
public class ReapersCBSmithy extends ActiveScript implements PaintListener, MouseListener, MessageListener {
    private static ActiveScript instance;
    private Tree smithing_nodes = null;
    public static final Manifest properties = ReapersCBSmithy.class.getAnnotation(Manifest.class);
    public static String status = "Starting up";
    private long runTime, seconds, minutes, hours, secToLevel, minutesToLevel, hoursToLevel;
    public static int orePrice, coalPrice, barPrice, ballPrice;
    private int profit, ballsMade, ammoHour, currentXP, currentLVL, gainedXP,
            gainedLVL, xpPerHour, profitHour, expToLevel, startXP, startLvl;
    private float secExp, minuteExp, hourExp;
    private static boolean osIsMacOsX;

    public static void setStartTime(long time) {
        startTime = time;
    }

    private static long startTime;

    private boolean hidePaint = false;

    public static boolean isInventoryNotChanged() {
        return inventoryNotChanged;
    }

    public static boolean inventoryNotChanged = true;

    private static Item[] invItems;

    public static Item[] getInvItems() {
        return invItems;
    }

    public static void setInvItems(Item[] invItems) {
        ReapersCBSmithy.invItems = invItems;
    }

    public static void setNotChanged(Boolean flag) {
        inventoryNotChanged = flag;
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        String serverMessage = messageEvent.getMessage();
        if (serverMessage.contains("You remove")) {
            ballsMade += 4;
        }
    }
    /*@Override
    public int loop() {
        Node[] smithing_jobs = {
                new Startup(),
                new WalkToFurnace(),
                new Smith(),
                new AnimationCheck(),
                new WalkToBank(),
                new BankingForBars(),
                new Antiban()
        };
        if (smithing_nodes == null) {
            smithing_nodes = new Tree(smithing_jobs);
        }
        for (Node job : smithing_jobs) {
            final Node node = smithing_nodes.state();
            if (node != null && job.activate() && !job.isAlive()) {
                getContainer().submit(node);
                node.join();
            }
        }
       *//* final Node node = smithing_jobs.state();
        if (node != null) {
            smithing_jobs.set(node);
            getContainer().submit(node);
            node.join();
        }*//*
        return Random.nextInt(100, 250);
    }*/

    public int loop() {
        if (!Variables.Bools.isGuiFinished) {
            return Random.nextInt(350, 500);
        } else {
            //TODO: Antiban on new Thread
            if (smithing_nodes == null) {
                if (Variables.Bools.isUsingBars) {
                    smithing_nodes = new Tree(new Node[]{
                            new ClickBarOnFurnaceWhileMoving(),
                            new SmithCannonballs(),
                            new WalkToFurnace(),
                            new BankingForBars(),
                            new WalkToBank()
                    });
                } else {
                    smithing_nodes = new Tree(new Node[]{
                            new WalkToFurnace(),
                            new ClickOreOnFurnaceWhileMoving(),
                            new SmithBars(),
                            new SmithCannonballs(),
                            new WalkToBank(),
                            new BankingForOres()
                    });
                }
            }
            final Node node = smithing_nodes.state();
            log.config(smithing_nodes.state() + "");
            log.info(smithing_nodes.state() + "");
            if (node != null) {
                log.info(node + "");
                smithing_nodes.set(node);
                getContainer().submit(node);
                log.info(getContainer().getActiveCount() + "");
                node.join();
                log.info(node + "");
            }
        }
        return Random.nextInt(150, 350);
    }

    @Override
    public void onStart() {
        getContainer().submit(new Task() {

            @Override
            public void execute() {
                orePrice = GeItem.lookup(Constants.IRON_ORE_ID).getPrice();
                coalPrice = GeItem.lookup(Constants.COAL_ID).getPrice();
                ballPrice = GeItem.lookup(Constants.BALL_ID).getPrice();
                barPrice = GeItem.lookup(Constants.STEEL_BAR_ID).getPrice();
            }
        });
        log.info("Reapers Cannonball Smithy Initialized!!");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String os = System.getProperty("os.name");
                if (os != null)
                    os = os.toLowerCase();
                osIsMacOsX = "mac os x".equals(os);
                if (osIsMacOsX) {
                    MACGUI frame = new MACGUI();
                    frame.setVisible(true);
                } else {
                    GUI frame = new GUI();
                    frame.setVisible(true);
                }
            }
        });
        if (Game.isLoggedIn()) {
            startXP = Skills.getExperience(Skills.SMITHING);
            startLvl = Skills.getLevel(Skills.SMITHING);
        }
        instance = this;
    }

    @Override
    public void onStop() {
        log.info("Thank you for choosing our script =D");
        smithing_nodes = null;
    }


    public static ActiveScript getInstance() {
        return instance;
    }

    //START: Code generated using Enfilade's Easel
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private final Color color1 = new Color(222, 222, 222);
    private final Color color12 = new Color(255, 204, 204);
    private final Color outline = new Color(0, 0, 0);
    private final Color color2 = new Color(255, 255, 255, 0);

    private final Font font1 = new Font("Tahoma", Font.BOLD, 17);

    private final Image img1 = getImage("http://img15.imageshack.us/img15/2720/reapersv2jpe.jpg");
    private final Image img2 = getImage("http://services.runescape.com/m=itemdb_rs/3759_obj_sprite.gif?id=2");
    Graphics2D g;

    @Override
    public void onRepaint(Graphics g1) {
        final Point mouse = Mouse.getLocation();
        g = (Graphics2D) g1;
        g.setColor(Color.BLACK);
        g.drawLine(0, Mouse.getY(), (int) Game.getDimensions().getWidth(), Mouse.getY());
        g.setColor(Color.RED);
        g.drawLine(Mouse.getX(), 0, Mouse.getX(), Game.getBaseX());
        g.translate(0, 50);
        g.drawImage(img2, 521, 468, null);//cannonball
        if (Game.isLoggedIn() && !hidePaint) {
            Rectangle area = new Rectangle(0, 379, 524, 240);
            if (area.contains(mouse)) {
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
            if (startXP == 0) {
                startXP = Skills.getExperience(Skills.SMITHING);
                startLvl = Skills.getLevel(Skills.SMITHING);
            }
            getProfit();
            if (startTime != 0) {
                runTime = System.currentTimeMillis() - startTime;
            } else {
                runTime = 0;
            }
            seconds = runTime / 1000;
            if (seconds >= 60) {
                minutes = seconds / 60;
                seconds -= (minutes * 60);
            }
            if (minutes >= 60) {
                hours = minutes / 60;
                minutes -= (hours * 60);
            }
            seconds = runTime / 1000;
            if (seconds >= 60) {
                minutes = seconds / 60;
                seconds -= minutes * 60;
            }
            if (minutes >= 60) {
                hours = minutes / 60;
                minutes -= hours * 60;
            }

            if ((minutes > 0 || hours > 0 || seconds > 0) && gainedXP > 0) {
                secExp = (float) gainedXP
                        / (float) (seconds + minutes * 60 + hours * 60 * 60);
            }
            minuteExp = secExp * 60;
            hourExp = minuteExp * 60;
            currentLVL = Skills.getLevel(Skills.SMITHING);
            expToLevel = Skills.getExperienceToLevel(Skills.SMITHING, currentLVL + 1);
            currentXP = Skills.getExperience(Skills.SMITHING);
            gainedXP = currentXP - startXP;
            gainedLVL = currentLVL - startLvl;
            xpPerHour = (int) ((3600000.0 / (double) runTime) * gainedXP);
            ammoHour = (int) ((3600000.0 / (double) runTime) * ballsMade);
            //profitHour = (int) ((3600000.0 / (double) runTime) * profit);
            profitHour = (int) ((profit * 3600000D) / runTime);

            g.setRenderingHints(antialiasing);
            g.setColor(color2);
            g.drawRect(521, 468, 32, 33);
            g.setFont(font1);
            g.setColor(color1);
            g.drawImage(img1, 0, 330, null);//background
            outline(g, "" + hours + ":" + minutes + ":" + seconds, color1, outline, 193, 358);
            outline(g, "" + hours + ":" + minutes + ":" + seconds, color1, outline, 193, 358);
            outline(g, "" + status, color1, outline, 123, 379);   //Draws Status
            outline(g, "" + ammoHour, color1, outline, 179, 401); //Draws Ammo made per hour
            outline(g, "" + ballsMade, color1, outline, 175, 424);//Draws Amount of balls added
            outline(g, "" + profit, color1, outline, 171, 445);    //Draws Profit
            outline(g, "" + gainedXP, color1, outline, 422, 357);  //Draws Gained Experience
            outline(g, "" + gainedLVL, color1, outline, 455, 379); //Draws Gained Levels
            outline(g, "" + xpPerHour, color1, outline, 409, 401); //Draws Experience Per Hour
            outline(g, "" + expToLevel, color1, outline, 431, 421);//Draws Experience needed for next level
            outline(g, "" + profitHour, color1, outline, 435, 444);//Draws Profit Per Hour
            outline(g, "" + properties.version(), color1, outline, 233, 476);

            final Node node = smithing_nodes == null ? null : smithing_nodes.get();
            if (node != null && node instanceof PaintListener) {
                ((PaintListener) node).onRepaint(g);
            }
        }
    }

    private void getProfit() {
        if (Variables.Bools.isUsingBars) {
            if (ballsMade > 0) {
                profit = (ballsMade * ballPrice) - (barPrice * (ballsMade / 4));
            }
        } else {
            if (ballsMade > 0) {
                profit = (ballsMade * ballPrice) - ((orePrice + (coalPrice * 2)) * (ballsMade / 4));
            }
        }
    }

    private static void outline(final Graphics g, final String s, final Color in, final Color out, final int x, final int y) {
        g.setColor(out);
        g.drawString(s, x + 1, y);
        g.drawString(s, x - 1, y);
        g.drawString(s, x, y + 1);
        g.drawString(s, x, y - 1);

        g.setColor(in);
        g.drawString(s, x, y);
    }


    public void mouseClicked(MouseEvent e) {
        Point point = e.getPoint();
        Rectangle area = new Rectangle(521, 468, 32, 33);
        if (area.contains(point)) {
            hidePaint = !hidePaint;
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Invoked when the mouse enters a component.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    private Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }
}