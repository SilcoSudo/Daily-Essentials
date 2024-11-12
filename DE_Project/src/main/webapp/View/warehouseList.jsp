
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@ page import="DAO.WarehouseDAO" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/warehouseList.css" />
        <title>Warehouse Management</title>
        <script>
            var contextPath = '${pageContext.request.contextPath}';
        </script>
    </head>
    <body>
        <%WarehouseDAO warehouseDAO = new WarehouseDAO();%>
        <!-- Warehouse Management Section -->
        <div class="w-container">
            <jsp:include page="headers.jsp"></jsp:include>
                <div style="flex: 5">
                    <div class="warehouse-container">
                        <div class="container">
                            <h1>Warehouse Management</h1>

                            <!-- Search and Filter Section -->
                            

                            <!-- Action Buttons -->
                            <div class="action-buttons">
                                <a href="${pageContext.request.contextPath}/DEHome/Manage-Products/import" class="btn">Import Inventory</a>
                            <a href="${pageContext.request.contextPath}/Warehouse/Inventory" class="btn">Inventory</a>

                            <form action="${pageContext.request.contextPath}/Warehouse/Export" method="get">
                                <button class="btn" type="submit">Export to Excel</button>
                            </form>
                        </div>

                        <!-- Warehouse Table -->
                        

                            
                    </div>
            <jsp:include page="/View/productList.jsp" />
            <jsp:include page="/View/categoryList.jsp" />
                </div>
            </div>


        </div>
    </div>   
    <script src="${pageContext.request.contextPath}/Component/JS/warehouse.js"></script>
</body>
</html>
