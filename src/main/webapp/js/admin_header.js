document.addEventListener("DOMContentLoaded", () => {
    const menu = document.getElementById("side-menu");
    const button = document.getElementById("hamburger");
    
    // ボタン押したらメニュー開閉
    button.addEventListener("click", () => {
        menu.classList.toggle("open");
        button.classList.toggle("active"); // CSS で見た目変更するだけ
    });

    // メニュー外クリックで閉じる
    document.addEventListener("click", (e) => {
        if (!menu.contains(e.target) && !button.contains(e.target)) {
            menu.classList.remove("open");
            button.classList.remove("active");
        }
    });
});