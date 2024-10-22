<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/homeCustomer.css">
        <title>Trang chủ - Daily Essentials</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="container">
                <div class="banner-section">
                    <div class="carousel-container">
                        <div class="w-button-carousel">
                            <button class="icon btnCarousel">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    class="svgCarousel"
                                    viewBox="0 0 320 512"
                                    >
                                <path
                                    class="pathCarousel"
                                    d="M9.4 233.4c-12.5 12.5-12.5 32.8 0 45.3l192 192c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L77.3 256 246.6 86.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0l-192 192z"
                                    />
                                </svg>
                            </button>
                            <button class="icon btnCarousel">
                                <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    class="svgCarousel"
                                    viewBox="0 0 320 512"
                                    >
                                <path
                                    class="pathCarousel"
                                    d="M310.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-192 192c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L242.7 256 73.4 86.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l192 192z"
                                    />
                                </svg>
                            </button>
                        </div>
                        <div class="carousel">
                            <ul>
                                <li>
                                    <img src="${pageContext.request.contextPath}/Component/IMG/banner.jpg" alt="Banner 1" />
                            </li>
                            <li>
                                <img src="${pageContext.request.contextPath}/Component/IMG/banner.jpg" alt="Banner 2" />
                            </li>
                            <li>
                                <img src="${pageContext.request.contextPath}/Component/IMG/banner.jpg" alt="Banner 3" />
                            </li>
                            <li>
                                <img src="${pageContext.request.contextPath}/Component/IMG/banner.jpg" alt="Banner 4" />
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="side-banner">
                    <div class="side-image">
                        <img src="${pageContext.request.contextPath}/Component/IMG/banner.jpg" alt="Side Image 1" />
                    </div>
                    <div class="side-image">
                        <img src="${pageContext.request.contextPath}/Component/IMG/banner.jpg" alt="Side Image 2" />
                    </div>
                </div>
            </div>
            <div class="product-list">
                <p class="title">Sản Phẩm</p>
                <div class="w-products">
                    <c:forEach var="product" items="${productList}">
                        <figure class="product-item">
                            <div class="w-product-item">
                                <img src="${pageContext.request.contextPath}/${product.imageUrl}" class="product-img" />
                                <div class="product-title">${product.productName}</div>
                                <div class="product-price">${product.productPrice} ₫</div>

                                <button class="btnQuaityProduct" value="${product.productId}">
                                    <div class="w-quantityProduct">
                                        <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 576 512">
                                        <path class="icon-path" d="M0 24C0 10.7 10.7 0 24 0L69.5 0c22 0 41.5 12.8 50.6 32l411 0c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3l-288.5 0 5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5L488 336c13.3 0 24 10.7 24 24s-10.7 24-24 24l-288.3 0c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5L24 48C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96zM252 160c0 11 9 20 20 20l44 0 0 44c0 11 9 20 20 20s20-9 20-20l0-44 44 0c11 0 20-9 20-20s-9-20-20-20l-44 0 0-44c0-11-9-20-20-20s-20 9-20 20l0 44-44 0c-11 0-20 9-20 20z"/>
                                        </svg>
                                        <p class="textQuantityProduct">Thêm vào giỏ hàng</p>
                                    </div>
                                </button>
                            </div>
                        </figure>
                    </c:forEach>
                    
                </div>
            </div>
            <div class="load-more-container">
                <button class="load-more-btn">
                    Xem thêm 25 sản phẩm 
                    <span class="highlight">Giá rẻ hôm nay</span> 
                    <svg xmlns="http://www.w3.org/2000/svg" class="dropdown-icon" viewBox="0 0 320 512">
                    <path d="M143 352.3c-4.5-4.6-6.6-10.7-6.6-16.8s2.1-12.2 6.6-16.8L273.5 183c9.3-9.2 24.5-9.2 33.8 0 9.3 9.2 9.3 24.2 0 33.3L176.8 336l130.5 119.7c9.3 9.2 9.3 24.2 0 33.3-9.3 9.2-24.5 9.2-33.8 0L143 352.3z"/>
                    </svg>
                </button>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            var contextPath = '${pageContext.request.contextPath}';
        </script>
        <script src="${pageContext.request.contextPath}/Component/JS/productDisplay.js"></script>
    </body>
</html>
