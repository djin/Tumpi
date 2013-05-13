/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elementosInterfaz;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import modelos.CancionPromocionada;
import modelos.ListaPromocionadaListener;
import modelos.ModeloTabla;

/**
 *
 * @author Juan
 */
public class TablaSonando extends JTable implements ListaPromocionadaListener {
    
    private ModeloTabla tabla;
    public String[] nombresColumnaSonando = {"Cancion", "Artista", "Votos"}; 
    private static int columnaSonandoCancion = 0, columnaSonandoAutor = 1, columnaSonandoVotos = 2;

    public TablaSonando() {

        tabla = new ModeloTabla(nombresColumnaSonando, 1);
        setModel(tabla);
        setRowHeight(20);
        
        getTableHeader().setReorderingAllowed(false);
        getColumnModel().getColumn(2).setMaxWidth(60);
        getColumnModel().getColumn(2).setMinWidth(60);
        getColumnModel().getColumn(1).setMinWidth(150);
        getColumnModel().getColumn(0).setMinWidth(150);
        setValueAt("Promociona una lista", 0, 0);
        setValueAt("", 0, 1);
        setValueAt("", 0, 2);
        
        getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel valor = new JLabel(value.toString());
                valor.setHorizontalAlignment(JLabel.CENTER);
                return valor;
            }
        });

    }

    @Override
    public void onNewListPromoted(ArrayList<CancionPromocionada> canciones) {

        int x = 0;
        tabla.setFilas(canciones.size());
        for (CancionPromocionada p : canciones) {

            tabla.setValueAt(p.getNombre(), x, columnaSonandoCancion);
            tabla.setValueAt(p.getArtista(), x, columnaSonandoAutor);
            tabla.setValueAt(0, x, columnaSonandoVotos);
            x++;
        }
    }

    @Override
    public void onSongVoted(int fila, boolean tipo) {
        
        int votos = (Integer)tabla.getValueAt(fila, columnaSonandoVotos);
        if(tipo){
            tabla.setValueAt(votos+1, fila, columnaSonandoVotos);
        }
        else{
            tabla.setValueAt(votos-1, fila, columnaSonandoVotos);
        }
    }
    @Override
    public void onSongPlayed(int fila) {
        
        tabla.setValueAt("*", fila, columnaSonandoVotos);
    }
}
