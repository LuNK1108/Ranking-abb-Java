import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private BinarySearchTree tree;
    private TreePanel treePanel;

    private JTextField nicknameField;
    private JTextField rankingField;
    private JTextField searchField;

    public MainFrame() {
        tree = new BinarySearchTree();

        setTitle("Ranking de Jogadores - ABB");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        treePanel = new TreePanel(tree);
        treePanel.setPreferredSize(new Dimension(5000, 3000));

        JScrollPane scrollPane = new JScrollPane(treePanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1));

        JPanel insertPanel = new JPanel();

        nicknameField = new JTextField(10);
        rankingField = new JTextField(5);

        JButton insertButton = new JButton("Inserir");
        JButton loadCsvButton = new JButton("Carregar CSV");

        insertPanel.add(new JLabel("Nickname:"));
        insertPanel.add(nicknameField);
        insertPanel.add(new JLabel("Ranking:"));
        insertPanel.add(rankingField);
        insertPanel.add(insertButton);
        insertPanel.add(loadCsvButton);

        JPanel searchPanel = new JPanel();

        searchField = new JTextField(10);

        JButton searchButton = new JButton("Buscar");
        JButton removeButton = new JButton("Remover");

        searchPanel.add(new JLabel("Nome:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(removeButton);

        controlPanel.add(insertPanel);
        controlPanel.add(searchPanel);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        insertButton.addActionListener(e -> insertPlayer());
        searchButton.addActionListener(e -> searchPlayer());
        removeButton.addActionListener(e -> removePlayer());
        loadCsvButton.addActionListener(e -> loadCsv());
    }

    private void insertPlayer() {
        try {
            String nickname = nicknameField.getText();
            int ranking = Integer.parseInt(rankingField.getText());

            if (nickname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um nickname.");
                return;
            }

            Player player = new Player(nickname, ranking);
            tree.insert(player);

            nicknameField.setText("");
            rankingField.setText("");

            treePanel.repaint();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ranking precisa ser um número inteiro.");
        }
    }

    private void searchPlayer() {
        String name = searchField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome para buscar.");
            return;
        }

        boolean found = tree.search(name);

        if (found) {
            JOptionPane.showMessageDialog(this, "Jogador encontrado: " + name);
        } else {
            JOptionPane.showMessageDialog(this, "Jogador não encontrado.");
        }
    }

    private void removePlayer() {
        String name = searchField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome para remover.");
            return;
        }

        Player removed = tree.remove(name);

        if (removed != null) {
            JOptionPane.showMessageDialog(this, "Jogador removido: " + removed.getNickname());
            treePanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Jogador não encontrado.");
        }
    }

    private void loadCsv() {
        JFileChooser fileChooser = new JFileChooser();

        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try {
                loadPlayersFromCsv(file);
                treePanel.repaint();
                JOptionPane.showMessageDialog(this, "CSV carregado com sucesso.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar CSV.");
            }
        }
    }

    private void loadPlayersFromCsv(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;

        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        reader.close();

        for (int i = lines.size() - 1; i >= 0; i--) {
            String currentLine = lines.get(i);

            if (currentLine.toLowerCase().contains("nickname")) {
                continue;
            }

            String[] data = currentLine.split(",");

            if (data.length >= 2) {
                String nickname = data[0].trim();
                int ranking = Integer.parseInt(data[1].trim());

                tree.insert(new Player(nickname, ranking));
            }
        }
    }
}