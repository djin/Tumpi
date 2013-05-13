/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Juan
 */
public class Menus extends JMenuBar {

    public JMenu[] menus;
    public ArrayList<JMenuItem> menuItems;

    public Menus() {

        menus = new JMenu[4];
        menuItems = new ArrayList();

        menus[0] = new JMenu("Servidor");
        menus[0].setMnemonic('S');
        
        JMenuItem servidor = new JMenuItem("Establecer Nombre");
        servidor.setMnemonic('E');
        menus[0].add(servidor);
        menuItems.add(servidor);

        add(menus[0]);


        menus[1] = new JMenu("Listas");
        menus[1].setMnemonic('L');


        JMenuItem anadirLista = new JMenuItem("Añadir Lista");
        anadirLista.setMnemonic('A');
        menus[1].add(anadirLista);
        menuItems.add(anadirLista);

        JMenuItem borrarLista = new JMenuItem("Borrar Lista");
        borrarLista.setMnemonic('B');
        menus[1].add(borrarLista);
        menuItems.add(borrarLista);

        JMenuItem promocionar = new JMenuItem("Promocionar");
        promocionar.setMnemonic('P');
        menus[1].add(promocionar);
        menuItems.add(promocionar);

        add(menus[1]);

        menus[2] = new JMenu("Canciones");
        menus[2].setMnemonic('C');

        JMenuItem anadirCancion = new JMenuItem("Añadir Canciones");
        anadirCancion.setMnemonic('A');
        menus[2].add(anadirCancion);
        menuItems.add(anadirCancion);

        JMenuItem borrarCancion = new JMenuItem("Borrar Canciones");
        borrarCancion.setMnemonic('B');
        menus[2].add(borrarCancion);
        menuItems.add(borrarCancion);

        add(menus[2]);

        menus[3] = new JMenu("Sobre");
        menus[1].setMnemonic('S');
        
        JMenuItem acerca = new JMenuItem("Acerca De");
        acerca.setMnemonic('D');
        menus[3].add(acerca);
        menuItems.add(acerca);

        add(menus[3]);

    }
}
