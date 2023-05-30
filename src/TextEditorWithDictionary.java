import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class TextEditorWithDictionary extends JFrame {

    private JTextArea textArea;
    private HashSet<String> dictionary;

    public TextEditorWithDictionary() {
        dictionary = new HashSet<>();

        dictionary.add("exemplo");
        dictionary.add("java");
        dictionary.add("editor");
        dictionary.add("texto");

        setTitle("Editor de Texto com Dicionário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton checkButton = new JButton("Verificar");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSpelling();
            }
        });
        mainPanel.add(checkButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void checkSpelling() {
        String text = textArea.getText();
        String[] words = text.split("\\W+");

        StringBuilder misspelledWords = new StringBuilder();

        for (String word : words) {
            if (!dictionary.contains(word.toLowerCase())) {
                misspelledWords.append(word).append("\n");
            }
        }

        if (misspelledWords.length() > 0) {
            JOptionPane.showMessageDialog(this, "Palavras incorretas:\n" + misspelledWords.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma palavra incorreta encontrada!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TextEditorWithDictionary();
            }
        });
    }
}
