package model;

public class Client {

    private int idClient;
    private String ville;
    private String codePostal;
    private double revenuMensuel;

    public Client() {}

    public Client(int idClient, String ville, String codePostal, double revenuMensuel) {
        this.idClient = idClient;
        this.ville = ville;
        this.codePostal = codePostal;
        this.revenuMensuel = revenuMensuel;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public double getRevenuMensuel() {
        return revenuMensuel;
    }

    public void setRevenuMensuel(double revenuMensuel) {
        this.revenuMensuel = revenuMensuel;
    }
}
