package lista.android;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class ListaCanciones extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Resources res = getResources();
        
        TabHost tabs = (TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();
        
        TabSpec spec = tabs.newTabSpec("Pestaña1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("", res.getDrawable(R.raw.ico_medium_list));
        tabs.addTab(spec);
        
        spec = tabs.newTabSpec("Pestaña2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("", res.getDrawable(R.raw.ico_medium_star));
        tabs.addTab(spec);
        
        spec = tabs.newTabSpec("Pestaña3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("", res.getDrawable(R.raw.ico_medium_options));
        tabs.addTab(spec);
        
        tabs.setCurrentTab(0);
        
        
    }
    
}
