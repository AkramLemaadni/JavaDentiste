package RendezVous;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RendezVousForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable appointmentTable;
    private List<Appointment> appointments;
    private final String filePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\appointments.txt";

    public RendezVousForm() {
        setLayout(new BorderLayout());

        // Add Title
        JLabel title = new JLabel("Rendez-Vous", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Initialize Table
        setupTable();

        // Initialize Buttons
        setupButtons();

        // Load Appointments from File
        loadAppointmentsFromFile();
    }

    private void setupTable() {
        String[] columns = {"ID", "Patient Name", "Doctor", "Date", "Time", "Reason"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        appointmentTable = new JTable(tableModel);
        appointmentTable.setRowHeight(30);
        appointmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        appointmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        appointmentTable.getTableHeader().setBackground(new Color(200, 200, 200));
        appointmentTable.getTableHeader().setForeground(Color.BLACK);
        appointmentTable.setSelectionBackground(new Color(173, 216, 230));
        appointmentTable.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(appointmentTable);
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

        addButton.addActionListener(e -> addAppointment());
        editButton.addActionListener(e -> editAppointment());
        deleteButton.addActionListener(e -> deleteAppointment());

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

    private void loadAppointmentsFromFile() {
        appointments = Appointment.loadFromFile(filePath);
        tableModel.setRowCount(0);
        for (Appointment appointment : appointments) {
            tableModel.addRow(new Object[]{
                    appointment.getId(),
                    appointment.getPatientName(),
                    appointment.getDoctor(),
                    appointment.getDate(),
                    appointment.getTime(),
                    appointment.getReason()
            });
        }
    }

    private void addAppointment() {
        String patientName = JOptionPane.showInputDialog(this, "Enter Patient Name:");
        if (patientName == null || patientName.isEmpty()) return;

        String doctorName = JOptionPane.showInputDialog(this, "Enter Doctor Name:");
        String date = JOptionPane.showInputDialog(this, "Enter Appointment Date (DD/MM/YYYY):");
        String time = JOptionPane.showInputDialog(this, "Enter Appointment Time (HH:MM):");
        String reason = JOptionPane.showInputDialog(this, "Enter Reason:");

        int newId = appointments.size() + 1;
        Appointment newAppointment = new Appointment(newId, patientName, doctorName, date, time, reason);
        appointments.add(newAppointment);

        Appointment.saveAllToFile(filePath, appointments);
        loadAppointmentsFromFile();

        JOptionPane.showMessageDialog(this, "Appointment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Appointment appointment = appointments.get(selectedRow);

        String patientName = JOptionPane.showInputDialog(this, "Edit Patient Name:", appointment.getPatientName());
        if (patientName == null || patientName.isEmpty()) return;

        String doctorName = JOptionPane.showInputDialog(this, "Edit Doctor Name:", appointment.getDoctor());
        String date = JOptionPane.showInputDialog(this, "Edit Appointment Date (DD/MM/YYYY):", appointment.getDate());
        String time = JOptionPane.showInputDialog(this, "Edit Appointment Time (HH:MM):", appointment.getTime());
        String reason = JOptionPane.showInputDialog(this, "Edit Reason:", appointment.getReason());

        appointment.setPatientName(patientName);
        appointment.setDoctor(doctorName);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setReason(reason);

        Appointment.saveAllToFile(filePath, appointments);
        loadAppointmentsFromFile();

        JOptionPane.showMessageDialog(this, "Appointment updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        appointments.remove(selectedRow);

        Appointment.saveAllToFile(filePath, appointments);
        loadAppointmentsFromFile();

        JOptionPane.showMessageDialog(this, "Appointment deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
