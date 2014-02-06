package org.hectordam.reproductorHector;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import org.hectordam.reproductorHector.beans.TablaReproduccion;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import jdk.internal.jfr.events.FileWriteEvent;

public class ListaReproduccion extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JScrollPane scrollPane;
	private TablaReproduccion tablaReproduccion;
	private JButton btReproducir;
	private JButton btAnadir;
	private JButton btEliminar;
	
	private Ventana ventana;
	private JButton btnCancelar;
	private JPanel panel_1;
	private JButton btImagen;
	private JTextField textField;
	private JLabel lblNombreDeLa;
	private JLabel label_1;
	private JMenuBar menuBar;
	private JMenu mnListas;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmCargar;
	private String imagen;

	public void mostrar(){
		setVisible(true);
	}

	public void reproducir(){
		
		ventana.reproduccion.reproducirLista(tablaReproduccion.getLista());
		
		btAnadir.setEnabled(false);
		btEliminar.setEnabled(false);
		btReproducir.setEnabled(false);
		btnCancelar.setEnabled(true);
	}
	
	public void cancelar(){
		
		ventana.reproduccion.setAcabar(true);
		
		btAnadir.setEnabled(true);
		btEliminar.setEnabled(true);
		btReproducir.setEnabled(true);
		btnCancelar.setEnabled(false);
	}
	
	private void guardar(){
		
		if(textField.getText() == ""){
			return;
		}
		
		try {
			PrintWriter ficheroGuardar = new PrintWriter(new BufferedWriter(new FileWriter(textField.getText()+".txt", false)));
			
			ficheroGuardar.println(label_1.getIcon());
			
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
				label_1.setIcon(new ImageIcon(linea));
			}
			else{
				label_1.setIcon(null);
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
	
	/**
	 * Create the frame.
	 */
	public ListaReproduccion(Ventana ventana){
		setBounds(100, 100, 586, 492);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnListas = new JMenu("Lista");
		menuBar.add(mnListas);
		
		mntmNewMenuItem = new JMenuItem("Guardar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
		});
		mnListas.add(mntmNewMenuItem);
		
		mntmCargar = new JMenuItem("Cargar");
		mntmCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargar();
			}
		});
		mnListas.add(mntmCargar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 82, 540, 275);
		panel.add(scrollPane);
		
		btReproducir = new JButton("Reproducir");
		btReproducir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reproducir();
			}
		});
		btReproducir.setEnabled(false);
		btReproducir.setBounds(445, 368, 105, 23);
		panel.add(btReproducir);
		
		btAnadir = new JButton("A\u00F1adir");
		btAnadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tablaReproduccion.anadir();
			}
		});
		btAnadir.setBounds(10, 368, 105, 23);
		panel.add(btAnadir);
		
		btEliminar = new JButton("Eliminar");
		btEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tablaReproduccion.eliminar();
			}
		});
		btEliminar.setEnabled(false);
		btEliminar.setBounds(125, 368, 105, 23);
		panel.add(btEliminar);
		
		tablaReproduccion = new TablaReproduccion(btEliminar, btReproducir);
		tablaReproduccion.setColumnSelectionAllowed(true);
		tablaReproduccion.setCellSelectionEnabled(true);
		scrollPane.setViewportView(tablaReproduccion);
		
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancelar();
			}
		});
		btnCancelar.setEnabled(false);
		btnCancelar.setBounds(330, 368, 105, 23);
		panel.add(btnCancelar);
		
		panel_1 = new JPanel();
		panel_1.setBounds(426, 7, 100, 75);
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		label_1 = new JLabel("");
		panel_1.add(label_1, BorderLayout.CENTER);
		
		btImagen = new JButton("Imagen");
		btImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChoose = new JFileChooser();
				if(fileChoose.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
					return;
				}
				
				label_1.setIcon(new ImageIcon(fileChoose.getSelectedFile().getAbsolutePath()));
				imagen = fileChoose.getSelectedFile().getAbsolutePath();
			}
		});
		btImagen.setBounds(311, 31, 105, 23);
		panel.add(btImagen);
		
		textField = new JTextField();
		textField.setBounds(125, 32, 172, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		lblNombreDeLa = new JLabel("Nombre de la lista");
		lblNombreDeLa.setBounds(10, 35, 105, 14);
		panel.add(lblNombreDeLa);
		
		this.setLocationRelativeTo(null);
		this.ventana = ventana;
	}
}
