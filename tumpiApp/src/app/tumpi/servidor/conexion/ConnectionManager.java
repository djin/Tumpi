package app.tumpi.servidor.conexion;

import android.os.AsyncTask;
import android.util.Log;
import java.net.*;

/**
 *
 * @author 66785270
 */
public class ConnectionManager implements ServerSocketListener {

	// ListasCancionesManager listas_canciones;
	public SocketServidor socket = null;

	public ConnectionManager() {
		// listas_canciones=ListasCancionesManager.getInstance();
	}

	public boolean createSocket() throws Exception {
		AsyncTask<Void, Void, Boolean> thread_conectar = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					String ip = "";
					ip = InetAddress.getByName("tumpi.net").getHostAddress();
					socket = new SocketServidor(ip, 2244);
				} catch (Exception ex) {
					Log.e("Conexion",
							"Error al crear el socket: " + ex.toString());
					return false;
				}
				return true;
			}
		};
		if (thread_conectar.execute().get()) {
			socket.addServerSocketListener(this);
			return socket.isBound();
		}

		return false;
	}
	public void closeSocket() throws Exception {
		// publicador.cancel(true);
		// publicador.interrupt();
		socket.removeServerSocketListener(this);
		socket.closeSocket();
		socket = null;
	}

	@Override
	public void onMessageReceived(String ip, String men) {

	}

	@Override
	public void onClientConnected(String ip) {
		try {
			Log.i("Conexion", "Cliente conectado: " + ip
					+ "\nNumero de clientes: " + socket.getClientsCount());
		} catch (Exception ex) {
			Log.e("Conexion", ex.toString());
		}
	}

	@Override
	public void onClientDisconnected(String ip) {
		Log.i("Conexion", "Cliente desconectado " + "\nNumero de clientes: "
				+ socket.getClientsCount());
	}
}
