<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="toast ${not empty errorMessage ? 'active' : ''}">
    <div class="toast-content">
        <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 448 512">
            <path
                d="M224 0c-17.7 0-32 14.3-32 32l0 19.2C119 66 64 130.6 64 208l0 25.4c0 45.4-15.5 89.5-43.8 124.9L5.3 377c-5.8 7.2-6.9 17.1-2.9 25.4S14.8 416 24 416l400 0c9.2 0 17.6-5.3 21.6-13.6s2.9-18.2-2.9-25.4l-14.9-18.6C399.5 322.9 384 278.8 384 233.4l0-25.4c0-77.4-55-142-128-156.8L256 32c0-17.7-14.3-32-32-32zm0 96c61.9 0 112 50.1 112 112l0 25.4c0 47.9 13.9 94.6 39.7 134.6L72.3 368C98.1 328 112 281.3 112 233.4l0-25.4c0-61.9 50.1-112 112-112zm64 352l-64 0-64 0c0 17 6.7 33.3 18.7 45.3s28.3 18.7 45.3 18.7s33.3-6.7 45.3-18.7s18.7-28.3 18.7-45.3z"
                />
        </svg>
        <div class="message">
            <span class="text text-1">Thông báo</span>
            <span class="text text-2">${errorMessage}</span>
        </div>
        <svg
            xmlns="http://www.w3.org/2000/svg"
            class="icon close"
            viewBox="0 0 512 512"
            >
            <path
                d="M256 48a208 208 0 1 1 0 416 208 208 0 1 1 0-416zm0 464A256 256 0 1 0 256 0a256 256 0 1 0 0 512zM175 175c-9.4 9.4-9.4 24.6 0 33.9l47 47-47 47c-9.4 9.4-9.4 24.6 0 33.9s24.6 9.4 33.9 0l47-47 47 47c9.4 9.4 24.6 9.4 33.9 0s9.4-24.6 0-33.9l-47-47 47-47c9.4-9.4 9.4-24.6 0-33.9s-24.6-9.4-33.9 0l-47 47-47-47c-9.4-9.4-24.6-9.4-33.9 0z"
                />
        </svg>
    </div>
</div>
<script>
    const button = document.querySelector("button"),
            toast = document.querySelector(".toast"),
            closeIcon = document.querySelector(".close"),
            progress = document.querySelector(".progress");

    window.addEventListener("DOMContentLoaded", () => {
        const toast = document.querySelector(".toast");
        const progress = document.querySelector(".progress");

        if (toast.classList.contains("active")) {
            setTimeout(() => {
                toast.classList.remove("active");
                progress.classList.remove("active");
            }, 3000);
        }

        const button = document.querySelector("button");
        const closeIcon = document.querySelector(".close");

        button.addEventListener("click", () => {
            toast.classList.add("active");
            progress.classList.add("active");

            setTimeout(() => {
                toast.classList.remove("active");
                progress.classList.remove("active");
            }, 3000);
        });

        closeIcon.addEventListener("click", () => {
            toast.classList.remove("active");
            progress.classList.remove("active");
        });
    });

</script>
