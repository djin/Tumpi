/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author 66785379
 */
public class PanelCaratula extends JPanel {
    
    private String path;
//    private String path_def;
    private BufferedImage image;
    
    PanelCaratula(String path){
        this.path = path;
//        this.path_def = path;
        image=new BufferedImage(1024,768,BufferedImage.TYPE_INT_ARGB_PRE);
        image.createGraphics().drawImage(Imagenes.getImagen(path).getImage(), 0, 0, null);
        this.setPreferredSize(new Dimension(80, 80));
    }
    
    public void cambiarCaratula(BufferedImage im){
        /*if(path!=null){
            this.path = path;
        }
        else
            this.path=path_def;*/
        if(im!=null) {
            image=im;
        }
        else{
            image=new BufferedImage(1024,768,BufferedImage.TYPE_INT_ARGB_PRE);
            image.createGraphics().drawImage(Imagenes.getImagen(path).getImage(), 0, 0, null);
        }
        this.setPreferredSize(new Dimension(80,80));
        this.repaint();        
    }
       @Override
       public void paintComponent(Graphics g){
           super.paintComponent(g);
//           ImageIcon caratula = new ImageIcon(path);
           if(image != null){
               g.drawImage(image, 0, 0, 80, 80, null);
               setOpaque(false);
           }
           else{
               setOpaque(true);
           }
       }
    
}
