/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tumpiold.app.servidor.modelos;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 *
 * @author Zellyago
 */
public class RunnableGuardado implements Runnable {

    ArrayList<ListaCanciones> listas;
    ArrayList<String> nombres;
    GuardarListas parent;
    boolean sdDisponible = false, sdAccesoEscritura = false;

    public RunnableGuardado(ArrayList<ListaCanciones> listas, ArrayList<String> nombresLista, GuardarListas _parent) {
        this.listas = listas;
        nombres = nombresLista;
        parent = _parent;
        String estado = Environment.getExternalStorageState();
        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;
        } else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponible = true;
            sdAccesoEscritura = false;
        } else {
            sdDisponible = false;
            sdAccesoEscritura = false;
        }
    }

    public void run() {
        try {
            File ruta_sd = Environment.getExternalStorageDirectory();
            String rutaFichero = ruta_sd.getAbsolutePath();
            File ficheroGuardarBorrar = new File(rutaFichero, "ListasGuardadas.txt");
            if (ficheroGuardarBorrar.exists()) {
                ficheroGuardarBorrar.delete();
            }
            File ficheroGuardar = new File(rutaFichero, "ListasGuardadas.txt");

            ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(ficheroGuardar));
            fout.writeObject(nombres);
            fout.writeObject(listas);
            fout.close();
        } catch (FileNotFoundException ex) {
            Log.e("Guardar", "Error al abrir el Stream");
        } catch (IOException ex) {
            Log.e("Guardar", "Error entrada salida");
        }
        parent.terminado = true;
    }
}
