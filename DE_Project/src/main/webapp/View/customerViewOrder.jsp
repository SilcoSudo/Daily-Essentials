<%-- 
    Document   : customerViewOrder
    Created on : Oct 18, 2024, 4:33:42 PM
    Author     : nhatl
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="Model.OrderHistory" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Lịch sử đơn hàng</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/customerViewOrder.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/customerInfo.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/navbar.css">
    </head>
    <body>   

        <jsp:include page="header.jsp"></jsp:include>

            <div class="container">
                <div class="side-menu">
                    <div class="side-menu-item">
                        <img src="${pageContext.request.contextPath}/Component/IMG/account-icon.png" alt="User Icon">
                    <a href="${pageContext.request.contextPath}/Home/Info">Tài khoản</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/order-icon.png" alt="Order Icon">
                    <a href="${pageContext.request.contextPath}/CustomerViewOrder">Quản lý đơn hàng</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/product-icon.png" alt="Product Icon">
                    <a href="productDetail.html">Sản phẩm đã mua</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/logout-icon.png" alt="Logout Icon">
                    <a href="${pageContext.request.contextPath}/Authen/Logout">Đăng xuất</a>
                </div>
            </div>

            <div class="main-content">
                <h2>Danh sách đơn hàng</h2>
                <div class="filter-section">
                    <div>
                        <label>ID Đơn hàng :</label>
                        <input type="text" id="searchOrderId" placeholder="Nhập ID đơn hàng"/>
                    </div>
                    <div class="filter-section-item">
                        <label>Tình trạng đơn :</label>
                        <select id="statusFilter">
                            <option value="">Tất cả</option>
                            <option value="Đang xử lý">Đang xử lý</option>
                            <option value="Ðã xác nhận">Ðã xác nhận</option>
                            <option value="Đang vận chuyển">Đang vận chuyển</option>
                            <option value="Ðã hoàn thành">Ðã hoàn thành</option>
                            <option value="Ðã hủy">Ðã hủy</option>
                        </select>
                    </div>
                    <div class="filter-section-item">
                        <label>Tổng số tiền :</label>
                        <input type="text" id="searchTotalAmount" placeholder="Nhập số tiền" />
                    </div>
                </div>
                <div class="filter-section">
                    <div class="filter-section-item">
                        <label for="Date"> Ngày tạo :</label>
                        <input type="Date"  id="searchDate">
                    </div>
                </div>

                <div class="table-container" items="${orderlist}" var="order">
                    <table>
                        <thead>
                            <tr>
                                <th>ID Đơn hàng</th>
                                <th>Ngày mua</th>
                                <th>Tổng tiền</th>
                                <th>Trạng thái đơn hàng</th>
                                <th>Ghi chú</th>
                            </tr>
                        </thead>
                        <c:forEach items="${orderlist}" var="order">
                            <tbody id="orderBody">
                                <tr>
                                    <td>${order.order_id}</td>
                                    <td>${order.order_date}</td>
                                    <td>${order.total_amount}</td>
                                    <td>${order.orderStatusString}</td>
                                    <td>...</td>
                                </tr>       
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
            </div>     
        </div>

        <script>
            function filterTable() {
                var searchId = document.getElementById('searchOrderId').value.toLowerCase();
                var searchAmount = document.getElementById('searchTotalAmount').value.toLowerCase();
                var searchDate = document.getElementById('searchDate').value;
                var selectedStatus = document.getElementById('statusFilter').value.toLowerCase();

                var rows = document.querySelectorAll('#orderBody tr');

                rows.forEach(function (row) {
                    var orderId = row.cells[0].textContent.toLowerCase();
                    var totalAmount = row.cells[2].textContent.toLowerCase();
                    var orderStatus = row.cells[3].textContent.toLowerCase();
                    var orderDate = row.cells[4].textContent;

                    var matchesId = (orderId.includes(searchId) || searchId === '');
                    var matchesAmount = (totalAmount.includes(searchAmount) || searchAmount === '');
                    var matchesStatus = (orderStatus.includes(selectedStatus) || selectedStatus === '');
                    var matchesDate = (orderDate.includes(searchDate) || searchDate === '');


                    if (matchesId && matchesAmount && matchesStatus && matchesDate) {
                        row.style.display = '';
                    } else {
                        row.style.display = 'none';
                    }
                });
            }

            document.getElementById('searchOrderId').addEventListener('input', filterTable);
            document.getElementById('searchTotalAmount').addEventListener('input', filterTable);
            document.getElementById('searchDate').addEventListener('input', filterTable);
            document.getElementById('statusFilter').addEventListener('change', filterTable);

        </script>
    </body>
</html>

