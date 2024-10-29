<%@page contentType="text/html" pageEncoding="UTF-8"%>

<link
  rel="stylesheet"
  href="${pageContext.request.contextPath}/Component/CSS/headers.css"
/>
<link
  rel="stylesheet"
  href="${pageContext.request.contextPath}/Component/CSS/mainStyle.css"
/>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<div class="slidebar">
  <div class="w-slidebar">
    <a href="${pageContext.request.contextPath}/DEHome" class="logo"
      >Daily Essentials</a
    >
    <ul class="slidebar-list-icon">
      <li
        class="slidebar-icon"
        data-url="${pageContext.request.contextPath}/DEHome"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="icon"
          viewBox="0 0 576 512"
        >
          <path
            d="M575.8 255.5c0 18-15 32.1-32 32.1l-32 0 .7 160.2c0 2.7-.2 5.4-.5 8.1l0 16.2c0 22.1-17.9 40-40 40l-16 0c-1.1 0-2.2 0-3.3-.1c-1.4 .1-2.8 .1-4.2 .1L416 512l-24 0c-22.1 0-40-17.9-40-40l0-24 0-64c0-17.7-14.3-32-32-32l-64 0c-17.7 0-32 14.3-32 32l0 64 0 24c0 22.1-17.9 40-40 40l-24 0-31.9 0c-1.5 0-3-.1-4.5-.2c-1.2 .1-2.4 .2-3.6 .2l-16 0c-22.1 0-40-17.9-40-40l0-112c0-.9 0-1.9 .1-2.8l0-69.7-32 0c-18 0-32-14-32-32.1c0-9 3-17 10-24L266.4 8c7-7 15-8 22-8s15 2 21 7L564.8 231.5c8 7 12 15 11 24z"
          />
        </svg>
        <span>Trang chủ</span>
      </li>
      <li
        class="slidebar-icon"
        data-url="${pageContext.request.contextPath}/DEHome/Manage-Account"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="icon"
          viewBox="0 0 576 512"
        >
          <path
            d="M528 160l0 256c0 8.8-7.2 16-16 16l-192 0c0-44.2-35.8-80-80-80l-64 0c-44.2 0-80 35.8-80 80l-32 0c-8.8 0-16-7.2-16-16l0-256 480 0zM64 32C28.7 32 0 60.7 0 96L0 416c0 35.3 28.7 64 64 64l448 0c35.3 0 64-28.7 64-64l0-320c0-35.3-28.7-64-64-64L64 32zM272 256a64 64 0 1 0 -128 0 64 64 0 1 0 128 0zm104-48c-13.3 0-24 10.7-24 24s10.7 24 24 24l80 0c13.3 0 24-10.7 24-24s-10.7-24-24-24l-80 0zm0 96c-13.3 0-24 10.7-24 24s10.7 24 24 24l80 0c13.3 0 24-10.7 24-24s-10.7-24-24-24l-80 0z"
          />
        </svg>
        <span>Quản lý tài khoản</span>
      </li>
      <li
        class="slidebar-icon"
        data-url="${pageContext.request.contextPath}/DEHome/Manage-Products"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="icon"
          viewBox="0 0 640 512"
        >
          <path
            d="M58.9 42.1c3-6.1 9.6-9.6 16.3-8.7L320 64 564.8 33.4c6.7-.8 13.3 2.7 16.3 8.7l41.7 83.4c9 17.9-.6 39.6-19.8 45.1L439.6 217.3c-13.9 4-28.8-1.9-36.2-14.3L320 64 236.6 203c-7.4 12.4-22.3 18.3-36.2 14.3L37.1 170.6c-19.3-5.5-28.8-27.2-19.8-45.1L58.9 42.1zM321.1 128l54.9 91.4c14.9 24.8 44.6 36.6 72.5 28.6L576 211.6l0 167c0 22-15 41.2-36.4 46.6l-204.1 51c-10.2 2.6-20.9 2.6-31 0l-204.1-51C79 419.7 64 400.5 64 378.5l0-167L191.6 248c27.8 8 57.6-3.8 72.5-28.6L318.9 128l2.2 0z"
          />
        </svg>
        <span>Quản lý hàng hóa</span>
      </li>
      <li
        class="slidebar-icon"
        data-url="${pageContext.request.contextPath}/DEHome/Manage-Orders"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="icon"
          viewBox="0 0 384 512"
        >
          <path
            d="M64 464c-8.8 0-16-7.2-16-16L48 64c0-8.8 7.2-16 16-16l160 0 0 80c0 17.7 14.3 32 32 32l80 0 0 288c0 8.8-7.2 16-16 16L64 464zM64 0C28.7 0 0 28.7 0 64L0 448c0 35.3 28.7 64 64 64l256 0c35.3 0 64-28.7 64-64l0-293.5c0-17-6.7-33.3-18.7-45.3L274.7 18.7C262.7 6.7 246.5 0 229.5 0L64 0zm56 256c-13.3 0-24 10.7-24 24s10.7 24 24 24l144 0c13.3 0 24-10.7 24-24s-10.7-24-24-24l-144 0zm0 96c-13.3 0-24 10.7-24 24s10.7 24 24 24l144 0c13.3 0 24-10.7 24-24s-10.7-24-24-24l-144 0z"
          />
        </svg>
        <span>Quản lý đơn hàng</span>
      </li>
    </ul>

    <a class="logout" href="${pageContext.request.contextPath}/Authen/Logout"
      ><img
        src="${pageContext.request.contextPath}/Component/IMG/logout-icon.png"
        alt="Logout Icon"
      />
      <span>Đăng xuất</span></a
    >
  </div>
</div>
<script>
  document.addEventListener("DOMContentLoaded", () => {
    const slidebarIcons = document.querySelectorAll(".slidebar-icon");

    const activeItemIndex = localStorage.getItem("activeSlidebarIndex");
    if (activeItemIndex !== null) {
      slidebarIcons[activeItemIndex].classList.add("active");
    }

    slidebarIcons.forEach((icon, index) => {
      icon.addEventListener("click", () => {
        slidebarIcons.forEach((item) => item.classList.remove("active"));
        icon.classList.add("active");

        localStorage.setItem("activeSlidebarIndex", index);
        const url = icon.getAttribute("data-url");
        if (url) {
          window.location.href = url;
        }
      });
    });
  });
</script>
