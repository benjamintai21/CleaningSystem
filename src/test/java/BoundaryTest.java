import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cleaningsystem.boundary.Boundary;
import com.cleaningsystem.controller.LoginLogout.LoginController;
import com.cleaningsystem.controller.ServiceListing.OthersServiceListingController;
import com.cleaningsystem.controller.ServiceListing.SearchServiceListingController;
import com.cleaningsystem.controller.UserAdmin.UserAccount.CreateUserAccountController;
import com.cleaningsystem.controller.UserAdmin.UserAccount.SearchUserAccountController;
import com.cleaningsystem.controller.UserAdmin.UserAccount.SuspendUserAccountController;
import com.cleaningsystem.controller.UserAdmin.UserAccount.UpdateUserAccountController;
import com.cleaningsystem.controller.UserAdmin.UserAccount.ViewUserAccountController;
import com.cleaningsystem.controller.UserAdmin.UserProfile.SearchUserProfileController;
import com.cleaningsystem.controller.UserAdmin.UserProfile.ViewUserProfileController;
import com.cleaningsystem.entity.ServiceListing;
import com.cleaningsystem.entity.UserAccount;
import com.cleaningsystem.entity.UserProfile;

import jakarta.servlet.http.HttpSession;

public class BoundaryTest {

    @InjectMocks
    private Boundary boundary;  // This will be replaced by spy in setUp()

    @Mock private LoginController LoginC;
    @Mock private CreateUserAccountController createUserAccountC;
    @Mock private ViewUserAccountController viewUserAccountC;
    @Mock private ViewUserProfileController viewUserProfileC;
    @Mock private UpdateUserAccountController updateUserAccountC;
    @Mock private SearchUserAccountController searchUserAccountC;
    @Mock private SearchUserProfileController searchUserProfileC;
    @Mock private SuspendUserAccountController suspendUserAccountC;
    @Mock private SearchServiceListingController searchServiceListingC;
    @Mock private OthersServiceListingController othersServiceListingC;
    @Mock private UserProfile userProfile;
    @Mock private UserAccount userAccount;
    @Mock private Model model;
    @Mock private HttpSession session;
    @Mock private RedirectAttributes redirectAttributes;

