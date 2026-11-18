package com.sidequest.common.config;

import com.sidequest.account.entity.Profile;
import com.sidequest.account.entity.Role;
import com.sidequest.account.entity.User;
import com.sidequest.account.entity.UserStatus;
import com.sidequest.account.repository.ProfileRepository;
import com.sidequest.account.repository.UserRepository;
import com.sidequest.community.entity.GuildRank;
import com.sidequest.community.entity.Review;
import com.sidequest.community.repository.GuildRankRepository;
import com.sidequest.community.repository.ReviewRepository;
import com.sidequest.quest.entity.Category;
import com.sidequest.quest.entity.Quest;
import com.sidequest.quest.entity.QuestSize;
import com.sidequest.quest.entity.QuestStatus;
import com.sidequest.quest.repository.CategoryRepository;
import com.sidequest.quest.repository.QuestRepository;
import com.sidequest.transaction.entity.AcceptanceStatus;
import com.sidequest.transaction.entity.QuestAcceptance;
import com.sidequest.transaction.repository.QuestAcceptanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// Sample data for local development only (runs with the "dev" and "mysql" profiles, never "prod").
// Names and quests follow the Figma prototype; rewards follow the workbook's size ranges.
// Full name used for @Profile because our entity is also called Profile.
@Component
@org.springframework.context.annotation.Profile({"dev", "mysql"})
@RequiredArgsConstructor
public class DevDataSeeder implements CommandLineRunner {

	// Obvious placeholder. Real hashing (BCrypt) comes with Spring Security.
	private static final String PLACEHOLDER_HASH = "CHANGE_ME";

	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final GuildRankRepository guildRankRepository;
	private final CategoryRepository categoryRepository;
	private final QuestRepository questRepository;
	private final QuestAcceptanceRepository acceptanceRepository;
	private final ReviewRepository reviewRepository;

	@Override
	@Transactional
	public void run(String... args) {
		// Skip if already seeded (e.g. after a DevTools restart).
		if (userRepository.count() > 0) {
			return;
		}

		User juan = createUser("juan.delacruz@cit.edu", "22-1001-101", "Juan Dela Cruz", "BSIT", 3, "CCS Building", 95);
		User mary = createUser("mary.mendoza@cit.edu", "22-1002-102", "Mary Mendoza", "BSIT", 3, "CCS Building", 40);
		User carlo = createUser("carlo.dizon@cit.edu", "23-1003-103", "Carlo Dizon", "BSCS", 2, "Library", 175);
		User bea = createUser("bea.cruz@cit.edu", "24-1004-104", "Bea Cruz", "BSIT", 1, "Library", 0);
		User paolo = createUser("paolo.reyes@cit.edu", "21-1005-105", "Paolo Reyes", "BSEMC", 4, "Gym", 320);
		User jamie = createUser("jamie.lim@cit.edu", "22-1006-106", "Jamie Lim", "BSCS", 3, "Canteen", 520);
		User admin = createUser("admin@cit.edu", "00-0000-001", "SideQuest Admin", null, null, null, 0);
		admin.setRole(Role.ADMIN);

		// Sample rank points (the last number above), as if earned before today, so every rank
		// shows up: Bea is a Recruit, Mary a Page, Juan a Squire (rank 3 of 6, like the Figma
		// profile), Carlo a Knight, Paolo a Champion and Jamie a Legend.

		// Juan's meter matches the Figma profile: 8 / 10, next energy in 24 minutes.
		juan.setEnergy(8);
		juan.setEnergyUpdatedAt(LocalDateTime.now().minusMinutes(6));

		Category printing = createCategory("Printing");
		Category tutoring = createCategory("Tutoring");
		Category errands = createCategory("Errands");
		Category equipment = createCategory("Equipment");
		Category techHelp = createCategory("Tech Help");
		Category eventHelp = createCategory("Event Help");

		createQuest(mary, techHelp, "Technical personnel needed!",
				"We are looking for technical personnel willing to support us during the opening week, "
						+ "in alliance with the SSG. Setting up laptops, projectors and sound.",
				QuestSize.LARGE, "300", "CCS Building", 4, QuestStatus.OPEN);
		createQuest(carlo, tutoring, "Peer tutor needed!",
				"Looking for a peer tutor for Data Structures: linked lists, stacks and queues before our long quiz.",
				QuestSize.MEDIUM, "150", "Library", 3, QuestStatus.OPEN);
		createQuest(bea, printing, "Printing help needed!",
				"We need someone to print 3 copies of our 20-page group report and bring them to room 301.",
				QuestSize.SMALL, "50", "Library", 1, QuestStatus.OPEN);
		createQuest(paolo, eventHelp, "Event helpers needed!",
				"Help set up chairs, booths and decorations for the CCS week opening program. Half a day.",
				QuestSize.LARGE, "400", "Gym", 5, QuestStatus.OPEN);
		createQuest(jamie, techHelp, "Poster designer needed!",
				"Need a clean poster for ITS week. I will send the text and logo, you send a print-ready file.",
				QuestSize.MEDIUM, "200", "Online", 2, QuestStatus.OPEN);
		createQuest(bea, equipment, "Borrow a scientific calculator",
				"Lending me a scientific calculator for tomorrow's Physics exam. I will return it right after.",
				QuestSize.SMALL, "40", "Canteen", 1, QuestStatus.OPEN);

		// Waiting for admin review, so it is not on the board yet.
		createQuest(juan, errands, "Moving boxes help",
				"Help me carry 6 boxes from the dorm to the CCS org room.",
				QuestSize.SMALL, "60", "CCS Building", 2, QuestStatus.PENDING);

		// Already taken by Carlo, so it shows as in progress.
		Quest tutorQuest = createQuest(juan, tutoring, "Tutor for Data Structures",
				"One review session on trees and graphs before the finals.",
				QuestSize.MEDIUM, "180", "Library", 3, QuestStatus.IN_PROGRESS);
		QuestAcceptance acceptance = new QuestAcceptance();
		acceptance.setQuest(tutorQuest);
		acceptance.setAdventurer(carlo);
		acceptanceRepository.save(acceptance);

		// Finished quests with reviews, so profiles have reviews to show (Objective 5).
		// Stand-in data: the real accept -> fund -> complete flow is Objective 4.
		// The rank points above already count these, so no points are added here.
		QuestAcceptance thesisPrint = completeQuest(mary, juan, printing, "Print my thesis draft",
				"Print and bind 2 copies of my 80-page thesis draft.", QuestSize.MEDIUM, "120", "CCS Building");
		createReview(thesisPrint, mary, 5, "Fast and neat, even bound it for me. Thank you!");
		createReview(thesisPrint, juan, 4, "Clear instructions and paid right away.");

		QuestAcceptance tripod = completeQuest(paolo, juan, equipment, "Borrow a tripod",
				"Need a camera tripod for our video shoot this afternoon.", QuestSize.SMALL, "50", "Gym");
		createReview(tripod, paolo, 4, "Returned on time and in good shape.");

		QuestAcceptance debugging = completeQuest(juan, jamie, techHelp, "Help me fix my Spring Boot error",
				"My app won't start because of a database error. Help me find out why.", QuestSize.MEDIUM, "150", "Online");
		createReview(debugging, juan, 5, "Found the problem in 10 minutes and explained it well.");
		createReview(debugging, jamie, 3, "Okay, but the quest description was missing some details.");
	}

