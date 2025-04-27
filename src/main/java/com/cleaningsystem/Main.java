package com.cleaningsystem;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cleaningsystem.dao.UserAccountDAO;
import com.cleaningsystem.dao.UserProfileDAO;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.Cleaner;
import com.cleaningsystem.model.HomeOwner;
import com.cleaningsystem.model.PlatformManager;
import com.cleaningsystem.model.UserAdmin;

@Component
public class Main implements CommandLineRunner {

    private final UserAccountDAO userAccountDAO;
    private final UserProfileDAO userProfileDAO;
    private final ApplicationContext applicationContext;

    private static Scanner scanner = new Scanner(System.in);
    private UserAccount currentUser = null;

    // Constructor injection
    public Main(UserAccountDAO userAccountDAO, UserProfileDAO userProfileDAO, ApplicationContext applicationContext) {
        this.userAccountDAO = userAccountDAO;
        this.userProfileDAO = userProfileDAO;
        this.applicationContext = applicationContext;
    }

    public void login() {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        currentUser = userAccountDAO.login(username, password);
        if (currentUser != null) {
            String profileName = userProfileDAO.getProfileById(currentUser.getProfileId()).getProfileName();
            routeToUserMenu(profileName);
        } else {
            System.out.println("Invalid credentials. Please try again.");
            login();
        }
    }

    private void routeToUserMenu(String profileName) {
        switch (profileName.toLowerCase()) {
            case "user admin" -> {
                UserAdmin admin = applicationContext.getBean(UserAdmin.class);
                admin.setUid(currentUser.getUid());
                admin.setUsername(currentUser.getUsername());
                admin.showAdminMenu();
            }
            case "cleaner" -> {
                Cleaner cleaner = applicationContext.getBean(Cleaner.class);
                cleaner.setUid(currentUser.getUid());
                cleaner.setUsername(currentUser.getUsername());
                cleaner.showCleanerMenu();
            }
            case "home owner" -> {
                HomeOwner homeOwner = applicationContext.getBean(HomeOwner.class);
                homeOwner.setUid(currentUser.getUid());
                homeOwner.setUsername(currentUser.getUsername());
                homeOwner.showHomeOwnerMenu();
            }
            case "platform manager" -> {
                PlatformManager platformManager = applicationContext.getBean(PlatformManager.class);
                platformManager.setUid(currentUser.getUid());
                platformManager.setUsername(currentUser.getUsername());
                platformManager.showPlatformManagerMenu();
            }
            default -> System.out.println("Unknown user profile type.");
        }
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to the Cleaning System!");
        login();
    }
}
