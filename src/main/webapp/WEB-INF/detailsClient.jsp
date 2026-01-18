<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Détails client</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
</head>
<body class="with-navbar">
    <%@ include file="navbar.jsp" %>

    <div class="container">
        <div class="page-header">
            <div>
                <h1>Détails du client #${client.idClient}</h1>
                <p class="subtitle">Informations client + prêts associés</p>
            </div>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/clients">
                ← Retour
            </a>
        </div>

        <!-- Infos client -->
        <div class="table-container">
            <table class="data-table">
                <tr><th>ID</th><td>${client.idClient}</td></tr>
                <tr><th>Ville</th><td>${client.ville}</td></tr>
                <tr><th>Code postal</th><td>${client.codePostal}</td></tr>
                <tr><th>Revenu mensuel</th><td>${client.revenuMensuel} DH</td></tr>
            </table>
        </div>

        <br/>

        <!-- Prêts -->
        <h2>Prêts du client</h2>

        <c:if test="${empty pretsClient}">
            <div class="alert alert-warning">
                Aucun prêt associé à ce client.
            </div>
        </c:if>

        <c:if test="${not empty pretsClient}">
            <div class="table-container">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID Prêt</th>
                            <th>Type</th>
                            <th>Montant mensuel</th>
                            <th>Durée</th>
                            <th>Taux</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${pretsClient}" var="p">
                            <tr>
                                <td>${p.idPret}</td>
                                <td>${p.typePret}</td>
                                <td>${p.montantMensuel} DH</td>
                                <td>${p.duree} mois</td>
                                <td>${p.tauxInteret}%</td>
                                <td>
                                    <a class="btn-action btn-edit"
                                       href="${pageContext.request.contextPath}/prets?action=edit&id=${p.idPret}">
                                       &#9998;
                                    </a>
                                    <a class="btn-action btn-delete"
                                       href="${pageContext.request.contextPath}/prets?action=delete&id=${p.idPret}"
                                       onclick="return confirm('Supprimer ce prêt ?');">
                                       &#128465;
                                    </a>
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/evaluerRisque"
                                          style="display:inline;">
                                        <input type="hidden" name="id_pret" value="${p.idPret}">
                                        <button type="submit" class="btn-action btn-risk" title="Évaluer risque">
                                            &#128202;
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

    </div>
</body>
</html>
