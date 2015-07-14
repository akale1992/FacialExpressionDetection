package facerecognition.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImageDisplayPanel extends JPanel {

    private BufferedImage bi;

    public ImageDisplayPanel() {
        this.setPreferredSize(new Dimension(200, 200));
    }

    public void setImage(File f) {
        try {
            bi = ImageIO.read(f);
        } catch (Exception e) {
            bi = null;
        }
        if (bi != null) {
            setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bi, 0, 0, null);
    }
}
