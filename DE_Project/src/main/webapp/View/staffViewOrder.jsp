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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/headers.css" type="text/css"/>
        <jsp:useBean id= "listorder" class="DAO.ViewOrderDAO" scope="request"></jsp:useBean>
        </head>
        <body>
            <div class="w-container">
            <jsp:include page="headers.jsp"></jsp:include>
                <div class="container">
                    <h1>Order Management</h1>
                    <div class="filter-section">
                        <div>
                            <label>Order ID :</label>
                            <input type="text" id="searchOrderId" placeholder="Enter the order ID"/>
                        </div>
                        <div>
                            <label>Order status :</label>
                            <select id="statusFilter">
                                <option value="">All</option>
                                <option value="Processing">Processing</option>
                                <option value="Confirmed">Confirmed</option>
                                <option value="Shipping">Shipping</option>
                                <option value="Completed">Completed</option>
                                <option value="Cancelled">Cancelled</option>
                            </select>
                        </div>
                        <div>
                            <label>Total amount :</label>
                            <input type="text" id="searchTotalAmount" placeholder="Enter the amount" />
                        </div>
                    </div>
                    <div class="filter-section">
                        <div>
                            <label for="Date">Date :</label>
                            <input type="Date"  id="searchDate">
                        </div>
                    </div>

                    <div class="table-container" items="${orderlist}" var="order">
                    <table id="orderTable">
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Customer ID</th>
                                <th>Total amount</th>
                                <th>Order status</th>
                                <th>Date</th>
                                <th></th>
                            </tr>
                        </thead>
                        <c:forEach items="${listorder.allOrder}" var="order">
                            <tbody id="orderBody">
                                <tr>
                                    <td>${order.order_id} </td>
                                    <td>${order.user_id}</td>
                                    <td class="product-price">${order.total_amount}</td>
                                    <td>${order.orderStatusString}</td>
                                    <td>${order.order_date}</td>
                                    <td class="actions">
                                        <a href="#" class="toggle-menu-btn">
                                            <img src="${pageContext.request.contextPath}/Component/IMG/ic-3dot.svg" class="verticaldots-button" alt="Vertical Dots Button">
                                        </a>
                                        <div class="actions-menu">
                                            <a href="${pageContext.request.contextPath}/OrderDetails?order_id=${order.order_id}">View details</a>
                                            <a href="#" class="update-status" data-order-id="${order.order_id}">Update order status</a>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </c:forEach>
                    </table>
                </div>
            </div>

            <!-- Popup Cập nhật trạng thái đơn -->
            <div class="popup" id="popup-update" style="display: none">
                <div class="popup-content popup-update-content">
                    <div class="popup-header">
                        <h2>Update order status</h2>
                    </div>
                    <br>
                    <div class="popup-body">
                        <form method="post" action="Manage-Orders">
                            <table>
                                <tr>
                                    <th>Order ID</th>
                                    <td id="update-order-id"></td>
                                <input type="hidden" name="order_id" id="hidden-order-id"> 
                                </tr>
                                <tr>
                                    <th>Order status</th>
                                    <td>
                                        <select id="order-status-select" name="order_status">
                                            <option value="0">Processing</option>
                                            <option value="1">Confirmed</option>
                                            <option value="2">Shipping</option>
                                            <option value="3">Completed</option>
                                            <option value="4">Cancelled</option>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                            <div class="popup-footer">
                                <button type="button" class="close-btn" data-popup-id="popup-update">Close</button>
                                <button type="submit">Save</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                const updateStatusLinks = document.querySelectorAll('.update-status');
                const closeButtons = document.querySelectorAll('.close-btn');
                const toggleMenuButtons = document.querySelectorAll('.toggle-menu-btn');

                function formatPrice(price) {
                    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
                }

                $(".product-price").each(function () {
                    var priceText = $(this).text().replace(" ₫", "");
                    var formattedPrice = formatPrice(priceText);
                    $(this).text(formattedPrice + " ₫");
                });

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

                updateStatusLinks.forEach(link => {
                    link.addEventListener('click', function (e) {
                        e.preventDefault();
                        openPopup('popup-update');

                        const actions = e.target.closest('.actions');
                        actions.classList.remove('active');

                        const orderRow = this.closest('tr');
                        const orderId = orderRow.cells[0].textContent.trim();
                        const currentStatus = orderRow.cells[3].textContent.trim();

                        document.getElementById('update-order-id').textContent = orderId;
                        document.getElementById('hidden-order-id').value = orderId;
                        document.getElementById('order-status-select').value = getStatusValue(currentStatus);

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

