package Facture;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Facture {
    private int id;
    private String patientName;
    private String date;
    private double amount;
    private String description;

    public Facture(int id, String patientName, String date, double amount, String description) {
        this.id = id;
        this.patientName = patientName;
        this.date = date;
        this.amount = amount;
        this.description = description;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Convert Facture to a CSV-like string
    @Override
    public String toString() {
        return id + "," + patientName + "," + date + "," + amount + "," + description;
    }

    // Convert a CSV-like string to a Facture object
    public static Facture fromString(String line) {
        String[] parts = line.split(",");
        return new Facture(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                Double.parseDouble(parts[3]),
                parts[4]
        );
    }

    // Load all invoices from a file
    public static List<Facture> loadFromFile(String filePath) {
        List<Facture> factures = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                factures.add(Facture.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error while loading invoices: " + e.getMessage());
        }
        return factures;
    }

    // Save all invoices to a file
    public static void saveAllToFile(String filePath, List<Facture> factures) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Facture facture : factures) {
                writer.write(facture.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving invoices: " + e.getMessage());
        }
    }
}
