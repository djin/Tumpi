package app.tumpi.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import app.tumpi.servidor.interfaz.social.Notificacion;
import app.tumpi.servidor.modelo.datos.ListasManager;

public class MyService extends Service {
	private static BroadcastReceiver m_ScreenOffReceiver;
	private ListasManager manager = ListasManager.getInstance();
	private Notificacion notificacion;
	
	public MyService() {
		// TODO Auto-generated constructor stub
		notificacion = manager.notificacion;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		registerScreenOffReceiver();
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(notificacion.getReceiver());
		m_ScreenOffReceiver = null;
	}

	private void registerScreenOffReceiver() {
		registerReceiver(notificacion.getReceiver(), notificacion.getFilter());
	}

	
}
