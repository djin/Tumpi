package lista.android;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

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
        
        TabHost.TabSpec spec = tabs.newTabSpec("Pestaña1");
        spec.setContent(R.id.tab1);
        spec.setIndicator(specStyle("Lista", R.raw.ico_music));
        tabs.addTab(spec);
        
        spec = tabs.newTabSpec("Pestaña2");
        spec.setContent(R.id.tab2);
        spec.setIndicator(specStyle("Home", R.raw.ico_home));
        tabs.addTab(spec);
        
        spec = tabs.newTabSpec("Pestaña3");
        spec.setContent(R.id.tab3);
        spec.setIndicator(specStyle("Options", android.R.drawable.ic_media_rew));
        tabs.addTab(spec);
        
        tabs.setCurrentTab(0);
    }
    
    public View specStyle(String name, int icon){
        View view = getLayoutInflater().inflate(R.layout.specstyle, null);
        ImageView ico = (ImageView)view.findViewById(R.id.image);
        ico.setBackgroundResource(icon);
        return view;
    }
}
