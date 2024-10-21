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
