
package eu.senla.myfirstapp.app.servlet;

import eu.senla.myfirstapp.app.service.person.PersonService;
import eu.senla.myfirstapp.model.people.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserInfoServlet {
    private final PersonService personService;

    @GetMapping("/main/user-info")
    public ModelAndView userInfo(Principal principal) {
        log.info("Entered User Info Page");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main/userInfoPage");
        Optional<Person> optionalPerson = personService.find(principal.getName());
        optionalPerson.ifPresent(person -> modelAndView.addObject("user", person));
        return modelAndView;
    }
}