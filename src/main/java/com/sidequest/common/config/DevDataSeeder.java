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
		createUser("maria.santos@cit.edu", "22-2345-678", "Maria", "Santos", 2, "BSCS", Role.STUDENT);
		createUser("admin@cit.edu", "00-0000-001", "Site", "Admin", null, null, Role.ADMIN);

		Category tutoring = new Category();
		tutoring.setCategoryName("Tutoring");
		tutoring.setDescription("Peer tutoring and study help");
		categoryRepository.save(tutoring);

		Listing listing = new Listing();
		listing.setSeller(juan);
		listing.setCategory(tutoring);
		listing.setTitle("Java OOP tutoring (1 hour)");
		listing.setDescription("One-on-one help with classes, inheritance, and interfaces.");
		listing.setPrice(new BigDecimal("150.00"));
		listing.setDeliveryDays(1);
		listing.setAvailableDays("Mon, Wed, Fri");
		listing.setMeetupSpots("Library, Canteen");
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
