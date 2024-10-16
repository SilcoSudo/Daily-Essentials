const products = [
    {
        id: 1,
        name: 'Nutri Boost Cam 1L',
        price: 29000,
        description: 'Nước uống sữa trái cây bổ dưỡng hương cam...',
        image: '../Component/IMG/sprite_1.5L.jpg',
    },
    {
        id: 2,
        name: 'Aquafina 1.5L',
        price: 10000,
        description: 'Nước khoáng tinh khiết Aquafina...',
        image: '../Component/IMG/Mogu.jpg',
    },
];

const productGrid = document.getElementById('product-grid');
const productDetail = document.getElementById('product-detail');
const closeBtn = document.getElementById('close-btn');

// Function to load products into the grid
function loadProducts() {
    products.forEach((product) => {
        const productCard = document.createElement('div');
        productCard.classList.add('product-card');
        productCard.innerHTML = `
            <img src="${product.image}" alt="${product.name}">
            <h3>${product.name}</h3>
            <p>${product.price.toLocaleString()} đ</p>
        `;
        productCard.addEventListener('click', () => showProductDetail(product));
        productGrid.appendChild(productCard);
    });
}

// Function to show product details
function showProductDetail(product) {
    document.getElementById('detail-name').innerText = product.name;
    document.getElementById('detail-price').innerText = product.price.toLocaleString();
    document.getElementById('detail-description').innerText = product.description;
    document.getElementById('detail-image').src = product.image;

    productDetail.classList.remove('hidden');
}

// Close product detail view
closeBtn.addEventListener('click', () => {
    productDetail.classList.add('hidden');
});

// Load products on page load
window.onload = loadProducts;
