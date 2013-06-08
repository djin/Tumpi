///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package modelos;
//
//import elementosInterfaz.ReproductorPanel;
//import elementosInterfaz.Tabla;
//import java.util.ArrayList;
//import org.junit.After;
//import org.junit.AfterClass;
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import reproductor.PlayerReproductor;
//
///**
// *
// * @author 66785361
// */
//public class ListasCancionesManagerTest {
//    
//    ListasCancionesManager instance;
//    ListaCanciones lista;
//    ArrayList<Cancion> canciones;
//    int id_lista;
//    PlayerReproductor reproductor;
//
//    public ListasCancionesManagerTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() throws Exception {
//    }
//
//    @AfterClass
//    public static void tearDownClass() throws Exception {
//    }
//
//    @Before
//    public void setUp() {
//        String nombreLista;
////        lista = new ListaCanciones();
//        canciones = new ArrayList();
//        id_lista=0;
//        canciones.add(new Cancion(1, "Love Lost", "Conditions", "The Temper Trap", 216140, "01 Love Lost.mp3"));
//        canciones.add(new Cancion(2, "Love Lost", "Conditions", "The Temper Trap", 216140, "01 Love Lost.mp3"));
//        canciones.add(new Cancion(3, "Love Lost", "Conditions", "The Temper Trap", 216140, "01 Love Lost.mp3"));
//
//        lista.setCanciones(canciones);
//
//        System.out.println("promocionarLista");
//        
//
//        instance = ListasCancionesManager.getInstance();
////        instance.addLista(lista);
//        String[] columnas = {"nombre","autor", "votos"};
////        instance.tabla_sonando = new Tabla(new ModeloTabla(columnas, 3));
//        ReproductorPanel panelReproductor = new ReproductorPanel(instance);
//        reproductor = new PlayerReproductor();
//    }
//
//    @After
//    public void tearDown() {
//        instance=null;
//        lista = null;
//        canciones = null;
//    }
//
//    /**
//     * Test of getInstance method, of class ListasCancionesManager.
//     */
////    @Test
////    public void testGetInstance() {
////        System.out.println("getInstance");
////        ListasCancionesManager expResult = null;
////        ListasCancionesManager result = ListasCancionesManager.getInstance();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
//    /**
//     * Test of promocionarLista method, of class ListasCancionesManager.
//     */
//    @Test
//    public void testPromocionarListaNumFilas() {
//        //numero de filas igual al numero de canciones
//        instance.promocionarLista(id_lista);
////        assertEquals(3, instance.tabla_sonando.getTabla().getFilas());
//    }
//    
//    @Test
//    public void testPromocionarListaCompCancion() {
//        //la cancion de la fila 3 coincide con la que hemos creado
//        instance.promocionarLista(id_lista);
////        assertEquals("Love Lost", instance.tabla_sonando.getTabla().getValueAt(2, 0));
//    }
//    
//    @Test
//    public void testPromocionarListaVotos() {
//        int flag = 0;
//        instance.promocionarLista(id_lista);
////        //verificar que la columna votos sea toda ceros
////        for (int i = 0; i < instance.tabla_sonando.getTabla().getFilas(); i++) {
////            if (Integer.parseInt(instance.tabla_sonando.getTabla().getValueAt(i, 2).toString()) != 0) {
////                flag++;
////            }
//        }
//
//        assertEquals(0, flag);
//    }
//    /**
//     * Test of procesarVoto method, of class ListasCancionesManager.
//     */
//    @Test
//    public void testProcesarVoto() {
//       instance.promocionarLista(id_lista);
//       instance.procesarVoto(1, true);
//       assertEquals(Integer.parseInt(instance.tabla_sonando.getTabla().getValueAt(0, 2).toString()),1);
//       
//       instance.procesarVoto(1, false);
//       assertEquals(Integer.parseInt(instance.tabla_sonando.getTabla().getValueAt(0, 2).toString()),0);
//    }
//
//    /**
//     * Test of playNext method, of class ListasCancionesManager.
//     */
//    @Test
//    public void testPlayNextTabla() {
//       instance.promocionarLista(id_lista);
//       instance.procesarVoto(1, true);
//       instance.playNext();
//       assertEquals("*", instance.tabla_sonando.getTabla().getValueAt(0, 2));
//    }
//    
//    /**
//     * Test of playNext method, of class ListasCancionesManager.
//     */
//    @Test
//    public void testPlayNextReproductor() {
//       instance.promocionarLista(id_lista);
//       instance.procesarVoto(1, true);
//       instance.playNext();
//       
//       //no funciona por motivos desconocidos
//       assertTrue(reproductor.getMediaPlayer().isPlaying());
//    }
//
////    /**
////     * Test of addCanciones method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testAddCanciones() {
////        System.out.println("addCanciones");
////        int index = 0;
////        ListasCancionesManager instance = null;
////        instance.addCanciones(index);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of crearTabla method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testCrearTabla() {
////        System.out.println("crearTabla");
////        ListasCancionesManager instance = null;
////        Tabla expResult = null;
////        Tabla result = instance.crearTabla();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of acabaEnMp3 method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testAcabaEnMp3() {
////        System.out.println("acabaEnMp3");
////        File f = null;
////        boolean expResult = false;
////        boolean result = ListasCancionesManager.acabaEnMp3(f);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of removeCancion method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testRemoveCancion() {
////        System.out.println("removeCancion");
////        int index = 0;
////        ListasCancionesManager instance = null;
////        instance.removeCancion(index);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of addLista method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testAddLista() {
////        System.out.println("addLista");
////        ListaCanciones lista = null;
////        ListasCancionesManager instance = null;
////        instance.addLista(lista);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of removeLista method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testRemoveLista() {
////        System.out.println("removeLista");
////        int index = 0;
////        ListasCancionesManager instance = null;
////        instance.removeLista(index);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of mediaChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testMediaChanged() {
////        System.out.println("mediaChanged");
////        MediaPlayer mp = null;
////        libvlc_media_t l = null;
////        String string = "";
////        ListasCancionesManager instance = null;
////        instance.mediaChanged(mp, l, string);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of opening method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testOpening() {
////        System.out.println("opening");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.opening(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of buffering method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testBuffering() {
////        System.out.println("buffering");
////        MediaPlayer mp = null;
////        float f = 0.0F;
////        ListasCancionesManager instance = null;
////        instance.buffering(mp, f);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of playing method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testPlaying() {
////        System.out.println("playing");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.playing(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of paused method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testPaused() {
////        System.out.println("paused");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.paused(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of stopped method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testStopped() {
////        System.out.println("stopped");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.stopped(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of forward method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testForward() {
////        System.out.println("forward");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.forward(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of backward method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testBackward() {
////        System.out.println("backward");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.backward(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of finished method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testFinished() {
////        System.out.println("finished");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.finished(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of timeChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testTimeChanged() {
////        System.out.println("timeChanged");
////        MediaPlayer mp = null;
////        long l = 0L;
////        ListasCancionesManager instance = null;
////        instance.timeChanged(mp, l);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of positionChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testPositionChanged() {
////        System.out.println("positionChanged");
////        MediaPlayer mp = null;
////        float f = 0.0F;
////        ListasCancionesManager instance = null;
////        instance.positionChanged(mp, f);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of seekableChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testSeekableChanged() {
////        System.out.println("seekableChanged");
////        MediaPlayer mp = null;
////        int i = 0;
////        ListasCancionesManager instance = null;
////        instance.seekableChanged(mp, i);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of pausableChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testPausableChanged() {
////        System.out.println("pausableChanged");
////        MediaPlayer mp = null;
////        int i = 0;
////        ListasCancionesManager instance = null;
////        instance.pausableChanged(mp, i);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of titleChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testTitleChanged() {
////        System.out.println("titleChanged");
////        MediaPlayer mp = null;
////        int i = 0;
////        ListasCancionesManager instance = null;
////        instance.titleChanged(mp, i);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of snapshotTaken method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testSnapshotTaken() {
////        System.out.println("snapshotTaken");
////        MediaPlayer mp = null;
////        String string = "";
////        ListasCancionesManager instance = null;
////        instance.snapshotTaken(mp, string);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of lengthChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testLengthChanged() {
////        System.out.println("lengthChanged");
////        MediaPlayer mp = null;
////        long l = 0L;
////        ListasCancionesManager instance = null;
////        instance.lengthChanged(mp, l);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of videoOutput method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testVideoOutput() {
////        System.out.println("videoOutput");
////        MediaPlayer mp = null;
////        int i = 0;
////        ListasCancionesManager instance = null;
////        instance.videoOutput(mp, i);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of error method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testError() {
////        System.out.println("error");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.error(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of mediaMetaChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testMediaMetaChanged() {
////        System.out.println("mediaMetaChanged");
////        MediaPlayer mp = null;
////        int i = 0;
////        ListasCancionesManager instance = null;
////        instance.mediaMetaChanged(mp, i);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of mediaSubItemAdded method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testMediaSubItemAdded() {
////        System.out.println("mediaSubItemAdded");
////        MediaPlayer mp = null;
////        libvlc_media_t l = null;
////        ListasCancionesManager instance = null;
////        instance.mediaSubItemAdded(mp, l);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of mediaDurationChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testMediaDurationChanged() {
////        System.out.println("mediaDurationChanged");
////        MediaPlayer mp = null;
////        long l = 0L;
////        ListasCancionesManager instance = null;
////        instance.mediaDurationChanged(mp, l);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of mediaParsedChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testMediaParsedChanged() {
////        System.out.println("mediaParsedChanged");
////        MediaPlayer mp = null;
////        int i = 0;
////        ListasCancionesManager instance = null;
////        instance.mediaParsedChanged(mp, i);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of mediaFreed method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testMediaFreed() {
////        System.out.println("mediaFreed");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.mediaFreed(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of mediaStateChanged method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testMediaStateChanged() {
////        System.out.println("mediaStateChanged");
////        MediaPlayer mp = null;
////        int i = 0;
////        ListasCancionesManager instance = null;
////        instance.mediaStateChanged(mp, i);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of newMedia method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testNewMedia() {
////        System.out.println("newMedia");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.newMedia(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of subItemPlayed method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testSubItemPlayed() {
////        System.out.println("subItemPlayed");
////        MediaPlayer mp = null;
////        int i = 0;
////        ListasCancionesManager instance = null;
////        instance.subItemPlayed(mp, i);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of subItemFinished method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testSubItemFinished() {
////        System.out.println("subItemFinished");
////        MediaPlayer mp = null;
////        int i = 0;
////        ListasCancionesManager instance = null;
////        instance.subItemFinished(mp, i);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of endOfSubItems method, of class ListasCancionesManager.
////     */
////    @Test
////    public void testEndOfSubItems() {
////        System.out.println("endOfSubItems");
////        MediaPlayer mp = null;
////        ListasCancionesManager instance = null;
////        instance.endOfSubItems(mp);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
//}
