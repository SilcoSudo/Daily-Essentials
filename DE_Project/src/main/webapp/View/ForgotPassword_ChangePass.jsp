<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/login.css" />
        <title>Quên mật khẩu</title>
    </head>
    <body>
        <div class="login-container">
            <div class="login-header">
                <a href="${pageContext.request.contextPath}/Authen/Login" class="back-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-back.svg" alt="Back" />
                </a>
                <h1>Daily Essentials</h1>
                <a href="homeCustomer.html" class="home-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-home.svg" alt="Home" />
                </a>
            </div>
            <h2 class="login-title">Quên mật khẩu</h2>
            <form action="forgot_changepass" method="POST" class="login-form">
                <label for="otp">Nhập mật khẩu</label>
                <input type="hidden" name="accountID" value="${accountID}"/>
                <input type="text" id="pass" name="pass" tabindex="1" placeholder="Nhập mật khẩu" />
                <button type="submit" class="btnAuthen" tabindex="2">Xác nhận</button>
            </form>
        </div>

        <jsp:include page="noti.jsp"></jsp:include>
    </body>
</html>
