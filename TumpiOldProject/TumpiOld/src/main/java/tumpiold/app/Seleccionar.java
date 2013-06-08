package tumpiold.app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import tumpiold.app.cliente.lista.android.PantallaPrincipal;
import tumpiold.app.servidor.interfaces.ListasCanciones;

public class Seleccionar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_app);
        Button btnServer = (Button) findViewById(R.id.botonIniciarServidor);
        btnServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(Seleccionar.this, ListasCanciones.class);
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inte);
            }
        });
        Button btnCliente = (Button) findViewById(R.id.botonIniciarCliente);
        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(Seleccionar.this, PantallaPrincipal.class);
                inte.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(inte);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.seleccionar, menu);
        return true;
    }
    
}
