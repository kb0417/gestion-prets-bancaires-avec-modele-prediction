<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier un client</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modifier.css">
</head>
<body>
    <div class="container">
        <!-- En-tête -->
        <div class="form-header">
            <h1>Modifier un client</h1>
            <p class="subtitle">Modifiez les informations du client #${client.idClient}</p>
        </div>

        <!-- Message d'erreur -->
        <c:if test="${not empty erreur}">
            <div class="alert alert-error">
                &#10060; ${erreur}
            </div>
        </c:if>

        <!-- Formulaire -->
        <div class="form-container">
            <form method="post" action="${pageContext.request.contextPath}/clients">
                <input type="hidden" name="action" value="update"/>
                
                <div class="form-group">
                    <label for="id_client">ID Client</label>
                    <input type="text" 
                           id="id_client" 
                           name="id_client" 
                           class="form-control readonly" 
                           value="${client.idClient}" 
                           readonly/>
                    <small class="form-hint">L'ID ne peut pas être modifié</small>
                </div>

                <div class="form-group">
                    <label for="ville">Ville <span class="required">*</span></label>
                    <input type="text" 
                           id="ville" 
                           name="ville" 
                           class="form-control" 
                           value="${client.ville}" 
                           placeholder="Ex: Casablanca"
                           required/>
                </div>

                <div class="form-group">
                    <label for="code_postal">Code postal <span class="required">*</span></label>
                    <input type="text" 
                           id="code_postal" 
                           name="code_postal" 
                           class="form-control" 
                           value="${client.codePostal}" 
                           placeholder="Ex: 20000"
                           required/>
                </div>

                <div class="form-group">
                    <label for="revenu_mensuel">Revenu mensuel (DH) <span class="required">*</span></label>
                    <input type="number" 
                           id="revenu_mensuel" 
                           name="revenu_mensuel" 
                           class="form-control" 
                           step="0.01" 
                           value="${client.revenuMensuel}" 
                           placeholder="Ex: 15000.00"
                           required/>
                </div>

                <div class="form-actions">
                    <a href="${pageContext.request.contextPath}/clients" class="btn btn-secondary">
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