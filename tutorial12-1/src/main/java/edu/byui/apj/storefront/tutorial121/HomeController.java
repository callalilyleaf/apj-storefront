package edu.byui.apj.storefront.tutorial121;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HomeController {

    @RequestMapping("/") // @RequestMapping maps all HTTP operations by default. // Use @GetMapping or @RequestMapping(method=GET) to narrow it down
    public @ResponseBody String greeting() {
        return "Hello, World!";
    }
}
