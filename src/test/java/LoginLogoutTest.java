import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cleaningsystem.controller.LoginLogout.LoginController;
import com.cleaningsystem.controller.LoginLogout.LogoutController;
import com.cleaningsystem.entity.UserAccount;
import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class LoginLogoutTest {

    @Mock
    private UserAccount userAccount;

    @InjectMocks
    private LoginController loginController;

    @Test
    public void testLogin_Success() {
        String username = "user1";
        String password = "pass123";
        int profileId = 1;

        UserAccount mockUser = new UserAccount(); // or mock(UserAccount.class) if complex

        // Mock the userAccount.login call
        when(userAccount.login(username, password, profileId)).thenReturn(mockUser);

        UserAccount result = loginController.login(username, password, profileId);

        assertNotNull(result);
        assertEquals(mockUser, result);

        verify(userAccount, times(1)).login(username, password, profileId);
    }

    @Test
    public void testLogin_Failure() {
        when(userAccount.login(anyString(), anyString(), anyInt())).thenReturn(null);

        UserAccount result = loginController.login("wrongUser", "wrongPass", 99);

        assertNull(result);
        verify(userAccount, times(1)).login("wrongUser", "wrongPass", 99);
    }

    @Test
    public void testLogout_SessionInvalidated() {
        HttpSession session = mock(HttpSession.class);
        LogoutController logoutController = new LogoutController();

        logoutController.logout(session);

        verify(session, times(1)).invalidate();
    }
}

