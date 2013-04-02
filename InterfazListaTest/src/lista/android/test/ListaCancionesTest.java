/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import lista.android.ListaCanciones;
//hola soy sergio y soy un mancazzzzzooooo y gay ademas...

public class ListaCancionesTest extends ActivityInstrumentationTestCase2<ListaCanciones> {

    private Activity a;
    private Solo solo;
    
    public ListaCancionesTest() throws ClassNotFoundException {
        super (ListaCanciones.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        a = getActivity();
        solo = new Solo(getInstrumentation(), a);
    }
    
//    public void testVariasClasesParaTestear() throws Throwable{
//        assertEquals(2, 2);
//    }
    
    @Override
    protected void tearDown() throws Exception {
        try {
            solo.finishOpenedActivities();
        } catch (Throwable e) {
        }
        getActivity().finish();
        super.tearDown();
    }
}
