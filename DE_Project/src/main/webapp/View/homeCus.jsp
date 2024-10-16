<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/homeCustomer.css">
        <title>Trang chủ - Daily Essentials</title>
    </head>
    <body>
        <nav class="navbar">
            <div class="menu-container">
                <div class="logo-container">
                    <a href="${pageContext.request.contextPath}/Home" class="logo">Daily Essentials</a>
                </div>
                <div class="search-container">
                    <div class="find-products">
                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-find.svg" class="icon ic-find" alt="Search Icon">
                        <input type="text" id="txt_FindProduct" placeholder="Giao hàng nhanh 3h, Giảm 20% sản phẩm Thịt Navi">
                    </div>
                </div>
                <div class="right-container">
                    <a href="#" class="cart-link">
                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-cart.svg" alt="Cart Icon" class="icon">
                        <span>Giỏ hàng (0)</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/Home/Info" class="user-link">
                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-user.svg" alt="User Icon" class="icon">
                        <span>Khách hàng</span>
                    </a>
                </div>
            </div>
        </nav>

        <section class="product-wrapper">
            <div class="product-menu-wrapper">
                <div class="product-menu-left">
                    <button class="menu-btn">
                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-bars.svg" class="icon ic-bars" alt="Bars Icon">
                        Danh mục sản phẩm
                    </button>
                </div>

                <div class="product-menu-right">
                    <button class="purchase-btn">
                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-cart2.svg" class="icon ic-cart2" alt="Cart Icon">
                        Sản phẩm đã mua
                    </button>
                </div>
            </div>

            <!-- Carousel Section -->
            <div class="carousel">
                <button class="carousel-btn left-btn" onclick="moveCarousel(-1)">&#10094;</button>
                <div class="carousel-track">
                    <img src="${pageContext.request.contextPath}/Component/IMG/sprite_1.5L.jpg" class="carousel-image" alt="Slide 1">
                    <img src="${pageContext.request.contextPath}/Component/IMG/sprite_1.5L.jpg" class="carousel-image" alt="Slide 2">
                    <img src="${pageContext.request.contextPath}/Component/IMG/sprite_1.5L.jpg" class="carousel-image" alt="Slide 3">
                </div>
                <button class="carousel-btn right-btn" onclick="moveCarousel(1)">&#10095;</button>
            </div>

            <!-- Product Section -->
            <section class="product-section">
                <h2>Sản Phẩm Hot</h2>
                <div id="product-grid" class="product-grid"></div>

                <!-- Load More Button -->
                <button id="load-more-btn" class="load-more-btn">
                    Xem thêm 25 sản phẩm <span class="highlight">Giá rẻ hôm nay</span> 
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-down.svg" class="icon ic-down" alt="Arrow Icon">
                </button>
            </section>
        </section>
        <script src="${pageContext.request.contextPath}/Component/JS/productDisplay.js"></script>

    </body>
</html>
