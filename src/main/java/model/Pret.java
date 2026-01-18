package model;

public class Pret {

    private int idPret;
    private String typePret;          // immobilier / automobile
    private double montantMensuel;
    private int duree;
    private double tauxInteret;
    private int idClient;

    public Pret() {}

    public Pret(int idPret, String typePret, double montantMensuel,
                int duree, double tauxInteret, int idClient) {
        this.idPret = idPret;
        this.typePret = typePret;
        this.montantMensuel = montantMensuel;
        this.duree = duree;
        this.tauxInteret = tauxInteret;
        this.idClient = idClient;
    }

    public int getIdPret() {
        return idPret;
    }

    public void setIdPret(int idPret) {
        this.idPret = idPret;
    }

    public String getTypePret() {
        return typePret;
    }

    public void setTypePret(String typePret) {
        this.typePret = typePret;
    }

    public double getMontantMensuel() {
        return montantMensuel;
    }

    public void setMontantMensuel(double montantMensuel) {
        this.montantMensuel = montantMensuel;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}
