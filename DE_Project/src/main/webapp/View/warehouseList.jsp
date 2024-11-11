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

    </head>
    <body>
        <%WarehouseDAO warehouseDAO = new WarehouseDAO();%>
        <!-- Warehouse Management Section -->
        <div class="w-container">
            <jsp:include page="headers.jsp"></jsp:include>
                <div style="flex:5">
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
                                <a href="${pageContext.request.contextPath}/DEHome/Manage-Products/ImportWarehouse" class="btn">Nhập kho</a>
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
            </div>
        </div>
    </body>
</html>
