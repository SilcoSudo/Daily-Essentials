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
        <link rel="icon" href="${pageContext.request.contextPath}/Component/IMG/IMG_Product_view/Product_noneImage-48x48.png" type="image/x-icon">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/register.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            var contextPath = '${pageContext.request.contextPath}';
        </script>
        <title>Register</title>
    </head>
    <body>
        <div class="register-container">
            <div class="register-header">
                <a href="${pageContext.request.contextPath}/Authen/Login" class="back-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-back.svg" alt="Back">
                </a>
                <h1>Daily Essentials</h1>
                <a href="${pageContext.request.contextPath}/Home" class="home-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-home.svg" alt="Home">
                </a>
            </div>
            <h2 class="register-title">Register page</h2>
            <form action="Register" method="POST" class="register-form">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" tabindex="1" placeholder="Enter your username">

                <label for="fullname">Full name*</label>
                <input type="text" id="fullname" name="fullname" tabindex="2" placeholder="Enter your full name">

                <label for="email">Email*</label>
                <input type="text" id="email" name="email" tabindex="4" placeholder="Enter email">

                <label for="phone">Phone number*</label>
                <input type="tel" id="phone" name="phone" tabindex="5" placeholder="Enter your phone">

                <label for="password">Password*</label>
                <div class="password-wrapper">
                    <input type="password" id="password" name="password" tabindex="6" placeholder="Enter your password">
                    <span class="show-password">
                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-eye.svg" alt="Show/Hide Password">
                    </span>
                </div>

                <label for="confirm-password">Confirm Password*</label>
                <input type="password" id="confirm-password" name="confirm-password" tabindex="7" placeholder="Confirm Password">
                <div class="gender">
                    <label>Gender*</label>
                    <label class="custom-radio">
                        <input type="radio" name="gender" value="male" checked>
                        <span class="radio-btn">Man</span> 
                    </label>
                    <label class="custom-radio">
                        <input type="radio" name="gender" value="female">
                        <span class="radio-btn">Girl</span>
                    </label>
                </div>
                <button type="submit" class="register-button" tabindex="8">Rigster</button>

            </form>
            <div class="or-divider">
                <hr><span>Hoặc</span><hr>
            </div>
            <a href="Login" class="login-button">Login</a>
        </div>
        <script src="${pageContext.request.contextPath}/Component/JS/main.js"></script>
    </body>
</html>
