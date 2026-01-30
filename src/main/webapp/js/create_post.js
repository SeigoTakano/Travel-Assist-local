document.addEventListener('DOMContentLoaded', () => {
    const imageInput = document.getElementById('imageInput');
    const preview = document.getElementById('preview');
    const postForm = document.getElementById('postForm');

    /**
     * 画像選択時のプレビュー表示
     */
    imageInput?.addEventListener('change', (e) => {
        const file = e.target.files[0];
        
        if (!file) {
            preview.innerHTML = `<span class="icon">画像を選択</span>`;
            preview.style.borderStyle = "dashed";
            return;
        }

        // 画像以外のファイル形式を弾く
        if (!file.type.startsWith('image/')) {
            alert("画像ファイルを選択してください。");
            imageInput.value = "";
            return;
        }

        const reader = new FileReader();
        reader.onload = (event) => {
            // プレビューエリアに画像を表示
            preview.innerHTML = `
                <img src="${event.target.result}" 
                     style="max-width: 100%; max-height: 100%; object-fit: contain; border-radius: 8px;" 
                     alt="プレビュー">`;
            preview.style.borderStyle = "solid";
            preview.style.borderColor = "#007bff";
        };
        reader.readAsDataURL(file);
    });

    /**
     * フォーム送信時の処理（二重送信防止など）
     */
    postForm?.addEventListener('submit', (e) => {
        const postBtn = document.getElementById('postBtn');
        
        // 送信ボタンを無効化して「投稿中」に変更
        postBtn.disabled = true;
        postBtn.innerText = "投稿中...";
        
        // 実際の送信はHTMLのForm actionが行う
    });
});