    private Boundary spyBoundary;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create a spy from the injected boundary instance to allow stubbing internal methods
        spyBoundary = Mockito.spy(boundary);
    }

    @Test
    public void testShowHomePage() {
        // Arrange - set up mocked return values
        List<UserAccount> mockCleaners = new ArrayList<>();
        mockCleaners.add(new UserAccount());
    
        List<Integer> mockServicesCountList = Arrays.asList(3, 5, 2);
    
        List<ServiceListing> mockServiceListings = new ArrayList<>();
        mockServiceListings.add(new ServiceListing());
    
        when(searchUserAccountC.searchUserAccount(4)).thenReturn(mockCleaners);
        when(othersServiceListingC.getServicesCountList()).thenReturn(mockServicesCountList);
        when(searchServiceListingC.searchServiceListing()).thenReturn(mockServiceListings);
    
        // Act
        String viewName = boundary.showHomePage(model);
    
        // Assert
        assertEquals("home", viewName);
        verify(model).addAttribute("cleaners", mockCleaners);
        verify(model).addAttribute("servicesCountList", mockServicesCountList);
        verify(model).addAttribute("serviceListings", mockServiceListings);
    }
    

    @Test
    public void testShowUserAdminHome() {
        Boundary spyBoundary = Mockito.spy(boundary);  // Create a spy
    
        // Stub checkAccess to return a valid user ID
        doReturn(Optional.of(1)).when(spyBoundary).checkAccess(session, "User Admin");
    
        when(session.getAttribute("username")).thenReturn("admin");
    
        String viewName = spyBoundary.showUserAdminHome(session, model);
    
        verify(model).addAttribute("username", "admin");
        assertEquals("useradmin_home_page", viewName);
    }
    

    @Test
    public void testShowCleanerHome() {
        Boundary spyBoundary = Mockito.spy(boundary);
    
        // Mock checkAccess to allow access
        when(spyBoundary.checkAccess(session, "Cleaner")).thenReturn(Optional.of(1));
    
        // Mock session attribute for username
        when(session.getAttribute("username")).thenReturn("cleaner");  // match the exact username you want to verify
    
        // Call the correct method for HomeOwner
        String viewName = spyBoundary.showCleanerHome(session, model);
    
        verify(model).addAttribute("username", "cleaner");  // make sure case matches what session returns
        assertEquals("cleaner_home_page", viewName);
    }

    @Test
    public void testShowHomeOwnerHome() {
        Boundary spyBoundary = Mockito.spy(boundary);
    
        // Mock checkAccess to allow access
        when(spyBoundary.checkAccess(session, "Home Owner")).thenReturn(Optional.of(1));
    
        // Mock session attribute for username
        when(session.getAttribute("username")).thenReturn("homeowner");  // match the exact username you want to verify
    
        // Call the correct method for HomeOwner
        String viewName = spyBoundary.showHomeOwnerHome(session, model);
    
        verify(model).addAttribute("username", "homeowner");  // make sure case matches what session returns
        assertEquals("homeowner_home_page", viewName);
    }
    
    @Test
    public void testShowPlatformManagerHome() {
        Boundary spyBoundary = Mockito.spy(boundary);  // spy on your boundary instance

        when(spyBoundary.checkAccess(session, "Platform Manager")).thenReturn(Optional.of(1));
        when(session.getAttribute("username")).thenReturn("PM");

        String viewName = spyBoundary.showPlatformManagerHome(session, model);

        verify(model).addAttribute("username", "PM");
        assertEquals("platformmanager_home_page", viewName);
    }

    @Test
    public void testShowLoginForm() {
        List<UserProfile> userProfiles = Arrays.asList(
            new UserProfile("Admin", "Admin Description"), 
            new UserProfile("User", "User Description")
        );
        when(searchUserProfileC.searchUserProfile()).thenReturn(userProfiles);

        String viewName = boundary.showLoginForm(model);
    
        verify(searchUserProfileC).searchUserProfile(); // <-- correct verification
        verify(model).addAttribute(eq("loginForm"), any(UserAccount.class));
        verify(model).addAttribute("userProfiles", userProfiles);
    
        assertEquals("login", viewName);
    }

    @Test
    public void testShowCleanerSignUpForm() {
        String viewName = boundary.showCleanerSignUpForm(session, model);

        verify(model).addAttribute(eq("CleanerUserCreationForm"), any(UserAccount.class));
        assertEquals("cleaner_user_creation", viewName);
    }

    @Test
    public void testProcessCleanerSignUp_Success() {
        // Arrange
        UserAccount mockUser = new UserAccount();
        mockUser.setName("benja");
        mockUser.setUsername("cleaner");
        mockUser.setAge(40);
        mockUser.setDob("2001-10-10");
        mockUser.setGender("other");
        mockUser.setAddress("lol town, nom nom");
        mockUser.setEmail("bytheeyeof___@gmail.com");
        mockUser.setPassword("minz");

        // Use spyBoundary here
        doReturn(Optional.of(1)).when(spyBoundary).checkAccess(session, "User Admin");

        when(createUserAccountC.createAccount(
                anyString(), anyInt(), anyString(), anyString(),
                anyString(), anyString(), anyString(), anyString(), eq(4)
        )).thenReturn(true);

        when(viewUserAccountC.viewUserAccount("cleaner")).thenReturn(mockUser);

        UserProfile mockProfile = new UserProfile();
        mockProfile.setProfileName("Cleaner");

        when(viewUserProfileC.viewUserProfile(4)).thenReturn(mockProfile);

        // Act - use spyBoundary here
        String viewName = spyBoundary.processCleanerSignUp(mockUser, session, model);

        // Assert
        assertEquals("user_account_info", viewName);
        verify(model).addAttribute("userAccountInfo", mockUser);
        verify(model).addAttribute("profileName", "Cleaner");
    }
 
    @Test
    public void testProcessLogin() {
        UserAccount mockUser = new UserAccount();
        mockUser.setUid(1);
        mockUser.setUsername("admin");
        mockUser.setProfileId(2);
    
        // 1. Mock LoginC to return your user on login call
        when(LoginC.login("admin", "testPass", 2)).thenReturn(mockUser);

        // 2. Ensure user is not suspended
        mockUser.setSuspended(false);

        // 3. Mock viewUserProfileC to return a profile
        UserProfile mockProfile = new UserProfile();
        mockProfile.setProfileName("User Admin");
        when(viewUserProfileC.viewUserProfile(2)).thenReturn(mockProfile);

        // 4. Call your boundary method with matching user data
        UserAccount loginForm = new UserAccount();
        loginForm.setUsername("admin");
        loginForm.setPassword("testPass");
        loginForm.setProfileId(2);

        String result = boundary.processLogin(loginForm, session, model, redirectAttributes);

        // 5. Assert expected redirect path
        assertEquals("redirect:/UserAdminHome", result);

        // 6. Verify session attributes and model attributes
        verify(session).setAttribute("uid", 1);
        verify(session).setAttribute("username", "admin");
        verify(session).setAttribute("profileId", 2);
        verify(model).addAttribute("userAccountInfo", mockUser);
        verify(model).addAttribute("profileName", "User Admin");

    }
    
    @Test
    public void testLogout() {
        String result = boundary.logout(session);

        verify(session).invalidate();
        assertEquals("redirect:/Login", result);
    }
    
    @Test
    public void testShowUserAccount() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        int mockUId = 4;

        UserAccount mockuser = new UserAccount();
        mockuser.setName("jamin");
        mockuser.setUid(mockUId);
        mockuser.setProfileId(4);

        UserProfile mockProfile = new UserProfile();
        mockProfile.setProfileName("mock");
        mockProfile.setProfileId(4);

        // Correct mocks — use the same mocks your controller uses
        when(viewUserAccountC.viewUserAccount(mockUId)).thenReturn(mockuser);
        when(viewUserProfileC.viewUserProfile(4)).thenReturn(mockProfile);

        // If checkAccess is used inside the method and is in the same class, spy or mock it:
        Boundary spyBoundary = Mockito.spy(boundary);
        when(spyBoundary.checkAccess(session, "User Admin")).thenReturn(Optional.of(1));

        String viewName = spyBoundary.showUserAccount(mockUId, session, model);

        verify(model).addAttribute("userAccountInfo", mockuser);
        verify(model).addAttribute("profileName", "mock");

        assertEquals("user_account_info", viewName);
    }

    @Test
    public void testShowUserAccountList() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
    
        // Mock user accounts list
        List<UserAccount> mockAccounts = new ArrayList<>();
        UserAccount user1 = new UserAccount(); user1.setProfileId(1); mockAccounts.add(user1);
        UserAccount user2 = new UserAccount(); user2.setProfileId(2); mockAccounts.add(user2);
    
        // Mock profile names list returned by searchUserProfileC
        List<String> profileNames = Arrays.asList("Admin", "Cleaner");
    
        // Mock checkAccess to allow flow
        Boundary spyBoundary = Mockito.spy(boundary);
        when(spyBoundary.checkAccess(session, "User Admin")).thenReturn(Optional.of(1));
    
        // Correct stubbing of dependencies actually used in the controller method
        when(searchUserAccountC.searchUserAccount()).thenReturn(mockAccounts);
        when(searchUserProfileC.searchUserProfileNamesForUserAccounts(mockAccounts)).thenReturn(profileNames);
    
        // Call the method on the spy
        String viewName = spyBoundary.showUserAccountList(session, model);
    
        // Verify model attributes
        verify(model).addAttribute("userAccounts", mockAccounts);
        verify(model).addAttribute("profileNames", profileNames);
    
        assertEquals("user_account_list", viewName);
    }
    
    @Test
    public void testShowUpdateUserAccount_Success() {
        int uid = 5;
        UserAccount mockUser = new UserAccount();
        mockUser.setUid(uid);
        mockUser.setUsername("jamin");

        doReturn(Optional.of(1)).when(spyBoundary).checkAccess(session, "User Admin");
        when(viewUserAccountC.viewUserAccount(uid)).thenReturn(mockUser);

        String viewName = spyBoundary.showUpdateUserAccount(uid, session, model);

        verify(model).addAttribute("userAccountInfo", mockUser);
        assertEquals("user_account_update", viewName);
    }

    @Test
    public void testProcessUpdateUserAccount_Success() {
        Boundary spyBoundary = Mockito.spy(boundary);  // Spy on boundary to mock checkAccess
    
        UserAccount user = new UserAccount();
        user.setUid(4);
        user.setProfileId(1);
        user.setUsername("jamin");
    
        // Initialize all other fields so the real call matches the mock
        user.setName("John");
        user.setAge(30);
        user.setDob("1990-01-01");
        user.setGender("Male");
        user.setAddress("123 Street");
        user.setEmail("john@example.com");
        user.setPassword("pass123");
    
        doReturn(Optional.of(1)).when(spyBoundary).checkAccess(session, "User Admin");
    
        when(updateUserAccountC.updateUserAccount(
            anyString(), anyInt(), anyString(), anyString(), anyString(),
            anyString(), anyString(), anyString(), eq(1), eq(4)
        )).thenReturn(true);
    
        UserProfile profile = new UserProfile();
        profile.setProfileName("Admin");
    
        when(viewUserProfileC.viewUserProfile(1)).thenReturn(profile);
    
        String viewName = spyBoundary.processUpdateUserAccount(user, session, model);
    
        assertEquals("redirect:/UserAccount?uid=4", viewName);
    
        verify(model, never()).addAttribute(eq("userAccountInfo"), any());
        verify(model, never()).addAttribute(eq("profileName"), any());
    }
    
    @Test
    public void testProcessUpdateUserAccount_Fail() {
        UserAccount user = new UserAccount();
        user.setUid(4);
        user.setProfileId(1);
    
        Boundary spyBoundary = Mockito.spy(boundary);
    
        doReturn(Optional.of(1)).when(spyBoundary).checkAccess(session, "User Admin");
    
        when(updateUserAccountC.updateUserAccount(
            anyString(), anyInt(), anyString(), anyString(), anyString(),
            anyString(), anyString(), anyString(), eq(1), eq(4)
        )).thenReturn(false);
    
        String viewName = spyBoundary.processUpdateUserAccount(user, session, model);
    
        assertEquals("user_account_update", viewName);
        verify(model).addAttribute("error", "Profile update failed! Please try again.");
    }
    
    @Test
    public void testProcessSuspendUserAccount_Success() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
    
        UserAccount user = new UserAccount();
        user.setUid(5);
    
        UserAccount updatedUser = new UserAccount();
        updatedUser.setProfileId(2);
        updatedUser.setSuspended(true);  // assuming this flag exists
    
        UserProfile profile = new UserProfile();
        profile.setProfileName("Cleaner");
    
        // Spy on boundary
        Boundary spyBoundary = Mockito.spy(boundary);
    
        // Stub checkAccess to simulate user has access
        doReturn(Optional.of(1)).when(spyBoundary).checkAccess(session, "User Admin");
    
        // Stub suspendUserAccount to return success
        when(suspendUserAccountC.suspendUserAccount(5, true)).thenReturn(true);
    
        // Stub viewUserAccount and viewUserProfile to return expected objects
        when(viewUserAccountC.viewUserAccount(5)).thenReturn(updatedUser);
        when(viewUserProfileC.viewUserProfile(2)).thenReturn(profile);
    
        String viewName = spyBoundary.processAccountSuspensionStatus(true, user, session, model);
    
        assertEquals("redirect:/UserAccount?uid=5", viewName);
    }
    
    
    @Test
    public void testSearchUserAccounts() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        String query = "searchTerm";
    
        // Prepare mock data
        List<UserAccount> mockAccounts = new ArrayList<>();
        UserAccount user = new UserAccount();
        user.setProfileId(3);
        mockAccounts.add(user);
    
        List<String> profileNames = List.of("HomeOwner");
    
        // Spy the controller to stub checkAccess
        Boundary boundarySpy = Mockito.spy(boundary);
    
        // Stub dependencies on spy (if not injected already)
        doReturn(mockAccounts).when(searchUserAccountC).searchUserAccount(query);
        doReturn(profileNames).when(searchUserProfileC).searchUserProfileNamesForUserAccounts(mockAccounts);
    
        // Stub checkAccess to return non-empty Optional so method continues
        doReturn(Optional.of(1)).when(boundarySpy).checkAccess(session, "User Admin");
    
        // Call method under test
        String viewName = boundarySpy.searchUserAccounts(query, session, model);
    
        // Verify model interactions
        verify(model).addAttribute("userAccounts", mockAccounts);
        verify(model).addAttribute("profileNames", profileNames);
    
        assertEquals("user_account_list", viewName);
    }
    

}
