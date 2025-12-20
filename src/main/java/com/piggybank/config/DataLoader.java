package com.piggybank.config;

import com.piggybank.model.*;
import com.piggybank.model.Location.LocationType;
import com.piggybank.model.User.UserRole;
import com.piggybank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import com.piggybank.model.Category;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SavingGoalRepository savingGoalRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void run(String... args) throws Exception {
        loadRwandanLocations();
        loadSampleData();
    }

    private void loadRwandanLocations() {
        if (locationRepository.count() == 0) {

            Location kigali = new Location("Kigali", "KG", LocationType.PROVINCE, null);
            Location southern = new Location("Southern", "ST", LocationType.PROVINCE, null);
            Location northern = new Location("Northern", "NT", LocationType.PROVINCE, null);
            Location eastern = new Location("Eastern", "ET", LocationType.PROVINCE, null);
            Location western = new Location("Western", "WT", LocationType.PROVINCE, null);

            locationRepository.saveAll(Arrays.asList(kigali, southern, northern, eastern, western));

            Location gasabo = new Location("Gasabo", "GAS", LocationType.DISTRICT, kigali);
            Location kicukiro = new Location("Kicukiro", "KIC", LocationType.DISTRICT, kigali);
            Location nyarugenge = new Location("Nyarugenge", "NYG", LocationType.DISTRICT, kigali);

            locationRepository.saveAll(Arrays.asList(gasabo, kicukiro, nyarugenge));

            Location gisozi = new Location("Gisozi", "GISO", LocationType.SECTOR, gasabo);
            Location kimihurura = new Location("Kimihurura", "KIMI", LocationType.SECTOR, gasabo);

            locationRepository.saveAll(Arrays.asList(gisozi, kimihurura));
        }
    }

    private void loadSampleData() {
        if (userRepository.count() == 0) {
            Location kigali = locationRepository.findByName("Kigali").orElse(null);
            Location gasabo = locationRepository.findByName("Gasabo").orElse(null);

            if (kigali != null && gasabo != null) {
                User admin = new User("Admin", "User", "admin@piggybank.rw", 
                        "admin123", "+250788123456", UserRole.ADMIN, kigali);
                userRepository.save(admin);

                User john = new User("John", "Doe", "john.doe@example.com", 
                        "password123", "+250788654321", UserRole.USER, gasabo);
                userRepository.save(john);

                Category education = new Category("Education", "Education related savings");
                Category emergency = new Category("Emergency", "Emergency fund");
                Category vacation = new Category("Vacation", "Vacation and travel savings");
                categoryRepository.saveAll(Arrays.asList(education, emergency, vacation));

                SavingGoal tuitionGoal = new SavingGoal("Tuition Fees", "Save for university tuition",
                        new BigDecimal("5000000"), LocalDate.of(2024, 9, 1), john);
                SavingGoal emergencyGoal = new SavingGoal("Emergency Fund", "3 months emergency fund",
                        new BigDecimal("1500000"), LocalDate.of(2024, 12, 31), john);
                savingGoalRepository.saveAll(Arrays.asList(tuitionGoal, emergencyGoal));

                Transaction deposit1 = new Transaction(new BigDecimal("100000"),
                        Transaction.TransactionType.DEPOSIT,
                        "Monthly savings", john, tuitionGoal);
                Transaction deposit2 = new Transaction(new BigDecimal("50000"),
                        Transaction.TransactionType.DEPOSIT,
                        "Emergency fund deposit", john, emergencyGoal);
                transactionRepository.saveAll(Arrays.asList(deposit1, deposit2));

                tuitionGoal.setCurrentAmount(new BigDecimal("100000"));
                emergencyGoal.setCurrentAmount(new BigDecimal("50000"));
                savingGoalRepository.saveAll(Arrays.asList(tuitionGoal, emergencyGoal));
            }
        }
    }
}