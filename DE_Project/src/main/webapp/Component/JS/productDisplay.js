let currentIndex = 0;
const images = document.querySelectorAll(".carousel ul li");
const totalImages = images.length;

document.querySelectorAll(".btnCarousel").forEach((button, index) => {
  button.addEventListener("click", () => {
    if (index === 0) {
      currentIndex = (currentIndex - 1 + totalImages) % totalImages;
    } else {
      currentIndex = (currentIndex + 1) % totalImages;
    }
    document.querySelector(".carousel ul").style.transform = `translateX(-${
      currentIndex * 100
    }%)`;
  });
});

$(document).ready(function () {
  function formatPrice(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
  }
  $(".product-price").each(function () {
    var priceText = $(this).text().replace(" ₫", "");
    var formattedPrice = formatPrice(priceText);
    $(this).text(formattedPrice + " ₫");
  });
  var offset = 0;
  function displayProducts(products) {
    products.forEach(function (product) {
      var formattedPrice = formatPrice(product.productPrice);
      var productHtml = `
              <figure class="product-item">
                  <div class="w-product-item">
                      <img src="${contextPath}/${product.imageUrl}" class="product-img" />
                      <div class="product-title">${product.productName}</div>
                      <div class="product-price">${formattedPrice} ₫</div>
                      <button class="btnQuaityProduct" value=${product.productId}>
                          <div class="w-quantityProduct">
                              <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 576 512">
                                  <path class="icon-path" d="M0 24C0 10.7 10.7 0 24 0L69.5 0c22 0 41.5 12.8 50.6 32l411 0c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3l-288.5 0 5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5L488 336c13.3 0 24 10.7 24 24s-10.7 24-24 24l-288.3 0c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5L24 48C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96zM252 160c0 11 9 20 20 20l44 0 0 44c0 11 9 20 20 20s20-9 20-20l0-44 44 0c11 0 20-9 20-20s-9-20-20-20l-44 0 0-44c0-11-9-20-20-20s-20 9-20 20l0 44-44 0c-11 0-20 9-20 20z"/>
                              </svg>
                              <p class="textQuantityProduct">Thêm vào giỏ hàng</p>
                          </div>
                      </button>
                  </div>
              </figure>
          `;
      $(".w-products").append(productHtml);
    });
  }

  function loadAllProducts() {
    $.ajax({
      url: `${contextPath}/Product/ViewAll`,
      method: "GET",
      success: function (response) {
        displayProducts(response);
      },
      error: function () {
        alert("Không thể tải thêm sản phẩm.");
      },
    });
  }

  $(".load-more-btn").click(function () {
    loadAllProducts();
    $(this).hide();
  });
  $("button[name='btnAddCart']").click(function () {
    var productId = $(this).val();
    addToCart(productId);
  });

  function addToCart(productId) {
    $.ajax({
      url: `${contextPath}/Cart/Add`,
      method: "POST",
      data: { productId: productId, quantity: 1 },
      success: function (response) {
        updateCartCount();
      },
      error: function (xhr, status, error) {
        if (xhr.status === 401) {
          window.location.href = `${contextPath}/Authen/Login`;
        } else {
          alert("Không thể thêm sản phẩm vào giỏ hàng.");
        }
      },
    });
  }

  function updateCartCount() {
    var currentCount = parseInt($("#cart-count").text());
    currentCount += 1;
    $("#cart-count").text(currentCount);
  }
});
