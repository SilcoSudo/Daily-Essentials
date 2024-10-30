<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DAO.WarehouseDAO"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Warehouse Management</title>

        <!-- Scoped Warehouse Management Styles -->
        <style>
            .warehouse-container {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 20px;
                width: 100%;
            }

            .warehouse-container .container {
                width: 90%;
                max-width: 1200px; /* Limit width to 1200px */
                margin: 0 auto; /* Center the container */
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                padding: 20px;
            }

            .warehouse-container h1 {
                text-align: center;
                color: #333;
            }

            .warehouse-container .form-group {
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 20px;
            }

            .warehouse-container .form-group select,
            .warehouse-container .form-group input,
            .warehouse-container .form-group button {
                padding: 10px;
                margin-right: 10px;
                font-size: 16px;
                border-radius: 4px;
                border: 1px solid #ccc;
                flex: 1;
            }

            .warehouse-container .form-group button {
                background-color: #0056b3;
                color: white;
                border: none;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .warehouse-container .form-group button:hover {
                background-color: #004080;
            }

            .warehouse-container table.warehouse-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            .warehouse-container table.warehouse-table th,
            .warehouse-container table.warehouse-table td {
                padding: 10px;
                text-align: left;
                border: 1px solid #ddd;
                text-align: center;
            }

            .warehouse-container th {
                background-color: #f2f2f2;
                border-bottom: 2px solid black;
            }

            .warehouse-container td {
                border-bottom: 1px solid #ddd;
            }

            .warehouse-container .btn {
                padding: 10px;
                background-color: #0056b3;
                color: white;
                text-decoration: none;
                border-radius: 5px;

                transition: background-color 0.3s;
            }

            .warehouse-container .btn:hover {
                background-color: #004080;
            }

            .warehouse-container .action-buttons {
                margin-bottom: 20px;
            }
        </style>

    </head>
    <body>
        <!-- Warehouse Management Section -->
        <div class="warehouse-container">
            <div class="container">
                <h1>Warehouse Management</h1>

                <!-- Search and Filter Section -->
                <form action="WarehouseController" method="GET">
                    <div class="form-group">
                        <input type="text" name="warehouseName" placeholder="Tên kho">
                        <input type="text" name="warehouseCode" placeholder="Mã kho">
                        <input type="text" name="warehouseAddress" placeholder="Địa chỉ">
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
                    <a href="#" class="btn">Xuất kho</a>
                    <a href="#" class="btn">Tồn kho</a>
                </div>

                <!-- Warehouse Table -->
                <table class="warehouse-table">
                    <thead>
                        <tr>
                            <th>Tên kho</th>
                            <th>Mã kho</th>
                            <th>Địa chỉ</th>
                            <th>Sức chứa hiện tại (sản phẩm)</th>
                            <th>Loại kho</th>
                            <th>Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            WarehouseDAO warehouseDAO = new WarehouseDAO();
                            ResultSet rs = (ResultSet) request.getAttribute("warehouses");
                             if (rs == null) {
                            rs = warehouseDAO.getAllWarehouses(); 
                        }
                            boolean foundResults = false;
                            while (rs != null && rs.next()) {
                            foundResults = true;

                        %>
                        <tr>
                            <td><%= rs.getString("warehouse_name") %></td>
                            <td><%= rs.getString("warehouse_code") %></td>
                            <td><%= rs.getString("warehouse_address") %></td>
                            <td><%= rs.getInt("warehouse_capacity") %></td>
                            <td><%= rs.getString("warehouse_type") %></td>
                            <td><%= rs.getString("warehouse_status") %></td>
                        </tr>
                        <%
                            }

                            if (!foundResults) {
                        %>
                        <tr>
                            <td colspan="6" style="text-align: center;">Không tìm thấy kho hàng nào phù hợp.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Product and Label Management Section (Scoped) -->
        <jsp:include page="productList.jsp" />
        <jsp:include page="labelList.jsp" />

    </body>
</html>
