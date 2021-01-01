package p3.network.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImgReader {

    public static ArrayList<Color> getColors() {
        BufferedImage image;
        int width;
        int height;
        ArrayList<Color> colors = new ArrayList<>();
        try {
            image = ImageIO.read(ImgReader.class.getClassLoader().getResource("Input.jpg"));
            width = image.getWidth();
            height = image.getHeight();
            for(int i=0; i<height; i++) {
                for(int j=0; j<width; j++) {
                    colors.add(new Color(image.getRGB(j, i)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return colors;
    }
}
