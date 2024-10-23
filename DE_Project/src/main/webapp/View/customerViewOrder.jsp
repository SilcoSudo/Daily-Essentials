<%-- 
    Document   : customerViewOrder
    Created on : Oct 18, 2024, 4:33:42 PM
    Author     : nhatl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lịch sử đơn hàng</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/customerViewOrder.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/customerInfo.css">
    </head>
    <body>    
            <div class="container">
                <div class="side-menu">
                    <div class="side-menu-item">
                        <img src="${pageContext.request.contextPath}/Component/IMG/account-icon.png" alt="User Icon">
                    <a href="${pageContext.request.contextPath}/Home/Info">Tài khoản</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/order-icon.png" alt="Order Icon">
                    <a href="${pageContext.request.contextPath}/CustomerViewOrder">Quản lý đơn hàng</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/product-icon.png" alt="Product Icon">
                    <a href="productDetail.html">Sản phẩm đã mua</a>
                </div>
                <div class="side-menu-item">
                    <img src="${pageContext.request.contextPath}/Component/IMG/logout-icon.png" alt="Logout Icon">
                    <a href="${pageContext.request.contextPath}/Authen/Logout">Đăng xuất</a>
                </div>
            </div>

            <div class="main-content">
                <h2>Danh sách đơn hàng</h2>
                <div class="filter-section">
                    <div style = "display: ruby;">
                        <label>ID đơn :</label>
                        <input type="text" id="searchOrderId" placeholder="Nhập ID đơn"/>
                    </div>
                    <div style = "display: ruby;">
                        <label>Tình trạng đơn :</label>
                        <select>
                            <option>Đang xử lý</option>
                            <option>Ðã xác nhận</option>
                            <option>Đang vận chuyển</option>
                            <option>Hoàn thành</option>
                            <option>Ðã hủy</option>
                        </select>
                    </div>
                    <div style = "display: ruby;">
                        <label>Tổng số tiền :</label>
                        <input type="text" id="searchTotalAmount" placeholder="Nhập số tiền" />
                    </div>
                </div>
                <div class="filter-section">
                    <div style = "display: ruby;">
                        <label for="Date"> Ngày tạo :</label>
                        <input type="Date"  id="searchDate">
                    </div>
                </div>

                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>ID Đơn hàng</th>
                                <th>Ngày mua</th>
                                <th>Tổng tiền</th>
                                <th>Trạng thái đơn hàng</th>
                                <th>Ghi chú</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>#0001F2</td>
                                <td>10/09/2024</td>
                                <td>140.000 đ</td>
                                <td>Hoàn thành</td>
                                <td>...</td>
                            </tr>       
                        </tbody>
                    </table>
                </div>
            </div>     
        </div>

        <script>
            document.getElementById('searchOrderId').addEventListener('input', function () {
                var searchId = this.value.toLowerCase();
                var rows = document.querySelectorAll('#orderBody tr');

                rows.forEach(function (row) {
                    var orderId = row.cells[0].textContent.toLowerCase();
                    if (orderId.includes(searchId) || searchId === '') {
                        row.style.display = '';
                    } else {
                        row.style.display = 'none';
                    }
                });
            });

            document.getElementById('searchTotalAmount').addEventListener('input', function () {
                var searchAmount = this.value.toLowerCase();
                var rows = document.querySelectorAll('#orderBody tr');

                rows.forEach(function (row) {
                    var totalAmount = row.cells[2].textContent.toLowerCase();
                    if (totalAmount.includes(searchAmount) || searchAmount === '') {
                        row.style.display = '';
                    } else {
                        row.style.display = 'none';
                    }
                });
            });

            document.getElementById('searchDate').addEventListener('input', function () {
                var searchDate = this.value;
                var rows = document.querySelectorAll('#orderBody tr');

                rows.forEach(function (row) {
                    var orderDate = row.cells[4].textContent; // Lấy giá trị từ ô ngày tạo
                    if (orderDate.includes(searchDate) || searchDate === '') {
                        row.style.display = '';
                    } else {
                        row.style.display = 'none';
                    }
                });
            });
        </script>
    </body>
</html>

