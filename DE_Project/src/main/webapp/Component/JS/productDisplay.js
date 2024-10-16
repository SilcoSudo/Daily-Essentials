// Product Data Array
const products = [
    { id: 1, name: 'Leffe Blonde 330ml', price: 55000, image: "../Component/IMG/Mogu.jpg" },
    { id: 2, name: 'Comfort 3.0KG', price: 139000, image: '../Component/IMG/sprite_1.5L.jpg' },
    { id: 3, name: 'Aquafina 1.5L', price: 10000, image: '../Component/IMG/Mogu.jpg' },
    { id: 4, name: 'Leffe Blonde 330ml', price: 55000, image: '../Component/IMG/Mogu.jpg' },
    { id: 5, name: 'Comfort 3.0KG', price: 139000, image: '../Component/IMG/sprite_1.5L.jpg' },
    { id: 6, name: 'Aquafina 1.5L', price: 10000, image: '../Component/IMG/Mogu.jpg' },
    { id: 7, name: 'Leffe Blonde 330ml', price: 55000, image: '../Component/IMG/Mogu.jpg' },
    { id: 8, name: 'Comfort 3.0KG', price: 139000, image: '../Component/IMG/sprite_1.5L.jpg' },
    { id: 9, name: 'Aquafina 1.5L', price: 10000, image: '../Component/IMG/Mogu.jpg' },
    { id: 10, name: 'Leffe Blonde 330ml', price: 55000, image: '../Component/IMG/Mogu.jpg' },
    { id: 11, name: 'Comfort 3.0KG', price: 139000, image: '../Component/IMG/sprite_1.5L.jpg' },
    { id: 12, name: 'Aquafina 1.5L', price: 10000, image: '../Component/IMG/Mogu.jpg' },
    // Add more products as needed...
];

// Display Products with Limit
function displayProducts(limit = 10) {
    const productGrid = document.getElementById('product-grid');
    productGrid.innerHTML = ''; // Clear current grid

    products.slice(0, limit).forEach(product => {
        const productCard = document.createElement('div');
        productCard.classList.add('product-card');
        productCard.innerHTML = `
        <img src="${product.image}" alt="${product.name}" class="product-image">
        <p class="product-name">${product.name}</p>
        <p class="product-price">${product.price.toLocaleString()} ₫</p>
        <button class="btn-add-to-cart" onclick="addToCart()"> 
            <img src="../Component/IMG/ic-cart.svg" alt="Cart Icon" class="cart-icon"> 
            Thêm vào giỏ hàng
        </button>
    `;
        productGrid.appendChild(productCard);
    });
}

// Filter Products by Name
function filterProducts() {
    const query = document.getElementById('txt_FindProduct').value.toLowerCase();
    const filteredProducts = products.filter(product => 
        product.name.toLowerCase().includes(query)
    );
    displayProducts(filteredProducts.length); // Display filtered results
}

// Add to Cart Functionality
let cartCount = 0;
function addToCart() {
    cartCount++;
    document.getElementById('cart-count').innerText = cartCount;
}

// Carousel Movement Logic
let currentSlide = 0;
function moveCarousel(direction) {
    const carouselTrack = document.querySelector('.carousel-track');
    const slides = document.querySelectorAll('.carousel-image');
    const totalSlides = slides.length;

    currentSlide = (currentSlide + direction + totalSlides) % totalSlides;
    const slideWidth = slides[0].clientWidth;
    carouselTrack.style.transform = `translateX(-${currentSlide * slideWidth}px)`;
}

// Load More Products on Click
document.getElementById('load-more-btn').addEventListener('click', () => {
    displayProducts(35); // Display all products
    document.getElementById('load-more-btn').style.display = 'none'; // Hide button
});

// Initial Product Load
document.addEventListener('DOMContentLoaded', () => displayProducts(10));
