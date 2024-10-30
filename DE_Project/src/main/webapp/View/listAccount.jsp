<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Models.Account" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="DAO.AccountDAO" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Tài Khoản</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/listAccount.css">
        <script>
            // Function to open the modal and populate it with account data
            function openModal(id, username, password, fullName, phone, email, role, status) {
                document.getElementById('accountId').value = id;
                document.getElementById('username').value = username;
                document.getElementById('password').value = password;
                document.getElementById('fullName').value = fullName;
                document.getElementById('phone').value = phone;
                document.getElementById('email').value = email;
                document.getElementById('role').value = role;

                if (status === "Hoạt động") {
                    document.getElementById('statusActive').checked = true;
                } else {
                    document.getElementById('statusLocked').checked = true;
                }

                // Show the modal
                document.getElementById('accountModal').style.display = 'block';
            }

            // Function to close the modal
            function closeModal() {
                document.getElementById('accountModal').style.display = 'none';
            }

            // Function to delete the account
            function deleteAccount() {
                if (confirm("Xác nhận xóa?")) {
                    var id = document.getElementById('accountId').value;
                    window.location.href = "AccountController?action=delete&id=" + id;
                }
            }
            function updateAccount() {
                if (confirm("Xác nhận lưu?")) {
                    var id = document.getElementById('accountId').value;
                    window.location.href = "AccountController?action=update&id=" + id;
                }
            }
        </script>
    </head>
    <body>

        <%
            
            AccountDAO accountDAO = new AccountDAO();

            
            String searchId = request.getParameter("search-accountId");
            String searchUsername = request.getParameter("search-username");
            String status = request.getParameter("status");

         
        %>
        <div class="container">
            <form method="post" action="AccountController">
                <input type="hidden" name="action" value="search">
                <div class="filter-section">
                    <div class="filter-item">
                        <label for="search-accountId">ID tài khoản</label>
                        <input type="text" id="search-accountId" name="search-accountId" placeholder="Tất cả" 
                               value="<%= request.getParameter("search-accountId") != null ? request.getParameter("search-accountId") : "" %>">
                    </div>
                    <div class="filter-item">
                        <label for="status">Trạng thái</label>
                        <select id="status" name="status">
                            <option value="">Tất cả</option>
                            <option value="active" <%= "active".equals(request.getParameter("status")) ? "selected" : "" %>>Hoạt động</option>
                            <option value="locked" <%= "locked".equals(request.getParameter("status")) ? "selected" : "" %>>Đã khóa</option>
                        </select>
                    </div>
                    <div class="filter-item">
                        <label for="search-username">Username</label>
                        <input type="text" id="search-username" name="search-username" placeholder="Tất cả" 
                               value="<%= request.getParameter("search-username") != null ? request.getParameter("search-username") : "" %>">
                    </div>
                    <button type="submit" class="filter-btn">Tìm</button>
                </div>
            </form>

            <button type="submit" class="btn-create" href="register.jsp" >Tạo tài khoản</button>

            <table class="account-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Tên</th>
                        <th>Phone</th>
                        <th>Mail</th>
                        <th>Role</th>
                        <th>Trạng thái</th>
                        <th>Cập nhật</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        ResultSet rs = (ResultSet) request.getAttribute("accountResultSet");

                        // If no filtered results are available, retrieve all accounts
                        if (rs == null) {
                            rs = accountDAO.getAllAccountsResultSet(); // Retrieve all accounts if filtered result is null
                        }

                        boolean hasAccounts = false; // Flag to track if any accounts are found
                        if (rs != null) {
                            while (rs.next()) {
                                hasAccounts = true; // We found at least one account
                    %>
                    <tr onclick="openModal('<%= rs.getInt("account_id") %>', '<%= rs.getString("username") %>', '<%= rs.getString("password") %>', '<%= rs.getString("user_fullname") %>', '<%= rs.getString("user_phone") %>', '<%= rs.getString("user_email") %>', '<%= rs.getString("role") %>', '<%= rs.getBoolean("is_lock") ? "Đã khóa" : "Hoạt động" %>')">
                        <td><%= rs.getInt("account_id") %></td>
                        <td><%= rs.getString("username") %></td>
                        <td><%= rs.getString("user_fullname") %></td>
                        <td><%= rs.getString("user_phone") %></td>
                        <td><%= rs.getString("user_email") %></td>
                        <td><%= rs.getString("role") %></td>
                        <td><%= rs.getBoolean("is_lock") ? "Đã khóa" : "Hoạt động" %></td>
                        <td><%= rs.getDate("update_at") != null ? rs.getDate("update_at").toString() : "N/A" %></td>
                    </tr>
                    <%
                            }
                        }
        
                        // If no accounts are found, display a message
                        if (!hasAccounts) {
                    %>
                    <tr>
                        <td colspan="8" style="text-align: center;">Không tìm thấy tài khoản nào.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>

            </table>
        </div>

        <!-- Modal for Editing Account -->
        <div id="accountModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h3>Thông tin tài khoản</h3>
                <form action="AccountController" method="post">
                    <input type="hidden" name="action" value="updatestaff">
                    <input type="hidden" name="id" id="accountId">

                    <label for="username">Tài khoản</label>
                    <input type="text" name="username" id="username" required>

                    <label for="password">Mật khẩu</label>
                    <input type="text" name="password" id="password" required>

                    <label for="fullName">Tên</label>
                    <input type="text" name="fullName" id="fullName" required>

                    <label for="phone">Điện thoại</label>
                    <input type="text" name="phone" id="phone" required>

                    <label for="email">Mail</label>
                    <input type="email" name="email" id="email" required>

                    <!-- Role Selection -->
                    <label for="role">Vai trò</label>
                    <select name="role" id="role" required>
                        <option value="customer">Customer</option>
                        <option value="staff">Staff</option>
                        <option value="admin">Admin</option>
                    </select>

                    <!-- Status -->
                    <label>Trạng thái</label>
                    <div class="status-container">
                        <input type="radio" id="statusActive" name="status" value="Hoạt động">
                        <label for="statusActive"><span>Mở</span></label>

                        <input type="radio" id="statusLocked" name="status" value="Đã khóa">
                        <label for="statusLocked"><span>Khóa</span></label>
                    </div>

                    <div class="modal-actions">
                        <button type="button" class="btn-delete" onclick="deleteAccount()">Xóa</button>
                        <button type="submit" class="btn-save" onclick="updateAccount()">Lưu</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
