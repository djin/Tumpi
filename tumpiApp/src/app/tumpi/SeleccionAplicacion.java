package app.tumpi;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import app.tumpi.cliente.lista.android.ListaCanciones;
import app.tumpi.cliente.lista.android.conexion.ConnectionManager;
import app.tumpi.servidor.interfaz.social.ListaPromocionada;
import app.tumpi.util.Installation;

/**
 *
 * @author Sergio Redondo
 */
public class SeleccionAplicacion extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccionar_aplicacion);

        ActionBar action = getActionBar();
        action.hide();

        Button btnClient = (Button) findViewById(R.id.botonIniciarCliente);
        btnClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View btnTumpiConectar) {
            	showTumpiDialog(btnTumpiConectar);
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
        // DO NOT REMOVE
        /*btnServer.setEnabled(false);
        btnServer.setBackground(null);
        btnServer.setText("");*/
    }

    private void showTumpiDialog(final View btnConectarTumpi){
        final String uuid = Installation.id(btnConectarTumpi.getContext());

        LayoutInflater inflater = getInflater();
        final View decoratedTumpiDialog = inflater.inflate(R.layout.style_view_nombre_servidor, null, false);
        final AlertDialog.Builder tumpiDialog = prepareTumpiDialog(inflater,decoratedTumpiDialog);

        tumpiDialog.setView(decoratedTumpiDialog);
        tumpiDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            private ConnectionManager conex;
            public void onClick(DialogInterface dialog, int whichButton) {
                final String uuid = Installation.id(btnConectarTumpi.getContext());
                EditText input = (EditText) decoratedTumpiDialog.findViewById(R.id.txtInputNombreServidor);
                final String value = input.getText().toString();
                if (!value.equals("")) {
                    final ProgressDialog pd = ProgressDialog.show(SeleccionAplicacion.this, "Conectando", "Espere unos segundos...", true, false);
                    conex = new ConnectionManager();
                    btnConectarTumpi.postDelayed(new Runnable() {
                        public void run() {
                            try {
                                if (conex.conectar() && conex.logInBridge(value, uuid)) {
                                    conex.conexion.startListeningServer();
                                    Intent inte = new Intent(SeleccionAplicacion.this, ListaCanciones.class);
                                    startActivity(inte);
                                } else {
                                    pd.dismiss();
                                    conex.conexion.cerrarConexion();
                                    conex = null;
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SeleccionAplicacion.this);
                                    dialogBuilder.setMessage("No existe un Tumpi con este nombre");
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
                    Toast.makeText(tumpiDialog.getContext(), "Escriba un nombre para el servidor", Toast.LENGTH_SHORT).show();
                    //dialog.cancel();
                }
            }
        });

        tumpiDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        tumpiDialog.show();
    }

    private AlertDialog.Builder prepareTumpiDialog(LayoutInflater inflater, View decoratedTumpiDialog){
    	AlertDialog.Builder tumpiDialog = new AlertDialog.Builder(SeleccionAplicacion.this);
        tumpiDialog.setView(decoratedTumpiDialog);
        return tumpiDialog;
    }

    private LayoutInflater getInflater(){
    	return (LayoutInflater) SeleccionAplicacion.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
