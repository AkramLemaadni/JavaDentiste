package Infermier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InfermierForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable infermierTable;
    private List<Infermier> infermiers;
    private final String filePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\acc.txt";

    public InfermierForm() {
        setLayout(new BorderLayout());

        // Add Title
        JLabel title = new JLabel("Gestion des Infirmiers", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Initialize the table
        setupTable();

        // Initialize buttons
        setupButtons();

        // Load infermiers from the file
        loadInfermiersFromFile();
    }

    private void setupTable() {
        String[] columns = {"ID", "Nom", "Téléphone", "Email", "Nom d'utilisateur"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        infermierTable = new JTable(tableModel);
        infermierTable.setRowHeight(30);
        infermierTable.setFont(new Font("Arial", Font.PLAIN, 14));
        infermierTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        infermierTable.getTableHeader().setBackground(new Color(200, 200, 200));
        infermierTable.getTableHeader().setForeground(Color.BLACK);
        infermierTable.setSelectionBackground(new Color(173, 216, 230));
        infermierTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(infermierTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton editButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");

        configureButtonStyle(editButton);
        configureButtonStyle(deleteButton);

        editButton.addActionListener(e -> onUpdate());
        deleteButton.addActionListener(e -> onDelete());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void configureButtonStyle(JButton button) {
        button.setPreferredSize(new Dimension(100, 30));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(54, 162, 235));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(54, 162, 235)));
    }

    private void loadInfermiersFromFile() {
        infermiers = Infermier.loadFromFile(filePath);
        tableModel.setRowCount(0);
        for (Infermier infermier : infermiers) {
            tableModel.addRow(new Object[]{
                    infermier.getId(),
                    infermier.getNom(),
                    infermier.getNumeroTel(),
                    infermier.getEmail(),
                    infermier.getNomUtilisateur()
            });
        }
    }

    private void onUpdate() {
        int selectedRow = infermierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un infirmier à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Infermier infermier = infermiers.get(selectedRow);

        JTextField txtNom = new JTextField(infermier.getNom());
        JTextField txtTel = new JTextField(infermier.getNumeroTel());
        JTextField txtEmail = new JTextField(infermier.getEmail());
        JTextField txtUsername = new JTextField(infermier.getNomUtilisateur());
        JPasswordField txtPassword = new JPasswordField(infermier.getMotDePasse());

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Nom:"));
        panel.add(txtNom);
        panel.add(new JLabel("Téléphone:"));
        panel.add(txtTel);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Nom d'utilisateur:"));
        panel.add(txtUsername);
        panel.add(new JLabel("Mot de passe:"));
        panel.add(txtPassword);

        int result = JOptionPane.showConfirmDialog(this, panel, "Modifier Infirmier", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            infermier.setNom(txtNom.getText());
            infermier.setNumeroTel(txtTel.getText());
            infermier.setEmail(txtEmail.getText());
            infermier.setNomUtilisateur(txtUsername.getText());
            infermier.setMotDePasse(new String(txtPassword.getPassword()));

            Infermier.saveAllToFile(filePath, infermiers);
            loadInfermiersFromFile();
            JOptionPane.showMessageDialog(this, "Infirmier modifié avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onDelete() {
        int selectedRow = infermierTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un infirmier à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cet infirmier ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            infermiers.remove(selectedRow);
            Infermier.saveAllToFile(filePath, infermiers);
            loadInfermiersFromFile();
            JOptionPane.showMessageDialog(this, "Infirmier supprimé avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
