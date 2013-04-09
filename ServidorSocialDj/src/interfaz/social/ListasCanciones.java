package interfaz.social;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ListasCanciones extends FragmentActivity {
    /**
     * HOLA SOY SERGEY MANCDONDO
     * COMO NABOS Y CHUPO BICHOS
     * SOY MUY GAY
     * ... DECLARADO...
     * ME ENCULA MIKE GONZALEZ
     * ... Y ME GUSTA...
     */

    ActionBar.TabListener tabListener;
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    SwipeViewPagerAdapter mSwipeViewPagerAdapter;
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
            case R.id.itemAnadirLista:
                return true;
            case R.id.itemImportar:
                return true;
            case R.id.itemVerImportarda:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
