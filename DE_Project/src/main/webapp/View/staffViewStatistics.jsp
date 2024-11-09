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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/headers.css" type="text/css"/>
    </head>
    <body>
        <div class="w-container">
            <jsp:include page="headers.jsp"></jsp:include>
                <div class="container">
                    <!-- Header cards for Doanh Thu and Đơn Hàng -->
                    <div class="header">

                        <!-- Doanh Thu Card -->
                        <div class="card revenue-card">
                            <h3>Revenue Statistics</h3>  
                            <br>
                            <p><strong>This month's revenue :</strong></p>

                        <h2 class="product-price card_items">${getCurrentMonthRevenue}</h2>
                        <br>
                        <p><strong>Compared to last month's revenue :</strong></p>
                        <br>
                        <h2 class="product-price card_items">${getPreviousMonthRevenue}</h2>
                        <h3 class="percent-price card_items">${getPercentChange}</h3>
                    </div>

                    <!-- Đơn Hàng Card -->
                    <div class="card order-card">
                        <h3>Order statistics</h3>
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
                    <h3>List of product statistics</h3>
                    <br>
                    <form action="${pageContext.request.contextPath}/DEHome" method="get">
                        <label for="category">Category:</label>
                        <select name="category_id" id="category" onchange="this.form.submit()">
                            <option value="0">All</option>
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
                                    <th>Product ID</th>
                                    <th>Category</th>
                                    <th>Product Name</th>
                                    <th>Quantity sold</th>
                                    <th>Product revenue</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${productlist}" var="productlist">
                                    <tr>
                                        <td>${productlist.product_id}</td>
                                        <td>${productlist.category_name}</td>
                                        <td>${productlist.product_name}</td>
                                        <td>${productlist.quantitySold}</td>
                                        <td class="product-price">${productlist.revenue}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Bảng sản phẩm bán chạy nhất -->
                <div class="filter-section">
                    <h3>List of 10 best-selling products</h3>
                    <div class="table-container">
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Category</th>
                                    <th>Product Name</th>
                                    <th>Quantity sold</th>
                                    <th>Product revenue</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="top_product" items="${topSellingProducts}">
                                    <tr>
                                        <td>${top_product.product_id}</td>
                                        <td>${top_product.category_name}</td>
                                        <td>${top_product.product_name}</td>
                                        <td>${top_product.quantitySold}</td>
                                        <td class="product-price">${top_product.revenue}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function formatPrice(price) {
                if (price === null || price === "" || price === "No revenue yet") {
                    return "No revenue yet";
                }
                return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " ₫";
            }

            $(".product-price").each(function () {
                var priceText = $(this).text().replace(" ₫", "");
                var formattedPrice = formatPrice(priceText);
                $(this).text(formattedPrice);
            });

            function formatPercent(percent) {
                if (percent === null || percent === "" || percent === "No data to compare") {
                    return "No data to compare";
                }
                return percent + " %";
            }

            $(".percent-price").each(function () {
                var percentText = $(this).text().replace(" %", "");
                var formattedPercent = formatPercent(percentText);
                $(this).text(formattedPercent);
            });
        </script>
    </body>
</html>
