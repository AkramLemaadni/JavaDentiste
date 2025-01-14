package RendezVous;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private int id;
    private String patientName;
    private String doctor;
    private String date;
    private String time;
    private String reason;
    private String status;

    public Appointment(int id, String patientName, String doctor, String date, String time, String reason, String status) {
        this.id = id;
        this.patientName = patientName;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    // Convert Appointment to a CSV-like string
    @Override
    public String toString() {
        return id + "," + patientName + "," + doctor + "," + date + "," + time + "," + reason + "," + status;
    }

    // Convert a CSV-like string to an Appointment object
    public static Appointment fromString(String line) {
        String[] parts = line.split(",");
        return new Appointment(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                parts[6]
        );
    }

    // Load all appointments from a file
    public static List<Appointment> loadFromFile(String filePath) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                appointments.add(Appointment.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error while loading appointments: " + e.getMessage());
        }
        return appointments;
    }

    // Save all appointments to a file
    public static void saveAllToFile(String filePath, List<Appointment> appointments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Appointment appointment : appointments) {
                writer.write(appointment.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving appointments: " + e.getMessage());
        }
    }
}
