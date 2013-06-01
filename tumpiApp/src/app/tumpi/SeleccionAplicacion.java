package app.tumpi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import app.tumpi.cliente.lista.android.PantallaPrincipal;
import app.tumpi.servidor.interfaz.social.ListaPromocionada;

/**
 *
 * @author Sergio Redondo
 */
public class SeleccionAplicacion extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccionar_aplicacion);
        Button btnClient = (Button) findViewById(R.id.botonIniciarCliente);
        btnClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent inte = new Intent(SeleccionAplicacion.this, PantallaPrincipal.class);
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inte);
            }
        });
        Button btnServer = (Button) findViewById(R.id.botonIniciarServidor);
        btnServer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent inte = new Intent(SeleccionAplicacion.this, ListaPromocionada.class);
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inte);
            }
        });
    }
}
