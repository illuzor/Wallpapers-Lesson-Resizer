package com.illuzor.lesson.resizer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ConstantConditions")
class ImagesTests {

    @Test
    @DisplayName("Test Files Existence")
    void testFilesExistence() {
        File[] imagesFolders = new File(Paths.IMAGES).listFiles(File::isDirectory);

        for (File categoryFolder : imagesFolders) {
            File previewCategoryFolder = new File(Paths.PREVIEWS, categoryFolder.getName());
            assertTrue(previewCategoryFolder.exists());
            assertTrue(previewCategoryFolder.isDirectory());

            for (File imageFile : categoryFolder.listFiles()) {
                File previewImageFile = new File(previewCategoryFolder, imageFile.getName());
                assertTrue(previewImageFile.exists());
                assertTrue(previewImageFile.isFile());
                assertNotEquals(0, previewImageFile.length());
            }
        }
    }

    @Test
    @DisplayName("Test Previews Sizes")
    void testPreviewsSizes() throws IOException {
        File[] previewsCategoriesFolders = new File(Paths.PREVIEWS).listFiles(File::isDirectory);

        for (File previewCategoryFolder : previewsCategoriesFolders) {
            for (File previewImageFile : previewCategoryFolder.listFiles()) {
                BufferedImage previewImage = ImageIO.read(previewImageFile);

                int width = previewImage.getWidth();
                int height = previewImage.getHeight();
                int min = Math.min(width, height);
                int max = Math.max(width, height);

                assertTrue(min == 399 || min == 400);
                assertTrue(max >= 399);
            }
        }
    }

}