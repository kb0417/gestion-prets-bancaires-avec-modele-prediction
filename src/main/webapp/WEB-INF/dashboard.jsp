<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord - Gestion des prêts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body class="with-navbar">
    <!-- Inclusion de la navbar -->
    <%@ include file="navbar.jsp" %>
    
    <div class="dashboard-container">
        <!-- En-tête avec animation -->
        <div class="dashboard-header fade-in">
            <div class="header-content">
                <h1 class="dashboard-title">Tableau de bord</h1>
                <p class="dashboard-subtitle">Bienvenue, ${sessionScope.utilisateur.login} &#128075;</p>
            </div>
            <div class="header-date">
                <span class="date-icon">&#128197;</span>
                <span id="currentDate"></span>
            </div>
        </div>
        
        <!-- Statistiques avec animations -->
        <div class="stats-grid">
            <div class="stat-card clients slide-up" style="animation-delay: 0.1s;">
                <div class="stat-icon-wrapper">
                    <span class="stat-icon pulse">&#128100;</span>
                </div>
                <div class="stat-content">
                    <div class="stat-label">Nombre de clients</div>
                    <div class="stat-value counter" data-target="${nbClients}">0</div>
                    <div class="stat-trend positive">
                        <span>&#9650;</span> +12% ce mois
                    </div>
                </div>
            </div>
            
            <div class="stat-card prets slide-up" style="animation-delay: 0.2s;">
                <div class="stat-icon-wrapper">
                    <span class="stat-icon pulse">&#128196;</span>
                </div>
                <div class="stat-content">
                    <div class="stat-label">Nombre de prêts</div>
                    <div class="stat-value counter" data-target="${nbPrets}">0</div>
                    <div class="stat-trend positive">
                        <span>&#9650;</span> +8% ce mois
                    </div>
                </div>
            </div>
            
            <div class="stat-card montant slide-up" style="animation-delay: 0.3s;">
                <div class="stat-icon-wrapper">
                    <span class="stat-icon pulse">&#128176;</span>
                </div>
                <div class="stat-content">
                    <div class="stat-label">Montant total</div>
                    <div class="stat-value">${totalMontant} DH</div>
                    <div class="stat-trend positive">
                        <span>&#9650;</span> +15% ce mois
                    </div>
                </div>
            </div>
            
            <div class="stat-card risque slide-up" style="animation-delay: 0.4s;">
                <div class="stat-icon-wrapper warning">
                    <span class="stat-icon shake">&#9888;</span>
                </div>
                <div class="stat-content">
                    <div class="stat-label">Prêts à risque</div>
                    <div class="stat-value counter" data-target="${nbPretsRisque}">0</div>
                    <a href="${pageContext.request.contextPath}/prets?risque=1" class="stat-link">
                        Voir les détails &#8594;
                    </a>
                </div>
            </div>          
        </div>
        
        <!-- Actions principales avec hover effects -->
        <div class="actions-section fade-in" style="animation-delay: 0.5s;">
            <h2 class="section-title">
                <span class="title-icon">&#9889;</span>
                Actions rapides
            </h2>
            <div class="action-grid">
                <a href="${pageContext.request.contextPath}/clients" class="action-card clients-card">
                    <div class="action-icon">&#128101;</div>
                    <div class="action-content">
                        <h3>Gestion des clients</h3>
                        <p>Ajouter, modifier ou consulter vos clients</p>
                    </div>
                    <span class="action-arrow">&#8594;</span>
                </a>
                
                <a href="${pageContext.request.contextPath}/prets" class="action-card prets-card">
                    <div class="action-icon">&#128179;</div>
                    <div class="action-content">
                        <h3>Gestion des prêts</h3>
                        <p>Suivez et gérez tous les prêts en cours</p>
                    </div>
                    <span class="action-arrow">&#8594;</span>
                </a>
                
                <c:if test="${sessionScope.utilisateur.role == 'ADMIN'}">
                    <a href="${pageContext.request.contextPath}/utilisateurs" class="action-card admin-card">
                        <div class="action-icon">&#9881;</div>
                        <div class="action-content">
                            <h3>Gestion des utilisateurs</h3>
                            <p>Administrez les accès et permissions</p>
                        </div>
                        <span class="action-arrow">&#8594;</span>
                    </a>
                </c:if>
            </div>
        </div>
    </div>

    <script>
        // Date actuelle
        const dateElement = document.getElementById('currentDate');
        const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
        dateElement.textContent = new Date().toLocaleDateString('fr-FR', options);

        // Animation des compteurs
        const counters = document.querySelectorAll('.counter');
        counters.forEach(counter => {
            const target = parseInt(counter.getAttribute('data-target'));
            const duration = 2000;
            const step = target / (duration / 16);
            let current = 0;

            const updateCounter = () => {
                current += step;
                if (current < target) {
                    counter.textContent = Math.floor(current);
                    requestAnimationFrame(updateCounter);
                } else {
                    counter.textContent = target;
                }
            };

            // Démarrer l'animation après un court délai
            setTimeout(updateCounter, 500);
        });
    </script>
</body>
</html>