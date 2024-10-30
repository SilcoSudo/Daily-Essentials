<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/navbar.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/Orders.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/notfi.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/PU_Select_area.css" />
        <title>Giỏ Hàng</title>
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
                    <div class="location" id="btnLocation">
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
                                ${sessionScope.location}
                            </div>
                        </div>
                    </div>
                    <div class="diviser"></div>
                    <div class="payment-info">
                        <div class="row">
                            <div class="title">Tạm tính giỏ hàng:</div>
                            <div class="money product-price" id="provisional">${sessionScope.provisionals} ₫</div>
                        </div>
                        <div class="row">
                            <div class="title">Phí vận chuyển:</div>
                            <div class="money product-price" id="feeShip">${sessionScope.feeShips} ₫</div>
                        </div>
                        <div class="row">
                            <div class="title">Thành tiền:</div>
                            <div class="money product-price" id="totalAmount">${sessionScope.totalAmounts} ₫</div>
                        </div>
                        <div class="discount">
                            Hóa đơn từ 300.000 đ sẽ giảm 40% phí giao hàng
                        </div>
                        <button class="btnPayment btn" id="btnPay" style="display: grid;
                                justify-content: center;
                                align-items: center;">
                            Thanh toán<br/><p class="product-price" style="justify-content: center;">${sessionScope.totalAmounts} ₫</p>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="popup-container hidden" id="popup">
            <div class="popup">
                <div class="popup-header">
                    <h2>Khu vực giao hàng dự kiến</h2>
                    <svg
                        xmlns="http://www.w3.org/2000/svg"
                        class="icon btnExit"
                        id="exit"
                        viewBox="0 0 512 512"
                        >
                    <path
                        d="M256 48a208 208 0 1 1 0 416 208 208 0 1 1 0-416zm0 464A256 256 0 1 0 256 0a256 256 0 1 0 0 512zM175 175c-9.4 9.4-9.4 24.6 0 33.9l47 47-47 47c-9.4 9.4-9.4 24.6 0 33.9s24.6 9.4 33.9 0l47-47 47 47c9.4 9.4 24.6 9.4 33.9 0s9.4-24.6 0-33.9l-47-47 47-47c9.4-9.4 9.4-24.6 0-33.9s-24.6-9.4-33.9 0l-47 47-47-47c-9.4-9.4-24.6-9.4-33.9 0z"
                        />
                    </svg>
                </div>
                <div class="popup-body" id="sa1">
                    <div class="form-group">
                        <label for="city p-10">Thành phố</label>
                        <select id="tinh" name="tinh">
                            <option value="0">Tỉnh thành</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="district p-10">Quận Huyện</label>
                        <select id="quan" name="quan">
                            <option value="0">Quận Huyện</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="ward p-10">Phường Xã</label>
                        <select id="phuong" name="phuong">
                            <option value="0">Phường Xã</option>
                        </select>
                    </div>
                    <button class="continue-btn" id="btnContinue">Tiếp tục</button>
                </div>
                <div class="popup-body hidden" id="sa2">
                    <div class="popup-body-address">
                        <p id="address-sa2-comfirm">

                        </p>
                        <div id="change-address" class="ic-Change p-10">
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                class="icon"
                                viewBox="0 0 512 512"
                                >
                            <path
                                d="M362.7 19.3L314.3 67.7 444.3 197.7l48.4-48.4c25-25 25-65.5 0-90.5L453.3 19.3c-25-25-65.5-25-90.5 0zm-71 71L58.6 323.5c-10.4 10.4-18 23.3-22.2 37.4L1 481.2C-1.5 489.7 .8 498.8 7 505s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L421.7 220.3 291.7 90.3z"
                                />
                            </svg>
                            <p>Thay đổi</p>
                        </div>
                    </div>
                    <button class="continue-btn" id="btnConfirm">Xác nhận</button>
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
    <script src="${pageContext.request.contextPath}/Component/JS/Select_area.js"></script>
    <script src="${pageContext.request.contextPath}/Component/JS/popup.js"></script>
</body>
</html>
