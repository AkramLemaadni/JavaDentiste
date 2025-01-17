package RendezVous;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable appointmentTable;
    private List<Appointment> appointments;
    private final String appointmentsFilePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\appointments.txt";
    private final String patientsFilePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\patients.txt";

    public RendezVousForm() {
        setLayout(new BorderLayout());

        // Add Title
        JLabel title = new JLabel("Gestion des Rendez-Vous", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        setupTable();
        setupButtons();
        loadAppointmentsFromFile();
    }

    private void setupTable() {
        String[] columns = {"ID", "Patient Name", "Doctor", "Date", "Time", "Reason", "Status"};
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
        appointments = Appointment.loadFromFile(appointmentsFilePath);
        tableModel.setRowCount(0);
        for (Appointment appointment : appointments) {
            tableModel.addRow(new Object[]{
                    appointment.getId(),
                    appointment.getPatientName(),
                    appointment.getDoctor(),
                    appointment.getDate(),
                    appointment.getTime(),
                    appointment.getReason(),
                    appointment.getStatus()
            });
        }
    }

    private List<String> loadPatientNames() {
        List<String> patientNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(patientsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    patientNames.add(parts[1]); // Assuming the name is in the second column
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading patients file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return patientNames;
    }

    private void addAppointment() {
        Appointment newAppointment = showAppointmentDialog(null, "Add Appointment");
        if (newAppointment != null) {
            newAppointment.setId(appointments.size() + 1);
            appointments.add(newAppointment);
            Appointment.saveAllToFile(appointmentsFilePath, appointments);
            loadAppointmentsFromFile();
            JOptionPane.showMessageDialog(this, "Appointment added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            Appointment appointment = appointments.get(selectedRow);
            Appointment updatedAppointment = showAppointmentDialog(appointment, "Edit Appointment");
            if (updatedAppointment != null) {
                appointments.set(selectedRow, updatedAppointment);
                Appointment.saveAllToFile(appointmentsFilePath, appointments);
                loadAppointmentsFromFile();
                JOptionPane.showMessageDialog(this, "Appointment updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select an appointment to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                appointments.remove(selectedRow);
                Appointment.saveAllToFile(appointmentsFilePath, appointments);
                loadAppointmentsFromFile();
                JOptionPane.showMessageDialog(this, "Appointment deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select an appointment to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Appointment showAppointmentDialog(Appointment existingAppointment, String title) {
        List<String> patientNames = loadPatientNames();
        JComboBox<String> cmbPatientName = new JComboBox<>(patientNames.toArray(new String[0]));
        if (existingAppointment != null) {
            cmbPatientName.setSelectedItem(existingAppointment.getPatientName());
        }

        JTextField txtDoctorName = new JTextField(existingAppointment != null ? existingAppointment.getDoctor() : "");
        JTextField txtDate = new JTextField(existingAppointment != null ? existingAppointment.getDate() : "");
        JTextField txtTime = new JTextField(existingAppointment != null ? existingAppointment.getTime() : "");
        JTextField txtReason = new JTextField(existingAppointment != null ? existingAppointment.getReason() : "");
        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Complété", "En attente", "Annulé"});
        if (existingAppointment != null) {
            cmbStatus.setSelectedItem(existingAppointment.getStatus());
        }

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.add(new JLabel("Patient Name:"));
        panel.add(cmbPatientName);
        panel.add(new JLabel("Doctor:"));
        panel.add(txtDoctorName);
        panel.add(new JLabel("Date:"));
        panel.add(txtDate);
        panel.add(new JLabel("Time:"));
        panel.add(txtTime);
        panel.add(new JLabel("Reason:"));
        panel.add(txtReason);
        panel.add(new JLabel("Status:"));
        panel.add(cmbStatus);

        int result = JOptionPane.showConfirmDialog(this, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String patientName = (String) cmbPatientName.getSelectedItem();
                String doctorName = txtDoctorName.getText();
                String date = txtDate.getText();
                String time = txtTime.getText();
                String reason = txtReason.getText();
                String status = (String) cmbStatus.getSelectedItem();

                if (patientName == null || doctorName.isEmpty() || date.isEmpty() || time.isEmpty() || reason.isEmpty()) {
                    throw new IllegalArgumentException("All fields are required.");
                }

                return new Appointment(
                        existingAppointment != null ? existingAppointment.getId() : 0,
                        patientName, doctorName, date, time, reason, status
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}