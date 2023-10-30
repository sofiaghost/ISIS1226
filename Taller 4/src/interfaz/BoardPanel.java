package interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import uniandes.dpoo.taller4.modelo.Tablero;

public class BoardPanel extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;

    // Definición de colores
    private static final Color ON_COLOR = new Color(242, 161, 114); // Color de las luces encendidas
    private static final Color OFF_COLOR = Color.WHITE; // Color de las luces apagadas
    private static final Color LINE_COLOR = new Color(140, 90, 108); // Color de las líneas del tablero

    // Niveles de dificultad
    private static final String EASY = "EASY";
    private static final String MEDIUM = "MEDIUM";
    private static final String HARD = "HARD";

    private Window father; // Referencia a la ventana principal
    private Tablero board; // Referencia al tablero del juego
    private int boardSize; // Tamaño del tablero
    private String difficulty; // Dificultad actual

    // Constructor de la clase
    public BoardPanel(Window father, Color backgroundColor, Tablero board) {
        this.father = father;
        this.board = board;
        this.difficulty = EASY; // Establecer la dificultad por defecto
        setBackground(backgroundColor);
        addMouseListener(this); // Agregar oyente de eventos de ratón
    }

    public int getBoardSize() {
        return boardSize;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        repaint(); // Redibujar el panel cuando cambia la dificultad
    }

    // Método para pintar el panel
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        boolean[][] boardMatrix = board.darTablero();
        boardSize = boardMatrix.length;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int x = (getWidth() / boardSize) * j;
                int y = (getHeight() / boardSize) * i;
                int width = getWidth() / boardSize;
                int height = getHeight() / boardSize;

                // Establecer el color de acuerdo al estado de la luz
                if (boardMatrix[i][j]) {
                    g2d.setColor(OFF_COLOR); // Configura el color OFF_COLOR para luces apagadas
                } else {
                    g2d.setColor(ON_COLOR); // Configura el color ON_COLOR para luces encendidas
                }

                g2d.fillRect(x, y, width, height);
                g2d.setColor(LINE_COLOR); // Configura el color LINE_COLOR para las líneas del tablero
                g2d.drawRect(x, y, width, height);
            }
        }
        paintDifficulty(g2d); // Dibujar el nivel de dificultad
    }

    // Método para dibujar el nivel de dificultad
    private void paintDifficulty(Graphics2D g2d) {
        if (difficulty.equals(MEDIUM)) {
            paintMedium(g2d);
        } else if (difficulty.equals(HARD)) {
            paintHard(g2d);
        }
    }

    // Método para ocultar luces en la mitad del tablero
    private void paintMedium(Graphics2D g2d) {
        int half = (int) boardSize / 2;
        int x = ((getWidth() / boardSize) * half);
        int y = ((getHeight() / boardSize) * half);
        int width = getWidth() / boardSize;
        int height = getHeight() / boardSize;
        g2d.setColor(LINE_COLOR);
        g2d.fillRect(x, y, width, height);
    }

    // Método para ocultar luces en la mitad y en dos extremos del tablero
    private void paintHard(Graphics2D g2d) {
        paintMedium(g2d);
        int x = 0;
        int y = 0;
        int width = getWidth() / boardSize;
        int height = getHeight() / boardSize;
        g2d.setColor(LINE_COLOR);
        g2d.fillRect(x, y, width, height);

        x = (getWidth() / boardSize) * (boardSize - 1);
        y = (getHeight() / boardSize) * (boardSize - 1);
        g2d.fillRect(x, y, width, height);
    }

    // Método que se activa al hacer clic en el panel
    @Override
    public void mouseClicked(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();

        int[] box = coordinatesToBox(clickX, clickY);
        System.out.println(box[0] + ", " + box[1]);
        father.play(box); // Llamar al método de la ventana principal
        repaint(); // Redibujar el panel después de jugar
    }

    // Método para convertir coordenadas de clic en una casilla del tablero
    private int[] coordinatesToBox(int x, int y) {
        int boxHeight = getHeight() / boardSize;
        int boxWidth = getWidth() / boardSize;
        int row = (int) (y / boxHeight);
        int col = (int) (x / boxWidth);
        return new int[] { row, col };
    }

    // Otros métodos
    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
