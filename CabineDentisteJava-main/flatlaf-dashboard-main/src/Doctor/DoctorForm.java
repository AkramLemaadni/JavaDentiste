package Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable doctorTable;
    private List<Doctor> doctors;

    public DoctorForm() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Doctor Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        setupTable();
        setupButtons();
        loadSampleData();
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
        doctorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        doctors = new ArrayList<>();
    }

    private void setupButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(e -> addDoctor());
        editButton.addActionListener(e -> editDoctor());
        deleteButton.addActionListener(e -> deleteDoctor());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadSampleData() {
        doctors.add(new Doctor(1, "Dr. Smith", "Dentist", "123456789", "smith@example.com"));
        doctors.add(new Doctor(2, "Dr. Johnson", "Orthodontist", "987654321", "johnson@example.com"));

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

        tableModel.addRow(new Object[]{
                newDoctor.getId(),
                newDoctor.getName(),
                newDoctor.getSpecialization(),
                newDoctor.getPhone(),
                newDoctor.getEmail()
        });

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
        if (name == null || name.isEmpty()) return;

        String specialization = JOptionPane.showInputDialog(this, "Edit Specialization:", doctor.getSpecialization());
        String phone = JOptionPane.showInputDialog(this, "Edit Phone Number:", doctor.getPhone());
        String email = JOptionPane.showInputDialog(this, "Edit Email Address:", doctor.getEmail());

        doctor.setName(name);
        doctor.setSpecialization(specialization);
        doctor.setPhone(phone);
        doctor.setEmail(email);

        tableModel.setValueAt(name, selectedRow, 1);
        tableModel.setValueAt(specialization, selectedRow, 2);
        tableModel.setValueAt(phone, selectedRow, 3);
        tableModel.setValueAt(email, selectedRow, 4);

        JOptionPane.showMessageDialog(this, "Doctor updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteDoctor() {
        int selectedRow = doctorTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a doctor to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this doctor?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        doctors.remove(selectedRow);
        tableModel.removeRow(selectedRow);

        JOptionPane.showMessageDialog(this, "Doctor deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
