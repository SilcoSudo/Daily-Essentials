<%-- 
    Document   : staffViewOrderDetail
    Created on : Oct 28, 2024, 12:32:05 AM
    Author     : nhatl
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
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
        
    </head>
    <body>
        <div class="container">
            <h2>Chi tiết đơn hàng</h2>
            <br />
            <div class="table-container">
                <table>

                    <tr>
                        <th>ID Đơn hàng</th>
                        <td>${orderlist.order_id}</td>
                    </tr>
                    <tr>
                        <th>Tình trạng đơn hàng</th>
                        <td>${orderlist.orderStatusString}</td>
                    </tr>
                    <tr>
                        <th>Ngày tạo</th>
                        <td>${orderlist.order_date}</td>
                    </tr>
                    <tr>
                        <th>Tổng số tiền</th>
                        <td>${orderlist.total_amount}</td>
                    </tr>
                </table>
                <br />
                <table>
                    <thead>
                        <tr>
                            <th>Ảnh</th>
                            <th>Tên sản phẩm</th>
                            <th>Số lượng</th>
                            <th>Giá tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orderDetails}" var="detail">
                            <tr>
                                <td><img src="${detail.productImage}" alt="Product Image" width="100" height="100"></td>
                                <td>${detail.productName}</td>
                                <td>${detail.quantity}</td>
                                <td>${detail.totalPrice}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="popup-footer">
                <a href="${pageContext.request.contextPath}/DEHome/Manage-Orders" class="update-button">Quay lại danh sách đơn hàng</a>
            </div>
        </div>
    </body>
</html>
