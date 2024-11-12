<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="DAO.ProductWMDAO"%>
<%@page import="DAO.LabelDAO"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="vi">
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
                <form action="${pageContext.request.contextPath}/DEHome/Manage-Products/ProductWM" method="GET">
                    <div class="pm-product-form-group">
                        <input type="text" name="productName" placeholder="Tên sản phẩm">
                        <input type="text" name="productID" placeholder="Mã sản phẩm">
                        <input type="text" name="price" placeholder="Giá">
                        <input type="text" name="label" placeholder="Nhãn">
                        <button type="submit" class="btn">Tìm</button>
                    </div>
                </form>

                <!-- Product Table -->
                <div class="pm-product-list-table">
                    <table class="pm-product-table">
                        <thead>
                            <tr>
                                <th class="pm-product-header">Tên sản phẩm</th>
                                <th class="pm-product-header">ID sản phẩm</th>
                                <th class="pm-product-header">Mã sản phẩm</th>
                                <th class="pm-product-header">Giá</th>
                                <th class="pm-product-header">Nhãn</th>
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
                                <td colspan="5" class="pm-product-cell">Không tìm thấy sản phẩm.</td>
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
                <h2>Thông tin sản phẩm</h2>
                <form action="${pageContext.request.contextPath}/DEHome/Manage-Products/ProductWM" method="POST">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" id="productId" name="productId">

                    <label class="modalproduct">Tên</label>
                    <input type="text" id="productName" name="productName" />

                    <label class="modalproduct">Mô tả</label>
                    <textarea id="productDescription" name="productDescription"></textarea>

                    <label class="modalproduct">Giá</label>
                    <input type="text" id="productPrice" name="productPrice" />

                    <label class="modalproduct">Tồn kho</label>
                    <input type="text" id="productQuantity" name="productQuantity" />

                    <label class="modalproduct">Nhãn</label>
                    <select id="labelName" name="labelName">
                        <option value="">Chọn Nhãn</option>
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

                    <label class="modalproduct">Phân loại</label>
                    <select id="categoryName" name="categoryName">
                        <option value="">Chọn phân loại</option>
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

                    <button id="changeImageButton">Đổi</button>
                    <img id="productImage" src="" alt="Product Image" />

                    <div>
                        <button type="button" onclick="deleteProduct()">Xóa</button>
                        <button type="submit">Lưu</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Include JavaScript File -->
        <script src="${pageContext.request.contextPath}/Component/JS/productList.js"></script>
        <script src="${pageContext.request.contextPath}/Component/JS/warehouse.js"></script>

    </body>
</html>
