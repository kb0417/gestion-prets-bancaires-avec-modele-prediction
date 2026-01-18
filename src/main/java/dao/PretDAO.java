package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Pret;
import util.DBConnection;

public class PretDAO {

    // 🔹 Récupérer tous les prêts
    public List<Pret> findAll() {
        List<Pret> prets = new ArrayList<>();
        String sql = "SELECT * FROM pret";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pret pret = mapResultSetToPret(rs);
                prets.add(pret);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prets;
    }

    // 🔹 Trouver un prêt par ID
    public Pret findById(int idPret) {
        Pret pret = null;
        String sql = "SELECT * FROM pret WHERE id_pret = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPret);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pret = mapResultSetToPret(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pret;
    }

    // 🔹 Récupérer les prêts d’un client
    public List<Pret> findByClient(int idClient) {
        List<Pret> prets = new ArrayList<>();
        String sql = "SELECT * FROM pret WHERE id_client = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prets.add(mapResultSetToPret(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prets;
    }

    // 🔹 Ajouter un prêt
    public void save(Pret pret) {
        String sql = """
            INSERT INTO pret (type_pret, montant_mensuel, duree, taux_interet, id_client)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pret.getTypePret());
            ps.setDouble(2, pret.getMontantMensuel());
            ps.setInt(3, pret.getDuree());
            ps.setDouble(4, pret.getTauxInteret());
            ps.setInt(5, pret.getIdClient());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Supprimer un prêt
    public void delete(int idPret) {
        String sql = "DELETE FROM pret WHERE id_pret = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPret);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Méthode utilitaire (mapping propre)
    private Pret mapResultSetToPret(ResultSet rs) throws SQLException {
        Pret pret = new Pret();
        pret.setIdPret(rs.getInt("id_pret"));
        pret.setTypePret(rs.getString("type_pret"));
        pret.setMontantMensuel(rs.getDouble("montant_mensuel"));
        pret.setDuree(rs.getInt("duree"));
        pret.setTauxInteret(rs.getDouble("taux_interet"));
        pret.setIdClient(rs.getInt("id_client"));
        return pret;
    }
    
    public void update(Pret pret) {
        String sql = "UPDATE pret SET type_pret=?, montant_mensuel=?, duree=?, taux_interet=?, id_client=? WHERE id_pret=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pret.getTypePret());
            ps.setDouble(2, pret.getMontantMensuel());
            ps.setInt(3, pret.getDuree());
            ps.setDouble(4, pret.getTauxInteret());
            ps.setInt(5, pret.getIdClient());
            ps.setInt(6, pret.getIdPret());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int count() {
        String sql = "SELECT COUNT(*) FROM pret";

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


    public double sumMontant() {
        String sql = "SELECT SUM(montant_mensuel) FROM pret";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    
    public List<Pret> search(String keyword) {
        List<Pret> prets = new ArrayList<>();
        String sql = """
            SELECT * FROM pret
            WHERE CAST(id_pret AS TEXT) ILIKE ?
               OR type_pret ILIKE ?
            ORDER BY id_pret DESC
        """;

        String k = "%" + keyword + "%";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k);
            ps.setString(2, k);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prets.add(mapResultSetToPret(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prets;
    }

    public void saveEvaluationRisque(int idPret, double probabilite, String interpretation) {

        String deleteSql = "DELETE FROM evaluation_risque WHERE id_pret = ?";
        String insertSql = """
            INSERT INTO evaluation_risque (id_pret, probabilite_risque, interpretation)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement psDel = conn.prepareStatement(deleteSql)) {
                psDel.setInt(1, idPret);
                psDel.executeUpdate();
            }

            try (PreparedStatement psIns = conn.prepareStatement(insertSql)) {
                psIns.setInt(1, idPret);
                psIns.setDouble(2, probabilite);
                psIns.setString(3, interpretation);
                psIns.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int countPretsRisque() {
        String sql = """
            SELECT COUNT(DISTINCT id_pret)
            FROM evaluation_risque
            WHERE LOWER(interpretation) = 'risque élevé'
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /***** Si un pret a déjà été évalué on ne l'évalue plus ***********/
    public boolean hasEvaluation(int idPret) {
        String sql = "SELECT 1 FROM evaluation_risque WHERE id_pret = ? LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPret);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Pret> findPretsRisque() {
        List<Pret> prets = new ArrayList<>();

        String sql = """
            SELECT p.*
            FROM pret p
            JOIN evaluation_risque e ON e.id_pret = p.id_pret
            WHERE LOWER(e.interpretation) = 'risque élevé'
            ORDER BY p.id_pret DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                prets.add(mapResultSetToPret(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prets;
    }

    public int countByClient(int idClient) {
        String sql = "SELECT COUNT(*) FROM pret WHERE id_client = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

   
}
