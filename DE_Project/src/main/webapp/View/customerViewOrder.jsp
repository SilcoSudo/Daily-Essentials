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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Component/CSS/homeCustomer.css">
        
    </head>
    <body>
        <!-- Navbar -->
        <nav class="navbar">
            <div class="menu-container">
                <div class="logo-container">
                    <a href="../View/homeCustomer.html" class="logo">Daily Essentials</a>
                </div>
                <div class="search-container">
                    <div class="find-products">
                        <img src="../Component/IMG/ic-find.svg" class="icon ic-find" alt="Search Icon">
                        <input type="text" id="txt_FindProduct" placeholder="Giao hàng nhanh 3h, Giảm 20% sản phẩm Thịt Navi">
                    </div>
                </div>
                <div class="right-container">
                    <a href="#" class="cart-link">
                        <img src="../Component/IMG/ic-cart.svg" alt="Cart Icon" class="icon">
                        <span>Giỏ hàng (0)</span>
                    </a>
                    <a href="login.html" class="user-link">
                        <img src="../Component/IMG/ic-user.svg" alt="User Icon" class="icon">
                        <span>Khách hàng</span>
                    </a>
                </div>
            </div>
        </nav>

        <div class="product-menu-wrapper">
            <div class="product-menu-left">
                <button class="menu-btn">
                    <img src="../Component/IMG/ic-bars.svg" class="icon ic-bars" alt="Bars Icon">
                    Danh mục sản phẩm
                </button>
            </div>

            <div class="product-menu-right">
                <button class="purchase-btn">
                    <img src="../Component/IMG/ic-cart2.svg" class="icon ic-cart2" alt="Cart Icon">
                    Sản phẩm đã mua
                </button>
            </div>
        </div>

        <div class="customerViewOrder-container">
            <div class="sidebar">
                <div class="side-menu-item">
                    <img src="../Component/IMG/account-icon.png" alt="User Icon">
                    <a href="customerInfo.html">Tài khoản</a>
                </div>
                <div class="side-menu-item">
                    <img src="../Component/IMG/order-icon.png" alt="Order Icon">
                    <a href="customerViewOrder.htmlViewOrder.html">Quản lý đơn hàng</a>
                </div>
                <div class="side-menu-item">
                    <img src="../Component/IMG/product-icon.png" alt="Product Icon">
                    <a href="productDetail.html">Sản phẩm đã mua</a>
                </div>
                <div class="side-menu-item">
                    <img src="../Component/IMG/logout-icon.png" alt="Logout Icon">
                    <a href="#">Đăng xuất</a>
                </div>
            </div>

            <div class="main-content">
                <h2>Danh sách đơn hàng</h2>
                <div class="filter-section">
                    <div>
                        <label>ID đơn :</label>
                        <input type="text" placeholder="Nhập ID đơn" />
                    </div>
                    <div>
                        <label>Trạng thái đơn hàng :</label>
                        <select>
                            <option>Đang xử lý</option>
                            <option>Ðã xác nhận</option>
                            <option>Đang vận chuyển</option>
                            <option>Hoàn thành</option>
                            <option>Ðã hủy</option>
                        </select>
                    </div>
                    <div>
                        <label>Tổng số tiền :</label>
                        <input type="text" placeholder="Nhập số tiền" />
                    </div>
                </div>
                <div class="filter-section">
                    <div>
                        <label for="Date"> Ngày tạo :</label>
                        <input type="Date" value="2024-03-20">
                    </div>
                    <button>Tìm</button>
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
                            <tr>
                                <td>#0001F2</td>
                                <td>10/09/2024</td>
                                <td>140.000 đ</td>
                                <td>Hoàn thành</td>
                                <td>...</td>
                            </tr>
                            <tr>
                                <td>#0001F2</td>
                                <td>10/09/2024</td>
                                <td>140.000 đ</td>
                                <td>Hoàn thành</td>
                                <td>...</td>
                            </tr>
                            <tr>
                                <td>#0001F2</td>
                                <td>10/09/2024</td>
                                <td>140.000 đ</td>
                                <td>Hoàn thành</td>
                                <td>...</td>
                            </tr>
                            <tr>
                                <td>#0001F2</td>
                                <td>10/09/2024</td>
                                <td>140.000 đ</td>
                                <td>Hoàn thành</td>
                                <td>...</td>
                            </tr>
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

        </script>
    </body>
</html>

