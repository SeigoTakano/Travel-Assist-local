let myPosts = [];

document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('search-input');
    searchInput?.addEventListener('input', (e) => filterPosts(e.target.value.toLowerCase()));

    const fab = document.getElementById('fab');
    fab?.addEventListener('click', () => {
        window.location.href = `${contextPath}/create_post`; 
    });

    // 拡大表示用オーバーレイを作成
    const overlay = document.createElement('div');
    overlay.id = 'zoom-overlay';
    overlay.className = 'zoom-overlay';
    document.body.appendChild(overlay);

    overlay.addEventListener('click', () => {
        overlay.classList.remove('active');
        overlay.innerHTML = '';
        document.body.style.overflow = '';
    });

    loadMyPosts();
});

// 投稿データ取得
async function loadMyPosts() {
    try {
        const response = await fetch(`${contextPath}/my_post_data`);
        if (!response.ok) throw new Error('HTTP error ' + response.status);
        myPosts = await response.json();
        renderPosts(myPosts);
    } catch (e) {
        console.error(e);
        document.getElementById('post-feed').innerHTML = '<p class="no-result">読み込めませんでした</p>';
    }
}

// 投稿フィルター
function filterPosts(keyword) {
    const filtered = myPosts.filter(post =>
        post.title?.toLowerCase().includes(keyword) ||
        post.impression?.toLowerCase().includes(keyword)
    );
    renderPosts(filtered);
}

// 投稿レンダリング
function renderPosts(posts) {
    const feed = document.getElementById('post-feed');
    if (!posts || posts.length === 0) {
        feed.innerHTML = '<p class="no-result">投稿がありません</p>';
        return;
    }

    feed.innerHTML = posts.map(post => `
        <article class="post-card">
            <div class="post-header">
                <div class="avatar"></div>
                <div class="user-info">
                    <div class="user-name">${escapeHtml(post.username)}</div>
                    <div class="post-time">${post.postDate || ''}</div>
                </div>
            </div>
            <div class="post-image-container">
                <img src="${contextPath}/img/${post.imagepass}" class="post-image">
            </div>
            <div class="post-content">
                <h3 class="post-title">${escapeHtml(post.title)}</h3>
                <p class="post-impression">${escapeHtml(post.impression)}</p>
            </div>
        </article>
    `).join('');

    // 画像拡大イベントを追加
    const overlay = document.getElementById('zoom-overlay');
    document.querySelectorAll('.post-image').forEach(img => {
        img.addEventListener('click', () => {
            const zoomedImg = new Image();
            zoomedImg.src = img.src;
            overlay.innerHTML = '';
            overlay.appendChild(zoomedImg);
            overlay.classList.add('active');
            document.body.style.overflow = 'hidden';
        });
    });

    // lucideがある場合はアイコン再描画
    if (window.lucide) lucide.createIcons();
}

// HTMLエスケープ
function escapeHtml(str) {
    if (!str) return "";
    const map = { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' };
    return str.replace(/[&<>"']/g, m => map[m]);
}
