const $ = (sel, root=document) => root.querySelector(sel);
const $$ = (sel, root=document) => Array.from(root.querySelectorAll(sel));

const CATEGORIES = [
  { key:'transport', label:'交通費', color:'#5cc8ff' },
  { key:'food',      label:'食費',   color:'#7ee081' },
  { key:'lodging',   label:'宿泊費', color:'#ffd166' },
];

const state = {
  people: 2,
  roundingMode: 'none',  // 'none', 'roundup', or '10yen'
  items: {
    transport: [],
    food: [],
    lodging: [],
  },
};

function roundTo10Yen(amount) {
  return Math.ceil(amount / 10) * 10;
}

function roundUp(amount) {
  return Math.ceil(amount);
}

function yen(n, mode = 'none') {
  if (!Number.isFinite(n)) return '¥0';
  
  let value;
  switch(mode) {
    case 'roundup':
      value = roundUp(n);
      break;
    case '10yen':
      value = roundTo10Yen(n);
      break;
    case 'none':
    default:
      value = Math.floor(n);
  }
  
  return '¥' + value.toLocaleString('ja-JP');
}

function todayStr(){
  const d = new Date();
  const y = d.getFullYear();
  const m = String(d.getMonth()+1).padStart(2,'0');
  const day = String(d.getDate()).padStart(2,'0');
  const w = ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'][d.getDay()];
  return `${y}\n${m}.${day} ${w}`;
}

// Chart.js Donut
let chart;
function setupChart(){
  const ctx = $('#donut');
  chart = new Chart(ctx, {
    type: 'doughnut',
    data: {
      labels: CATEGORIES.map(c=>c.label),
      datasets: [{ data: new Array(CATEGORIES.length).fill(0), backgroundColor: CATEGORIES.map(c=>c.color), borderWidth:0 }]
    },
    options:{
      plugins:{ legend:{ display:false } },
      cutout: '70%',
    }
  });
}

function updateLegend(subtotals){
  const legend = $('#legend');
  legend.innerHTML = '';
  CATEGORIES.forEach((c,i)=>{
    const el = document.createElement('div');
    el.className = 'entry';
    el.innerHTML = `<span style="display:flex;align-items:center;gap:8px"><span class="dot" style="background:${c.color}"></span>${c.label}</span><strong>${yen(subtotals[i])}</strong>`;
    legend.appendChild(el);
  });
}

function slugify(str){
  return str
    .toLowerCase()
    .trim()
    .replace(/[^a-z0-9\u3040-\u30ff\u3400-\u9fff]+/g,'-')
    .replace(/^-+|-+$/g,'');
}

function ensureUniqueKey(base){
  let key = base || 'cat';
  let i = 2;
  const exists = () => CATEGORIES.some(c=>c.key === key);
  while (exists()) { key = `${base}-${i++}`; }
  return key;
}

function getNextColor(){
  const palette = ['#c792ea','#ff8fab','#72ddf7','#ef8354','#9bde7e','#6c9ff6','#f6bd60','#84a59d'];
  const used = new Set(CATEGORIES.map(c=>c.color));
  for (const col of palette){ if (!used.has(col)) return col; }
  // fallback: random pleasant color
  return `hsl(${Math.floor(Math.random()*360)},70%,70%)`;
}

function createCategorySection(c){
  if ($(`#items-${c.key}`)) return; // already exists
  const main = $('main.content');
  const section = document.createElement('section');
  section.className = 'category';
  section.dataset.key = c.key;
  section.innerHTML = `
    <header>
      <h2>${c.label}</h2>
      <div class="subtotal">
        <span>小計</span>
        <strong id="subtotal-${c.key}">¥0</strong>
        <span class="per">/ 人: <span id="pp-${c.key}">¥0</span></span>
      </div>
      <button class="icon del del-cat" data-key="${c.key}" title="ジャンル削除">削除</button>
    </header>
    <div class="items" id="items-${c.key}"></div>
  `;
  main.appendChild(section);
}

function addCategory(label){
  const name = (label || '').trim();
  if (!name) return;
  if (CATEGORIES.some(c=>c.label === name)) return; // duplicate label not allowed
  const baseKey = slugify(name) || 'cat';
  const key = ensureUniqueKey(baseKey);
  const color = getNextColor();
  const cat = { key, label:name, color };
  CATEGORIES.push(cat);
  createCategorySection(cat);
  addItemRow(key);
  ensureTrailingEmptyRow(key);
  // update chart structures
  if (chart){
    chart.data.labels.push(cat.label);
    chart.data.datasets[0].backgroundColor.push(cat.color);
    chart.data.datasets[0].data.push(0);
    chart.update();
  }
  recalc();
}

function removeCategory(key){
  const idx = CATEGORIES.findIndex(c=>c.key===key);
  if (idx < 0) return;
  // Remove from DOM
  const sec = document.querySelector(`section.category[data-key="${key}"]`);
  if (sec) sec.remove();
  // Update chart structures first using saved index
  if (chart){
    chart.data.labels.splice(idx,1);
    chart.data.datasets[0].backgroundColor.splice(idx,1);
    chart.data.datasets[0].data.splice(idx,1);
    chart.update();
  }
  // Remove from categories
  CATEGORIES.splice(idx,1);
  recalc();
}

