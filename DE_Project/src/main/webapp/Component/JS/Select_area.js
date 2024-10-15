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
    var thanhPho = $("#tinh option:selected").text();
    var quanHuyen = $("#quan option:selected").text();
    var phuongXa = $("#phuong option:selected").text();

    if (
      thanhPho === "Tỉnh thành" ||
      quanHuyen === "Quận Huyện" ||
      phuongXa === "Phường Xã"
    ) {
      alert("Vui lòng chọn đầy đủ thông tin!");
    } else {
      $("#address-sa2-comfirm").html(
        `Địa chỉ: <strong>P. ${phuongXa}, Q. ${quanHuyen}, TP. ${thanhPho}</strong>`
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
  });
});
