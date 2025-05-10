
import com.cleaningsystem.controller.UserAccountController;
import com.cleaningsystem.controller.UserProfileController;
import com.cleaningsystem.model.UserAccount;
import com.cleaningsystem.model.UserProfile;
import com.cleaningsystem.boundary.Boundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.*;
import javax.management.modelmbean.ModelMBean;
import jdk.jfr.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundaryTest {

    @InjectMocks
    private Boundary boundary;

    @Mock
    private UserProfileController userProfileC;

    @Mock
    private UserAccountController userAccountC;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowHomePage() {
        String viewName = boundary.showHomePage(model);
        assertEquals("home", viewName);
    }

    @Test
    public void testShowUserAdminHome() {
        when(session.getAttribute("username")).thenReturn("admin");

        String viewName = boundary.showUserAdminHome(session, model);

        verify(model).addAttribute("username", "admin");
        assertEquals("useradmin_home_page", viewName);
    }

    @Test
    public void testShowCleanerHome() {
        when(session.getAttribute("username")).thenReturn("cleaner");

        String viewName = boundary.showCleanerHome(session, model);

        verify(model).addAttribute("username", "cleaner");
        assertEquals("cleaner_home_page", viewName);
    }

    @Test
    public void testShowHomeOwnerHome() {
        when(session.getAttribute("username")).thenReturn("homeowner");

        String viewName = boundary.showHomeOwnerHome(session, model);

        verify(model).addAttribute("username", "homeowner");
        assertEquals("homeowner_home_page", viewName);
    }

    @Test
    public void testShowPlatformManagerHome() {
        when(session.getAttribute("username")).thenReturn("PM");

        String viewName = boundary.showPlatformManagerHome(session, model);

        verify(model).addAttribute("username", "PM");
        assertEquals("platformmanager_home_page", viewName);
    }

    @Test
    public void testShowLoginForm() {
        List<String> profileNames = Arrays.asList("Admin", "User");
        when(userProfileC.getProfileNames()).thenReturn(profileNames);

        String viewName = boundary.showLoginForm(model);

        verify(userProfileC).getProfileNames();
        verify(model).addAttribute(eq("loginForm"), any());
        verify(model).addAttribute("userProfileNames", profileNames);

        assertEquals("login", viewName);
    }

    @Test
    public void testShowCleanerSignUpForm() {
        Model model = mock(Model.class);
    
        String viewName = boundary.showCleanerSignUpForm(model);
    
        assertEquals("cleaner_user_creation", viewName);
        verify(model).addAttribute(eq("CleanerUserCreationForm"), any(UserAccount.class));
    }
    
    @Test
    public void testprocessCleanerSignUp() {
        Model model = mock(Model.class);
        UserAccount mockuser = new UserAccount();
    
        mockuser.setName("benja");
        mockuser.setUsername("cleaner");
        mockuser.setAge(40);
        mockuser.setDob("2001-10-10");
        mockuser.setGender("other");
        mockuser.setAddress("lol town, nom nom");
        mockuser.setEmail("bytheeyeof___@gmail.com");
        mockuser.setPassword("minz");
        mockuser.setProfileId(4);
    
        // Mocking userAccountC and userProfileC behavior
        when(userAccountC.CreateAccount(anyString(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt()))
            .thenReturn(true);
    
        UserProfile mockProfile = new UserProfile();
        mockProfile.setProfileName("Cleaner");
        when(userProfileC.getProfileById(4)).thenReturn(mockProfile);
    
        String viewName = boundary.processCleanerSignUp(mockuser, model);
    
        assertEquals("user_account_info", viewName);
        verify(model).addAttribute("userAccountInfo", mockuser);
        verify(model).addAttribute("profileName", "Cleaner");
    }
    
    @Test
    public void testProcessLogin() {
        UserAccount mockUser = new UserAccount();
        mockUser.setUid(1);
        mockUser.setUsername("admin");
        mockUser.setProfileId(2);

        when(userAccountC.login("admin", "testPass", 2)).thenReturn(mockUser);

        UserProfile mockProfile = new UserProfile();
        mockProfile.setProfileName("User");
        when(userProfileC.getProfileById(2)).thenReturn(mockProfile);

        UserAccount loginForm = new UserAccount();
        loginForm.setUsername("admin");
        loginForm.setPassword("testPass");
        loginForm.setProfileId(2);

        String result = boundary.processLogin(loginForm, model, session, redirectAttributes);

        assertEquals("redirect:/UserHome", result);

        verify(session).setAttribute("uid", 1);
        verify(session).setAttribute("username", "admin");
        verify(session).setAttribute("profileId", 2);

        verify(model).addAttribute("userAccountInfo", mockUser);
        verify(model).addAttribute("profileName", "User");
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
        int mockUID = 4;
    
        // Mock user account
        UserAccount mockuser = new UserAccount();
        mockuser.setName("jamin");
        mockuser.setUid(mockUID);
        mockuser.setProfileId(4);
    
        // Mock profile
        UserProfile mockProfile = new UserProfile();
        mockProfile.setProfileName("mock");
        mockProfile.setProfileId(4);
    
        // Stubbing the controller calls
        when(userAccountC.ViewUserAccount(mockUID)).thenReturn(mockuser);
        when(userProfileC.getProfileById(4)).thenReturn(mockProfile);
    
        // Call the method under test
        String viewName = boundary.showUserAccount(mockUID, model);
    
        // Verify model attributes are added
        verify(model).addAttribute("userAccountInfo", mockuser);
        verify(model).addAttribute("profileName", "mock");
    
        // Assert the view name
        assertEquals("user_account_info", viewName);

    }
    @Test
    public void testShowUserAccountList() {
        Model model = mock(Model.class);

        // Mock user accounts list
        List<UserAccount> mockAccounts = new ArrayList<>();
        UserAccount user1 = new UserAccount(); user1.setProfileId(1); mockAccounts.add(user1);
        UserAccount user2 = new UserAccount(); user2.setProfileId(2); mockAccounts.add(user2);

        // Mock profiles
        UserProfile profile1 = new UserProfile(); profile1.setProfileName("Admin");
        UserProfile profile2 = new UserProfile(); profile2.setProfileName("Cleaner");

        // Stubbing
        when(userAccountC.getAllUsers()).thenReturn(mockAccounts);
        when(userProfileC.getProfileById(1)).thenReturn(profile1);
        when(userProfileC.getProfileById(2)).thenReturn(profile2);

        // Call the method
        String viewName = boundary.showUserAccountList(model);

        // Verify
        verify(model).addAttribute(eq("userAccounts"), eq(mockAccounts));
        verify(model).addAttribute(eq("profileNames"), argThat(list -> ((List<String>) list).contains("Admin") && ((List<String>) list).contains("Cleaner")));

        assertEquals("user_account_list", viewName);
    }
    @Test
    public void testShowUpdateUserAccount() {
        Model model = mock(Model.class);
        String username = "jamin";

        UserAccount mockUser = new UserAccount();
        mockUser.setUsername(username);

        when(userAccountC.ViewUserAccount(username)).thenReturn(mockUser);

        String viewName = boundary.showUpdateUserAccount(username, model);

        verify(model).addAttribute("userAccountInfo", mockUser);
        assertEquals("user_account_update", viewName);
    }
    @Test
public void testProcessUpdateUserAccount_Success() {
    Model model = mock(Model.class);

    UserAccount user = new UserAccount();
    user.setUid(4);
    user.setProfileId(1);

    UserProfile mockProfile = new UserProfile();
    mockProfile.setProfileName("Admin");

    when(userAccountC.UpdateUserAccount(any(), anyInt(), any(), any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(true);
    when(userProfileC.getProfileById(1)).thenReturn(mockProfile);

    String viewName = boundary.processUpdateUserAccount(user, model);

    verify(model).addAttribute("userAccountInfo", user);
    verify(model).addAttribute("profileName", "Admin");
    assertEquals("user_account_info", viewName);
}

    @Test
    public void testProcessUpdateUserAccount_Fail() {
        Model model = mock(Model.class);

        UserAccount user = new UserAccount();

        when(userAccountC.UpdateUserAccount(any(), anyInt(), any(), any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(false);

        String viewName = boundary.processUpdateUserAccount(user, model);

        verify(model).addAttribute("error", "Profile update failed! Please try again.");
        assertEquals("user_account_update", viewName);
    }
    @Test
    public void testProcessSuspendUserAccount_Success() {
        Model model = mock(Model.class);
    
        UserAccount user = new UserAccount();
        user.setUid(5);
    
        UserAccount updatedUser = new UserAccount();
        updatedUser.setProfileId(2);
    
        UserProfile profile = new UserProfile();
        profile.setProfileName("Cleaner");
    
        when(userAccountC.setSuspensionStatus(5, true)).thenReturn(true);
        when(userAccountC.ViewUserAccount(5)).thenReturn(updatedUser);
        when(userProfileC.getProfileById(2)).thenReturn(profile);
    
        String viewName = boundary.processAccountSuspensionStatus(true, user, model);
    
        assertEquals("redirect:/ViewUserAccount?uid=5", viewName);
    }
    @Test
    public void testSearchUserAccounts() {
        Model model = mock(Model.class);
        String query = "searchTerm";

        // Mock user accounts
        List<UserAccount> mockAccounts = new ArrayList<>();
        UserAccount user = new UserAccount(); user.setProfileId(3); mockAccounts.add(user);

        UserProfile profile = new UserProfile(); profile.setProfileName("HomeOwner");

        when(userAccountC.SearchUser(query)).thenReturn(mockAccounts);
        when(userProfileC.getProfileById(3)).thenReturn(profile);

        String viewName = boundary.searchUserAccounts(query, model);

        verify(model).addAttribute("userAccounts", mockAccounts);
        verify(model).addAttribute(eq("profileNames"), argThat(list -> ((List<String>) list).contains("HomeOwner")));

        assertEquals("user_account_list", viewName);
}



}
