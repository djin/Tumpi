/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import android.view.KeyEvent;
import lista.android.PantallaDatosServidor;
import lista.android.PantallaPrincipal;
import lista.android.R;

public class PantallaPrincipalTest extends ActivityInstrumentationTestCase2<PantallaPrincipal> {

    private Activity a;
    private Solo solo;

    public PantallaPrincipalTest() throws ClassNotFoundException {
        super(PantallaPrincipal.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        a = getActivity();
        solo = new Solo(getInstrumentation(), a);
    }

    public void testPrueba() throws Throwable {

        assertEquals(2, 2);

    }

    public void testUI() throws Throwable {

        //guarda una foto en /sdCard/Robotium.screenshots (no funciona, aunque le des permisos T_T)
        solo.takeScreenshot();

        assertTrue(solo.searchButton("Entrar"));

        assertTrue(solo.searchText(a.getString(R.string.entrar)));
    }

    public void testPassActivityConectionManual() throws Throwable {
        solo.clickOnImageButton(0);
        solo.assertCurrentActivity("No es la actividad esperada", PantallaDatosServidor.class);
        sendKeys(KeyEvent.KEYCODE_BACK);
        solo.assertCurrentActivity("No ha vuelto a la actividad", PantallaPrincipal.class);
    }

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
