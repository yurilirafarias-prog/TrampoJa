package service;

import com.trampoja.api.model.Freelancer;
import com.trampoja.api.repository.FreelancerRepository;

import java.util.List;

public class FreelancerService {

    private final FreelancerRepository freelancerRepository;

    public FreelancerService(FreelancerRepository freelancerRepository) {
        this.freelancerRepository = freelancerRepository;
    }
    public List<Freelancer> getAll() {
        return freelancerRepository.listarTodos();
    }
    public Freelancer create(Freelancer freelancer) {
        return freelancerRepository.salvar(freelancer);
    }

}