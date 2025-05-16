import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import com.cleaningsystem.controller.UserAdmin.UserAccount.*;
import com.cleaningsystem.controller.UserAdmin.UserProfile.*;
import com.cleaningsystem.entity.CleanerShortlist;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.UserAccount;
import com.cleaningsystem.entity.UserProfile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserAdminTest {

    // Mocked Entity Beans
    @Mock
    private UserAccount userAccount;

    @Mock
    private UserProfile userProfile;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private UserAccount realUserAccount;

    // Inject Controllers
    @InjectMocks
    private CreateUserAccountController createController;

    @InjectMocks
    private SearchUserAccountController searchController;

    @InjectMocks
    private SuspendUserAccountController suspendController;

    @InjectMocks
    private UpdateUserAccountController updateController;

    @InjectMocks
    private ViewUserAccountController viewController;

    @InjectMocks
    private CreateUserProfileController createUserProfileController;

    @InjectMocks
    private SearchUserProfileController searchUserProfileController;

    @InjectMocks
    private SuspendUserProfileController suspendUserProfileController;

    @InjectMocks
    private UpdateUserProfileController updateUserProfileController;

    @InjectMocks
    private ViewUserProfileController viewUserProfileController;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Setup realUserAccount with jdbcTemplate mock injected by reflection
        realUserAccount = new UserAccount();
        java.lang.reflect.Field jdbcField1 = UserAccount.class.getDeclaredField("jdbcTemplate");
        jdbcField1.setAccessible(true);
        jdbcField1.set(realUserAccount, jdbcTemplate);

        // Injecting mock JdbcTemplate into UserProfile using reflection
        userProfile = new UserProfile();
        java.lang.reflect.Field jdbcField2 = UserProfile.class.getDeclaredField("jdbcTemplate");
        jdbcField2.setAccessible(true);
        jdbcField2.set(userProfile, jdbcTemplate);

        // Injecting mock UserAccount into UserProfile
        java.lang.reflect.Field uaField = UserProfile.class.getDeclaredField("userAccount");
        uaField.setAccessible(true);
        uaField.set(userProfile, userAccount);
    }
    // userAccount controller Tests
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
        int profileId = 123;
        boolean suspension = true;

        when(userProfile.suspendUserProfile(profileId, suspension)).thenReturn(true);

        boolean result = suspendUserProfileController.suspendUserProfile(profileId, suspension);

        assertTrue(result);
        verify(userProfile).suspendUserProfile(profileId, suspension);
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
        when(jdbcTemplate.update(...)).thenReturn(1);
        boolean created = realUserAccount.createAccount(...);
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

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(10))).thenReturn(mockList);

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

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(mockList);

        List<UserAccount> result = userAccount.searchUserAccount();

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
        UserProfile profile = new UserProfile(1, "Admin", "desc", false);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1))).thenReturn(List.of(profile));

        UserProfile result = userProfile.viewUserProfile(1);

        assertNotNull(result);
        assertEquals(1, result.getProfileId());
        assertEquals("Admin", result.getProfileName());
    }

    @Test
    public void testViewUserProfile_NotFound() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt())).thenReturn(List.of());

        UserProfile result = userProfile.viewUserProfile(999);

        assertNull(result);
    }

    @Test
    public void testGetProfileIdByName_Found() {
        when(jdbcTemplate.query(anyString(), any(), eq("Admin"))).thenReturn(List.of(1));

        Integer id = userProfile.getProfileIdByName("Admin");

        assertNotNull(id);
        assertEquals(1, id);
    }

    @Test
    public void testGetProfileIdByName_NotFound() {
        when(jdbcTemplate.query(anyString(), any(), anyString())).thenReturn(List.of());

        Integer id = userProfile.getProfileIdByName("NonExistent");

        assertNull(id);
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

