<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier un utilisateur</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modifier.css">
</head>
<body>
    <div class="container">
        <!-- En-tête -->
        <div class="form-header">
            <h1>Modifier un utilisateur</h1>
            <p class="subtitle">Modifiez les informations de l'utilisateur #${utilisateur.idUtilisateur}</p>
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
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id_utilisateur" value="${utilisateur.idUtilisateur}">
                
                <div class="form-group">
                    <label for="id_display">ID Utilisateur</label>
                    <input type="text" 
                           id="id_display" 
                           class="form-control readonly" 
                           value="${utilisateur.idUtilisateur}" 
                           readonly/>
                    <small class="form-hint">L'ID ne peut pas être modifié</small>
                </div>

                <div class="form-group">
                    <label for="login">Nom d'utilisateur (Login) <span class="required">*</span></label>
                    <input type="text" 
                           id="login" 
                           name="login" 
                           class="form-control" 
                           value="${utilisateur.login}" 
                           placeholder="Ex: agent.commercial"
                           required/>
                </div>

                <div class="form-group">
                    <label for="mot_de_passe">Mot de passe <span class="required">*</span></label>
                    <input type="password" 
                           id="mot_de_passe" 
                           name="mot_de_passe" 
                           class="form-control" 
                           value="${utilisateur.motDePasse}" 
                           placeholder="Minimum 6 caractères"
                           minlength="6"
                           required/>
                    <small class="form-hint">Laissez tel quel si vous ne souhaitez pas changer le mot de passe</small>
                </div>

                <div class="form-group">
                    <label for="role">Rôle <span class="required">*</span></label>
                    <select id="role" name="role" class="form-control" required>
                        <option value="AGENT" ${utilisateur.role == 'AGENT' ? 'selected' : ''}>
                            AGENT - Accès standard
                        </option>
                        <option value="ADMIN" ${utilisateur.role == 'ADMIN' ? 'selected' : ''}>
                            ADMIN - Accès administrateur
                        </option>
                    </select>
                    <small class="form-hint">Les agents peuvent gérer les clients et prêts. Les admins ont accès à tout.</small>
                </div>

                <div class="form-actions">
                    <a href="${pageContext.request.contextPath}/utilisateurs" class="btn btn-secondary">
                        &#8592; Annuler
                    </a>
                    <button type="submit" class="btn btn-primary">
                        &#128190; Enregistrer les modifications
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>