function addItemRow(key, memo='', amount=''){
  const tpl = $('#item-row-tpl');
  const row = tpl.content.firstElementChild.cloneNode(true);
  const memoInput = $('.memo', row);
  const amtInput = $('.amount', row);

  memoInput.value = memo;
  amtInput.value = amount;

  const onChange = () => { recalc(); ensureTrailingEmptyRow(key); };
  // 数値のみ許可: 入力時に非数字を除去
  amtInput.addEventListener('input', (e)=>{
    const cleaned = (e.target.value || '').replace(/\D+/g, '');
    if (e.target.value !== cleaned) e.target.value = cleaned;
    onChange();
  });
  memoInput.addEventListener('input', onChange);
  $('.del', row).addEventListener('click', () => { row.remove(); recalc(); ensureTrailingEmptyRow(key); });

  $(`#items-${key}`).appendChild(row);
}

function isEmptyRow(row){
  const memo = $('.memo', row).value.trim();
  const amt = Number($('.amount', row).value || 0);
  return memo === '' && !(amt > 0);
}

function ensureTrailingEmptyRow(key){
  const container = $(`#items-${key}`);
  const rows = $$('.item-row', container);
  // 1) 余分な空行を削除（末尾以外の空行）
  rows.slice(0, -1).forEach(r => { if (isEmptyRow(r)) r.remove(); });
  // 2) 末尾の状態を確認し、入力が入っていれば空行を足す。空行が無ければ1行作る。
  const rowsAfter = $$('.item-row', container);
  const last = rowsAfter[rowsAfter.length - 1];
  if (!last) {
    addItemRow(key);
    return;
  }
  if (!isEmptyRow(last)) {
    addItemRow(key);
  }
}

function readItems(key){
  const rows = $$(`#items-${key} .item-row`);
  return rows.map(r=>({
    memo: $('.memo', r).value.trim(),
    amount: Number((($('.amount', r).value || '').replace(/\D+/g, '')) || 0)
  })).filter(x=>Number.isFinite(x.amount) && x.amount>0);
}

function recalc(){
  state.people = Math.max(1, Number($('#people').value || 1));
  const roundTo10 = state.roundTo10Yen;

  const subtotals = CATEGORIES.map(c=>{
    const list = readItems(c.key);
    const sum = list.reduce((a,b)=> a + b.amount, 0);
    $(`#subtotal-${c.key}`).textContent = yen(sum, 'none'); // 小計は常に切り捨て
    $(`#pp-${c.key}`).textContent = yen(sum / state.people, state.roundingMode);
    return sum;
  });

  const grand = subtotals.reduce((a,b)=>a+b,0);
  $('#grandTotal').textContent = yen(grand, 'none'); // 合計は常に切り捨て
  $('#perPerson').textContent = yen(grand / state.people, state.roundingMode);

  // Chart
  if (chart){
    chart.data.datasets[0].data = subtotals;
    chart.update();
  }
  updateLegend(subtotals);
  // 入力UIの末尾空行を維持
  CATEGORIES.forEach(c=> ensureTrailingEmptyRow(c.key));
}

function initPrefill(){
  // 各カテゴリに空行を1つ用意
  CATEGORIES.forEach(c=> addItemRow(c.key));
  CATEGORIES.forEach(c=> ensureTrailingEmptyRow(c.key));
}

function main(){
  $('#today').textContent = todayStr();
  setupChart();
  
  // 各カテゴリに初期入力行を追加
  initPrefill();
  
  // リセット: 各カテゴリを空にして空行を1つ残す
  $('#clearAll').addEventListener('click', ()=> {
    $$('.items').forEach(d=>d.innerHTML='');
    CATEGORIES.forEach(c=> addItemRow(c.key));
    recalc();
    CATEGORIES.forEach(c=> ensureTrailingEmptyRow(c.key));
  });
  // ジャンル追加
  $('#addCategory').addEventListener('click', ()=>{
    const name = window.prompt('追加するジャンル名を入力');
    addCategory(name || '');
  });
  // ジャンル削除（委譲）
  document.addEventListener('click', (e)=>{
    const btn = e.target.closest('.del-cat');
    if (!btn) return;
    const key = btn.dataset.key || btn.closest('section.category')?.dataset.key;
    if (!key) return;
    const ok = window.confirm('このジャンルを削除しますか？\n（入力内容も消えます）');
    if (!ok) return;
    removeCategory(key);
  });

  // 端数処理のラジオボタン
  document.querySelectorAll('input[name="rounding"]').forEach(radio => {
    radio.addEventListener('change', (e) => {
      state.roundingMode = e.target.value;
      recalc();
    });
  });

  // イベントリスナー
  $('input, select').forEach(el=>{
    el.addEventListener('change', recalc);
    if (el.type === 'number') el.addEventListener('input', recalc);
  });
  initPrefill();
  $('#people').addEventListener('input', recalc);
  $('#budget').addEventListener('input', ()=>{/* 予算は現状表示のみ */});
  recalc();
}

document.addEventListener('DOMContentLoaded', main);