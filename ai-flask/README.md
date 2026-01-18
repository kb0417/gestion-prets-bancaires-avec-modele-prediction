# Flask AI – Prédiction du risque de prêt

## Installation
```bash
pip install -r requirements.txt

## Lancement
```bash
python app.py

## Endpoint
POST /predict
### Exemple
{
  "revenu": 5000,
  "remboursement": 1200,
  "duree": 240,
  "taux": 3.5,
  "type": "immobilier"
}

