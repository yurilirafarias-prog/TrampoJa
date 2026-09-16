package controller;

import model.Freelancer;
import service.FreelancerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/freelancer")
public class FreelancerController {

    private final FreelancerService freelancerService;

    public FreelancerController(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
    }

    @GetMapping
    public List<Freelancer> getAll() {
        return freelancerService.getAll();
    }

    @PostMapping
    public Freelancer create(@RequestBody Freelancer novoFreelancer) {
        return freelancerService.create(novoFreelancer);
    }
}