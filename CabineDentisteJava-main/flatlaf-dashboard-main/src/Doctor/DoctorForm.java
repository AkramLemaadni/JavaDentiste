package Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable doctorTable;
    private List<Doctor> doctors;
    private final String filePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\doctors.txt";

    public DoctorForm() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Doctors", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        setupTable();
        setupButtons();
        loadDoctorsFromFile();
    }

    private void setupTable() {
        String[] columns = {"ID", "Name", "Specialization", "Phone", "Email"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        doctorTable = new JTable(tableModel);
        doctorTable.setRowHeight(30);
        doctorTable.setFont(new Font("Arial", Font.PLAIN, 14));
        doctorTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        doctorTable.getTableHeader().setBackground(new Color(200, 200, 200));
        doctorTable.getTableHeader().setForeground(Color.BLACK);
        doctorTable.setSelectionBackground(new Color(173, 216, 230));
        doctorTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(doctorTable);
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

        addButton.addActionListener(e -> addDoctor());
        editButton.addActionListener(e -> editDoctor());
        deleteButton.addActionListener(e -> deleteDoctor());

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

    private void loadDoctorsFromFile() {
        doctors = Doctor.loadFromFile(filePath);
        tableModel.setRowCount(0); // Clear table
        for (Doctor doctor : doctors) {
            tableModel.addRow(new Object[]{
                    doctor.getId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getPhone(),
                    doctor.getEmail()
            });
        }
    }

    private void addDoctor() {
        Doctor newDoctor = showDoctorDialog(null, "Add Doctor");
        if (newDoctor != null) {
            newDoctor.setId(doctors.size() + 1);
            doctors.add(newDoctor);
            Doctor.saveAllToFile(filePath, doctors);
            loadDoctorsFromFile();
            JOptionPane.showMessageDialog(this, "Doctor added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editDoctor() {
        int selectedRow = doctorTable.getSelectedRow();
        if (selectedRow >= 0) {
            Doctor doctor = doctors.get(selectedRow);
            Doctor updatedDoctor = showDoctorDialog(doctor, "Edit Doctor");
            if (updatedDoctor != null) {
                doctors.set(selectedRow, updatedDoctor);
                Doctor.saveAllToFile(filePath, doctors);
                loadDoctorsFromFile();
                JOptionPane.showMessageDialog(this, "Doctor updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a doctor to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDoctor() {
        int selectedRow = doctorTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this doctor?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                doctors.remove(selectedRow);
                Doctor.saveAllToFile(filePath, doctors);
                loadDoctorsFromFile();
                JOptionPane.showMessageDialog(this, "Doctor deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a doctor to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Doctor showDoctorDialog(Doctor existingDoctor, String title) {
        JTextField txtName = new JTextField(existingDoctor != null ? existingDoctor.getName() : "");
        JTextField txtSpecialization = new JTextField(existingDoctor != null ? existingDoctor.getSpecialization() : "");
        JTextField txtPhone = new JTextField(existingDoctor != null ? existingDoctor.getPhone() : "");
        JTextField txtEmail = new JTextField(existingDoctor != null ? existingDoctor.getEmail() : "");

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Name:"));
        panel.add(txtName);
        panel.add(new JLabel("Specialization:"));
        panel.add(txtSpecialization);
        panel.add(new JLabel("Phone:"));
        panel.add(txtPhone);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);

        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = txtName.getText();
                String specialization = txtSpecialization.getText();
                String phone = txtPhone.getText();
                String email = txtEmail.getText();

                if (name.isEmpty() || specialization.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    throw new IllegalArgumentException("All fields are required.");
                }

                return new Doctor(
                        existingDoctor != null ? existingDoctor.getId() : 0,
                        name, specialization, phone, email
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
