import org.springframework.web.bind.annotation.*;

@RestController
public class App {

    @GetMapping("/")
    public String hello() {
        return "Hello DevOps 🚀";
    }
}
