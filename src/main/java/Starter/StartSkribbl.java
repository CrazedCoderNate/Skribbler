package Starter;

import Entities.Skribbl;

import java.awt.image.BufferedImage;

import static Drawer.DrawImage.drawImage;
import static Identifier.FindSkribblEntities.*;

public class StartSkribbl {
    public static void main(String[] args) throws InterruptedException {
        ImageGrabber imageGrabber = new ImageGrabber("haunted house");
        BufferedImage bImage = imageGrabber.grabImage();
        Skribbl skribbl = findElements();
        drawImage(bImage, skribbl);
    }
}
