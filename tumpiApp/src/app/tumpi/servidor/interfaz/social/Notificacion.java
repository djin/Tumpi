package app.tumpi.servidor.interfaz.social;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;
import app.tumpi.R;
import app.tumpi.SeleccionAplicacion;
import app.tumpi.services.MyService;
import app.tumpi.servidor.interfaces.NotificationListener;
import app.tumpi.servidor.modelo.datos.ListasManager;
import app.tumpi.servidor.multimedia.AudioExplorer;

public class Notificacion {

	private BroadcastReceiver receiver;
	private IntentFilter filter;
	private ListasManager manager;
	private AudioExplorer explorer;
	private PendingIntent nextPendingIntent;
	private PendingIntent playPendingIntent;
	private PendingIntent closePendingIntent;
	private Activity activity;
	private ArrayList<NotificationListener> listeners;
	
	public Notificacion() {
		// TODO Auto-generated constructor stub
	}
	public Notificacion(Activity activity) {
		// TODO Auto-generated constructor stub
		manager = ListasManager.getInstance();
		this.activity = activity;
		Intent nextIntent = new Intent("next");
		nextPendingIntent = PendingIntent.getBroadcast(activity, 0, nextIntent, 0);
		Intent playIntent = new Intent("play");
		playPendingIntent = PendingIntent.getBroadcast(activity, 0, playIntent, 0);
		Intent closeIntent = new Intent("close");
		closePendingIntent = PendingIntent.getBroadcast(activity, 0, closeIntent, 0);
		receiver = new MyReciever();
		filter = new IntentFilter();
		filter.addAction("next");
		filter.addAction("play");
		filter.addAction("close");
		explorer = AudioExplorer.getInstance(activity.getApplicationContext());
		listeners = new ArrayList<NotificationListener>();

	}
	
	public void next(){
		manager.procesarVotos();
		sacarNotificacion();
	}
	
	public void play(){
		manager.player.pause();
		sacarNotificacion();
		fireOnClickPlayNotification();
	}
	
	public void close(){
		if (!manager.cerrarConexion()) {
			Toast.makeText(activity, "Error al salir del Tumpi",
					Toast.LENGTH_SHORT).show();
		}
		NotificationManager mNotificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);
        manager.promotedList.getCanciones().clear();
        if(manager.player.isPlaying()){
        	manager.player.pause();
        }
        manager.player.resetPlayer();
        manager.setCancionReproduciendo(null);
        Intent service = new Intent(activity, MyService.class);
        activity.stopService(service);
        fireOnCloseNotification();
	}

	public BroadcastReceiver getReceiver() {
		return receiver;
	}
	
	public IntentFilter getFilter() {
		return filter;
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void sacarNotificacion() {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			BitmapDrawable bitmap = ((BitmapDrawable) activity
					.getResources().getDrawable(R.drawable.caratula_default));
			RemoteViews mRemoteView = new RemoteViews(activity.getPackageName(),
					R.layout.notification_player);

			RemoteViews mRemoteViewBig = new RemoteViews(activity.getPackageName(),
					R.layout.notification_player_big);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					activity).setSmallIcon(R.drawable.logo_tumpi)
					.setLargeIcon(bitmap.getBitmap()).setOngoing(true)
					.setTicker(manager.getCancionReproduciendo().nombreCancion);
			
			Bitmap imgDefault = explorer.getAlbumImage(manager
					.getCancionReproduciendo().album_id);
			if(imgDefault == null){
					imgDefault = ((BitmapDrawable) activity
							.getResources().getDrawable(R.drawable.caratula_default)).getBitmap();
			}

			mRemoteView.setTextViewText(R.id.texto_cancion_notificacion,
					manager.getCancionReproduciendo().nombreCancion);
			mRemoteView.setTextViewText(R.id.texto_autor_notificacion,
					manager.getCancionReproduciendo().nombreAutor);
			mRemoteView
					.setImageViewBitmap(R.id.img_reproductor_notificacion,
							imgDefault);

			
			mRemoteView.setOnClickPendingIntent(R.id.btn_next_notificacion,
					nextPendingIntent);
			mRemoteView.setOnClickPendingIntent(R.id.btn_play_notificacion,
					playPendingIntent);
			mRemoteView.setOnClickPendingIntent(R.id.btn_close_notificacion,
					closePendingIntent);

			mRemoteViewBig.setTextViewText(R.id.texto_cancion_notificacion_big,
					manager.getCancionReproduciendo().nombreCancion);
			mRemoteViewBig.setTextViewText(R.id.texto_autor_notificacion_big,
					manager.getCancionReproduciendo().nombreAutor);
			mRemoteViewBig.setTextViewText(R.id.texto_album_notificacion_big,
					manager.getCancionReproduciendo().nombreAlbum);
			mRemoteViewBig
					.setImageViewBitmap(R.id.img_reproductor_notificacion_big,
							imgDefault);
			
			mRemoteViewBig.setOnClickPendingIntent(R.id.btn_next_notificacion_big,
					nextPendingIntent);
			mRemoteViewBig.setOnClickPendingIntent(R.id.btn_play_notificacion_big,
					playPendingIntent);
			mRemoteViewBig.setOnClickPendingIntent(R.id.btn_close_notificacion_big,
					closePendingIntent);

			if(manager.player.isPlaying()){
				mRemoteView.setImageViewResource(R.id.btn_play_notificacion, R.drawable.image_pause_white);
				mRemoteViewBig.setImageViewResource(R.id.btn_play_notificacion_big, R.drawable.image_pause_white);
			} else {
				mRemoteView.setImageViewResource(R.id.btn_play_notificacion, R.drawable.image_play_white);
				mRemoteViewBig.setImageViewResource(R.id.btn_play_notificacion_big, R.drawable.image_play_white);
			}
			mBuilder.setContent(mRemoteView);

			Intent notIntent = new Intent(activity,
					SeleccionAplicacion.class);
			notIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			PendingIntent contIntent = PendingIntent.getActivity(
					activity, 0, notIntent, 0);
			

			mBuilder.setContentIntent(contIntent);

			Notification nf = mBuilder.build();
			
			nf.bigContentView = mRemoteViewBig;
			NotificationManager mNotificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);

			mNotificationManager.notify(1, nf);
		}
	}
	
	public void addNotificationListener (NotificationListener listener){
		if(!listeners.contains(listener)){
			listeners.add(listener);
		}
	}
	
	public void fireOnClickPlayNotification(){
		for (NotificationListener e: listeners){
			e.onClickPlayNotification();
		}
	}
	
	public void fireOnCloseNotification(){
		for(NotificationListener e: listeners){
			e.onCloseNotification();
		}
	}
//
//	public void setFilter(IntentFilter filter) {
//		this.filter = filter;
//	}
//
//	public void setReceiver(BroadcastReceiver receiver) {
//		this.receiver = receiver;
//	}
}
