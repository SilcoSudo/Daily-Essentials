// Open the modal with product details
function openModal(product) {
    document.getElementById("productId").value = product.id;
    document.getElementById("productName").value = product.name;
    document.getElementById("productDescription").value = product.description;
    document.getElementById("productPrice").value = product.price;
    document.getElementById("productQuantity").value = product.quantity;
    document.getElementById("labelName").value = product.label;
    document.getElementById("categoryName").value = product.category;
    document.getElementById("productImage").src = product.imageUrl ? contextPath + "/" + product.imageUrl : "";
    document.getElementById("productModal").style.display = "block";
}

// Close the modal
function closeModal() {
    document.getElementById("productModal").style.display = "none";
}

// Event listener for clicking outside the modal to close it
window.onclick = function (event) {
    if (event.target === document.getElementById('productModal')) {
        closeModal();
    }
};

// Save product data to server
function saveProduct() {
    try {
        const productId = document.getElementById("productId").value;
        const productName = document.getElementById("productName").value;
        const productDescription = document.getElementById("productDescription").value;
        const productPrice = document.getElementById("productPrice").value;
        const productQuantity = document.getElementById("productQuantity").value;
        const labelName = document.getElementById("labelName").value;
        const categoryName = document.getElementById("categoryName").value;
        const imageUrl = document.getElementById("productImage").src;

        // Simple validation for required fields
        if (!productName || !productDescription || !productPrice || !productQuantity) {
            alert("All fields are required!");
            return;
        }

        const xhr = new XMLHttpRequest();
        xhr.open("POST", `${contextPath}/DEHome/Manage-Products/update`, true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.onload = function () {
            if (xhr.status === 200 && xhr.responseText === "success") {
                alert("Product updated successfully.");
                closeModal();
                location.reload();  // Reload the page to view changes
            } else {
                alert("Failed to update product.");
                console.error("Error:", xhr.responseText);
            }
        };
        xhr.onerror = function () {
            console.error("Request failed");
        };

        xhr.send("action=update&productId=" + productId +
                "&productName=" + encodeURIComponent(productName) +
                "&productDescription=" + encodeURIComponent(productDescription) +
                "&productPrice=" + encodeURIComponent(productPrice) +
                "&productQuantity=" + encodeURIComponent(productQuantity) +
                "&labelName=" + encodeURIComponent(labelName) +
                "&categoryName=" + encodeURIComponent(categoryName) +
                "&imageUrl=" + encodeURIComponent(imageUrl));
    } catch (error) {
        console.error("Error in saveProduct:", error);
    }
}

function deleteProduct() {
    const productId = document.getElementById("productId").value;

    if (!productId) {
        alert("Product ID is missing!");
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open("POST", `${contextPath}/DEHome/ProductWMController/delete`, true);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.onload = function () {
        if (xhr.status === 200 && xhr.responseText === "success") {
            alert("Product deleted successfully.");
            closeModal();
            location.reload();
        } else {
            alert("Failed to delete product.");
            console.error("Error:", xhr.responseText);
        }
    };
    xhr.onerror = function () {
        console.error("Request failed");
    };
    xhr.send("action=delete&productId=" + encodeURIComponent(productId));
}

// Add click event to product rows for opening the modal
document.querySelectorAll('.product-data-row').forEach(row => {
    row.addEventListener('click', function () {
        const product = {
            id: this.getAttribute('data-id'),
            name: this.getAttribute('data-name'),
            description: this.getAttribute('data-description'),
            price: this.getAttribute('data-price'),
            quantity: this.getAttribute('data-quantity'),
            label: this.getAttribute('data-label'),
            category: this.getAttribute('data-category'),
            imageUrl: this.getAttribute('data-image-url')
        };
        openModal(product);
    });
});
