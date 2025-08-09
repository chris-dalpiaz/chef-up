// Seleciona todos os elementos do DOM que possuem a classe 'footer-item' e guarda na variável footerItems
const footerItems = document.querySelectorAll('.footer-item');

// Para cada elemento dentro de footerItems, executa a função passada
footerItems.forEach(item => {
  // Adiciona um listener para o evento de clique no item atual
  item.addEventListener('click', () => {
    // Ao clicar, percorre todos os footerItems e remove a classe 'selected' de cada um
    footerItems.forEach(i => i.classList.remove('selected'));
    // Adiciona a classe 'selected' somente no item que foi clicado, para destacar ele
    item.classList.add('selected');
  });
});
