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

import javax.swing.JFileChooser;
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
import org.hectordam.reproductorHector.beans.TablaReproduccion;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.hectordam.reproductorHector.beans.TablaRecientes;

public class Ventana implements FullScreenStrategy{

	public JFrame frame;
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
	private static final String ruta_libreria = "C:\\Users\\nose\\Desktop\\2-DAM\\programacion multimedia\\vlc-2.1.2\\";
	
	public EmbeddedMediaPlayerComponent mediaPlayer;
	private JLabel lbVolumen;
	public boolean tiempo = true;
	public String dimension;
	public Reproduccion reproduccion;
	private ListaReproduccion listaReproduccion;
	private Recientes reciente;
	private JPanel pnCentral;
	private JMenuItem mntmAbrirStriming;
	private JMenuItem mntmRetornarPantalla;
	private FullScreenStrategy fullScreenStrategy;
	private JPanel pnLista;
	private JButton btnHabilitarEste;
	private boolean mostrarLista = false;
	private boolean mostrarRecientes = false;
	private boolean mostrarSur = true;
	private JPanel pnEste;
	private JScrollPane scrollPane;
	private JButton btnReproducir;
	private JButton btnAnadir;
	private JButton btnEliminar;
	private JButton btnCancelar;
	private JPanel panel_2;
	private JButton btnImagen;
	private JTextField textField;
	private JLabel label;
	private TablaReproduccion tablaReproduccion;
	private JMenu mnLista;
	private JMenuItem mntmGuardar;
	private JMenuItem mntmCargar;
	private JLabel lbImagen;
	private JPanel panel_3;
	private JPanel pnOeste;
	private JButton btnOeste;
	private JScrollPane scrollPane_1;
	private JButton btnReproducir_1;
	private TablaRecientes tablaRecientes;
	private JPanel pnSur;
	private JButton btnSur;
	private JPanel panelOpciones;
	private JLabel lblNewLabel;
	private JLabel lblImagen;
	private JLabel lblReproduccion;
	private JButton btnBorrar;
	
	
	private void guardar(){
		
		if(textField.getText() == ""){
			return;
		}
		
		try {
			PrintWriter ficheroGuardar = new PrintWriter(new BufferedWriter(new FileWriter(textField.getText()+".txt", false)));
			
			ficheroGuardar.println(lbImagen.getIcon());
			
			ArrayList<String> lista = tablaReproduccion.getLista();

			for(int i = 0; i < lista.size(); i++){
				
				ficheroGuardar.println(lista.get(i));
				
			}
			
			ficheroGuardar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void cargar(){
		
		JFileChooser fileChooser = new JFileChooser();
		if(fileChooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
			return;
		}
		
		ArrayList<String> lista = new ArrayList<String>();
		try {
			BufferedReader fichero = new BufferedReader(new FileReader(fileChooser.getSelectedFile().getAbsoluteFile()));
			
			textField.setText(fileChooser.getSelectedFile().getName());
			String linea = fichero.readLine();
			
			if(linea.equalsIgnoreCase("null") == false){
				lbImagen.setIcon(new ImageIcon(linea));
			}
			else{
				lbImagen.setIcon(null);
			}
			
			linea = fichero.readLine();
			while(linea != null){
				
				lista.add(linea);
				
				linea = fichero.readLine();
			}
			
			tablaReproduccion.setLista(lista);
			tablaReproduccion.listar();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void reproducir(){
		
		reproduccion.reproducirLista(tablaReproduccion.getLista());
		
		btnAnadir.setEnabled(false);
		btnEliminar.setEnabled(false);
		btnReproducir.setEnabled(false);
		btnCancelar.setEnabled(true);
	}
	
	private void cancelar(){
		
		reproduccion.setAcabar(true);
		
		btnAnadir.setEnabled(true);
		btnEliminar.setEnabled(true);
		btnReproducir.setEnabled(true);
		btnCancelar.setEnabled(false);
	}
	
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
		
		frame.setTitle("HGM");
	}
	
	private void inicializar(){
		try {
			PrintWriter recientes = new PrintWriter (new BufferedWriter(new FileWriter("Reciente", true)));
			recientes.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		tablaRecientes.listar();
		
		cargarReproductor();
		reproduccion = new Reproduccion();
		reproduccion.inicializar(this);
		
		listaReproduccion = new ListaReproduccion(this);
		reciente = new Recientes(this);
	}
	
	private void cargarReproductor(){
		
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), ruta_libreria);
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		pnCentral.setLayout(new BorderLayout(0, 0));
		
		mediaPlayer = new EmbeddedMediaPlayerComponent();
		
		this.pnCentral.add(mediaPlayer);
		
		btnHabilitarEste = new JButton("");
		btnHabilitarEste.setIcon(new ImageIcon("arrow-right-3.png"));
		pnCentral.add(btnHabilitarEste, BorderLayout.EAST);
		btnHabilitarEste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(mostrarLista == true){
					
					pnLista.setVisible(false);
					
					mostrarLista = false;
				}
				else{
					
					pnLista.setVisible(true);
					
					mostrarLista = true;
				}
			}
		});
		btnHabilitarEste.setBackground(Color.WHITE);
		btnHabilitarEste.setPreferredSize(new Dimension(12, 10));
		
