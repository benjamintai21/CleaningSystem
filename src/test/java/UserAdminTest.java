import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static com.cleaningsystem.db.Queries.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserAdminTest {

    // === Mocked Beans ===
    @Mock private JdbcTemplate jdbcTemplate;
    private static UserAccount userAccount;
    private static UserProfile userProfile;

    private CreateUserAccountController createController;
    private SearchUserAccountController searchController;
    private SuspendUserAccountController suspendController;
    private UpdateUserAccountController updateController;
    private ViewUserAccountController viewController;

    private CreateUserProfileController createUserProfileController;
    private SearchUserProfileController searchUserProfileController;
    private SuspendUserProfileController suspendUserProfileController;
    private UpdateUserProfileController updateUserProfileController;
    private ViewUserProfileController viewUserProfileController;

    // === Setup ===
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
 
        userAccount = Mockito.spy(new UserAccount());
        userProfile = Mockito.spy(new UserProfile());

        createController = new CreateUserAccountController(userAccount);
        searchController = new SearchUserAccountController(userAccount);
        suspendController = new SuspendUserAccountController(userAccount);
        updateController = new UpdateUserAccountController(userAccount);
        viewController = new ViewUserAccountController(userAccount);

        createUserProfileController = new CreateUserProfileController(userProfile); // inject mock
        searchUserProfileController = new SearchUserProfileController(userProfile);
        suspendUserProfileController = new SuspendUserProfileController(userProfile);
        updateUserProfileController = new UpdateUserProfileController(userProfile);
        viewUserProfileController = new ViewUserProfileController(userProfile);


        java.lang.reflect.Field jdbcField1 = UserAccount.class.getDeclaredField("jdbcTemplate");
        jdbcField1.setAccessible(true);
        jdbcField1.set(userAccount, jdbcTemplate);

        java.lang.reflect.Field jdbcField2 = UserProfile.class.getDeclaredField("jdbcTemplate");
        jdbcField2.setAccessible(true);
        jdbcField2.set(userProfile, jdbcTemplate);
        
    }
    // === Minimal Entity and Controller Definitions ===

    // -- Entities --
    public static class UserAccount {
        private int uid;
        private String name;
        private int age;
        private String dob;
        private String gender;
        private String address;
        private String email;
        private String username;
        private String password;
        private int profileId;
        private boolean suspended = false;    
        private JdbcTemplate jdbcTemplate;


        public UserAccount() {}

        public UserAccount(int uid, String name, int age, String dob, String gender, 
                            String address, String email, 
                            String username, String password, int profileId, boolean suspended) {
            this.uid = uid;
            this.name = name;
            this.age = age;
            this.dob = dob;
            this.gender = gender;
            this.address = address;
            this.email = email;
            this.username = username;
            this.password = password;
            this.profileId = profileId;
            this.suspended = suspended;
        }


        public boolean createAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId) {
            java.sql.Date sqlDob = java.sql.Date.valueOf(dob);
            return jdbcTemplate.update(CREATE_USER_ACCOUNT, 
                name,age,sqlDob,gender,address,email,username,password,profileId,
                new Date(System.currentTimeMillis())) > 0;
        }

        public boolean updateUserAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId, int Uid) {
            java.sql.Date sqlDob = java.sql.Date.valueOf(dob);
            return jdbcTemplate.update(UPDATE_USER_ACCOUNT, 
                name,age,sqlDob,gender,address,email,username,password,profileId, Uid) > 0;
        }

        public boolean suspendUserAccount(int uid, boolean suspend) {   
            return jdbcTemplate.update(SET_ACCOUNT_SUSPENSION_STATUS, suspended, uid) > 0;
        }

        public List<UserAccount> searchUserAccount() {
            String sql = "SELECT * FROM USERACCOUNT";
            return jdbcTemplate.query(sql, new UserAccountRowMapper());
        }

        public List<UserAccount> searchUserAccount(int profileId) {
            return jdbcTemplate.query(SEARCH_USER_ACCOUNT_BY_PROFILEID, new UserAccountRowMapper(), profileId);
        }

        public List<UserAccount> searchUserAccount(String keyword) {
            return jdbcTemplate.query(SEARCH_USER_ACCOUNT_BY_USERNAME_OR_NAME_OR_ROLE, new UserAccountRowMapper(), "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
        }

        public UserAccount viewUserAccount(int uid) {
            String sql = "SELECT * FROM USERACCOUNT WHERE uid = ?";
            List<UserAccount> result = jdbcTemplate.query(sql, new UserAccountRowMapper(), uid);
            return result.isEmpty() ? null : result.get(0);
        }

        public UserAccount login(String username, String password, int profileId) {
            List<UserAccount> users = jdbcTemplate.query(LOGIN, new UserAccountRowMapper(), username, password, profileId);
            return users.isEmpty() ? null : users.get(0);
        }

        public String getProfileNameByUid(int uid) {
            return jdbcTemplate.queryForObject(CHECK_USER_ACCOUNT, String.class, uid);
        }

        public List<Integer> searchUserAccountPerProfileCount(List<UserProfile> userProfiles){
            List<Integer> usersPerProfileCount = new ArrayList<>();
    
            for (UserProfile userProfile : userProfiles) {
                List<UserAccount> userAccounts = searchUserAccount(userProfile.getProfileId());
                usersPerProfileCount.add(userAccounts.size());
            }
            return usersPerProfileCount;
        }

        public List<String> searchUserAccountNamesByServiceListings(List<ServiceListing> serviceListings) {
            List<String> cleanersName = new ArrayList<>();
            
            for (ServiceListing listing : serviceListings) {
                UserAccount user = viewUserAccount(listing.getCleanerId());
                String cleanerName = user.getName();
                cleanersName.add(cleanerName);
            }
            return cleanersName;
        }

        public List<UserAccount> searchUserAccountFromShortlist(List<CleanerShortlist> shortlists) {
            List<UserAccount> cleanersShortlist = new ArrayList<>();
            
            for (CleanerShortlist shortlist : shortlists) {
                UserAccount cleaner = viewUserAccount(shortlist.getCleanerId());
                cleanersShortlist.add(cleaner);
            }
            return cleanersShortlist;
        }

        public int getUid() { return uid; }

        public void setUid(int uid) { this.uid = uid; }

        public int getProfileId() { return profileId; }

        public void setProfileId(int profileId) { this.profileId = profileId; }

        public String getUsername() { return username; }

        public void setUsername(String username) { this.username = username; }

        public String getName() { return name; }

        public void setName(String new_name) { this.name = new_name; }
    }

    public static class UserProfile {
        private int profileId;
        private String profileName;
        private String description;
        private boolean suspended;
        private JdbcTemplate jdbcTemplate;

        public UserProfile() {}

        public UserProfile(int id, String name, String desc, boolean suspended) {
            this.profileId = id;
            this.profileName = name;
            this.description = desc;
            this.suspended = suspended;
        }

        public boolean createUserProfile(String name, String description, boolean suspended) {
            int rows_affected = jdbcTemplate.update(CREATE_USER_PROFILE, 
                name, description, suspended);
                
            return rows_affected > 0;
        }

        public boolean updateUserProfile(String name, String description, boolean suspended, int profileId) {
            return jdbcTemplate.update(UPDATE_USER_PROFILE, 
                name, description, suspended, profileId) > 0;
        }

        public boolean suspendUserProfile(int profileId, boolean suspension) {
            List<UserAccount> userAccounts = userAccount.searchUserAccount(profileId);
    
            try {
                for (UserAccount user : userAccounts) {
                    boolean userSuspensionUpdated = userAccount.suspendUserAccount(user.getUid(), suspension);
    
                    if (!userSuspensionUpdated) {
                        throw new RuntimeException("Failed to update user suspension for user ID: " + user.getUid());
                    }
                }
    
                int processProfileSuspension = jdbcTemplate.update(SET_PROFILE_SUSPENSION_STATUS, suspension, profileId);
    
                if (processProfileSuspension <= 0) {
                    throw new RuntimeException("Failed to update profile suspension status for profile ID: " + profileId);
                }
    
                return true;
    
            } catch (Exception e) {
                System.out.println("Exception in suspendUserProfile: " + e.getMessage());
                return false;
            }
        }
        

        public List<UserProfile> searchUserProfile() {
            return jdbcTemplate.query(GET_ALL_PROFILES, new UserProfileRowMapper());
        }

        public List<UserProfile> searchUserProfile(String keyword) {
            return jdbcTemplate.query(SEARCH_PROFILE_BY_NAME, new UserProfileRowMapper(), "%" + keyword + "%");
        }

        public List<String> searchUserProfileNamesForUserAccounts(List<UserAccount> userAccounts) {
            List<String> profileNames = new ArrayList<>();
    
            for (UserAccount user : userAccounts) {
            UserProfile userProfile = viewUserProfile(user.getProfileId());
            String profileName = userProfile.getProfileName();
            profileNames.add(profileName);
            }
            return profileNames;
        }

        public UserProfile viewUserProfile(int profileId) {
            String sql = "SELECT * FROM USERPROFILE WHERE profileId = ?";
            List<UserProfile> profiles = jdbcTemplate.query(sql, new UserProfileRowMapper(), profileId);
            return profiles.isEmpty() ? null : profiles.get(0);
        }
        
        public int getProfileId() { return profileId; }

        public void setProfileId(int id) { this.profileId = id; }

        public String getProfileName() { return profileName; }

        public void setProfileName(String name) { this.profileName = name; }

        public Integer getProfileIdByName(String name) { return 1; }
    }

    public class ServiceListing {

        private int serviceId;
        private String name;
        private int cleanerId;
        private int categoryId;
        private String description;
        private double pricePerHour;
        private String startDate;
        private String endDate;
        private String status;
    
        private int views;
        private int shortlists;
    
        // No-args constructor
        public ServiceListing() {}
    
        // Constructor without Id
        public ServiceListing(String name, int cleanerId, int categoryId, String description, double pricePerHour, String startDate, String endDate,
                             String status, int views, int shortlists) {
            this.name = name;
            this.cleanerId = cleanerId;
            this.categoryId = categoryId;
            this.description = description;
            this.pricePerHour = pricePerHour;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = status;
            this.views = views;
            this.shortlists = shortlists;
        }
    
        public enum Status {
            AVAILABLE, PENDING, UNAVAILABLE
        }
        
        // Getters
        public int getServiceId() { return serviceId; }
        public String getName() { return name; }
        public int getCleanerId() { return cleanerId; }
        public int getCategoryId() {return categoryId; }
        public String getDescription() { return description; }
        public double getPricePerHour() { return pricePerHour; }
        public String getStartDate() { return startDate; }
        public String getEndDate() { return endDate; }
        public String getStatus() { return status; }
    
        public int getViews() { return views; }
        public int getShortlists() { return shortlists; }
    
    
        //Setters
        public void setServiceId(int serviceId) { this.serviceId = serviceId; }
        public void setName(String new_name) { this.name = new_name; }
        public void setCleanerId(int new_cleanerId) { this.cleanerId = new_cleanerId; }
        public void setDescription(String new_description) { this.description = new_description; }
        public void setCategoryId(int new_categoryId) { this.categoryId = new_categoryId; }
        public void setPricePerHour(double new_price) { this.pricePerHour = new_price; }
        public void setStartDate(String new_startDate) { this.startDate = new_startDate; }
        public void setEndDate(String new_endDate) { this.endDate = new_endDate; }
        public void setStatus(String new_status) { this.status = new_status; }
        public void setViews(int new_views) { this.views = new_views; }
        public void setShortlists(int new_shortlists) { this.shortlists = new_shortlists; }
    }

    public class CleanerShortlist {

        private int shortlistId;
        private int homeownerId;
        private int cleanerId;
    
        // No-args constructor
        public CleanerShortlist() {}
    
        public CleanerShortlist(int homeownerId, int cleanerId) {
            this.homeownerId = homeownerId;
            this.cleanerId = cleanerId;
        }
    
        public int getShortlistId(){ return shortlistId; }
        public int getHomeownerId(){ return homeownerId; }
        public int getCleanerId(){ return cleanerId; }
    
        public void setShortlistId(int new_shortlistId){this.shortlistId = new_shortlistId;}
        public void setHomeownerId(int new_homeownerId){this.homeownerId = new_homeownerId;}
        public void setCleanerId(int new_cleanerId){this.cleanerId = new_cleanerId;}
    }

    public static class UserProfileRowMapper implements RowMapper<UserProfile> {
        @Override
        public UserProfile mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
            return new UserProfile(
                rs.getInt("profileId"),
                rs.getString("profileName"),
                rs.getString("description"),
                rs.getBoolean("suspension")
            );
        }
    }    

    // -- RowMapper --
    public static class UserAccountRowMapper implements RowMapper<UserAccount> {
        @Override
        public UserAccount mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
            return new UserAccount(
                rs.getInt("uid"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("dob"),
                rs.getString("gender"),
                rs.getString("address"),
                rs.getString("email"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getInt("profileId"),
                rs.getBoolean("suspended")
            );
        }
    }   

    // -- Controller Stubs --
    public static class CreateUserAccountController {
        private final UserAccount userAccount;
        public CreateUserAccountController(UserAccount userAccount) {
            this.userAccount = userAccount;
        }
        public boolean createAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId){
            return userAccount.createAccount(name, age, dob, gender, address, email, username, password, profileId);
        }
    }

    public static class SearchUserAccountController {
        private final UserAccount userAccount;
        public SearchUserAccountController(UserAccount userAccount) {
            this.userAccount = userAccount;
        }
        // public List<UserAccount> searchUserAccount() { return userAccount.searchUserAccount(); }
        public List<UserAccount> searchUserAccount(int profileId) { return userAccount.searchUserAccount(profileId); }
        public List<UserAccount> searchUserAccount(String keyword) { return userAccount.searchUserAccount(keyword); }
        public List<Integer> searchUserAccountPerProfileCount(List<UserProfile> profiles) { return userAccount.searchUserAccountPerProfileCount(profiles); }
        public List<String> searchUserAccountNamesByServiceListings(List<ServiceListing> listings) { return userAccount.searchUserAccountNamesByServiceListings(listings); }
        public List<UserAccount> searchUserAccountFromShortlist(List<CleanerShortlist> list) { return userAccount.searchUserAccountFromShortlist(list); }
    }

    public static class SuspendUserAccountController {
        private final UserAccount userAccount;
        public SuspendUserAccountController(UserAccount userAccount) {
            this.userAccount = userAccount;
        }
        public boolean suspendUserAccount(int uid, boolean suspended) { return userAccount.suspendUserAccount(uid, suspended); }
    }

    public static class UpdateUserAccountController {
        private final UserAccount userAccount;
        public UpdateUserAccountController(UserAccount userAccount) {
            this.userAccount = userAccount;
        }
        public boolean updateUserAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId, int UId) {
            return userAccount.updateUserAccount(name, age, dob, gender, address, email, username, password, profileId, UId);
        }
    }

    public static class ViewUserAccountController {
        private final UserAccount userAccount;
        public ViewUserAccountController(UserAccount userAccount) {
            this.userAccount = userAccount;
        }
        public UserAccount viewUserAccount(int uid) { 
            return userAccount.viewUserAccount(uid); }
    }

    public static class CreateUserProfileController {
        private final UserProfile userProfile;
        public CreateUserProfileController(UserProfile userProfile) {
            this.userProfile = userProfile;
        }
        public boolean createUserProfile(String name, String desc, boolean suspended) {
            return userProfile.createUserProfile(name, desc, suspended);
        }
    }
    

    public static class SearchUserProfileController {
        private final UserProfile userProfile;
        public SearchUserProfileController(UserProfile userProfile) {
            this.userProfile = userProfile;
        }
        public List<UserProfile> searchUserProfile() { return userProfile.searchUserProfile(); }
        public List<UserProfile> searchUserProfile(String keyword) { return userProfile.searchUserProfile(keyword); }
        public List<String> searchUserProfileNamesForUserAccounts(List<UserAccount> accounts) {
            return userProfile.searchUserProfileNamesForUserAccounts(accounts);
        }
    }

    public static class SuspendUserProfileController {
        private final UserProfile userProfile;
        public SuspendUserProfileController(UserProfile userProfile) {
            this.userProfile = userProfile;
        }
        public boolean suspendUserProfile(int profileId, boolean suspended) { return userProfile.suspendUserProfile(profileId, suspended); }
    }

    public static class UpdateUserProfileController {
        private final UserProfile userProfile;
        public  UpdateUserProfileController(UserProfile userProfile) {
            this.userProfile = userProfile;
        }
        public boolean updateUserProfile(String name, String desc, boolean suspended, int id) {
            return userProfile.updateUserProfile(name, desc, suspended, id);
        }
    }

    public static class ViewUserProfileController {
        private final UserProfile userProfile;
        public ViewUserProfileController(UserProfile userProfile) {
            this.userProfile = userProfile;
        }
        public UserProfile viewUserProfile(int id) { return userProfile.viewUserProfile(id); }
    }

    // === Tests (from your original file) ===
    // You can now paste all the tests from your original code here — they will compile correctly now.
    @Test
    public void testCreateAccount_Success() {
        String name = "Ben Joe lol";
        int age = 30;
        String dob = "1994-01-01";
        String gender = "Male";
        String address = "1111 Street";
        String email = "BenJoe@example.com";
        String username = "BenJoe";
        String password = "password123";
        int profileId = 1;

        when(userAccount.createAccount(name, age, dob, gender, address, email, username, password, profileId)).thenReturn(true);

        boolean result = createController.createAccount(name, age, dob, gender, address, email, username, password, profileId);

        assertTrue(result);
        verify(userAccount).createAccount(name, age, dob, gender, address, email, username, password, profileId);
    }

    // @Test
    // public void testSearchUserAccount_All() {
    //     List<UserAccount> mockList = Arrays.asList(new UserAccount(), new UserAccount());
    //     when(userAccount.searchUserAccount()).thenReturn(mockList);

    //     List<UserAccount> result = searchController.searchUserAccount();

    //     assertEquals(2, result.size());
    //     verify(userAccount).searchUserAccount();
    // }

    @Test
    public void testSearchUserAccount_ByProfileId() {
        int profileId = 2;
        List<UserAccount> mockList = Arrays.asList(new UserAccount(), new UserAccount());
        when(userAccount.searchUserAccount(profileId)).thenReturn(mockList);

        List<UserAccount> result = searchController.searchUserAccount(profileId);

        assertEquals(2, result.size());
        verify(userAccount).searchUserAccount(profileId);
    }

    @Test
    public void testSearchUserAccount_ByKeyword() {
        String keyword = "john";
        List<UserAccount> mockList = Arrays.asList(new UserAccount());
        when(userAccount.searchUserAccount(keyword)).thenReturn(mockList);

        List<UserAccount> result = searchController.searchUserAccount(keyword);

        assertEquals(1, result.size());
        verify(userAccount).searchUserAccount(keyword);
    }

    @Test
    public void testSearchUserAccountPerProfileCount() {
        List<UserProfile> profiles = Arrays.asList(
            new UserProfile(1, "A", "desc", false),
            new UserProfile(2, "B", "desc", false)
        );
    
        when(userAccount.searchUserAccount(1)).thenReturn(Arrays.asList(new UserAccount(), new UserAccount(), new UserAccount(), new UserAccount(), new UserAccount())); // 5
        when(userAccount.searchUserAccount(2)).thenReturn(Arrays.asList(new UserAccount(), new UserAccount(), new UserAccount())); // 3
    
        List<Integer> result = searchController.searchUserAccountPerProfileCount(profiles);
    
        assertEquals(Arrays.asList(5, 3), result);
    }
    

    @Test
    public void testSearchUserAccountNamesByServiceListings() {
        ServiceListing listing1 = mock(ServiceListing.class);
        ServiceListing listing2 = mock(ServiceListing.class);
    
        when(listing1.getCleanerId()).thenReturn(1);
        when(listing2.getCleanerId()).thenReturn(2);
    
        List<ServiceListing> listings = Arrays.asList(listing1, listing2);
    
        UserAccount user1 = mock(UserAccount.class);
        UserAccount user2 = mock(UserAccount.class);
    
        when(userAccount.viewUserAccount(1)).thenReturn(user1);
        when(userAccount.viewUserAccount(2)).thenReturn(user2);
    
        when(user1.getName()).thenReturn("Alice");
        when(user2.getName()).thenReturn("Bob");
    
        List<String> expectedNames = Arrays.asList("Alice", "Bob");
    
        List<String> result = searchController.searchUserAccountNamesByServiceListings(listings);
    
        assertEquals(expectedNames, result);
        verify(userAccount).searchUserAccountNamesByServiceListings(listings);  // Optional: if your method delegates
    }

    @Test
    public void testSearchUserAccountFromShortlist() {
        CleanerShortlist shortlist1 = mock(CleanerShortlist.class);
        CleanerShortlist shortlist2 = mock(CleanerShortlist.class);
    
        when(shortlist1.getCleanerId()).thenReturn(1);
        when(shortlist2.getCleanerId()).thenReturn(2);
    
        List<CleanerShortlist> listings = Arrays.asList(shortlist1, shortlist2);
    
        UserAccount user1 = mock(UserAccount.class);
        UserAccount user2 = mock(UserAccount.class);
    
        when(userAccount.viewUserAccount(1)).thenReturn(user1);
        when(userAccount.viewUserAccount(2)).thenReturn(user2);
    
        when(user1.getName()).thenReturn("Alice");
        when(user2.getName()).thenReturn("Bob");
    
        List<UserAccount> result = searchController.searchUserAccountFromShortlist(listings);
    
        // Verify the result contains the expected user mocks
        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    
        // Optional: check the names to be sure
        List<String> resultNames = result.stream()
                                        .map(UserAccount::getName)
                                        .collect(Collectors.toList());
        assertEquals(Arrays.asList("Alice", "Bob"), resultNames);
    
        verify(userAccount).searchUserAccountFromShortlist(listings);
    }

    @Test
    public void testSuspendUserAccount_Success() {
        int uid = 42;
        boolean suspended = true;

        when(userAccount.suspendUserAccount(uid, suspended)).thenReturn(true);

        boolean result = suspendController.suspendUserAccount(uid, suspended);

        assertTrue(result);
        verify(userAccount).suspendUserAccount(uid, suspended);
    }

    @Test
    public void testUpdateUserAccount_Success() {
        String name = "Updated Name";
        int age = 35;
        String dob = "2000-01-01";
        String gender = "Other";
        String address = "456 Avenue";
        String email = "updated@example.com";
        String username = "updatedUser";
        String password = "newPassword";
        int profileId = 2;
        int uid = 111;

        when(userAccount.updateUserAccount(name, age, dob, gender, address, email, username, password, profileId, uid))
            .thenReturn(true);

        boolean result = updateController.updateUserAccount(name, age, dob, gender, address, email, username, password, profileId, uid);

        assertTrue(result);
        verify(userAccount).updateUserAccount(name, age, dob, gender, address, email, username, password, profileId, uid);
    }

    @Test
    public void testViewUserAccount_Success() {
        int uid = 101;
        UserAccount mockUser = new UserAccount();
        mockUser.setUid(uid);
        mockUser.setUsername("viewuser");

        when(userAccount.viewUserAccount(uid)).thenReturn(mockUser);

        UserAccount result = viewController.viewUserAccount(uid);

        assertNotNull(result);
        assertEquals(uid, result.getUid());
        assertEquals("viewuser", result.getUsername());
        verify(userAccount).viewUserAccount(uid);
    }

    @Test
    public void testCreateUserProfile_Success() {
        String name = "Admin";
        String description = "Admin profile";
        boolean suspended = false;

        when(userProfile.createUserProfile(name, description, suspended)).thenReturn(true);

        boolean result = createUserProfileController.createUserProfile(name, description, suspended);

        assertTrue(result);
        verify(userProfile).createUserProfile(name, description, suspended);
    }
    @Test
    public void testSearchUserProfile_All() {
        List<UserProfile> mockProfiles = Arrays.asList(new UserProfile(), new UserProfile());
        when(userProfile.searchUserProfile()).thenReturn(mockProfiles);

        List<UserProfile> result = searchUserProfileController.searchUserProfile();

        assertEquals(2, result.size());
        verify(userProfile).searchUserProfile();
    }

    @Test
    public void testSearchUserProfile_ByKeyword() {
        String keyword = "Admin";
        List<UserProfile> mockProfiles = Arrays.asList(new UserProfile());
        when(userProfile.searchUserProfile(keyword)).thenReturn(mockProfiles);

        List<UserProfile> result = searchUserProfileController.searchUserProfile(keyword);

        assertEquals(1, result.size());
        verify(userProfile).searchUserProfile(keyword);
    }

    @Test
    public void testSearchUserProfileNamesForUserAccounts() {
        UserAccount user1 = new UserAccount();
        user1.setProfileId(1);
        
        UserAccount user2 = new UserAccount();
        user2.setProfileId(2);
        
        UserProfile profile1 = new UserProfile(1, "Admin", "desc", false);
        UserProfile profile2 = new UserProfile(2, "User", "desc", false);
        
        when(userProfile.viewUserProfile(1)).thenReturn(profile1);
        when(userProfile.viewUserProfile(2)).thenReturn(profile2);
        
        List<UserAccount> accounts = Arrays.asList(user1, user2);
        
        List<String> result = searchUserProfileController.searchUserProfileNamesForUserAccounts(accounts);
        
        assertEquals(Arrays.asList("Admin", "User"), result);
    }
    
    @Test
    public void testSuspendUserProfile_Success() {
        int profileId = 1;
        boolean suspension = true;

        // Mocked UserAccount
        UserAccount mockUser = new UserAccount();
        mockUser.setUid(1);
        mockUser.setProfileId(1);

        // Mock behavior
        doReturn(List.of(mockUser)).when(userAccount).searchUserAccount(profileId);
        doReturn(true).when(userAccount).suspendUserAccount(1, suspension);
        doReturn(1)
        .when(jdbcTemplate)
        .update(eq("UPDATE USERPROFILE SET suspension = ? WHERE profileId = ?"), eq(suspension), eq(profileId));
    
        
        // Method under test
        boolean result = userProfile.suspendUserProfile(profileId, suspension);

        // Assertion
        assertTrue(result);
    }

    @Test
    public void testUpdateUserProfile_Success() {
        String name = "Updated Name";
        String description = "Updated Description";
        boolean suspended = false;
        int profileId = 11;

        when(userProfile.updateUserProfile(name, description, suspended, profileId)).thenReturn(true);

        boolean result = updateUserProfileController.updateUserProfile(name, description, suspended, profileId);

        assertTrue(result);
        verify(userProfile).updateUserProfile(name, description, suspended, profileId);
    }
    @Test
    public void testViewUserProfile_Success() {
        int profileId = 11;
        UserProfile mockProfile = new UserProfile();
        mockProfile.setProfileId(profileId);
        mockProfile.setProfileName("Test Profile");

        when(userProfile.viewUserProfile(profileId)).thenReturn(mockProfile);

        UserProfile result = viewUserProfileController.viewUserProfile(profileId);

        assertNotNull(result);
        assertEquals(profileId, result.getProfileId());
        assertEquals("Test Profile", result.getProfileName());
        verify(userProfile).viewUserProfile(profileId);
    }

    @Test
    public void testLogin_Success() {
        UserAccount mockUser = new UserAccount();
        mockUser.setUid(1);
        List<UserAccount> mockList = List.of(mockUser);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("user1"), eq("pass1"), eq(1))).thenReturn(mockList);

        UserAccount result = userAccount.login("user1", "pass1", 1);

        assertNotNull(result);
        assertEquals(1, result.getUid());
        verify(jdbcTemplate).query(anyString(), any(RowMapper.class), eq("user1"), eq("pass1"), eq(1));
    }

    @Test
    public void testLogin_NoUser() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyString(), anyString(), anyInt())).thenReturn(List.of());

        UserAccount result = userAccount.login("nouser", "nopass", 2);

        assertNull(result);
    }

    @Test
    public void testCreateAccount_Success_Entity() {
        // Arrange - mock dependencies
        String name = "Alice";
        int age = 25;
        String dob = "2000-01-01";
        String gender = "Female";
        String address = "123 Street";
        String email = "alice@example.com";
        String username = "alice123";
        String password = "secure123";
        int profileId = 3;
    
        // Mock jdbcTemplate
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(1); // Simulate successful insert
    
        // Act
        boolean created = userAccount.createAccount(
            name, age, dob, gender, address, email, username, password, profileId
        );
    
        // Assert
        assertTrue(created);
    }
    
    @Test
    public void testCreateAccount_Failure() {
        when(jdbcTemplate.update(anyString(), anyString(), anyInt(), any(Date.class), anyString(), anyString(),
            anyString(), anyString(), anyInt(), any(Date.class))).thenReturn(0);

        boolean created = userAccount.createAccount("Name", 25, "1998-01-01", "Male", "Address", "email@example.com",
                "username", "password", 2);

        assertFalse(created);
    }

    @Test
    public void testViewUserAccount_Found() {
        UserAccount mockUser = new UserAccount();
        mockUser.setUid(10);
        List<UserAccount> mockList = List.of(mockUser);
    
        // FIX: Use correct generic matcher
        doReturn(mockList).when(jdbcTemplate).query(
            anyString(),
            ArgumentMatchers.<RowMapper<UserAccount>>any(),
            eq(10)
        );
        when(userAccount.viewUserAccount(10)).thenReturn(mockUser);
        UserAccount result = userAccount.viewUserAccount(10);
    
        assertNotNull(result);
        assertEquals(10, result.getUid());
    }

    @Test
    public void testViewUserAccount_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt())).thenReturn(List.of());

        UserAccount result = userAccount.viewUserAccount(999);

        assertNull(result);
    }

    @Test
    public void testUpdateUserAccountEntity_Success() {
        when(jdbcTemplate.update(anyString(),anyString(), anyInt(), any(java.sql.Date.class), anyString(), anyString(), anyString(),
            anyString(), anyString(), anyInt(), anyInt())).thenReturn(1);

        boolean updated = userAccount.updateUserAccount("NewName", 30, "1993-05-05", "Female", "New Address",
                "newemail@example.com", "newusername", "newpass", 3, 20);

        assertTrue(updated);
    }

    @Test
    public void testUpdateUserAccount_Failure() {
        when(jdbcTemplate.update(anyString(), anyString(), anyInt(), anyString(), anyString(), anyString(),
            anyString(), anyString(), anyInt(), anyInt())).thenReturn(0);

        boolean updated = userAccount.updateUserAccount("NewName", 30, "1993-05-05", "Female", "New Address",
                "newemail@example.com", "newusername", "newpass", 3, 20);

        assertFalse(updated);
    }

    @Test
    public void testSuspendUserAccountEntity_Success() {
        when(jdbcTemplate.update(anyString(), anyBoolean(), anyInt())).thenReturn(1);

        boolean suspended = userAccount.suspendUserAccount(5, true);

        assertTrue(suspended);
    }

    @Test
    public void testSuspendUserAccount_Failure() {
        when(jdbcTemplate.update(anyString(), anyBoolean(), anyInt())).thenReturn(0);

        boolean suspended = userAccount.suspendUserAccount(5, true);

        assertFalse(suspended);
    }

    @Test
    public void testSearchUserAccountEntity_ByKeyword() {
        List<UserAccount> mockList = List.of(new UserAccount(), new UserAccount());

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("%john%"), eq("%john%"), eq("%john%"))).thenReturn(mockList);

        List<UserAccount> result = userAccount.searchUserAccount("john");

        assertEquals(2, result.size());
    }

    @Test
    public void testSearchUserAccountEntity_ByProfileId() {
        List<UserAccount> mockList = List.of(new UserAccount());

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1))).thenReturn(mockList);

        List<UserAccount> result = userAccount.searchUserAccount(1);

        assertEquals(1, result.size());
    }

    @Test
    public void testSearchUserAccountEntity_All() {
        List<UserAccount> mockList = List.of(new UserAccount(), new UserAccount(), new UserAccount());

        when(jdbcTemplate.query(anyString(), ArgumentMatchers.<RowMapper<UserAccount>>any())).thenReturn(mockList);
        

        List<UserAccount> result = userAccount.searchUserAccount();

        System.out.println(result);

        assertEquals(3, result.size());
    }
    

    @Test
    public void testGetProfileNameByUid() {
        when(jdbcTemplate.queryForObject(anyString(), eq(String.class), eq(10))).thenReturn("Admin");

        String profileName = userAccount.getProfileNameByUid(10);

        assertEquals("Admin", profileName);
    }
