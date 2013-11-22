package app.tumpi;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import app.tumpi.servidor.interfaz.social.ListaPromocionada;
import app.tumpi.servidor.modelo.datos.ListasManager;

/**
 *
 * @author Sergio Redondo
 */
public class SeleccionAplicacion extends ActionBarActivity {

	private View.OnClickListener btnServerListener;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccionar_aplicacion);
        
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);

        ActionBar action = getSupportActionBar();
        action.hide();

        Button btnClient = (Button) findViewById(R.id.botonIniciarCliente);
        btnClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View btnTumpiConectar) {
            	showTumpiDialog(btnTumpiConectar, 
            			new ConectarClienteRunnable(SeleccionAplicacion.this));
            }
        });

        Button btnServer = (Button) findViewById(R.id.botonIniciarServidor);
        
        
        
        ListasManager manager = ListasManager.getInstance();
        if(manager.conectado) {
        	btnServer.setText("Entrar en " + manager.nick);
        	btnServerListener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent inte = new Intent(SeleccionAplicacion.this,
							ListaPromocionada.class);
					inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(inte);
				}
			};
        }else {
        	btnServer.setText("Crea un Tumpi");
        	btnServerListener = new View.OnClickListener() {
                public void onClick(View btnServerConectar) {
                	showTumpiDialog(btnServerConectar, 
                			new ConectarServidorRunnable(SeleccionAplicacion.this));
                }
            };
        }
        btnServer.setOnClickListener(btnServerListener);
        
        // DO NOT REMOVE
        /*btnServer.setEnabled(false);
        btnServer.setBackground(null);
        btnServer.setText("");*/
    }
    
    private void showTumpiDialog(final View btnConectarTumpi,final ValueInterface choice){
        LayoutInflater inflater = getInflater();
        final View decoratedTumpiDialog = inflater.inflate(R.layout.style_view_nombre_servidor, null, false);
        final AlertDialog.Builder tumpiDialog = prepareTumpiDialog(inflater,decoratedTumpiDialog);

        tumpiDialog.setView(decoratedTumpiDialog);
        tumpiDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                EditText input = (EditText) decoratedTumpiDialog.findViewById(R.id.txtInputNombreServidor);
                final String value = input.getText().toString();
                if (!value.equals("")) {
                	choice.setValue(value);
                    btnConectarTumpi.postDelayed(choice, 100);
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
    
    public static interface ValueInterface extends Runnable {
    	public void setValue(String value);
    }    
    
}
