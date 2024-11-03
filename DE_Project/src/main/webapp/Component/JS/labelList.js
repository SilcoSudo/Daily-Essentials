/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function populateForm(labelId, categoryName, labelName) {
    document.getElementById("updateLabelId").value = labelId;
    document.getElementById("updateCategoryName").value = categoryName;
    document.getElementById("updateLabelName").value = labelName;
}

// Event listener to close modal
function closeModal() {
    document.getElementById("addLabelModal").style.display = "none";
}

// Open Add Modal
function openAddModal() {
    // Clear the fields in the add form
    document.getElementById("addLabelName").value = "";
    document.getElementById("addCategoryName").value = "";
    document.getElementById("addLabelModal").style.display = "block";
}

// Close the modal when clicking outside of it
window.onclick = function (event) {
    const modal = document.getElementById("addLabelModal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

// Initialize row click for updating fields
document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll(".label-data-row");

    rows.forEach(row => {
        row.addEventListener("click", function () {
            const labelId = this.getAttribute("data-label-id");
            const categoryName = this.getAttribute("data-category-name");
            const labelName = this.getAttribute("data-label-name");

            populateForm(labelId, categoryName, labelName);
        });
    });
});
document.addEventListener("DOMContentLoaded", function () {
    // Open modal when "Add Label" button is clicked
    const addLabelButton = document.getElementById("addLabelButton"); // The button that opens the modal
    const addLabelModal = document.getElementById("addLabelModal");
    const closeModalButton = document.querySelector(".label-close-btn");

    // Ensure addLabelButton is defined before adding click event
    if (addLabelButton) {
        addLabelButton.addEventListener("click", function () {
            addLabelModal.style.display = "block";
        });
    }

    // Close modal when the close button (×) is clicked
    if (closeModalButton) {
        closeModalButton.addEventListener("click", function () {
            addLabelModal.style.display = "none";
        });
    }

    // Close modal when clicking outside the modal content
    window.addEventListener("click", function (event) {
        if (event.target === addLabelModal) {
            addLabelModal.style.display = "none";
        }
    });
});
