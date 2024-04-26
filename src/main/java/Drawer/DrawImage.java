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
                    boolean cont = false;
                    for (int y = 0; y < bImage.getHeight(); y++) {
                        if (bImage.getRGB(x, y) == color.getColor().getRGB()&&cont) {
                            delayLol++;
                            if (delayLol > 12) {
                                robot.delay(15);
                                delayLol = 0;
                            }
                        }
                        else if (bImage.getRGB(x, y) == color.getColor().getRGB()) {
                            cont = true;
                            robot.mouseMove(skribbl.getCanvasBox().getXPosition() + 2 * x, skribbl.getCanvasBox().getYPosition() + 2 * y);
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            delayLol++;
                            if (delayLol > 12) {
                                robot.delay(15);
                                delayLol = 0;
                            }
                        }
                        else if (cont) {
                            cont = false;
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            robot.delay(12);
                            if (delayLol > 12) {
                                robot.delay(15);
                                delayLol = 0;
                            }
                        }
                    }
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }
            }
        }

    }

    private static void clickColor(DrawColor drawColor){
        robot.delay(12);
        robot.mouseMove(drawColor.getXPosition(), drawColor.getYPosition());
        robot.delay(12);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(12);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(12);
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
