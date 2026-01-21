document.getElementById("forgotForm").addEventListener("submit", function (event) {
    const email = document.getElementById("floatingEmail").value.trim();
    const emailError = document.getElementById("emailError");

    // メッセージ初期化
    emailError.textContent = "";

    // ✅ 入力形式チェック
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        emailError.textContent = "正しいメールアドレスの形式で入力してください。";
        event.preventDefault(); // 送信を中止
        return;
    }
});