package interfaz;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.formdev.flatlaf.FlatLightLaf;

import uniandes.dpoo.taller4.modelo.Tablero;
import uniandes.dpoo.taller4.modelo.Top10;

public class Window extends JFrame {
    private static final long serialVersionUID = 8724079055290223626L;

    // Rutas de archivo y configuración de colores
    private static final String DATA_FILE_PATH = "data/top10.csv";
    private static final Color FOREGROUND_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(217, 118, 102);
    private static final String ORIGINAL_DIFF = "EASY";
    private static final int ORIGINAL_SIZE = 5;

    private File dataFile;
    private Top10 top10;
    private OptionsPanel northPanel;
    private BoardPanel centerPanel;
    private PlayersPanel playersPanel;
    private InfoPanel southPanel;
    private Tablero board;

    public Window() {
        dataFile = new File(DATA_FILE_PATH);
        top10 = new Top10();
        loadTop10();

        setLayout(new BorderLayout());

        // Panel izquierdo
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(200, 1));
        leftPanel.setBackground(BACKGROUND_COLOR);
        leftPanel.setPreferredSize(new Dimension(80, leftPanel.getHeight()));
        add(leftPanel, BorderLayout.WEST);

        // Panel de opciones en la parte superior
        northPanel = new OptionsPanel(this, FOREGROUND_COLOR, BACKGROUND_COLOR);
        add(northPanel, BorderLayout.NORTH);

        board = new Tablero(northPanel.getBoardSize());
        centerPanel = new BoardPanel(this, BACKGROUND_COLOR, board);
        add(centerPanel, BorderLayout.CENTER);

        playersPanel = new PlayersPanel(this, FOREGROUND_COLOR, BACKGROUND_COLOR);
        add(playersPanel, BorderLayout.EAST);

        southPanel = new InfoPanel(this, FOREGROUND_COLOR, BACKGROUND_COLOR);
        add(southPanel, BorderLayout.SOUTH);

        // Guardar registros del TOP 10 al cerrar la ventana
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveTop10();
            }
        });

        setTitle("Lights Out");
        setSize(1400, 900); // Aumentamos el ancho de la ventana
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Método para procesar un movimiento en el juego
    public void play(int[] box) {
        board.play(box[0], box[1]);
        southPanel.setPlays(board.darJugadas());
        southPanel.setPoints(board.calcularPuntaje());
        boardLitUp();
    }

    // Método para verificar si se ha completado el tablero
    private void boardLitUp() {
        if (board.tableroIluminado()) {
            int points = board.calcularPuntaje();
            if (top10.esTop10(points)) {
                top10.agregarRegistro(southPanel.getPlayerName(), points);
                saveTop10();
            }
        }
    }

    // Iniciar un nuevo juego
    public void newBoard() {
        resizeBoard(centerPanel.getBoardSize());
    }

    // Reiniciar el juego con la dificultad original
    public void restartBoard() {
        centerPanel.setDifficulty(ORIGINAL_DIFF);
        resizeBoard(ORIGINAL_SIZE);
    }

    // Mostrar la lista de los 10 mejores puntajes
    public void showTop10() {
        new Top10Dialog(top10.darRegistros(), FOREGROUND_COLOR, BACKGROUND_COLOR);
    }

    // Cambiar el nombre del jugador
    public void changePlayer() {
        new ChangePlayerDialog(this, FOREGROUND_COLOR, BACKGROUND_COLOR);
    }

    // Establecer el nombre del jugador en la interfaz
    public void setPlayer(String playerName) {
        southPanel.setPlayerName(playerName);
    }

    // Cambiar el tamaño del tablero
    public void resizeBoard(int newSize) {
        String difficulty = centerPanel.getDifficulty();
        remove(centerPanel);
        board = new Tablero(newSize);
        centerPanel = new BoardPanel(this, BACKGROUND_COLOR, board);
        centerPanel.setDifficulty(difficulty);
        add(centerPanel, BorderLayout.CENTER);
        southPanel.setPoints(getPoints());
        southPanel.setPlays(0);
        setVisible(false);
        setVisible(true);
    }

    // Establecer la dificultad del juego
    public void setDifficulty(String diff) {
        centerPanel.setDifficulty(diff);
    }

    // Obtener el puntaje actual del juego
    public int getPoints() {
        return board.calcularPuntaje();
    }

    // Cargar registros del TOP 10 desde un archivo
    private void loadTop10() {
        top10.cargarRecords(dataFile);
    }

    // Guardar registros del TOP 10 en un archivo
    protected void saveTop10() {
        try {
            top10.salvarRecords(dataFile);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FlatLightLaf.install(); // Configurar el aspecto FlatLaf
        new Window(); // Iniciar la ventana principal
    }
}
