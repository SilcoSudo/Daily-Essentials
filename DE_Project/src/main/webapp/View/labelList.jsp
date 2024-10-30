<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="DAO.LabelDAO"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Label Management</title>
        <style>
            /* Global Styles */
            body {
                font-family: Arial, sans-serif;
                background-color: #f9f9f9;
                margin: 0;
                padding: 0;
            }
            .w-label{
                width: 100%;
                padding: 20px;
            }
            .label-management-container {
                width: 90%;
                max-width: 1200px; /* Limit width to 1200px */
                margin: 0 auto; /* Center the container */
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                padding: 20px;
                display: flex;
                justify-content: space-between;
            }

            h1.label-title {
                text-align: center;
                color: #333;
            }

            /* Table Section */
            .label-table-section {
                flex: 2;
                margin-right: 20px;
                min-width: 600px;
                width: 700px;
            }

            .label-form-group {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
            }

            .label-form-group select,
            .label-form-group input,
            .label-form-group button {
                padding: 10px;
                margin-right: 10px;
                font-size: 16px;
                border-radius: 4px;
                border: 1px solid #ccc;
                flex: 1;
            }

            .label-search-btn {
                background-color: #0056b3;
                color: white;
                border: none;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            .label-search-btn:hover {
                background-color: #004080;
            }

            table.label-data-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }

            table.label-data-table th,
            table.label-data-table td {
                padding: 10px;
                min-width: 100px;
                width: 20%;
                border: none;
                text-align: center;
            }

            th.label-table-header {
                background-color: #f2f2f2;
                border-bottom: 2px solid;
            }

            td.label-table-cell {
                border-bottom: 1px solid #ddd;
            }

            /* Scrollbar Styling */
            .label-data-table-container {
                height: 300px;
                overflow-y: scroll;
                display: block;
                min-width: 500px;
                max-width: 600px;
            }

            /* Scrollbar custom styling */
            ::-webkit-scrollbar {
                width: 10px;
            }

            ::-webkit-scrollbar-track {
                box-shadow: inset 0px 0px 20px 20px grey;
                border-radius: 10px;
            }

            ::-webkit-scrollbar-thumb {
                background: #000080;
                border-radius: 10px;
            }

            /* Form Section */
            .label-form-section {
                flex: 1;
            }

            .label-form-section h3 {
                margin-bottom: 20px;
                color: #333;
            }

            .label-input-group {
                margin-bottom: 15px;
                display: flex;
                flex-direction: column;
            }

            .label-input-group input {
                padding: 10px;
                font-size: 16px;
                border-radius: 4px;
                border: 1px solid #ccc;
                margin-bottom: 10px;
            }

            .label-input-group button.label-submit-btn {
                background-color: #002080;
                color: white;
                border: none;
                padding: 10px;
                font-size: 16px;
                border-radius: 4px;
                cursor: pointer;
            }

            .label-input-group button.label-submit-btn:hover {
                background-color: #004080;
            }

            .label-delete-btn {
                color: red;
                cursor: pointer;
                font-size: 16px;
            }

            /* Modal Styles */
            .label-modal-overlay {
                display: none;
                position: fixed;
                z-index: 1;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0, 0, 0, 0.4);
            }

            .label-modal-content {
                background-color: #fefefe;
                margin: 15% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 30%;
                min-width: 310px;
                border-radius: 8px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .label-close-btn {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
            }

            .label-close-btn:hover,
            .label-close-btn:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
            }
        </style>
        <script>
            function populateForm(labelId, categoryName, labelName) {
                document.getElementById("updateLabelId").value = labelId;
                document.getElementById("updateCategoryName").value = categoryName;
                document.getElementById("updateLabelName").value = labelName;
            }

            // Event listener to close modal
            function closeModal() {
                document.getElementById("addLabelModal").style.display = "none";
            }

            // Open Add Modal
            function openAddModal() {
                // Clear the fields in the add form
                document.getElementById("addLabelName").value = "";
                document.getElementById("addCategoryName").value = "";
                document.getElementById("addLabelModal").style.display = "block";
            }

            // Close the modal when clicking outside of it
            window.onclick = function (event) {
                const modal = document.getElementById("addLabelModal");
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            }

            // Initialize row click for updating fields
            document.addEventListener("DOMContentLoaded", function () {
                const rows = document.querySelectorAll(".label-data-row");

                rows.forEach(row => {
                    row.addEventListener("click", function () {
                        const labelId = this.getAttribute("data-label-id");
                        const categoryName = this.getAttribute("data-category-name");
                        const labelName = this.getAttribute("data-label-name");

                        populateForm(labelId, categoryName, labelName);
                    });
                });
            });
            document.addEventListener("DOMContentLoaded", function () {
                // Open modal when "Add Label" button is clicked
                const addLabelButton = document.getElementById("addLabelButton"); // The button that opens the modal
                const addLabelModal = document.getElementById("addLabelModal");
                const closeModalButton = document.querySelector(".label-close-btn");

                // Ensure addLabelButton is defined before adding click event
                if (addLabelButton) {
                    addLabelButton.addEventListener("click", function () {
                        addLabelModal.style.display = "block";
                    });
                }

                // Close modal when the close button (×) is clicked
                if (closeModalButton) {
                    closeModalButton.addEventListener("click", function () {
                        addLabelModal.style.display = "none";
                    });
                }

                // Close modal when clicking outside the modal content
                window.addEventListener("click", function (event) {
                    if (event.target === addLabelModal) {
                        addLabelModal.style.display = "none";
                    }
                });
            });
        </script>
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


