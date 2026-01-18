package util;

import dao.PretDAO;
import model.Pret;

public class TestPretDAO {

    public static void main(String[] args) {
        PretDAO dao = new PretDAO();

        for (Pret p : dao.findAll()) {
            System.out.println(
                p.getIdPret() + " | " +
                p.getTypePret() + " | " +
                p.getMontantMensuel()
            );
        }
    }
}
