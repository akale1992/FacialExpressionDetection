/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageoper;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.ScaleDescriptor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author kishor
 */
public class Resizeimage {

    /**
     * this gets rid of exception for not using native acceleration
     */
    /*static
    {
    System.setProperty("com.sun.media.jai.disableMediaLib", "true");
    }*/

    /*public static void resize(BufferedImage bimage) {
    try {
    //PlanarImage image = JAI.create("bandmerge", bimage);
    //PlanarImage image = PlanarImage.wrapRenderedImage(bimage);
    int destWidth = 125;
    float xScale = (float) destWidth / bimage.getWidth();

    int destHeight = 150;
    float yScale = (float)destHeight / (float)bimage.getHeight();

    RenderedOp renderedOp = ScaleDescriptor.create(bimage, new Float(xScale), new Float(yScale),
    new Float(0.0f), new Float(0.0f), Interpolation.getInstance(Interpolation.INTERP_BICUBIC), null);
    writeJPEG(renderedOp.getAsBufferedImage(), "output.jpg");
    } catch (IOException e) {
    e.printStackTrace();
    }
    }*/
    public static void writeJPEG(BufferedImage input, String name) throws IOException {
        /*Iterator iter =
        ImageIO.getImageWritersByFormatName("JPG");
        if (iter.hasNext()) {
        ImageWriter writer = (ImageWriter) iter.next();
        ImageWriteParam iwp =
        writer.getDefaultWriteParam();
        iwp.setCompressionMode(
        ImageWriteParam.MODE_EXPLICIT);
        iwp.setCompressionQuality(0.95f);
        File outFile = new File(name);
        FileImageOutputStream output =
        new FileImageOutputStream(outFile);
        writer.setOutput(output);
        IIOImage image =
        new IIOImage(input, null, null);
        writer.write(null, image, iwp);
        output.close();
        }*/

        try {
            BufferedImage bi = input; // retrieve image
            File outputfile = new File("" + name + ".png");
            if (outputfile.exists()) {
                outputfile.delete();
            }
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
        }
    }

    /**
     * Convenience method that returns a scaled instance of the
     * provided {@code BufferedImage}.
     *
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance,
     *    in pixels
     * @param targetHeight the desired height of the scaled instance,
     *    in pixels
     * @param hint one of the rendering hints that corresponds to
     *    {@code RenderingHints.KEY_INTERPOLATION} (e.g.
     *    {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *    {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step
     *    scaling technique that provides higher quality than the usual
     *    one-step technique (only useful in downscaling cases, where
     *    {@code targetWidth} or {@code targetHeight} is
     *    smaller than the original dimensions, and generally only when
     *    the {@code BILINEAR} hint is specified)
     * @return a scaled version of the original {@code BufferedImage}
     */
    public static BufferedImage getScaledInstance(BufferedImage img,
            int targetWidth,
            int targetHeight,
            Object hint,
            boolean higherQuality) {
        targetHeight = 150;
        targetWidth = 125;
        int type = (img.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage) img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (w > targetWidth) {
                w /= 2;

            }
            if (w < targetWidth) {
                w = targetWidth;
            }
            if (h > targetHeight) {
                h /= 2;

            }
            if (h < targetHeight) {
                h = targetHeight;
            }

            BufferedImage tmp = new BufferedImage(targetWidth, targetHeight, img.getType());
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

    public static BufferedImage getSmallerInstance(BufferedImage img,
            Object hint,
            boolean higherQuality) {
        float sfact;
        int w = img.getWidth(), h = img.getHeight();
        if (img.getHeight() > img.getWidth()) {
            sfact = (float) img.getWidth() / img.getHeight();
            w = (int) ((int) 800 * sfact);
            h = 800;
            System.out.println("w=" + w + "h=" + h);
            System.out.println("sfact " + sfact);
        } else {
            sfact = (float) img.getHeight() / img.getWidth();
            w = 800;
            h = (int) (800 * sfact);
            System.out.println("w=" + w + "h=" + h);
            System.out.println("sfact " + sfact);
        }
        int targetHeight = 800;
        int targetWidth = 800;
        int type = (img.getTransparency() == Transparency.OPAQUE)
                ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage) img;

        if (img.getHeight() > 800) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
        }
        if (w > 800) {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
        }

        /*do {
        if (higherQuality && w > targetWidth) {
        w /= 2;
        if (w < targetWidth) {
        w = targetWidth;
        }
        }

        if (higherQuality && h > targetHeight) {
        h /= 2;
        if (h < targetHeight) {
        h = targetHeight;
        }
        }*/
        System.out.println("w=" + w + "h=" + h);
        BufferedImage tmp = new BufferedImage(w, h, img.getType());
        Graphics2D g2 = tmp.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.drawImage(ret, 0, 0, w, h, null);
        g2.dispose();

        ret = tmp;
        //} while (w != targetWidth || h != targetHeight);

        return ret;
    }
}
