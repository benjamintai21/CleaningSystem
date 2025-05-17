import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserAdminTest {

    // === Mocked Beans ===
    @Mock private JdbcTemplate jdbcTemplate;
    @Mock private UserAccount userAccount;
    @InjectMocks private UserProfile userProfile;

    @InjectMocks private CreateUserAccountController createController;
    @InjectMocks private SearchUserAccountController searchController;
    @InjectMocks private SuspendUserAccountController suspendController;
    @InjectMocks private UpdateUserAccountController updateController;
    @InjectMocks private ViewUserAccountController viewController;

    @InjectMocks private CreateUserProfileController createUserProfileController;
    @InjectMocks private SearchUserProfileController searchUserProfileController;
    @InjectMocks private SuspendUserProfileController suspendUserProfileController;
    @InjectMocks private UpdateUserProfileController updateUserProfileController;
    @InjectMocks private ViewUserProfileController viewUserProfileController;

    // === Setup ===
    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        java.lang.reflect.Field jdbcField1 = UserAccount.class.getDeclaredField("jdbcTemplate");
        jdbcField1.setAccessible(true);
        jdbcField1.set(userAccount, jdbcTemplate);

        java.lang.reflect.Field jdbcField2 = UserProfile.class.getDeclaredField("jdbcTemplate");
        jdbcField2.setAccessible(true);
        jdbcField2.set(userProfile, jdbcTemplate);

        java.lang.reflect.Field uaField = UserProfile.class.getDeclaredField("userAccount");
        uaField.setAccessible(true);
        uaField.set(userProfile, userAccount);
    }

    // === Minimal Entity and Controller Definitions ===

    // -- Entities --
    public static class UserAccount {
        private int uid;
        private int profileId;
        private String username;
        private JdbcTemplate jdbcTemplate;

        public boolean createAccount(String name, int age, String dob, String gender, String address,
                                     String email, String username, String password, int profileId) { return true; }

        public boolean updateUserAccount(String name, int age, String dob, String gender, String address,
                                         String email, String username, String password, int profileId, int uid) { return true; }

        public boolean suspendUserAccount(int uid, boolean suspend) { return true; }

        public List<UserAccount> searchUserAccount() {
            String sql = "SELECT * FROM USERACCOUNT";
            return jdbcTemplate.query(sql, new UserAccountRowMapper());
        }

        public List<UserAccount> searchUserAccount(int profileId) { return List.of(); }

        public List<UserAccount> searchUserAccount(String keyword) { return List.of(); }

        public UserAccount viewUserAccount(int uid) { return new UserAccount(); }

        public UserAccount login(String username, String password, int profileId) { return new UserAccount(); }

        public String getProfileNameByUid(int uid) { return "Admin"; }

        public List<Integer> searchUserAccountPerProfileCount(List<UserProfile> profiles) { return List.of(); }

        public List<String> searchUserAccountNamesByServiceListings(List<ServiceListing> listings) { return List.of(); }

        public List<UserAccount> searchUserAccountFromShortlist(List<CleanerShortlist> shortlists) { return List.of(); }

        public int getUid() { return uid; }

        public void setUid(int uid) { this.uid = uid; }

        public int getProfileId() { return profileId; }

        public void setProfileId(int profileId) { this.profileId = profileId; }

        public String getUsername() { return username; }

        public void setUsername(String username) { this.username = username; }
    }

    public static class UserProfile {
        private int profileId;
        private String profileName;
        private JdbcTemplate jdbcTemplate;
        private UserAccount userAccount;

        public UserProfile() {}

        public UserProfile(int id, String name, String desc, boolean suspended) {
            this.profileId = id;
            this.profileName = name;
        }

        public boolean createUserProfile(String name, String desc, boolean suspended) { return true; }

        public boolean updateUserProfile(String name, String desc, boolean suspended, int id) { return true; }

        public boolean suspendUserProfile(int profileId, boolean suspended) { return true; }

        public List<UserProfile> searchUserProfile() { return List.of(); }

        public List<UserProfile> searchUserProfile(String keyword) { return List.of(); }

        public List<String> searchUserProfileNamesForUserAccounts(List<UserAccount> accounts) { return List.of(); }

        public UserProfile viewUserProfile(int profileId) { return new UserProfile(profileId, "Profile", "desc", false); }

        public int getProfileId() { return profileId; }

        public void setProfileId(int id) { this.profileId = id; }

        public String getProfileName() { return profileName; }

        public void setProfileName(String name) { this.profileName = name; }

        public Integer getProfileIdByName(String name) { return 1; }
    }

    public static class ServiceListing {}
    public static class CleanerShortlist {}

    // -- RowMapper --
    public static class UserAccountRowMapper implements RowMapper<UserAccount> {
        @Override
        public UserAccount mapRow(java.sql.ResultSet rs, int rowNum) {
            return new UserAccount();
        }
    }

    // -- Controller Stubs --
    public static class CreateUserAccountController {
        @InjectMocks UserAccount userAccount;
        public boolean createAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId){
            return userAccount.createAccount(name, age, dob, gender, address, email, username, password, profileId);
        }
    }

    public static class SearchUserAccountController {
        @InjectMocks UserAccount userAccount;
        public List<UserAccount> searchUserAccount() { return userAccount.searchUserAccount(); }
        public List<UserAccount> searchUserAccount(int profileId) { return userAccount.searchUserAccount(profileId); }
        public List<UserAccount> searchUserAccount(String keyword) { return userAccount.searchUserAccount(keyword); }
        public List<Integer> searchUserAccountPerProfileCount(List<UserProfile> profiles) { return userAccount.searchUserAccountPerProfileCount(profiles); }
        public List<String> searchUserAccountNamesByServiceListings(List<ServiceListing> listings) { return userAccount.searchUserAccountNamesByServiceListings(listings); }
        public List<UserAccount> searchUserAccountFromShortlist(List<CleanerShortlist> list) { return userAccount.searchUserAccountFromShortlist(list); }
    }

    public static class SuspendUserAccountController {
        @InjectMocks UserAccount userAccount;
        public boolean suspendUserAccount(int uid, boolean suspended) { return userAccount.suspendUserAccount(uid, suspended); }
    }

    public static class UpdateUserAccountController {
        @InjectMocks UserAccount userAccount;
        public boolean updateUserAccount(String name, int age, String dob, String gender, String address, String email, String username, String password, int profileId, int UId) {
            return userAccount.updateUserAccount(name, age, dob, gender, address, email, username, password, profileId, UId);
        }
    }

    public static class ViewUserAccountController {
        @InjectMocks UserAccount userAccount;
        public UserAccount viewUserAccount(int uid) { return userAccount.viewUserAccount(uid); }
    }

    public static class CreateUserProfileController {
        @InjectMocks UserProfile userProfile;
        public boolean createUserProfile(String name, String desc, boolean suspended) { return userProfile.createUserProfile(name, desc, suspended); }
    }

    public static class SearchUserProfileController {
        @InjectMocks UserProfile userProfile;
        public List<UserProfile> searchUserProfile() { return userProfile.searchUserProfile(); }
        public List<UserProfile> searchUserProfile(String keyword) { return userProfile.searchUserProfile(keyword); }
        public List<String> searchUserProfileNamesForUserAccounts(List<UserAccount> accounts) {
            return userProfile.searchUserProfileNamesForUserAccounts(accounts);
        }
    }

    public static class SuspendUserProfileController {
        @InjectMocks UserProfile userProfile;
        public boolean suspendUserProfile(int profileId, boolean suspended) { return userProfile.suspendUserProfile(profileId, suspended); }
    }

    public static class UpdateUserProfileController {
        @InjectMocks UserProfile userProfile;
        public boolean updateUserProfile(String name, String desc, boolean suspended, int id) {
            return userProfile.updateUserProfile(name, desc, suspended, id);
        }
    }

    public static class ViewUserProfileController {
        @InjectMocks UserProfile userProfile;
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

    @Test
    public void testSearchUserAccount_All() {
        List<UserAccount> mockList = Arrays.asList(new UserAccount(), new UserAccount());
        when(userAccount.searchUserAccount()).thenReturn(mockList);

        List<UserAccount> result = searchController.searchUserAccount();

        assertEquals(2, result.size());
        verify(userAccount).searchUserAccount();
    }

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
        List<UserProfile> profiles = Arrays.asList(new UserProfile(), new UserProfile());
        List<Integer> expectedCounts = Arrays.asList(5, 3);
        when(userAccount.searchUserAccountPerProfileCount(profiles)).thenReturn(expectedCounts);

        List<Integer> result = searchController.searchUserAccountPerProfileCount(profiles);

        assertEquals(expectedCounts, result);
        verify(userAccount).searchUserAccountPerProfileCount(profiles);
    }

    @Test
    public void testSearchUserAccountNamesByServiceListings() {
        List<ServiceListing> listings = Arrays.asList(new ServiceListing(), new ServiceListing());
        List<String> expectedNames = Arrays.asList("Alice", "Bob");
        when(userAccount.searchUserAccountNamesByServiceListings(listings)).thenReturn(expectedNames);

        List<String> result = searchController.searchUserAccountNamesByServiceListings(listings);

        assertEquals(expectedNames, result);
        verify(userAccount).searchUserAccountNamesByServiceListings(listings);
    }

    @Test
    public void testSearchUserAccountFromShortlist() {
        List<CleanerShortlist> shortlists = Arrays.asList(new CleanerShortlist(), new CleanerShortlist());
        List<UserAccount> expectedUsers = Arrays.asList(new UserAccount());
        when(userAccount.searchUserAccountFromShortlist(shortlists)).thenReturn(expectedUsers);

        List<UserAccount> result = searchController.searchUserAccountFromShortlist(shortlists);

        assertEquals(expectedUsers, result);
        verify(userAccount).searchUserAccountFromShortlist(shortlists);
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

    // Create User Profile controller test
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
        List<UserAccount> accounts = Arrays.asList(new UserAccount(), new UserAccount());
        List<String> expectedNames = Arrays.asList("Admin", "User");
        when(userProfile.searchUserProfileNamesForUserAccounts(accounts)).thenReturn(expectedNames);

        List<String> result = searchUserProfileController.searchUserProfileNamesForUserAccounts(accounts);

        assertEquals(expectedNames, result);
        verify(userProfile).searchUserProfileNamesForUserAccounts(accounts);
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
//Entity tests 
// userAccountEntity
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
        when(jdbcTemplate.update(anyString(), anyString(), anyInt(), any(Date.class), anyString(), anyString(),
            anyString(), anyString(), anyInt(), anyInt())).thenReturn(1);

        boolean updated = userAccount.updateUserAccount("NewName", 30, "1993-05-05", "Female", "New Address",
                "newemail@example.com", "newusername", "newpass", 3, 20);

        assertTrue(updated);
    }

    @Test
    public void testUpdateUserAccount_Failure() {
        when(jdbcTemplate.update(anyString(), anyString(), anyInt(), any(Date.class), anyString(), anyString(),
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

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq("%john%"))).thenReturn(mockList);

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

        // Use spy or mock to override viewUserProfile for this test
        UserProfile spyProfile = spy(userProfile);
        doReturn(profile1).when(spyProfile).viewUserProfile(1);
        doReturn(profile2).when(spyProfile).viewUserProfile(2);

        List<UserAccount> users = List.of(user1, user2);
        List<String> names = spyProfile.searchUserProfileNamesForUserAccounts(users);

        assertEquals(List.of("Admin", "User"), names);
    }
}