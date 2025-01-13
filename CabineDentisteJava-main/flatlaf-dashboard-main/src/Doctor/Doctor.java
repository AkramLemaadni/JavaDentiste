package Doctor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private int id;
    private String name;
    private String specialization;
    private String phone;
    private String email;

    public Doctor(int id, String name, String specialization, String phone, String email) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Convert Doctor to a CSV-like string
    @Override
    public String toString() {
        return id + "," + name + "," + specialization + "," + phone + "," + email;
    }

    // Convert a CSV-like string to a Doctor object
    public static Doctor fromString(String line) {
        String[] parts = line.split(",");
        return new Doctor(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4]
        );
    }

    // Load all doctors from a file
    public static List<Doctor> loadFromFile(String filePath) {
        List<Doctor> doctors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                doctors.add(Doctor.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error while loading doctors: " + e.getMessage());
        }
        return doctors;
    }

    // Save all doctors to a file
    public static void saveAllToFile(String filePath, List<Doctor> doctors) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Doctor doctor : doctors) {
                writer.write(doctor.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving doctors: " + e.getMessage());
        }
    }
}
