document.addEventListener('DOMContentLoaded', () => {
    const sidebar = document.getElementById('sidebar');
    const toggleBtn = document.getElementById('toggle-btn');
    const logoText = document.getElementById('sidebar-logo-text');

    toggleBtn.addEventListener('click', () => {
        sidebar.classList.toggle('collapsed');
        if (sidebar.classList.contains('collapsed')) {
            logoText.textContent = "DE"; 
        } else {
            logoText.textContent = "Daily Essentials"; 
        }
    });

    const trangThaiOptions = ['Tất cả', 'Trống', 'Đầy'];
    const loaiKhoOptions = ['Tất cả', 'Công nghệ', 'Thực phẩm'];
    const nhanOptions = ['Tất cả', 'Sữa', 'Café', 'Nước ngọt'];

    function populateDropdown(id, options) {
        const dropdown = document.getElementById(id);
        options.forEach(option => {
            const opt = document.createElement('option');
            opt.value = option;
            opt.textContent = option;
            dropdown.appendChild(opt);
        });
    }

    populateDropdown('trang-thai', trangThaiOptions);
    populateDropdown('loai-kho', loaiKhoOptions);
    populateDropdown('nhan', nhanOptions);

    const warehouseData = [
        { name: 'Kho Cà Mau', code: 'WH001', address: '117 Tran Binh Trong, k5 p5', capacity: '443/800', type: 'Công nghệ', status: 'Còn trống' },
        { name: 'Kho Cà Mau', code: 'WH001', address: '117 Tran Binh Trong, k5 p5', capacity: '443/800', type: 'Công nghệ', status: 'Còn trống' },
        { name: 'Kho Cà Mau', code: 'WH001', address: '117 Tran Binh Trong, k5 p5', capacity: '443/800', type: 'Công nghệ', status: 'Còn trống' },
        { name: 'Kho Cà Mau', code: 'WH001', address: '117 Tran Binh Trong, k5 p5', capacity: '443/800', type: 'Công nghệ', status: 'Còn trống' },
        { name: 'Kho Cà Mau', code: 'WH001', address: '117 Tran Binh Trong, k5 p5', capacity: '443/800', type: 'Công nghệ', status: 'Còn trống' },
        { name: 'Kho Cà Mau', code: 'WH001', address: '117 Tran Binh Trong, k5 p5', capacity: '443/800', type: 'Công nghệ', status: 'Còn trống' },
        { name: 'Kho Cà Mau', code: 'WH001', address: '117 Tran Binh Trong, k5 p5', capacity: '443/800', type: 'Công nghệ', status: 'Còn trống' },
        { name: 'Kho Cà Mau', code: 'WH001', address: '117 Tran Binh Trong, k5 p5', capacity: '443/800', type: 'Công nghệ', status: 'Còn trống' },
    ];

    const warehouseTable = document.querySelector('.warehouse-table tbody');

    function renderWarehouseTable() {
        warehouseTable.innerHTML = '';
        warehouseData.slice(0, 6).forEach(item => {
            const row = `<tr>
                <td>${item.name}</td>
                <td>${item.code}</td>
                <td>${item.address}</td>
                <td>${item.capacity}</td>
                <td>${item.type}</td>
                <td>${item.status}</td>
            </tr>`;
            warehouseTable.innerHTML += row;
        });
    }

    renderWarehouseTable();

    const productData = [
        { id: 'P001', name: 'Laptop Dell', category: 'Công nghệ', price: '15,000,000', stock: '100' },
        { id: 'P002', name: 'Smartphone Samsung', category: 'Công nghệ', price: '8,000,000', stock: '150' },
        { id: 'P003', name: 'Sữa tươi Vinamilk', category: 'Thực phẩm', price: '25,000', stock: '300' },
        { id: 'P004', name: 'Café G7', category: 'Thực phẩm', price: '50,000', stock: '250' },
    ];

    const productTable = document.querySelector('.product-table tbody');

    function renderProductTable() {
        productTable.innerHTML = '';
        productData.forEach(item => {
            const row = `<tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.category}</td>
                <td>${item.price}</td>
                <td>${item.stock}</td>
            </tr>`;
            productTable.innerHTML += row;
        });
    }

    renderProductTable();

    const labelData = [
        { id: 'CT001', category: 'Sữa các loại', label: 'Sữa tươi' },
        { id: 'CT001', category: 'Sữa các loại', label: 'Sữa tươi' }
    ];

    const labelTable = document.querySelector('.label-table tbody');

    function renderLabelTable() {
        labelTable.innerHTML = '';
        labelData.forEach(item => {
            const row = `<tr>
                <td>${item.id}</td>
                <td>${item.category}</td>
                <td>${item.label}</td>
            </tr>`;
            labelTable.innerHTML += row;
        });
    }

    renderLabelTable();


});
