package Drawer;

import Entities.DrawColor;
import Entities.Skribbl;
import ImageProcessor.ProcessImage;
import com.microsoft.playwright.options.MouseButton;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class DrawImage {

    private static Robot robot;
    public static void drawImage(BufferedImage bImage, Skribbl skribbl) throws InterruptedException {
        initRobot();
        int delayLol = 0;
        for(DrawColor color : skribbl.getDrawColors()) {
            if(!(color.getColor().getBlue() == 255 && color.getColor().getGreen() == 255 && color.getColor().getRed() == 255)) {
                clickColor(color);
                for(int x = 0; x <  bImage.getWidth(); x++) {
                    for (int y = 0; y < bImage.getHeight(); y++) {
                        if (bImage.getRGB(x, y) == color.getColor().getRGB()) {
                            robot.mouseMove(skribbl.getCanvasBox().getXPosition() + 2 * x, skribbl.getCanvasBox().getYPosition() + 2 * y);
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            delayLol++;
                            if (delayLol > 50) {
                                robot.delay(12);
                                delayLol = 0;
                            }
                        }
                    }
                }
            }
        }

    }

    private static void clickColor(DrawColor drawColor){
        robot.mouseMove(drawColor.getXPosition(), drawColor.getYPosition());
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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
