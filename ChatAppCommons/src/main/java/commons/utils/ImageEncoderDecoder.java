package commons.utils;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageEncoderDecoder {


    public static String getEncodedImage(File f)throws IOException {

        byte [] data = null;
        BufferedImage bImage = ImageIO.read(f);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, getFileExtension(f), bos );
        data= bos.toByteArray();

        String encoded = Base64.getEncoder().encodeToString(data);
        return  encoded;
    }

    public static Image getDecodedImage(String encodedImage){
        byte[] dst = Base64.getDecoder().decode(encodedImage);
        Image img = new Image(new ByteArrayInputStream(dst));
        return  img;
    }
      private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf+1);
    }
}
