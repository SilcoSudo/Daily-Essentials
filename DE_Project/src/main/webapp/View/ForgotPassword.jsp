<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/login.css">
        <title>Daily Esstentials</title>
    </head>
    <body>
        <div class="login-container">
            <div class="login-header">
                <a href="${pageContext.request.contextPath}/Authen/Login" class="back-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-back.svg" alt="Back">
                </a>
                <h1>Daily Essentials</h1>
                <a href="homeCustomer.html" class="home-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-home.svg" alt="Home">
                </a>
            </div>
            <h2 class="login-title">Quên mật khẩu</h2>
            <form action="forgot" method="POST" class="login-form">
                <label for="email">Email</label>
                <input type="text" id="email" name="email" tabindex="1" placeholder="Nhập email">
                <button type="submit" class="btnAuthen" tabindex="2">Tiếp tục</button>
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
