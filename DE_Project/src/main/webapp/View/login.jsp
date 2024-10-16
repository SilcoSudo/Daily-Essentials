
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/login.css">
        <title>Đăng nhập</title>
    </head>
    <body>
        <div class="login-container">
            <div class="login-header">
                <a href="homeCustomer.html" class="back-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-back.svg" alt="Back">
                </a>
                <h1>Daily Essentials</h1>
                <a href="homeCustomer.html" class="home-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-home.svg" alt="Home">
                </a>
            </div>
            <h2 class="login-title">Đăng nhập</h2>
            <form action="Login" method="POST" class="login-form">
                <label for="username">Tên tài khoản</label>
                <input type="text" id="username" name="username" tabindex="1" placeholder="Nhập tài khoản">

                <label for="password">Mật khẩu</label>
                <div class="password-wrapper">
                    <input type="password" id="password" name="password" tabindex="2" placeholder="Nhập mật khẩu" >
                    <span class="show-password">
                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-eye.svg" alt="Show/Hide Password">
                    </span>
                </div>
                <div class="forgot-password">
                    <a href="#">Quên mật khẩu?</a>
                </div>
                <button type="submit" class="login-button" tabindex="3">Đăng nhập</button>
                <div class="or-divider">
                    <hr><span>Hoặc</span><hr>
                </div>
                <a href="Register" class="register-button">Đăng ký</a>
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
