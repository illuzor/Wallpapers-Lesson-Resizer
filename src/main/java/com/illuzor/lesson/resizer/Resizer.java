package com.illuzor.lesson.resizer;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("ALL")
class Resizer {

    void resize() throws IOException {
        final int MIN_SIZE = 400;
        File imagesFolder = new File(Paths.IMAGES);
        File previewsFolder = new File(Paths.PREVIEWS);

        if (!previewsFolder.exists()) {
            previewsFolder.mkdir();
        }

        File[] categoriesFolders = imagesFolder.listFiles(File::isDirectory);

        for (File categoryFolder : categoriesFolders) {
            String categoryName = categoryFolder.getName();
            File previewCategoryFolder = new File(previewsFolder, categoryName);
            if (!previewCategoryFolder.exists()) {
                previewCategoryFolder.mkdir();
            }
            for (File imageFile : categoryFolder.listFiles()) {
                File previewFile = new File(previewCategoryFolder, imageFile.getName());
                if (!previewFile.exists()) {
                    BufferedImage image = ImageIO.read(imageFile);
                    int width = image.getWidth();
                    int height = image.getHeight();
                    int previewWidth;
                    int previewHeight;

                    if (width > height) {
                        previewWidth = (int) ((float) width * MIN_SIZE / height);
                        previewHeight = MIN_SIZE;
                    } else if (width < height) {
                        previewWidth = MIN_SIZE;
                        previewHeight = (int) ((float) height * MIN_SIZE / width);
                    } else {
                        previewWidth = MIN_SIZE;
                        previewHeight = MIN_SIZE;
                    }

                    String imageFormat;
                    if (imageFile.getName().toLowerCase().endsWith(".png")) {
                        imageFormat = "PNG";
                    } else {
                        imageFormat = "JPG";
                    }

                    BufferedImage previewImage = Scalr.resize(image, previewWidth, previewHeight);
                    ImageIO.write(previewImage, imageFormat, previewFile);

                    System.out.println("Preview file created: " + previewFile.getPath());
                    System.out.print("Original size: " + width + "x" + height);
                    System.out.println(", new size: " + previewWidth + "x" + previewHeight);
                } else {
                    System.out.println("Preview file exists: " + previewFile.getPath());
                }
                System.out.println();
            }
        }
        System.out.println("--- --- --- \n Finished");
    }

}
