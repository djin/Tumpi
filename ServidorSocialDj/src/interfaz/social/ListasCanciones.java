package interfaz.social;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class ListasCanciones extends FragmentActivity {

    ActionBar.TabListener tabListener;
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    SwipeViewPagerAdapter mSwipeViewPagerAdapter;
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setTitle("");
        final ActionBar actionBar = getActionBar();
        // Specify that a dropdown list should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter(actionBar.getThemedContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1, new String[]{"Listas Preparadas", "Lista en reproduccion"}),
                // Provide a listener to be called when an item is selected.
                new ActionBar.OnNavigationListener() {
            public boolean onNavigationItemSelected(int position, long id) {
                // Take action here, e.g. switching to the
                // corresponding fragment.
                return true;
            }
        });
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mSwipeViewPagerAdapter = new SwipeViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSwipeViewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menulistas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAnadirCanciones:
                return true;
            case R.id.itemCrearLista:
                mSwipeViewPagerAdapter.crearLista(mViewPager);
                mViewPager.setOffscreenPageLimit(mSwipeViewPagerAdapter.getCount());
                mSwipeViewPagerAdapter.notifyDataSetChanged();
                return true;
            case R.id.itemImportar:
                return true;
            case R.id.itemBorrarLista:
                int pestanaBorrar = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(pestanaBorrar-1, true);
//                mViewPager.removeViewAt(pestanaBorrar);
                mSwipeViewPagerAdapter.borrarLista(mViewPager, pestanaBorrar);
                mViewPager.setOffscreenPageLimit(mSwipeViewPagerAdapter.getCount());
                mSwipeViewPagerAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
