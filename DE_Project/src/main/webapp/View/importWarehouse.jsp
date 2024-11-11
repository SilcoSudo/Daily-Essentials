<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="DAO.WarehouseDAO"%>
<%@page import="DAO.LabelDAO"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
        <title>Import Products</title>
        <style>
            .warehouse-import {
                font-family: sans-serif;
                background-color: #f4f4f4;
                padding: 20px;
            }

            .warehouse-import h1 {
                color: #333;
                text-align: center;
                font-size: 24px;
                font-weight: bold;
                margin-bottom: 20px;
            }

            .warehouse-import .container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                max-width: 800px;
                margin: 0 auto;
                border: 1px solid #ccc;
            }

            .warehouse-import .form-group {
                margin-bottom: 20px;
            }

            .warehouse-import label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
                font-size: 14px;
                color: #333;
            }

            .warehouse-import input[type="text"],
            .warehouse-import input[type="number"],
            .warehouse-import select,
            .warehouse-import input[type="file"] {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 14px;
            }

            .warehouse-import input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                padding: 12px 20px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                font-weight: bold;
                margin-top: 20px;
            }

            .warehouse-import input[type="submit"]:hover {
                background-color: #45a049;
            }

            .warehouse-import .product-group {
                border: 1px solid #0046b8;
                padding: 15px;
                margin-bottom: 20px;
                border-radius: 5px;
                background-color: #fff;
            }

            .warehouse-import .product-group h3 {
                margin-top: 0;
                background-color: #0046b8;
                color: #fff;
                padding: 10px;
                font-size: 16px;
                cursor: pointer;
                font-weight: bold;
            }

            .warehouse-import .product-details {
                display: none;
                padding-top: 15px;
            }

            .warehouse-import .product-details.show {
                display: block;
            }

            .warehouse-import button {
                margin-top: 10px;
            }
        </style>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    </head>
    <body class="warehouse-import">
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
                        <div class="form-group">
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
    </body>
</html>
