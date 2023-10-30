package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ChangePlayerDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static final String LABEL_TEXT = "Nombre de jugador: ";
	private static final String BUTTON_TEXT = "JUGAR";
	private static final Color BUTTON_COLOR = new Color(54,121,105);
	private static final Font FONT = new Font("Arial", Font.PLAIN, 16);
	
	private Window father;
	private JLabel label;
	private JTextField textField;
	private JButton button;
	
	public ChangePlayerDialog(Window father, Color foregroundColor, Color backgroundColor)
	{
		this.father = father;
		
		setLayout(new FlowLayout());
		getContentPane().setBackground(backgroundColor);
		getContentPane().setForeground(foregroundColor);
		
		label = new JLabel(LABEL_TEXT);
		label.setFont(FONT);
		label.setBackground(backgroundColor);
		label.setForeground(foregroundColor);
		add(label);
		
		textField = new JTextField();
		textField.setFont(FONT);
		textField.setPreferredSize(new Dimension(200, 30));
		add(textField);
		
		button = new JButton(BUTTON_TEXT);
		button.setBackground(BUTTON_COLOR);
		button.setForeground(foregroundColor);
		button.setFont(FONT);
		button.addActionListener(this);
		add(button);
		
		setTitle("Cambiar jugador");
		setSize(500, 100);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		father.setPlayer(textField.getText());
		dispose();
	}

}