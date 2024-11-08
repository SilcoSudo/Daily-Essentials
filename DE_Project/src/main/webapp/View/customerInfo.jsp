<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="${pageContext.request.contextPath}/Component/IMG/IMG_Product_view/Product_noneImage-48x48.png" type="image/x-icon">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/navbar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/notfi.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/customerInfo.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/PU_Select_area.css" />
        <title>Account Information</title>
    </head>
    <body>

        <jsp:include page="header.jsp"></jsp:include>

            <div class="container">
                <div class="side-menu">
                    <div class="w-side-menu">
                        <div class="side-menu-item">
                            <img src="${pageContext.request.contextPath}/Component/IMG/account-icon.png" alt="User Icon">
                        <a href="${pageContext.request.contextPath}/Home/Info">Account information</a>
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
            </div>

            <div class="main-content">
                <h2 style="padding-bottom: 20px;">Account information</h2>
                <form action="${pageContext.request.contextPath}/Account/Update" method="POST">
                    <c:forEach var="info" items="${sessionScope.userProfiles}">
                        <label for="name">Full Name *</label>
                        <input type="text" id="name" name="name" value="${info.fullName}" required>

                        <label for="phone">Phone number</label>
                        <input type="text" id="phone" name="phone" value="${info.user_phone}" required>

                        <label for="email">Email *</label>
                        <input type="email" id="email" name="email" value="${info.user_email}" required>

                        <div class="gender">
                            <label>Gender *</label>
                            <label class="custom-radio">
                                <input type="radio" name="gender" value="male" <c:if test="${info.gender}">checked</c:if>>
                                    <span class="radio-btn">Man</span> 
                                </label>
                                <label class="custom-radio">
                                    <input type="radio" name="gender" value="female" <c:if test="${!info.gender}">checked</c:if>>
                                    <span class="radio-btn">Girl</span>
                                </label>
                            </div>

                            <label for="tinh">Province/City</label>
                            <select id="tinh" name="tinh" data-selected="${info.user_province}">
                            <option value="0">Province</option>
                        </select>

                        <label for="quan">District</label>
                        <select id="quan" name="quan" data-selected="${info.user_district}">
                            <option value="0">District</option>
                        </select>

                        <label for="phuong">Ward</label>
                        <select id="phuong" name="phuong" data-selected="${info.user_ward}">
                            <option value="0">Ward</option>
                        </select>

                        <label for="address">Detailed home address</label>
                        <input type="text" id="address" name="address" value="${info.user_address != null ? info.user_address : "Chưa có địa chỉ" }">
                    </c:forEach>

                    <button type="submit" class="update-button">Confirm update</button>
                </form>
            </div>
        </div>
        <%--<jsp:include page="noti.jsp"></jsp:include>--%>
        <script src="https://esgoo.net/scripts/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/Component/JS/Select_area2.js"></script>
            <!--<script src="${pageContext.request.contextPath}/Component/JS/popup.js"></script>-->

    </body>
</html>
