<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/productDetail.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/navbar.css">
        <title>${sessionScope.productName}</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="container">
            <c:forEach items="${productDetail}" var="p">
                <section class="product-detail-wrapper" >
                    <div class="product-detail-left">
                        <img
                            src="${pageContext.request.contextPath}/${p.imageUrl}"
                            alt="Nutri Boost Product"
                            class="product-detail-image"
                            />
                    </div>
                    <div class="product-detail-right">
                        <h1>${p.productName}</h1>
                        <p>
                            <strong>Giá niêm yết:</strong>
                            <span class="product-price">${p.productPrice}</span>
                        </p>
                        <p><strong>Tình trạng: </strong>${p.productQuantity} sản phẩm</p>

                        <div class="quantity-control">
                            <button class="quantity-btn" id="decrement">-</button>
                            <input type="text" value="${sessionScope.productInCart}" id="quantity" readonly />
                            <button class="quantity-btn" id="increment">+</button>
                        </div>

                        <button class="btn-add-to-cart" id="btnAddToCart_details">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                class="icon"
                                viewBox="0 0 576 512"
                                >
                            <path
                                class="icon-path"
                                d="M0 24C0 10.7 10.7 0 24 0L69.5 0c22 0 41.5 12.8 50.6 32l411 0c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3l-288.5 0 5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5L488 336c13.3 0 24 10.7 24 24s-10.7 24-24 24l-288.3 0c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5L24 48C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96zM252 160c0 11 9 20 20 20l44 0 0 44c0 11 9 20 20 20s20-9 20-20l0-44 44 0c11 0 20-9 20-20s-9-20-20-20l-44 0 0-44c0-11-9-20-20-20s-20 9-20 20l0 44-44 0c-11 0-20 9-20 20z"
                                />
                            </svg>
                            <p class="textQuantityProduct">Thêm vào giỏ hàng</p>
                        </button>
                    </div>
                </section>

                <section class="product-description">
                    <h2>Mô tả</h2>
                    <p>
                        ${p.productDescription}
                    </p>
                </section>
            </c:forEach>
        </div>
        <script src="${pageContext.request.contextPath}/Component/JS/productDetail.js"></script>    
    </body>
</html>
