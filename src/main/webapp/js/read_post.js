let allPosts = [];

document.addEventListener('DOMContentLoaded', () => {
    // 検索入力のイベント
    const searchInput = document.getElementById('search-input');
    searchInput?.addEventListener('input', (e) => filterPosts(e.target.value.toLowerCase()));

    // 新規投稿ボタン（FAB）
    const fab = document.getElementById('fab');
    fab?.addEventListener('click', () => {
        window.location.href = `${contextPath}/create_post`; 
    });

    // 初回読み込み
    loadPosts();
});

// 1. 投稿一覧の読み込み
async function loadPosts() {
    try {
        const response = await fetch(`${contextPath}/read_post_data`);
        allPosts = await response.json();
        renderPosts(allPosts);
    } catch (e) {
        console.error("Load Error:", e);
        document.getElementById('post-feed').innerHTML = '<p class="no-result">データの読み込みに失敗しました</p>';
    }
}

// 2. 検索フィルター
function filterPosts(keyword) {
    const filtered = allPosts.filter(post => 
        (post.title?.toLowerCase().includes(keyword)) || 
        (post.impression?.toLowerCase().includes(keyword)) ||
        (post.username?.toLowerCase().includes(keyword))
    );
    renderPosts(filtered);
}

// 3. 画面描画（自分の投稿にだけ削除ボタンを出す）
function renderPosts(posts) {
    const feed = document.getElementById('post-feed');
    if (!posts || posts.length === 0) {
        feed.innerHTML = '<p class="no-result">投稿がありません</p>';
        return;
    }

    feed.innerHTML = posts.map(post => {
        // JSPから渡された loginUser と投稿者の名前を比較
        const isMyPost = (post.username === loginUser);

        return `
            <article class="post-card">
                <div class="post-header">
                    <div class="avatar"></div>
                    <div class="user-info">
                        <div class="user-name">${escapeHtml(post.username)}</div>
                        <div class="post-time">${post.postDate || ''}</div>
                    </div>
                    ${isMyPost ? `
                        <button class="delete-btn" onclick="deletePost(${post.postNumber})" title="削除">
                            <i data-lucide="trash-2"></i>
                        </button>
                    ` : ''}
                </div>
                <div class="post-image-container">
                    <img src="${contextPath}/img/${post.imagepass}" 
                         class="post-image" 
                         onclick="toggleZoom(this)">
                </div>
                <div class="post-content">
                    <h3 class="post-title">${escapeHtml(post.title)}</h3>
                    <p class="post-impression">${escapeHtml(post.impression)}</p>
                </div>
            </article>
        `;
    }).join('');
    
    // Lucideアイコンを再描画
    if (window.lucide) lucide.createIcons();
}

// 4. ★削除処理（サーバーと通信）
async function deletePost(postId) {
    if (!confirm('この投稿を削除してもよろしいですか？')) return;
    
    try {
        // サーブレットの doPost を呼び出す
        const response = await fetch(`${contextPath}/read_post_data`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `postNumber=${postId}`
        });
        
        const result = await response.text();
        
        if (result === 'success') {
            alert('削除が完了しました');
            loadPosts(); // 再読み込みして画面を更新
        } else {
            alert('削除に失敗しました');
        }
    } catch (e) {
        console.error("Delete Error:", e);
        alert('通信エラーが発生しました');
    }
}

// 5. 画像ズーム機能
function toggleZoom(img) {
    const overlay = document.getElementById('zoom-overlay');
    const closeZoom = () => {
        img.classList.remove('zoomed');
        overlay.classList.remove('active');
        document.body.style.overflow = '';
    };

    if (img.classList.contains('zoomed')) {
        closeZoom();
    } else {
        img.classList.add('zoomed');
        overlay.classList.add('active');
        document.body.style.overflow = 'hidden';
        overlay.onclick = closeZoom;
    }
}

// XSS対策：HTMLエスケープ
function escapeHtml(str) {
    if (!str) return "";
    const map = { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' };
    return str.replace(/[&<>"']/g, m => map[m]);
}