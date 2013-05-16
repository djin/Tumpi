/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ficheros;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelos.ListaCanciones;
import modelos.ListasCancionesManager;

/**
 *
 * @author 66786575
 */
public class FicherosManager {

    private static ArrayList<ListaCanciones> listas;
    private static ListasCancionesManager listas_manager;
    private static File fichSelector;
    private static InputStream fichEntrada;
    private static InputStream bufferEntrada;
    private static OutputStream fichSalida;
    private static OutputStream bufferSalida;
    private static Properties propiedades;
    private static FileInputStream entradaDatos;
    private static FileOutputStream salidaDatos;
    private static ObjectOutputStream sesion_out;
    private static ObjectInputStream sesion_in;

    public static ArrayList cargarSesion() {
        try {
            fichEntrada = new FileInputStream("fich_sesion");
        } catch (FileNotFoundException ex) {
            System.err.println("No se pudo abrir el fichero de sesion:" +ex);
        }
        listas = new ArrayList();

        bufferEntrada = null;
        sesion_in = null;

        try {

            bufferEntrada = new BufferedInputStream(fichEntrada);
            sesion_in = new ObjectInputStream(bufferEntrada);

            while (sesion_in.available() != 0 && sesion_in != null) {
                try {
                    listas.add((ListaCanciones) sesion_in.readObject());
                    System.out.println("hola2");
                } catch (ClassNotFoundException ex) {
                    System.err.println("Objeto no encontrado: " + ex);
                }
            }

        } catch (IOException ex) {
                System.err.println("Error al cargar el fichero de listas" + ex);
        } finally {
            try {
                
                sesion_in.close();
                bufferEntrada.close();
                fichEntrada.close();

            } catch (IOException ex) {
                System.err.println("Error al cargar el fichero de listas++" + ex);
            }
        }

        return listas;
    }

    public static void guardarSesion(ArrayList _listas) {

        listas = _listas;
        try {
            fichSalida = new FileOutputStream("fich_sesion");
        } catch (FileNotFoundException ex) {
            System.err.println("No se pudo guardar el fichero de sesion:" +ex);
        }
        bufferSalida = null;
        sesion_out = null;

        try {
            bufferSalida = new BufferedOutputStream(fichSalida);
            sesion_out = new ObjectOutputStream(bufferSalida);
            for (ListaCanciones l : listas) {
                sesion_out.writeObject(l);
            }

        } catch (IOException ex) {
            System.err.println("Error al guardar el fichero de sesion" + ex);
        } finally {
            try {
                sesion_out.close();
            } catch (IOException ex) {
                System.err.println("Error al guardar el fichero de sesion++" + ex);
            }
        }
    }

    public static void cargarPreferencias() {

        listas_manager = ListasCancionesManager.getInstance();
        fichSelector = new File("fich_selector");
        propiedades = new Properties();
        entradaDatos = null;
        boolean i = false;

        if (fichSelector.exists()) {

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

            listas_manager.setPath(propiedades.getProperty("selector"));

        } else {

            listas_manager.setPath("C:\\Users\\66785361\\Documents");
        }
    }

    public static void guardarPreferencias() {

        fichSelector = new File("fich_selector");
        propiedades = new Properties();

        propiedades.setProperty("selector", listas_manager.getPath());
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
