/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author 66786575
 */
public class DialogoNombreLista extends JDialog implements WindowListener {
    
    String nombre;
    boolean cancelar;
    boolean continuar;
    
    JPanel panel = (JPanel) this.getContentPane();
    JLabel label;
    JTextField campoTexto;
    JButton aceptar;
    
    
    public DialogoNombreLista(String _nombre){
        
        nombre = _nombre;
        cancelar = true;
        continuar = false;
        
        panel = (JPanel) this.getContentPane();
        FlowLayout fl = new FlowLayout();
        panel.setLayout(fl);
        
        label = new JLabel("Introduzca el nombre de la lista: ");
        panel.add(label, BorderLayout.CENTER);
        
        campoTexto = new JTextField();
        campoTexto.setPreferredSize(new Dimension(250,25));
        panel.add(campoTexto, BorderLayout.CENTER);
        
        aceptar = new JButton(new AbstractAction(){
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                nombre = campoTexto.toString();
                cancelar = false;
                continuar = true;
                setVisible(false);
            }                 
        });
        
        aceptar.setText("Aceptar");
        aceptar.setPreferredSize(new Dimension (100,50));
        panel.add(aceptar, BorderLayout.CENTER);
        
        this.addWindowListener(this);
        setLocation(430,200);
        setSize(360,160);
        setVisible(true);
        
           
    }

    public boolean acabado(){
        
        return continuar;
    }
    
    public boolean cancelado(){
        
        return cancelar;
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        
        continuar = true;
   
    }
    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
