/*
 * Copyright (c) 2012. Reapers Rage LLC.
 */

package org.reapersrage.reaperscbsmithy.jobs;

/**
 * Created by IntelliJ IDEA.
 * User: Kyle
 * Date: 6/11/12
 * Time: 1:32 AM
 */

import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import java.awt.*;
import java.util.logging.Logger;

/**
 * Simple anti ban class
 *
 * @author RoadProphet
 */
public class Antiban extends Thread implements PaintListener {

    /**
     * Our timer
     */
    private Timer antiBanTimer;

    /**
     * Our min and max times
     */
    private int minTime = (20 * 1000); //20 seconds
    private int maxTime = (120 * 1000); //2 minutes, or 120 seconds

    /**
     * Constructor. Initializes timer.
     */
    public Antiban() {
        //super.setLock(false);
        antiBanTimer = new Timer(Random.nextInt(minTime, maxTime)); //Do an anti ban action at least once between 20 seconds and two minutes
    }

    @Override
    public void run() {
        int whatdo = Random.nextInt(0, 10);
        Logger.getAnonymousLogger().info("Antiban Executing with code " + whatdo);
        switch (whatdo) {

            case 0:
                //Open the skills tab and check a random skill
                int randomSkill = Random.nextInt(0, 24);
                Tabs.STATS.open();
                WidgetChild randStat = Skills.getWidgetChild(randomSkill);
                Point randStatPoint = randStat.getAbsoluteLocation();
                randStatPoint.x += Random.nextInt(-10, 10); //Don't have the mouse go to the EXACT same spot every time! :)
                randStatPoint.y += Random.nextInt(-10, 10); //Also, you can change all of these values. >_>
                Mouse.move(randStatPoint);
                break;
            case 1:
                Camera.setAngle(Camera.getYaw() + Random.nextInt(-780, 1280));
                //Fill in shit here
                break;

            case 2:

                break;

            default:
                if (Players.getLocal().getInteracting() != null) {
                    //Turn to the object we are interacting with, then move it around a bit
                    Camera.turnTo(Players.getLocal().getInteracting());
                    int currentPitch = Camera.getPitch();
                    int currentYaw = Camera.getYaw();
                    Camera.setPitch(currentPitch + Random.nextInt(-13, 13));
                    Camera.setAngle(currentYaw + Random.nextInt(-25, 25));
                } else {
                    //Move the camera a bit
                    int currentPitch = Camera.getPitch();
                    int currentYaw = Camera.getYaw();
                    Camera.setPitch(currentPitch + Random.nextInt(-50, 50));
                    Camera.setAngle(currentYaw + Random.nextInt(-70, 70));
                }
        }

        resetAntiBanTime();
    }
    /**
     * Resets the timer to a new random time between our min and max values
     */
    private void resetAntiBanTime() {
        antiBanTimer.setEndIn(Random.nextInt(minTime, maxTime));
    }
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private final Color color1 = new Color(0, 255, 0);

    private final Font font1 = new Font("Bell MT", 1, 15);

    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(antialiasing);

        g.setFont(font1);
        g.setColor(color1);
    }
}