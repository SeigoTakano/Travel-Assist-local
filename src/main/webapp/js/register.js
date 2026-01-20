document.getElementById("registerForm").addEventListener("submit", function (event) {
    const userId = document.getElementById("floatingName").value.trim();
    const email = document.getElementById("floatingEmail").value.trim();
    const password = document.getElementById("floatingPassword").value;
    const confirmPassword = document.getElementById("floatingPasswordConfirm").value;

    let isValid = true;

    // エラーメッセージ初期化
    const errorIds = ["nameError", "emailError", "passwordError", "confirmError"];
    errorIds.forEach(id => document.getElementById(id).textContent = "");

    // ユーザーIDチェック
    if (!/^[A-Za-z0-9]+$/.test(userId)) {
        document.getElementById("nameError").textContent = "ユーザーIDは英字と数字のみ使用できます";
        isValid = false;
    }

    // メール形式チェック
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        document.getElementById("emailError").textContent = "正しいメールアドレスの形式で入力してください";
        isValid = false;
    }

    // パスワードチェック (DBのVARCHAR(10)制限を考慮しつつバリデーション)
    if (password.length < 8 || password.length > 10) {
        document.getElementById("passwordError").textContent = "パスワードは8文字以上10文字以内で入力してください";
        isValid = false;
    }

    // パスワード一致チェック
    if (password !== confirmPassword) {
        document.getElementById("confirmError").textContent = "パスワードが一致しません。";
        isValid = false;
    }

    // エラーがあれば送信を中止
    if (!isValid) {
        event.preventDefault();
    }
});