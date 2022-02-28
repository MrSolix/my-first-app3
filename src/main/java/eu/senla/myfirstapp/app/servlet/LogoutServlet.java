package eu.senla.myfirstapp.app.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class LogoutServlet {

    @GetMapping("/main/logout")
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("You have been logged out.");
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
