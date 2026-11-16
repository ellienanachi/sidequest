// Quest Board ("Near you"): loads open quests from /api/quests and draws them as cards.

const grid = document.getElementById("grid");
const chips = document.getElementById("chips");
const count = document.getElementById("count");
const searchInput = document.getElementById("search-input");
const locationSelect = document.getElementById("location");
const sizeSelect = document.getElementById("size");

let activeCategoryId = null;

renderSidebar("home");
const headerIcons = document.getElementById("header-icons");
const bell = iconButton("bell", "Quest alerts");
bell.dataset.soon = "Quest alerts";
const rank = iconButton("star", "Guild rank");
rank.dataset.soon = "Guild rank";
headerIcons.append(bell, rank);

// ---------- Filters ----------

async function loadFilters() {
	try {
		const [categories, sizes, allQuests] = await Promise.all([
			getJson("/api/categories"),
			getJson("/api/quest-sizes"),
			getJson("/api/quests"),
		]);
		renderChips(categories);
		for (const s of sizes) {
			const label = titleCase(s.size) + " (" + peso(s.minReward) + " to " + peso(s.maxReward) + ")";
			sizeSelect.appendChild(new Option(label, s.size));
		}
		const locations = [...new Set(allQuests.map((q) => q.location).filter(Boolean))].sort();
		for (const location of locations) locationSelect.appendChild(new Option(location, location));
	} catch (e) {
		// The board itself shows the error state.
	}
}

function renderChips(categories) {
	chips.replaceChildren();
	for (const category of [{ id: null, name: "All" }, ...categories]) {
		const chip = el("button", "chip", category.name);
		chip.type = "button";
		const active = category.id === activeCategoryId;
		chip.classList.toggle("is-active", active);
		chip.setAttribute("aria-pressed", String(active));
		chip.addEventListener("click", () => {
			activeCategoryId = category.id;
			renderChips(categories);
			loadQuests();
		});
		chips.appendChild(chip);
	}
}

// ---------- Board ----------

async function loadQuests() {
	const params = new URLSearchParams();
	if (searchInput.value.trim()) params.set("q", searchInput.value.trim());
	if (activeCategoryId) params.set("categoryId", activeCategoryId);
	if (sizeSelect.value) params.set("size", sizeSelect.value);
	if (locationSelect.value) params.set("location", locationSelect.value);

	grid.replaceChildren(...Array.from({ length: 4 }, () => el("div", "skeleton")));
	try {
		const quests = await getJson("/api/quests?" + params);
		renderQuests(quests);
	} catch (e) {
		count.textContent = "";
		renderState("Can't reach the server", "Make sure the app is running, then refresh.");
	}
}

function renderState(title, message) {
	const box = el("div", "state");
	box.append(el("strong", "", title), el("span", "", message));
	grid.replaceChildren(box);
}

function renderQuests(quests) {
	count.textContent = quests.length === 1 ? "1 open quest" : quests.length + " open quests";
	if (quests.length === 0) {
		renderState("No quests match", "Try another keyword, size, location or category.");
		return;
	}
	grid.replaceChildren(...quests.map(questCard));
}

function questCard(quest, index) {
	const card = el("article", "card");

	const art = el("div", "card-art");
	art.innerHTML = DOODLES[index % DOODLES.length]; // our own static SVG
	art.setAttribute("aria-hidden", "true");

	const body = el("div", "card-body");
	body.appendChild(el("h3", "card-title", quest.title));
	body.appendChild(el("p", "card-poster", shortName(quest.posterName)));
	body.appendChild(el("p", "card-desc", quest.description));

	const meta = el("div", "card-meta");
	meta.appendChild(el("span", "tag reward", peso(quest.reward)));
	meta.appendChild(el("span", "tag", titleCase(quest.size)));
	if (quest.location) meta.appendChild(el("span", "tag", quest.location));
	meta.appendChild(el("span", "tag", "Due " + shortDate(quest.deadline)));
	body.appendChild(meta);

	const more = el("a", "btn-tan", "See more");
	more.href = "quest.html?id=" + encodeURIComponent(quest.id);
	body.appendChild(more);

	card.append(art, body);
	return card;
}

// ---------- Events ----------

document.getElementById("search-form").addEventListener("submit", (event) => {
	event.preventDefault();
	loadQuests();
});

let typingTimer;
searchInput.addEventListener("input", () => {
	clearTimeout(typingTimer);
	typingTimer = setTimeout(loadQuests, 300);
});

locationSelect.addEventListener("change", loadQuests);
sizeSelect.addEventListener("change", loadQuests);

loadFilters();
loadQuests();
