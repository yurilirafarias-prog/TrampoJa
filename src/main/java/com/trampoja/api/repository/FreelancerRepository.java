package repository;

import com.trampoja.api.model.Freelancer;

import java.util.ArrayList;
import java.util.List;

public class FreelancerRepository {

    private final List<Freelancer> freelancers = new ArrayList<>();
    private Long proximoId = 1L;

    public Freelancer salvar(Freelancer freelancer) {
        freelancer.setId(proximoId++);
        freelancers.add(freelancer);
        return freelancer;
    }

    public List<Freelancer> listarTodos() {
        return freelancers;
    }

    public Freelancer buscarPorId(Long id) {
        for (Freelancer freelancer : freelancers) {
            if (freelancer.getId().equals(id)) {
                return freelancer;
            }
        }

        return null;
    }

    public Freelancer atualizar(Long id, Freelancer freelancerAtualizado) {
        Freelancer freelancer = buscarPorId(id);

        if (freelancer != null) {
            freelancer.setNome(freelancerAtualizado.getNome());
            freelancer.setCpf(freelancerAtualizado.getCpf());
            freelancer.setEmail(freelancerAtualizado.getEmail());
            freelancer.setTelefone(freelancerAtualizado.getTelefone());
        }

        return freelancer;
    }

    public boolean deletar(Long id) {
        Freelancer freelancer = buscarPorId(id);

        if (freelancer != null) {
            freelancers.remove(freelancer);
            return true;
        }

        return false;
    }
}