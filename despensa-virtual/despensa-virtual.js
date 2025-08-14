document.addEventListener("DOMContentLoaded", function () {
document.querySelectorAll('.nav-item').forEach(item => {
    item.addEventListener('mouseenter', (e) => {
        const icon = e.target.querySelector('img');
        if (icon) {
            if (e.target.id === 'perfil-item') {
                icon.src = 'circle-user-round2.svg';
            } else if (e.target.id === 'despensa-item') {
                icon.src = 'package2.svg';
            } else if (e.target.id === 'receitas-item') {
                icon.src = 'utensils2.svg';
            } else if (e.target.id === 'favoritos-item') {
                icon.src = 'heart2.svg';
            } else if (e.target.id === 'configuracoes-item') {
                icon.src = 'settings2.svg';
            }
        }
    });

    item.addEventListener('mouseleave', (e) => {
        const icon = e.target.querySelector('img');
        if (icon) {
            if (e.target.id === 'perfil-item') {
                icon.src = 'circle-user-round.svg';
            } else if (e.target.id === 'despensa-item') {
                icon.src = 'package.svg';
            } else if (e.target.id === 'receitas-item') {
                icon.src = 'utensils.svg';
            } else if (e.target.id === 'favoritos-item') {
                icon.src = 'heart.svg';
            } else if (e.target.id === 'configuracoes-item') {
                icon.src = 'settings.svg';
            }
        }
    });
});
});