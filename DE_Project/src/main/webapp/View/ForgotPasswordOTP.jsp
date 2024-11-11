<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/login.css" />
        <link rel="icon" href="${pageContext.request.contextPath}/Component/IMG/IMG_Product_view/Product_noneImage-48x48.png" type="image/x-icon">

        <title>Change password</title>
    </head>
    <body>
        <div class="login-container">
            <div class="login-header">
                <a href="${pageContext.request.contextPath}/Authen/Login" class="back-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-back.svg" alt="Back" />
                </a>
                <h1>Daily Essentials</h1>
                <a href="${pageContext.request.contextPath}/Home" class="home-button">
                    <img src="${pageContext.request.contextPath}/Component/IMG/ic-home.svg" alt="Home" />
                </a>
            </div>
            <h2 class="login-title">Forgot Password</h2>
            <form action="forgot_otp" method="POST" class="login-form">
                <label for="otp">Enter OTP</label>
                <input type="hidden" name="email" value="${email}"/>
                <input type="text" id="otp" name="otp" tabindex="1" placeholder="Enter OTP" />
                <button type="submit" class="btnAuthen" tabindex="2">Continue</button>
            </form>

            <!-- Countdown and Resend Button -->
            <div class="otp-timer">
                <!-- Initially hidden text -->
                <p id="countdownText" style="display: none;">You can request the code again later. <span id="countdown">30</span> s</p>
                <button id="resendOtp">Resend OTP code</button>
            </div>
        </div>

        <jsp:include page="noti.jsp"></jsp:include>

            <script>
                function startCountdown() {
                    let countdownElement = document.getElementById("countdown");
                    let countdownText = document.getElementById("countdownText");
                    let resendOtpButton = document.getElementById("resendOtp");
                    let timeLeft = 30;

                    resendOtpButton.disabled = true; // Disable the resend button while counting
                    countdownText.style.display = 'block'; // Show countdown text

                    let countdownInterval = setInterval(function () {
                        timeLeft--;
                        countdownElement.textContent = timeLeft;

                        if (timeLeft <= 0) {
                            clearInterval(countdownInterval); // Stop the countdown
                            resendOtpButton.disabled = false;  // Enable the button after countdown
                            countdownText.style.display = 'none'; // Hide the countdown text after it ends
                        }
                    }, 1000); // Decrease every second
                }

                // Handle OTP resend button click
                document.getElementById("resendOtp").addEventListener("click", function () {

                    // Perform AJAX request to servlet
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "${pageContext.request.contextPath}/Authen/forgot_otp-2", true);
                    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                    // Handle response from server
                    xhr.onload = function () {
                        if (xhr.status === 200) {
                            var response = JSON.parse(xhr.responseText);
                            if (response.status === "success") {
                                startCountdown();
                            }
                        } else {
                            alert("Đã xảy ra lỗi khi gửi yêu cầu. Vui lòng thử lại.");
                        }
                    };

                    xhr.send();
                });
        </script>
    </body>
</html>
