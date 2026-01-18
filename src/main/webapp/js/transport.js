document.addEventListener('DOMContentLoaded', async () => {
    let masterData = {};

    // 都市名と各交通機関の拠点（ハブ）を紐付ける辞書
    const cityHubMapping = {
        "東京": { shinkansen: "東京", plane: "羽田空港", bus: "バスタ新宿" },
        "名古屋": { shinkansen: "名古屋", plane: "中部国際空港", bus: "名鉄バスセンター(名古屋)" },
        "京都": { shinkansen: "京都", plane: "伊丹空港", bus: "京都駅烏丸口" },
        "大阪": { shinkansen: "新大阪", plane: "伊丹空港", bus: "大阪(梅田)阪急三番街" },
        "広島": { shinkansen: "広島", plane: "広島空港", bus: "広島バスセンター" },
        "博多": { shinkansen: "博多", plane: "福岡空港", bus: "西鉄天神高速バスターミナル" },
        "仙台": { shinkansen: "仙台", plane: "仙台空港", bus: "仙台駅東口バスターミナル" },
        "札幌": { shinkansen: "新函館北斗", plane: "新千歳空港", bus: "札幌駅前ターミナル" },
        "金沢": { shinkansen: "金沢", plane: "金沢", bus: "主要ターミナル" }
    };

    const departureSelect = document.getElementById('departure');
    const destinationSelect = document.getElementById('destination');
    const compareBtn = document.getElementById('compareBtn');
    const singleSearchBtn = document.getElementById('singleBtn');
    const travelDateInput = document.getElementById('travelDate');
    const resultContentContainer = document.getElementById('resultContent');
    const resultSection = document.getElementById('resultCard');

    // データの読み込み
    try {
        const response = await fetch('transport_data.json');
        if (!response.ok) throw new Error("JSON file not found");
        masterData = await response.json();
    } catch (error) {
        console.error("データの読み込みに失敗:", error);
        return;
    }

    // 拠点特定ロジック
    function findTargetStation(transportType, selectedPlaceName) {
        let targetStationName = selectedPlaceName;
        for (const cityName in cityHubMapping) {
            const hubs = cityHubMapping[cityName];
            if (selectedPlaceName.includes(cityName) || Object.values(hubs).includes(selectedPlaceName)) {
                targetStationName = hubs[transportType];
                break;
            }
        }
        for (const region in masterData[transportType]) {
            const station = masterData[transportType][region].find(item => item.name === targetStationName);
            if (station) return station;
        }
        return null;
    }

    // ボタンの状態制御
    function refreshCompareButtonState() {
        const origin = departureSelect.value;
        const destination = destinationSelect.value;
        const activeTab = document.querySelector('.tab.active');
        if (!activeTab || !origin || !destination) {
            compareBtn.disabled = true;
            compareBtn.style.opacity = "0.5";
            return;
        }
        const currentType = activeTab.dataset.type;
        const otherTypes = ['shinkansen', 'plane', 'bus'].filter(type => type !== currentType);
        const isComparisonAvailable = otherTypes.some(type => findTargetStation(type, origin) && findTargetStation(type, destination));

        if (isComparisonAvailable) {
            compareBtn.disabled = false;
            compareBtn.style.opacity = "1";
            compareBtn.innerText = "すべての手段を比較";
            compareBtn.style.cursor = "pointer";
        } else {
            compareBtn.disabled = true;
            compareBtn.style.opacity = "0.5";
            compareBtn.innerText = "比較対象データなし";
            compareBtn.style.cursor = "not-allowed";
        }
    }

    // 検索実行
    function runSearch(isFullComparisonMode) {
        const activeTransportType = document.querySelector('.tab.active').dataset.type;
        const selectedOrigin = departureSelect.value;
        const selectedDestination = destinationSelect.value;

        const transportSettings = {
            shinkansen: { label: "新幹線", speedKmh: 220, costPerKm: 26, baseWaitMin: 20, themeColor: "#2563eb" },
            plane:      { label: "飛行機", speedKmh: 650, costPerKm: 22, baseWaitMin: 120, themeColor: "#2563eb" },
            bus:        { label: "高速バス", speedKmh: 60, costPerKm: 10, baseWaitMin: 30, themeColor: "#2563eb" }
        };

        const isWeekend = [0, 6].includes(new Date(travelDateInput.value).getDay());
        const demandMultiplier = isWeekend ? 1.3 : 1.0;

        const searchResults = Object.keys(transportSettings).map(type => {
            const originStation = findTargetStation(type, selectedOrigin);
            const destStation = findTargetStation(type, selectedDestination);
            if (!originStation || !destStation) return null;

            const settings = transportSettings[type];
            const distanceKm = Math.sqrt(Math.pow((originStation.lat - destStation.lat) * 111, 2) + Math.pow((originStation.lng - destStation.lng) * 91, 2)) * 1.15;

            let cost = distanceKm * settings.costPerKm;
            if (type === 'plane') cost *= (demandMultiplier * 1.5);
            else if (type === 'bus') cost *= (demandMultiplier * 1.2);
            else cost *= (1 + (demandMultiplier - 1) * 0.1);

            return {
                type, label: settings.label, originName: originStation.name, destName: destStation.name,
                totalCost: Math.round(cost), totalTimeMin: Math.round((distanceKm / settings.speedKmh) * 60 + settings.baseWaitMin),
                color: settings.themeColor
            };
        }).filter(r => r !== null);

        resultContentContainer.innerHTML = "";
        searchResults.forEach(res => {
            if (!isFullComparisonMode && res.type !== activeTransportType) return;
            const resultCard = document.createElement('div');
            resultCard.className = `result-card-mini ${res.type === activeTransportType ? 'highlight' : ''}`;
            resultCard.innerHTML = `
                <div class="card-header" style="background:${res.color}">${res.label}</div>
                <div class="card-body">
                    <div class="location-info">${res.originName} ➔ ${res.destName}</div>
                    <div class="price">${res.totalCost.toLocaleString()}</div>
                    <div class="time">約 ${Math.floor(res.totalTimeMin / 60)}h ${res.totalTimeMin % 60}m</div>
                </div>
            `;
            resultContentContainer.appendChild(resultCard);
        });
        resultSection.style.display = 'block';
        resultSection.scrollIntoView({ behavior: 'smooth' });
    }

    function updateStationSelectOptions() {
        const currentType = document.querySelector('.tab.active').dataset.type;
        const allStations = Object.values(masterData[currentType]).flat().sort((a, b) => a.name.localeCompare(b.name, 'ja'));
        [departureSelect, destinationSelect].forEach(select => {
            const previous = select.value;
            select.innerHTML = '<option value="" disabled selected>選択してください</option>';
            allStations.forEach(s => {
                const opt = document.createElement('option');
                opt.value = s.name; opt.textContent = s.name; select.appendChild(opt);
            });
            if (previous) select.value = previous;
        });
        refreshCompareButtonState();
    }

    document.querySelectorAll('.tab').forEach(tab => {
        tab.addEventListener('click', (e) => {
            document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
            e.target.classList.add('active');
            document.getElementById('formTitle').textContent = e.target.textContent + '検索';
            document.getElementById('singleBtnText').textContent = e.target.textContent;
            updateStationSelectOptions();
        });
    });

    departureSelect.addEventListener('change', refreshCompareButtonState);
    destinationSelect.addEventListener('change', refreshCompareButtonState);
    singleSearchBtn.addEventListener('click', () => runSearch(false));
    compareBtn.addEventListener('click', () => runSearch(true));

    updateStationSelectOptions();
    travelDateInput.valueAsDate = new Date();
});