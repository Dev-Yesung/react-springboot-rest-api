fetch(`http://localhost:8081/v1/orders/${userId}/all`)
    .then(response => {
        if (!response.ok) {
            throw new Error("네트워크가 정상적이지 않아 요청을 처리할 수 없습니다.");
        }
        return response.json();
    })
    .then(data => {
        const cartList = document.getElementById("cartList");
        const order = data.orderList;
        order.forEach(song => {
            const divElem = document.createElement("p");
            const titleElem = document.createElement("div");
            titleElem.innerText = `노래 : ${song.title}`;

            const priceElem = document.createElement("div");
            priceElem.innerText = `가격 : ${song.price}원`;

            const deleteBtn = document.createElement("button");
            deleteBtn.dataset.id = song.id;
            deleteBtn.className = "btn-delete";
            deleteBtn.textContent = "삭제하기";
            deleteBtn.style.color = "white";
            deleteBtn.style.backgroundColor = "Royalblue";
            deleteBtn.style.width = "80px";
            deleteBtn.style.height = "39px";
            deleteBtn.style.border = "none";
            deleteBtn.style.borderRadius = "7px";

            divElem.appendChild(titleElem);
            divElem.appendChild(priceElem);
            divElem.appendChild(deleteBtn);
            cartList.appendChild(divElem);
        });

        const deleteButton = document.querySelectorAll(".btn-delete");
        deleteButton.forEach(delBtn => {
            delBtn.addEventListener("click", function () {
                const songId = this.dataset.id;
                fetch(`http://localhost:8081/v1/orders/${userId}/songs/${songId}`, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                    },
                }).then(response => {
                    if (!response.ok) {
                        throw new Error("Network response was not ok");
                    }
                }).then((data) => {
                    console.log(data);
                    if (data) {
                        console.log(data);
                    }
                }).catch(error => {
                    console.error("Error:", error);
                    alert("곡이 장바구니에서 삭제되었습니다.");
                });
            }, { once : true });
        });

        const totalPrice = document.createElement("div");
        totalPrice.innerText = `총 가격 : ${data.totalPrice}`;
        cartList.appendChild(totalPrice);
    })
    .catch(error => {
        console.error("Error:", error);
    });
