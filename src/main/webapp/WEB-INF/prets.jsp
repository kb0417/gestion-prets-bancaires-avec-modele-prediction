<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des prêts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
</head>
<body class="with-navbar">
	<!-- Inclusion de la navbar -->
    <%@ include file="navbar.jsp" %>
    
    <div class="container">
        <!-- En-tête avec bouton d'ajout -->
        <div class="page-header">
            <div>
                <h1>Gestion des prêts</h1>
                <p class="subtitle">Consultez et gérez tous les prêts</p>
            </div>
            <a href="${pageContext.request.contextPath}/prets?action=add" class="btn btn-primary">
                <span class="btn-icon">➕</span> Ajouter un prêt
            </a>
        </div>

        <!-- Filtre par client -->
        <div class="filter-section">
		    <form method="get" action="${pageContext.request.contextPath}/prets" class="filter-form">
		        <label class="filter-label">Filtrer par client :</label>
		        <select name="clientId" class="form-control filter-select">
		            <option value="">-- Tous les clients --</option>
		            <c:forEach items="${clients}" var="c">
		                <option value="${c.idClient}" ${param.clientId == c.idClient ? 'selected' : ''}>
		                    Client #${c.idClient}
		                </option>
		            </c:forEach>
		        </select>
		
		        <label class="filter-label">Rechercher :</label>
		        <input type="text" name="q" class="form-control"
		               placeholder="ID prêt ou type..."
		               value="${param.q}"/>
		
		        <button type="submit" class="btn btn-filter">Appliquer</button>
		        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/prets">Réinitialiser</a>
		    </form>
		</div>

        

		<c:if test="${param.risque == '1'}">
		    <div class="alert alert-warning" style="margin-bottom:15px;">
		        Affichage : <b>Prêts risqués uniquement</b>
		        <a href="${pageContext.request.contextPath}/prets" style="margin-left:15px;">(Voir tous)</a>
		    </div>
		</c:if>
        <!-- Info pagination -->
        <div class="pagination-info">
            Affichage de <span id="start">0</span> à <span id="end">0</span> sur <span id="total">0</span> prêts
        </div>
        
        <!-- Tableau des prêts -->
        <div class="table-container">
            <table class="data-table" id="pretsTable">
                <thead>
                    <tr>
                        <th>ID Prêt</th>
                        <th>ID Client</th>
                        <th>Montant</th>
                        <th>Durée</th>
                        <th>Taux</th>
                        <th>Type</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <c:forEach items="${prets}" var="pret">
                        <tr class="pret-row">
                            <td><span class="badge">${pret.idPret}</span></td>
                            <td><span class="badge badge-secondary">${pret.idClient}</span></td>
                            <td class="amount">${pret.montantMensuel} DH</td>
                            <td>${pret.duree} mois</td>
                            <td class="rate">${pret.tauxInteret}%</td>
                            <td><span class="type-badge">${pret.typePret}</span></td>
                            <td>
                                <div class="action-buttons">
                                    <a href="${pageContext.request.contextPath}/prets?action=edit&id=${pret.idPret}" 
                                       class="btn-action btn-edit" 
                                       title="Modifier">
                                        &#9998;
                                    </a>
                                    <a href="${pageContext.request.contextPath}/prets?action=delete&id=${pret.idPret}" 
                                       class="btn-action btn-delete" 
                                       title="Supprimer"
                                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce prêt ?');">
                                        &#128465;
                                    </a>
                                    <form method="post" 
                                          action="${pageContext.request.contextPath}/evaluerRisque"
                                          style="display: inline;">
                                        <input type="hidden" name="id_pret" value="${pret.idPret}">
                                        <button type="submit" class="btn-action btn-risk" title="Évaluer le risque">
                                            &#128202;
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Pagination -->
        <div class="pagination" id="pagination"></div>

        <!-- Bouton retour -->
        <div class="page-footer">
            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">
                ← Retour au dashboard
            </a>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script>
        initPagination('.pret-row', 20);
    </script>
</body>
</html>