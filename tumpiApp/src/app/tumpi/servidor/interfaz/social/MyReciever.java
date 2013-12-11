package app.tumpi.servidor.interfaz.social;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import app.tumpi.servidor.modelo.datos.ListasManager;

public class MyReciever extends BroadcastReceiver {

	ListasManager manager = ListasManager.getInstance();

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals("next")) {
			manager.notificacion.next();
		}
		if (intent.getAction().equals("play")) {
			manager.notificacion.play();
		}
		if (intent.getAction().equals("close")) {
			manager.notificacion.close();
		}
	}

}
