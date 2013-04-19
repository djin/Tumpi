/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import java.net.*;

/**
 *
 * @author 66785270
 */
public class ConnectionManager implements ServerSocketListener{
    
    //ListasCancionesManager listas_canciones;
    public static SocketServidor socket=null;
    private DatagramSocket dsocket;
    private Thread publicador;
    private String ip_server=null;
    private Context context;
    int port; 
    
    public ConnectionManager(Context c){
        context=c;
        //listas_canciones=ListasCancionesManager.getInstance();
    }
    
    public boolean createSocket(final int _port) throws Exception{
        port=_port;
        AsyncTask<Void,Void,Boolean> thread_conectar=new AsyncTask<Void,Void,Boolean>(){

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    ip_server=getIpAddr();
                    socket=new SocketServidor(port);
                } catch (Exception ex) {
                    Log.e("Conexion", "Error al crear el socket: "+ex.toString());
                    return false;
                }
                return true;
            }
        };
        if(thread_conectar.execute().get()){
            publicador=new Thread(){
                @Override
                public void run(){
                    try {
                        dsocket=new DatagramSocket(8888);
                        dsocket.setBroadcast(true);
                        String identificacion="servidor_socialDj|"+ip_server+"|";
                        while(socket.isBound()){
                            Log.i("Conexion","Escuchando broadcast en "+ip_server);
                            byte[] datos = new byte[50];
                            DatagramPacket paquete=new DatagramPacket(datos,50);
                            dsocket.receive(paquete);
                            String mensaje=new String(paquete.getData(),"utf-8");
                            if("cliente_socialDj".equals(mensaje.split("\\|")[0])){
                                String ip_cliente=paquete.getAddress().getHostAddress();
                                byte[] mensaje_id=identificacion.getBytes("utf-8");
                                DatagramPacket paquete_id=new DatagramPacket(mensaje_id,mensaje_id.length, new InetSocketAddress(ip_cliente,8888));
                                dsocket.send(paquete_id);
                                Log.i("Conexion","Escuchada una solicitud("+ip_server+") de "+ip_cliente);
                            }
                        }
                    } catch (Exception ex) {
                        Log.e("Conexion","Error al hacer broadcast: "+ex.toString());
                    }
                }
            };
            publicador.start();
//            publicador=new ThreadPublicador();
//            publicador.execute();
            socket.startSearchClients();
            socket.addServerSocketListener(this);
            return socket.isBound();
        }
        
        return false;        
    }
    public String getIpAddr() {
       WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
       WifiInfo wifiInfo = wifiManager.getConnectionInfo();
       int ip = wifiInfo.getIpAddress();

       String ipString = String.format(
       "%d.%d.%d.%d",
       (ip & 0xff),
       (ip >> 8 & 0xff),
       (ip >> 16 & 0xff),
       (ip >> 24 & 0xff));

       return ipString;
    }
    public void closeSocket() throws Exception{
        //publicador.cancel(true);
        publicador.interrupt();
        socket.removeServerSocketListener(this);
        socket.closeSocket();
        socket=null;
        port=0;
    }
    
    @Override
    public void onMessageReceived(String ip, String men) {
        try {
            String message=men;
            Log.i("Conexion", "Mensaje de "+ip+" : "+men);
            //ArrayList<Integer> votos_cliente=listas_canciones.votos_cliente.get(ip);
            int tipo=Integer.parseInt(message.split("\\|")[0]);
            message=message.split("\\|")[1]; 
            switch(tipo){
                case 0:
                        socket.enviarMensajeServer(ip,"0|empty");
//                    if(listas_canciones.lista_sonando!=null){
//                        Log.i("Conexion","0|"+listas_canciones.lista_sonando.toString());
//                        socket.enviarMensajeServer(ip,"0|"+listas_canciones.lista_sonando.toString());
//                    }
//                    if(listas_canciones.cancion_sonando!=null)
//                        socket.enviarMensajeServer(ip,"4|"+listas_canciones.cancion_sonando.toString());
//                    if(votos_cliente!=null){
//                        for(int id_cancion:votos_cliente)
//                            socket.enviarMensajeServer(ip,"1|"+id_cancion);
//                    }
                    break;
                case 1:
//                    if(listas_canciones.procesarVoto(Integer.parseInt(message),true)){
//                        socket.enviarMensajeServer(ip,"1|"+message);
//                        if(votos_cliente!=null && !votos_cliente.contains(Integer.decode(message)))
//                           votos_cliente.add(Integer.parseInt(message)); 
//                    }
//                    else
//                        socket.enviarMensajeServer(ip,"1|0");   
                    break;
                case 3:
//                    if(listas_canciones.procesarVoto(Integer.parseInt(message),false)){
//                        socket.enviarMensajeServer(ip,"3|"+message);
//                        if(votos_cliente!=null)
//                            votos_cliente.remove(Integer.decode(message));
//                    }
//                    else
//                        socket.enviarMensajeServer(ip,"3|0"); 
                    break;
            }
        } catch (Exception ex) {
            //FramePrincipal.log("Error al procesar mensaje recivido: "+ex.toString());
        }
    }

    @Override
    public void onClientConnected(String ip) {
        try{
            Log.i("Conexion","Cliente conectado: "+ip+"\nNumero de clientes: "+socket.getClientsCount());
//            if(listas_canciones.votos_cliente.get(ip)==null){
//                listas_canciones.votos_cliente.put(ip, new ArrayList<Integer>());
//                FramePrincipal.log("Hash de votos creado para el cliente");
//            }
        }catch(Exception ex){
            Log.e("Conexion",ex.toString());
        }
    }

    @Override
    public void onClientDisconnected(String ip) {
        Log.i("Conexion","Cliente desconectado "+"\nNumero de clientes: "+socket.getClientsCount());
    }
//    class ThreadPublicador extends AsyncTask<Void, Void, Boolean> {
//        @Override
//        protected void onPreExecute() {
//           // pd = ProgressDialog.show(context, "Buscando servidor", "Espere unos segundos...", true, false);
//        }        
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            try {
//                dsocket=new DatagramSocket(8888);
//                dsocket.setBroadcast(true);
//                String identificacion="servidor_socialDj|"+ip_server+"|";
//                while(socket.isBound()){
//                    Log.i("Conexion","Escuchando broadcast en "+ip_server);
//                    byte[] datos = new byte[50];
//                    DatagramPacket paquete=new DatagramPacket(datos,50);
//                    dsocket.receive(paquete);
//                    String mensaje=new String(paquete.getData(),"utf-8");
//                    if("cliente_socialDj".equals(mensaje.split("\\|")[0])){
//                        String ip_cliente=paquete.getAddress().getHostAddress();
//                        byte[] mensaje_id=identificacion.getBytes("utf-8");
//                        DatagramPacket paquete_id=new DatagramPacket(mensaje_id,mensaje_id.length, new InetSocketAddress(ip_cliente,8888));
//                        dsocket.send(paquete_id);
//                        Log.i("Conexion","Escuchada una solicitud("+ip_server+") de "+ip_cliente);
//                    }
//                }
//            } catch (Exception ex) {
//                Log.e("Conexion","Error al hacer broadcast: "+ex.toString());
//            }
//            return true;
//        }
//        @Override
//        protected void onPostExecute(Boolean result) {
//          //  if(pd != null){
//          //      pd.dismiss();
//          //  }
//        }
//    }
    
}
