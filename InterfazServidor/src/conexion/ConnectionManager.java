package conexion;

import java.net.InetAddress;

/**
 *
 * @author 66785270
 */
public class ConnectionManager implements ServerSocketListener {

    public SocketServidor socket = null;

    public ConnectionManager() {
        //listas_canciones=ListasCancionesManager.getInstance();
    }

    public boolean createSocket() throws Exception {
        boolean creado;
        try {
            String ip;
            ip = InetAddress.getByName("tumpi.net").getHostAddress();
            socket = new SocketServidor(ip, 2244);
            creado = true;
        } catch (Exception ex) {
            System.out.println("Error al crear el socket: " + ex.toString());
            creado = false;
        }
        if (creado) {
            socket.addServerSocketListener(this);
            return socket.isBound();
        }
        return false;
    }

    public void closeSocket() throws Exception {
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
            System.out.println("Cliente conectado: " + ip + "\nNumero de clientes: " + socket.getClientsCount());
        } catch (Exception ex) {
            System.err.append("No se pudo conectar al cliente: "+ex);
        }
    }

    @Override
    public void onClientDisconnected(String ip) {
        System.out.println("Cliente desconectado " + "\nNumero de clientes: " + socket.getClientsCount());
    }
    
    public SocketServidor getSocket (){
        return socket;
    }
}
//    ListasCancionesManager listas_canciones;
//    public static SocketServidor socket=null;
//    private DatagramSocket dsocket;
//    private Thread publicador;
//    private InetAddress ip_server=null;
//    int port; 
//    
//    public ConnectionManager(){
//        
//        listas_canciones=ListasCancionesManager.getInstance();
//    }
//    
//    public boolean createSocket(final int _port) throws Exception{
//        socket=new SocketServidor(_port);
//        Enumeration e = NetworkInterface.getNetworkInterfaces();
//         while(e.hasMoreElements()) {
//            NetworkInterface ni = (NetworkInterface) e.nextElement();
//            if(!ni.isLoopback() && !ni.isVirtual() && ni.isUp() && ni.getName().contains("net")){
//                Enumeration e2 = ni.getInetAddresses();
//                while (e2.hasMoreElements()){
//                   try{
//                       Inet4Address ip=(Inet4Address) e2.nextElement();
//                       ip_server=ip;
//                   }catch(Exception ex2){                           
//                   }
//                }
//            }
//         }
//        publicador=new Thread(){
//            @Override
//            public void run(){
//                try {
//                    dsocket=new DatagramSocket(8888);
//                    dsocket.setBroadcast(true);
//                    String identificacion="servidor_socialDj|"+ip_server.getHostAddress()+"|";
//                    while(socket.isBound()){
//                        System.out.println("Escuchando broadcast en "+ip_server.getHostAddress());
//                        byte[] datos = new byte[50];
//                        DatagramPacket paquete=new DatagramPacket(datos,50);
//                        dsocket.receive(paquete);                
//                        String mensaje=new String(paquete.getData(),"utf-8");
//                        if("cliente_socialDj".equals(mensaje.split("\\|")[0])){
//                            String ip_cliente=mensaje.split("\\|")[1];
//                            byte[] mensaje_id=identificacion.getBytes("utf-8");
//                            DatagramPacket paquete_id=new DatagramPacket(mensaje_id,mensaje_id.length, new InetSocketAddress(ip_cliente,8888));
//                            dsocket.send(paquete_id);
//                            System.out.println("Escuchada una solicitud de "+ip_cliente);
//                        }
//                    }
//                }catch(Exception ex){
//                    System.out.println("Error al escuchar broadcast: "+ex.toString());
//                }
//            }
//        };
//        publicador.start();
//        port=_port;
//        socket.startSearchClients();
//        socket.addServerSocketListener(this);
//        return socket.isBound();
//    }
//    
//    public void closeSocket() throws Exception{
//        publicador=null;
//        socket.removeServerSocketListener(this);
//        socket.closeSocket();
//        socket=null;
//        port=0;
//    }
//    
//    @Override
//    public void onMessageReceived(String ip, String men) {
//        FramePrincipal.log(ip+": "+men);
//        try {
//            String message=men;
//            ArrayList<Integer> votos_cliente=listas_canciones.votos_cliente.get(ip);
//            int tipo=Integer.parseInt(message.split("\\|")[0]);
//            message=message.split("\\|")[1]; 
//            switch(tipo){
//                case 0:
//                    if(listas_canciones.lista_sonando!=null){
//                        System.out.println("0|"+listas_canciones.lista_sonando.toString());
//                        socket.enviarMensajeServer(ip,"0|"+listas_canciones.lista_sonando.toString());
//                    }
//                    if(listas_canciones.cancion_sonando!=null)
//                        socket.enviarMensajeServer(ip,"4|"+listas_canciones.cancion_sonando.toString());
//                    if(votos_cliente!=null){
//                        for(int id_cancion:votos_cliente)
//                            socket.enviarMensajeServer(ip,"1|"+id_cancion);
//                    }
//                    break;
//                case 1:
//                    if(listas_canciones.procesarVoto(Integer.parseInt(message),true)){
//                        socket.enviarMensajeServer(ip,"1|"+message);
//                        if(votos_cliente!=null && !votos_cliente.contains(Integer.decode(message)))
//                           votos_cliente.add(Integer.parseInt(message)); 
//                    }
//                    else
//                        socket.enviarMensajeServer(ip,"1|0");   
//                    break;
//                case 3:
//                    if(listas_canciones.procesarVoto(Integer.parseInt(message),false)){
//                        socket.enviarMensajeServer(ip,"3|"+message);
//                        if(votos_cliente!=null)
//                            votos_cliente.remove(Integer.decode(message));
//                    }
//                    else
//                        socket.enviarMensajeServer(ip,"3|0"); 
//                    break;
//            }
//        } catch (Exception ex) {
//            FramePrincipal.log("Error al procesar mensaje recivido: "+ex.toString());
//        }
//    }
//
//    @Override
//    public void onClientConnected(String ip) {
//        try{
//            FramePrincipal.log("Cliente conectado: "+ip+"\nNumero de clientes: "+socket.getClientsCount());
//            if(listas_canciones.votos_cliente.get(ip)==null){
//                listas_canciones.votos_cliente.put(ip, new ArrayList<Integer>());
//                FramePrincipal.log("Hash de votos creado para el cliente");
//            }
//        }catch(Exception ex){
//            FramePrincipal.log(ex.toString());
//        }
//    }
//
//    @Override
//    public void onClientDisconnected(String ip) {
//        FramePrincipal.log("Cliente desconectado "+"\nNumero de clientes: "+socket.getClientsCount());
//    }
