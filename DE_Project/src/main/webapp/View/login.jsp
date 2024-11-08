
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="${pageContext.request.contextPath}/Component/IMG/IMG_Product_view/Product_noneImage-48x48.png" type="image/x-icon">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/login.css">
        <title>Login</title>
    </head>
    <body>
        <div class="login-container">
            <div class="login-header">
                <a href="${pageContext.request.contextPath}/Home" class="back-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-back.svg" alt="Back">
                </a>
                <h1>Daily Essentials</h1>
                <a href="${pageContext.request.contextPath}/Home" class="home-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-home.svg" alt="Home">
                </a>
            </div>
            <h2 class="login-title">Login page</h2>
            <form action="Login" method="POST" class="login-form">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" tabindex="1" placeholder="Enter your username">

                <label for="password">Password</label>
                <div class="password-wrapper">
                    <input type="password" id="password" name="password" tabindex="2" placeholder="Enter password" >
                    <span class="show-password">
                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-eye.svg" alt="Show/Hide Password">
                    </span>
                </div>
                <div class="forgot-password">
                    <a href="${pageContext.request.contextPath}/Authen/ForgotPassword">Forgot password?</a>
                </div>
                <button type="submit" class="login-button" tabindex="3">Login</button>
                <div class="or-divider">
                    <hr><span>Or</span><hr>
                </div>
                <a href="${pageContext.request.contextPath}/Authen/Register" class="register-button">Register</a>
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
