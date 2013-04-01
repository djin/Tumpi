/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.pruebas;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import android.content.res.Resources;
import android.content.Context;
import lista.android.R;

public class PantallaPrincipalTest extends ActivityInstrumentationTestCase2 {

    private static final String TARGET_PACKAGE_ID = "com.yourcompany.yourapp";
    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.yourcompany.yourapp.Activities.MainActivity";
    private static Class<?> launcherActivityClass;
    private Solo solo;
    private Activity activity;
    private Resources res;
    private Context context;

    // This will launch the application specified above on the device
    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public PantallaPrincipalTest() throws ClassNotFoundException {
        super(TARGET_PACKAGE_ID, launcherActivityClass);
    }

    @Override
    protected void setUp() throws Exception {
        activity = getActivity();
        solo = new Solo(getInstrumentation(), activity);
        context = getInstrumentation().getContext();
        res = context.getResources();
    }

    public void testUI() throws Throwable {
        // ....

        // Robotium code begins here

        // Wait for the 'Cancel' button
        solo.waitForText(res.getString(R.id.btnAntiguaConexion));

        // We may want to take a screen capture...
        solo.takeScreenshot();

        // Click the cancel button
        solo.clickOnText(res.getString(R.id.btnAntiguaConexion));

        // ....

    }

    @Override
    protected void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            // Catch this
        }
        getActivity().finish();
        super.tearDown();
    }
}
