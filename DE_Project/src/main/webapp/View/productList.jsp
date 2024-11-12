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
                            // Get the filtered product results if available, otherwise retrieve all products
                            ProductWMDAO productWMDAO = new ProductWMDAO();
                            ResultSet rs = (ResultSet) request.getAttribute("filteredProduct");
                            if (rs == null) {
                                rs = productWMDAO.getAllProducts(); 
                            }
                            boolean hasProducts = false;
                            // Iterate through the products and display them in the table
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
                            // If no products were found, display a message
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
                <form action="${pageContext.request.contextPath}/DEHome/Manage-Products/update" method="POST">
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

        <!-- Include JavaScript File -->
        <script src="${pageContext.request.contextPath}/Component/JS/productList.js"></script>
        <script src="${pageContext.request.contextPath}/Component/JS/warehouse.js"></script>

    </body>
</html>
