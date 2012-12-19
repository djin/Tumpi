/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazreproductorbasico;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author 66785379
 */
public class PanelCaratula extends JPanel {
    
    private String path;
    
    PanelCaratula(String path){
        this.path = path;
        
    }
    
    public void cambiarCaratula(String path){
        this.path = path;
        this.setPreferredSize(new Dimension(80,80));
        this.repaint();
    }
    
       @Override
       public void paintComponent(Graphics g){
           super.paintComponent(g);
           ImageIcon caratula = new ImageIcon(path);
           if(caratula != null){
               g.drawImage(caratula.getImage(), 0, 0, 80, 80, null);
               setOpaque(false);
           }
           else{
               setOpaque(true);
           }
       }
    
}
