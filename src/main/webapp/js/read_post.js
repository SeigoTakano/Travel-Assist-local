let allPosts = [];

document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.getElementById('search-input');
    searchInput?.addEventListener('input', (e) => filterPosts(e.target.value.toLowerCase()));

    const fab = document.getElementById('fab');
    fab?.addEventListener('click', () => {
        window.location.href = `${contextPath}/create_post`; 
    });

    loadPosts();
});

async function loadPosts() {
    try {
        const response = await fetch(`${contextPath}/read_post_data`);
        allPosts = await response.json();
        renderPosts(allPosts);
    } catch (e) {
        document.getElementById('post-feed').innerHTML = '<p class="no-result">読み込めませんでした</p>';
    }
}

function filterPosts(keyword) {
    const filtered = allPosts.filter(post => 
        (post.title?.toLowerCase().includes(keyword)) || 
        (post.impression?.toLowerCase().includes(keyword)) ||
        (post.username?.toLowerCase().includes(keyword))
    );
    renderPosts(filtered);
}

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
                <img src="${contextPath}/img/${post.imagepass}" 
                     class="post-image" 
                     onclick="toggleZoom(this)">
            </div>
            <div class="post-content">
                <h3 class="post-title">${escapeHtml(post.title)}</h3>
                <p class="post-impression">${escapeHtml(post.impression)}</p>
            </div>
        </article>
    `).join('');
    
    if (window.lucide) lucide.createIcons();
}

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

function escapeHtml(str) {
    if (!str) return "";
    const map = { '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' };
    return str.replace(/[&<>"']/g, m => map[m]);
}