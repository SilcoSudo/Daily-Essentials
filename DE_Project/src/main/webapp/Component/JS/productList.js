/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function openModal(product) {
    document.getElementById("productId").value = product.id;
    document.getElementById("productName").value = product.name;
    document.getElementById("productDescription").value = product.description;
    document.getElementById("productPrice").value = product.price;
    document.getElementById("productQuantity").value = product.quantity;
    document.getElementById("labelName").value = product.label;
    document.getElementById("categoryName").value = product.category;
    document.getElementById("productImage").src = contextPath + "/" + product.imageUrl;

    document.getElementById("productModal").style.display = "block";
}

function closeModal() {
    document.getElementById("productModal").style.display = "none";
}
window.onclick = function (event) {
    const modal = document.querySelector('.modal');
    if (event.target === modal) {
        closeModal();
    }
}

function saveProduct() {
    try {
        const productIdElement = document.getElementById("productId"); // Kiểm tra sự tồn tại của ID sản phẩm
        if (!productIdElement) {
            console.error("Product ID element not found");
            return;
        }
        const productId = productIdElement.value; // Lấy ID sản phẩm
        const productName = document.getElementById("productName").value;
        const productDescription = document.getElementById("productDescription").value;
        const productPrice = document.getElementById("productPrice").value;
        const productQuantity = document.getElementById("productQuantity").value; // Lấy số lượng sản phẩm
        const labelName = document.getElementById("labelName").value; // Lấy tên nhãn
        const categoryName = document.getElementById("categoryName").value; // Lấy tên danh mục
        const imageUrl = document.getElementById("productImage").src;

        // Kiểm tra các giá trị trước khi gửi yêu cầu
        console.log("Product ID:", productId);
        console.log("Product Name:", productName);
        console.log("Product Description:", productDescription);
        console.log("Product Price:", productPrice);
        console.log("Product Quantity:", productQuantity);
        console.log("Label:", labelName);
        console.log("Category:", categoryName);
        console.log("Image URL:", imageUrl);

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "ProductWMController", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.onload = function () {
            console.log("Response status:", xhr.status); // Ghi lại mã trạng thái của phản hồi
            console.log("Response text:", xhr.responseText); // Ghi lại nội dung phản hồi
            if (xhr.responseText === "success") {
                alert("Product updated successfully.");
                closeModal();
                location.reload(); // Tải lại trang để xem các thay đổi
            } else {
                alert("Failed to update product.");
                console.log("Server response:", xhr.responseText); // Ghi lại phản hồi từ máy chủ
            }
        };
        xhr.onerror = function () {
            console.error("Request failed"); // Ghi log lỗi yêu cầu
        };

        // Gửi yêu cầu
        xhr.send("action=save&productId=" + productId +
                "&productName=" + encodeURIComponent(productName) +
                "&productDescription=" + encodeURIComponent(productDescription) +
                "&productPrice=" + productPrice +
                "&productQuantity=" + productQuantity +
                "&labelName=" + encodeURIComponent(labelName) +
                "&categoryName=" + encodeURIComponent(categoryName) +
                "&imageUrl=" + encodeURIComponent(imageUrl));
    } catch (error) {
        console.error("Error in saveProduct:", error); // Ghi log bất kỳ lỗi nào
    }
    closeModal();
}

function deleteProduct() {
    // Implement delete logic here
    closeModal();
}

// Add event listener to each row
document.querySelectorAll('.product-data-row').forEach(row => {
    row.addEventListener('click', function () {
        // Collect the data attributes from the clicked row
        const product = {
            id: this.getAttribute('data-id'),
            name: this.getAttribute('data-name'),
            description: this.getAttribute('data-description'),
            price: this.getAttribute('data-price'),
            quantity: this.getAttribute('data-quantity'),
            type: this.getAttribute('data-type'),
            category: this.getAttribute('data-category'),
            imageUrl: this.getAttribute('data-image-url')
        };
        openModal(product); // Open modal with product details
    });
});

// Close the modal if the user clicks outside of it
window.onclick = function (event) {
    if (event.target === document.getElementById('productModal')) {
        closeModal();
    }
};

document.querySelector(".close-button").onclick = function () {
    document.querySelector(".modal").style.display = "none";
};

function deleteProduct() {
    const productId = document.getElementById("productId").value; // Get product ID

    const xhr = new XMLHttpRequest();
    xhr.open("POST", "ProductWMController", true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.responseText === "success") {
            alert("Product deleted successfully.");
            closeModal();
            location.reload(); // Reload the page to see changes
        } else {
            alert("Failed to delete product.");
        }
    };
    xhr.send("action=delete&productId=" + productId);
}