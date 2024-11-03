<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="DAO.LabelDAO"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../Component/CSS/labelList.css" />
        <title>Label Management</title>
        <script src="${pageContext.request.contextPath}/Component/JS/labelList.js"></script>

    </head>
    <body>
        <div class="w-label"> 
            <div class="label-management-container">
                <!-- Table Section -->
                <div class="label-table-section">
                    <div class="label-form-group">
                        <form action="LabelController" method="GET">
                            <select name="labelName">
                                <option value="">Nhãn</option>
                                <%
                                    LabelDAO labelDAO = new LabelDAO();
                                    ResultSet label = labelDAO.getLabels();
                                    while (label != null && label.next()) {
                                        String labelName = label.getString("label_name");
                                %>
                                <option value="<%= labelName %>"><%= labelName %></option>
                                <%
                                    }
                                %>
                            </select>

                            <!-- Thay đổi phần này thành input để người dùng có thể nhập nhãn -->
                            <input type="text" name="categoryName" placeholder="Nhập phân loại" />
                            <button type="submit" class="label-search-btn">Tìm</button>
                            <button type="button" id="addLabelButton" class="label-search-btn label-add-btn">Thêm nhãn</button>
                        </form>
                    </div>

                    <!-- Data Table -->
                    <div class="label-data-table-container">
                        <table class="label-data-table">
                            <thead>
                                <tr>
                                    <th class="label-table-header">Mã nhãn</th>
                                    <th class="label-table-header">Nhãn</th>
                                    <th class="label-table-header">Phân loại</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ResultSet rs = (ResultSet) request.getAttribute("filteredLabels");
                                    if (rs == null) {
                                        rs = labelDAO.getAllLabels(); // Fallback to all labels if no filter applied
                                    }

                                    if (rs != null) {
                                        while (rs.next()) {
                                            int labelId = rs.getInt("label_id");
                                            String categoryName = rs.getString("category_name");
                                            String labelName = rs.getString("label_name");
                                %>
                                <!-- Add data attributes to hold the row's data -->
                                <tr class="label-data-row" 
                                    data-label-id="<%= labelId %>" 
                                    data-label-name="<%= labelName %>"
                                    data-category-name="<%= categoryName %>">
                                    <td class="label-table-cell"><%= labelId %></td>
                                    <td class="label-table-cell"><%= labelName %></td>
                                    <td class="label-table-cell"><%= categoryName %></td>
                                </tr>
                                <%
                                        }
                                    } else {
                                %>
                                <tr>
                                    <td colspan="3" class="label-table-cell">No labels found.</td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Form Section -->
                <div class="label-form-section">
                    <h3>Thông tin nhãn</h3>
                    <form action="LabelController" method="POST">
                        <!-- Hidden field to specify if it's an update or add -->
                        <input type="hidden" name="action" id="updateAction" value="update" />

                        <!-- ID of the label for updating -->
                        <input type="hidden" id="updateLabelId" name="labelId" value="" />

                        <div class="label-input-group">
                            <label for="updateLabelName">Nhãn</label>
                            <input type="text" id="updateLabelName" name="labelName" value="" />
                        </div>

                        <div class="label-input-group">
                            <label for="updateCategoryName">Phân loại</label>
                            <input type="text" id="updateCategoryName" name="categoryName" value="" />
                        </div>

                        <div class="label-input-group">
                            <button type="submit" class="label-submit-btn">Lưu</button>
                        </div>
                    </form>
                </div>

                <!-- Add Label Modal -->
                <div id="addLabelModal" class="label-modal-overlay">
                    <div class="label-modal-content">
                        <span class="label-close-btn" onclick="closeModal()">&times;</span>
                        <h2>Thêm nhãn mới</h2>
                        <form action="LabelController" method="POST">
                            <input type="hidden" name="action" value="add" /> 
                            <label for="addLabelName">Tên nhãn</label>
                            <input type="text" id="addLabelName" name="labelName" required>
                            <label for="addCategoryName">Phân loại</label>
                            <input type="text" id="addCategoryName" name="categoryName" required>
                            <button type="submit">Thêm</button>
                        </form>
                    </div>
                </div>
            </div>

            <% if (request.getAttribute("errorMessage") != null) { %>
            <div style="color: red;"><%= request.getAttribute("errorMessage") %></div>
            <% } %>
            <% if (request.getAttribute("successMessage") != null) { %>
            <div style="color: green;"><%= request.getAttribute("successMessage") %></div>
            <% } %>
        </div>
    </body>
</html>


