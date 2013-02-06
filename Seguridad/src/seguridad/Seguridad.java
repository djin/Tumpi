/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 *
 * @author 66785361
 */
public class Seguridad {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Metodos seg = new Metodos();
        seg.volcarDatos();
    }

    /**
     * @return the metaData
     */
}


class Metodos
{
    //Statement s;
    ResultSet rs;
    String[][] datos=null;
    String[][] diccionario=null;
    Connection conexion;
    ResultSetMetaData metaData;
    int numFilas, numColum;
    private String pass, user;
    private String[] arrayUser, arrayPass;
    public void volcarDatos()
    {
        
        try
        {
            
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            conexion = DriverManager.getConnection("jdbc:derby://localhost/seguridad", "seguridad"
                    + "", "seguridad");
            
             
            //PreparedStatement s = conexion.prepareStatement("select * from APP.SEGURIDAD");
            Statement st = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("select * from APP.SEGURIDAD");
          //  rs = s.executeQuery();
           datos= volcarMemoria(rs);
           
           try
        {
            //pedire el usuario y contraseña del servidor
            BufferedReader lectura = new BufferedReader(new InputStreamReader(System.in));
            
            System.out.println("Introduce el nombre de usuario del servidor: ");
            user=lectura.readLine();

            System.out.println("Introduce la contraseña del usuario del servidor: ");
            pass=lectura.readLine();
            
            comprobarUserPass(user, pass);
             
        }
        catch(Exception e)
         {
                
         }
            
            conexion.close();
        }
        catch (Exception e)
        {
            System.err.println(e);
            System.exit(0);
        }
    }
    
    private String[][] volcarMemoria(ResultSet rs)
    {   
        try
        {
            
            metaData = rs.getMetaData();
            numColum = metaData.getColumnCount();//almaceno las columnas del rs
            numFilas = (getFilas(rs));//almaceno las filas del rs
            //inicializo el array con las filas y columnas que sacamos anteriormente
            diccionario = new String[numFilas][numColum];
            rs.absolute(1);//ponemos el cursos del rs en la primera fila
            for(int i=0;i<numFilas;i++)
                for(int j=0;j<numColum;j++)
                {
                   rs.absolute(i+1);//voy actualizando el cursor
                    //almaceno en el array bidimensional la columna correspondiente del rs
                    diccionario[i][j]=rs.getString(j+1);
                    //System.out.println(diccionario[i][j]);   
                }
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
        return diccionario;
    }

    /**
     * @return the metaData
     */
    
    private int getFilas(ResultSet rs)
    {
        int fil=0;
       
            try
            {
                while(rs.next())
                {
                    fil=rs.getRow();
                }
            }
            catch(Exception e)
            {
                System.err.println("Fallo al capturar el numero de filas.");
            }
            return fil;
    }
    
    private void comprobarUserPass(String user, String pass)
    {
        int flag=0;
        for (int i=0;i<numFilas;i++)
        {
            if (user.equals(diccionario[i][0]))
            {
                if(pass.equals(diccionario[i][1]))
                {
                    flag=0;
                    break;
                }
                else
                {
                    flag=1;
                    break;
                }
            }
            else
            {
                flag=2;
                break;
            }
        }
        if (flag==0)
            System.out.println("Usuario identificado correctamente");
        if (flag==1)
            System.err.println("La contraseña introducida no es valida.");
        if(flag==2)
           System.err.println("El usuario introducido no existe."); 
        
    }
}
