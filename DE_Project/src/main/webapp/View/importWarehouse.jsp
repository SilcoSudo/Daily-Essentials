<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="DAOs.WarehouseDAO"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Nhập kho</title>
        <style>
            /* Styles for the warehouse import form */
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
        </style>
    </head>
    <body class="warehouse-import">
        <div class="container">
            <h1>Nhập kho</h1>
            <form action="ImportWarehouseController" method="post" enctype="multipart/form-data">
                <h2>Thông tin kho</h2>
                <div class="form-group">
                    <label for="warehouseName">Tên kho:</label>
                    <select id="warehouseName" name="warehouseName" onchange="updateWarehouseInfo()">
                        <%
                          WarehouseDAO warehouseDAO = new WarehouseDAO();
                          ResultSet rs = warehouseDAO.getAllWarehouses();
                          while (rs != null && rs.next()) {
                        %>
                        <option value="<%= rs.getString("warehouse_code") %>"><%= rs.getString("warehouse_name") %></option>
                        <%
                          }
                        %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="warehouseCode">Mã kho:</label>
                    <input type="text" id="warehouseCode" name="warehouseCode" readonly>
                </div>
                <div class="form-group">
                    <label for="warehouseAddress">Địa chỉ:</label>
                    <input type="text" id="warehouseAddress" name="warehouseAddress" readonly>
                </div>
                <div class="form-group">
                    <label for="warehouseCapacity">Sức chứa:</label>
                    <input type="text" id="warehouseCapacity" name="warehouseCapacity">
                </div>
                <div class="form-group">
                    <label for="warehouseType">Loại kho:</label>
                    <input type="text" id="warehouseType" name="warehouseType">
                </div>

                <h2>Thông tin sản phẩm</h2>
                <div id="productContainer"></div>
                <button type="button" onclick="addProductSection()">Thêm sản phẩm</button>
                <br><br>

                <input type="submit" value="Nhập kho">
            </form>
            <div id="productTemplate" style="display: none;">
                <div class="product-group">
                    <h3 onclick="toggleProductDetails(this)">Sản phẩm <span class="product-number"></span> ∨</h3>
                    <div class="product-details">
                        <div class="form-group">
                            <label for="productName">Tên sản phẩm:</label>
                            <input type="text" name="productName[]" required>
                        </div>
                        <div class="form-group">
                            <label for="price">Giá:</label>
                            <input type="number" name="price[]" required>
                        </div>
                        <div class="form-group">
                            <label for="sku">Mã SKU:</label>
                            <input type="text" name="sku[]" required>
                        </div>
                        <div class="form-group">
                            <label for="quantity">Số lượng:</label>
                            <input type="number" name="quantity[]" required>
                        </div>
                        <div class="form-group">
                            <label for="category">Phân loại:</label>
                            <select name="category[]" required>
                                <option value="">Chọn phân loại</option>
                                <!-- Populate categories if available -->
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="label">Nhãn:</label>
                            <select name="label[]" required>
                                <option value="">Chọn nhãn</option>
                                <!-- Populate labels if available -->
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="image">Ảnh:</label>
                            <input type="file" name="image[]" accept="image/*">
                        </div>
                    </div>
                </div>
            </div>

            <script>
                let productCount = 0;

                // Function to add a new product section
                function addProductSection() {
                    if (productCount >= 30) {
                        alert("You can add up to 30 products at a time.");
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

                // Function to toggle product details visibility
                function toggleProductDetails(header) {
                    const details = header.nextElementSibling;
                    details.classList.toggle("show");
                }

                // Function to update warehouse info
                function updateWarehouseInfo() {
                    var warehouseCode = document.getElementById("warehouseName").value;
                    var xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function () {
                        if (this.readyState == 4 && this.status == 200) {
                            var warehouse = JSON.parse(this.responseText);
                            document.getElementById("warehouseCode").value = warehouse.warehouseCode;
                            document.getElementById("warehouseAddress").value = warehouse.warehouseAddress;
                        }
                    };
                    xhttp.open("GET", "WarehouseController?warehouseCode=" + warehouseCode, true);
                    xhttp.send();
                }
            </script>
    </body>
</html>