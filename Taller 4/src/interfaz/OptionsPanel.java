package interfaz;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OptionsPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 6572277267365782756L;
    
    // Textos y configuraciones constantes
    private static final String SIZE = "Tamaño: ";
    private static final String[] BOARD_SIZES = {"3x3", "4x4", "5x5", "6x6", "7x7", "8x8", "9x9", "10x10", "11x11"};
    private static final int I = 3; // Desplazamiento para el índice seleccionado
    private static final int START_SIZE = 2; // Índice inicial del tamaño del tablero
    private static final String DIFFICULTY = "        Dificultad: ";
    private static final String EASY = "Fácil";
    private static final String MEDIUM = "Medio";
    private static final String HARD = "Difícil";
    private static final Font FONT = new Font("Arial", Font.PLAIN, 16);

    private Window father; // Referencia a la ventana principal
    private JLabel sizeLabel; // Etiqueta para el tamaño del tablero
    private JComboBox<String> sizesComboBox; // Cuadro de selección para el tamaño del tablero
    private JLabel diffLabel; // Etiqueta para la dificultad del juego
    private JRadioButton easyButton; // Botón de radio para la dificultad fácil
    private JRadioButton midButton; // Botón de radio para la dificultad media
    private JRadioButton hardButton; // Botón de radio para la dificultad difícil
    private ButtonGroup buttonGroup; // Grupo de botones de radio para selección única

    // Constructor de la clase
    public OptionsPanel(Window father, Color foregroundColor, Color backgroundColor) {
        this.father = father;

        setLayout(new FlowLayout());
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setOpaque(true);

        // Etiqueta y cuadro de selección para el tamaño del tablero
        sizeLabel = new JLabel(SIZE);
        sizeLabel.setForeground(getForeground());
        sizeLabel.setFont(FONT);
        add(sizeLabel);

        sizesComboBox = new JComboBox<String>(BOARD_SIZES);
        sizesComboBox.setSelectedIndex(START_SIZE); // Establece el tamaño inicial seleccionado
        sizesComboBox.addActionListener(this); // Registra este panel como un oyente de eventos
        sizesComboBox.setActionCommand(SIZE); // Etiqueta para el comando del evento
        sizesComboBox.setForeground(getForeground());
        sizesComboBox.setBackground(getBackground());
        sizesComboBox.setFont(FONT);
        add(sizesComboBox);

        // Etiqueta y botones de radio para la dificultad del juego
        diffLabel = new JLabel(DIFFICULTY);
        diffLabel.setForeground(getForeground());
        diffLabel.setFont(FONT);
        add(diffLabel);

        easyButton = new JRadioButton(EASY);
        easyButton.setSelected(true); // Establece "Fácil" como la dificultad seleccionada por defecto
        easyButton.addActionListener(this); // Registra este panel como oyente de eventos
        easyButton.setActionCommand(EASY); // Etiqueta para el comando del evento
        easyButton.setForeground(getForeground());
        easyButton.setBackground(getBackground());
        easyButton.setFont(FONT);
        add(easyButton);

        midButton = new JRadioButton(MEDIUM);
        midButton.addActionListener(this); // Registra este panel como oyente de eventos
        midButton.setActionCommand(MEDIUM); // Etiqueta para el comando del evento
        midButton.setForeground(getForeground());
        midButton.setBackground(getBackground());
        midButton.setFont(FONT);
        add(midButton);

        hardButton = new JRadioButton(HARD);
        hardButton.addActionListener(this); // Registra este panel como oyente de eventos
        hardButton.setActionCommand(HARD); // Etiqueta para el comando del evento
        hardButton.setForeground(getForeground());
        hardButton.setBackground(getBackground());
        hardButton.setFont(FONT);
        add(hardButton);

        // Grupo de botones de radio para garantizar la selección única
        buttonGroup = new ButtonGroup();
        buttonGroup.add(easyButton);
        buttonGroup.add(midButton);
        buttonGroup.add(hardButton);
    }

    // Método para obtener el tamaño del tablero seleccionado
    public int getBoardSize() {
        return sizesComboBox.getSelectedIndex() + I; // Calcula el tamaño del tablero
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); // Obtiene el comando del evento
        if (command.equals(SIZE)) {
            father.resizeBoard(getBoardSize()); // Cambia el tamaño del tablero en función de la selección
        } else if (command.equals(EASY)) {
            father.setDifficulty(EASY); // Establece la dificultad en "Fácil"
        } else if (command.equals(MEDIUM)) {
            father.setDifficulty(MEDIUM); // Establece la dificultad en "Medio"
        } else {
            father.setDifficulty(HARD); // Establece la dificultad en "Difícil"
        }
    }
}