	// A quest that is already done: the quest and the acceptance are both COMPLETED.
	private QuestAcceptance completeQuest(User giver, User adventurer, Category category, String title,
			String description, QuestSize size, String reward, String location) {
		Quest quest = createQuest(giver, category, title, description, size, reward, location, -3, QuestStatus.COMPLETED);
		QuestAcceptance acceptance = new QuestAcceptance();
		acceptance.setQuest(quest);
		acceptance.setAdventurer(adventurer);
		acceptance.setStatus(AcceptanceStatus.COMPLETED);
		acceptance.setCompletedAt(LocalDateTime.now().minusDays(3));
		return acceptanceRepository.save(acceptance);
	}

	private void createReview(QuestAcceptance acceptance, User reviewer, int rating, String comment) {
		Review review = new Review();
		review.setAcceptance(acceptance);
		review.setReviewer(reviewer);
		review.setRating(rating);
		review.setComment(comment);
		reviewRepository.save(review);
	}

	private User createUser(String email, String studentId, String fullName, String program,
			Integer yearLevel, String preferredLocation, int rankPoints) {
		User user = new User();
		user.setEmail(email);
		user.setStudentId(studentId);
		user.setFullName(fullName);
		user.setPasswordHash(PLACEHOLDER_HASH);
		user.setStatus(UserStatus.ACTIVE); // already verified
		user.setPreferredLocation(preferredLocation);
		userRepository.save(user);

		Profile profile = new Profile();
		profile.setUser(user);
		profile.setProgram(program);
		profile.setYearLevel(yearLevel);
		profileRepository.save(profile);

		// Starts as a Recruit with 0 points; addPoints moves the title up to match.
		GuildRank rank = new GuildRank();
		rank.setUser(user);
		rank.addPoints(rankPoints);
		guildRankRepository.save(rank);
		return user;
	}

	private Category createCategory(String name) {
		Category category = new Category();
		category.setName(name);
		return categoryRepository.save(category);
	}

	private Quest createQuest(User poster, Category category, String title, String description,
			QuestSize size, String reward, String location, int daysUntilDeadline, QuestStatus status) {
		Quest quest = new Quest();
		quest.setPoster(poster);
		quest.setCategory(category);
		quest.setTitle(title);
		quest.setDescription(description);
		quest.setSize(size);
		quest.setReward(new BigDecimal(reward));
		quest.setLocation(location);
		quest.setDeadline(LocalDateTime.of(LocalDate.now().plusDays(daysUntilDeadline), LocalTime.of(17, 0)));
		quest.setStatus(status);
		return questRepository.save(quest);
	}
}
