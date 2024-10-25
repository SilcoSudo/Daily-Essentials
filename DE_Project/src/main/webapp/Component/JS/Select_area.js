$(document).ready(function () {
  $.getJSON("https://esgoo.net/api-tinhthanh/1/0.htm", function (data_tinh) {
    if (data_tinh.error == 0) {
      $.each(data_tinh.data, function (key_tinh, val_tinh) {
        $("#tinh").append(
          '<option value="' +
            val_tinh.id +
            '">' +
            val_tinh.full_name +
            "</option>"
        );
      });
      $("#tinh").change(function (e) {
        var idtinh = $(this).val();
        $.getJSON(
          "https://esgoo.net/api-tinhthanh/2/" + idtinh + ".htm",
          function (data_quan) {
            if (data_quan.error == 0) {
              $("#quan").html('<option value="0">Quận Huyện</option>');
              $("#phuong").html('<option value="0">Phường Xã</option>');
              $.each(data_quan.data, function (key_quan, val_quan) {
                $("#quan").append(
                  '<option value="' +
                    val_quan.id +
                    '">' +
                    val_quan.full_name +
                    "</option>"
                );
              });
              $("#quan").change(function (e) {
                var idquan = $(this).val();
                $.getJSON(
                  "https://esgoo.net/api-tinhthanh/3/" + idquan + ".htm",
                  function (data_phuong) {
                    if (data_phuong.error == 0) {
                      $("#phuong").html('<option value="0">Phường Xã</option>');
                      $.each(
                        data_phuong.data,
                        function (key_phuong, val_phuong) {
                          $("#phuong").append(
                            '<option value="' +
                              val_phuong.id +
                              '">' +
                              val_phuong.full_name +
                              "</option>"
                          );
                        }
                      );
                    }
                  }
                );
              });
            }
          }
        );
      });
    }
  });

  $("#btnContinue").click(function () {
    var thanhPho = $("#tinh").val();
    var quanHuyen = $("#quan").val();
    var phuongXa = $("#phuong").val();

    if (thanhPho === "0" || quanHuyen === "0" || phuongXa === "0") {
      alert("Vui lòng chọn đầy đủ thông tin!");
    } else {
      $("#address-sa2-comfirm").html(
        `Địa chỉ: <strong>P. ${$("#phuong option:selected").text()}, Q. ${$(
          "#quan option:selected"
        ).text()}, TP. ${$("#tinh option:selected").text()}</strong>`
      );

      $("#sa1").addClass("hidden");
      $("#sa2").removeClass("hidden");
    }
  });

  $("#change-address").click(function () {
    $("#sa2").addClass("hidden");
    $("#sa1").removeClass("hidden");
  });

  $("#btnConfirm").click(function () {
    $("#popup").addClass("hidden");
    var thanhPho = $("#tinh option:selected").text();
    var quanHuyen = $("#quan option:selected").text();
    var phuongXa = $("#phuong option:selected").text();
    $.ajax({
      url: `${contextPath}/Orders/AddressInfo`,
      type: "POST",
      data: {
        thanhPho: thanhPho,
        quanHuyen: quanHuyen,
        phuongXa: phuongXa,
      },
      success: function (response) {
        var txtLocation = $(".location-address");
        txtLocation.html(
          `Địa chỉ đã chọn: P. ${phuongXa}, Q. ${quanHuyen}, TP. ${thanhPho}`
        );
      },
      error: function (xhr, status, error) {
        alert("Đã xảy ra lỗi khi gửi địa chỉ: " + error);
      },
    });
  });

  $("#btnLocation").click(function () {
    $("#popup").removeClass("hidden");
  });
});
