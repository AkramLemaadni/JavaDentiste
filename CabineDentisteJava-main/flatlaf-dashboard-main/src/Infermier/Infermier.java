package Infermier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Infermier {
    private int id;
    private String nom;
    private String numeroTel;
    private String email;
    private String nomUtilisateur;
    private String motDePasse;

    public Infermier(int id, String nom, String numeroTel, String email, String nomUtilisateur, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.numeroTel = numeroTel;
        this.email = email;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getNumeroTel() { return numeroTel; }
    public void setNumeroTel(String numeroTel) { this.numeroTel = numeroTel; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNomUtilisateur() { return nomUtilisateur; }
    public void setNomUtilisateur(String nomUtilisateur) { this.nomUtilisateur = nomUtilisateur; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    // Convert Infermier to CSV-like string
    @Override
    public String toString() {
        return id + "," + nom + "," + numeroTel + "," + email + "," + nomUtilisateur + "," + motDePasse;
    }

    // Convert CSV-like string to Infermier object
    public static Infermier fromString(String line) {
        String[] parts = line.split(",");
        return new Infermier(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5]
        );
    }

    // Load all infermiers from a file
    public static List<Infermier> loadFromFile(String filePath) {
        List<Infermier> infermiers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                infermiers.add(Infermier.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error while loading infermiers: " + e.getMessage());
        }
        return infermiers;
    }

    // Save all infermiers to a file
    public static void saveAllToFile(String filePath, List<Infermier> infermiers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Infermier infermier : infermiers) {
                writer.write(infermier.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving infermiers: " + e.getMessage());
        }
    }
}
