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
                    <h1>Import Products</h1>

                    <!-- Display error message if it exists -->
                <c:if test="${not empty errorMessage}">
                    <div class="error-message">${errorMessage}</div>
                </c:if>

                <!-- Product Import Form -->
                <form action="${pageContext.request.contextPath}/DEHome/Manage-Products/import" method="post">
                    <h2>Product Information</h2>
                    <div class="product-group">    
                        <h3>Product Information</h3>
                        <div class="form-group">
                            <label for="productName">Product Name:</label>
                            <input type="text" id="productName" name="productName" required>
                        </div>
                        <div class="form-group">
                            <label for="productPrice">Price:</label>
                            <input type="number" id="productPrice" name="productPrice" required>
                        </div>
                        <div class="form-group">
                            <label for="sku">SKU:</label>
                            <input type="text" id="sku" name="sku" required>
                        </div>
                        <div class="form-group">
                            <label for="productQuantity">Quantity:</label>
                            <input type="number" id="productQuantity" name="productQuantity" required>
                        </div>
                        <div class="form-group">
                            <label for="productDescription">Description:</label>
                            <input type="text" id="productDescription" name="productDescription" required>
                        </div>
                        <div class="form-group">
                            <label for="productImage">Image URL:</label>
                            <input type="text" id="productImage" name="productImage" required>
                        </div>
                        <div class="form-group">
                            <label for="categoryId">Category ID:</label>
                            <input type="number" id="categoryId" name="categoryId" required>
                        </div>
                    </div>

                    <input type="submit" value="Import Products">
                </form>
            </div>
        </div>
    </body>
</html>
