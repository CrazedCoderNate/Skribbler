package Starter;

import ImageProcessor.ProcessImage;
import com.microsoft.playwright.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;
import java.util.Random;

import static ImageProcessor.ProcessImage.convertImages;
import static ImageProcessor.ProcessImage.preloadColorArray;

@NoArgsConstructor
public class ImageGrabber {

    private static String IMAGE_SEARCH = "https://www.google.com/search?tbm=isch&q=";

    @Getter @Setter
    private String drawEntity;
    private Robot robot;
    private BufferedImage entityImage;

    public ImageGrabber(String drawEntity){
        this.drawEntity = drawEntity;
    }

    public BufferedImage grabImage(){
        BufferedImage bImage;
        try (Playwright playwright = Playwright.create()) {
            preloadColorArray();
            BrowserType webkit = playwright.webkit();
            Browser browser = webkit.launch();
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate(IMAGE_SEARCH + drawEntity);
            page.waitForLoadState();
            //page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(drawEntity + ".png")));
            bImage = resizeImage(findImage(page), 238, 150);
            browser.close();


            File outputFile = new File(drawEntity + ".png");
            ImageIO.write(bImage, "png", outputFile);
            convertImages(bImage);
            File outputFileConverted = new File(drawEntity + "_converted.png");
            ImageIO.write(bImage, "png", outputFileConverted);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bImage;
    }

    private BufferedImage findImage(final Page page) throws IOException {
        final String selector = ".bRMDJf.islir > img";
        page.waitForSelector(selector);
        final Locator locator = page.locator(selector);
        final Random random = new Random();
        String imageURL = locator.nth(random.nextInt(locator.count())).getAttribute("src");
        for(int i = 0; i < locator.count(); i++)
        {
            if(Objects.isNull(imageURL)) {
                imageURL = locator.nth(random.nextInt(locator.count())).getAttribute("src");
            }
            else {
                i = locator.count();
            }
        }
        if(imageURL.contains(";base64,")) {
            String bits = imageURL.substring(imageURL.indexOf(";base64,") + 8);
            byte[] decodedString = Base64.getDecoder().decode(bits);
            return ImageIO.read(new ByteArrayInputStream(decodedString));
        }
        System.out.println(imageURL);
        Image image;
        try {
            URL url = new URL(imageURL);
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return toBufferedImage(image);
    }

    // Thank you, Sri Harsha Chilakapati, from https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }
        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bImage;
    }
    // Thank you Baeldung from https://www.baeldung.com/java-resize-image
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(targetWidth, targetHeight)
                .outputFormat("JPEG")
                .outputQuality(1)
                .toOutputStream(outputStream);
        byte[] data = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return ImageIO.read(inputStream);
    }
}

/* Code Written by Nathan Hamilton
   CrazedCoder on StackOverflow
   CrazedCoderNate on Github
   CrazedCoderNate on Youtube
 */
