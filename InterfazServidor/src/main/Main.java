
package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
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
    
    private JScrollPane scroll1;
    private JScrollPane scroll2;
    
    private JPanel botones;
    
    private JTabbedPane pestanas;
    private int numeroListas = 5;
    
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
        
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.LEADING);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        panel.setLayout(fl);
        
        listaSonando = new Tabla(modeloTablaSonando);
        scroll1 = new JScrollPane(listaSonando);
        scroll1.setPreferredSize(new Dimension(400,650));
        scroll1.setLocation(25, 25);
        this.add(scroll1);
        
        SetBotones();
        
        botones.setPreferredSize(new Dimension (400,300));
        botones.setLocation(425, 5);
        this.add(botones);
        
        listasPendientes = new Tabla(modeloTablaPendientes);
        scroll2 = new JScrollPane(listasPendientes);
        scroll2.setPreferredSize(new Dimension(400,650));
        scroll2.setLocation(425, 325);
        this.add(scroll2);      
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
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
    
    private void SetBotones(){
        
        botones = new JPanel();
        
        JButton anadirLista = new JButton("anadir lista");
        botones.add(anadirLista);
        
        JButton promocionarLista = new JButton("promocionar lista");
        botones.add(promocionarLista);
        
        JButton borrarLista = new JButton("borrar lista");
        botones.add(borrarLista);
        
        JButton anadirCancion = new JButton("añadir canciones");
        botones.add(anadirCancion);
        
        JButton reproducirCancion = new JButton("reproducir cancion");
        botones.add(reproducirCancion);
        
        JButton borrarCancion = new JButton("borrar cancion");
        botones.add(borrarCancion);        
        
    }
}
