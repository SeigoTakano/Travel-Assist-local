document.getElementById("resetForm").addEventListener("submit", function (event) {
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const errorDiv = document.getElementById("passwordError");

    errorDiv.textContent = "";

    if (newPassword.length < 6) {
        errorDiv.textContent = "パスワードは6文字以上で入力してください。";
        event.preventDefault();
        return;
    }

    if (newPassword !== confirmPassword) {
        errorDiv.textContent = "パスワードが一致しません。";
        event.preventDefault();
        return;
    }
});