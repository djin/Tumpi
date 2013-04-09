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
import android.widget.TextView;

/**
 *
 * @author fdgbgbnesad
 */
public class ListaFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(R.layout.style_listas_preparacion, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.text1)).setText(Integer.toString(args.getInt(ARG_OBJECT)));
        return rootView;
    }
}
