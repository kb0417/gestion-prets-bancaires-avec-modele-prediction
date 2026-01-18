package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Client;
import util.DBConnection;

public class ClientDAO {

    // 🔹 Récupérer tous les clients
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setVille(rs.getString("ville"));
                client.setCodePostal(rs.getString("code_postal"));
                client.setRevenuMensuel(rs.getDouble("revenu_mensuel"));

                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    // 🔹 Trouver un client par ID
    public Client findById(int idClient) {
        Client client = null;
        String sql = "SELECT * FROM client WHERE id_client = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setVille(rs.getString("ville"));
                client.setCodePostal(rs.getString("code_postal"));
                client.setRevenuMensuel(rs.getDouble("revenu_mensuel"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }

    // 🔹 Ajouter un client
    public void save(Client client) {
        String sql = """
            INSERT INTO client (id_client, ville, code_postal, revenu_mensuel)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, client.getIdClient());
            ps.setString(2, client.getVille());
            ps.setString(3, client.getCodePostal());
            ps.setDouble(4, client.getRevenuMensuel());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Mettre à jour un client
    public void update(Client client) {
        String sql = """
            UPDATE client
            SET ville = ?, code_postal = ?, revenu_mensuel = ?
            WHERE id_client = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getVille());
            ps.setString(2, client.getCodePostal());
            ps.setDouble(3, client.getRevenuMensuel());
            ps.setInt(4, client.getIdClient());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Supprimer un client
    public void delete(int idClient) {
        String sql = "DELETE FROM client WHERE id_client = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int count() {
        String sql = "SELECT COUNT(*) FROM client";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<Client> search(String keyword) {
        List<Client> clients = new ArrayList<>();
        String sql = """
            SELECT * FROM client
            WHERE CAST(id_client AS TEXT) ILIKE ?
               OR ville ILIKE ?
               OR code_postal ILIKE ?
            ORDER BY id_client
        """;

        String k = "%" + keyword + "%";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Client c = new Client();
                c.setIdClient(rs.getInt("id_client"));
                c.setVille(rs.getString("ville"));
                c.setCodePostal(rs.getString("code_postal"));
                c.setRevenuMensuel(rs.getDouble("revenu_mensuel"));
                clients.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }
}
