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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <title>Đăng ký</title>
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
                <button type="submit" class="register-button" tabindex="6" disabled>Đăng ký</button>
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
            
            function validateField(field) {
                let isValid = true;
                let errorMessage = "";

                const username = $('#username').val().trim();
                const fullname = $('#fullname').val().trim();
                const phone = $('#phone').val().trim();
                const password = $('#password').val().trim();
                const confirmPassword = $('#confirm-password').val().trim();

                if (field === 'username' && username.length <= 6) {
                    isValid = false;
                    errorMessage += "Tên tài khoản phải lớn hơn 6 ký tự.<br>";
                }

                if (field === 'fullname' && fullname.length === 0) {
                    isValid = false;
                    errorMessage += "Họ và tên không được để trống.<br>";
                }

                const phonePattern = /^[0-9]{10,}$/;
                if (field === 'phone' && !phonePattern.test(phone)) {
                    isValid = false;
                    errorMessage += "Số điện thoại phải có ít nhất 10 chữ số.<br>";
                }

                if (field === 'password' && password.length <= 6) {
                    isValid = false;
                    errorMessage += "Mật khẩu phải lớn hơn 6 ký tự.<br>";
                }

                if (field === 'confirm-password' && password !== confirmPassword) {
                    isValid = false;
                    errorMessage += "Xác nhận mật khẩu không khớp.<br>";
                }

                if (!isValid) {
                    $('.register-button').prop('disabled', true);
                    $('.text.text-2').html(errorMessage);
                    $('.toast').addClass('active');
                    setTimeout(() => {
                        $('.toast').removeClass('active');
                    }, 3000);
                } else {
                    $('.register-button').prop('disabled', false);
                    $('.text.text-2').html("");
                    $('.toast').removeClass('active');
                }

                return isValid;
            }

            $('#username, #fullname, #phone, #password, #confirm-password').on('blur', function () {
                const fieldId = $(this).attr('id');
                validateField(fieldId);
            });

        </script>
    </body>
</html>
