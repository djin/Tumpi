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
    private File fichListas;
    private Properties propiedades;

    public FicherosManager(ListasCancionesManager _listas_manager) {

        listas_manager = _listas_manager;
    }

    public boolean cargarPreferencias() {


        fichListas = new File("fich_listas");
        fichSelector = new File("fich_selector");
        propiedades = new Properties();
        boolean i = false;

        if (fichSelector.exists()) {

            FileInputStream entradaDatos = null;

            try {

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


        if (fichListas.exists()) {

            return false;

        } else {

            return true;
        }
    }

    public void guardarPreferencias() {

        fichSelector = new File("fich_selector");
        propiedades = new Properties();

        propiedades.setProperty("selector", listas_manager.path);

        FileOutputStream selectorStream = null;

        try {
            selectorStream = new FileOutputStream(fichSelector);
            propiedades.store(selectorStream, "");
        } catch (IOException ex) {
            System.err.println("Error al guardar el fichero del selector" + ex);
        } finally {
            try {
                selectorStream.close();
            } catch (IOException ex) {
                System.err.println("Error al guardar el fichero del selector++" + ex);
            }
        }
    }
}
