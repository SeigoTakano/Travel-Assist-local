/**
 * ログイン画面制御用スクリプト
 * * 役割:
 * 1. フォーム送信時のバリデーション
 * 2. ボタン連打による二重送信の防止
 * 3. 入力フィールドのフォーカス演出（オプション）
 */

document.addEventListener('DOMContentLoaded', function() {
    // フォーム要素の取得
    const loginForm = document.getElementById('loginForm');
    const emailInput = document.getElementById('floatingInput');
    const passwordInput = document.getElementById('floatingPassword');
    // JS側の修正例
	const loginBtn = document.querySelector('button[type="submit"]');

    // フォームが存在する場合のみ実行
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            
            // --- 1. バリデーション処理 ---
            let errorMessage = "";
            const emailValue = emailInput.value.trim();
            const passwordValue = passwordInput.value.trim();

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

            // エラーがある場合は送信を中断
            if (errorMessage) {
                alert(errorMessage);
                event.preventDefault(); // サーバーへの送信を止める
                return;
            }

            // --- 2. 二重送信防止処理 ---
            // 送信ボタンを無効化し、処理中であることを示す
            loginBtn.disabled = true;
            loginBtn.innerText = "ログイン中...";
            loginBtn.style.opacity = "0.7";
            loginBtn.style.cursor = "not-allowed";

            console.log("バリデーション通過。サーバーへ送信します...", {
                email: emailValue,
                rememberMe: document.getElementsByName('rememberMe')[0].checked
            });
        });
    }

    /**
     * メールアドレスの形式チェック用関数
     */
    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(email);
    }

    // --- 3. (オプション) 入力欄へのフォーカス時演出 ---
    // CSSの :focus でも十分ですが、JSで特定の挙動を加えたい場合に拡張可能です
    [emailInput, passwordInput].forEach(input => {
        input.addEventListener('focus', () => {
            console.log(`${input.name} フィールドにフォーカスされました`);
        });
    });
});