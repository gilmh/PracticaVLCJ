package org.hectordam.reproductorHector;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import org.hectordam.reproductorHector.beans.TablaReproduccion;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	
	/**
	 * Create the frame.
	 */
	public ListaReproduccion(Ventana ventana){
		setBounds(100, 100, 382, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 336, 346);
		panel.add(scrollPane);
		
		btReproducir = new JButton("Reproducir");
		btReproducir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reproducir();
			}
		});
		btReproducir.setEnabled(false);
		btReproducir.setBounds(241, 368, 105, 23);
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
		btnCancelar.setBounds(241, 392, 105, 23);
		panel.add(btnCancelar);
		
		this.ventana = ventana;
	}
}
