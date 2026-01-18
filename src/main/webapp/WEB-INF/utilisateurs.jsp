<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des utilisateurs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
</head>
<body class="with-navbar">
	<!-- Inclusion de la navbar -->
    <%@ include file="navbar.jsp" %>
    
    <div class="container">
        <!-- Message de succès -->
        <c:if test="${not empty sessionScope.success}">
            <div class="alert alert-success">
                ✓ ${sessionScope.success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>

        <!-- En-tête avec bouton d'ajout -->
        <div class="page-header">
            <div>
                <h1>Gestion des utilisateurs</h1>
                <p class="subtitle">Gérez les accès et les rôles</p>
            </div>
            <a href="${pageContext.request.contextPath}/utilisateurs?action=add" class="btn btn-primary">
                <span class="btn-icon">➕</span> Ajouter un utilisateur
            </a>
        </div>

        <!-- Info pagination -->
        <div class="pagination-info">
            Affichage de <span id="start">0</span> à <span id="end">0</span> sur <span id="total">0</span> utilisateurs
        </div>

        <!-- Tableau des utilisateurs -->
        <div class="table-container">
            <table class="data-table" id="utilisateursTable">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Login</th>
                        <th>Rôle</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <c:forEach items="${utilisateurs}" var="u">
                        <tr class="user-row">
                            <td><span class="badge">${u.idUtilisateur}</span></td>
                            <td>${u.login}</td>
                            <td>
                                <span class="role-badge ${u.role == 'ADMIN' ? 'role-admin' : 'role-user'}">
                                    ${u.role}
                                </span>
                            </td>
                            <td>
                                <div class="action-buttons">
                                    <a href="${pageContext.request.contextPath}/utilisateurs?action=edit&id=${u.idUtilisateur}" 
                                       class="btn-action btn-edit" 
                                       title="Modifier">
                                        &#9998;
                                    </a>
                                    <a href="${pageContext.request.contextPath}/utilisateurs?action=delete&id=${u.idUtilisateur}" 
                                       class="btn-action btn-delete" 
                                       title="Supprimer"
                                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?');">
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
        initPagination('.user-row', 20);
    </script>
</body>
</html>