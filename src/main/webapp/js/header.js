document.addEventListener("DOMContentLoaded", () => {
    // --- ハンバーガーメニュー制御の統合 ---
    const menu = document.getElementById("side-menu");
    const button = document.getElementById("hamburger");
    
    if (menu && button) {
        // ボタン押したらメニュー開閉
        button.addEventListener("click", (e) => {
            e.stopPropagation(); 
            menu.classList.toggle("open");
            button.classList.toggle("active");
            console.log("Menu toggled");
        });

        // メニュー本体をクリックしても閉じないようにする
        menu.onclick = (e) => {
            e.stopPropagation();
        };

        // メニュー外（document）クリックで閉じる
        document.addEventListener("click", () => {
            if (menu.classList.contains("open")) {
                menu.classList.remove("open");
                button.classList.remove("active");
                console.log("Menu closed by clicking outside");
            }
        });
    } else {
        console.warn("サイドメニューまたはボタンが見つかりません。header.jspのIDを確認してください。");
    }

    // --- 以下、既存の交通検索用のロジックがあれば続けて記述 ---
    // 例：タブの切り替え機能
    const tabs = document.querySelectorAll(".tab");
    tabs.forEach(tab => {
        tab.addEventListener("click", () => {
            tabs.forEach(t => t.classList.remove("active"));
            tab.classList.add("active");
            const type = tab.getAttribute("data-type");
            document.getElementById("formTitle").innerText = tab.innerText + "検索";
            document.getElementById("singleBtnText").innerText = tab.innerText;
        });
    });
});