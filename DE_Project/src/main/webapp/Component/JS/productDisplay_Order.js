$(document).ready(function () {
  $(".product-price").each(function () {
    var priceText = $(this).text().replace(" ₫", "");
    var formattedPrice = formatPrice(priceText);
    $(this).text(formattedPrice + " ₫");
  });

  let debounceTimer;
  let quantityChanges = {};

  $(".increase-btn").click(function () {
    let productId = $(this).data("product-id");
    updateQuantityProduct(productId, 1);
    trackQuantityChange(productId);
  });

  $(".decrease-btn").click(function () {
    let productId = $(this).data("product-id");
    updateQuantityProduct(productId, -1);
    trackQuantityChange(productId);
  });

  $(".product-quantity .number").each(function () {
    updateButtonState($(this));
  });

  $(".product-quantity .number").on("focus", function () {
    $(this).data("previous-value", $(this).val());
  });

  $(".product-quantity .number").on("input", function () {
    let productId = $(this).attr("id").split("-")[1];
    let maxQuantity = parseInt($(this).data("product-quantity"), 10);
    let inputValue = $(this).val();
    let previousValue = $(this).data("previous-value");

    if (!/^\d*$/.test(inputValue)) {
      return;
    }

    let newQuantity = parseInt(inputValue) || 0;

    setTimeout(() => {
      if (newQuantity > maxQuantity) {
        $(this).val(previousValue);
      } else {
        $(this).val(newQuantity);
      }
      updateButtonState($(this));
      trackQuantityChange(productId);
    }, 1000);
  });

  $("#btnDeleteCart").click(() => {
    $.ajax({
      url: `${contextPath}/Cart/RemoveCart`,
      method: "POST",
      success: () => {
        window.location.href = `${contextPath}/Orders`;
      },
      error: (xhr) => {
        alert(xhr.responseText);
        window.location.href = `${contextPath}/Orders`;
      },
    });
  });
  $(".btnRemoveItem").click(function () {
    let productId = $(this).data("product-id");

    $.ajax({
      url: `${contextPath}/Cart/RemoveItemInCart`,
      method: "POST",
      data: { productId: productId },
      success: function (response) {
        $(this).closest("figure").remove();
        location.reload();
      },
      error: function (xhr) {
        alert("Cannot remove: " + xhr.statusText);
      },
    });
  });

  $("#btnPay").click(() => {
    $.ajax({
      url: `${contextPath}/Payment/pay`,
      method: "POST",
      success: () => {
        window.location.href = `${contextPath}/Home`;
      },
      error: (xhr) => {
        alert(xhr.responseText);
        window.location.href = `${contextPath}/Orders`;
      },
    });
  });

  function updateCartQuantity(productId, quantity) {
    const url = `${contextPath}/Cart/UpdateQuantity`;

    $.ajax({
      url: url,
      method: "POST",
      data: { productId: productId, quantity: quantity },
      dataType: "json",
      success: function (response) {
        if (response.success) {
          location.reload();
        } else {
          alert("Update failed :(");
        }
      },
      error: function (xhr) {
        alert("Error: " + xhr.statusText);
      },
    });
  }

  function trackQuantityChange(productId) {
    clearTimeout(debounceTimer);

    debounceTimer = setTimeout(function () {
      const quantityElement = $(`#quantity-${productId}`);
      const currentQuantity = parseInt(quantityElement.val());
      updateCartQuantity(productId, currentQuantity);
    }, 1000);
  }

  $(document).on("click", ".product-img, .product-name", function (event) {
    const productId = $(this).closest("figure").data("product-id");
    if (productId) {
      window.location.href = `${contextPath}/Product/Detail?id=${productId}`;
    }
  });
});

function updateQuantityProduct(productId, change) {
  let quantityElement = $(`#quantity-${productId}`);
  let currentQuantity = parseInt(quantityElement.val());
  let maxQuantity = parseInt(quantityElement.data("product-quantity"));

  let newQuantity = currentQuantity + change;
  if (change > 0) {
    if (newQuantity > maxQuantity) {
      return;
    }
  } else if (change < 0) {
    if (currentQuantity <= 1) {
      return;
    }
  }

  quantityElement.val(newQuantity);
  updateButtonState(quantityElement);
}

function updateButtonState(quantityElement) {
  let currentQuantity = parseInt(quantityElement.val(), 10);
  let maxQuantity = parseInt(quantityElement.data("product-quantity"), 10);

  if (currentQuantity <= 1) {
    quantityElement.siblings(".decrease-btn").addClass("disabled");
  } else {
    quantityElement.siblings(".decrease-btn").removeClass("disabled");
  }

  if (currentQuantity >= maxQuantity) {
    quantityElement.siblings(".increase-btn").addClass("disabled");
  } else {
    quantityElement.siblings(".increase-btn").removeClass("disabled");
  }
}

function formatPrice(price) {
  return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
