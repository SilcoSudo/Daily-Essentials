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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/staffViewOrderDetail.css">
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
                                <th>Chi tiết</th>
                            </tr>
                        </thead>
                        <c:forEach items="${orderlist}" var="order">
                            <tbody id="orderBody">
                                <tr>
                                    <td>${order.order_id}</td>
                                    <td>${order.order_date}</td>
                                    <td>${order.total_amount}</td>
                                    <td>${order.orderStatusString}</td>
                                    <td style="display:none;">${order.fee_shipp}</td>
                                    <td class="actions">
                                        <a href="#" class="view-details" data-order-id="${order.order_id}">Xem chi tiết</a>
                                    </td>
                                </tr>       
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
            </div>     
        </div>

        <!-- Popup Xem chi tiết -->
        <div class="popup" id="popup-details">
            <div class="popup-content">
                <div class="popup-header">
                    <h2>Chi tiết đơn hàng</h2>
                </div>
                <div class="popup-body">
                    <table>
                        <tr>
                            <th>ID Đơn hàng</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>Tình trạng đơn hàng</th>
                            <td></td>
                        </tr>

                        <tr>
                            <th>Ngày tạo</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>Phí vận chuyển</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>Tổng số tiền</th>
                            <td></td>
                        </tr>
                    </table>

                    <br />

                    <table>
                        <thead>
                            <tr>
                                <th>Ảnh</th>
                                <th>Tên sản phẩm</th>
                                <th>Số lượng</th>
                                <th>Giá</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><img src="" alt="product-image"></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><img src="" alt="product-image"></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="popup-footer">
                    <td></td>
                    <br/><br/>
                    <button class="close-btn" data-popup-id="popup-details">Đóng</button>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const viewDetailsLinks = document.querySelectorAll('.view-details');
                const closeButtons = document.querySelectorAll('.close-btn');
                const toggleMenuButtons = document.querySelectorAll('.toggle-menu-btn');

                function openPopup(popupId) {
                    document.getElementById(popupId).style.display = 'flex';
                }

                function closePopup(popupId) {
                    document.getElementById(popupId).style.display = 'none';
                }

                function filterTable() {
                    var searchId = document.getElementById('searchOrderId').value.toLowerCase();
                    var searchAmount = document.getElementById('searchTotalAmount').value.toLowerCase();
                    var searchDate = document.getElementById('searchDate').value;
                    var selectedStatus = document.getElementById('statusFilter').value.toLowerCase();

                    var rows = document.querySelectorAll('#orderBody tr');

                    rows.forEach(function (row) {
                        var orderId = row.cells[0].textContent.toLowerCase();
                        var orderDate = row.cells[1].textContent;
                        var totalAmount = row.cells[2].textContent.toLowerCase();
                        var orderStatus = row.cells[3].textContent.toLowerCase();
                        

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

                viewDetailsLinks.forEach(link => {
                    link.addEventListener('click', function (e) {
                        e.preventDefault();
                        const orderId = this.getAttribute('data-order-id');
                        openPopup('popup-details');

                        const orderDetails = Array.from(document.querySelectorAll('#orderBody tr'))
                                .find(row => row.cells[0].textContent.trim() === orderId);

                        const actions = e.target.closest('.actions');
                        actions.classList.remove('active');


                        if (orderDetails) {
                            const orderData = {
                                order_id: orderDetails.cells[0].textContent,
                                order_date: orderDetails.cells[1].textContent,
                                total_amount: orderDetails.cells[2].textContent,
                                orderStatusString: orderDetails.cells[3].textContent,
                                fee_shipp: orderDetails.cells[4].textContent

                            };

                            const popupBody = document.querySelector('#popup-details .popup-body');
                            const tds = popupBody.querySelectorAll('td');
                            tds[0].textContent = orderData.order_id;
                            tds[1].textContent = orderData.orderStatusString;
                            tds[2].textContent = orderData.order_date;
                            tds[3].textContent = orderData.fee_shipp;
                            tds[4].textContent = orderData.total_amount;
                        }
                    });
                });

                closeButtons.forEach(button => {
                    button.addEventListener('click', function () {
                        const popupId = this.getAttribute('data-popup-id');
                        closePopup(popupId);
                    });
                });

                toggleMenuButtons.forEach(button => {
                    button.addEventListener('click', function (e) {
                        const actions = e.target.closest('.actions');
                        actions.classList.toggle('active');

                        const menus = document.querySelectorAll('.actions');
                        menus.forEach(menu => {
                            if (menu !== actions) {
                                menu.classList.remove('active');
                            }
                        });
                    });
                });

                window.addEventListener('click', function (event) {
                    const popups = document.querySelectorAll('.popup');
                    popups.forEach(popup => {
                        if (event.target === popup) {
                            closePopup(popup.id);
                        }
                    });

                    const menus = document.querySelectorAll('.actions');
                    menus.forEach(menu => {
                        if (!menu.contains(event.target)) {
                            menu.classList.remove('active');
                        }
                    });
                });
            });
        </script>
    </body>
</html>

