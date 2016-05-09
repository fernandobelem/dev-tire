/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author fobm
 */
public class ImagePanel extends JPanel {

    public BufferedImage image;

    public ImagePanel() {
    }

    public void carregaImagem(byte[] byteImg) {
        try {
            if (byteImg != null) {

                InputStream in = new ByteArrayInputStream(byteImg);
                image = ImageIO.read(in);
                repaint();
            }
        } catch (IOException ex) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public byte[] loadImgBytes(String path){
    	BufferedImage img = null;
    	try {
    	    img = ImageIO.read(getClass().getResourceAsStream(path));
    	} catch (IOException e) {
    	}
    	
    	return extractBytes(img);
        
    	
    }

    public byte[] extractBytes() {
        // get DataBufferBytes from Raster
        if (image != null) {
            try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();
                return imageInByte;

            } catch (IOException ex) {
                Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }
    
    public static byte[] extractBytes(BufferedImage img) {
        // get DataBufferBytes from Raster
        if (img != null) {
            try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(img, "png", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();
                return imageInByte;

            } catch (IOException ex) {
                Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

    public void repaintPanel(File f) {
        try {
            image = ImageIO.read(f);
            repaint();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Não foi possivel identifcar o item selecionado. Por favor, selecione uma imagem !");
            // handle exception...
        }
    }

    public void erasePaint() {
        image = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(scaledImage, 0, 0, null); // see javadoc for more info on the parameters      
        }
    }

}
