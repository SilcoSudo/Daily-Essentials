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
            <!-- Header cards for Doanh Thu and Đơn Hàng -->
            <div class="header">
                <!-- Doanh Thu Card -->
                <div class="card revenue-card">
                    <h3>Doanh Thu</h3>
                    <p>Tháng này</p>
                    <h2>128,732,412 đ</h2>
                    <p>+ 8% so với tháng trước</p>
                </div>

                <!-- Đơn Hàng Card -->
                <div class="card order-card">
                    <h3>Thống kê đơn hàng</h3>
                    <div class="order-content">
                        <c:forEach items="${orderStatistics}" var="order">
                            <div class="order-row">
                                <div>
                                    <h5>${order.orderStatusString}</h5> 
                                </div>
                                <div>
                                    <span>${order.orderstatus_count}</span>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <!-- Bảng thống kê sản phẩm -->
            <div class="filter-section">
                <h3>Danh sách thống kê sản phẩm</h3>
                <br>
                <form action="${pageContext.request.contextPath}/ViewProductStatistics" method="get">
                    <label for="category">Danh mục:</label>
                    <select name="category_id" id="category" onchange="this.form.submit()">
                        <option value="0">Tất cả</option>
                        <c:forEach var="category" items="${categoryList}">
                            <option value="${category.category_id}" ${param.category_id == category.category_id ? 'selected' : ''}>
                                ${category.category_name}
                            </option>
                        </c:forEach>
                    </select>
                </form>
                <div class="table-container">
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
                        <tbody>
                            <c:forEach items="${productlist}" var="product">
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
            </div>

            <!-- Bảng sản phẩm bán chạy nhất -->
            <div class="filter-section">
                <h3> Sản phẩm bán chạy nhất trong </h3>
                <br>
                <label>Thoi gian</label>
                <select name="month">
                    <option value="10" selected>Tháng 10</option>

                </select>
                <select name="year">
                    <option value="2024" selected>2024</option>
                </select>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên</th>
                                <th>SL bán</th>
                                <th>Tổng tiền thu</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="" var="topProduct">
                                <tr>
                                    <td>}</td>
                                    <td> <img src="placeholder-image.png" alt="product" class="product-img"> </td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

    </body>
</html>
