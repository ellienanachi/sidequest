// SideQuest/s preview page.
// Loads categories and listings from the Spring Boot API and draws them as cards.
// Each "render" function maps to a future React component (CategoryChips, QuestCard, ...).

const grid = document.getElementById("grid");
const chips = document.getElementById("chips");
const count = document.getElementById("count");
const searchForm = document.getElementById("search-form");
const searchInput = document.getElementById("search-input");
const toast = document.getElementById("toast");

let activeCategoryId = null;

// One color pair per category so tags are easy to tell apart.
const TAG_COLORS = [
	["#f6e9ea", "#7b1e2b"],
	["#fff4d1", "#8a5a00"],
	["#e6f2ec", "#1f6b45"],
	["#e8eefc", "#2a4a9b"],
	["#f1e9fb", "#5b2d91"],
];

const ICONS = {
	pin: '<svg viewBox="0 0 24 24"><path d="M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0z"/><circle cx="12" cy="10" r="3"/></svg>',
	calendar: '<svg viewBox="0 0 24 24"><rect x="3" y="4" width="18" height="18" rx="2"/><path d="M16 2v4M8 2v4M3 10h18"/></svg>',
	clock: '<svg viewBox="0 0 24 24"><circle cx="12" cy="12" r="9"/><path d="M12 7v5l3 2"/></svg>',
	bookmark: '<svg viewBox="0 0 24 24"><path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"/></svg>',
};

// ---------- API ----------

async function getJson(url) {
	const response = await fetch(url);
	if (!response.ok) {
		throw new Error("HTTP " + response.status);
	}
	return response.json();
}

async function loadCategories() {
	try {
		const categories = await getJson("/api/categories");
		renderChips(categories);
	} catch (e) {
		chips.innerHTML = "";
	}
}

async function loadQuests() {
	const keyword = searchInput.value.trim();
	const params = new URLSearchParams();
	if (activeCategoryId) params.set("categoryId", activeCategoryId);
	else if (keyword) params.set("q", keyword);

	renderSkeletons();
	try {
		let quests = await getJson("/api/search?" + params);
		// The backend searches by category OR keyword, so apply the keyword here too.
		if (activeCategoryId && keyword) {
			quests = quests.filter((q) => q.title.toLowerCase().includes(keyword.toLowerCase()));
		}
		renderQuests(quests, keyword);
	} catch (e) {
		renderState("Can't reach the server", "Make sure the app is running with mvnw spring-boot:run, then refresh.");
		count.textContent = "";
	}
}

// ---------- Rendering ----------

// Small helper: el("p", "class", "text") builds an element safely (no HTML injection).
function el(tag, className, text) {
	const node = document.createElement(tag);
	if (className) node.className = className;
	if (text !== undefined && text !== null) node.textContent = text;
	return node;
}

function tagColorFor(categoryId) {
	return TAG_COLORS[(categoryId - 1) % TAG_COLORS.length] || TAG_COLORS[0];
}

function renderChips(categories) {
	chips.innerHTML = "";
	const all = [{ categoryId: null, categoryName: "All" }, ...categories];
	for (const category of all) {
		const chip = el("button", "chip", category.categoryName);
		chip.type = "button";
		chip.setAttribute("role", "tab");
		const isActive = category.categoryId === activeCategoryId;
		chip.classList.toggle("is-active", isActive);
		chip.setAttribute("aria-selected", String(isActive));
		chip.addEventListener("click", () => {
			activeCategoryId = category.categoryId;
			renderChips(categories);
			loadQuests();
		});
		chips.appendChild(chip);
	}
}

function renderSkeletons() {
	grid.innerHTML = "";
	for (let i = 0; i < 3; i++) {
		grid.appendChild(el("div", "card skeleton"));
	}
}

function renderState(title, message) {
	grid.innerHTML = "";
	const box = el("div", "state");
	box.appendChild(el("strong", "", title));
	box.appendChild(el("span", "", message));
	grid.appendChild(box);
}

