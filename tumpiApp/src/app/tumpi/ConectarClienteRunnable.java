package app.tumpi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;
import app.tumpi.SeleccionAplicacion.ValueInterface;
import app.tumpi.cliente.lista.android.ListaCanciones;
import app.tumpi.cliente.lista.android.conexion.ConnectionManager;
import app.tumpi.util.Installation;

public class ConectarClienteRunnable implements ValueInterface {

	private ConnectionManager conex = new ConnectionManager();
	private String value, uuid;
	private ProgressDialog pd;
	private Context context;

	public ConectarClienteRunnable(Context context) {
		this.uuid = Installation.id(context);
		this.context = context;
	}

	@Override
	public void run() {
		try {
			pd = ProgressDialog.show(context, "Conectando",
					"Espere unos segundos...", true, false);
			if (conex.conectar() && conex.logInBridge(value, uuid)) {
				ConnectionManager.conexion.startListeningServer();
				Intent inte = new Intent(context, ListaCanciones.class);
				context.startActivity(inte);
			} else {
				pd.dismiss();
				ConnectionManager.conexion.cerrarConexion();
				conex = null;
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
						context);
				dialogBuilder.setMessage("No existe un Tumpi con este nombre");
				dialogBuilder.setTitle("Error de conexion");
				dialogBuilder.setPositiveButton("Aceptar",
						new android.content.DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				dialogBuilder.show();
			}

		} catch (Exception ex) {
			pd.dismiss();
			conex = null;
			Toast.makeText(
					context,
					"Ha ocurrido un error al intentar conectar, intentelo mas tarde: \n"
							+ ex.toString(), Toast.LENGTH_LONG).show();
		}
		pd.dismiss();
	}

	public void setValue(String value) {
		this.value = value;
	}

}