		panel_3 = new JPanel();
		pnCentral.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		btnOeste = new JButton("");
		btnOeste.setBackground(Color.WHITE);
		btnOeste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(mostrarRecientes == true){
					
					pnOeste.setVisible(false);
					
					mostrarRecientes = false;
				}
				else{
					
					pnOeste.setVisible(true);
					
					mostrarRecientes = true;
				}
			}
		});
		btnOeste.setIcon(new ImageIcon("arrow-left-3.png"));
		btnOeste.setPreferredSize(new Dimension(12, 23));
		panel_3.add(btnOeste);
		
		pnSur = new JPanel();
		pnCentral.add(pnSur, BorderLayout.SOUTH);
		pnSur.setLayout(new BorderLayout(0, 0));
		
		btnSur = new JButton("");
		btnSur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(mostrarSur == true){
					
					panelOpciones.setVisible(false);
					
					mostrarSur = false;
				}
				else{
					
					panelOpciones.setVisible(true);
					
					mostrarSur = true;
				}
			}
		});
		btnSur.setBackground(Color.WHITE);
		btnSur.setPreferredSize(new Dimension(16, 13));
		btnSur.setIcon(new ImageIcon("arrow-down-3.png"));
		pnSur.add(btnSur);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("pictograms-aem-0010-wear_ear_protection.png"));
		frame.setMinimumSize(new Dimension(800, 475));
		frame.setBounds(100, 100, 700, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panelOpciones = new JPanel();
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
		btRetrasar.setIcon(new ImageIcon("media-seek-backward-7.png"));
		panelOpciones.add(btRetrasar);
		
		btPlay = new JButton("");
		btPlay.setEnabled(false);
		btPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reproduccion.play();
			}
		});
		btPlay.setIcon(new ImageIcon("media-playback-start-7.png"));
		panelOpciones.add(btPlay);
		
		btPause = new JButton("");
		btPause.setEnabled(false);
		btPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				reproduccion.pausar();
			}
		});
		btPause.setIcon(new ImageIcon("media-playback-pause-7.png"));
		panelOpciones.add(btPause);
		
		btStop = new JButton("");
		btStop.setEnabled(false);
		btStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reproduccion.stop();
			}
		});
		btStop.setIcon(new ImageIcon("media-playback-stop-7.png"));
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
		btAdelantar.setIcon(new ImageIcon("media-seek-forward-7.png"));
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
		
		pnCentral = new JPanel();
		panelVentana.add(pnCentral, BorderLayout.CENTER);
		
		pnLista = new JPanel();
		pnLista.setPreferredSize(new Dimension(330, 10));
		panelVentana.add(pnLista, BorderLayout.EAST);
		pnLista.setLayout(new BorderLayout(0, 0));
		pnLista.setVisible(false);
		
		pnEste = new JPanel();
		pnLista.add(pnEste, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		
		btnReproducir = new JButton("Reproducir");
		btnReproducir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reproducir();
			}
		});
		btnReproducir.setEnabled(false);
		
		btnAnadir = new JButton("A\u00F1adir");
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablaReproduccion.anadir();
			}
		});
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablaReproduccion.eliminar();
			}
		});
		btnEliminar.setEnabled(false);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelar();
			}
		});
		btnCancelar.setEnabled(false);
		
		panel_2 = new JPanel();
		panel_2.setLayout(new BorderLayout(0, 0));
		
		lbImagen = new JLabel("");
		panel_2.add(lbImagen, BorderLayout.CENTER);
		
		btnImagen = new JButton("Imagen");
		btnImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChoose = new JFileChooser();
				if(fileChoose.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
					return;
				}
				
				lbImagen.setIcon(new ImageIcon(fileChoose.getSelectedFile().getAbsolutePath()));
			}
		});
		
		textField = new JTextField();
		textField.setColumns(10);
		
		label = new JLabel("Nombre de la lista");
		
		tablaReproduccion = new TablaReproduccion(btnEliminar, btnReproducir);
		scrollPane.setViewportView(tablaReproduccion);
		
		lblNewLabel = new JLabel("Lista Reproduccion");
		
		lblImagen = new JLabel("Imagen");
		
		lblReproduccion = new JLabel("Reproduccion");
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				lbImagen.setIcon(null);
			}
		});
		GroupLayout gl_pnEste = new GroupLayout(pnEste);
		gl_pnEste.setHorizontalGroup(
			gl_pnEste.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnEste.createSequentialGroup()
					.addGroup(gl_pnEste.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnEste.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnEste.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_pnEste.createSequentialGroup()
									.addComponent(btnEliminar, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnBorrar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_pnEste.createSequentialGroup()
									.addComponent(btnAnadir, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnImagen, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnEste.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addGap(18)
									.addComponent(lblImagen)))
							.addGroup(gl_pnEste.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnEste.createSequentialGroup()
									.addGap(6)
									.addGroup(gl_pnEste.createParallelGroup(Alignment.LEADING)
										.addComponent(btnReproducir, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_pnEste.createSequentialGroup()
									.addGap(17)
									.addComponent(lblReproduccion))))
						.addGroup(gl_pnEste.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_pnEste.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnEste.createSequentialGroup()
									.addGroup(gl_pnEste.createParallelGroup(Alignment.LEADING)
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE))
									.addGap(10)
									.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnEste.setVerticalGroup(
			gl_pnEste.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnEste.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_pnEste.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnEste.createSequentialGroup()
							.addComponent(label)
							.addGap(10)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnEste.createSequentialGroup()
							.addGap(4)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnEste.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pnEste.createSequentialGroup()
							.addGroup(gl_pnEste.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(lblImagen)
								.addComponent(lblReproduccion))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnEste.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnAnadir)
								.addComponent(btnImagen))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnEste.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnEliminar)
								.addComponent(btnBorrar)))
						.addGroup(gl_pnEste.createSequentialGroup()
							.addComponent(btnReproducir)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancelar)))
					.addContainerGap())
		);
		pnEste.setLayout(gl_pnEste);
		
		pnOeste = new JPanel();
		pnOeste.setPreferredSize(new Dimension(250, 10));
		panelVentana.add(pnOeste, BorderLayout.WEST);
		pnOeste.setVisible(false);
		
		scrollPane_1 = new JScrollPane();
		
		tablaRecientes = new TablaRecientes(this);
		scrollPane_1.setViewportView(tablaRecientes);
		
		btnReproducir_1 = new JButton("Reproducir");
		btnReproducir_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tablaRecientes.reproducir();
			}
		});
		GroupLayout gl_pnOeste = new GroupLayout(pnOeste);
		gl_pnOeste.setHorizontalGroup(
			gl_pnOeste.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnOeste.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnOeste.createParallelGroup(Alignment.LEADING)
						.addComponent(btnReproducir_1, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnOeste.setVerticalGroup(
			gl_pnOeste.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnOeste.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnReproducir_1))
		);
		pnOeste.setLayout(gl_pnOeste);
		
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
		
		mntmAbrirStriming = new JMenuItem("Abrir striming");
		mntmAbrirStriming.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Striming ruta = new Striming();
				ruta.mostrar(reproduccion);
			}
		});
		mnArchivo.add(mntmAbrirStriming);
		
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
				fullScreenStrategy = new DefaultFullScreenStrategy(frame);
				frame.setResizable(false);
				fullScreenStrategy.enterFullScreenMode();
				
				mntmPantallaCompleta.setEnabled(false);
				mntmRetornarPantalla.setEnabled(true);
			}
		});
		mnAjustes.add(mntmPantallaCompleta);
		
		mntmRetornarPantalla = new JMenuItem("Retornar Pantalla");
		mntmRetornarPantalla.setEnabled(false);
		mntmRetornarPantalla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				fullScreenStrategy.exitFullScreenMode();
				frame.setResizable(true);
				mntmPantallaCompleta.setEnabled(true);
				mntmRetornarPantalla.setEnabled(false);
			}
		});
		mnAjustes.add(mntmRetornarPantalla);
		
		mnLista = new JMenu("Lista");
		menuBar.add(mnLista);
		
		mntmGuardar = new JMenuItem("Guardar");
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardar();
				
			}
		});
		mnLista.add(mntmGuardar);
		
		mntmCargar = new JMenuItem("Cargar");
		mntmCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargar();
			}
		});
		mnLista.add(mntmCargar);
		
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
