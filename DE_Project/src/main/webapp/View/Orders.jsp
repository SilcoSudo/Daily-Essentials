<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/Component/CSS/navbar.css"
            />
        <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/Component/CSS/Orders.css"
            />

        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container">
            <div class="w-orders">
                <div class="products-list">
                    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

                    <c:forEach var="product" items="${cartProducts}">
                        <figure>
                            <div class="product-info">
                                <img
                                    src="${pageContext.request.contextPath}/${product.imageUrl}"
                                    class="product-img"
                                    alt="${product.productName}"
                                    />
                                <p class="product-name">${product.productName}</p>
                            </div>
                            <div class="product-price">${product.productPrice}</div>
                            <div class="product-quantity">
                                <img
                                    src="${pageContext.request.contextPath}/Component/IMG/ic-minus.svg"
                                    alt="Decrease quantity"
                                    class="icon decrease-btn"
                                    data-product-id="${product.productId}"
                                    />
                                <p class="number" id="quantity-${product.productId}">${product.quantityInCart}</p>
                                <img
                                    src="${pageContext.request.contextPath}/Component/IMG/ic-plus.svg"
                                    alt="Increase quantity"
                                    class="icon increase-btn"
                                    data-product-id="${product.productId}"
                                    />
                            </div>
                        </figure>
                    </c:forEach>

                    <button class="btn" id="btnDeleteCart">Xóa giỏ hàng</button>
                </div>
                <div class="w-payment">
                    <div class="location">
                        <svg
                            class="icon"
                            style="height: 45px !important"
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 384 512"
                            >
                        <path
                            d="M215.7 499.2C267 435 384 279.4 384 192C384 86 298 0 192 0S0 86 0 192c0 87.4 117 243 168.3 307.2c12.3 15.3 35.1 15.3 47.4 0zM192 128a64 64 0 1 1 0 128 64 64 0 1 1 0-128z"
                            />
                        </svg>
                        <div class="location-text">
                            <strong class="location-title">Chọn Khu vực</strong>
                            <div class="location-address">
                                P. Long Tuyền, Q. Bình Thủy, TP. Cần Thơ
                            </div>
                        </div>
                    </div>
                    <div class="diviser"></div>
                    <div class="payment-info">
                        <div class="row">
                            <div class="title">Tạm tính giỏ hàng:</div>
                            <div class="money">53.900 ₫</div>
                        </div>
                        <div class="row">
                            <div class="title">Phí vận chuyển:</div>
                            <div class="money">53.900 ₫</div>
                        </div>
                        <div class="row">
                            <div class="title">Thành tiền:</div>
                            <div class="money">10.153.900 ₫</div>
                        </div>
                        <div class="discount">
                            Mua thêm để miễn phí giao hàng từ 300.000 ₫
                        </div>
                        <button class="btnPayment btn">
                            <p>Thanh toán<br />80.900 ₫</p>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            var contextPath = '${pageContext.request.contextPath}';
        </script>
        <script src="${pageContext.request.contextPath}/Component/JS/productDisplay.js"></script>    
        <script>
            
        </script>
    </body>
</html>
