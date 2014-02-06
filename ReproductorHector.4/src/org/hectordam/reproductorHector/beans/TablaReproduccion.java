package org.hectordam.reproductorHector.beans;

import java.awt.Button;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hectordam.reproductorHector.Ventana;
import org.hectordam.reproductorHector.base.Reproduccion;
import org.hectordam.reproductorHector.util.Filtro;

public class TablaReproduccion extends JTable{

	private DefaultTableModel modelo;
	private ArrayList<String> lista;
	
	private JButton btEliminar;
	private JButton btReproducir;
	
	public TablaReproduccion(JButton eliminar, JButton reproducir){
		
		String[] columna = {"lista"};
		
		modelo = new DefaultTableModel(columna, 0);
		this.setModel(modelo);
		
		this.lista = new ArrayList<String>();
		this.btReproducir = reproducir;
		this.btEliminar = eliminar;
	}
	
	public void listar(){
		
		modelo.setNumRows(0);

		for(int i = 0; i < lista.size(); i++){
			
			File fichero = new File(lista.get(i));
			
			String[] fila = {fichero.getName()};

			modelo.addRow(fila);
		}
		
	}
	
	public void anadir(){
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new Filtro());
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION){
			return;
		}
		
		lista.add(fileChooser.getSelectedFile().getAbsolutePath());
		listar();
		
		btEliminar.setEnabled(true);
		btReproducir.setEnabled(true);
	}
	
	public void eliminar(){
		
		if(getSelectedRow() == -1){
			return;
		}
		
		lista.remove(getSelectedRow());
		
		listar();
		
		if(lista.size() == 0){
			btEliminar.setEnabled(false);
			btReproducir.setEnabled(false);
		}
		
	}

	public ArrayList<String> getLista() {
		return lista;
	}

	public void setLista(ArrayList<String> lista) {
		this.lista = lista;
	}
	
}
