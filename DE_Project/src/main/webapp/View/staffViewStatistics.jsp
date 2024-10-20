<%-- 
    Document   : staffViewStatistics
    Created on : Oct 20, 2024, 2:40:47 PM
    Author     : nhatl
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

            <div class="filter-section">
                <h3>Danh mục</h3>
                <select>
                    <option>Đồ uống</option>
                    <option>Thức ăn</option>
                </select>
                <select>
                    <option>Cafe</option>
                    <option>Nước ngọt</option>
                </select>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Danh mục</th>
                            <th>Tên sản phẩm</th>
                            <th>Số lượng bán</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1</td>
                            <td>Đồ uống</td>
                            <td>Café</td>
                            <td>132,414</td>
                        </tr>
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
                            <td><img src="placeholder-image.png" alt="product"> Bia vàng Leffe chai 330ml</td>
                            <td>123,402</td>
                            <td>561,426,124 đ</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </body>
</html>
