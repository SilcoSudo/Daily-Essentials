$(document).ready(() => {
  // Lấy giá trị data-selected từ HTML
  const selectedProvince = $("#tinh").data("selected");
  const selectedDistrict = $("#quan").data("selected");
  const selectedWard = $("#phuong").data("selected");

  // Gọi API để lấy danh sách tỉnh thành
  $.getJSON("https://esgoo.net/api-tinhthanh/1/0.htm", function (data_tinh) {
    if (data_tinh.error == 0) {
      // Thêm option mặc định
      $("#tinh").html('<option value="0">Tỉnh thành</option>');
      // Duyệt qua danh sách tỉnh và thêm vào select box
      $.each(data_tinh.data, function (key_tinh, val_tinh) {
        let isSelected = selectedProvince == val_tinh.id ? "selected" : "";

        $("#tinh").append(
          '<option value="' +
            val_tinh.id +
            '" ' +
            isSelected +
            ">" +
            val_tinh.full_name +
            "</option>"
        );
      });
      // Nếu có tỉnh được chọn, tải danh sách quận huyện của tỉnh đó
      if (selectedProvince) {
        let idtinh = $("#tinh option:selected").val();
        loadDistricts(idtinh);
      } else {
        loadDistricts(selectedProvince);
      }

      // Sự kiện khi người dùng thay đổi tỉnh thành
      $("#tinh").change(function () {
        var idtinh = $(this).val();
        loadDistricts(idtinh); // Gọi hàm load quận/huyện khi thay đổi tỉnh
      });
    }
  });
  // Hàm tải danh sách quận/huyện
  function loadDistricts(provinceId) {
    $.getJSON(
      "https://esgoo.net/api-tinhthanh/2/" + provinceId + ".htm",
      function (data_quan) {
        if (data_quan.error == 0) {
          $("#quan").html('<option value="0">Quận Huyện</option>');
          $("#phuong").html('<option value="0">Phường Xã</option>');
          $.each(data_quan.data, function (key_quan, val_quan) {
            let isSelected = selectedDistrict == val_quan.id ? "selected" : "";

            $("#quan").append(
              '<option value="' +
                val_quan.id +
                '" ' +
                isSelected +
                ">" +
                val_quan.full_name +
                "</option>"
            );
          });
          // Nếu có quận/huyện được chọn, tải danh sách phường/xã của quận đó
          if (selectedDistrict) {
            let idquan = $("#quan option:selected").val();
            loadWards(idquan);
          } else {
            loadWards(selectedDistrict);
          }

          // Sự kiện khi người dùng thay đổi quận huyện
          $("#quan").change(function () {
            var idquan = $(this).val();
            loadWards(idquan); // Gọi hàm load phường/xã khi thay đổi quận/huyện
          });
        }
      }
    );
  }

  // Hàm tải danh sách phường/xã
  function loadWards(districtId) {
    $.getJSON(
      "https://esgoo.net/api-tinhthanh/3/" + districtId + ".htm",
      function (data_phuong) {
        if (data_phuong.error == 0) {
          $("#phuong").html('<option value="0">Phường Xã</option>');
          $.each(data_phuong.data, function (key_phuong, val_phuong) {
            let isSelected = selectedWard == val_phuong.id ? "selected" : "";
            $("#phuong").append(
              '<option value="' +
                val_phuong.id +
                '" ' +
                isSelected +
                ">" +
                val_phuong.full_name +
                "</option>"
            );
          });
        }
      }
    );
  }
});
