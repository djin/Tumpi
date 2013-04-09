/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.social;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 *
 * @author sdfhgjkltñfdtgf
 */
public class SwipeViewPagerAdapter extends FragmentStatePagerAdapter {

    public SwipeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new ListaFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(ListaFragment.ARG_OBJECT, i + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
