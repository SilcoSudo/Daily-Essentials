<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="DAO.ProductDAO" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="Model.Product" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
        <title>Import Products</title>
    </head>
    <body class="warehouse-import">
        <div class="w-container">
            <jsp:include page="headers.jsp"></jsp:include>
            <div class="container">
                <h1>Import Products</h1>
                <form action="${pageContext.request.contextPath}/ImportWarehouse" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <h2>Product Information</h2>
                    <div id="productContainer"></div>
                    <button type="button" id="addProductBtn">Add Product</button>
                    <br><br>
                    <input type="submit" value="Import Products">
                </form>

                <div id="productTemplate" style="display: none;">
                    <div class="product-group">
                        <h3 onclick="toggleProductDetails(this)">Product <span class="product-number"></span></h3>
                        <div class="product-details">
                            <div class="form-group">
                                <label for="productName_">Product Name:</label>
                                <input type="text" name="productName[]" required>
                            </div>
                            <div class="form-group">
                                <label for="productPrice_">Price:</label>
                                <input type="number" name="productPrice[]" required>
                            </div>
                            <div class="form-group">
                                <label for="sku_">SKU:</label>
                                <input type="text" name="sku[]" required>
                            </div>
                            <div class="form-group">
                                <label for="productQuantity_">Quantity:</label>
                                <input type="number" name="productQuantity[]" required>
                            </div>
                            <<div class="container">
                <h1>Import Products</h1>
                <form action="${pageContext.request.contextPath}/ImportWarehouse" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <h2>Product Information</h2>
                    <div id="productContainer"></div>
                    <button type="button" id="addProductBtn">Add Product</button>
                    <br><br>
                    <input type="submit" value="Import Products">
                </form>

                div class="form-group">
                                <label for="productDescription_">Description:</label>
                                <input type="text" name="productDescription[]" required>
                            </div>
                            <div class="form-group">
                                <label for="productImage_">Image URL:</label>
                                <input type="text" name="productImage[]" required>
                            </div>
                            <div class="form-group">
                                <label for="categoryId_">Category ID:</label>
                                <input type="number" name="categoryId[]" required>
                            </div>
                            <button type="button" onclick="removeProductSection(this)">Remove Product</button>
                        </div>
                    </div>
                </div>

                <script>
                    let productCount = 0;

                    // Function to add a new product section
                    function addProductSection() {
                        if (productCount >= 30) {
                            alert("You can add up to 30 products.");
                            return;
                        }
                        productCount++;

                        // Clone the product template and make it visible
                        const productTemplate = document.getElementById("productTemplate");
                        const newProductSection = productTemplate.cloneNode(true);
                        newProductSection.style.display = "block";
                        newProductSection.id = "";

                        // Set the product number in the header
                        newProductSection.querySelector(".product-number").innerText = productCount;

                        // Append the new product section to the container
                        document.getElementById("productContainer").appendChild(newProductSection);
                    }

                    // Function to remove a product section
                    function removeProductSection(button) {
                        $(button).closest('.product-group').remove();
                        productCount--;
                    }

                    // Function to toggle product details visibility
                    function toggleProductDetails(header) {
                        const details = header.nextElementSibling;
                        details.classList.toggle("show");
                    }

                    function validateForm() {
                        const inputs = document.querySelectorAll('input[required], select[required]');
                        for (let input of inputs) {
                            if (input.offsetParent !== null && !input.value) {
                                alert(`Please fill in the field: ${input.name}`);
                                input.focus();
                                return false;
                            }
                        }
                        return true;
                    }

                    document.getElementById('addProductBtn').addEventListener('click', addProductSection);
                </script>
            </div>
        </div>
    </body>
</html>
