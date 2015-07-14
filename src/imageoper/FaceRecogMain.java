package imageoper;

/*
 * Main.java
 *
 * Created on Sep 12, 2010, 12:44:27 PM
 */
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;

import com.face.RunFaceRecog;




import name.audet.samuel.javacv.*;
import name.audet.samuel.javacv.jna.cxcore.*;
import name.audet.samuel.javacv.jna.cv.*;
import name.audet.samuel.javacv.jna.*;
import static name.audet.samuel.javacv.jna.cxcore.*;
import static name.audet.samuel.javacv.jna.cv.*;
import static name.audet.samuel.javacv.jna.highgui.*;
import static name.audet.samuel.javacv.jna.cvaux.*;

/**
 *
 * @author kishor
 */
public class FaceRecogMain extends javax.swing.JFrame {

  

    public void facedetect(String fname) {
        BufferedImage buff = null;//new BufferedImage(125, 150, BufferedImage.TYPE_BYTE_GRAY);
        try {
            File file = new File(fname);
            if (file.exists()) {

                IplImage image = cvLoadImage(fname, 1);
                if (image.width > 800 || image.height > 800) {
                    BufferedImage bim = image.getBufferedImage();
                    bim = Resizeimage.getSmallerInstance(bim, "2", true);
                    image = IplImage.createFrom(bim);
                }

                IplImage grayImage = cvCreateImage(cvSize(image.width, image.height), IPL_DEPTH_8U, 1),//IplImage.create(image.width, image.height, IPL_DEPTH_8U, 1),
                        rotatedImage = image.clone(),
                        procimg = cvCreateImage(cvSize(640, 640), IPL_DEPTH_8U, 1);
                //cvResize(procimg, image, CV_C);
                //cvResize( procimg,image, CV_INTER_AREA);
                //cvEqualizeHist(procimg, procimg);//giving standard brightness and contrast
                // ImageIcon imageicon = new ImageIcon();

                if (image == null) {
                    System.out.println("Could not load image");
                } else {

                    //cvNamedWindow("Example1", CV_WINDOW_AUTOSIZE);
                    //cvShowImage("Example1", image);
                    //cvNamedWindow("Gray img", CV_WINDOW_AUTOSIZE);
                    //cvShowImage("Gray img", rotatedImage);
                    cvCvtColor(image, grayImage, CV_BGR2GRAY); //error
                    cvSmooth(grayImage, grayImage, CV_C, 1, 1, (double) 2, (double) 2);
                    cvEqualizeHist(grayImage, grayImage);
                    bgm = grayImage.getBufferedImage();
                    //cvSmooth(grayImage, grayImage, CV_C, CV_L1, CV_L2, CV_BGFG_MOG_MINAREA, CV_BGFG_MOG_MINAREA);
                    //cvNamedWindow("Gray img1", CV_WINDOW_AUTOSIZE);
                    //cvShowImage("Gray img1", grayImage);
                    //cvNamedWindow("resize window", CV_WINDOW_AUTOSIZE);
                    //cvShowImage("resize window", procimg);
                    ////CanvasFrame cf= new CanvasFrame("kishor");
                    //cf.showImage(image);
                    // cvCvMemStorage storage = CvMemStorage.create();

                    // Contiguous regions of native memory may be allocated using createArray() factory methods.
                    String mm = "\\haarcascades\\haarcascade_frontalface_alt_tree.xml";
                    System.out.println(mm);
                    File fil = new File(System.getProperty("user.dir") + "\\haarcascades\\haarcascade_frontalface_alt.xml");
                    //File fil1= new File(System.getProperty("user.dir")+"\\haarcascades\\ojoD.xml");
                    //File fil2= new File(System.getProperty("user.dir")+"\\haarcascades\\ojoI.xml");
                    //File fil3= new File(System.getProperty("user.dir")+"\\haarcascades\\haarcascade_mcs_eyepair_small.xml");
                    CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(fil.getCanonicalPath()));
                    //CvHaarClassifierCascade cascade1 = new CvHaarClassifierCascade(cvLoad(fil1.getCanonicalPath()));
                    //CvHaarClassifierCascade cascade2 = new CvHaarClassifierCascade(cvLoad(fil2.getCanonicalPath()));
                    //CvHaarClassifierCascade cascade3 = new CvHaarClassifierCascade(cvLoad(fil3.getCanonicalPath()));

                    String dd = "haarcascade_frontalface_alt.xml";//System.getProperty("user.dir")+"\\haarcascade\\haarcascade_frontalface_alt.xml";



                    if (cascade != null) {
                        if (!CV_IS_HAAR_CLASSIFIER(cascade)) {
                            System.out.println("cascade is valid");
                        }
                        System.out.println("cascade not null");
                    } else {
                        System.out.println("cascade is null");
                    }

                    CvMemStorage storage = CvMemStorage.create();
                    // CvPoint[] hatPoints = CvPoint.createArray(3);
                    CvSeq.PointerByReference contourPointer = new CvSeq.PointerByReference();
                    System.out.println("0");
                    /*int sizeofCvContour = com.sun.jna.Native.getNativeSize(CvContour.ByValue.class);
                    System.out.println("1");*/
                    CvSeq faces = cvHaarDetectObjects(grayImage, cascade, storage, 1.1, 3, 0/*CV_HAAR_DO_CANNY_PRUNING*/, cvSize(90, 90));
                    //CvSeq eyer = cvHaarDetectObjects(grayImage, cascade1, storage, 1.1, 3,0);
                    // CvSeq eyel = cvHaarDetectObjects(grayImage, cascade2, storage, 1.1, 3,0);
                    System.out.println("face total " + faces.total);
                    // for (int i = 0; i < faces.total; i++)
                    if (faces.total > 1) {
                    } else {

                        CvRect r = new CvRect(cvGetSeqElem(faces, 0));
                        cvRectangle(image, cvPoint(r.x, r.y), cvPoint(r.x + r.width, r.y + r.height), CvScalar.RED, 1, CV_AA, 0);
                        //cvCircle(image, cvPoint(r.x,r.y),r.width/2, CvScalar.RED, 2, 2, 2);
                        int widt = r.width;
                        int height = r.height;
                        int startx = r.x;
                        int starty = r.y;
                        buff = new BufferedImage(widt - 20, height - 10, bgm.getType());
                        buff = bgm.getSubimage(startx + 10, starty + 10, widt - 20, height - 20);
                        //ImageIcon icon = new ImageIcon((Image)buff);
                        //jLabel1.setIcon(icon);
                        System.out.println("Scaling the face detected");
                        buff = Resizeimage.getScaledInstance(buff, 0, 0, 0, true);
                        System.out.println("writing the face detected");
                        Resizeimage.writeJPEG(buff, "output");
                        //cvr
                        /*hatPoints[0].x = r.x - r.width / 10;
                        hatPoints[0].y = r.y - r.height / 10;
                        hatPoints[1].x = r.x + r.width * 11 / 10;
                        hatPoints[1].y = r.y - r.height / 10;
                        hatPoints[2].x = r.x + r.width / 2;
                        hatPoints[2].y = r.y - r.height / 2;
                        cvFillConvexPoly(image, hatPoints, hatPoints.length, CvScalar.GREEN, CV_AA, 0);*/
                    }
                    /*for(int i=0;i<eyer.total;i++){
                    CvRect r = new CvRect(cvGetSeqElem(eyer, i));
                    cvRectangle(image, cvPoint(r.x, r.y), cvPoint(r.x + r.width, r.y + r.height), CvScalar.WHITE, 1, CV_AA, 0);
                    //cvRectangle(image, cvPoint(r.x, r.y), cvPoint(r.x + r.width/2, r.y + r.height), CvScalar.GREEN, 1, CV_AA, 0);
                    //cvRectangle(image, cvPoint(r.x+r.width/2, r.y), cvPoint(r.x+r.width/2 + r.width/2, r.y + r.height), CvScalar.YELLOW, 1, CV_AA, 0);
                    }
                    for(int i=0;i<eyel.total;i++){
                    CvRect r1 = new CvRect(cvGetSeqElem(eyel, i));
                    cvRectangle(image, cvPoint(r1.x, r1.y), cvPoint(r1.x + r1.width, r1.y + r1.height), CvScalar.GREEN, 1, CV_AA, 0);
                    }*/
                    //jsc = new JScrollPane() {

                    // ImageIcon image1=null;
                    //ImageIcon image = new ImageIcon(image);//new ImageIcon(getClass().getResource("/image/jpane.jpg"));

                    /* @Override
                    public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Dimension d = getSize();
                    for (int x = 0; x < d.width; x += image.getIconWidth()) {
                    for (int y = 0; y < d.height; y += image.getIconHeight()) {
                    g.drawImage(image.getImage(), x, y, null, null);
                    }
                    }
                    // super.paint(g);
                    }
                    };
                    jsc.setOpaque(false);
                    getContentPane().add(jsc);
                    jsc.setSize(getContentPane().getMaximumSize());*/
                    //cvShowImage("Example1", image);
                    //cvWaitKey(0);
                    //cvDestroyWindow("Example1");
                }
            } else {
                System.out.println("File does not exist");
                System.out.println(System.getProperty("user.dir"));
            }
            // cvNamedWindow("Example1", CV_WINDOW_AUTOSIZE );
            //cvShowImage( "Example1", image );
            //cvWaitKey(0);
            //cvDestroyWindow( "Example1");

        } catch (Exception ex) {
            Logger.getLogger(FaceRecogMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );

        fileMenu.setText("File");

        openMenuItem.setText("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText("Save As ...");
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setText("Help");

        contentsMenuItem.setText("Contents");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	DBConn db = new DBConn();
    	
        System.out.println(System.getProperty("user.dir"));
        cascadeName = args.length > 0 ? args[0] : "haarcascade_frontalface_alt.xml";
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                RunFaceRecog runface = new RunFaceRecog();
                runface.runFace();
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables
    static String cascadeName = "";
    JScrollPane jsc;
    int dlb = 0;
    int drb = 0;
    int der = 0;
    int del = 0;
    int de = 0;
    BufferedImage bgm = null;
}
