package Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable patientTable;
    private List<Patient> patients;
    private final String filePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\patients.txt";

    public PatientForm() {
        setLayout(new BorderLayout());

        // Add Title
        JLabel title = new JLabel("Patients", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Initialize the table
        setupTable();

        // Initialize buttons
        setupButtons();

        // Load patients from the file
        loadPatientsFromFile();
    }

    private void setupTable() {
        String[] columns = {"ID", "Name", "Email", "Phone", "Address", "Date of Birth", "Medical History"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        patientTable = new JTable(tableModel);
        patientTable.setRowHeight(30);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 14));
        patientTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        patientTable.getTableHeader().setBackground(new Color(200, 200, 200));
        patientTable.getTableHeader().setForeground(Color.BLACK);
        patientTable.setSelectionBackground(new Color(173, 216, 230));
        patientTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(patientTable);
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

        addButton.addActionListener(e -> addPatient());
        editButton.addActionListener(e -> editPatient());
        deleteButton.addActionListener(e -> deletePatient());

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

    private void loadPatientsFromFile() {
        patients = Patient.loadFromFile(filePath);
        tableModel.setRowCount(0);
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
        String name = JOptionPane.showInputDialog(this, "Enter Name:");
        if (name == null || name.isEmpty()) return;

        String email = JOptionPane.showInputDialog(this, "Enter Email:");
        String phone = JOptionPane.showInputDialog(this, "Enter Phone:");
        String address = JOptionPane.showInputDialog(this, "Enter Address:");
        String dob = JOptionPane.showInputDialog(this, "Enter Date of Birth (DD/MM/YYYY):");
        String history = JOptionPane.showInputDialog(this, "Enter Medical History:");

        int newId = patients.size() + 1;
        Patient newPatient = new Patient(newId, name, email, phone, address, dob, history);
        patients.add(newPatient);

        Patient.saveAllToFile(filePath, patients);
        loadPatientsFromFile();

        JOptionPane.showMessageDialog(this, "Patient added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Patient patient = patients.get(selectedRow);

        String name = JOptionPane.showInputDialog(this, "Edit Name:", patient.getName());
        if (name == null || name.isEmpty()) return;

        String email = JOptionPane.showInputDialog(this, "Edit Email:", patient.getEmail());
        String phone = JOptionPane.showInputDialog(this, "Edit Phone:", patient.getPhone());
        String address = JOptionPane.showInputDialog(this, "Edit Address:", patient.getAddress());
        String dob = JOptionPane.showInputDialog(this, "Edit Date of Birth (DD/MM/YYYY):", patient.getDateOfBirth());
        String history = JOptionPane.showInputDialog(this, "Edit Medical History:", patient.getMedicalHistory());

        patient.setName(name);
        patient.setEmail(email);
        patient.setPhone(phone);
        patient.setAddress(address);
        patient.setDateOfBirth(dob);
        patient.setMedicalHistory(history);

        Patient.saveAllToFile(filePath, patients);
        loadPatientsFromFile();

        JOptionPane.showMessageDialog(this, "Patient updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deletePatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        patients.remove(selectedRow);

        Patient.saveAllToFile(filePath, patients);
        loadPatientsFromFile();

        JOptionPane.showMessageDialog(this, "Patient deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
