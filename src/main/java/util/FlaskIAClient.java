package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class FlaskIAClient {

    private static final String FLASK_URL = "http://localhost:5000/predict";

    // ✅ DTO simple pour retourner 2 valeurs
    public static class PredictionResult {
        private final String interpretation;
        private final double probabilite;

        public PredictionResult(String interpretation, double probabilite) {
            this.interpretation = interpretation;
            this.probabilite = probabilite;
        }

        public String getInterpretation() {
            return interpretation;
        }

        public double getProbabilite() {
            return probabilite;
        }
    }

    // ✅ Méthode réutilisable
    public static PredictionResult predict(String jsonInput) throws Exception {

        URL url = new URL(FLASK_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes());
        }

        BufferedReader br;
        if (conn.getResponseCode() >= 400) {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }

        StringBuilder responseIA = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            responseIA.append(line);
        }

        JSONObject json = new JSONObject(responseIA.toString());

        String interpretation = json.getString("interpretation");
        double probabilite = json.getDouble("probabilite_risque");

        return new PredictionResult(interpretation, probabilite);
    }
}
