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
public class Menus extends JMenuBar{

    public JMenu[] menus;
    public ArrayList<JMenuItem> menuItems;
    
    public Menus() {

        menus = new JMenu[3];
        menuItems = new ArrayList();

        menus[0] = new JMenu("Listas");
        menus[0].setMnemonic('L');

        
        JMenuItem anadirLista = new JMenuItem("Añadir Lista");
        anadirLista.setMnemonic('A');
        menus[0].add(anadirLista);
        menuItems.add(anadirLista);

        JMenuItem borrarLista = new JMenuItem("Borrar Lista");
        borrarLista.setMnemonic('L');
        menus[0].add(borrarLista);
        menuItems.add(borrarLista);
        
        JMenuItem promocionar = new JMenuItem("Promocionar");
        promocionar.setMnemonic('P');
        menus[0].add(promocionar);
        menuItems.add(promocionar);
        
        JMenuItem guardarSesion = new JMenuItem("Guardar Sesion");
        guardarSesion.setMnemonic('G');
        menus[0].add(guardarSesion);
        menuItems.add(guardarSesion);

        JMenuItem cargarSesion = new JMenuItem("Cargar Sesion");
        cargarSesion.setMnemonic('S');
        menus[0].add(cargarSesion);
        menuItems.add(cargarSesion);

        add(menus[0]);

        menus[1] = new JMenu("Canciones");
        menus[1].setMnemonic('C');

        JMenuItem anadirCancion = new JMenuItem("Añadir Canciones");
        anadirCancion.setMnemonic('C');
        menus[1].add(anadirCancion);
        menuItems.add(anadirCancion);
        
        JMenuItem borrarCancion = new JMenuItem("Borrar Canciones");
        borrarCancion.setMnemonic('B');
        menus[1].add(borrarCancion);
        menuItems.add(borrarCancion);

        add(menus[1]);

        menus[2] = new JMenu("Sobre");

        JMenuItem acerca = new JMenuItem("Acerca De");
        acerca.setMnemonic('D');
        menus[2].add(acerca);
        menuItems.add(acerca);

        add(menus[2]);

    }
}
