const searchKeyword = sessionStorage.getItem("searchKeyword");

fetch(`http://localhost:8081/v1/songs/title/${searchKeyword}`)
    .then(response => {
        if (!response.ok) {
            throw new Error("네트워크가 정상적이지 않아 요청을 처리할 수 없습니다.");
        }
        return response.json();
    })
    .then(data => {
        const searchList = document.getElementById("searchList");
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
            listenBtn.href = song.url;
            listenBtn.target = "_blank";
            listenBtn.textContent = "듣기";

            const lyricsBtn = document.createElement("button");
            lyricsBtn.dataset.lyrics = song.lyrics;
            lyricsBtn.className = "btn-lyrics";
            lyricsBtn.textContent = "가사보기";
            lyricsBtn.style.color = "white";
            lyricsBtn.style.backgroundColor = "Royalblue";
            lyricsBtn.style.width = "80px";
            lyricsBtn.style.height = "39px";
            lyricsBtn.style.border = "none";
            lyricsBtn.style.borderRadius = "7px";

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
            searchList.appendChild(cardDiv);

            const lyricsButtons = document.querySelectorAll('.btn-lyrics');
            lyricsButtons.forEach(lyricsBtn => {
                lyricsBtn.addEventListener("click", function () {
                    const lyricsData = this.dataset.lyrics;

                    const newWindow = window.open('', '_blank');
                    newWindow.document.write('<!DOCTYPE html>');
                    newWindow.document.write('<html>');
                    newWindow.document.write('<head>');
                    newWindow.document.write('<title>가사</title>');
                    newWindow.document.write('<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">');
                    newWindow.document.write('</head>');
                    newWindow.document.write('<body>');
                    newWindow.document.write('<div class="container">');
                    newWindow.document.write('<h1>가사</h1>');
                    newWindow.document.write('<pre>' + lyricsData + '</pre>');
                    newWindow.document.write('</div>');
                    newWindow.document.write('</body>');
                    newWindow.document.write('</html>');
                    newWindow.document.close();
                });
            });
        });
    })
    .catch(error => {
        console.error("Error:", error);
    });
