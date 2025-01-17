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
    private double montantPayer;
    private double montantRester;

    public Facture(int id, String patientName, String date, double amount, String description, double montantPayer, double montantRester) {
        this.id = id;
        this.patientName = patientName;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.montantPayer = montantPayer;
        this.montantRester = montantRester;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getMontantPayer() { return montantPayer; }
    public void setMontantPayer(double montantPayer) { this.montantPayer = montantPayer; }

    public double getMontantRester() { return montantRester; }
    public void setMontantRester(double montantRester) { this.montantRester = montantRester; }

    @Override
    public String toString() {
        return id + "," + patientName + "," + date + "," + amount + "," + description + "," + montantPayer + "," + montantRester;
    }

    public static Facture fromString(String line) {
        String[] parts = line.split(",");
        return new Facture(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                Double.parseDouble(parts[3]),
                parts[4],
                Double.parseDouble(parts[5]),
                Double.parseDouble(parts[6])
        );
    }

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
