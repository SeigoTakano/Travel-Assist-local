// 今回はデータなし（後でServletやAPIから受け取る想定）
const users = [];
const posts = [];

const tableBody = document.getElementById("userTableBody");
const noUserMessage = document.getElementById("noUserMessage");
const postContent = document.getElementById("postContent");

function renderUsers() {
  tableBody.innerHTML = "";

  if (users.length === 0) {
    noUserMessage.style.display = "block";
    return;
  }

  noUserMessage.style.display = "none";

  users.forEach(user => {
    const tr = document.createElement("tr");

    tr.innerHTML = `
      <td>${user.id}</td>
      <td>${user.email}</td>
      <td>${user.name}</td>
      <td><button class="delete-btn">削除</button></td>
    `;

    tableBody.appendChild(tr);
  });
}

function renderPosts() {
  if (posts.length === 0) {
    postContent.textContent = "投稿がありません";
  }
}

// 初期表示
renderUsers();
renderPosts();
