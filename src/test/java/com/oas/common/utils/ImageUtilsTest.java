package com.oas.common.utils;

import static org.junit.Assert.fail;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageUtilsTest {

	@Test
	public void testGetScaledInstance() {
		
		try {
			BufferedImage imageIn = ImageIO.read(new BufferedInputStream(new FileInputStream("c:/modelfee.gif")));
			//BufferedImage imageOut = ImageUtils.getScaledInstance(imageIn, 500, 500, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
			BufferedImage imageOut = ImageUtils.resize(imageIn, 100, 100);
			imageOut.createGraphics();
			Graphics2D g = (Graphics2D)imageOut.getGraphics();  
			g.drawImage(imageOut, null, 100, 100);
			ImageIO.write(imageOut, "gif", new File("c:/modelfee_new.gif"));  
		} catch (Exception e) {
			fail("test failed because of " + e.getMessage());
		}
		
	}

}
