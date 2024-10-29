$(document).ready(function () {
  $("#txt_FindProduct").on("keypress", function (e) {
    if (e.which == 13) {
      // Mã phím 13 là Enter
      var searchQuery = $(this).val();

      $.ajax({
        url: `${contextPath}/Category/Search`,
        type: "GET",
        data: { search: searchQuery },
        success: function (response) {
          window.location.href = `${contextPath}/Category/View`;
        },
        error: function (xhr, status, error) {
          console.error("Error fetching data: " + error);
        },
      });
    }
  });

  $(".category_item").click(function () {
    const categoryId = $(this).attr("id");

    const targetUrl = `${contextPath}/Category/Search`;
    $.ajax({
      url: targetUrl,
      type: "POST",
      data: { categoryId: categoryId },
      success: function () {
        window.location.href = `${contextPath}/Category/View`;
      },
      error: function (xhr, status, error) {
        console.log("Có lỗi xảy ra:", error);
      },
    });
  });
});
