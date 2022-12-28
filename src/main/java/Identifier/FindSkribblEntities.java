package Identifier;

import Entities.CanvasBox;
import Entities.DrawColor;
import Entities.Skribbl;
import ImageProcessor.ProcessImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import static ImageProcessor.ProcessImage.preloadColorArray;

public class FindSkribblEntities {

    private static Robot robot;

    private static BufferedImage bImage;

    private static void getScreenCapture(){
        initRobot();
        bImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }

    public static Skribbl findElements(){
        getScreenCapture();
        CanvasBox canvasBox = findCanvasBox();
        return new Skribbl(canvasBox, findColors(canvasBox));
    }

    private static CanvasBox findCanvasBox() {
        Color color = new Color(255,255, 255); //White Canvas Box
        int height = 0;
        int biggestStreak = 0;
        int prevBiggestStreak = 0;
        for(int y = 0; y < bImage.getHeight(); y++) {
            int streak = 0;
            int lastX = 0;
            for(int x = 0; x < bImage.getWidth(); x++) {
                ArrayList<Integer> allStreaks = new ArrayList<>();
                if(bImage.getRGB(x, y) == color.getRGB()) {
                     streak++;
                }
                else {
                    if(streak > Toolkit.getDefaultToolkit().getScreenSize().width/3){
                        allStreaks.add(streak);
                        lastX = x;
                    }
                    streak = 0;
                }
                if(!allStreaks.isEmpty()) {
                    biggestStreak = Collections.max(allStreaks);
                }
            }
            if(prevBiggestStreak == biggestStreak){
                height ++;
            }
            else {
                if(height > Toolkit.getDefaultToolkit().getScreenSize().height/3) {
                    System.out.println("\nFound the box. Height is " + height + "\nWidth is " + prevBiggestStreak
                    + "\nx is " + lastX + "\ny is " + y);
                    return new CanvasBox(lastX-prevBiggestStreak, y - height, height, prevBiggestStreak);
                }
                height = 0;
            }
            prevBiggestStreak = biggestStreak;
        }
        System.out.println("Failed to find the canvas");
        return null;
    }

    public static ArrayList<DrawColor> findColors(CanvasBox canvasBox) {
        preloadColorArray();
        ArrayList<DrawColor> drawColors = new ArrayList<>();
        for (Color color: ProcessImage.getColors()) {
            for(int x = canvasBox.getXPosition(); x < canvasBox.getXPosition() + canvasBox.getWidth()/2; x+=3) {
                for (int y = canvasBox.getYPosition(); y < canvasBox.getYPosition() + canvasBox.getHeight() + 50; y+=3)
                {
                    if(bImage.getRGB(x, y) == color.getRGB()) {
                        System.out.println("Found color");
                        drawColors.add(new DrawColor(x+2, y+2, color));
                        y = canvasBox.getYPosition() + canvasBox.getHeight() + 150;
                        x = canvasBox.getXPosition() + canvasBox.getWidth();
                    }
                }
            }
        }
        return drawColors;
    }

    private static void initRobot() {
        try {
            if (Objects.isNull(robot)) {
                robot = new Robot();
            }
        }
        catch(AWTException e) {
            e.printStackTrace();
        }
    }

}
