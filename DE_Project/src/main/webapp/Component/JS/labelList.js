/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// Điền dữ liệu vào form cập nhật
function populateForm(labelId, categoryName, labelName) {
    document.getElementById("updateLabelId").value = labelId;
    document.getElementById("updateCategoryName").value = categoryName;
    document.getElementById("updateLabelName").value = labelName;
}

// Mở modal thêm nhãn
function openAddModal() {
    document.getElementById("addLabelName").value = "";
    document.getElementById("addCategoryName").value = "";
    document.getElementById("addLabelModal").style.display = "block";
}

// Đóng modal
function closeModal() {
    document.getElementById("addLabelModal").style.display = "none";
}

// Đóng modal khi nhấn bên ngoài
window.onclick = function (event) {
    const modal = document.getElementById("addLabelModal");
    if (event.target === modal) {
        closeModal();
    }
}

// Thiết lập các sự kiện khi trang tải xong
document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll(".label-data-row");
    const addLabelButton = document.getElementById("addLabelButton");
    const closeModalButton = document.querySelector(".label-close-btn");

    // Gán sự kiện điền dữ liệu vào form khi nhấn dòng trong bảng
    rows.forEach(row => {
        row.addEventListener("click", function () {
            const labelId = this.getAttribute("data-label-id");
            const categoryName = this.getAttribute("data-category-name");
            const labelName = this.getAttribute("data-label-name");
            populateForm(labelId, categoryName, labelName);
        });
    });

    // Mở modal thêm nhãn khi nhấn nút "Thêm nhãn"
    if (addLabelButton) {
        addLabelButton.addEventListener("click", openAddModal);
    }

    // Đóng modal khi nhấn nút đóng (×)
    if (closeModalButton) {
        closeModalButton.addEventListener("click", closeModal);
    }
});

// Dynamically filter labels by category
function filterLabels() {
    const category = document.getElementById("categoryFilter").value;
    const xhr = new XMLHttpRequest();
    xhr.open("GET", `${contextPath}/DEHome/Manage-Products/manageLabels?categoryName=${category}`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            document.getElementById("labelDropdown").innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}
function deleteCategory() {
    const categoryId = document.getElementById("updateCategoryId").value; // Sử dụng updateCategoryId cho Category

    if (!categoryId) {
        alert("No category selected to delete.");
        return;
    }

    if (confirm("Are you sure you want to delete this category?")) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", `${contextPath}/DEHome/Manage-Products/manageLabels`, true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

        xhr.onload = function () {
            if (xhr.status === 200 && xhr.responseText === "success") {
                alert("Category deleted successfully.");
                location.reload(); // Reload lại trang để cập nhật
            } else {
                alert("Failed to delete category.");
            }
        };

        xhr.onerror = function () {
            console.error("Request failed.");
        };

        // Gửi yêu cầu xóa với action là "delete" và categoryId
        xhr.send(`action=delete&categoryId=${encodeURIComponent(categoryId)}`);
    }
}

