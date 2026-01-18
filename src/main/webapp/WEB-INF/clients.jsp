<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des clients</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
</head>
<body class="with-navbar">
	<!-- Inclusion de la navbar -->
    <%@ include file="navbar.jsp" %>
    
    <div class="filter-section">
	    <form method="get" action="${pageContext.request.contextPath}/clients" class="filter-form">
	        <label class="filter-label">Rechercher :</label>
	        <input type="text" name="q" class="form-control"
	               placeholder="ID, ville, code postal..."
	               value="${param.q}">
	        <button type="submit" class="btn btn-filter">Rechercher</button>
	        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/clients">Réinitialiser</a>
	    </form>
	</div>
    
    
    <div class="container">
        <!-- Message de succès -->
        <c:if test="${not empty sessionScope.success}">
            <div class="alert alert-success">
                ✓ ${sessionScope.success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>
         <!-- Message d'erreur -->
        <c:if test="${not empty sessionScope.error}">
		    <div class="alert alert-danger">
		        ✗ ${sessionScope.error}
		    </div>
		    <c:remove var="error" scope="session"/>
		</c:if>
        

        <!-- En-tête avec bouton d'ajout -->
        <div class="page-header">
            <div>
                <h1>Gestion des clients</h1>
                <p class="subtitle">Consultez et gérez tous vos clients</p>
            </div>
            <a href="${pageContext.request.contextPath}/clients?action=add" class="btn btn-primary">
                <span class="btn-icon">➕</span> Ajouter un client
            </a>
        </div>

        <!-- Info pagination -->
        <div class="pagination-info">
            Affichage de <span id="start">0</span> à <span id="end">0</span> sur <span id="total">0</span> clients
        </div>

        <!-- Tableau des clients -->
        <div class="table-container">
            <table class="data-table" id="clientsTable">
                <thead>
                    <tr>
                        <th>ID Client</th>
                        <th>Ville</th>
                        <th>Code postal</th>
                        <th>Revenu mensuel</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <c:forEach items="${clients}" var="client">
                        <tr class="client-row">
                            <td><span class="badge">${client.idClient}</span></td>
                            <td>${client.ville}</td>
                            <td>${client.codePostal}</td>
                            <td class="amount">${client.revenuMensuel} DH</td>
                            <td>
                                <div class="action-buttons">
	                                <a href="${pageContext.request.contextPath}/clients?action=view&id=${client.idClient}"
									   class="btn-action btn-view"
									   title="Voir détails">
									   👁
									</a>
                                
                                    <a href="${pageContext.request.contextPath}/clients?action=edit&id=${client.idClient}" 
                                       class="btn-action btn-edit" 
                                       title="Modifier">
                                        &#9998;
                                    </a>
                                    <a href="${pageContext.request.contextPath}/clients?action=delete&id=${client.idClient}" 
                                       class="btn-action btn-delete" 
                                       title="Supprimer"
                                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce client ?');">
                                        &#128465;
                                    </a>
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
        initPagination('.client-row', 20);
    </script>
</body>
</html>