function renderQuests(quests, keyword) {
	count.textContent = quests.length === 1 ? "1 quest" : quests.length + " quests";
	if (quests.length === 0) {
		renderState(
			keyword ? "No quests match “" + keyword + "”" : "No quests here yet",
			"Try another keyword or category."
		);
		return;
	}
	grid.innerHTML = "";
	for (const quest of quests) {
		grid.appendChild(questCard(quest));
	}
}

function metaItem(icon, text) {
	const li = el("li");
	li.innerHTML = ICONS[icon]; // our own static SVG, not user data
	li.appendChild(el("span", "", text));
	return li;
}

function initials(name) {
	return name.split(" ").filter(Boolean).map((part) => part[0]).slice(0, 2).join("").toUpperCase();
}

function formatPrice(value) {
	return "₱" + Number(value).toLocaleString("en-PH", { maximumFractionDigits: 2 });
}

function questCard(quest) {
	const card = el("article", "card");

	// Top: category tag + bookmark
	const top = el("div", "card-top");
	const tag = el("span", "tag", quest.categoryName);
	const [bg, fg] = tagColorFor(quest.categoryId);
	tag.style.setProperty("--tag-bg", bg);
	tag.style.setProperty("--tag-fg", fg);
	const bookmark = el("button", "bookmark");
	bookmark.type = "button";
	bookmark.setAttribute("aria-label", "Bookmark " + quest.title);
	bookmark.setAttribute("aria-pressed", "false");
	bookmark.innerHTML = ICONS.bookmark;
	// TODO: save to /api/bookmarks once login exists. For now it only toggles on screen.
	bookmark.addEventListener("click", () => {
		const saved = bookmark.classList.toggle("is-saved");
		bookmark.setAttribute("aria-pressed", String(saved));
		showToast(saved ? "Saved (preview only, not stored yet)" : "Removed from saved");
	});
	top.append(tag, bookmark);

	// Body: title, description, details
	const body = el("div", "card-body");
	body.appendChild(el("h3", "card-title", quest.title));
	body.appendChild(el("p", "card-desc", quest.description));
	const meta = el("ul", "meta");
	if (quest.meetupSpots) meta.appendChild(metaItem("pin", quest.meetupSpots));
	if (quest.availableDays) meta.appendChild(metaItem("calendar", quest.availableDays));
	if (quest.deliveryDays) {
		meta.appendChild(metaItem("clock", "Done in " + quest.deliveryDays + (quest.deliveryDays === 1 ? " day" : " days")));
	}
	body.appendChild(meta);

	// Footer: seller + price
	const foot = el("div", "card-foot");
	const seller = el("div", "seller");
	seller.appendChild(el("span", "seller-avatar", initials(quest.sellerName)));
	seller.appendChild(el("span", "seller-name", quest.sellerName));
	const price = el("div", "price");
	price.appendChild(el("small", "", "Reward"));
	price.appendChild(el("strong", "", formatPrice(quest.price)));
	foot.append(seller, price);

	// Book button (placeholder)
	const book = el("button", "book-btn", "Book this quest");
	book.type = "button";
	book.addEventListener("click", () => showToast("Booking isn't built yet. This is a preview."));

	card.append(top, body, foot, book);
	return card;
}

// ---------- Small UI helpers ----------

let toastTimer;
function showToast(message) {
	toast.textContent = message;
	toast.classList.add("is-visible");
	clearTimeout(toastTimer);
	toastTimer = setTimeout(() => toast.classList.remove("is-visible"), 2400);
}

// Anything marked data-soon is a feature that isn't built yet.
document.addEventListener("click", (event) => {
	const soon = event.target.closest("[data-soon]");
	if (soon) {
		event.preventDefault();
		showToast(soon.dataset.soon + " is coming soon.");
	}
	const focusSearch = event.target.closest("[data-focus-search]");
	if (focusSearch) {
		event.preventDefault();
		window.scrollTo({ top: 0, behavior: "smooth" });
		searchInput.focus();
	}
});

searchForm.addEventListener("submit", (event) => {
	event.preventDefault();
	loadQuests();
});

// Search as you type, with a short delay so we don't call the API on every key.
let typingTimer;
searchInput.addEventListener("input", () => {
	clearTimeout(typingTimer);
	typingTimer = setTimeout(loadQuests, 300);
});

loadCategories();
loadQuests();
