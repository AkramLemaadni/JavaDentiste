package Facture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FactureForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable factureTable;
    private List<Facture> factures;
    private final String filePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\factures.txt";

    public FactureForm() {
        setLayout(new BorderLayout());

        // Add Title
        JLabel title = new JLabel("Factures", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Initialize Table
        setupTable();

        // Initialize Buttons
        setupButtons();

        // Load Invoices from File
        loadFacturesFromFile();
    }

    private void setupTable() {
        // Define table columns
        String[] columns = {"ID", "Patient Name", "Date", "Amount", "Description"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        // Create the table
        factureTable = new JTable(tableModel);
        factureTable.setRowHeight(30);
        factureTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        factureTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        factureTable.getTableHeader().setBackground(new Color(200, 200, 200));
        factureTable.getTableHeader().setForeground(Color.BLACK);
        factureTable.setSelectionBackground(new Color(173, 216, 230));
        factureTable.setSelectionForeground(Color.BLACK);

        // Add table to a scroll pane
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
        factures = Facture.loadFromFile(filePath);
        tableModel.setRowCount(0);
        for (Facture facture : factures) {
            tableModel.addRow(new Object[]{
                    facture.getId(),
                    facture.getPatientName(),
                    facture.getDate(),
                    facture.getAmount(),
                    facture.getDescription()
            });
        }
    }

    private void addFacture() {
        String patientName = JOptionPane.showInputDialog(this, "Enter Patient Name:");
        if (patientName == null || patientName.isEmpty()) return;

        String date = JOptionPane.showInputDialog(this, "Enter Date (DD/MM/YYYY):");
        String amountStr = JOptionPane.showInputDialog(this, "Enter Amount:");
        String description = JOptionPane.showInputDialog(this, "Enter Description:");

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int newId = factures.size() + 1;
        Facture newFacture = new Facture(newId, patientName, date, amount, description);
        factures.add(newFacture);

        Facture.saveAllToFile(filePath, factures);
        loadFacturesFromFile();

        JOptionPane.showMessageDialog(this, "Invoice added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editFacture() {
        int selectedRow = factureTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an invoice to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Facture facture = factures.get(selectedRow);

        String patientName = JOptionPane.showInputDialog(this, "Edit Patient Name:", facture.getPatientName());
        if (patientName == null || patientName.isEmpty()) return;

        String date = JOptionPane.showInputDialog(this, "Edit Date (DD/MM/YYYY):", facture.getDate());
        String amountStr = JOptionPane.showInputDialog(this, "Edit Amount:", facture.getAmount());
        String description = JOptionPane.showInputDialog(this, "Edit Description:", facture.getDescription());

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        facture.setPatientName(patientName);
        facture.setDate(date);
        facture.setAmount(amount);
        facture.setDescription(description);

        Facture.saveAllToFile(filePath, factures);
        loadFacturesFromFile();

        JOptionPane.showMessageDialog(this, "Invoice updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteFacture() {
        int selectedRow = factureTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an invoice to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        factures.remove(selectedRow);

        Facture.saveAllToFile(filePath, factures);
        loadFacturesFromFile();

        JOptionPane.showMessageDialog(this, "Invoice deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
