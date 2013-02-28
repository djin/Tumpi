/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reproductor;

import java.awt.image.BufferedImage;
import modelos.Cancion;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.co.caprica.vlcj.player.MediaPlayer;

/**
 *
 * @author 66785379
 */
public class PlayerReproductorTest {
    
    PlayerReproductor reproductor;
    Cancion cancion;
    Cancion cancion2;
            
    public PlayerReproductorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        cancion = new Cancion(0, "Love Lost", "Conditions", "The Temper Trap", 216140, "C:\\Users\\66785379\\Desktop\\Musica\\Conditions (2009)\\01 Love Lost.mp3");
        cancion2 = new Cancion(0, "Love Lost", "Conditions", "The Temper Trap", 216140, "C:\\Users\\66785379\\Desktop\\Musica\\Conditions (2009)\\01 Love Lost.mp3");
        reproductor = new PlayerReproductor();
    }

    @After
    public void tearDown() {
        reproductor = null;
        cancion = null;
        cancion2 = null;
    }

    @Test
    public void testGetCancion() {
        //reproductor.getMediaPlayer().playMedia("C:\\Users\\66785379\\Desktop\\Musica\\Conditions (2009)\\01 Love Lost.mp3");
        assertEquals(cancion, reproductor.getCancion("C:\\Users\\66785379\\Desktop\\Musica\\Conditions (2009)\\01 Love Lost.mp3"));
    }

    @Test
    public void testEquals() {
        assertEquals(cancion, cancion2);
    }
}