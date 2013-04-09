/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.ArrayList;

/**
 *
 * @author fdgbgbnesad
 */
public class ListaFragment extends Fragment {

    public ArrayList<String> datos = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(R.layout.style_listas_preparacion, container, false);
        Bundle args = getArguments();
        ListView listaCanciones = (ListView) rootView.findViewById(R.id.listaCanciones);
        AdaptadorLista adapter = new AdaptadorLista(this.getActivity(), datos, R.layout.row_style_preparation);
        listaCanciones.setAdapter(adapter);
        return rootView;
    }
}
