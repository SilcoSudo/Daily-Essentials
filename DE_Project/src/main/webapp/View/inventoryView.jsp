<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="${pageContext.request.contextPath}/Component/IMG/IMG_Product_view/Product_noneImage-48x48.png" type="image/x-icon">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/inventory.css">
        <title>Inventory</title>
    </head>
    <body>
        <div class="w-container">
            <jsp:include page="headers.jsp"></jsp:include>
                <div style="flex: 5">
                    <div class="warehouse-container">
                        <h2>Inventory</h2>
                        <div class="w-btn">
                            <button class="btnComeBack btn" onclick="history.back()">Back</button>
                        </div>
                        <div class="w-content">
                            <table >
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Code</th>
                                        <th>Product ID</th>
                                        <th>Date</th>
                                        <th>Beginning Quantity</th>
                                        <th>Incoming Quantity</th>
                                        <th>Outgoing Quantity</th>
                                        <th>Ending Quantity</th>
                                        <th>Created At</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="inventory" items="${sessionScope.inventory}">
                                    <c:choose>
                                        <c:when test="${inventory.endingQuantity > 600}">
                                            <tr class="high-quantity">
                                            </c:when>
                                            <c:when test="${inventory.endingQuantity > 200}">
                                            <tr class="medium-quantity">
                                            </c:when>
                                            <c:when test="${inventory.endingQuantity > 100}">
                                            <tr class="low-quantity">
                                            </c:when>
                                            <c:otherwise>
                                            <tr>
                                            </c:otherwise>
                                        </c:choose>
                                        <td>${inventory.inventoryId}</td>
                                        <td>${inventory.inventoryCode}</td>
                                        <td>${inventory.productId}</td>
                                        <td>${inventory.periodDateFormatted}</td>
                                        <td>${inventory.beginningQuantity}</td>
                                        <td>${inventory.incomingQuantity}</td>
                                        <td>${inventory.outgoingQuantity}</td>
                                        <td>${inventory.endingQuantity}</td>
                                        <td>${inventory.createAtFormatted}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div
    </body>
</html>
