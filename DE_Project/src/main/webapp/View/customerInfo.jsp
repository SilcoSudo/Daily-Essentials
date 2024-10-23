<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/navbar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/customerInfo.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/PU_Select_area.css" />
        <title>Account Information</title>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
            <div class="container">
                <div class="side-menu">
                    <div class="side-menu-item">
                        <img src="${pageContext.request.contextPath}/Component/IMG/account-icon.png" alt="User Icon">
                    <a href="customerInfo.html">Tài khoản</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/order-icon.png" alt="Order Icon">
                    <a href="${pageContext.request.contextPath}/CustomerViewOrder">Quản lý đơn hàng</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/product-icon.png" alt="Product Icon">
                    <a href="productDetail.html">Sản phẩm đã mua</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/logout-icon.png" alt="Logout Icon">
                    <a href="${pageContext.request.contextPath}/Authen/Logout">Đăng xuất</a>
                </div>
            </div>
            <div class="main-content">
                <h2>Thông tin tài khoản</h2>
                <form action="#" method="POST">
                    <label for="name">Họ và tên *</label>
                    <input type="text" id="name" name="name" value="Nguyễn Quốc Khang" required>

                    <label for="phone">Số điện thoại</label>
                    <input type="text" id="phone" name="phone" value="8413136117">

                    <label for="email">Email *</label>
                    <input type="email" id="email" name="email" value="yinkennalt@gmail.com" required>
                    <div class="gender">
                        <label>Giới tính *</label>
                        <label class="custom-radio">
                            <input type="radio" name="gender" value="male" checked>
                            <span class="radio-btn">Nam</span> 
                        </label>
                        <label class="custom-radio">
                            <input type="radio" name="gender" value="female">
                            <span class="radio-btn">Nữ</span>
                        </label>
                    </div>
                    <label for="dob">Ngày sinh</label>
                    <input type="date" id="dob" name="dob" value="2004-10-01">
                    
                    <label for="tinh">Tỉnh/ Thành phố</label>
                    <select id="tinh" name="tinh">
                        <option value="0">Tỉnh thành</option>
                    </select>

                    <label for="quan">Quận/ Huyện</label>
                    <select id="quan" name="quan">
                        <option value="0">Quận Huyện</option>
                    </select>

                    <label for="phuong">Phường/ Xã</label>
                    <select id="phuong" name="phuong">
                        <option value="0">Phường Xã</option>
                    </select>

                    <label for="address">Địa chỉ nhà chi tiết</label>
                    <input type="text" id="address" name="address" value="194b Nhà trọ !!!">

                    <button type="submit" class="update-button">Cập nhật</button>
                </form>
            </div>
        </div>
        <script src="https://esgoo.net/scripts/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/Component/JS/Select_area.js"></script>
    </body>
</html>
