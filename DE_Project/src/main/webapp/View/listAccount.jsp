<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="UtilsFuction.Encryption" %>
<%@ page import="Model.Account" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="DAO.AccountDAO" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Account List</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/listAccount.css">
        <script>
            var contextPaths = '<%= request.getContextPath() %>';
            // Function to open the modal and populate it with account data
            function openModal(id, username, password, fullName, phone, email, role, status) {
                document.getElementById('accountId').value = id;
                document.getElementById('username').value = username;
                document.getElementById('password').value = password;
                document.getElementById('fullName').value = fullName;
                document.getElementById('phone').value = phone;
                document.getElementById('email').value = email;
                var roleSelect = document.getElementById('role');
                for (var i = 0; i < roleSelect.options.length; i++) {
                    if (roleSelect.options[i].value === role) {
                        roleSelect.selectedIndex = i;
                        break;
                    }
                }

                if (status === "Active") {
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
            // Confirmation function for deletion
            function confirmDelete() {
                return confirm("Are you sure you want to delete this account?");
            }

            // Confirmation function for update
            function confirmUpdate() {
                return confirm("Are you sure you want to update this account?");
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
        <div class="w-container">
            <jsp:include page="headers.jsp"></jsp:include>
                <div class="container">
                    <form action="${pageContext.request.contextPath}/ManageAccount" method="POST">
                    <div class="filter-section">
                        <div class="filter-item">
                            <label for="search-accountId">Account ID</label>
                            <input type="text" id="search-accountId" name="search-accountId" placeholder="All" 
                                   value="<%= request.getParameter("search-accountId") != null ? request.getParameter("search-accountId") : "" %>">
                        </div>
                        <div class="filter-item">
                            <label for="status">Status</label>
                            <select id="status" name="status">
                                <option value="">All</option>
                                <option value="active" <%= "active".equals(request.getParameter("status")) ? "selected" : "" %>>Active</option>
                                <option value="locked" <%= "locked".equals(request.getParameter("status")) ? "selected" : "" %>>Locked</option>
                            </select>
                        </div>
                        <div class="filter-item">
                            <label for="search-username">Username</label>
                            <input type="text" id="search-username" name="search-username" placeholder="All" 
                                   value="<%= request.getParameter("search-username") != null ? request.getParameter("search-username") : "" %>">
                        </div>
                        <button type="submit" name="btnAction" value="search" class="filter-btn">Search</button>
                    </div>
                </form>

                <div class="account-list-table">
                    <table class="account-table">
                        <thead>
                            <tr>
                                <th class="id-column">ID</th>
                                <th class="username-column">Username</th>
                                <th class="fullname-column">Full Name</th>
                                <th class="phone-column">Phone</th>
                                <th class="email-column">Email</th>
                                <th>Role</th>
                                <th>Status</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ResultSet rs = (ResultSet) request.getSession().getAttribute("accountResultSet");

                                if (rs == null) {
                                    rs = accountDAO.getAllAccountsResultSet();
                                }

                                boolean hasAccounts = false;
                                if (rs != null) {
                                    while (rs.next()) {
                                        hasAccounts = true;
                                        String decryptedPassword = "";
                                        try {
                                            decryptedPassword = Encryption.decrypt(rs.getString("password"));
                                        } catch (Exception e) {
                                            decryptedPassword = "Decryption Error";
                                            e.printStackTrace();
                                        }
                            %>
                            <tr onclick="openModal('<%= rs.getInt("account_id") %>', '<%= rs.getString("username") %>', '<%= decryptedPassword %>', '<%= rs.getString("user_fullname") %>', '<%= rs.getString("user_phone") %>', '<%= rs.getString("user_email") %>', '<%= rs.getString("role") %>', '<%= rs.getBoolean("is_lock") ? "Locked" : "Active" %>')">
                                <td class="id-column"><%= rs.getInt("account_id") %></td>
                                <td class="username-column"><%= rs.getString("username") %></td>
                                <td class="fullname-column"><%= rs.getString("user_fullname") %></td>
                                <td class="phone-column"><%= rs.getString("user_phone") %></td>
                                <td class="email-column"><%= rs.getString("user_email") %></td>
                                <td><%= rs.getString("role") %></td>
                                <td><%= rs.getBoolean("is_lock") ? "Locked" : "Active" %></td>
                                <td><%= rs.getDate("update_at") != null ? rs.getDate("update_at").toString() : "N/A" %></td>
                            </tr>
                            <%
                                    }
                                }
                                if (!hasAccounts) {
                            %>
                            <tr>
                                <td colspan="8" style="text-align: center;">No accounts found.</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- Modal for Editing Account -->
        <div id="accountModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h3>Account Information</h3>
                <form action="${pageContext.request.contextPath}/ManageAccount" method="post">
                    <input type="hidden" name="id" id="accountId">

                    <label for="username">Username</label>
                    <input type="text" name="username" id="username" required>

                    <label for="password">Password</label>
                    <input type="text" name="password" id="password" required>

                    <label for="fullName">Full Name</label>
                    <input type="text" name="fullName" id="fullName" required>

                    <label for="phone">Phone</label>
                    <input type="text" name="phone" id="phone" required>

                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" required>

                    <!-- Role Selection -->
                    <label for="role">Role</label>
                    <select name="role" id="role" required>
                        <option value="customer">Customer</option>
                        <option value="staff">Staff</option>
                        <option value="admin">Admin</option>
                    </select>

                    <!-- Status -->
                    <label>Status</label>
                    <div class="status-container">
                        <input type="radio" id="statusActive" name="status" value="Active">
                        <label for="statusActive"><span>Active</span></label>

                        <input type="radio" id="statusLocked" name="status" value="Locked">
                        <label for="statusLocked"><span>Locked</span></label>
                    </div>

                    <div class="modal-actions">
                        <button type="submit" class="btn-delete" name="btnAction" value="delete" onclick="return confirmDelete()">Delete</button>
                        <button type="submit" class="btn-save" name="btnAction" value="update" onclick="return confirmUpdate()">Update</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
