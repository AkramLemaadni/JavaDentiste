package DossierMedicale;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DossierMedicaleForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable dossierTable;
    private List<DossierMedicale> dossiers;
    private final String dossierFilePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\dossiersMedicale.txt";
    private final String patientsFilePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\patients.txt";

    public DossierMedicaleForm() {
        setLayout(new BorderLayout());

        // Add Title
        JLabel title = new JLabel("Gestion des Dossiers Médicaux", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        setupTable();
        setupButtons();
        loadDossiersFromFile();
    }

    private void setupTable() {
        String[] columns = {"ID", "Patient Name", "CIN", "Mutualiste", "Dents", "Amount"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dossierTable = new JTable(tableModel);
        dossierTable.setRowHeight(30);
        dossierTable.setFont(new Font("Arial", Font.PLAIN, 14));
        dossierTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        dossierTable.getTableHeader().setBackground(new Color(200, 200, 200));
        dossierTable.getTableHeader().setForeground(Color.BLACK);
        dossierTable.setSelectionBackground(new Color(173, 216, 230));
        dossierTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(dossierTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        configureButtonStyle(addButton);
        configureButtonStyle(editButton);
        configureButtonStyle(deleteButton);

        addButton.addActionListener(e -> addDossier());
        editButton.addActionListener(e -> editDossier());
        deleteButton.addActionListener(e -> deleteDossier());

        buttonPanel.add(addButton);
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

    private void loadDossiersFromFile() {
        dossiers = DossierMedicale.loadFromFile(dossierFilePath);
        tableModel.setRowCount(0);
        for (DossierMedicale dossier : dossiers) {
            tableModel.addRow(new Object[]{
                    dossier.getId(),
                    dossier.getPatientName(),
                    dossier.getCin(),
                    dossier.getMutualiste(),
                    dossier.getDents(),
                    dossier.getAmount()
            });
        }
    }

    private List<String> loadPatientNames() {
        List<String> patientNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(patientsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    patientNames.add(parts[1]); // Assuming the name is in the second column
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading patients file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return patientNames;
    }

    private void addDossier() {
        DossierMedicale newDossier = showDossierDialog(null, "Add Dossier Medicale");
        if (newDossier != null) {
            newDossier.setId(dossiers.size() + 1);
            dossiers.add(newDossier);
            DossierMedicale.saveAllToFile(dossierFilePath, dossiers);
            loadDossiersFromFile();
            JOptionPane.showMessageDialog(this, "Dossier added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editDossier() {
        int selectedRow = dossierTable.getSelectedRow();
        if (selectedRow >= 0) {
            DossierMedicale dossier = dossiers.get(selectedRow);
            DossierMedicale updatedDossier = showDossierDialog(dossier, "Edit Dossier Medicale");
            if (updatedDossier != null) {
                dossiers.set(selectedRow, updatedDossier);
                DossierMedicale.saveAllToFile(dossierFilePath, dossiers);
                loadDossiersFromFile();
                JOptionPane.showMessageDialog(this, "Dossier updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a dossier to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDossier() {
        int selectedRow = dossierTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this dossier?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dossiers.remove(selectedRow);
                DossierMedicale.saveAllToFile(dossierFilePath, dossiers);
                loadDossiersFromFile();
                JOptionPane.showMessageDialog(this, "Dossier deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a dossier to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DossierMedicale showDossierDialog(DossierMedicale existingDossier, String title) {
        List<String> patientNames = loadPatientNames();
        JComboBox<String> cmbPatientName = new JComboBox<>(patientNames.toArray(new String[0]));
        if (existingDossier != null) {
            cmbPatientName.setSelectedItem(existingDossier.getPatientName());
        }

        JTextField txtCin = new JTextField(existingDossier != null ? existingDossier.getCin() : "");
        JComboBox<String> cmbMutualiste = new JComboBox<>(new String[]{"Non", "CNOPS", "CNSS", "MAMDA", "RAMIDE", "FAR"});
        if (existingDossier != null) {
            cmbMutualiste.setSelectedItem(existingDossier.getMutualiste());
        }

        JTextField txtDents = new JTextField(existingDossier != null ? existingDossier.getDents() : "");
        JTextField txtAmount = new JTextField(existingDossier != null ? String.valueOf(existingDossier.getAmount()) : "");

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Patient Name:"));
        panel.add(cmbPatientName);
        panel.add(new JLabel("CIN:"));
        panel.add(txtCin);
        panel.add(new JLabel("Mutualiste:"));
        panel.add(cmbMutualiste);
        panel.add(new JLabel("Dents:"));
        panel.add(txtDents);
        panel.add(new JLabel("Amount:"));
        panel.add(txtAmount);

        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String patientName = (String) cmbPatientName.getSelectedItem();
                String cin = txtCin.getText();
                String mutualiste = (String) cmbMutualiste.getSelectedItem();
                String dents = txtDents.getText();
                double amount = Double.parseDouble(txtAmount.getText());

                if (patientName == null || cin.isEmpty() || mutualiste.isEmpty() || dents.isEmpty()) {
                    throw new IllegalArgumentException("All fields are required.");
                }

                return new DossierMedicale(
                        existingDossier != null ? existingDossier.getId() : 0,
                        patientName, cin, mutualiste, dents, amount
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
