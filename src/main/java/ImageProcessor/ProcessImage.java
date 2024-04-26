package ImageProcessor;

import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ProcessImage {

    @Getter
    private static ArrayList<Color> colors;
    public static void convertImages(BufferedImage bImage)
    {
        for(int x = 0; x < bImage.getWidth(); x++) {
            for(int y = 0; y< bImage.getHeight(); y++) {
                int minDist = 255*3, minIndex = -1;
                for(int i = 0; i < colors.size(); i++) {
                    double dist = myColorDistance(new Color(bImage.getRGB(x, y)), colors.get(i));
                    if(dist < minDist) {
                        minDist = (int)dist;
                        minIndex = i;
                    }
                }
                bImage.setRGB(x, y, colors.get(minIndex).getRGB());
            }
        }
    }

    private static double euclidianColorDistance(Color c1, Color c2)
    {
        int r1 = c1.getRed(), r2 = c2.getRed();
        int rMean = (r1 + r2) >> 1;
        int r = r1 - r2;
        int g = c1.getGreen() - c2.getGreen();
        int b = c1.getBlue() - c2.getBlue();
        return Math.sqrt((((512+rMean)*r*r)>>8) + 4*g*g + (((767-rMean)*b*b)>>8));
    }

    private static double myColorDistance(Color c1, Color c2){
        return Math.sqrt(Math.pow(Math.abs(c1.getBlue() - c2.getBlue()), 2) +
                Math.pow(Math.abs(c1.getGreen() - c2.getGreen()), 2) +
                Math.pow(Math.abs(c1.getRed() - c2.getRed()), 2));
    }

    public static void preloadColorArray() {
        colors = new ArrayList<>();
        colors.add(new Color(255, 255, 255)); // WHITE 255 255 255
        colors.add(new Color(0, 0, 0));// BLACK 0 0 0
        colors.add(new Color(193, 193, 193)); // LIGHT GREY 193 193 193
        colors.add(new Color(80, 80, 80)); // DARK GREY 76 76 76
        colors.add(new Color(239, 19, 11));// RED 239 19 11
        colors.add(new Color(116, 11, 7));// MAROON 116 11 7
        colors.add(new Color(255, 113, 0));// ORANGE 255 113 0
        colors.add(new Color(194, 56, 0));// RUST 194 56 0
        colors.add(new Color(255, 228, 0));// YELLOW 255 228 0
        colors.add(new Color(232, 162, 0));// LEMON 232 162 0
        colors.add(new Color(0, 204, 0));// LIME 0 204 0
        colors.add(new Color(0, 70, 25));// FOREST 0 85 16
        colors.add(new Color(0, 255, 145));// AQUA 0 85 16
        colors.add(new Color(0, 120, 93));// EMERALD 0 178 255
        colors.add(new Color(0, 178, 255));// SKY 0 204 0
        colors.add(new Color(0, 86, 158));// MURKY BLUE 0 86 158
        colors.add(new Color(35, 31, 211));// BLUE 35 31 211
        colors.add(new Color(14, 8, 101));// NAVY 14 8 101
        colors.add(new Color(163, 0, 186));// MAGENTA 163 0 186
        colors.add(new Color(85, 0, 105));// PURPLE 85 0 105
        colors.add(new Color(223, 105, 167));// PINK 211 124 170
        colors.add(new Color(135, 53, 84));// SALMON 167 85 116
        colors.add(new Color(255, 172, 142));// TAN 211 124 170
        colors.add(new Color(204, 119, 77));// LIGHT SKIN 167 85 116
        colors.add(new Color(160, 82, 45));// LIGHT BROWN 160 82 45
        colors.add(new Color(99, 48, 13));// BROWN 99 48 13
    }
}
