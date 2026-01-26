document.addEventListener("DOMContentLoaded", function () {
    const resetForm = document.getElementById("resetForm");

    if (resetForm) {
        resetForm.addEventListener("submit", function (event) {
            const newPassword = document.getElementById("newPassword").value;
            const confirmPassword = document.getElementById("confirmPassword").value;
            const errorDiv = document.getElementById("passwordError");

            // メッセージをクリア
            errorDiv.textContent = "";

            // 1. 文字数チェック（8文字以上）
            if (newPassword.length < 8) {
                errorDiv.textContent = "パスワードは8文字以上で入力してください。";
                event.preventDefault();
                return;
            }

            // 2. 一致チェック
            if (newPassword !== confirmPassword) {
                errorDiv.textContent = "パスワードが一致しません。";
                event.preventDefault();
                return;
            }

            // ✅ バリデーションが通った場合：二重送信防止
            const submitBtn = resetForm.querySelector('button[type="submit"]');
            submitBtn.disabled = true;
            submitBtn.textContent = "更新中...";
        });
    }
});