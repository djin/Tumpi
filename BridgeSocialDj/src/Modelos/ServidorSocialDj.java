/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.util.ArrayList;

/**
 *
 * @author 66785270
 */
public class ServidorSocialDj {
    public ArrayList<ClienteSocialDj> clientes;
    public String nombre;
    public String id;
    public ServidorSocialDj(String name,String id){
        clientes=new ArrayList();
        nombre=name;
        this.id=id;
    }
    
    public boolean isClient(String id){
        for(ClienteSocialDj cliente:clientes)
            if(cliente.id.equals(id))
                return true;
        return false;
    }
    public ClienteSocialDj getClient(String id){
        for(ClienteSocialDj client:clientes)
            if(client.id.equals(id))
                return client;
        return null;
    }
}
