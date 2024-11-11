<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@page import="java.sql.ResultSet"%> 
<%@page import="DAO.WarehouseDAO"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="../Component/CSS/warehouseList.css" />
        <title>Warehouse Management</title>
        <script>
            var contextPath = '${pageContext.request.contextPath}';
        </script>
    </head>
    <body>
        <!-- Warehouse Management Section -->
        <div class="w-container">
            <jsp:include page="headers.jsp"></jsp:include>
                <div style="flex: 5">
                    <div class="warehouse-container">
                        <div class="container">
                            <h1>Warehouse Management</h1>

                            <!-- Search and Filter Section -->
                            <form action="WarehouseController" method="GET">
                                <div class="form-group">
                                    <input type="text" name="warehouseName" placeholder="Tên kho" />
                                    <input type="text" name="warehouseCode" placeholder="Mã kho" />
                                    <input
                                        type="text"
                                        name="warehouseAddress"
                                        placeholder="Địa chỉ"
                                        />
                                    <select name="warehouseStatus">
                                        <option value="">Trạng thái</option>
                                        <option value="Còn trống">Còn trống</option>
                                        <option value="Đã đầy">Đã đầy</option>
                                    </select>
                                    <button type="submit" class="btn">Tìm</button>
                                </div>
                            </form>

                            <!-- Action Buttons -->
                            <div class="action-buttons">
                                <a href="importWarehouse.jsp" class="btn">Nhập kho</a>
                                <a href="${pageContext.request.contextPath}/Warehouse/Inventory" class="btn">Inventory</a>

                                <form action="${pageContext.request.contextPath}/Warehouse/Export" method="get">
                                <button class="btn" type="submit">Export to Excel</button>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>   
        <script src="${pageContext.request.contextPath}/Component/JS/warehouse.js"></script>
    </body>
</html>
