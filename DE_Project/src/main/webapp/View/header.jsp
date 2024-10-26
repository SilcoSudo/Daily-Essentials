<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    var contextPath = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/Component/JS/category.js"></script>
<header>
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
                <a href="${pageContext.request.contextPath}/Orders" class="cart-link">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-cart.svg" alt="Cart Icon" class="icon">
                    <span>Giỏ hàng (<span id="cart-count">${sessionScope.totalCartItems != null ? sessionScope.totalCartItems : 0}</span>)</span>
                </a>
                <a href="${pageContext.request.contextPath}/Home/Info" class="user-link">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-user.svg" alt="User Icon" class="icon">
                    <span>${sessionScope.userFullName != null ? sessionScope.userFullName : 'Khách hàng'}</span>
                </a>
            </div>
        </div>
    </nav>
    <section class="product-wrapper">
        <div class="product-menu-wrapper">
            <div class="product-menu-left">
                <div class="dropdown">
                    <button class="menu-btn">
                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-bars.svg" class="icon ic-bars" alt="Bars Icon"> Danh mục sản phẩm
                    </button>
                    <ul class="dropdown_content">
                        <c:forEach items="${fullLabel}" var="label_item">
                            <li class="category_item" id="${label_item.labelId}">${label_item.categoryName}</li>
                            </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </section>
</header>
