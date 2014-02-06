package org.hectordam.reproductorHector;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import org.hectordam.reproductorHector.base.Reproduccion;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Striming extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private Reproduccion reproduccion;
	
	public void mostrar(Reproduccion reproduccion){
		
		setVisible(true);
		this.reproduccion = reproduccion;
	}
	
	private void aceptar(){
		
		reproduccion.reproducirDVD(textField.getText());
		setVisible(false);
	}
	
	private void cancelar(){
		
		textField.setText("");
		setVisible(false);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Striming dialog = new Striming();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Striming() {
		setResizable(false);
		setBounds(100, 100, 607, 94);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			textField = new JTextField();
			contentPanel.add(textField, BorderLayout.CENTER);
			textField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						aceptar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelar();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
