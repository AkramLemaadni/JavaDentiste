package Facture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FactureForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable factureTable;
    private List<Facture> factures;

    public FactureForm() {
        setLayout(new BorderLayout());

        // Add Title
        JLabel title = new JLabel("Invoice Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Initialize Table
        setupTable();

        // Initialize Buttons
        setupButtons();

        // Load Sample Data
        loadSampleData();
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

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(factureTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Initialize the list of invoices
        factures = new ArrayList<>();
    }

    private void setupButtons() {
        // Create Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // Create Buttons
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.setPreferredSize(new Dimension(100, 30));
        editButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setPreferredSize(new Dimension(100, 30));

        // Add Action Listeners
        addButton.addActionListener(e -> addFacture());
        editButton.addActionListener(e -> editFacture());
        deleteButton.addActionListener(e -> deleteFacture());

        // Add Buttons to Panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadSampleData() {
        // Add sample invoices
        factures.add(new Facture(1, "Alice Johnson", "01/01/2023", 150.0, "Teeth cleaning"));
        factures.add(new Facture(2, "Bob Smith", "02/02/2023", 200.0, "Root canal treatment"));

        // Populate the table
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
        // Show input dialogs to get invoice details
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

        // Add the new invoice to the list
        int newId = factures.size() + 1;
        Facture newFacture = new Facture(newId, patientName, date, amount, description);
        factures.add(newFacture);

        // Add the new invoice to the table
        tableModel.addRow(new Object[]{
                newFacture.getId(),
                newFacture.getPatientName(),
                newFacture.getDate(),
                newFacture.getAmount(),
                newFacture.getDescription()
        });

        JOptionPane.showMessageDialog(this, "Invoice added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editFacture() {
        // Get the selected row
        int selectedRow = factureTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an invoice to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the invoice to edit
        Facture facture = factures.get(selectedRow);

        // Show input dialogs with pre-filled values
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

        // Update the invoice
        facture.setPatientName(patientName);
        facture.setDate(date);
        facture.setAmount(amount);
        facture.setDescription(description);

        // Update the table
        tableModel.setValueAt(patientName, selectedRow, 1);
        tableModel.setValueAt(date, selectedRow, 2);
        tableModel.setValueAt(amount, selectedRow, 3);
        tableModel.setValueAt(description, selectedRow, 4);

        JOptionPane.showMessageDialog(this, "Invoice updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteFacture() {
        // Get the selected row
        int selectedRow = factureTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an invoice to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this invoice?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // Remove the invoice from the list and table
        factures.remove(selectedRow);
        tableModel.removeRow(selectedRow);

        JOptionPane.showMessageDialog(this, "Invoice deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
