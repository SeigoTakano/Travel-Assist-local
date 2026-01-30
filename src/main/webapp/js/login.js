/**
 * ログイン画面制御用スクリプト
 */
document.addEventListener('DOMContentLoaded', function() {
    // 要素の取得
    const loginForm = document.getElementById('loginForm');
    const emailInput = document.getElementById('floatingInput');
    const passwordInput = document.getElementById('floatingPassword');
    const loginBtn = document.querySelector('button[type="submit"]');
    
    // パスワード表示切り替え用の要素
    const togglePassword = document.getElementById('togglePassword');
    const eyeIcon = document.getElementById('eyeIcon');

    // --- 1. パスワード表示/非表示の切り替えロジック ---
    if (togglePassword && passwordInput) {
        togglePassword.addEventListener('click', function(e) {
            // ボタンがフォーム送信(submit)を起こさないように念のため防止
            e.preventDefault();

            // 現在のタイプを判定して切り替え
            const isPassword = passwordInput.getAttribute('type') === 'password';
            passwordInput.setAttribute('type', isPassword ? 'text' : 'password');

            // アイコンの切り替え
            if (isPassword) {
                // 目の斜線ありアイコン（表示中の状態）
                eyeIcon.innerHTML = '<path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7 7 0 0 0-2.79.588l.77.771A6 6 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755q-.247.248-.517.486z"/><path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z"/><path d="M3.35 5.47q-.27.24-.518.487A13 13 0 0 0 1.172 8q.086.13.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7 7 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12z"/>';
            } else {
                // 通常の目アイコン（隠している状態）
                eyeIcon.innerHTML = '<path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8M1.173 8a13 13 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5s3.879 1.168 5.168 2.457A13 13 0 0 1 14.828 8q-.086.13-.195.288c-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5s-3.879-1.168-5.168-2.457A13 13 0 0 1 1.172 8z"/><path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5M4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0"/>';
            }
        });
    }

    // --- 2. フォーム送信時のバリデーションと二重送信防止 ---
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            
            let errorMessage = "";
            const emailValue = emailInput.value.trim();
            const passwordValue = passwordInput.value.trim();

            // バリデーション
            if (!emailValue) {
                errorMessage += "メールアドレスを入力してください。\n";
            } else if (!validateEmail(emailValue)) {
                errorMessage += "正しいメールアドレス形式で入力してください。\n";
            }

            if (!passwordValue) {
                errorMessage += "パスワードを入力してください。\n";
            } else if (passwordValue.length < 8) {
                errorMessage += "パスワードは8文字以上で入力してください。\n";
            }

            // エラーがあれば送信を中止
            if (errorMessage) {
                alert(errorMessage);
                event.preventDefault();
                return;
            }

            // 二重送信防止
            loginBtn.disabled = true;
            loginBtn.innerText = "ログイン中...";
            loginBtn.style.opacity = "0.7";
            
            console.log("バリデーションOK、送信中...");
        });
    }

    /**
     * メールアドレス形式チェック
     */
    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }
});