$(document).ready(function () {
  $("#txt_FindProduct").on("keypress", function (e) {
    if (e.which == 13) {
      // Mã phím 13 là Enter
      var searchQuery = $(this).val();

      $.ajax({
        url: `${contextPath}/Category/Search`,
        type: "GET",
        data: { search: searchQuery },
        success: function(response) {
          window.location.href = `${contextPath}/View/category.jsp`;
        },
        error: function (xhr, status, error) {
          console.error("Error fetching data: " + error);
        },
      });
    }
  });
});
