package app.tumpi.servidor.conexion;

import android.os.AsyncTask;
import android.util.Log;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author 66785270
 */
public class SocketServidor {

	private Socket socket_server = null;
	// private InetAddress ip_server=InetAddress.getLocalHost();
	// private int puerto=0;
	// private ThreadBuscarClientes thread_buscar_clientes=null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	// private ArrayList<Cliente> clientes=new ArrayList();
	private ArrayList<String> clientes = new ArrayList();
	private String nick = "";
	private Thread thread_server;
	private ArrayList<ServerSocketListener> listeners = new ArrayList();

	public SocketServidor(String ip, int port) throws IOException {
		// puerto=port;
		socket_server = new Socket(ip, port);
		if (socket_server.isConnected()) {
			input = new DataInputStream(socket_server.getInputStream());
			output = new DataOutputStream(socket_server.getOutputStream());
		}
	}

	public boolean isBound() {
		return socket_server.isBound();
	}

	// public void startSearchClients(){
	// thread_buscar_clientes=new ThreadBuscarClientes();
	// thread_buscar_clientes.execute();
	// }
	// public void finishSearchClients(){
	// thread_buscar_clientes.cancel(true);
	// }

	public void enviarMensajeServer(String id_cliente, String mensaje)
			throws IOException {
		try {
			output.writeUTF("c:" + id_cliente + "|" + mensaje);
		} catch (IOException ex) {
			System.out.println("Error al enviar el mensaje al bridge: "
					+ ex.toString());
		}
	}

	public int getClientsCount() {
		return clientes.size();
	}

	public boolean logIn(final String nick, final String uuid) throws Exception {
		AsyncTask<Void, Void, Boolean> thread_log = new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					output.writeUTF("s:log|" + nick + "|" + uuid);
					String resp = input.readUTF();
					Log.i("Conexion", "Server: " + resp);
					if ("b:log|1".equals(resp)) {
						SocketServidor.this.nick = nick;
						return true;
					}
					return false;
				} catch (IOException ex) {
					Log.e("Conexion",
							"Error al mandar peticion login: " + ex.toString());
					return false;
				}
			}
		};
		return thread_log.execute().get(5, TimeUnit.SECONDS);
	}

	private void mensajeRecivido(String mensaje) {
		if ("b".equals(mensaje.split("\\:")[0])) {
			String tipo = mensaje.substring(mensaje.indexOf(":") + 1,
					mensaje.indexOf("|"));
			Log.i("Conexion", "Bridge: " + mensaje);
			if ("client_on".equals(tipo)) {
				clientes.add(mensaje.substring(mensaje.indexOf("|") + 1));
				final String clientUUID = mensaje
						.substring(mensaje.indexOf("|") + 1);
				fireClientConnectedEvent(clientUUID);
			} else if ("client_off".equals(tipo)) {
				String id_cliente = mensaje.substring(mensaje.indexOf("|") + 1);
				clientes.remove(id_cliente);
				fireClientDisconnectedEvent(id_cliente);
			}
		} else {
			String id_cliente = mensaje.substring(0, mensaje.indexOf("|"));
			mensaje = mensaje.substring(mensaje.indexOf("|") + 1);
			fireMessageReceivedEvent(id_cliente, mensaje);
		}
	}

	public void startListenBridge() {
		thread_server = new Thread() {
			@Override
			public void run() {
				while (thread_server != null && socket_server.isConnected()) {
					String mensaje = "";
					try {
						mensaje = input.readUTF();
						mensajeRecivido(mensaje);
					} catch (IOException ex) {
						try {
							closeSocket();
							fireMessageReceivedEvent("", "exit");
						} catch (IOException ex1) {
							Log.e("Conexion",
									"Error al cerrar la conexion con el bridge: "
											+ ex);
						}
						thread_server = null;
					}
				}
			}
		};
		thread_server.start();
	}

	public void closeSocket() throws IOException {
		// finishSearchClients();
		// for(Cliente cliente : clientes){
		// cliente.enviarMensaje("exit");
		// cliente.finishListenClient();
		// }
		if (!socket_server.isClosed()) {
			output.writeUTF("s:exit");
			thread_server = null;
			socket_server.close();
		}
	}

	public void addServerSocketListener(ServerSocketListener listener) {
		listeners.add(listener);
	}

	public void removeServerSocketListener(ServerSocketListener listener) {
		listeners.remove(listener);
	}

	public void fireClientConnectedEvent(String uuid) {
		for (ServerSocketListener listener : listeners) {
			listener.onClientConnected(uuid);
		}
	}

	public void fireClientDisconnectedEvent(String id) {
		for (ServerSocketListener listener : listeners) {
			listener.onClientDisconnected(id);
		}
	}

	public void fireMessageReceivedEvent(String id, String message) {
		for (ServerSocketListener listener : listeners) {
			listener.onMessageReceived(id, message);
		}
	}
}
