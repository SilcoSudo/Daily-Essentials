<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="DAO.WarehouseDAO"%>
<%@page import="DAO.ProductWMDAO"%>
<%@page import="DAO.LabelDAO"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html  lang="vi">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
            <form action="ImportWarehouseController" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                <h2>Thông tin kho</h2>
                <div class="form-group">
                    <label for="warehouseName">Tên kho:</label>
                    <select id="warehouseName" name="warehouseName" onchange="updateWarehouseInfo()">
                        <option value="">Chọn kho</option>
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


                <h2>Thông tin sản phẩm</h2>
                <div id="productContainer"></div>
                <button type="button" onclick="addProductSection()">Thêm sản phẩm</button>
                <br><br>

                <input type="submit" value="Nhập kho">
            </form>

            <div id="productTemplate" style="display: none;">
                <div class="product-group" >
                    <h3 onclick="toggleProductDetails(this)">Sản phẩm <span class="product-number"></span> </h3>
                    <div class="product-details">
                        <div class="form-group">
                            <label for="productName_">Tên sản phẩm:</label>
                            <input type="text" name="productName" id="productName_" required>
                        </div>
                        <div class="form-group">
                            <label for="productPrice_">Giá:</label>
                            <input type="number" name="productPrice" id="productPrice_" required>
                        </div>
                        <div class="form-group">
                            <label for="sku_">Mã SKU:</label>
                            <input type="text" name="sku" id="sku_" required>
                        </div>
                        <div class="form-group">
                            <label for="productQuantity_">Số lượng:</label>
                            <input type="number" name="productQuantity" id="productQuantity_" required>
                        </div>
                        <div class="form-group">
                            <label for="category_">Phân loại:</label>
                            <select name="category" id="category_" required>
                                <option value="">Chọn phân loại</option>
                                <% 
                                    LabelDAO labelDAO = new LabelDAO();
                                    ResultSet categories = labelDAO.getCategories();
                                    while (categories != null && categories.next()) {
                                        String categoryName = categories.getString("category_name");
                                %>
                                <option value="<%= categoryName %>"><%= categoryName %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="label_">Nhãn:</label>
                            <select name="label" id="label_" required>
                                <option value="">Chọn Nhãn</option>
                                <% 
                                    ResultSet labels = labelDAO.getLabels();
                                    while (labels != null && labels.next()) {
                                        String labelName = labels.getString("label_name");
                                %>
                                <option value="<%= labelName %>"><%= labelName %></option>
                                <% } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="image_">Ảnh:</label>
                            <input type="file" name="image" id="image_" accept="image/*">
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

                    // Set unique IDs for each input/select in the cloned product section
                    const inputs = newProductSection.querySelectorAll("input, select, label");
                    inputs.forEach((input) => {
                        if (input.id) {
                            input.id = input.id.replace("_", `_${productCount}`);
                        }
                        if (input.htmlFor) {
                            input.htmlFor = input.htmlFor.replace("_", `_${productCount}`);
                        }
                    });

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
                    if (warehouseCode) {
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
                    } else {
                        // Xóa giá trị nếu không có kho nào được chọn
                        document.getElementById("warehouseCode").value = "";
                        document.getElementById("warehouseAddress").value = "";
                    }
                }


                function validateForm() {
                    const inputs = document.querySelectorAll('input[required], select[required]');
                    for (let input of inputs) {
                        if (input.offsetParent === null) { // Kiểm tra xem phần tử có bị ẩn không
                            alert(`Trường ${input.name} đang bị ẩn và không thể được nhập liệu. Vui lòng kiểm tra lại.`);
                            return false;
                        }
                        if (!input.value) {
                            alert(`Vui lòng nhập giá trị cho trường: ${input.name}`);
                            input.focus();
                            return false;
                        }
                    }
                    return true;
                }

                document.querySelector('form').onsubmit = validateForm;


            </script>
        </div>
    </body>
</html>
