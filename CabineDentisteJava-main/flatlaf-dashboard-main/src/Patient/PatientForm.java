package Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PatientForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable patientTable;
    private List<Patient> patients;

    public PatientForm() {
        setLayout(new BorderLayout());

        // Add Title
        JLabel title = new JLabel("Patient Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Initialize the table
        setupTable();

        // Initialize buttons
        setupButtons();

        // Load initial sample data
        loadSampleData();
    }

    private void setupTable() {
        // Table columns
        String[] columns = {"ID", "Name", "Email", "Phone", "Address", "Date of Birth", "Medical History"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        // Create JTable
        patientTable = new JTable(tableModel);
        patientTable.setRowHeight(30);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(patientTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Initialize patient list
        patients = new ArrayList<>();
    }

    private void setupButtons() {
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // Buttons
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.setPreferredSize(new Dimension(100, 30));
        editButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setPreferredSize(new Dimension(100, 30));

        // Add Action Listeners
        addButton.addActionListener(e -> addPatient());
        editButton.addActionListener(e -> editPatient());
        deleteButton.addActionListener(e -> deletePatient());

        // Add buttons to the panel
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Add panel to the bottom of the form
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadSampleData() {
        // Add sample data
        patients.add(new Patient(1, "Alice", "alice@example.com", "123456789", "123 Street", "01/01/1990", "No medical history"));
        patients.add(new Patient(2, "Bob", "bob@example.com", "987654321", "456 Avenue", "02/02/1980", "Diabetic"));

        // Populate table
        for (Patient patient : patients) {
            tableModel.addRow(new Object[]{
                    patient.getId(),
                    patient.getName(),
                    patient.getEmail(),
                    patient.getPhone(),
                    patient.getAddress(),
                    patient.getDateOfBirth(),
                    patient.getMedicalHistory()
            });
        }
    }

    private void addPatient() {
        // Show input dialog for new patient details
        String name = JOptionPane.showInputDialog(this, "Enter Name:");
        if (name == null || name.isEmpty()) return;

        String email = JOptionPane.showInputDialog(this, "Enter Email:");
        String phone = JOptionPane.showInputDialog(this, "Enter Phone:");
        String address = JOptionPane.showInputDialog(this, "Enter Address:");
        String dob = JOptionPane.showInputDialog(this, "Enter Date of Birth (DD/MM/YYYY):");
        String history = JOptionPane.showInputDialog(this, "Enter Medical History:");

        // Add to list
        int newId = patients.size() + 1;
        Patient newPatient = new Patient(newId, name, email, phone, address, dob, history);
        patients.add(newPatient);

        // Add to table
        tableModel.addRow(new Object[]{
                newPatient.getId(),
                newPatient.getName(),
                newPatient.getEmail(),
                newPatient.getPhone(),
                newPatient.getAddress(),
                newPatient.getDateOfBirth(),
                newPatient.getMedicalHistory()
        });

        JOptionPane.showMessageDialog(this, "Patient added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editPatient() {
        // Get selected row
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the patient to edit
        Patient patient = patients.get(selectedRow);

        // Show input dialogs with pre-filled values
        String name = JOptionPane.showInputDialog(this, "Edit Name:", patient.getName());
        if (name == null || name.isEmpty()) return;

        String email = JOptionPane.showInputDialog(this, "Edit Email:", patient.getEmail());
        String phone = JOptionPane.showInputDialog(this, "Edit Phone:", patient.getPhone());
        String address = JOptionPane.showInputDialog(this, "Edit Address:", patient.getAddress());
        String dob = JOptionPane.showInputDialog(this, "Edit Date of Birth (DD/MM/YYYY):", patient.getDateOfBirth());
        String history = JOptionPane.showInputDialog(this, "Edit Medical History:", patient.getMedicalHistory());

        // Update patient details
        patient.setName(name);
        patient.setEmail(email);
        patient.setPhone(phone);
        patient.setAddress(address);
        patient.setDateOfBirth(dob);
        patient.setMedicalHistory(history);

        // Update table
        tableModel.setValueAt(name, selectedRow, 1);
        tableModel.setValueAt(email, selectedRow, 2);
        tableModel.setValueAt(phone, selectedRow, 3);
        tableModel.setValueAt(address, selectedRow, 4);
        tableModel.setValueAt(dob, selectedRow, 5);
        tableModel.setValueAt(history, selectedRow, 6);

        JOptionPane.showMessageDialog(this, "Patient updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deletePatient() {
        // Get selected row
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirm delete
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // Remove from list and table
        patients.remove(selectedRow);
        tableModel.removeRow(selectedRow);

        JOptionPane.showMessageDialog(this, "Patient deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
