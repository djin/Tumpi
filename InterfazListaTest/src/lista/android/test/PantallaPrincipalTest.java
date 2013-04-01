/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import android.content.res.Resources;
import android.content.Context;
import lista.android.PantallaPrincipal;
import lista.android.R;

public class PantallaPrincipalTest extends ActivityInstrumentationTestCase2<PantallaPrincipal> {

    private static final String TARGET_PACKAGE_ID = "lista.android";
    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "lista.android.PantallaPrincipal";
    private static Class<?> launcherActivityClass;
    private Solo solo;
    private Resources res;
    private Context context;

    // This will launch the application specified above on the device
//    static {
//        try {
//            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public PantallaPrincipalTest() throws ClassNotFoundException {
        super(PantallaPrincipal.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        context = getInstrumentation().getContext();
        res = context.getResources();
    }

    public void testPrueba() throws Throwable {

        assertEquals(2, 2);

    }

    public void testUI() throws Throwable {
        // ....

        // Robotium code begins here

        // Wait for the 'Cancel' button
        solo.waitForText(res.getString(R.string.entrar));

        // We may want to take a screen capture...
        solo.takeScreenshot();

        // Click the cancel button
        solo.clickOnText(res.getString(R.string.entrar));

        // ....
    }

    @Override
    protected void tearDown() throws Exception {
        try {
            solo.finishOpenedActivities();
        } catch (Throwable e) {
            // Catch this
        }
        getActivity().finish();
        super.tearDown();
    }
}
