/**
 * Script de pagination réutilisable
 * Utilisation : initPagination('.nom-classe-lignes', nombreLignesParPage)
 */

function initPagination(rowSelector, rowsPerPage = 20) {
    let currentPage = 1;
    const rows = document.querySelectorAll(rowSelector);
    const totalRows = rows.length;
    const totalPages = Math.ceil(totalRows / rowsPerPage);

    function displayPage(page) {
        const start = (page - 1) * rowsPerPage;
        const end = start + rowsPerPage;

        rows.forEach((row, index) => {
            if (index >= start && index < end) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });

        // Mise a jour des infos
        const startEl = document.getElementById('start');
        const endEl = document.getElementById('end');
        const totalEl = document.getElementById('total');
        
        if (startEl && endEl && totalEl) {
            startEl.textContent = totalRows > 0 ? start + 1 : 0;
            endEl.textContent = Math.min(end, totalRows);
            totalEl.textContent = totalRows;
        }

        // Mise a jour de la pagination
        renderPagination();
        
        // Scroll vers le haut du tableau
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    function renderPagination() {
        const paginationDiv = document.getElementById('pagination');
        if (!paginationDiv) return;
        
        paginationDiv.innerHTML = '';

        if (totalPages <= 1) return;

        // Bouton Precedent
        const prevBtn = document.createElement('button');
        prevBtn.innerHTML = '&larr; Pr&eacute;c&eacute;dent';
        prevBtn.className = 'page-btn' + (currentPage === 1 ? ' disabled' : '');
        prevBtn.disabled = currentPage === 1;
        prevBtn.onclick = () => {
            if (currentPage > 1) {
                currentPage--;
                displayPage(currentPage);
            }
        };
        paginationDiv.appendChild(prevBtn);

        // Numeros de pages
        const maxButtons = 5;
        let startPage = Math.max(1, currentPage - Math.floor(maxButtons / 2));
        let endPage = Math.min(totalPages, startPage + maxButtons - 1);

        if (endPage - startPage < maxButtons - 1) {
            startPage = Math.max(1, endPage - maxButtons + 1);
        }

        // Premiere page
        if (startPage > 1) {
            const firstBtn = document.createElement('button');
            firstBtn.textContent = '1';
            firstBtn.className = 'page-number';
            firstBtn.onclick = () => {
                currentPage = 1;
                displayPage(currentPage);
            };
            paginationDiv.appendChild(firstBtn);

            if (startPage > 2) {
                const dots = document.createElement('span');
                dots.textContent = '...';
                dots.className = 'page-dots';
                paginationDiv.appendChild(dots);
            }
        }

        // Pages intermediaires
        for (let i = startPage; i <= endPage; i++) {
            const btn = document.createElement('button');
            btn.textContent = i;
            btn.className = 'page-number' + (i === currentPage ? ' active' : '');
            btn.onclick = () => {
                currentPage = i;
                displayPage(currentPage);
            };
            paginationDiv.appendChild(btn);
        }

        // Derniere page
        if (endPage < totalPages) {
            if (endPage < totalPages - 1) {
                const dots = document.createElement('span');
                dots.textContent = '...';
                dots.className = 'page-dots';
                paginationDiv.appendChild(dots);
            }

            const lastBtn = document.createElement('button');
            lastBtn.textContent = totalPages;
            lastBtn.className = 'page-number';
            lastBtn.onclick = () => {
                currentPage = totalPages;
                displayPage(currentPage);
            };
            paginationDiv.appendChild(lastBtn);
        }

        // Bouton Suivant
        const nextBtn = document.createElement('button');
        nextBtn.innerHTML = 'Suivant &rarr;';
        nextBtn.className = 'page-btn' + (currentPage === totalPages ? ' disabled' : '');
        nextBtn.disabled = currentPage === totalPages;
        nextBtn.onclick = () => {
            if (currentPage < totalPages) {
                currentPage++;
                displayPage(currentPage);
            }
        };
        paginationDiv.appendChild(nextBtn);
    }

    // Initialisation
    displayPage(1);
}