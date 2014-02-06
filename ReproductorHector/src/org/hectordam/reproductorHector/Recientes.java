package org.hectordam.reproductorHector;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import org.hectordam.reproductorHector.beans.TablaRecientes;

public class Recientes extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private TablaRecientes tablaRecientes;
	
	public void mostrar(){
		setVisible(true);
		tablaRecientes.listar();
	}

	/**
	 * Create the frame.
	 */
	public Recientes() {
		setBounds(100, 100, 350, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 314, 340);
		contentPane.add(scrollPane);
		
		tablaRecientes = new TablaRecientes();
		scrollPane.setViewportView(tablaRecientes);
	}
}
