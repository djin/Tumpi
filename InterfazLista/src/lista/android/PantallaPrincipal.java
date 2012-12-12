/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

/**
 *
 * @author 66785320
 */
public class PantallaPrincipal extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stylepantallainicial);
        
        Button btnNuevaConexion = (Button)findViewById(R.id.btnconexion);
        btnNuevaConexion.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, PantallaDatosServidor.class);
                
                startActivity(intent);
            }
        });                        
    }
}
