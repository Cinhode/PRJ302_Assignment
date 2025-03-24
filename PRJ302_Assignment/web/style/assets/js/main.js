// add hovered class to selected list item
let list = document.querySelectorAll(".navigation li");

function activeLink() {
    list.forEach((item) => {
        item.classList.remove("hovered");
    });
    this.classList.add("hovered");
}

list.forEach((item) => item.addEventListener("mouseover", activeLink));

// Menu Toggle
let toggle = document.querySelector(".toggle");
let navigation = document.querySelector(".navigation");
let main = document.querySelector(".main");

toggle.onclick = function () {
    navigation.classList.toggle("active");
    main.classList.toggle("active");
};

function showRequestDetails(id) {
    if (!id) {
        console.error("Invalid ID:", id); // Debugging
        return;
    }
    fetch("http://localhost:8080/Assignment/request/receive?id=" + id)
            .then(response => {
                if (response.headers.get('Content-Type').includes('application/json')) {
                    return response.json();
                } else {
                    throw new Error('Phản hồi không phải JSON');
                }
            })
            .then(data => {
                // Điền dữ liệu vào modal
                document.getElementById("modalOwner").innerText = data.createdby.name;
                document.getElementById("modalTitle").innerText = data.title;
                document.getElementById("modalFrom").innerText = data.from;
                document.getElementById("modalTo").innerText = data.to;
                document.getElementById("modalReason").innerText = data.reason;
                document.getElementById("modalStatus").innerText = data.status;
                document.getElementById("modalProcessor").innerText = data.processedby ? data.processedby.username : " ";

                // Cập nhật sự kiện cho nút Accept và Reject
                document.getElementById("btnAccept").onclick = function () {
                    updateRequestStatus(data.id, 1); // 1 = Accept
                };
                document.getElementById("btnReject").onclick = function () {
                    updateRequestStatus(data.id, 2); // 2 = Reject
                };

                // Hiển thị modal
                document.getElementById("requestModal").style.display = "block";
            })
            .catch(error => console.error("Error:", error));
}

// Đóng modal khi nhấn ra ngoài
function closeModal() {
    document.getElementById("requestModal").style.display = "none";
}

