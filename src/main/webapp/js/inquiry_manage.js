document.addEventListener('DOMContentLoaded', () => {

  // トースト表示
  function showToast() {
    const toast = document.getElementById('toast');
    toast.classList.add('show');

    setTimeout(() => {
      toast.classList.remove('show');
    }, 1200);
  }

  // コピー処理
  document.querySelectorAll('.copy-target').forEach(el => {
    el.addEventListener('click', () => {
      const text = el.textContent.trim();

      navigator.clipboard.writeText(text).then(() => {
        showToast();
      });
    });
  });

  // 返信ボタン（メール作成）
  document.querySelectorAll('.reply-btn').forEach(btn => {
    btn.addEventListener('click', () => {
      const mail = btn.dataset.mail;
      if (!mail) return;

      location.href =
        `mailto:${mail}?subject=【お問い合わせ回答】トラベルアシスト`;
    });
  });

  // ページネーション
  const ITEMS_PER_PAGE = 10;
  const items = document.querySelectorAll('.card');
  const pagination = document.getElementById('pagination');

  function showPage(page) {
    const start = (page - 1) * ITEMS_PER_PAGE;
    const end = start + ITEMS_PER_PAGE;

    items.forEach((item, index) => {
      item.style.display =
        index >= start && index < end ? 'block' : 'none';
    });

    document.querySelectorAll('.pagination button').forEach(btn => {
      btn.classList.remove('active');
    });

    const activeBtn =
      document.querySelector(`.pagination button[data-page="${page}"]`);
    if (activeBtn) {
      activeBtn.classList.add('active');
    }
  }

  function setupPagination() {
    pagination.innerHTML = '';
    const pageCount = Math.max(1, Math.ceil(items.length / ITEMS_PER_PAGE));

    for (let i = 1; i <= pageCount; i++) {
      const btn = document.createElement('button');
      btn.textContent = i;
      btn.dataset.page = i;
      btn.addEventListener('click', () => showPage(i));
      pagination.appendChild(btn);
    }

    if (pageCount > 0) {
      showPage(1);
    }
  }

  setupPagination();
});
