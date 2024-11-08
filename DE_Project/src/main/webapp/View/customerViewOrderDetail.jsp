<%-- 
    Document   : customerViewOrderDetail
    Created on : Oct 27, 2024, 7:42:02 PM
    Author     : nhatl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chi tiết Đơn hàng</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/customerViewOrder.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/customerInfo.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/navbar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/staffViewOrderDetail.css">
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>

            <div class="container">
                <div class="side-menu">
                    <div class="side-menu-item">
                        <img src="${pageContext.request.contextPath}/Component/IMG/account-icon.png" alt="User Icon">
                    <a href="${pageContext.request.contextPath}/Home/Info">Account</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/order-icon.png" alt="Order Icon">
                    <a href="${pageContext.request.contextPath}/CustomerViewOrder">Order Management</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/product-icon.png" alt="Product Icon">
                    <a href="${pageContext.request.contextPath}/Authen/ForgotPassword">Change password</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/logout-icon.png" alt="Logout Icon">
                    <a href="${pageContext.request.contextPath}/Authen/Logout">Log out</a>
                </div>
            </div>
            <div class="main-content">
                <div class="popup-header">
                    <h2>Order details</h2>
                </div>
                <div class="popup-body">
                    <table>
                        <tr>
                            <th>Order ID</th>
                            <td>${orderlist.order_id}</td>
                        </tr>
                        <tr>
                            <th>Order status</th>
                            <td>${orderlist.orderStatusString}</td>
                        </tr>
                        <tr>
                            <th>Date</th>
                            <td>${orderlist.order_date}</td>
                        </tr>
                        <tr>
                            <th>Total amount</th>
                            <td class="product-price">${orderlist.total_amount}</td>
                        </tr>
                    </table>

                    <br />

                    <table>
                        <thead>
                            <tr>
                                <th>Image</th>
                                <th>Product Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orderDetails}" var="detail">
                                <tr>
                                    <td><img src="${detail.productImage}" alt="Product Image" width="100" height="100"></td>
                                    <td>${detail.productName}</td>
                                    <td>${detail.quantity}</td>
                                    <td class="product-price">${detail.totalPrice}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="popup-footer">
                    <a href="${pageContext.request.contextPath}/CustomerViewOrder" class="update-button">Back</a>
                </div>
            </div>
            <script>
                function formatPrice(price) {
                    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
                }

                $(".product-price").each(function () {
                    var priceText = $(this).text().replace(" ₫", "");
                    var formattedPrice = formatPrice(priceText);
                    $(this).text(formattedPrice + " ₫");
                });
            </script>
    </body>
</html>

