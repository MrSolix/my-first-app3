package eu.senla.myfirstapp.app.servlet.student;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class StudentServlet {

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public String redirectStudentPage() {
        log.info("Entered Student Page");
        return "student/studentPage";
    }
}
