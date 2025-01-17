package DossierMedicale;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class DossierMedicale {
    private int id;
    private String patientName;
    private String cin;
    private String mutualiste;
    private String dents;
    private double amount;

    public DossierMedicale(int id, String patientName, String cin, String mutualiste, String dents, double amount) {
        this.id = id;
        this.patientName = patientName;
        this.cin = cin;
        this.mutualiste = mutualiste;
        this.dents = dents;
        this.amount = amount;
    }

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

    public String getCin() {
        return cin;
    }
    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getMutualiste() {
        return mutualiste;
    }

    public void setMutualiste(String mutualiste) {
        this.mutualiste = mutualiste;
    }

    public String getDents() {
        return dents;
    }

    public void setDents(String dents) {
        this.dents = dents;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public static List<DossierMedicale> loadFromFile(String filePath) {
        List<DossierMedicale> dossiers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    dossiers.add(new DossierMedicale(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4],
                            Double.parseDouble(parts[5])
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dossiers;
    }

    public static void saveAllToFile(String filePath, List<DossierMedicale> dossiers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (DossierMedicale dossier : dossiers) {
                writer.write(dossier.getId() + "," + dossier.getPatientName() + "," + dossier.getCin() + "," +
                        dossier.getMutualiste() + "," + dossier.getDents() + "," + dossier.getAmount());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}