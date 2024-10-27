<%-- 
    Document   : staffViewStatistics
    Created on : Oct 20, 2024, 2:40:47 PM
    Author     : nhatl
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List" %>
<%@page import="Model.ProductStatistics" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin/Home</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/staffViewStatistics.css" type="text/css"/>
    </head>
    <body>

        <div class="container">
            <div class="header">
                <div class="card">
                    <h3>Doanh Thu</h3>
                    <p>Tháng này</p>
                    <h2>200.000.000 đ</h2>
                    <p>+ 8% so với tháng trước</p>
                </div>
                <div class="card">
                    <h3>Đơn hàng</h3>
                    <p>Giao thành công</p>
                    <h2>500 đơn</h2>
                    <p class="order-info">Bị hủy: 10 | Lỗi: 4</p>
                </div>
            </div>

            <!-- Bảng thống kê sản phẩm -->
            <div class="filter-section">
                <h3>Danh sách thống kê sản phẩm</h3>
                <br>
                <form action="${pageContext.request.contextPath}/ViewProductStatistics" method="get">
                    <label for="category">Danh mục:</label>
                    <select name="category_id" id="category" onchange="this.form.submit()">
                        <option value="0" ${param.category_id == '0' ? 'selected' : ''}>Tất cả</option>
                        <option value="1" ${param.category_id == '1' ? 'selected' : ''}>Trứng</option>
                        <option value="2" ${param.category_id == '2' ? 'selected' : ''}>Đậu hủ</option>
                        <option value="3" ${param.category_id == '3' ? 'selected' : ''}>Sữa tươi</option>
                        <option value="4" ${param.category_id == '4' ? 'selected' : ''}>Sữa hạt đậu</option>
                        <option value="5" ${param.category_id == '5' ? 'selected' : ''}>Sữa bột</option>
                        <option value="6" ${param.category_id == '6' ? 'selected' : ''}>Rau lá</option>
                        <option value="7" ${param.category_id == '7' ? 'selected' : ''}>Củ quả</option>
                        <option value="8" ${param.category_id == '8' ? 'selected' : ''}>Trái cây tươi</option>
                        <option value="9" ${param.category_id == '9' ? 'selected' : ''}>Thịt</option>
                        <option value="10" ${param.category_id == '10' ? 'selected' : ''}>Hải sản tươi</option>
                        <option value="11" ${param.category_id == '11' ? 'selected' : ''}>Bia</option>
                        <option value="12" ${param.category_id == '12' ? 'selected' : ''}>Cà phê</option>
                        <option value="13" ${param.category_id == '13' ? 'selected' : ''}>Nước suối</option>
                        <option value="14" ${param.category_id == '14' ? 'selected' : ''}>Nước ngọt</option>
                        <c:forEach var="category" items="${categoryList}">
                            <option value="${category.category_id}" ${category.category_id == param.category_id ? 'selected' : ''}>
                                ${category.category_name}
                            </option>
                        </c:forEach>
                    </select>
                </form>
                <table>
                    <thead>
                        <tr>
                            <th>ID Sản phẩm</th>
                            <th>Danh mục</th>
                            <th>Tên sản phẩm</th>
                            <th>Số lượng bán</th>
                            <th>Doanh thu sản phẩm</th>
                        </tr>
                    </thead>
                    <c:forEach items="${productlist}" var="product">
                        <tbody>
                            <tr>
                                <td>${product.product_id}</td>
                                <td>${product.category_name}</td>
                                <td>${product.product_name}</td>
                                <td></td>
                                <td></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Bảng sản phẩm bán chạy nhất -->
            <div class="filter-section">
                <h3>Top sản phẩm bán chạy nhất</h3>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Danh mục</th>
                            <th>Tên sản phẩm</th>
                            <th>Số lượng bán</th>
                            <th>Doanh thu sản phẩm</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Đồ uống</td>
                            <td><img src="placeholder-image.png" alt="product"> Cafe </td>
                            <td>100</td>
                            <td>561,426</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </body>
</html>
