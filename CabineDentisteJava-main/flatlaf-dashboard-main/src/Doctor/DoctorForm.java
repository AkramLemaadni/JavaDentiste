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

        JLabel title = new JLabel("Docteur", JLabel.CENTER);
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
        String name = JOptionPane.showInputDialog(this, "Enter Doctor's Name:");
        if (name == null || name.isEmpty()) return;

        String specialization = JOptionPane.showInputDialog(this, "Enter Specialization:");
        String phone = JOptionPane.showInputDialog(this, "Enter Phone Number:");
        String email = JOptionPane.showInputDialog(this, "Enter Email Address:");

        int newId = doctors.size() + 1;
        Doctor newDoctor = new Doctor(newId, name, specialization, phone, email);
        doctors.add(newDoctor);

        Doctor.saveAllToFile(filePath, doctors);
        loadDoctorsFromFile();
        JOptionPane.showMessageDialog(this, "Doctor added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editDoctor() {
        int selectedRow = doctorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Doctor doctor = doctors.get(selectedRow);

        String name = JOptionPane.showInputDialog(this, "Edit Doctor's Name:", doctor.getName());
        String specialization = JOptionPane.showInputDialog(this, "Edit Specialization:", doctor.getSpecialization());
        String phone = JOptionPane.showInputDialog(this, "Edit Phone Number:", doctor.getPhone());
        String email = JOptionPane.showInputDialog(this, "Edit Email Address:", doctor.getEmail());

        doctor.setName(name);
        doctor.setSpecialization(specialization);
        doctor.setPhone(phone);
        doctor.setEmail(email);

        Doctor.saveAllToFile(filePath, doctors);
        loadDoctorsFromFile();
        JOptionPane.showMessageDialog(this, "Doctor updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteDoctor() {
        int selectedRow = doctorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        doctors.remove(selectedRow);
        Doctor.saveAllToFile(filePath, doctors);
        loadDoctorsFromFile();
        JOptionPane.showMessageDialog(this, "Doctor deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
