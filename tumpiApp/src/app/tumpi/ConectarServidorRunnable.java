package app.tumpi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;
import app.tumpi.SeleccionAplicacion.ValueInterface;
import app.tumpi.cliente.lista.android.ListaCanciones;
import app.tumpi.servidor.interfaz.social.ListaPromocionada;
import app.tumpi.servidor.modelo.datos.ListasManager;
import app.tumpi.util.Installation;


public class ConectarServidorRunnable implements ValueInterface {
	private ListasManager manager = ListasManager.getInstance();
	private String value, uuid;
	private ProgressDialog pd;
	private Context context;

	public ConectarServidorRunnable(Context context) {
		this.uuid = Installation.id(context);
		this.context = context;
	}

	@Override
	public void run() {
		try {
			pd = ProgressDialog.show(context, "Conectando",
					"Espere unos segundos...", true, false);
			if (manager.logInBridge(value, uuid)) {
				Intent inte = new Intent(context, ListaPromocionada.class);
				inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(inte);
			} else {
				pd.dismiss();
				manager.cerrarConexion();
				manager = null;
				AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
						context);
				dialogBuilder.setMessage("Error al crear el Tumpi");
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
			manager = null;
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
