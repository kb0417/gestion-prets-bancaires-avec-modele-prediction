from flask import Flask, request, jsonify
import joblib
import numpy as np

app = Flask(__name__)

# Chargement du modèle et du scaler
model = joblib.load("modele_risque_pret.pkl")
scaler = joblib.load("scaler.pkl")

# Mapping du type de prêt (UX propre)
TYPE_MAP = {
    "immobilier": 0,
    "automobile": 1
}

@app.route("/", methods=["GET"])
def home():
    return "API Flask - Évaluation du risque de prêt bancaire"

@app.route("/predict", methods=["POST"])
def predict():
    try:
        data = request.get_json()

        # Vérification des champs requis
        required_fields = ["revenu", "remboursement", "duree", "taux", "type"]
        for field in required_fields:
            if field not in data:
                return jsonify({"error": f"Champ manquant : {field}"}), 400

        revenu = float(data["revenu"])
        remboursement = float(data["remboursement"])
        duree = int(data["duree"])
        taux = float(data["taux"])

        type_str = data["type"].lower()
        if type_str not in TYPE_MAP:
            return jsonify({
                "error": "Type de prêt invalide (immobilier ou automobile)"
            }), 400

        type_pret = TYPE_MAP[type_str]

        # Features dans le bon ordre
        features = np.array([[revenu, remboursement, duree, taux, type_pret]])

        # Normalisation
        features_scaled = scaler.transform(features)

        # Prédiction
        prediction = model.predict(features_scaled)[0]
        proba = model.predict_proba(features_scaled)[0][1]

        return jsonify({
            "risque": int(prediction),
            "probabilite_risque": round(float(proba), 2),
            "interpretation": (
                "Risque élevé" if prediction == 1 else "Risque faible"
            )
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500


if __name__ == "__main__":
    app.run(debug=True)

