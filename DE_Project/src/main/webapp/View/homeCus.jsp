<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/homeCustomer.css">
        <title>Trang chủ - Daily Essentials</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>

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
