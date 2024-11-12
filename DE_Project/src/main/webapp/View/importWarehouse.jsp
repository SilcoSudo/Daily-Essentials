<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="DAO.ProductDAO" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="Model.Product" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/importWarehouse.css" />
        <title>Import Products</title>
        <style>
            .error-message {
                color: red;
                font-weight: bold;
                margin-top: 10px;
            }
        </style>
    </head>
    <body class="warehouse-import">
        <div class="w-container">
            <jsp:include page="headers.jsp"></jsp:include>
                <div class="container">
                    <h1>Import Products from Excel</h1>

                    <!-- Hiển thị thông báo lỗi nếu có -->
                <c:if test="${not empty errorMessage}">
                    <div class="error-message">${errorMessage}</div>
                </c:if>

                <!-- Form tải lên file Excel -->
                <form action="${pageContext.request.contextPath}/DEHome/Manage-Products/importExcel" 
                      method="post" 
                      enctype="multipart/form-data">
                    <label for="productExcel">Select Excel file:</label>
                    <input type="file" id="productExcel" name="productExcel" accept=".xlsx" required>
                    <input type="submit" value="Import Products">
                </form>

            </div>
        </div>
    </body>
</html>
