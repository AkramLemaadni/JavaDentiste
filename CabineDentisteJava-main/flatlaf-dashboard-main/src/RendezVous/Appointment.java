package RendezVous;

public class Appointment {
    private int id;
    private String patientName;
    private String doctor;
    private String date;
    private String time;
    private String reason;

    public Appointment(int id, String patientName, String doctor, String date, String time, String reason) {
        this.id = id;
        this.patientName = patientName;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.reason = reason;
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
}
