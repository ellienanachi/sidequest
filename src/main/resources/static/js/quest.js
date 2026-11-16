// Quest Details: loads one quest from /api/quests/{id}.
// Request!, save and message are placeholders until Objectives 3 to 5 are built.

const detail = document.getElementById("detail");
const questId = new URLSearchParams(location.search).get("id");

renderSidebar("home");
document.getElementById("back").innerHTML = icon("back");
document.getElementById("hero-art").innerHTML = DOODLES[1];
document.getElementById("message").innerHTML = icon("chat");

const saveButton = document.getElementById("save");
saveButton.innerHTML = icon("heart");
// TODO (Objective 3): store in saved_quests once login exists.
saveButton.addEventListener("click", () => {
	const saved = saveButton.classList.toggle("is-saved");
	saveButton.setAttribute("aria-pressed", String(saved));
	showToast(saved ? "Saved (preview only, not stored yet)" : "Removed from saved");
});

document.getElementById("message").dataset.soon = "Messaging";
// TODO (Objective 4): accept the quest, spend 1 energy, ask the giver to fund it.
document.getElementById("request").addEventListener("click", () =>
	showToast("Accepting quests is coming soon. It will use 1 energy."));

async function loadQuest() {
	if (!questId) {
		renderError("No quest selected", "Go back to the Quest Board and pick one.");
		return;
	}
	try {
		renderQuest(await getJson("/api/quests/" + encodeURIComponent(questId)));
	} catch (e) {
		renderError("Quest not found", "It may have been removed. Go back to the Quest Board.");
	}
}

function renderError(title, message) {
	const box = el("div", "state");
	box.append(el("strong", "", title), el("span", "", message));
	detail.replaceChildren(box);
}

function fact(label, value) {
	const li = el("li");
	li.append(el("span", "", label), el("span", "", value));
	return li;
}

function renderQuest(quest) {
	document.title = quest.title + " · SideQuest/s";

	// Quest giver, e.g. "MARY M." and "BSIT - 3"
	const poster = el("section", "poster");
	poster.appendChild(el("div", "poster-avatar", initials(quest.posterName)));
	const who = el("div");
	who.appendChild(el("h1", "poster-name", shortName(quest.posterName)));
	const sub = [quest.posterProgram, quest.posterYearLevel].filter((v) => v !== null && v !== undefined).join(" - ");
	if (sub) who.appendChild(el("p", "poster-sub", sub));
	poster.appendChild(who);

	// Left panel: what is needed
	const need = el("section", "panel");
	need.appendChild(el("h2", "", quest.title));
	need.appendChild(el("p", "", quest.description));
	const needFacts = el("ul", "facts");
	needFacts.append(
		fact("Category", quest.categoryName),
		fact("Size", titleCase(quest.size)),
		fact("Location", quest.location || "Anywhere"),
		fact("Deadline", shortDate(quest.deadline)));
	need.appendChild(needFacts);

	// Right panel: the reward, as the workbook describes it (escrow, 10% commission)
	const reward = el("section", "panel");
	reward.appendChild(el("h2", "", "Reward " + peso(quest.reward)));
	reward.appendChild(el("p", "",
		"Held in escrow once the quest giver funds it, and released to you when they confirm the quest is done."));
	const methods = quest.paymentMethods.map((m) => (m === "QR_PH" ? "QR Ph" : titleCase(m).replace("Gcash", "GCash"))).join(", ");
	const rewardFacts = el("ul", "facts");
	rewardFacts.append(
		fact("You receive", peso(quest.adventurerPayout)),
		fact("SideQuest/s commission", "10%"),
		fact("Paid through", methods),
		fact("Energy to accept", String(quest.energyToAccept)));
	reward.appendChild(rewardFacts);

	const panels = el("div", "panels");
	panels.append(need, reward);
	detail.replaceChildren(poster, panels);

	document.getElementById("action-note").textContent =
		"Uses " + quest.energyToAccept + " energy. You get it back when the quest is completed.";
	document.getElementById("action-bar").hidden = false;
}

loadQuest();
