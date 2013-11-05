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

	// private DatagramSocket dsocket;
	// private Thread publicador;
	// private String ip_server=null;

	public ConnectionManager() {
		// listas_canciones=ListasCancionesManager.getInstance();
	}

	public boolean createSocket() throws Exception {
		AsyncTask<Void, Void, Boolean> thread_conectar = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					String ip = "192.168.1.38";
//					ip = InetAddress.getByName("tumpi.net").getHostAddress();
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
			// publicador=new Thread(){
			// @Override
			// public void run(){
			// try {
			// dsocket=new DatagramSocket(8888);
			// dsocket.setBroadcast(true);
			// String identificacion="servidor_socialDj|"+ip_server+"|";
			// while(socket.isBound()){
			// Log.i("Conexion","Escuchando broadcast en "+ip_server);
			// byte[] datos = new byte[50];
			// DatagramPacket paquete=new DatagramPacket(datos,50);
			// dsocket.receive(paquete);
			// String mensaje=new String(paquete.getData(),"utf-8");
			// if("cliente_socialDj".equals(mensaje.split("\\|")[0])){
			// String ip_cliente=paquete.getAddress().getHostAddress();
			// byte[] mensaje_id=identificacion.getBytes("utf-8");
			// DatagramPacket paquete_id=new
			// DatagramPacket(mensaje_id,mensaje_id.length, new
			// InetSocketAddress(ip_cliente,8888));
			// dsocket.send(paquete_id);
			// Log.i("Conexion","Escuchada una solicitud("+ip_server+") de "+ip_cliente);
			// }
			// }
			// } catch (Exception ex) {
			// Log.e("Conexion","Error al hacer broadcast: "+ex.toString());
			// }
			// }
			// };
			// publicador.start();
			// publicador=new ThreadPublicador();
			// publicador.execute();
			// socket.startListenBridge();
			socket.addServerSocketListener(this);
			return socket.isBound();
		}

		return false;
	}

	// public String getIpAddr() {
	// WifiManager wifiManager = (WifiManager)
	// context.getSystemService(context.WIFI_SERVICE);
	// WifiInfo wifiInfo = wifiManager.getConnectionInfo();
	// int ip = wifiInfo.getIpAddress();
	//
	// String ipString = String.format(
	// "%d.%d.%d.%d",
	// (ip & 0xff),
	// (ip >> 8 & 0xff),
	// (ip >> 16 & 0xff),
	// (ip >> 24 & 0xff));
	//
	// return ipString;
	// }
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
	// class ThreadPublicador extends AsyncTask<Void, Void, Boolean> {
	// @Override
	// protected void onPreExecute() {
	// // pd = ProgressDialog.show(context, "Buscando servidor",
	// "Espere unos segundos...", true, false);
	// }
	// @Override
	// protected Boolean doInBackground(Void... params) {
	// try {
	// dsocket=new DatagramSocket(8888);
	// dsocket.setBroadcast(true);
	// String identificacion="servidor_socialDj|"+ip_server+"|";
	// while(socket.isBound()){
	// Log.i("Conexion","Escuchando broadcast en "+ip_server);
	// byte[] datos = new byte[50];
	// DatagramPacket paquete=new DatagramPacket(datos,50);
	// dsocket.receive(paquete);
	// String mensaje=new String(paquete.getData(),"utf-8");
	// if("cliente_socialDj".equals(mensaje.split("\\|")[0])){
	// String ip_cliente=paquete.getAddress().getHostAddress();
	// byte[] mensaje_id=identificacion.getBytes("utf-8");
	// DatagramPacket paquete_id=new
	// DatagramPacket(mensaje_id,mensaje_id.length, new
	// InetSocketAddress(ip_cliente,8888));
	// dsocket.send(paquete_id);
	// Log.i("Conexion","Escuchada una solicitud("+ip_server+") de "+ip_cliente);
	// }
	// }
	// } catch (Exception ex) {
	// Log.e("Conexion","Error al hacer broadcast: "+ex.toString());
	// }
	// return true;
	// }
	// @Override
	// protected void onPostExecute(Boolean result) {
	// // if(pd != null){
	// // pd.dismiss();
	// // }
	// }
	// }

}
