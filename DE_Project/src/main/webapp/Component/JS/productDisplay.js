let currentIndex = 0;
const images = document.querySelectorAll(".carousel ul li");
const totalImages = images.length;

// Hiển thị thêm sản phẩm
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
  // Thực hiển chuyển đổi
  $(".product-price").each(function () {
    var priceText = $(this).text().replace(" ₫", "");
    var formattedPrice = formatPrice(priceText);
    $(this).text(formattedPrice + " ₫");
  });

  // Thực hiển load sản phẩm
  $(".load-more-btn").click(function () {
    loadAllProducts();
    $(this).hide();
  });

  // Thực hiện tăng số lượng sản phẩm
  $(".increase-btn").click(function () {
    let productId = $(this).data("product-id");
    updateCartQuantity(productId, "increase");
  });

  // Thực hiện giảm số lượng sản phẩm
  $(".decrease-btn").click(function () {
    let productId = $(this).data("product-id");
    updateCartQuantity(productId, "decrease");
  });

  // Nhấn vào sản phẩm xem chi tiết
  $(document).on("click", ".product-img, .product-title", function (event) {
    const productId = $(this).closest("figure").data("product-id");
    if (productId) {
      window.location.href = `${contextPath}/Product/Detail?id=${productId}`;
    }
  });
});

// Hàm tăng số lượng (text) sản phẩm trong giỏ hàng khi thêm sản phẩm vào giỏ hàng
function updateCartCount(increase) {
  var currentCount = parseInt($("#cart-count").text());
  if (increase == 1) {
    currentCount += 1;
  } else {
    currentCount -= 1;
  }

  $("#cart-count").text(currentCount);
}

