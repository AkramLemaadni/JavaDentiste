package Facture;

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
}
