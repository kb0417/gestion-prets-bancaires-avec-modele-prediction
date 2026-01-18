<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - Gestion des prêts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <div class="login-wrapper">
        <div class="login-container">
            <!-- Logo / En-tête -->
            <div class="login-header">
                <div class="logo-circle">
                    <span class="logo-icon">&#128179;</span>
                </div>
                <h1>Gestion des Prêts</h1>
                <p class="subtitle">Connectez-vous pour accéder à votre espace</p>
            </div>

            <!-- Message d'erreur -->
            <c:if test="${not empty erreur}">
                <div class="alert-error">
                    &#10060; ${erreur}
                </div>
            </c:if>

            <!-- Formulaire de connexion -->
            <form action="${pageContext.request.contextPath}/login" method="post" class="login-form">
                <div class="input-group">
                    <label for="login">Nom d'utilisateur</label>
                    <div class="input-wrapper">
                        <span class="input-icon">&#128100;</span>
                        <input type="text" 
                               id="login" 
                               name="login" 
                               placeholder="Entrez votre login" 
                               autocomplete="username"
                               required />
                    </div>
                </div>

                <div class="input-group">
                    <label for="mot_de_passe">Mot de passe</label>
                    <div class="input-wrapper">
                        <span class="input-icon">&#128274;</span>
                        <input type="password" 
                               id="mot_de_passe" 
                               name="mot_de_passe" 
                               placeholder="Entrez votre mot de passe"
                               autocomplete="current-password"
                               required />
                    </div>
                </div>

                <button type="submit" class="login-btn">
                    Se connecter
                </button>
            </form>

            <!-- Footer -->
            <div class="login-footer">
                <p>&copy; 2026 Plateforme de Gestion des Prêts Bancaires</p>
            </div>
        </div>
    </div>
</body>
</html>