// Hàm tăng/ giảm số lượng số lượng sản phẩm trong giỏ hàng
function updateCartQuantity(productId, action) {
  const url = `${contextPath}/Cart/UpdateQuantity`;
  const increase = action === "increase";

  const quantityElement = $(`#quantity-${productId}`);
  let currentQuantity = parseInt(quantityElement.text());

  let quantity = increase ? currentQuantity + 1 : currentQuantity - 1;
  console.log(quantity);

  $.ajax({
    url: url,
    method: "POST",
    data: { productId: productId, quantity: quantity },
    success: function () {
      if (increase) {
        quantityElement.text(++currentQuantity);
      } else {
        if (currentQuantity > 1) {
          quantityElement.text(--currentQuantity);
        } else {
          quantityElement.closest(".w-quantityProduct2").replaceWith(`
            <button class="btnQuaityProduct" name="btnAddCart" value="${productId}" onclick="addToCart(${productId})">
              <div class="w-quantityProduct">
                <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 576 512">
                  <path class="icon-path" d="M0 24C0 10.7 10.7 0 24 0L69.5 0c22 0 41.5 12.8 50.6 32l411 0c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3l-288.5 0 5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5L488 336c13.3 0 24 10.7 24 24s-10.7 24-24 24l-288.3 0c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5L24 48C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96zM252 160c0 11 9 20 20 20l44 0 0 44c0 11 9 20 20 20s20-9 20-20l0-44 44 0c11 0 20-9 20-20s-9-20-20-20l-44 0 0-44c0-11-9-20-20-20s-20 9-20 20l0 44-44 0c-11 0-20 9-20 20z"/>
                </svg>
                <p class="textQuantityProduct" style="width: 100%">Add to cart</p>
              </div>
            </button>
          `);
          updateCartCount(0);
        }
      }
    },
    error: function () {
      alert("Cannot update quantity product.");
    },
  });
}
// Hàm chuyển đổi giá tiền thêm dấu chấm
function formatPrice(price) {
  return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

// Hàm hiển thị danh sách sản phẩm
function displayProducts(products) {
  products.forEach(function (product) {
    var formattedPrice = formatPrice(product.productPrice);

    // Kiểm tra nếu sản phẩm đã có trong giỏ hàng (quantityInCart > 0)
    var productHtml = "";
    if (product.quantityInCart > 0) {
      productHtml = `
          <figure class="product-item" data-product-id="${product.productId}">
              <div class="w-product-item">
                  <img src="${contextPath}/${product.imageUrl}" class="product-img" />
                  <div class="product-title">${product.productName}</div>
                  <div class="product-price">${formattedPrice} ₫</div>
                  <div class="w-quantityProduct2">
                      <div class="btnQuantity decrease-btn" data-product-id="${product.productId}">
                          <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512">
                              <path d="M432 256c0 17.7-14.3 32-32 32L48 288c-17.7 0-32-14.3-32-32s14.3-32 32-32l352 0c17.7 0 32 14.3 32 32z"/>
                          </svg>
                      </div>
                      <p class="number" id="quantity-${product.productId}">${product.quantityInCart}</p>
                      <div class="btnQuantity increase-btn" data-product-id="${product.productId}">
                          <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512">
                              <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/>
                          </svg>
                      </div>
                  </div>
              </div>
          </figure>
        `;
    } else {
      productHtml = `
          <figure class="product-item" data-product-id="${product.productId}">
              <div class="w-product-item">
                  <img src="${contextPath}/${product.imageUrl}" class="product-img" />
                  <div class="product-title">${product.productName}</div>
                  <div class="product-price">${formattedPrice} ₫</div>
                  <button class="btnQuaityProduct" name="btnAddCart" value="${product.productId}"  onclick="addToCart(${product.productId})">
                      <div class="w-quantityProduct">
                          <svg xmlns="http://www.w3.org/2000/svg" class="icon" viewBox="0 0 576 512">
                              <path class="icon-path" d="M0 24C0 10.7 10.7 0 24 0L69.5 0c22 0 41.5 12.8 50.6 32l411 0c26.3 0 45.5 25 38.6 50.4l-41 152.3c-8.5 31.4-37 53.3-69.5 53.3l-288.5 0 5.4 28.5c2.2 11.3 12.1 19.5 23.6 19.5L488 336c13.3 0 24 10.7 24 24s-10.7 24-24 24l-288.3 0c-34.6 0-64.3-24.6-70.7-58.5L77.4 54.5c-.7-3.8-4-6.5-7.9-6.5L24 48C10.7 48 0 37.3 0 24zM128 464a48 48 0 1 1 96 0 48 48 0 1 1 -96 0zm336-48a48 48 0 1 1 0 96 48 48 0 1 1 0-96zM252 160c0 11 9 20 20 20l44 0 0 44c0 11 9 20 20 20s20-9 20-20l0-44 44 0c11 0 20-9 20-20s-9-20-20-20l-44 0 0-44c0-11-9-20-20-20s-20 9-20 20l0 44-44 0c-11 0-20 9-20 20z"/>
                          </svg>
                          <p class="textQuantityProduct" style="width: 100%">Add to cart</p>
                      </div>
                  </button>
              </div>
          </figure>
        `;
    }

    $(".w-products").append(productHtml);
  });

  // Gán sự kiện cho các nút tăng/giảm số lượng sau khi hiển thị sản phẩm
  bindQuantityButtons();
}

// Hàm load sản phẩm
function loadAllProducts() {
  $.ajax({
    url: `${contextPath}/Product/ViewAll`,
    method: "GET",
    success: function (response) {
      displayProducts(response);
    },
    error: function () {
      alert("Not be load product.");
    },
  });
}

// Hàm thêm sản phẩm vào giỏ hàng
function addToCart(productId) {
  $.ajax({
    url: `${contextPath}/Cart/Add`,
    method: "POST",
    data: { productId: productId, quantity: 1 },
    success: function (response) {
      const buttonElement = $(`button[value='${productId}']`);
      buttonElement.replaceWith(`
          <div class="w-quantityProduct2">
            <div class="btnQuantity decrease-btn" data-product-id="${productId}">
              <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512">
                <path d="M432 256c0 17.7-14.3 32-32 32L48 288c-17.7 0-32-14.3-32-32s14.3-32 32-32l352 0c17.7 0 32 14.3 32 32z"></path>
              </svg>
            </div>
            <p class="number" id="quantity-${productId}">1</p>
            <div class="btnQuantity increase-btn" data-product-id="${productId}">
              <svg class="icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512">
                <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"></path>
              </svg>
            </div>
          </div>
        `);
      bindQuantityButtons();
      updateCartCount(1);
    },
    error: function (xhr, status, error) {
      if (xhr.status === 401) {
        window.location.href = `${contextPath}/Authen/Login`;
      } else {
        alert("Cannot add product to cart.");
      }
    },
  });
}

// Gán sự kiện cho các nút tăng/giảm số lượng
function bindQuantityButtons() {
  $(".increase-btn").click(function () {
    let productId = $(this).data("product-id");
    updateCartQuantity(productId, "increase");
  });

  $(".decrease-btn").click(function () {
    let productId = $(this).data("product-id");
    updateCartQuantity(productId, "decrease");
  });
}
