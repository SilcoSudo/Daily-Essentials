<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="DAO.ProductWMDAO"%>
<%@page import="DAO.LabelDAO"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/productList.css" />
        <script>
            var contextPath = '${pageContext.request.contextPath}';
        </script>
        <title>Product Management</title>
    </head>
    <body>
        <div class="product-container">
            <div class="pm-container">
                <h1>Product Management</h1>

                <!-- Search and Filter Section for Products -->
                <form action="${pageContext.request.contextPath}/DEHome/Manage-Products/search" method="GET">
                    <div class="pm-product-form-group">
                        <input type="text" name="productName" placeholder="Product Name">
                        <input type="text" name="productID" placeholder="Product ID">
                        <input type="text" name="price" placeholder="Price">
                        <input type="text" name="label" placeholder="Label">
                        <button type="submit" class="btn">Search</button>
                    </div>
                </form>

                <!-- Product Table -->
                <div class="pm-product-list-table">
                    <table class="pm-product-table">
                        <thead>
                            <tr>
                                <th class="pm-product-header">Product Name</th>
                                <th class="pm-product-header">Product ID</th>
                                <th class="pm-product-header">SKU</th>
                                <th class="pm-product-header">Price</th>
                                <th class="pm-product-header">Label</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            ProductWMDAO productWMDAO = new ProductWMDAO();
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
                            </tr>
                            <% 
                                 }
                            }
                            if (!hasProducts) {
                            %>
                            <tr>
                                <td colspan="5" class="pm-product-cell">No products found.</td>
                            </tr>
                            <% 
                            }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Modal Structure -->
        <div id="productModal" class="modal">
            <div class="modal-content">
                <span class="close-button" onclick="closeModal()">&times;</span>
                <h2>Product Information</h2>
                <form id="productForm" action="${pageContext.request.contextPath}/DEHome/Manage-Products/update" method="POST">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" id="productId" name="productId">

                    <label class="modalproduct">Name</label>
                    <input type="text" id="productName" name="productName" />

                    <label class="modalproduct">Description</label>
                    <textarea id="productDescription" name="productDescription"></textarea>

                    <label class="modalproduct">Price</label>
                    <input type="text" id="productPrice" name="productPrice" />

                    <label class="modalproduct">Stock</label>
                    <input type="text" id="productQuantity" name="productQuantity" />

                    <label class="modalproduct">Label</label>
                    <select id="labelName" name="labelName">
                        <option value="">Select Label</option>
                        <%
                            LabelDAO labelDAO = new LabelDAO();
                            ResultSet labels = labelDAO.getLabels();
                            while (labels != null && labels.next()) {
                                String labelName = labels.getString("label_name");
                        %>
                        <option value="<%= labelName %>"><%= labelName %></option>
                        <%
                            }
                        %>
                    </select>

                    <label class="modalproduct">Category</label>
                    <select id="categoryName" name="categoryName">
                        <option value="">Select Category</option>
                        <%
                            ResultSet categories = labelDAO.getCategories();
                            while (categories != null && categories.next()) {
                                String categoryName = categories.getString("category_name");
                        %>
                        <option value="<%= categoryName %>"><%= categoryName %></option>
                        <%
                            }
                        %>
                    </select>

                    <button id="changeImageButton">Change</button>
                    <img id="productImage" src="" alt="Product Image" />

                    <div>
                        <button type="button" onclick="deleteProduct()">Delete</button>
                        <button type="submit">Save</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- JavaScript Functions -->
        <script>
            // Open the modal with product details
            function openModal(product) {
                console.log("Opening modal for product:", product);
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
                console.log("Closing modal");
                document.getElementById("productModal").style.display = "none";
            }

            // Event listener for clicking outside the modal to close it
            window.onclick = function (event) {
                if (event.target === document.getElementById('productModal')) {
                    closeModal();
                }
            };

            // Delete product function
            function deleteProduct() {
                const productId = document.getElementById("productId").value;
                if (!productId) {
                    alert("Product ID is missing!");
                    return;
                }
                console.log("Deleting product with ID:", productId);

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
        </script>
    </body>
</html>
