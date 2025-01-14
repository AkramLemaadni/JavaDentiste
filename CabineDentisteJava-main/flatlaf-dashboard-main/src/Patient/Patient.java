package Patient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Patient {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String dateOfBirth; // Date of birth for the patient
    private String medicalHistory; // Medical history summary
    private String dateAdded; // Date added in YYYY-MM-DD format

    public Patient(int id, String name, String email, String phone, String address, String dateOfBirth, String medicalHistory, String dateAdded) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.medicalHistory = medicalHistory;
        this.dateAdded = dateAdded;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }
    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }

    // Convert Patient to a CSV-like string
    @Override
    public String toString() {
        return id + "," + name + "," + email + "," + phone + "," + address + "," + dateOfBirth + "," + medicalHistory + "," + dateAdded;
    }

    // Convert a CSV-like string to a Patient object
    public static Patient fromString(String line) {
        String[] parts = line.split(",");
        return new Patient(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                parts[6],
                parts[7]
        );
    }

    // Load all patients from a file
    public static List<Patient> loadFromFile(String filePath) {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                patients.add(Patient.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error while loading patients: " + e.getMessage());
        }
        return patients;
    }

    // Save all patients to a file
    public static void saveAllToFile(String filePath, List<Patient> patients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Patient patient : patients) {
                writer.write(patient.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving patients: " + e.getMessage());
        }
    }
}
