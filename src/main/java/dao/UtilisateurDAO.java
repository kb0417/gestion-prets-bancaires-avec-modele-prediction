package dao;

import model.Utilisateur;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {

    // Pour l'authentification
    public Utilisateur findByLoginAndPassword(String login, String motDePasse) {

        Utilisateur utilisateur = null;

        String sql = """
            SELECT * FROM utilisateur
            WHERE login = ? AND mot_de_passe = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, motDePasse);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setIdUtilisateur(rs.getInt("id_utilisateur"));
                utilisateur.setLogin(rs.getString("login"));
                utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
                utilisateur.setRole(rs.getString("role"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    // 🔹 LISTE DES UTILISATEURS
    public List<Utilisateur> findAll() {
        List<Utilisateur> list = new ArrayList<>();

        String sql = "SELECT * FROM utilisateur";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setIdUtilisateur(rs.getInt("id_utilisateur"));
                u.setLogin(rs.getString("login"));
                u.setMotDePasse(rs.getString("mot_de_passe"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 TROUVER PAR ID
    public Utilisateur findById(int id) {
        Utilisateur u = null;

        String sql = "SELECT * FROM utilisateur WHERE id_utilisateur = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Utilisateur();
                u.setIdUtilisateur(rs.getInt("id_utilisateur"));
                u.setLogin(rs.getString("login"));
                u.setMotDePasse(rs.getString("mot_de_passe"));
                u.setRole(rs.getString("role"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return u;
    }

    // 🔹 AJOUTER UTILISATEUR
    public void save(Utilisateur u) {

        String sql = """
            INSERT INTO utilisateur (id_utilisateur, login, mot_de_passe, role)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, u.getIdUtilisateur());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getMotDePasse());
            ps.setString(4, u.getRole());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 MODIFIER UTILISATEUR
    public void update(Utilisateur u) {

        String sql = """
            UPDATE utilisateur
            SET login = ?, mot_de_passe = ?, role = ?
            WHERE id_utilisateur = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getLogin());
            ps.setString(2, u.getMotDePasse());
            ps.setString(3, u.getRole());
            ps.setInt(4, u.getIdUtilisateur());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 SUPPRIMER UTILISATEUR
    public void delete(int id) {

        String sql = "DELETE FROM utilisateur WHERE id_utilisateur = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
