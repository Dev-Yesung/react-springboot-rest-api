package kr.co.prgrms.clone;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    String redirectToBrowsePage() {
        return "redirect:/browse";
    }

    @GetMapping("/browse")
    String loadBrowsePage() {
        return "browse";
    }

    @GetMapping("/browse-login")
    String loadBrowseLogInPage() {
        return "browse-login";
    }

    @GetMapping("/search")
    String loadSearchPage() {
        return "search";
    }

    @GetMapping("/signup")
    String loadSignUpPage() {
        return "signup";
    }

    @GetMapping("/login")
    String loadLogInPage() {
        return "login";
    }

    @GetMapping("/browse/login")
    String loadBrowseAfterLogInPage() {
        return "browse-login";
    }
}
