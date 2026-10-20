package com.sidequest.common.config;

import com.sidequest.account.entity.Profile;
import com.sidequest.account.entity.Role;
import com.sidequest.account.entity.User;
import com.sidequest.account.repository.ProfileRepository;
import com.sidequest.account.repository.UserRepository;
import com.sidequest.listing.entity.Category;
import com.sidequest.listing.entity.Listing;
import com.sidequest.listing.entity.ListingStatus;
import com.sidequest.listing.repository.CategoryRepository;
import com.sidequest.listing.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

// Sample data for local development only (runs with the "dev" profile).
// Full name used because our entity is also called Profile.
@Component
@org.springframework.context.annotation.Profile("dev")
@RequiredArgsConstructor
public class DevDataSeeder implements CommandLineRunner {

	// Obvious placeholder. Real hashing (BCrypt) comes with Spring Security.
	private static final String PLACEHOLDER_HASH = "CHANGE_ME";

	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final CategoryRepository categoryRepository;
	private final ListingRepository listingRepository;

	@Override
	@Transactional
	public void run(String... args) {
		// Skip if already seeded (e.g. after a DevTools restart).
		if (userRepository.count() > 0) {
			return;
		}

		User juan = createUser("juan.delacruz@cit.edu", "21-1234-567", "Juan", "Dela Cruz", 3, "BSIT", Role.STUDENT);
		User maria = createUser("maria.santos@cit.edu", "22-2345-678", "Maria", "Santos", 2, "BSCS", Role.STUDENT);
		createUser("admin@cit.edu", "00-0000-001", "Site", "Admin", null, null, Role.ADMIN);

		Category tutoring = createCategory("Tutoring", "Peer tutoring and study help");
		Category design = createCategory("Design", "Logos, posters, slides and layouts");
		Category tech = createCategory("Tech Help", "Laptop fixes, installs and setup");
		Category errands = createCategory("Errands", "Printing, pickups and deliveries on campus");

		createListing(juan, tutoring, "Java OOP tutoring (1 hour)",
				"One-on-one help with classes, inheritance, and interfaces.",
				"150.00", 1, "Mon, Wed, Fri", "Library, Canteen");
		createListing(maria, design, "Logo and poster design for your org",
				"Clean, print-ready designs for events and org pages. Two revisions included.",
				"300.00", 3, "Tue, Thu, Sat", "Online, Canteen");
		createListing(juan, tech, "Laptop cleanup and software install",
				"Remove junk, speed up boot, and install IntelliJ, VS Code, or Office for class.",
				"200.00", 1, "Mon to Fri", "GLE Building lobby");
		createListing(maria, errands, "Print and deliver your requirements",
				"Send the file, I print it and bring it to your room before class.",
				"50.00", 1, "Mon to Sat", "Anywhere on campus");
		createListing(maria, tutoring, "Calculus 1 review session",
				"Exam prep for limits and derivatives with practice problems.",
				"120.00", 2, "Wed, Sat", "Library");
	}

	private Category createCategory(String name, String description) {
		Category category = new Category();
		category.setCategoryName(name);
		category.setDescription(description);
		return categoryRepository.save(category);
	}

	private void createListing(User seller, Category category, String title, String description,
			String price, int deliveryDays, String availableDays, String meetupSpots) {
		Listing listing = new Listing();
		listing.setSeller(seller);
		listing.setCategory(category);
		listing.setTitle(title);
		listing.setDescription(description);
		listing.setPrice(new BigDecimal(price));
		listing.setDeliveryDays(deliveryDays);
		listing.setAvailableDays(availableDays);
		listing.setMeetupSpots(meetupSpots);
		listing.setStatus(ListingStatus.ACTIVE);
		listingRepository.save(listing);
	}

	private User createUser(String email, String studentId, String firstName, String lastName,
			Integer yearLevel, String program, Role role) {
		User user = new User();
		user.setEmail(email);
		user.setPasswordHash(PLACEHOLDER_HASH);
		user.setStudentIdNumber(studentId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setYearLevel(yearLevel);
		user.setProgram(program);
		user.setRole(role);
		user.setIsVerified(true);
		userRepository.save(user);

		Profile profile = new Profile();
		profile.setUser(user);
		profile.setDisplayName(firstName + " " + lastName);
		profileRepository.save(profile);
		return user;
	}
}
