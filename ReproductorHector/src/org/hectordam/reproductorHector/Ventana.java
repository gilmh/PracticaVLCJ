package org.hectordam.reproductorHector;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.mrl.CdMrl;
import uk.co.caprica.vlcj.mrl.SimpleDvdMrl;
import uk.co.caprica.vlcj.player.embedded.DefaultFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSlider;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JLabel;

import org.hectordam.reproductorHector.base.Reproduccion;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Ventana implements FullScreenStrategy{

	private JFrame frame;
	public JInternalFrame internalFrame;
	private JMenuItem mntmAbrir;
	private JMenuItem mntmAbrirDvd;
	private JMenuItem mntmAbrirCds;
	private JMenuItem mntmTamano;
	private JMenuItem mntmSubtitulos;
	private JMenuItem mntmListaDeReproduccion;
	private JMenuItem mntmRecientes;
	private JMenuItem mntmCaptura;
	private JMenuItem menuItem;
	private JMenuItem menuItem_1;
	private JMenuItem mntmPantallaCompleta;
	private JMenuItem mntmPredeterminada;
	
	public JButton btRetrasar;
	public JButton btPlay;
	public JButton btPause;
	public JButton btStop;
	public JButton btAdelantar;
	public JSlider slVolumen;
	public JSlider slTiempo;
	
	public EmbeddedMediaPlayerComponent mediaPlayer;
	private static final String ruta_libreria = "C:\\Users\\nose\\Desktop\\2-DAM\\programacion multimedia\\vlc-2.1.2\\";
	
	private JLabel lbVolumen;
	public boolean tiempo = true;
	public String dimension;
	public Reproduccion reproduccion;
	private ListaReproduccion listaReproduccion;
	private Recientes reciente;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Ventana window = new Ventana();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ventana() {
		initialize();
		inicializar();
	}
	
	private void inicializar(){
		try {
			PrintWriter recientes = new PrintWriter (new BufferedWriter(new FileWriter("Reciente", true)));
			recientes.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		
		cargarReproductor();
		reproduccion = new Reproduccion();
		reproduccion.inicializar(this);
		
		listaReproduccion = new ListaReproduccion(this);
		reciente = new Recientes();
	}
	
	private void cargarReproductor(){
		
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), ruta_libreria);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		
		mediaPlayer = new EmbeddedMediaPlayerComponent();
		
		internalFrame.setContentPane(mediaPlayer);
		internalFrame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(615, 425));
		frame.setBounds(100, 100, 615, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelOpciones = new JPanel();
		frame.getContentPane().add(panelOpciones, BorderLayout.SOUTH);
		
		btRetrasar = new JButton("");
		btRetrasar.setEnabled(false);
		btRetrasar.setBounds(0, 0, 25, 25);
		btRetrasar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(mediaPlayer.getMediaPlayer().getTime() < 3000){
					mediaPlayer.getMediaPlayer().setTime(0);
				}
				else{
					mediaPlayer.getMediaPlayer().setTime(mediaPlayer.getMediaPlayer().getTime() - 3000);
				}
			}
		});
		btRetrasar.setIcon(new ImageIcon("C:\\Users\\nose\\Desktop\\2-DAM\\programacion multimedia\\bloque 2\\ReproductorHector\\media-seek-backward-7.png"));
		panelOpciones.add(btRetrasar);
		
		btPlay = new JButton("");
		btPlay.setEnabled(false);
		btPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reproduccion.play();
			}
		});
		btPlay.setIcon(new ImageIcon("C:\\Users\\nose\\Desktop\\2-DAM\\programacion multimedia\\bloque 2\\ReproductorHector\\media-playback-start-7.png"));
		panelOpciones.add(btPlay);
		
		btPause = new JButton("");
		btPause.setEnabled(false);
		btPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reproduccion.pausar();
			}
		});
		btPause.setIcon(new ImageIcon("C:\\Users\\nose\\Desktop\\2-DAM\\programacion multimedia\\bloque 2\\ReproductorHector\\media-playback-pause-7.png"));
		panelOpciones.add(btPause);
		
		btStop = new JButton("");
		btStop.setEnabled(false);
		btStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reproduccion.stop();
			}
		});
		btStop.setIcon(new ImageIcon("C:\\Users\\nose\\Desktop\\2-DAM\\programacion multimedia\\bloque 2\\ReproductorHector\\media-playback-stop-7.png"));
		panelOpciones.add(btStop);
		
		btAdelantar = new JButton("");
		btAdelantar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(mediaPlayer.getMediaPlayer().getTime() > mediaPlayer.getMediaPlayer().getLength()-3000){
					mediaPlayer.getMediaPlayer().setTime(mediaPlayer.getMediaPlayer().getLength());
				}
				else{
					mediaPlayer.getMediaPlayer().setTime(mediaPlayer.getMediaPlayer().getTime() + 3000);
				}
			}
		});
		btAdelantar.setEnabled(false);
		btAdelantar.setIcon(new ImageIcon("C:\\Users\\nose\\Desktop\\2-DAM\\programacion multimedia\\bloque 2\\ReproductorHector\\media-seek-forward-7.png"));
		panelOpciones.add(btAdelantar);
		
		slVolumen = new JSlider();
		slVolumen.setValue(100);
		slVolumen.setMaximum(200);
		slVolumen.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evento) {
				mediaPlayer.getMediaPlayer().setVolume(slVolumen.getValue());
				lbVolumen.setText(Integer.toString(slVolumen.getValue()));
			}
		});
		slVolumen.setEnabled(false);
		panelOpciones.add(slVolumen);
		
		lbVolumen = new JLabel(Integer.toString(slVolumen.getValue()));
		panelOpciones.add(lbVolumen);
		
		JPanel panelVentana = new JPanel();
		frame.getContentPane().add(panelVentana, BorderLayout.CENTER);
		panelVentana.setLayout(new BorderLayout(0, 0));
		
		slTiempo = new JSlider();
		slTiempo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				mediaPlayer.getMediaPlayer().setTime(slTiempo.getValue());
				tiempo = true;
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				tiempo = false;
			}
		});
		slTiempo.setValue(0);
		slTiempo.setEnabled(false);
		panelVentana.add(slTiempo, BorderLayout.SOUTH);
		
		internalFrame = new JInternalFrame("");
		internalFrame.setFrameIcon(null);
		panelVentana.add(internalFrame, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		mntmAbrir = new JMenuItem("Abrir archivo");
		mntmAbrir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reproduccion.abrirArchivo();
			}
		});
		mnArchivo.add(mntmAbrir);
		
		mntmAbrirDvd = new JMenuItem("Abrir DVDs");
		mntmAbrirDvd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String mrl = new SimpleDvdMrl().device("/D:\\").title(0).chapter(0).angle(0).value();
				reproduccion.reproducirDVD(mrl);
			}
		});
		mnArchivo.add(mntmAbrirDvd);
		
		mntmAbrirCds = new JMenuItem("Abrir CDs");
		mntmAbrirCds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 String mrl = new CdMrl().device("/D:\\").track(0).value();
				 reproduccion.reproducirDVD(mrl);
			}
		});
		mnArchivo.add(mntmAbrirCds);
		
		JMenu mnReproductor = new JMenu("Reproductor");
		menuBar.add(mnReproductor);
		
		mntmListaDeReproduccion = new JMenuItem("Lista de reproduccion");
		mntmListaDeReproduccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listaReproduccion.mostrar();
			}
		});
		mnReproductor.add(mntmListaDeReproduccion);
		
		mntmRecientes = new JMenuItem("Recientes");
		mntmRecientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reciente.mostrar();
			}
		});
		mnReproductor.add(mntmRecientes);
		
		mntmCaptura = new JMenuItem("Captura");
		mntmCaptura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.getMediaPlayer().saveSnapshot(new File(""));
			}
		});
		mnReproductor.add(mntmCaptura);
		
		mntmSubtitulos = new JMenuItem("Subtitulos");
		mntmSubtitulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reproduccion.subtitulos();
			}
		});
		mnReproductor.add(mntmSubtitulos);
		
		JMenu mnAjustes = new JMenu("Tama\u00F1o");
		menuBar.add(mnAjustes);
		
		mntmTamano = new JMenuItem("4 : 3");
		mntmTamano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mediaPlayer.getMediaPlayer().setCropGeometry("4:3");
			}
		});
		
		mntmPredeterminada = new JMenuItem("Predeterminada");
		mntmPredeterminada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.getMediaPlayer().setCropGeometry(dimension);
			}
		});
		mnAjustes.add(mntmPredeterminada);
		mnAjustes.add(mntmTamano);
		
		menuItem = new JMenuItem("16 : 9");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.getMediaPlayer().setCropGeometry("16:9");
			}
		});
		mnAjustes.add(menuItem);
		
		menuItem_1 = new JMenuItem("16 : 10");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mediaPlayer.getMediaPlayer().setCropGeometry("16:10");
			}
		});
		mnAjustes.add(menuItem_1);
		
		mntmPantallaCompleta = new JMenuItem("Pantalla Completa");
		mntmPantallaCompleta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FullScreenStrategy fullScreenStrategy = new DefaultFullScreenStrategy(frame);
				fullScreenStrategy.enterFullScreenMode();
			}
		});
		mnAjustes.add(mntmPantallaCompleta);
		internalFrame.setVisible(true);
		
		frame.setLocationRelativeTo(null);
	}

	@Override
	public void enterFullScreenMode() {
		
		
	}

	@Override
	public void exitFullScreenMode() {
		
		
	}

	@Override
	public boolean isFullScreenMode() {
		
		return false;
	}
	
}
