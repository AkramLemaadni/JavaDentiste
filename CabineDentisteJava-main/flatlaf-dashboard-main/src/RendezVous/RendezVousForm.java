package RendezVous;

import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class RendezVousForm extends JPanel {

    private DefaultTableModel tableModel;
    private JTable appointmentTable;
    private List<Appointment> appointments;
    private final String appointmentsFilePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\appointments.txt";
    private final String patientsFilePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\patients.txt";
    private final String doctorsFilePath = "C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\doctors.txt";
    private final Map<String, String> appointmentSchedule = new HashMap<>(); // Key: DateTime, Value: Patient Name

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
        appointmentSchedule.clear(); // Reset the schedule map

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
            String dateTime = appointment.getDate() + " " + appointment.getTime();
            appointmentSchedule.put(dateTime, appointment.getPatientName());
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

    private List<String> loadDoctorNames() {
        List<String> doctorNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(doctorsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    doctorNames.add(parts[1]); // Assuming the name is in the second column
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading doctors file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return doctorNames;
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

        List<String> doctorNames = loadDoctorNames();
        JComboBox<String> cmbDoctorName = new JComboBox<>(doctorNames.toArray(new String[0]));
        if (existingAppointment != null) {
            cmbDoctorName.setSelectedItem(existingAppointment.getDoctor());
        }

        // Configure date picker
        UtilDateModel dateModel = new UtilDateModel();
        Properties dateProperties = new Properties();
        dateProperties.put("text.today", "Today");
        dateProperties.put("text.month", "Month");
        dateProperties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, dateProperties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

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
        panel.add(cmbDoctorName);
        panel.add(new JLabel("Date:"));
        panel.add(datePicker);
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
                String doctorName = (String) cmbDoctorName.getSelectedItem();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(dateModel.getValue());
                String time = txtTime.getText();
                String reason = txtReason.getText();
                String status = (String) cmbStatus.getSelectedItem();

                if (patientName == null || doctorName == null || date.isEmpty() || time.isEmpty() || reason.isEmpty()) {
                    throw new IllegalArgumentException("All fields are required.");
                }

                String dateTime = date + " " + time;
                if (appointmentSchedule.containsKey(dateTime)) {
                    throw new IllegalArgumentException("This appointment time is already taken by: " + appointmentSchedule.get(dateTime));
                }

                appointmentSchedule.put(dateTime, patientName); // Add to the schedule

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

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) throws java.text.ParseException {
            if (value != null) {
                return dateFormatter.format(value);
            }
            return "";
        }
    }
}
