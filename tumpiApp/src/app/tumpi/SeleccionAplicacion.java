package app.tumpi;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import app.tumpi.cliente.lista.android.ListaCanciones;
import app.tumpi.cliente.lista.android.PantallaPrincipal;
import static app.tumpi.cliente.lista.android.PantallaPrincipal.pd;
import app.tumpi.cliente.lista.android.conexion.ConnectionManager;
import app.tumpi.servidor.interfaz.social.ListaPromocionada;

/**
 *
 * @author Sergio Redondo
 */
public class SeleccionAplicacion extends Activity {

    public SeleccionAplicacion() {
        ConnectionManager conex;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccionar_aplicacion);
        ActionBar action = getActionBar();
        action.hide();
        Button btnClient = (Button) findViewById(R.id.botonIniciarCliente);
        btnClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(SeleccionAplicacion.this);
                LayoutInflater inflater = (LayoutInflater) SeleccionAplicacion.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View vi = inflater.inflate(R.layout.style_view_nombre_servidor, null, false);
                alert.setView(vi);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    private ConnectionManager conex;
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText input = (EditText) vi.findViewById(R.id.txtInputNombreServidor);
                        final String value = input.getText().toString();
                        if (!value.equals("")) {
                            pd = ProgressDialog.show(SeleccionAplicacion.this, "Conectando", "Espere unos segundos...", true, false);
                            conex = new ConnectionManager();
                            v.postDelayed(new Runnable() {
                                public void run() {
                                    try {
                                        if (conex.conectar() && conex.logInBridge(value)) {
                                            conex.conexion.startListeningServer();
                                            Intent inte = new Intent(SeleccionAplicacion.this, ListaCanciones.class);
                                            startActivity(inte);
                                        } else {
                                            pd.dismiss();
                                            conex.conexion.cerrarConexion();
                                            conex = null;
                                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SeleccionAplicacion.this);
                                            dialogBuilder.setMessage("No existe un servidor publicado con este nombre o no tiene conexion con la central de socialDj");
                                            dialogBuilder.setTitle("Error de conexion");
                                            dialogBuilder.setPositiveButton("Aceptar", new android.content.DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialogBuilder.show();
                                        }

                                    } catch (Exception ex) {
                                        pd.dismiss();
                                        conex = null;
                                        Toast.makeText(SeleccionAplicacion.this, "Ha ocurrido un error al intentar conectar, intentelo mas tarde: \n" + ex.toString(), Toast.LENGTH_LONG).show();
                                    }
                                    pd.dismiss();
                                }
                            }, 100);
                        } else {
                            Toast.makeText(alert.getContext(), "Escriba un nombre para el servidor", Toast.LENGTH_SHORT).show();
                            //dialog.cancel();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
        Button btnServer = (Button) findViewById(R.id.botonIniciarServidor);
        btnServer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent inte = new Intent(SeleccionAplicacion.this, ListaPromocionada.class);
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inte);
            }
        });
    }
}
