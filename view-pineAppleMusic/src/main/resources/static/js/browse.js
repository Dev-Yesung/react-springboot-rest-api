fetch("http://localhost:8081/v1/songs/all")
    .then(response => {
        if (!response.ok) {
            throw new Error("네트워크가 정상적이지 않아 요청을 처리할 수 없습니다.");
        }
        return response.json();
    })
    .then(data => {
        const songList = document.getElementById("songList");
        data.forEach(song => {
            const cardDiv = document.createElement("div");
            cardDiv.className = "card";

            const cardBodyDiv = document.createElement("div");
            cardBodyDiv.className = "card-body";

            const titleH5 = document.createElement("h5");
            titleH5.className = "card-title";
            titleH5.textContent = song.title;

            const artistP = document.createElement("p");
            artistP.className = "card-text";
            artistP.textContent = `장르 : ${song.genre}`;

            const description1P = document.createElement("p");
            description1P.className = "card-text";
            description1P.textContent = `시간 : ${song.playTime}분`;

            const description2P = document.createElement("p");
            description2P.className = "card-text";
            description2P.textContent = `가격 : ${song.price}원`;

            const listenBtn = document.createElement("a");
            listenBtn.className = "btn btn-primary";
            listenBtn.textContent = "듣기";

            const lyricsBtn = document.createElement("a");
            lyricsBtn.className = "btn btn-primary";
            lyricsBtn.textContent = "가사보기";

            const addToCartBtn = document.createElement("a");
            addToCartBtn.className = "btn btn-primary";
            addToCartBtn.textContent = "장바구니에 넣기";

            cardBodyDiv.appendChild(titleH5);
            cardBodyDiv.appendChild(artistP);
            cardBodyDiv.appendChild(description1P);
            cardBodyDiv.appendChild(description2P);
            cardBodyDiv.appendChild(listenBtn);
            cardBodyDiv.appendChild(lyricsBtn);
            cardBodyDiv.appendChild(addToCartBtn);

            cardDiv.appendChild(cardBodyDiv);
            songList.appendChild(cardDiv);
        });
    })
    .catch(error => {
        console.error("Error:", error);
    });
