<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Évaluation du risque</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/evaluation.css">
</head>
<body>
    <div class="evaluation-wrapper">
        <div class="evaluation-container">
            <!-- En-tête avec icône -->
            <div class="evaluation-header">
                <div class="icon-circle ${interpretation eq 'Risque élevé' ? 'icon-danger' : 'icon-success'}">
                    <span class="icon">${interpretation eq 'Risque élevé' ? '&#9888;' : '&#10004;'}</span>
                </div>
                <h1>Évaluation du risque</h1>
                <p class="subtitle">Analyse basée sur l'intelligence artificielle</p>
            </div>

            <!-- Résultats -->
            <div class="results-section">
                <!-- Interprétation -->
                <div class="result-card ${interpretation eq 'Risque élevé' ? 'card-danger' : 'card-success'}">
                    <div class="result-label">Interprétation</div>
                    <div class="result-value">
                        ${interpretation}
                    </div>
                </div>

                <!-- Probabilité -->
                <div class="result-card card-neutral">
                    <div class="result-label">Probabilité de risque</div>
                    <div class="result-value probability">
                        ${probabilite}
                    </div>
                </div>

                <!-- Indicateur visuel -->
                <div class="risk-indicator">
                    <div class="indicator-bar">
                        <c:choose>
                            <c:when test="${interpretation eq 'Risque élevé'}">
                                <div class="indicator-fill danger" style="width: 75%;"></div>
                            </c:when>
                            <c:otherwise>
                                <div class="indicator-fill success" style="width: 25%;"></div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="indicator-labels">
                        <span class="label-low">Risque faible</span>
                        <span class="label-high">Risque élevé</span>
                    </div>
                </div>
            </div>

            <!-- Actions -->
            <div class="action-buttons">
                <a href="${pageContext.request.contextPath}/prets" class="btn btn-primary">
                    &#8592; Retour aux prêts
                </a>
                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">
                    &#127968; Dashboard
                </a>
            </div>
        </div>
    </div>
</body>
</html>