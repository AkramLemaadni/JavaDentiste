package Facture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FactureForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable factureTable;
    private List<Facture> factures;
    private final String facturesFilePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\factures.txt";
    private final String patientsFilePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\patients.txt";

    public FactureForm() {
        setLayout(new BorderLayout());

        // Add Title
        JLabel title = new JLabel("Gestion des Factures", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        setupTable();
        setupButtons();
        loadFacturesFromFile();
    }

    private void setupTable() {
        String[] columns = {"ID", "Patient Name", "Date", "Amount", "Description", "Montant Payer", "Montant Rester"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        factureTable = new JTable(tableModel);
        factureTable.setRowHeight(30);
        factureTable.setFont(new Font("Arial", Font.PLAIN, 14));
        factureTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        factureTable.getTableHeader().setBackground(new Color(200, 200, 200));
        factureTable.getTableHeader().setForeground(Color.BLACK);
        factureTable.setSelectionBackground(new Color(173, 216, 230));
        factureTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(factureTable);
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

        addButton.addActionListener(e -> addFacture());
        editButton.addActionListener(e -> editFacture());
        deleteButton.addActionListener(e -> deleteFacture());

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

    private void loadFacturesFromFile() {
        factures = Facture.loadFromFile(facturesFilePath);
        tableModel.setRowCount(0);
        for (Facture facture : factures) {
            tableModel.addRow(new Object[]{
                    facture.getId(),
                    facture.getPatientName(),
                    facture.getDate(),
                    facture.getAmount(),
                    facture.getDescription(),
                    facture.getMontantPayer(),
                    facture.getMontantRester()
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

    private void addFacture() {
        Facture newFacture = showFactureDialog(null, "Add Facture");
        if (newFacture != null) {
            newFacture.setId(factures.size() + 1);

            // Calculate Montant Rester
            double montantRester = newFacture.getAmount() - newFacture.getMontantPayer();
            newFacture.setMontantRester(montantRester);

            factures.add(newFacture);
            Facture.saveAllToFile(facturesFilePath, factures);
            loadFacturesFromFile();
            JOptionPane.showMessageDialog(this, "Facture added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editFacture() {
        int selectedRow = factureTable.getSelectedRow();
        if (selectedRow >= 0) {
            Facture facture = factures.get(selectedRow);
            Facture updatedFacture = showFactureDialog(facture, "Edit Facture");
            if (updatedFacture != null) {

                // Update Montant Rester
                double montantRester = updatedFacture.getAmount() - updatedFacture.getMontantPayer();
                updatedFacture.setMontantRester(montantRester);

                factures.set(selectedRow, updatedFacture);
                Facture.saveAllToFile(facturesFilePath, factures);
                loadFacturesFromFile();
                JOptionPane.showMessageDialog(this, "Facture updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a facture to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteFacture() {
        int selectedRow = factureTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this facture?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                factures.remove(selectedRow);
                Facture.saveAllToFile(facturesFilePath, factures);
                loadFacturesFromFile();
                JOptionPane.showMessageDialog(this, "Facture deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a facture to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Facture showFactureDialog(Facture existingFacture, String title) {
        List<String> patientNames = loadPatientNames();
        JComboBox<String> cmbPatientName = new JComboBox<>(patientNames.toArray(new String[0]));
        if (existingFacture != null) {
            cmbPatientName.setSelectedItem(existingFacture.getPatientName());
        }

        JTextField txtDate = new JTextField(existingFacture != null ? existingFacture.getDate() : "");
        JTextField txtAmount = new JTextField(existingFacture != null ? String.valueOf(existingFacture.getAmount()) : "");
        JTextField txtMontantPayer = new JTextField(existingFacture != null ? String.valueOf(existingFacture.getMontantPayer()) : "");

        JComboBox<String> cmbDescription = new JComboBox<>(new String[]{"Payéé", "Non Payéé"});
        if (existingFacture != null) {
            cmbDescription.setSelectedItem(existingFacture.getDescription());
        }

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Patient Name:"));
        panel.add(cmbPatientName);
        panel.add(new JLabel("Date:"));
        panel.add(txtDate);
        panel.add(new JLabel("Amount:"));
        panel.add(txtAmount);
        panel.add(new JLabel("Montant Payer:"));
        panel.add(txtMontantPayer);
        panel.add(new JLabel("Description:"));
        panel.add(cmbDescription);

        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String patientName = (String) cmbPatientName.getSelectedItem();
                String date = txtDate.getText();
                double amount = Double.parseDouble(txtAmount.getText());
                double montantPayer = Double.parseDouble(txtMontantPayer.getText());
                String description = (String) cmbDescription.getSelectedItem();

                if (patientName == null || date.isEmpty() || description.isEmpty()) {
                    throw new IllegalArgumentException("All fields are required.");
                }

                return new Facture(
                        existingFacture != null ? existingFacture.getId() : 0,
                        patientName, date, amount, description, montantPayer, amount - montantPayer
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
