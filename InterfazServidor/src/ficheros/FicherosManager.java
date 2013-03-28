/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ficheros;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import modelos.ListasCancionesManager;

/**
 *
 * @author 66786575
 */
public class FicherosManager {

    private static ListasCancionesManager listas_manager;
    private File fichSelector;
    private File fichSesion;
    private Properties propiedades;
    private FileInputStream entradaDatos = null;
    private FileOutputStream salidaDatos;

    public FicherosManager(ListasCancionesManager _listas_manager) {

        listas_manager = _listas_manager;
    }
    
    public static boolean acabaEnsdj(File f) {
        return f.getName().endsWith(".sdj");
    }
    
    public boolean cargarSesion() {
        
        JFileChooser chooser = new JFileChooser() {
            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                dialog.setAlwaysOnTop(true);
                return dialog;
            }
        };
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(new File(""));
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                if (acabaEnsdj(f) || f.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return "Filtro sdj";
            }
        });
        
        fichSesion = new File("");
        boolean i = false;

        if (fichSesion.exists()) {

            entradaDatos = null;

            try {

                i = false;
                i = fichSesion.createNewFile();
                entradaDatos = new FileInputStream(fichSesion);
                

            } catch (IOException ex) {
                if (i == false) {
                    System.err.println("Error al cargar el fichero de listas" + ex);
                }
            } finally {
                try {
                    entradaDatos.close();
                    return true;
                } catch (IOException ex) {
                    System.err.println("Error al cargar el fichero de listas++" + ex);
                    return false;
                }
            }
        }
        else{
            return false;
        }
    }
    
    public void cargarPreferencias() {


        fichSelector = new File("fich_selector");
        propiedades = new Properties();
        boolean i = false;

        if (fichSelector.exists()) {

            entradaDatos = null;

            try {

                i = false;
                i = fichSelector.createNewFile();
                entradaDatos = new FileInputStream(fichSelector);
                propiedades.load(entradaDatos);

            } catch (IOException ex) {
                if (i == false) {
                    System.err.println("Error al cargar el fichero del selector" + ex);
                }
            } finally {
                try {
                    entradaDatos.close();
                } catch (IOException ex) {
                    System.err.println("Error al cargar el fichero del selector++" + ex);
                }
            }

            listas_manager.path = propiedades.getProperty("selector");

        } else {

            listas_manager.path = "C:\\Users\\66785361\\Documents";
        }
    }

    public void guardarPreferencias() {

        fichSesion = new File("fich_sesion");
        fichSelector = new File("fich_selector");
        propiedades = new Properties();
        
        try {
            salidaDatos = new FileOutputStream(fichSesion);
            
        } catch (IOException ex) {
            System.err.println("Error al guardar el fichero de sesion" + ex);
        } finally {
            try {
                salidaDatos.close();
            } catch (IOException ex) {
                System.err.println("Error al guardar el fichero de sesion++" + ex);
            }
        }
        
        propiedades.setProperty("selector", listas_manager.path);
        salidaDatos = null;

        try {
            salidaDatos = new FileOutputStream(fichSelector);
            propiedades.store(salidaDatos, "");
        } catch (IOException ex) {
            System.err.println("Error al guardar el fichero del selector" + ex);
        } finally {
            try {
                salidaDatos.close();
            } catch (IOException ex) {
                System.err.println("Error al guardar el fichero del selector++" + ex);
            }
        }
    }
}
