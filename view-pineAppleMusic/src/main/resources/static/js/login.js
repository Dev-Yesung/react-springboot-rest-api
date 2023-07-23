const form = document.getElementById("loginForm");

form.addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = new FormData(form);
    const data = {
        "email": formData.get("email"),
        "password": formData.get("password")
    };

    fetch("http://localhost:8081/v1/users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    }).then(response => {
        if (!response.ok) {
            alert("로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요!");
            throw new Error();
        }
        return response.json();
    }).then((data) => {
        console.log(data);
        if (data) {
            console.log(data);
            alert("로그인에 성공했습니다!");
            window.location.replace("/browse-login");
        }
    }).catch(error => {
        console.error("Error:", error);
    });
});
