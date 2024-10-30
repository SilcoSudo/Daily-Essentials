$(document).ready(function () {
  $(".register-form").on("submit", function (event) {
    event.preventDefault();

    const username = $("#username").val().trim();
    const fullname = $("#fullname").val().trim();
    const email = $("#email").val().trim();
    const phone = $("#phone").val().trim();
    const password = $("#password").val().trim();
    const confirmPassword = $("#confirm-password").val().trim();

    if (username === "") {
      alert("Vui lòng nhập tài khoản.");
      return;
    }
    if (!/^[a-zA-Z0-9@-_]+$/.test(username)) {
      alert("Chỉ chấp nhận 1 số ký tự đặc biệt như @, -, _");
      return;
    }
    if (username.length < 6 || username.length > 25) {
      alert("Nhập tên tài khoản dài trong 6 đến 25 ký tự");
      return;
    }

    if (fullname === "") {
      alert("Vui lòng nhập họ và tên.");
      return;
    }
    if (
      !/^[\p{L}\s]+$/u.test(fullname) ||
      fullname.split(" ").length < 2 ||
      fullname.split(" ").length > 7
    ) {
      alert("Tên của bạn không hợp lệ, hãy nhập lại");
      return;
    }

    if (/\d{2,}/.test(fullname)) {
      alert("Tên của bạn không hợp lệ, hãy nhập lại");
      return;
    }
    if (fullname.split(" ").some((name) => name.length < 3)) {
      alert("Tên của bạn không hợp lệ, hãy nhập lại");
      return;
    }

    if (email === "") {
      alert("Vui lòng nhập địa chỉ email.");
      return;
    }
    if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
      alert("Email của bạn không hợp lệ, hãy nhập lại");
      return;
    }

    if (phone === "") {
      alert("Vui lòng nhập số điện thoại.");
      return;
    }
    if (!/^\d{9,11}$/.test(phone)) {
      alert("Số điện thoại phải có từ 9 đến 11 chữ số.");
      return;
    }

    if (password === "") {
      alert("Mật khẩu của bạn không được để trống hoặc khoảng cách");
      return;
    }
    if (/\s/.test(password)) {
      alert("Mật khẩu của bạn không được chứa khoảng cách");
      return;
    }
    if (password.length < 6 || password.length > 32) {
      alert("Mật khẩu phải có độ dài từ 6 đến 32 ký tự.");
      return;
    }

    if (confirmPassword === "") {
      alert("Vui lòng xác nhận mật khẩu.");
      return;
    }
    if (password !== confirmPassword) {
      alert("Mật khẩu của bạn không trùng với bạn đã nhập");
      return;
    }

    // Nếu không có lỗi, gửi form qua Ajax
    const formData = $(this).serialize();

    $.ajax({
      url: "Register",
      type: "POST",
      data: formData,
      dataType: "json",
      success: function (response) {
          window.location.href = `${contextPath}/Authen/Login`;
      },
      error: function (response) {
        alert(response.responseText);
      },
    });
  });
});
