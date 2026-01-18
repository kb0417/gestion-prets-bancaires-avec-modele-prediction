<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar">
    <div class="navbar-container">
        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/dashboard" class="navbar-brand">
            <img src="${pageContext.request.contextPath}/images/LOGO.png" alt="PRÊTFLOW" class="navbar-logo">
            <span class="navbar-title">PRÊTFLOW</span>
        </a>

        <!-- Menu de navigation -->
        <ul class="navbar-menu">
            <li class="navbar-item">
                <a href="${pageContext.request.contextPath}/dashboard" class="navbar-link">
                    <span class="nav-icon">&#127968;</span>
                    <span>Dashboard</span>
                </a>
            </li>
            <li class="navbar-item">
                <a href="${pageContext.request.contextPath}/clients" class="navbar-link">
                    <span class="nav-icon">&#128100;</span>
                    <span>Clients</span>
                </a>
            </li>
            <li class="navbar-item">
                <a href="${pageContext.request.contextPath}/prets" class="navbar-link">
                    <span class="nav-icon">&#128179;</span>
                    <span>Prêts</span>
                </a>
            </li>
            <c:if test="${sessionScope.utilisateur.role == 'ADMIN'}">
                <li class="navbar-item">
                    <a href="${pageContext.request.contextPath}/utilisateurs" class="navbar-link">
                        <span class="nav-icon">&#9881;</span>
                        <span>Utilisateurs</span>
                    </a>
                </li>
            </c:if>
        </ul>

        <!-- Profil utilisateur -->
        <div class="navbar-user">
            <div class="user-badge ${sessionScope.utilisateur.role == 'ADMIN' ? 'badge-admin' : 'badge-agent'}">
                <span class="user-icon">&#128100;</span>
                <div class="user-info">
                    <span class="user-name">${sessionScope.utilisateur.login}</span>
                    <span class="user-role">${sessionScope.utilisateur.role}</span>
                </div>
            </div>
            <a href="${pageContext.request.contextPath}/logout" 
               class="logout-link"
               onclick="return confirm('Êtes-vous sûr de vouloir vous déconnecter ?');"
               title="Déconnexion">
                &#128682;
            </a>
        </div>
    </div>
</nav>