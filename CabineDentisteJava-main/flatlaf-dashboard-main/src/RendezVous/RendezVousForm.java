package RendezVous;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable appointmentTable;
    private List<Appointment> appointments;

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

        // Load Sample Data
        loadSampleData();
    }

    private void setupTable() {
        // Define table columns
        String[] columns = {"ID", "Patient Name", "Doctor", "Date", "Time", "Reason"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        // Create the table
        appointmentTable = new JTable(tableModel);
        appointmentTable.setRowHeight(30);
        appointmentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        appointmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        appointmentTable.getTableHeader().setBackground(new Color(200, 200, 200));
        appointmentTable.getTableHeader().setForeground(Color.BLACK);
        appointmentTable.setSelectionBackground(new Color(173, 216, 230));
        appointmentTable.setSelectionForeground(Color.BLACK);

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Initialize the list of appointments
        appointments = new ArrayList<>();
    }

    private void setupButtons() {
        // Create Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // Create Buttons
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        configureButtonStyle(addButton);
        configureButtonStyle(editButton);
        configureButtonStyle(deleteButton);

        // Add Action Listeners
        addButton.addActionListener(e -> addAppointment());
        editButton.addActionListener(e -> editAppointment());
        deleteButton.addActionListener(e -> deleteAppointment());

        // Add Buttons to Panel
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

    private void loadSampleData() {
        // Add sample appointments
        appointments.add(new Appointment(1, "Alice Johnson", "Dr. Smith", "01/01/2023", "10:00 AM", "Regular check-up"));
        appointments.add(new Appointment(2, "Bob Smith", "Dr. Johnson", "02/02/2023", "2:00 PM", "Toothache"));

        // Populate the table
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
        // Show input dialogs to get appointment details
        String patientName = JOptionPane.showInputDialog(this, "Enter Patient Name:");
        if (patientName == null || patientName.isEmpty()) return;

        String doctorName = JOptionPane.showInputDialog(this, "Enter Doctor Name:");
        String date = JOptionPane.showInputDialog(this, "Enter Appointment Date (DD/MM/YYYY):");
        String time = JOptionPane.showInputDialog(this, "Enter Appointment Time (HH:MM):");
        String reason = JOptionPane.showInputDialog(this, "Enter Reason:");

        // Add the new appointment to the list
        int newId = appointments.size() + 1;
        Appointment newAppointment = new Appointment(newId, patientName, doctorName, date, time, reason);
        appointments.add(newAppointment);

        // Add the new appointment to the table
        tableModel.addRow(new Object[]{
                newAppointment.getId(),
                newAppointment.getPatientName(),
                newAppointment.getDoctor(),
                newAppointment.getDate(),
                newAppointment.getTime(),
                newAppointment.getReason()
        });

        JOptionPane.showMessageDialog(this, "Appointment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editAppointment() {
        // Get the selected row
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the appointment to edit
        Appointment appointment = appointments.get(selectedRow);

        // Show input dialogs with pre-filled values
        String patientName = JOptionPane.showInputDialog(this, "Edit Patient Name:", appointment.getPatientName());
        if (patientName == null || patientName.isEmpty()) return;

        String doctorName = JOptionPane.showInputDialog(this, "Edit Doctor Name:", appointment.getDoctor());
        String date = JOptionPane.showInputDialog(this, "Edit Appointment Date (DD/MM/YYYY):", appointment.getDate());
        String time = JOptionPane.showInputDialog(this, "Edit Appointment Time (HH:MM):", appointment.getTime());
        String reason = JOptionPane.showInputDialog(this, "Edit Reason:", appointment.getReason());

        // Update the appointment
        appointment.setPatientName(patientName);
        appointment.setDoctor(doctorName);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setReason(reason);

        // Update the table
        tableModel.setValueAt(patientName, selectedRow, 1);
        tableModel.setValueAt(doctorName, selectedRow, 2);
        tableModel.setValueAt(date, selectedRow, 3);
        tableModel.setValueAt(time, selectedRow, 4);
        tableModel.setValueAt(reason, selectedRow, 5);

        JOptionPane.showMessageDialog(this, "Appointment updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteAppointment() {
        // Get the selected row
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this appointment?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // Remove the appointment from the list and table
        appointments.remove(selectedRow);
        tableModel.removeRow(selectedRow);

        JOptionPane.showMessageDialog(this, "Appointment deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
