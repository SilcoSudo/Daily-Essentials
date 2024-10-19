<%-- 
    Document   : staffViewOrder
    Created on : Oct 18, 2024, 5:05:45 PM
    Author     : nhatl
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.OrderHistory" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View Order</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/staffViewOrder.css" type="text/css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/staffUpdateStatusOrder.css" type="text/css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/staffViewOrderDetail.css" type="text/css"/>
        <jsp:useBean id= "listorder" class="DAO.ViewOrderDAO" scope="request"></jsp:useBean>
        </head>
        <body>
            <div class="container">
                <h1>Quản lý đơn hàng</h1>
                <div class="filter-section">
                    <div>
                        <label>ID đơn :</label>
                        <input type="text" id="searchOrderId" placeholder="Nhập ID đơn"/>
                    </div>
                    <div>
                        <label>Tình trạng đơn :</label>
                        <select>
                            <option>Đang xử lý</option>
                            <option>Ðã xác nhận</option>
                            <option>Đang vận chuyển</option>
                            <option>Hoàn thành</option>
                            <option>Ðã hủy</option>
                        </select>
                    </div>
                    <div>
                        <label>Tổng số tiền :</label>
                        <input type="text" id="searchTotalAmount" placeholder="Nhập số tiền" />
                    </div>
                </div>
                <div class="filter-section">
                    <div>
                        <label for="Date"> Ngày tạo :</label>
                        <input type="Date"  id="searchDate">
                    </div>
                </div>

                <div class="table-container" items="${orderlist}" var="order">
                <table id="orderTable">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>ID Khách hàng</th>
                            <th>Tổng số tiền</th>
                            <th>Trạng thái đơn hàng</th>
                            <th>Ngày tạo</th>
                            <th></th>
                        </tr>
                    </thead>
                    <c:forEach items="${listorder.allOrder}" var="order">
                        <tbody id="orderBody">
                            <tr>
                                <td>${order.order_id} </td>
                                <td>${order.user_id}</td>
                                <td>${order.total_amount}</td>
                                <td>Đang xử lý</td>
                                <td>${order.order_date}</td>


                                <td class="actions">
                                    <a href="#" class="toggle-menu-btn">
                                        <img src="${pageContext.request.contextPath}/Component/IMG/ic-3dot.svg" class="verticaldots-button" alt="Vertical Dots Button">
                                    </a>
                                    <div class="actions-menu">
                                        <a href="#" class="view-details" data-order-id="${order.order_id}">Xem chi tiết</a>
                                        <a href="#" class="update-status">Cập nhật trạng thái đơn hàng</a>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </c:forEach>
                </table>
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
                            <th>ID</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>ID Khách hàng</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>Tình trạng đơn hàng</th>
                            <td>Đang xử lý</td>
                        </tr>
                        <tr>
                            <th>Ngày tạo</th>
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

        <!-- Popup Cập nhật trạng thái đơn -->
        <div class="popup" id="popup-update">
            <div class="popup-content popup-update-content">
                <div class="popup-header">
                    <h2>Cập nhật trạng thái đơn hàng</h2>
                </div>
                <div class="popup-body">
                    <table>
                        <tr>
                            <th>ID</th>
                            <td></td>
                        </tr>
                        <tr>
                            <th>Trạng thái đơn</th>
                            <td>
                                <select>
                                    <option>Đang xử lý</option>
                                    <option>Ðã xác nhận</option>
                                    <option>Đang vận chuyển</option>
                                    <option>Hoàn thành</option>
                                    <option>Ðã hủy</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="popup-footer">
                    <button class="close-btn" data-popup-id="popup-update">Đóng</button>
                    <button>Cập nhật</button>
                </div>
            </div>
        </div>

        <script>
            document.getElementById('searchOrderId').addEventListener('input', function () {
                var searchId = this.value.toLowerCase();
                var rows = document.querySelectorAll('#orderBody tr');

                rows.forEach(function (row) {
                    var orderId = row.cells[0].textContent.toLowerCase();
                    if (orderId.includes(searchId) || searchId === '') {
                        row.style.display = '';
                    } else {
                        row.style.display = 'none';
                    }
                });
            });

            document.getElementById('searchTotalAmount').addEventListener('input', function () {
                var searchAmount = this.value.toLowerCase();
                var rows = document.querySelectorAll('#orderBody tr');

                rows.forEach(function (row) {
                    var totalAmount = row.cells[2].textContent.toLowerCase();
                    if (totalAmount.includes(searchAmount) || searchAmount === '') {
                        row.style.display = '';
                    } else {
                        row.style.display = 'none';
                    }
                });
            });

            document.getElementById('searchDate').addEventListener('input', function () {
                var searchDate = this.value;
                var rows = document.querySelectorAll('#orderBody tr');

                rows.forEach(function (row) {
                    var orderDate = row.cells[4].textContent; // Lấy giá trị từ ô ngày tạo
                    if (orderDate.includes(searchDate) || searchDate === '') {
                        row.style.display = '';
                    } else {
                        row.style.display = 'none';
                    }
                });
            });

            document.addEventListener('DOMContentLoaded', function () {
                const viewDetailsLinks = document.querySelectorAll('.view-details');
                const updateStatusLinks = document.querySelectorAll('.update-status');
                const closeButtons = document.querySelectorAll('.close-btn');
                const toggleMenuButtons = document.querySelectorAll('.toggle-menu-btn');

                function openPopup(popupId) {
                    document.getElementById(popupId).style.display = 'flex';
                }

                function closePopup(popupId) {
                    document.getElementById(popupId).style.display = 'none';
                }

                viewDetailsLinks.forEach(link => {
                    link.addEventListener('click', function (e) {
                        e.preventDefault();
                        const orderId = this.getAttribute('data-order-id');
                        openPopup('popup-details');

                        const orderDetails = Array.from(document.querySelectorAll('#orderBody tr'))
                                .find(row => row.cells[0].textContent.trim() === orderId); // Tìm dòng đơn hàng được nhấp
                        
                        const actions = e.target.closest('.actions');
                        actions.classList.remove('active');
                        
        
                        if (orderDetails) {
                            const orderData = {
                                order_id: orderDetails.cells[0].textContent,
                                user_id: orderDetails.cells[1].textContent,
                                total_amount: orderDetails.cells[2].textContent,
                                order_date: orderDetails.cells[4].textContent,
                            };

                            // Cập nhật popup với dữ liệu đơn hàng
                            const popupBody = document.querySelector('#popup-details .popup-body');
                            const tds = popupBody.querySelectorAll('td'); 
                            tds[0].textContent = orderData.order_id; 
                            tds[1].textContent = orderData.user_id; 
                            tds[3].textContent = orderData.order_date; 
                            tds[4].textContent = orderData.total_amount; 
                        }

                       
                    });
                });

                updateStatusLinks.forEach(link => {
                    link.addEventListener('click', function (e) {
                        e.preventDefault();
                        openPopup('popup-update');

                        const actions = e.target.closest('.actions');
                        actions.classList.remove('active');
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

