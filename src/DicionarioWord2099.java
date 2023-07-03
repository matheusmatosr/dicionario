import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class DicionarioWord2099 extends JFrame {

    // Minhas variaveis globais

    private JTextArea areaTexto;
    private HashMap<String, Boolean> dicionario;
    private File arquivoDicionario;
    private JButton botaoCancelar;

    public DicionarioWord2099() { // Construtor de classe
        dicionario = carregarDicionario();

        setTitle("Word 2099");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        JPanel painelPrincipal = new JPanel(); // Tela do dicionario
        painelPrincipal.setLayout(new BorderLayout());

        areaTexto = new JTextArea(); // Caso a tela for grande, possibilidade de "scrollar" ela
        JScrollPane scroll = new JScrollPane(areaTexto);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        JButton checarBotao = new JButton("Verificar"); // Botão de confirmação
        checarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checarOrtografia();
            }
        });
        painelPrincipal.add(checarBotao, BorderLayout.SOUTH);

        JButton abrirBotao = new JButton("Abrir Arquivo"); // Botão para abrir o arquivo txt.
        abrirBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                àbrirArquivo();
            }
        });
        painelPrincipal.add(abrirBotao, BorderLayout.NORTH);

        add(painelPrincipal);
        setVisible(true);
    }

    private HashMap<String, Boolean> carregarDicionario() { // Sistema para carregar/criar o dicionario
        HashMap<String, Boolean> dicionarioCarregado = null;
        arquivoDicionario = new File("dicionario.txt");

        if (arquivoDicionario.exists()) { // Verifica se o arquivo do dicionário existe
            try (FileInputStream fileInputStream = new FileInputStream(arquivoDicionario);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

                dicionarioCarregado = (HashMap<String, Boolean>) objectInputStream.readObject(); // Lê o dicionário
                                                                                                 // serializado apartir
                                                                                                 // do arquivo // do
                                                                                                 // arquivo
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar o dicionário: " + e.getMessage());
            }
        }

        if (dicionarioCarregado == null) { // Se o dicionário não existir ou ocorrer algum erro, cria um dicionário
                                           // vazio com algumas palavras
            dicionarioCarregado = new HashMap<>();
            dicionarioCarregado.put("matheus", true);
            dicionarioCarregado.put("jessica", true);
            dicionarioCarregado.put("hiago", true);
            dicionarioCarregado.put("lucas", true);
        }

        return dicionarioCarregado;
    }

    private void salvarDicionario() { // Salva o dicionário serializado no arquivo
        try (FileOutputStream fileOutputStream = new FileOutputStream(arquivoDicionario);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(dicionario);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o dicionário: " + e.getMessage());
        }
    }

    private void adicionarPalavraDicionario(String palavra) {

        dicionario.put(palavra, true); // Adiciona a palavra ao dicionário

        salvarDicionario(); // Salva o dicionário atualizado no arquivo
    }

    private boolean verificarPalavraDicionario(String palavra) {

        return dicionario.containsKey(palavra); // Verifica se a palavra está presente no dicionário
    }

    private void checarOrtografia() {
        String texto = areaTexto.getText();
        String[] palavras = texto.split("\\W+");

        StringBuilder palavrasErradas = new StringBuilder();

        for (String palavra : palavras) {
            String letraMin = palavra.toLowerCase();
            if (!verificarPalavraDicionario(letraMin)) { // Exibe uma caixa de diálogo perguntando se o usuário deseja
                                                         // adicionar a palavra ao dicionário

                int op = JOptionPane.showConfirmDialog(this, "Palavra incorreta encontrada: " + palavra
                        + "\nDeseja adicionar essa palavra ao dicionário?", "Verificação Ortográfica",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (op == JOptionPane.YES_OPTION) { // O usuário optou por adicionar a palavra ao dicionário
                    adicionarPalavraDicionario(letraMin);
                } else if (op == JOptionPane.CANCEL_OPTION) { // O usuário optou por cancelar a verificação
                    botaoCancelar.setEnabled(false);
                    JOptionPane.showMessageDialog(this, "Verificação cancelada pelo usuário.");
                    return;
                } else { // O usuário optou por não adicionar a palavra ao dicionário
                    palavrasErradas.append(palavra).append("\n");
                }
            }
        }

        if (palavrasErradas.length() > 0) { // Exibe as palavras incorretas encontradas

            JOptionPane.showMessageDialog(this, "Palavras incorretas:\n" + palavrasErradas.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma palavra incorreta encontrada!");
        }
    }

    private void àbrirArquivo() { // Abre o arquivo de texto para começar a leitura.
        JFileChooser selecionarArquivo = new JFileChooser();
        selecionarArquivo.setCurrentDirectory(new File("."));
        int op = selecionarArquivo.showOpenDialog(this);
        if (op == JFileChooser.APPROVE_OPTION) {
            String caminhoArquivo = selecionarArquivo.getSelectedFile().getAbsolutePath();
            try {
                FileReader leitor = new FileReader(caminhoArquivo);
                BufferedReader bufferedReader = new BufferedReader(leitor);
                StringBuilder conteudoArquivo = new StringBuilder();
                String linha;

                while ((linha = bufferedReader.readLine()) != null) {
                    conteudoArquivo.append(linha).append("\n");
                }

                bufferedReader.close();
                areaTexto.setText(conteudoArquivo.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir o arquivo: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) { // Main
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DicionarioWord2099();
            }
        });
    }
}