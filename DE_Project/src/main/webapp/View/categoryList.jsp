<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="DAO.LabelDAO"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/labelList.css" />
        <title>Label Management</title>
        <script src="${pageContext.request.contextPath}/Component/JS/labelList.js"></script>
    </head>
    <body>
        <div class="w-label"> 
            <div class="label-management-container">
                <!-- Table Section -->
                <div class="label-table-section">
                    <div class="label-form-group">
                        <form action="${pageContext.request.contextPath}/DEHome/Manage-Products/manageLabels" method="GET">
                            <select id="labelDropdown" name="labelName" onchange="filterLabels()">
                                <option value="">Label</option>
                                <%
                                    LabelDAO labelDAO = new LabelDAO();
                                    ResultSet label = labelDAO.getLabels();
                                    while (label != null && label.next()) {
                                        String labelName = label.getString("label_name");
                                %>
                                <option value="<%= labelName %>"><%= labelName %></option>
                                <% } %>
                            </select>

                            <!-- Category Filter and Search Button -->
                            <input type="text" id="categoryFilter" name="categoryName" placeholder="Enter category" oninput="filterLabels()" />
                            <button type="submit" class="label-search-btn">Search</button>
                            <button type="button" id="addLabelButton" class="label-add-btn">Add Label</button>
                        </form>
                    </div>

                    <!-- Data Table -->
                    <div class="label-data-table-container">
                        <table class="label-data-table">
                            <thead>
                                <tr>
                                    <th class="label-table-header">Label ID</th>
                                    <th class="label-table-header">Label</th>
                                    <th class="label-table-header">Category</th>
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
                    <h3>Label Information</h3>
                    <form action="${pageContext.request.contextPath}/DEHome/Manage-Products/manageLabels" method="POST">
                        <!-- Hidden field to specify if it's an update or add -->
                        <input type="hidden" name="action" id="updateAction" value="update" />

                        <!-- ID of the label for updating -->
                        <input type="hidden" id="updateLabelId" name="labelId" value="" />

                        <div class="label-input-group">
                            <label for="updateLabelName">Label</label>
                            <input type="text" id="updateLabelName" name="labelName" placeholder="Enter label" value="" />
                        </div>

                        <div class="label-input-group">
                            <label for="updateCategoryName">Category</label>
                            <input type="text" id="updateCategoryName" name="categoryName" placeholder="Enter category" value="" />
                        </div>

                        <!-- Action Buttons for Save and Delete with space between -->
                        <div class="form-actions">
<!--                            <button type="button" class="label-delete-btn" onclick="deleteCategory()">
                                <span class="delete-icon">🗑</span> Delete
                            </button>-->
                            <button type="submit" class="label-submit-btn">Save</button>
                        </div>
                    </form>
                </div>


                <!-- Add Label Modal -->
                <div id="addLabelModal" class="label-modal-overlay">
                    <div class="add-label-modal-content">
                        <span class="add-label-close-btn" onclick="closeModal()">&times;</span>
                        <h2>Add New Label and Category</h2>
                        <form action="${pageContext.request.contextPath}/DEHome/Manage-Products/manageLabels" method="POST">
                            <input type="hidden" name="action" value="add" /> 

                            <!-- Input with Datalist for Label -->
                            <label for="labelInput">Label:</label>
                            <input list="labelOptions" id="labelInput" name="labelName" placeholder="Select or type new label" />
                            <datalist id="labelOptions">
                                <% 
                                    ResultSet labels = labelDAO.getLabels();
                                    while (labels != null && labels.next()) {
                                        String labelName = labels.getString("label_name");
                                %>
                                <option value="<%= labelName %>"><%= labelName %></option>
                                <% } %>
                            </datalist>

                            <!-- Category Name Input -->
                            <label for="addCategoryName">Category:</label>
                            <input type="text" id="addCategoryName" name="categoryName" required placeholder="Enter category name" />

                            <button type="submit" class="label-add-btn">Add</button>
                        </form>
                    </div>
                </div>

                <% if (request.getAttribute("errorMessage") != null) { %>
                <div style="color: red;"><%= request.getAttribute("errorMessage") %></div>
                <% } %>
                <% if (request.getAttribute("successMessage") != null) { %>
                <div style="color: green;"><%= request.getAttribute("successMessage") %></div>
                <% } %>
            </div>
        </div>
    </body>
</html>
