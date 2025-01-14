package Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PatientForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable patientTable;
    private List<Patient> patients;
    private final String filePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\patients.txt";

    public PatientForm() {
        setLayout(new BorderLayout());

        // Add Title
        JLabel title = new JLabel("Gestion des Patients", JLabel.CENTER);
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
        String[] columns = {"ID", "Name", "Email", "Phone", "Address", "Date of Birth", "Medical History", "Date Added"};
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
                    patient.getMedicalHistory(),
                    patient.getDateAdded()
            });
        }
    }

    private void addPatient() {
        Patient newPatient = showPatientDialog(null, "Add Patient");
        if (newPatient != null) {
            newPatient.setId(patients.size() + 1);
            newPatient.setDateAdded(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // Set today's date
            patients.add(newPatient);
            Patient.saveAllToFile(filePath, patients);
            loadPatientsFromFile();
            JOptionPane.showMessageDialog(this, "Patient added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow >= 0) {
            Patient patient = patients.get(selectedRow);
            Patient updatedPatient = showPatientDialog(patient, "Edit Patient");
            if (updatedPatient != null) {
                updatedPatient.setDateAdded(patient.getDateAdded()); // Keep the original dateAdded
                patients.set(selectedRow, updatedPatient);
                Patient.saveAllToFile(filePath, patients);
                loadPatientsFromFile();
                JOptionPane.showMessageDialog(this, "Patient updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a patient to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this patient?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                patients.remove(selectedRow);
                Patient.saveAllToFile(filePath, patients);
                loadPatientsFromFile();
                JOptionPane.showMessageDialog(this, "Patient deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a patient to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Patient showPatientDialog(Patient existingPatient, String title) {
        JTextField txtName = new JTextField(existingPatient != null ? existingPatient.getName() : "");
        JTextField txtEmail = new JTextField(existingPatient != null ? existingPatient.getEmail() : "");
        JTextField txtPhone = new JTextField(existingPatient != null ? existingPatient.getPhone() : "");
        JTextField txtAddress = new JTextField(existingPatient != null ? existingPatient.getAddress() : "");
        JTextField txtDob = new JTextField(existingPatient != null ? existingPatient.getDateOfBirth() : "");
        JTextField txtHistory = new JTextField(existingPatient != null ? existingPatient.getMedicalHistory() : "");

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.add(new JLabel("Name:"));
        panel.add(txtName);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Phone:"));
        panel.add(txtPhone);
        panel.add(new JLabel("Address:"));
        panel.add(txtAddress);
        panel.add(new JLabel("Date of Birth:"));
        panel.add(txtDob);
        panel.add(new JLabel("Medical History:"));
        panel.add(txtHistory);

        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = txtName.getText();
                String email = txtEmail.getText();
                String phone = txtPhone.getText();
                String address = txtAddress.getText();
                String dob = txtDob.getText();
                String history = txtHistory.getText();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || dob.isEmpty() || history.isEmpty()) {
                    throw new IllegalArgumentException("All fields are required.");
                }

                return new Patient(
                        existingPatient != null ? existingPatient.getId() : 0,
                        name, email, phone, address, dob, history, existingPatient != null ? existingPatient.getDateAdded() : ""
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
