<%-- 
    Document   : register
    Created on : Oct 16, 2024, 8:39:30 AM
    Author     : Yin Kenna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/register.css">
        <title>Đăng ký</title>
    </head>
    <body>
        <div class="register-container">
            <div class="register-header">
                <a href="${pageContext.request.contextPath}/Home" class="back-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-back.svg" alt="Back">
                </a>
                <h1>Daily Essentials</h1>
                <a href="${pageContext.request.contextPath}/Home" class="home-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-home.svg" alt="Home">
                </a>
            </div>
            <h2 class="register-title">Đăng ký</h2>
            <form action="register" method="POST" class="register-form">
                <label for="username">Tài khoản</label>
                <input type="text" id="username" name="username" tabindex="1" placeholder="Nhập tài khoản">

                <label for="fullname">Họ và tên*</label>
                <input type="text" id="fullname" name="fullname" tabindex="2" placeholder="Nhập họ và tên">

                <label for="phone">Số điện thoại*</label>
                <input type="tel" id="phone" name="phone" tabindex="3" placeholder="Nhập số điện thoại">
                <label for="password">Mật khẩu*</label>
                <div class="password-wrapper">
                    <input type="password" id="password" name="password" tabindex="4" placeholder="Nhập mật khẩu">
                    <span class="show-password">
                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-eye.svg" alt="Show/Hide Password">
                    </span>
                </div>

                <label for="confirm-password">Xác nhận mật khẩu*</label>
                <input type="password" id="confirm-password" name="confirm-password" tabindex="5" placeholder="Xác nhận mật khẩu">
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



                <button type="submit" class="register-button" tabindex="6">Đăng ký</button>
                <div class="or-divider">
                    <hr><span>Hoặc</span><hr>
                </div>
                <a href="Login" class="login-button">Đăng nhập</a>
            </form>
        </div>
        <jsp:include page="noti.jsp"></jsp:include>

        <script>
            function togglePasswordVisibility() {
                const passwordField = document.getElementById("password");
                const passwordFieldType = passwordField.getAttribute("type");

                if (passwordFieldType === "password") {
                    passwordField.setAttribute("type", "text");
                } else {
                    passwordField.setAttribute("type", "password");
                }
            }

            document.querySelector('.show-password').addEventListener('click', togglePasswordVisibility);
        </script>
    </body>
</html>
