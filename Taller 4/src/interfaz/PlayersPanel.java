package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayersPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 5679299933104336814L;

    // Textos y configuraciones constantes
    private static final String NEW = "NUEVO";
    private static final String RESTART = "REINICIAR";
    private static final String TOP_10 = "TOP 10";
    private static final String CHANGE_PLAYER = "CAMBIAR JUGADOR";
    private static final Color BUTTON_COLOR = new Color(255, 255, 255); // Color de fondo de los botones
    private static final Color BUTTON_TEXT_COLOR = new Color(140, 90, 108); // Nuevo color de la letra
    private static final Font FONT = new Font("Arial", Font.PLAIN, 20);

    private Window father; // Referencia a la ventana principal
    private JButton newGameButton; // Botón para comenzar un nuevo juego
    private JButton restartButton; // Botón para reiniciar el juego
    private JButton top10Button; // Botón para ver la lista de los 10 mejores puntajes
    private JButton changePlayerButton; // Botón para cambiar de jugador

    // Constructor de la clase
    public PlayersPanel(Window father, Color foregroundColor, Color backgroundColor) {
        this.father = father;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setOpaque(true);

        // Botón para comenzar un nuevo juego
        newGameButton = createButton(NEW);
        add(createPanel(newGameButton, backgroundColor));

        // Botón para reiniciar el juego
        restartButton = createButton(RESTART);
        add(createPanel(restartButton, backgroundColor));

        // Botón para ver la lista de los 10 mejores puntajes
        top10Button = createButton(TOP_10);
        add(createPanel(top10Button, backgroundColor));

        // Botón para cambiar de jugador
        changePlayerButton = createButton(CHANGE_PLAYER);
        add(createPanel(changePlayerButton, backgroundColor));
    }

    // Metodo para crear un botón
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setActionCommand(text);
        button.setForeground(getForeground());
        button.setBackground(BUTTON_COLOR);
        button.setFont(FONT);
        button.setForeground(BUTTON_TEXT_COLOR);
        return button;
    }

    // Metodo para crear un panel para el boton con el fondo especificado
    private JPanel createPanel(JButton button, Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setBackground(backgroundColor);
        panel.add(button);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(NEW))
            father.newBoard(); // Comenzar un nuevo juego
        else if (command.equals(RESTART))
            father.restartBoard(); // Reiniciar el juego
        else if (command.equals(TOP_10))
            father.showTop10(); // Mostrar la lista de los 10 mejores puntajes
        else
            father.changePlayer(); // Cambiar de jugador
    }
}
