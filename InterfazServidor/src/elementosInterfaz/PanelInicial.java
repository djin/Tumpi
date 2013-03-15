/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author 66785320
 */
public class PanelInicial extends JPanel {

    private Dimension tamanio;
    private Random random;
    private boolean direccion1 = false, direccion2 = false, direccion3 = false, direccion4 = false, direccion5 = false;
    private int tamanoVolumen = 80, columna1 = 0, columna2 = 20, columna3 = 40, columna4 = 10, columna5 = 50;
    private int maximo1 = 140, minimo1 = 30, maximo2 = 140, minimo2 = 30, maximo3 = 140, minimo3 = 30, maximo4 = 120, minimo4 = 30, maximo5 = 120, minimo5 = 30;

    public PanelInicial() {
        tamanio = new Dimension(400, 200);
        random = new Random();
        Timer time = new Timer(2, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!direccion1) {
                    columna1++;
//                    columna3++;
//                    columna4++;
//                    columna5++;
                    if (columna1 == maximo1) {
                        direccion1 = true;
                        minimo1 = random.nextInt(80);
                    }
                } else {
                    columna1--;
//                    columna3--;
//                    columna4--;
//                    columna5--;
                    if (columna1 == minimo1) {
                        direccion1 = false;
                        maximo1 = random.nextInt(80) + 120;
                    }
                }
                if (!direccion2) {
                    columna2++;
                    if (columna2 == maximo2) {
                        direccion2 = true;
                        minimo2 = random.nextInt(80);
                    }
                } else {
                    columna2 --;
                    if(columna2 == minimo2){
                        direccion2 = false;
                        maximo2 = random.nextInt(80)+120;
                    }
                }
                if (!direccion3) {
                    columna3++;
                    if (columna3 == maximo3) {
                        direccion3 = true;
                        minimo3 = random.nextInt(80);
                    }
                } else {
                    columna3 --;
                    if(columna3 == minimo3){
                        direccion3 = false;
                        maximo3 = random.nextInt(80)+120;
                    }
                }
                if (!direccion4) {
                    columna4++;
                    if (columna4 == maximo4) {
                        direccion4 = true;
                        minimo4 = random.nextInt(80);
                    }
                } else {
                    columna4 --;
                    if(columna4 == minimo4){
                        direccion4 = false;
                        maximo4 = random.nextInt(80)+120;
                    }
                }
                if (!direccion5) {
                    columna5++;
                    if (columna5 == maximo5) {
                        direccion5 = true;
                        minimo5 = random.nextInt(80);
                    }
                } else {
                    columna5 --;
                    if(columna5 == minimo5){
                        direccion5 = false;
                        maximo5 = random.nextInt(80)+120;
                    }
                }
                repaint();
            }
        });
        time.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tamanio = getSize();
        ImageIcon imagenFondo = Imagenes.getImagen("icons/socialDj2.png");
        if (imagenFondo != null) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, tamanio.width, tamanio.height);
            g.setColor(new Color(107, 107, 107));
            g.fillRect(0, tamanio.height - columna1, tamanoVolumen, columna1);
            g.fillRect(tamanoVolumen, tamanio.height - columna2, tamanoVolumen, columna2);
            g.fillRect(tamanoVolumen * 2, tamanio.height - columna3, tamanoVolumen, columna3);
            g.fillRect(tamanoVolumen * 3, tamanio.height - columna4, tamanoVolumen, columna4);
            g.fillRect(tamanoVolumen * 4, tamanio.height - columna5, tamanoVolumen, columna5);
            g.drawImage(imagenFondo.getImage(), 50, 65, tamanio.width - 110, tamanio.height - 130, null);
            setOpaque(false);
        } else {
            setOpaque(true);
        }
    }
}