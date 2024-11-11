<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="icon" href="${pageContext.request.contextPath}/Component/IMG/IMG_Product_view/Product_noneImage-48x48.png" type="image/x-icon">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/navbar.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/Orders.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/notfi.css">
        <title>Order</title>
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="container">
            <div class="w-orders">
                <div class="products-list">
                    <c:forEach var="product" items="${cartProducts}">
                        <figure data-product-id="${product.productId}">
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
                                <input type="text" class="number" id="quantity-${product.productId}" data-product-quantity="${product.productQuantity}" value="${product.quantityInCart}" id="quantity" />
                                <img
                                    src="${pageContext.request.contextPath}/Component/IMG/ic-plus.svg"
                                    alt="Increase quantity"
                                    class="icon increase-btn"
                                    data-product-id="${product.productId}"
                                    />
                            </div>
                            <div class="btnRemoveItem" data-product-id="${product.productId}">Remove</div>
                        </figure>
                    </c:forEach>
                    <button class="btn" id="btnDeleteCart">Remove cart</button>
                </div>
                <div class="w-payment">
                    <div class="payment-info">
                        <div class="row">
                            <div class="title">Total amount:</div>
                            <div class="money product-price" id="totalAmount">${sessionScope.totalAmounts} ₫</div>
                        </div>
                        <button class="btnPayment btn" id="btnPay" style="display: grid;
                                justify-content: center;
                                align-items: center;">
                            Pay<br/><p class="product-price" style="justify-content: center;">${sessionScope.totalAmounts} ₫</p>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="noti.jsp"></jsp:include>
    <script src="${pageContext.request.contextPath}/Component/JS/productDisplay_Order.js"></script>    
    <script>
        if ($(".products-list figure").length === 0) {
            $("#btnDeleteCart").remove();
        }
    </script>
</body>
</html>