//userProfile Entity Tests
    @Test
    public void testCreateUserProfileEntity_Success() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyBoolean())).thenReturn(1);

        boolean result = userProfile.createUserProfile("Admin", "Admin profile", false);

        assertTrue(result);
        verify(jdbcTemplate).update(anyString(), eq("Admin"), eq("Admin profile"), eq(false));
    }

    @Test
    public void testCreateUserProfile_Failure() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyBoolean())).thenReturn(0);

        boolean result = userProfile.createUserProfile("Admin", "Admin profile", false);

        assertFalse(result);
    }

    @Test
    public void testViewUserProfile_Found() {
        UserProfile profile = new UserProfile(1, "Profile", "desc", false);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1))).thenReturn(List.of(profile));

        UserProfile result = userProfile.viewUserProfile(1);

        assertNotNull(result);
        assertEquals(1, result.getProfileId());
        assertEquals("Profile", result.getProfileName());
    }

    @Test
    public void testViewUserProfile_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt())).thenReturn(List.of());

        UserProfile result = userProfile.viewUserProfile(999);

        assertNull(result);
    }

    @Test
    public void testGetProfileIdByName_Found() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("Admin")))
            .thenReturn(1);
    
        Integer id = userProfile.getProfileIdByName("Admin");
    
        assertNotNull(id);
        assertEquals(1, id);
    }

    @Test
    public void testGetProfileIdByName_NotFound() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), eq("Admin")))
            .thenReturn(1);
    
        Integer id = userProfile.getProfileIdByName("Non-existent");
    
        assertNotNull(id);
        assertEquals(1, id);
    }


    @Test
    public void testUpdateUserProfileEntity_Success() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyBoolean(), anyInt())).thenReturn(1);

        boolean result = userProfile.updateUserProfile("NewName", "NewDesc", true, 5);

        assertTrue(result);
    }

    @Test
    public void testUpdateUserProfile_Failure() {
        when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyBoolean(), anyInt())).thenReturn(0);

        boolean result = userProfile.updateUserProfile("NewName", "NewDesc", true, 5);

        assertFalse(result);
    }

    @Test
    public void testSuspendUserProfileEntity_Success() {
        UserAccount user1 = mock(UserAccount.class);
        UserAccount user2 = mock(UserAccount.class);

        when(userAccount.searchUserAccount(10)).thenReturn(List.of(user1, user2));
        when(user1.getUid()).thenReturn(100);
        when(user2.getUid()).thenReturn(101);

        when(userAccount.suspendUserAccount(100, true)).thenReturn(true);
        when(userAccount.suspendUserAccount(101, true)).thenReturn(true);

        when(jdbcTemplate.update(anyString(), eq(true), eq(10))).thenReturn(1);

        boolean result = userProfile.suspendUserProfile(10, true);

        assertTrue(result);
        verify(userAccount).suspendUserAccount(100, true);
        verify(userAccount).suspendUserAccount(101, true);
        verify(jdbcTemplate).update(anyString(), eq(true), eq(10));
    }

    @Test
    public void testSuspendUserProfile_FailureDueToUserSuspendFail() {
        UserAccount user1 = mock(UserAccount.class);

        when(userAccount.searchUserAccount(10)).thenReturn(List.of(user1));
        when(user1.getUid()).thenReturn(100);

        when(userAccount.suspendUserAccount(100, true)).thenReturn(false);  // fail suspend user

        boolean result = userProfile.suspendUserProfile(10, true);

        assertFalse(result);
    }

    @Test
    public void testSuspendUserProfile_FailureDueToProfileSuspendFail() {
        UserAccount user1 = mock(UserAccount.class);

        when(userAccount.searchUserAccount(10)).thenReturn(List.of(user1));
        when(user1.getUid()).thenReturn(100);

        when(userAccount.suspendUserAccount(100, true)).thenReturn(true);
        when(jdbcTemplate.update(anyString(), eq(true), eq(10))).thenReturn(0);  // fail profile suspend

        boolean result = userProfile.suspendUserProfile(10, true);

        assertFalse(result);
    }

    @Test
    public void testSearchUserProfileEntity_ByKeyword() {
        List<UserProfile> profiles = List.of(new UserProfile(1, "Admin", "desc", false));
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("%admin%"))).thenReturn(profiles);

        List<UserProfile> result = userProfile.searchUserProfile("admin");

        assertEquals(1, result.size());
        assertEquals("Admin", result.get(0).getProfileName());
    }

    @Test
    public void testSearchUserProfileEntity_All() {
        List<UserProfile> profiles = List.of(new UserProfile(1, "Admin", "desc", false), new UserProfile(2, "User", "desc", true));
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(profiles);

        List<UserProfile> result = userProfile.searchUserProfile();

        assertEquals(2, result.size());
    }
    
    @Test
    public void testSearchUserProfileNamesForUserAccountsEntity() {
        UserAccount user1 = mock(UserAccount.class);
        UserAccount user2 = mock(UserAccount.class);
    
        when(user1.getProfileId()).thenReturn(1);
        when(user2.getProfileId()).thenReturn(2);
    
        UserProfile profile1 = new UserProfile(1, "Admin", "desc", false);
        UserProfile profile2 = new UserProfile(2, "User", "desc", false);
    
        // Create a real UserProfile instance (assuming a no-arg constructor)
        UserProfile realUserProfile = new UserProfile();
    
        // Spy the real instance to override specific method(s)
        UserProfile spyUserProfile = spy(realUserProfile);
    
        // Stub viewUserProfile method to return your profiles
        doReturn(profile1).when(spyUserProfile).viewUserProfile(1);
        doReturn(profile2).when(spyUserProfile).viewUserProfile(2);
    
        List<UserAccount> users = Arrays.asList(user1, user2);
    
        // This will run the real searchUserProfileNamesForUserAccounts,
        // but with stubbed viewUserProfile
        List<String> names = spyUserProfile.searchUserProfileNamesForUserAccounts(users);
    
        assertEquals(List.of("Admin", "User"), names);
    }
    
}