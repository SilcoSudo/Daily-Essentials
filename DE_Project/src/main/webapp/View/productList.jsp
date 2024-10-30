<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="DAO.ProductWMDAO"%>
<%@page import="DAO.LabelDAO"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product Management</title>
        <style>
            /* Global Styles for Product Management */
            .product-container {
                width: 100%;
                padding: 20px;
            }

            .pm-container {
                width: 90%;
                max-width: 1200px;
                margin: 0 auto;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                padding: 20px;
            }

            h1 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }

            .pm-product-form-group {
                display: flex;
                flex-wrap: wrap; /* Allow wrapping */
                align-items: center;
                justify-content: space-between;
                margin-bottom: 20px;
            }

            .pm-product-form-group input,
            .pm-product-form-group select,
            .pm-product-form-group button {
                padding: 10px;
                margin-right: 10px;
                font-size: 16px;
                border-radius: 4px;
                border: 1px solid #ccc;
                flex: 1;
                min-width: 150px; /* Ensure minimum width */
                box-sizing: border-box; /* Include padding and border in width */
            }

            .pm-product-form-group button {
                background-color: #0056b3;
                color: white;
                border: none;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .pm-product-form-group button:hover {
                background-color: #004080;
            }

            table.pm-product-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            table.pm-product-table th,
            table.pm-product-table td {
                padding: 12px;
                text-align: center;
                border: none;
            }

            th.pm-product-header {
                background-color: whitesmoke; /* Header background color */
                border-bottom: 2px solid #0056b3; /* Darker border for the header */
            }

            td.pm-product-cell {
                background-color: white; /* Light gray for cells */
            }

            /* Modal styles */
            .modal {
                display: none; /* Hidden by default */
                position: fixed; /* Stay in place */
                z-index: 1; /* Sit on top */
                left: 0;
                top: 0;
                width: 100%; /* Full width */
                height: 100%; /* Full height */
                background-color: rgba(0, 0, 0, 0.7); /* Dark background with higher opacity */
            }

            .modal-content {
                background-color: #fff; /* White background */
                margin: 10% auto; /* Center the modal */
                padding: 30px; /* Increased padding */
                border-radius: 10px; /* Rounded corners */
                width: 50%; /* Modal width */
                max-width: 600px; /* Maximum width */
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.5); /* Soft shadow */
            }

            .close-button {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .close-button:hover,
            .close-button:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }

            label.modalproduct {
                display: block;
                margin: 15px 0 5px; /* Spacing around labels */
                font-weight: bold; /* Make label text bold */
            }

            input[type="text"],
            textarea,
            select {
                width: calc(100% - 20px); /* Full width minus padding */
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                margin-bottom: 10px;
            }

            textarea{
                font-family: Arial;
            }
            #changeImageButton {
                background-color: #0056b3;
                color: white;
                border: none;
                padding: 10px;
                border-radius: 4px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            #changeImageButton:hover {
                background-color: #004080;
            }

            button {
                padding: 10px 15px; /* Increased padding for buttons */
                border-radius: 5px; /* Rounded corners */
                border: none; /* No border */
                cursor: pointer;
                transition: background-color 0.3s;
            }

            button:hover {
                background-color: #0056b3; /* Hover effect */
                color: white;
            }

            button:focus {
                outline: none; /* Remove focus outline */
            }

        </style>
    </head>
    <body>
        <div class="product-container">
            <div class="pm-container">
                <h1>Product Management</h1>

                <!-- Search and Filter Section for Products -->
                <form action="ProductWMController" method="GET">
                    <div class="pm-product-form-group">
                        <input type="text" name="productName" placeholder="Tên sản phẩm">
                        <input type="text" name="productID" placeholder="Mã sản phẩm">
                        <input type="text" name="price" placeholder="Giá">
                        <input type="text" name="label" placeholder="Nhãn">
                        <select name="warehouse">
                            <option value="">Kho</option>
                            <%
                                // Populate warehouse options from the database
                                ProductWMDAO productWMDAO = new ProductWMDAO();
                                ResultSet warehouses = productWMDAO.getWarehouses(); // Assuming getWarehouses method exists
                                while (warehouses != null && warehouses.next()) {
                            %>
                            <option value="<%= warehouses.getString("warehouse_name") %>"><%= warehouses.getString("warehouse_name") %></option>
                            <%
                                }
                            %>
                        </select>
                        <button type="submit" class="btn">Tìm</button>
                    </div>
                </form>

                <!-- Product Table -->
                <table class="pm-product-table">
                    <thead>
                        <tr>
                            <th class="pm-product-header">Tên sản phẩm</th>
                            <th class="pm-product-header">ID sản phẩm</th>
                            <th class="pm-product-header">Mã sản phẩm</th>
                            <th class="pm-product-header">Giá</th>
                            <th class="pm-product-header">Nhãn</th>
                            <th class="pm-product-header">Kho</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        ResultSet rs = (ResultSet) request.getAttribute("filteredProduct");
                        if (rs == null) {
                            rs = productWMDAO.getAllProducts(); 
                        }
                        boolean hasProducts = false;
                        if (rs != null) {
                            while (rs.next()) {
                                hasProducts = true;
                        %>
                        <tr class="product-data-row"
                            data-id="<%= rs.getString("product_id") %>"
                            data-name="<%= rs.getString("product_name") %>"
                            data-description="<%= rs.getString("product_description") %>"
                            data-price="<%= rs.getBigDecimal("product_price") %>"
                            data-quantity="<%= rs.getInt("product_quantity") %>"
                            data-label="<%= rs.getString("label_name") %>"
                            data-category="<%= rs.getString("category_name") %>"
                            data-image-url="<%= rs.getString("image_url") %>">
                            <td class="pm-product-cell"><%= rs.getString("product_name") %></td>
                            <td class="pm-product-cell"><%= rs.getInt("product_id") %></td>
                            <td class="pm-product-cell"><%= rs.getString("product_sku") %></td>
                            <td class="pm-product-cell"><%= rs.getBigDecimal("product_price") %></td>
                            <td class="pm-product-cell"><%= rs.getString("label_name") %></td>
                            <td class="pm-product-cell"><%= rs.getString("warehouse_name") %></td>
                        </tr>
                        <% 
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="6" class="pm-product-cell">No products found.</td>
                        </tr>
                        <% 
                        }
                        %>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal Structure -->
        <div id="productModal" class="modal">
            <div class="modal-content">
                <span class="close-button" onclick="closeModal()">&times;</span>
                <h2>Thông tin sản phẩm</h2>

                <input type="hidden" id="productId" name="productId" />

                <label class="modalproduct">Tên</label>
                <input type="text" id="productName" name="productName" />

                <label class="modalproduct">Mô tả</label>
                <textarea id="productDescription" name="productDescription"></textarea>

                <label class="modalproduct">Giá</label>
                <input type="text" id="productPrice" name="productPrice" />

                <label class="modalproduct">Tồn kho</label>
                <input type="text" id="productQuantity" name="productQuantity" />

                <label class="modalproduct">Phân loại</label>
                <select id="labelName" name="labelName">
                    <option value="">Chọn phân loại</option>
                    <%
                        LabelDAO labelDAO = new LabelDAO();
                        ResultSet categories = labelDAO.getCategories();
                        while (categories != null && categories.next()) {
                            String categoryName = categories.getString("category_name");
                    %>
                    <option value="<%= categoryName %>"><%= categoryName %></option>
                    <%
                        }
                    %>
                </select>

                <label class="modalproduct">Nhãn</label>
                <select id="categoryName" name="categoryName">
                    <option value="">Chọn Nhãn</option>
                    <%
                        ResultSet labels = labelDAO.getLabels();
                        while (labels != null && labels.next()) {
                            String labelName = labels.getString("label_name");
                    %>
                    <option value="<%= labelName %>"><%= labelName %></option>
                    <%
                        }
                    %>
                </select>

                <button id="changeImageButton">Đổi</button>
                <img id="productImage" src="" alt="Product Image" />

                <div>
                    <button onclick="deleteProduct()">Xóa</button>
                    <button onclick="saveProduct()">Lưu</button>
                </div>
            </div>
        </div>


        <script>
            function openModal(product) {
                document.getElementById("productId").value = product.id;
                document.getElementById("productName").value = product.name;
                document.getElementById("productDescription").value = product.description;
                document.getElementById("productPrice").value = product.price;
                document.getElementById("productQuantity").value = product.quantity;
                document.getElementById("labelName").value = product.label;
                document.getElementById("categoryName").value = product.category;
                document.getElementById("productImage").src = product.imageUrl;

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
        </script>
    </body>
</html>


