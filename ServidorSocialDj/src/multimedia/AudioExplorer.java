/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multimedia;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author 66785270
 */
public class AudioExplorer {
    private static AudioExplorer explorer=null;
    private String[] formatos=null;
    private String init_path=null;
    private String[] track_properties = null;
    private String filtro=null;
    private Context context=null;
    private ContentResolver resolver = null;//.getContentResolver();
    private Cursor cursor=null;
    private BitmapFactory.Options sBitmapOptionsCache ;
    private Uri sArtworkUri ;
    
    private AudioExplorer(Context c){
        formatos="mp3,mp4,acc,m4a,3gp,ogp".split(",");
        init_path=Environment.getExternalStorageDirectory().getAbsolutePath();
        filtro = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        track_properties=new String[] {          
            MediaStore.Audio.AudioColumns.ALBUM,   //album
            MediaStore.Audio.AlbumColumns.ALBUM_ID, //album_id
            MediaStore.Audio.AudioColumns.ARTIST,  //artist
            MediaStore.Audio.AudioColumns.DURATION,//duration
            MediaStore.Audio.AudioColumns.TITLE,   //title
            MediaStore.MediaColumns.SIZE,          //_size
            MediaStore.MediaColumns.DATA,          //_data
            MediaStore.MediaColumns.DISPLAY_NAME   //_display_name
        };
        context=c;
        resolver=c.getContentResolver();
        sBitmapOptionsCache=new BitmapFactory.Options();
        sBitmapOptionsCache.inPreferredConfig = Bitmap.Config.RGB_565;
        sBitmapOptionsCache.inDither = false;
        sArtworkUri= Uri.parse("content://media/external/audio/albumart");
    }
    public static AudioExplorer getInstance(Context c){
        if(explorer==null)
            explorer=new AudioExplorer(c);
        return explorer;
    }
    public void setInitPath(String path){
        init_path=path;
    }
    public ArrayList<HashMap> searchAudio(){
        ArrayList<HashMap> paths=new ArrayList();
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(init_path);
        cursor = resolver.query(uri, track_properties, filtro, null, "title asc");
        HashMap cancion=null;
        while(cursor.moveToNext())
        {
            if(checkFormat(cursor.getString(cursor.getColumnIndex("_data")))){
                cancion=new HashMap();
                cancion.put("name", cursor.getString(cursor.getColumnIndex("title")));
                cancion.put("album", cursor.getString(cursor.getColumnIndex("album")));
                cancion.put("album_id", cursor.getInt(cursor.getColumnIndex("album_id")));
                cancion.put("artist", cursor.getString(cursor.getColumnIndex("artist")));
                cancion.put("length", cursor.getString(cursor.getColumnIndex("duration")));
                cancion.put("path", cursor.getString(cursor.getColumnIndex("_data")));
                paths.add(cancion);
            }
        }
        cursor.close();
        return paths;
    }
    public HashMap getTrackInfo(String path){
        HashMap track_info=null;
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(path);
        cursor = resolver.query(uri, track_properties, filtro+" and "+MediaStore.MediaColumns.DATA + "=?", new String[]{path},null);
        if(cursor.getCount()>0){
            cursor.moveToNext();
            track_info=new HashMap();
            track_info.put("name", cursor.getString(cursor.getColumnIndex("title")));
            track_info.put("album", cursor.getString(cursor.getColumnIndex("album")));
            track_info.put("album_id", cursor.getInt(cursor.getColumnIndex("album_id")));
            track_info.put("artist", cursor.getString(cursor.getColumnIndex("artist")));
            track_info.put("length", cursor.getString(cursor.getColumnIndex("duration")));
        }
        cursor.close();
        return track_info;
    }
    public boolean checkFormat(String name){
        String[] name_aux=name.split("\\.");
        String ext=name_aux[name_aux.length-1];
        boolean flag=false;
        for(String format: formatos)
            if(ext.equals(format))
                flag=true;
        return flag;
    }
    public Bitmap getAlbumImage(int album_id ) {
        // NOTE: There is in fact a 1 pixel frame in the ImageView used to
        // display this drawable. Take it into account now, so we don't have to
        // scale later.
        int w = 100;
        int h = 100;
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
        if (uri != null) {
            ParcelFileDescriptor fd = null;
            try {
                fd = res.openFileDescriptor(uri, "r");
                int sampleSize = 1;
                
                // Compute the closest power-of-two scale factor 
                // and pass that to sBitmapOptionsCache.inSampleSize, which will
                // result in faster decoding and better quality
                sBitmapOptionsCache.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, sBitmapOptionsCache);
                int nextWidth = sBitmapOptionsCache.outWidth >> 1;
                int nextHeight = sBitmapOptionsCache.outHeight >> 1;
                while (nextWidth>w && nextHeight>h) {
                    sampleSize <<= 1;
                    nextWidth >>= 1;
                    nextHeight >>= 1;
                }

                sBitmapOptionsCache.inSampleSize = sampleSize;
                sBitmapOptionsCache.inJustDecodeBounds = false;
                Bitmap b = BitmapFactory.decodeFileDescriptor(
                        fd.getFileDescriptor(), null, sBitmapOptionsCache);

                if (b != null) {
                    // finally rescale to exactly the size we need
                    if (sBitmapOptionsCache.outWidth != w || sBitmapOptionsCache.outHeight != h) {
                        Bitmap tmp = Bitmap.createScaledBitmap(b, w, h, true);
                        b.recycle();
                        b = tmp;
                    }
                }
                
                return b;
            } catch (Exception e) {
            } finally {
                try {
                    if (fd != null)
                        fd.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }
}
