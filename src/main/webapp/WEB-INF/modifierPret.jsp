<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier un prêt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modifier.css">
</head>
<body>
    <div class="container">
        <!-- En-tête -->
        <div class="form-header">
            <h1>Modifier un prêt</h1>
            <p class="subtitle">Modifiez les informations du prêt #${pret.idPret}</p>
        </div>

        <!-- Message d'erreur -->
        <c:if test="${not empty erreur}">
            <div class="alert alert-error">
                &#10060; ${erreur}
            </div>
        </c:if>

        <!-- Formulaire -->
        <div class="form-container">
            <form method="post" action="${pageContext.request.contextPath}/prets">
                <input type="hidden" name="action" value="update"/>
                <input type="hidden" name="id_pret" value="${pret.idPret}"/>
                
                <div class="form-group">
                    <label for="id_client">Client <span class="required">*</span></label>
                    <select id="id_client" name="id_client" class="form-control" required>
                        <c:forEach items="${clients}" var="c">
                            <option value="${c.idClient}"
                                ${c.idClient == pret.idClient ? "selected" : ""}>
                                Client #${c.idClient} - ${c.ville}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="type_pret">Type de prêt <span class="required">*</span></label>
                    <select id="type_pret" name="type_pret" class="form-control" required>
                        <option value="immobilier" ${pret.typePret == 'immobilier' ? 'selected' : ''}>Immobilier</option>
                        <option value="automobile" ${pret.typePret == 'automobile' ? 'selected' : ''}>Automobile</option>
                    </select>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="montant_mensuel">Montant mensuel (DH) <span class="required">*</span></label>
                        <input type="number" 
                               id="montant_mensuel" 
                               name="montant_mensuel" 
                               class="form-control" 
                               step="0.01" 
                               value="${pret.montantMensuel}" 
                               placeholder="Ex: 5000.00"
                               required/>
                    </div>

                    <div class="form-group">
                        <label for="duree">Durée (mois) <span class="required">*</span></label>
                        <input type="number" 
                               id="duree" 
                               name="duree" 
                               class="form-control" 
                               value="${pret.duree}" 
                               placeholder="Ex: 120"
                               required/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="taux_interet">Taux d'intérêt (%) <span class="required">*</span></label>
                    <input type="number" 
                           id="taux_interet" 
                           name="taux_interet" 
                           class="form-control" 
                           step="0.01" 
                           value="${pret.tauxInteret}" 
                           placeholder="Ex: 4.5"
                           required/>
                </div>

                <div class="form-actions">
                    <a href="${pageContext.request.contextPath}/prets" class="btn btn-secondary">
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