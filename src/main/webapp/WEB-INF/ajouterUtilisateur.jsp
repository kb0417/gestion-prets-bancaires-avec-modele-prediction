<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un utilisateur</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
</head>
<body class="with-navbar">
	<!-- Inclusion de la navbar -->
    <%@ include file="navbar.jsp" %>
    
    <div class="container">
        <!-- En-tête -->
        <div class="form-header">
            <h1>Ajouter un utilisateur</h1>
            <p class="subtitle">Créez un nouveau compte utilisateur avec accès au système</p>
        </div>

        <!-- Message d'erreur -->
        <c:if test="${not empty erreur}">
            <div class="alert alert-error">
                &#10060; ${erreur}
            </div>
        </c:if>

        <!-- Formulaire -->
        <div class="form-container">
            <form method="post" action="${pageContext.request.contextPath}/utilisateurs">
                <input type="hidden" name="action" value="add">

                <div class="form-group">
                    <label for="id_utilisateur">ID Utilisateur <span class="required">*</span></label>
                    <input type="number" 
                           id="id_utilisateur" 
                           name="id_utilisateur" 
                           class="form-control" 
                           placeholder="Ex: 1001"
                           required>
                </div>

                <div class="form-group">
                    <label for="login">Nom d'utilisateur (Login) <span class="required">*</span></label>
                    <input type="text" 
                           id="login" 
                           name="login" 
                           class="form-control" 
                           placeholder="Ex: agent.commercial"
                           required>
                </div>

                <div class="form-group">
                    <label for="mot_de_passe">Mot de passe <span class="required">*</span></label>
                    <input type="password" 
                           id="mot_de_passe" 
                           name="mot_de_passe" 
                           class="form-control" 
                           placeholder="Minimum 6 caractères"
                           minlength="6"
                           required>
                    <small class="form-hint">Le mot de passe doit contenir au moins 6 caractères</small>
                </div>

                <div class="form-group">
                    <label for="role">Rôle <span class="required">*</span></label>
                    <select id="role" name="role" class="form-control" required>
                        <option value="">-- Sélectionnez un rôle --</option>
                        <option value="AGENT">AGENT - Accès standard</option>
                        <option value="ADMIN">ADMIN - Accès administrateur</option>
                    </select>
                    <small class="form-hint">Les agents peuvent gérer les clients et prêts. Les admins ont accès à tout.</small>
                </div>

                <div class="form-actions">
                    <a href="${pageContext.request.contextPath}/utilisateurs" class="btn btn-secondary">
                        &#8592; Annuler
                    </a>
                    <button type="submit" class="btn btn-primary">
                        &#10004; Créer l'utilisateur
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>