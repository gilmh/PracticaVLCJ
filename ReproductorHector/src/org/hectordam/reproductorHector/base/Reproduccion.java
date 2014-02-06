package org.hectordam.reproductorHector.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.hectordam.reproductorHector.Ventana;
import org.hectordam.reproductorHector.util.Filtro;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class Reproduccion {

	private Ventana ventana;
	private EmbeddedMediaPlayerComponent mediaPlayer;
	private File ficheroVideo;
	
	public ArrayList<File> lista;
	private boolean fin = false;
	private boolean acabar = false;
	
	public Reproduccion(){
		
	}
	public void inicializar(Ventana ventana){
		this.ventana = ventana;
		this.mediaPlayer = ventana.mediaPlayer;
	}
	
	public void abrirArchivo(){
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new Filtro());
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
			return;
		
		ficheroVideo = fileChooser.getSelectedFile();
		
		habilitar();
		reproducir(ficheroVideo);
	}
	
	public void reproducirLista(ArrayList<File> listar){
		this.lista = listar;
		acabar = false;
		
		Thread hilo = new Thread(new Runnable(){

			@Override
			public void run() {
				
				for(int i = 0; i < lista.size(); i++){
					habilitar();
					reproducir(lista.get(i));
					while(fin == false){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if(acabar == true){
						break;
					}
					fin = false;
				}
				deshabilitar();
				acabar = false;
			}
		});
		hilo.start();
	}
	
	public void reproducir(File fichero){
		
		try {
			PrintWriter reciente = new PrintWriter (new BufferedWriter(new FileWriter("Reciente", true)));
			reciente.println(fichero.getName());
			reciente.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		ventana.internalFrame.setTitle(fichero.getName());
		mediaPlayer.getMediaPlayer().playMedia(fichero.getAbsolutePath());
		
		ventana.dimension = mediaPlayer.getMediaPlayer().getCropGeometry();
		
		Thread hilo = new Thread(new Runnable(){
			
			@Override
			public void run() {
				while(mediaPlayer.getMediaPlayer().getLength() == 0){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				ventana.slTiempo.setMaximum((int) mediaPlayer.getMediaPlayer().getLength());
				
				while(mediaPlayer.getMediaPlayer().getTime() < mediaPlayer.getMediaPlayer().getLength()){
					if(ventana.tiempo == true){
						try {
							Thread.sleep(500);
							
							ventana.slTiempo.setValue((int) mediaPlayer.getMediaPlayer().getTime());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				fin = true;
			}
		});
		hilo.start();
	}
	
	public void reproducirDVD(String ruta){
		
		mediaPlayer.getMediaPlayer().playMedia(ruta);
		
		ventana.dimension = mediaPlayer.getMediaPlayer().getCropGeometry();
		
		Thread hilo = new Thread(new Runnable(){
			
			@Override
			public void run() {
				while(mediaPlayer.getMediaPlayer().getLength() == 0){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				ventana.slTiempo.setMaximum((int) mediaPlayer.getMediaPlayer().getLength());
				
				while(mediaPlayer.getMediaPlayer().getTime() < mediaPlayer.getMediaPlayer().getLength()){
					if(ventana.tiempo == true){
						try {
							Thread.sleep(500);
							
							ventana.slTiempo.setValue((int) mediaPlayer.getMediaPlayer().getTime());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				fin = true;
			}
		});
		hilo.start();
	}
	
	public void reproducirCD(){
		
		
	}
	
	public void subtitulos(){
		
		JFileChooser fileChoose = new JFileChooser();
		if (fileChoose.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
			return;
		
		mediaPlayer.getMediaPlayer().setSubTitleFile(fileChoose.getSelectedFile());
		
	}
	
	public void play(){
		
		mediaPlayer.getMediaPlayer().play();
		
		ventana.btPlay.setEnabled(false);
		ventana.btPause.setEnabled(true);
	}
	
	public void pausar(){
		
		mediaPlayer.getMediaPlayer().pause();
		
		ventana.btPlay.setEnabled(true);
		ventana.btPause.setEnabled(false);
	}
	
	public void stop(){
		
		mediaPlayer.getMediaPlayer().stop();
		
		ventana.internalFrame.setTitle("");
		
		deshabilitar();
	}
	
	public void habilitar(){
		
		ventana.btAdelantar.setEnabled(true);
		ventana.btPause.setEnabled(true);
		ventana.btPlay.setEnabled(false);
		ventana.btRetrasar.setEnabled(true);
		ventana.btStop.setEnabled(true);
		
		ventana.slTiempo.setEnabled(true);
		ventana.slVolumen.setEnabled(true);
	}
	
	private void deshabilitar(){
		
		ventana.btAdelantar.setEnabled(false);
		ventana.btPause.setEnabled(false);
		ventana.btPlay.setEnabled(false);
		ventana.btRetrasar.setEnabled(false);
		ventana.btStop.setEnabled(false);
		
		ventana.slTiempo.setEnabled(false);
		ventana.slVolumen.setEnabled(false);
	}
	
	public void setAcabar(boolean acabar) {
		this.acabar = acabar;
	}
	
}
