/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazlistatest;

import org.robolectric.Robolectric;
import android.view.LayoutInflater;
import android.content.Context;
import android.app.Activity;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.view.View;
import lista.android.R;
import android.app.ListActivity;
import lista.android.AdaptadorLista;
import lista.android.Cancion;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLayoutInflater;
import static org.robolectric.Robolectric.shadowOf;
import org.robolectric.shadows.ShadowListActivity;
import static org.junit.Assert.*;

/**
 *
 * @author 66785320
 */
@RunWith(RobolectricTestRunner.class)
public class AdaptadorListaTest {

    ArrayList<Cancion> listaCanciones;
    AdaptadorLista adaptador;
    ListActivity activity;

    @Before
    public void setUp() {
        listaCanciones = new ArrayList<Cancion>();
        listaCanciones.add(new Cancion("Los redondeles", "redondels", "n1", 1, false, false));
        listaCanciones.add(new Cancion("Los redondeles2", "redondels2", "n2", 2, false, false));
        listaCanciones.add(new Cancion("Los redondeles3", "redondels3", "n3", 3, false, false));
        activity = new ListActivity();
        adaptador = new AdaptadorLista(activity, listaCanciones, R.layout.rowstyle);
    }

    @Test
    public void testCount() throws Exception {
        assertEquals(adaptador.getCount(), 3);
    }

    @Test
    public void testGetItem() throws Exception {
        assertEquals(adaptador.getItem(0), new Cancion("Los redondeles", "redondels", "n1", 1, false, false));
        assertEquals(adaptador.getItem(1), new Cancion("Los redondeles2", "redondels2", "n2", 2, false, false));
        assertEquals(adaptador.getItem(2), new Cancion("Los redondeles3", "redondels3", "n3", 3, false, false));
    }

    @Test
    public void testGetItemId() throws Exception {
        assertEquals(adaptador.getItemId(0), 0L);
        assertEquals(adaptador.getItemId(1), 1L);
        assertEquals(adaptador.getItemId(2), 2L);
    }

    @Test
    public void testGetNombreCancion() throws Exception {
        assertEquals(adaptador.getDatos().get(0).getNombreCancion(), "Los redondeles");
        assertEquals(adaptador.getDatos().get(1).getNombreCancion(), "Los redondeles2");
        assertEquals(adaptador.getDatos().get(2).getNombreCancion(), "Los redondeles3");
    }
    
    @Test
    public void testNotNullLayout() throws Exception{
        LayoutInflater inflater = (LayoutInflater) Robolectric.application.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assertNotNull(inflater);
        ShadowLayoutInflater sLayout = shadowOf(inflater);
        View v = inflater.inflate(R.layout.rowstyle, null);
//        View v = inflater.inflate(R.layout.rowstyle, new LinearLayout(activity), false);
        assertNotNull(v);
        assertNotNull(activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        assertNotNull(new LinearLayout(activity));
    }

//    @Test
//    public void testGetView() throws Exception {
//        View nameView = (View) adaptador.getView(0, null, new LinearLayout(activity));
//        assertEquals(((TextView) nameView.findViewById(R.id.songName)).getText().toString(), "Los redondeles");
//    }
}
