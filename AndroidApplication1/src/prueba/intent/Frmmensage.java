/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba.intent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
/**
 *
 * @author 66785320
 */
public class Frmmensage extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmmensaje);
        
        TextView txtMensaje = (TextView)findViewById(R.id.TxtMensaje);
 
        Bundle bundle = getIntent().getExtras();
 
        txtMensaje.setText("Hola " + bundle.getString("NOMBRE"));
    }
}