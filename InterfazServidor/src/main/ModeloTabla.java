/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Point;
import java.util.EventListener;
import java.util.HashMap;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author 66786575
 */
public class ModeloTabla implements TableModel {
    
    private int filas;
    private int columnas;
    
    private String nombreColumnas[];
    private HashMap<Point, String> mapa;
    protected transient EventListenerList listaListeners;
     
    public ModeloTabla(String nombreColumns[], int numFilas){
        
        columnas = nombreColumns.length;
        filas = numFilas;
        nombreColumnas = nombreColumns;
        mapa = new HashMap<Point, String>();
        listaListeners = new EventListenerList();
        
        for(int x = 0; x < filas; x++){
            
            for(int y = 0; y < columnas; y++){
                if(y==1) mapa.put(new Point(x, y), "0");
            }
        }
    }
    
    @Override
    public void setValueAt(Object dato, int fila, int columna) {
            
        getMapa().put(new Point(fila, columna), dato.toString());
        
        fireTableDataChanged();
    }
 
    @Override
    public boolean isCellEditable(int fila, int columna) {
        return columna != 0 && columna !=1;
    
    }
    /**
     * @return the filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * @param filas the filas to set
     */
    public void setFilas(int filas) {
        this.filas = filas;
    }

    /**
     * @return the columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * @param columnas the columnas to set
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    /**
     * @return the nombreColumnas
     */
    public String[] getNombreColumnas() {
        return nombreColumnas;
    }

    /**
     * @param nombreColumnas the nombreColumnas to set
     */
    public void setNombreColumnas(String[] nombreColumnas) {
        this.nombreColumnas = nombreColumnas;
    }

    /**
     * @return the mapa
     */
    public HashMap<Point, String> getMapa() {
        return mapa;
    }

    /**
     * @param mapa the mapa to set
     */
    public void setMapa(HashMap<Point, String> mapa) {
        this.mapa = mapa;
    }

    @Override
    public String getColumnName(int columna) {
        return nombreColumnas[columna];
    }
 
    
    @Override
    public Object getValueAt(int fila, int columna) {
        return mapa.get(new Point(fila, columna));
    }

    @Override
    public int getRowCount() {
        
        return filas;
    }

    @Override
    public int getColumnCount() {
        
        return nombreColumnas.length;
    }
    
    
    

    public int findColumn(String columnName) {
        for (int i = 0; i < getColumnCount(); i++) {
            if (columnName.equals(getColumnName(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
	return Object.class;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        if(listaListeners == null) {
            listaListeners = new EventListenerList();
        }
        
	listaListeners.add(TableModelListener.class, l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        if(listaListeners == null) {
            listaListeners = new EventListenerList();
        }
	listaListeners.remove(TableModelListener.class, l);
    }

    public TableModelListener[] getTableModelListeners() {
        if(listaListeners == null) {
            listaListeners = new EventListenerList();
        }
        return (TableModelListener[])listaListeners.getListeners(
                TableModelListener.class);
    }

    public void fireTableDataChanged() {
        fireTableChanged(new TableModelEvent(this));
    }

    public void fireTableStructureChanged() {
        fireTableChanged(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
    }

    public void fireTableRowsInserted(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
    }

    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow,
                             TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
    }

    public void fireTableCellUpdated(int row, int column) {
        fireTableChanged(new TableModelEvent(this, row, row, column));
    }

    public void fireTableChanged(TableModelEvent e) {
        if(listaListeners == null) {
            listaListeners = new EventListenerList();
        }
	Object[] listeners = listaListeners.getListenerList();
        
	for (int i = listeners.length-2; i>=0; i-=2) {
	    if (listeners[i]==TableModelListener.class) {
		((TableModelListener)listeners[i+1]).tableChanged(e);
	    }
	}
    }
    
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) { 
	return listaListeners.getListeners(listenerType); 
    }

}