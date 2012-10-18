/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuInflater;

/**
 *
 * @author 66785320
 */
public class Settings extends ListActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Establecemos el diseño principal de la Actividad
        setContentView(R.layout.settings);
        
        MenuInflater inflater = getMenuInflater();
        
    }
}
