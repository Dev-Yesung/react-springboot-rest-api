const form = document.getElementById("loginForm");

form.addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = new FormData(form);
    const data = {
        "email": formData.get("email"),
        "password": formData.get("password")
    };

    fetch("http://localhost:8081/v1/users/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((data) => {
            if (data.email != null) {
                console.log(data);
                alert("축하합니다! 가입에 성공했습니다.");
                window.location.replace("/browse");
            } else {
                alert("가입에 실패했습니다. 아이디와 비밀번호를 확인하세요!");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("아이디가 중복입니다! 다른 아이디를 작성해주세요.");
        });
});
