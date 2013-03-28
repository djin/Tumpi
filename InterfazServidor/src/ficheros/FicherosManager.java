/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ficheros;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
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

    public boolean cargarPreferencias() {


        fichSesion = new File("fich_listas");
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
                } catch (IOException ex) {
                    System.err.println("Error al cargar el fichero de listas++" + ex);
                }
            }

            return false;

        } else {

            return true;
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
