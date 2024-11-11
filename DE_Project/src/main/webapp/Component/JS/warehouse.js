$(document).ready(function () {
  $("#btnInventory").click(function () {
    InventoryWare();
  });
});
function InventoryWare() {
  $.ajax({
    url: `${contextPath}/Warehouse/Inventory`,
    type: "GET",
    success: function (response) {},
    error: function (xhr, status, error) {
      console.error("Error exporting data:", error);
    },
  });
}
