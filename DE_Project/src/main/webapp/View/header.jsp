<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <li><a href="#">Giá rẻ</a></li>
                        <li><a href="#">Sữa các loại</a></li>
                        <li><a href="#">Rau - Củ - Trái cây</a></li>
                        <li><a href="#">Hóa phẩm - Tẩy rửa</a></li>
                        <li><a href="#">Chăm sóc cá nhân</a></li>
                        <li><a href="#">Thịt - Hải sản tươi</a></li>
                        <li><a href="#">Đồ uống có cồn</a></li>
                        <li><a href="#">Đồ uống - Giải khát</a></li>
                        <li><a href="#">Mì - Thực phẩm ăn liền</a></li>
                        <li><a href="#">Thực phẩm khô</a></li>
                        <li><a href="#">Thực phẩm chế biến</a></li>
                    </ul>
                </div>
            </div>

            <div class="product-menu-right">
                <button class="purchase-btn">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-cart2.svg" class="icon ic-cart2" alt="Cart Icon">
                    Sản phẩm đã mua
                </button>
            </div>
        </div>
    </section>
</header>
