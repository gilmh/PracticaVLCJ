package org.hectordam.reproductorHector.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.hectordam.reproductorHector.Ventana;

public class TablaRecientes extends JTable{

	private DefaultTableModel modelo;
	private ArrayList<String> lista;
	private Ventana ventana;
	
	public TablaRecientes(Ventana ventana){
		this.ventana = ventana;
		
		String[] columna = {"Abiertos recientemente"};
		
		modelo = new DefaultTableModel(columna, 0);
		
		setModel(modelo);
		
		lista = new ArrayList<String>();
	}
	
	public void listar(){
		
		modelo.setNumRows(0);
		for(int i = lista.size()-1; i >=0; i--){
			lista.remove(i);
		}
		
		try {
			BufferedReader reciente = new BufferedReader (new FileReader("Reciente"));
			String frase = reciente.readLine();
			while(frase != null){
				
				lista.add(frase);
				frase = reciente.readLine();
			}
			reciente.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = lista.size()-1; i >=0; i--){
			
			File fichero = new File(lista.get(i));
			
			String[] fila = {fichero.getName()};
			modelo.addRow(fila);
		}
	}
	
	public void reproducir(){
		
		ventana.reproduccion.reproducir(lista.get(lista.size()-getSelectedRow()-1));
	}
}
