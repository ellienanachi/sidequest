// Shared helpers for every SideQuest/s page: icons, sidebar, toast and formatting.
// Pages load this first, then their own script (board.js, quest.js).

const ICONS = {
	home: '<path d="M3 10.5 12 3l9 7.5V20a1 1 0 0 1-1 1h-5v-6h-6v6H4a1 1 0 0 1-1-1z"/>',
	chat: '<path d="M21 15a2 2 0 0 1-2 2H8l-5 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/><path d="M8 9h8M8 13h5"/>',
	scroll: '<path d="M8 3h11a2 2 0 0 1 2 2v2h-4"/><path d="M17 7v12a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2v-1h10"/><path d="M8 3a2 2 0 0 0-2 2v13"/><path d="M10 9h4M10 13h4"/>',
	user: '<circle cx="12" cy="8" r="4"/><path d="M4 21a8 8 0 0 1 16 0"/>',
	bell: '<path d="M6 8a6 6 0 0 1 12 0c0 7 3 9 3 9H3s3-2 3-9"/><path d="M10.3 21a1.94 1.94 0 0 0 3.4 0"/>',
	star: '<path d="m12 2 2.9 6.6 7.1.6-5.4 4.7 1.6 7L12 17.3 5.8 20.9l1.6-7L2 9.2l7.1-.6z"/>',
	search: '<circle cx="11" cy="11" r="7"/><path d="m20 20-3.5-3.5"/>',
	back: '<path d="M19 12H5M11 6l-6 6 6 6"/>',
	heart: '<path d="M20.8 4.6a5.5 5.5 0 0 0-7.8 0L12 5.7l-1-1.1a5.5 5.5 0 0 0-7.8 7.8L12 21.2l8.8-8.8a5.5 5.5 0 0 0 0-7.8z"/>',
	plus: '<path d="M12 5v14M5 12h14"/>',
};

// Hand-drawn style art for quest cards, like the Figma doodles.
const DOODLES = [
	'<svg viewBox="0 0 100 100" fill="none" stroke="currentColor" stroke-width="6" stroke-linejoin="round"><path d="M30 12 40 40 70 30 52 52 74 84 44 66 24 88 30 58 8 46 36 38z"/></svg>',
	'<svg viewBox="0 0 100 100" fill="none" stroke="currentColor" stroke-width="6" stroke-linecap="round"><circle cx="50" cy="50" r="40"/><circle cx="38" cy="40" r="3" fill="currentColor"/><circle cx="62" cy="40" r="3" fill="currentColor"/><path d="M32 58q18 18 36 0"/></svg>',
];

function icon(name) {
	return '<svg class="icon" viewBox="0 0 24 24" aria-hidden="true">' + ICONS[name] + "</svg>";
}

// Builds an element safely: text goes in as textContent, never as HTML.
function el(tag, className, text) {
	const node = document.createElement(tag);
	if (className) node.className = className;
	if (text !== undefined && text !== null) node.textContent = text;
	return node;
}

// Our own static SVG only (never user data).
function iconButton(name, label, className) {
	const button = el("button", className || "side-link");
	button.type = "button";
	button.setAttribute("aria-label", label);
	button.title = label;
	button.innerHTML = icon(name);
	return button;
}

// Left sidebar from the Figma OPTIONS frame. Features that are not built yet show a toast.
function renderSidebar(active) {
	const nav = document.getElementById("sidebar");
	const items = [
		{ name: "home", label: "Quest Board", href: "/" },
		{ name: "chat", label: "Messages", soon: "Messages" },
		{ name: "scroll", label: "My quests", soon: "My quests" },
		{ name: "user", label: "Profile", soon: "Profiles" },
	];
	for (const item of items) {
		const link = item.href ? el("a", "side-link") : iconButton(item.name, item.label);
		if (item.href) {
			link.href = item.href;
			link.setAttribute("aria-label", item.label);
			link.title = item.label;
			link.innerHTML = icon(item.name);
		} else {
			link.dataset.soon = item.soon;
		}
		link.classList.toggle("is-active", item.name === active);
		if (item.name === active) link.setAttribute("aria-current", "page");
		nav.appendChild(link);
	}
	nav.appendChild(el("div", "side-spacer"));
	const post = iconButton("plus", "Post a quest");
	post.dataset.soon = "Posting a quest";
	nav.appendChild(post);
	const bell = iconButton("bell", "Notifications", "side-link desktop-only");
	bell.dataset.soon = "Notifications";
	nav.appendChild(bell);
}

let toastTimer;
function showToast(message) {
	const toast = document.getElementById("toast");
	toast.textContent = message;
	toast.classList.add("is-visible");
	clearTimeout(toastTimer);
	toastTimer = setTimeout(() => toast.classList.remove("is-visible"), 2600);
}

// Anything marked data-soon is a feature that is not built yet.
document.addEventListener("click", (event) => {
	const soon = event.target.closest("[data-soon]");
	if (soon) {
		event.preventDefault();
		showToast(soon.dataset.soon + " is coming soon.");
	}
});

async function getJson(url) {
	const response = await fetch(url);
	if (!response.ok) throw new Error("HTTP " + response.status);
	return response.json();
}

function peso(value) {
	return "₱" + Number(value).toLocaleString("en-PH", { maximumFractionDigits: 2 });
}

function titleCase(word) {
	return word ? word.charAt(0) + word.slice(1).toLowerCase() : "";
}

function shortDate(iso) {
	return iso ? new Date(iso).toLocaleDateString("en-PH", { month: "short", day: "numeric" }) : "No deadline";
}

function initials(name) {
	return (name || "?").split(" ").filter(Boolean).map((p) => p[0]).slice(0, 2).join("").toUpperCase();
}

// "Mary Mendoza" -> "Mary M." like the Figma cards.
function shortName(name) {
	const parts = (name || "").split(" ").filter(Boolean);
	return parts.length > 1 ? parts[0] + " " + parts[parts.length - 1][0] + "." : name;
}
