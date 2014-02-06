package org.hectordam.reproductorHector.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class Filtro extends FileFilter {

	@Override
	public boolean accept(File fichero) {
		
		if (fichero.isDirectory())
			return true;
		
		if (fichero.getName().indexOf(".") == -1)
			return false;
		
		String extension = fichero.getName().substring(fichero.getName().lastIndexOf("."), fichero.getName().length());
		
		// filtros de video
		if (extension.equals(".flv") || extension.equals(".avi") || extension.equals(".mpeg") || extension.equals(".wma") || extension.equals(".mp4"))
			return true;
		
		// filtros de musica
		if (extension.equals(".mp3") || extension.equals(".mp4") || extension.equals(".mkv"))
			return true;
		
		return false;
	}

	@Override
	public String getDescription() {
		return "Archivos de video";
	}

	
}
