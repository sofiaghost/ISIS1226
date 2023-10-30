package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import uniandes.dpoo.taller4.modelo.RegistroTop10;

public class Top10Dialog extends JDialog {
    private static final long serialVersionUID = 8470471086162782477L;

    // Configuraciones de fuente y color
    private static final Font FONT = new Font("Arial", Font.BOLD, 16);
    private static final Color BACKGROUND_COLOR = new Color(217, 118, 102); // Cambiar el color de fondo a verde
    private static final Color FOREGROUND_COLOR = Color.WHITE; // Cambiar el color de letra a blanco

    public Top10Dialog(Collection<RegistroTop10> top10Records, Color foregroundColor, Color backgroundColor) {
        // Configura los colores de fondo y primer plano
        getContentPane().setBackground(BACKGROUND_COLOR); // Cambiar el color de fondo a verde
        getContentPane().setForeground(FOREGROUND_COLOR); // Cambiar el color de letra a blanco

        // Crea y muestra la lista de registros del TOP 10
        createRecordsList(top10Records);

        // Configura el título y dimensiones de la ventana de diálogo
        setTitle("Registros del TOP 10");
        setSize(150, 300);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // Método para crear la lista de registros del TOP 10
    private void createRecordsList(Collection<RegistroTop10> top10Records) {
        DefaultListModel<String> model = new DefaultListModel<String>();

        // Agrega una etiqueta para encabezado de la lista
        model.addElement(" #  Nombre pts ");

        String labelText;
        String name;
        String pts;
        int i = 1;
        for (RegistroTop10 registro : top10Records) {
            // Agrega un asterisco a los primeros tres registros
            if (i <= 3)
                labelText = "*";
            else
                labelText = " ";

            // Agrega el número de posición del registro
            labelText += Integer.toString(i);

            // Ajusta la alineación del número de posición
            if (labelText.length() < 3)
                labelText += "    ";
            else
                labelText += "   ";

            name = registro.darNombre();
            pts = Integer.toString(registro.darPuntos());

            // Agrega el nombre del jugador y la puntuación
            labelText += name + "    " + pts;

            // Agrega la etiqueta a la lista de modelos
            model.addElement(labelText);
            i++;
        }

        // Crea una lista con el modelo de datos y configura su apariencia
        JList<String> top10RecordsList = new JList<String>(model);
        top10RecordsList.setBackground(BACKGROUND_COLOR); // Cambiar el color de fondo a verde
        top10RecordsList.setForeground(FOREGROUND_COLOR); // Cambiar el color de letra a blanco
        top10RecordsList.setFont(FONT);

        // Crea un panel de desplazamiento para la lista
        JScrollPane scroll = new JScrollPane(top10RecordsList);
        scroll.setBackground(BACKGROUND_COLOR); // Cambiar el color de fondo a verde
        scroll.setForeground(FOREGROUND_COLOR); // Cambiar el color de letra a blanco
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Agrega el panel de desplazamiento a la ventana de diálogo
        add(scroll);
    }
}

