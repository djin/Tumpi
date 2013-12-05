/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.servidor.multimedia;

import android.media.MediaPlayer;

/**
 *
 * @author 66785270
 */
public class Player {
    private static Player instance=null;
    private static MediaPlayer player=null;
    //private player
    private Player(){
            player=new MediaPlayer();
    }
    public static Player getInstance(){
        if(instance==null)
            instance=new Player();
        return instance;
    }
    public boolean isPlaying(){
        return player.isPlaying();
    }
    public void pause(){
        if(player!=null){
            if(player.isPlaying())
                player.pause();
            else
                player.start();
        }
    }
    public boolean playSong(String path) throws Exception{
        try{
            player.reset();
            player.setDataSource(path);
            player.prepare();
            player.start();
            
            return true;
        }catch(Exception ex){
            throw ex;
        }
    }
    public boolean addPlayerListener(PlayerListener listener){
        if(player!=null){
            player.setOnInfoListener(listener);
            player.setOnCompletionListener(listener);
            player.setOnErrorListener(listener);
            player.setOnPreparedListener(listener);
            return true;
        }
        return false;
    }
    public void resetPlayer(){
    	player.reset();
    }
}
