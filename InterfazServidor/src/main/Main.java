
package main;

import tablas.Tabla;
import tablas.ModeloTabla;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;



/**
 *
 * @author 66786575
 */
public class Main extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {

                JFrame main = new Main();
                main.setVisible(true);
            }
        });  
    }
    
    private JPanel panel = (JPanel) this.getContentPane();
    
    private ModeloTabla modeloTablaSonando;
    private ModeloTabla modeloTablaPendientes;
    
    private String[] nombresColumnaSonando = {"Cancion", "Votos"};
    private String[] nombresColumnaPendientes = {"Cancion"};
    private ArrayList <String>  contenidos;
        
    private Tabla listaSonando;
    private Tabla listasPendientes;
    
    private JScrollPane scrollSonando;
    private JScrollPane scrollPendientes;
    private JTabbedPane pestanasPendientes;
    
    private JPanel botones;
    private JPanel conjunto;
    
    private BorderLayout border;
    private int numeroListas = 1;
    
    private JMenuBar barramenus = new JMenuBar();
    private JMenu[] menus;
    
    
    public Main(){
        
        menus=new JMenu[2];
        SetMenus();
        
        contenidos = new ArrayList();
        contenidos.add("Los redondeles");
        contenidos.add("HUEHUEHUEHUEHUEHUE");
        contenidos.add("Never gonna give you up");
        contenidos.add("Whenever you need somebody");
        
        modeloTablaSonando = new ModeloTabla(nombresColumnaSonando, 60);
        modeloTablaPendientes = new ModeloTabla(nombresColumnaPendientes, 60, contenidos);
        
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 27, 20)); 
        border = new BorderLayout();
        
        listaSonando = new Tabla(modeloTablaSonando);
        scrollSonando = new JScrollPane(listaSonando);
        scrollSonando.setPreferredSize(new Dimension(500,700));
        panel.add(scrollSonando, border.WEST);
        
        conjunto = new JPanel();
        
        SetBotones();
        botones.setPreferredSize(new Dimension (700,250));
        conjunto.add(botones, border.NORTH);
        
        listasPendientes = new Tabla(modeloTablaPendientes);
        scrollPendientes = new JScrollPane(listasPendientes);
        pestanasPendientes = new JTabbedPane();
        
        pestanasPendientes.add(scrollPendientes, "Predeterminada");
        pestanasPendientes.setPreferredSize(new Dimension(700,400));
        conjunto.add(pestanasPendientes, border.SOUTH);      
        
        panel.add(conjunto, border.CENTER);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    private void SetBotones(){
        
        botones = new JPanel();
        
        JButton reproducirCancion = new JButton(new actions.ReproducirCancion());
        reproducirCancion.setText("Reproducir cancion");
        reproducirCancion.setPreferredSize(new Dimension (150,100));
        botones.add(reproducirCancion);
        
        JButton siguienteCancion = new JButton(new actions.SiguienteCancion());
        siguienteCancion.setText("Siguiente cancion");
        siguienteCancion.setPreferredSize(new Dimension (150,100));
        botones.add(siguienteCancion);    
        
        JButton anadirCanciones = new JButton(new actions.AnadirCanciones());
        anadirCanciones.setText("Anadir canciones");
        anadirCanciones.setPreferredSize(new Dimension (150,100));
        botones.add(anadirCanciones);
        
        JButton borrarCancion = new JButton(new actions.BorrarCancion());
        borrarCancion.setText("Borrar cancion");
        borrarCancion.setPreferredSize(new Dimension (150,100));
        botones.add(borrarCancion);
        
        JButton promocionarLista = new JButton(new actions.PromocionarLista());
        promocionarLista.setText("Promocionar lista");
        promocionarLista.setPreferredSize(new Dimension (150,100));
        botones.add(promocionarLista);
        
        JButton anadirLista = new JButton(new actions.AnadirLista());
        anadirLista.setText("Anadir lista");
        anadirLista.setPreferredSize(new Dimension (150,100));
        botones.add(anadirLista);
        
        JButton borrarLista = new JButton(new actions.BorrarLista());
        borrarLista.setText("Borrar lista");
        borrarLista.setPreferredSize(new Dimension (150,100));
        botones.add(borrarLista);
        
        JButton salir = new JButton(new actions.Salir());
        salir.setText("Salir");
        salir.setPreferredSize(new Dimension (150,100));
        botones.add(salir);    
        
    }
    
    private void SetMenus(){
        
        menus[0]= new JMenu("Archivo");
        menus[0].setMnemonic('A');
        
        JMenuItem hola = new JMenuItem("Hola");
        hola.setMnemonic('H');
        menus[0].add(hola);
        
        JMenuItem adios = new JMenuItem("Adios");
        adios.setMnemonic('d');
        menus[0].add(adios);
        
        barramenus.add(menus[0]);
        
        menus[1] = new JMenu ("Sobre");
        menus[1].setMnemonic('S');
        
        JMenuItem autores = new JMenuItem("autores");
        autores.setMnemonic('A');
        menus[1].add(autores);
                
        barramenus.add(menus[1]);
        
        setJMenuBar(barramenus);
        
    }
}
