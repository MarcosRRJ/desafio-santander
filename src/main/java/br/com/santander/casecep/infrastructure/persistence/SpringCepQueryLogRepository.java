package br.com.santander.casecep.infrastructure.persistence;

import br.com.santander.casecep.domain.CepQueryLog;
import br.com.santander.casecep.domain.CepQueryLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

interface JpaLogRepository extends JpaRepository<CepQueryLog, UUID> {}

@Repository
public class SpringCepQueryLogRepository implements CepQueryLogRepository {
    private final JpaLogRepository repository;

    public SpringCepQueryLogRepository(JpaLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public CepQueryLog save(CepQueryLog log) {
        return repository.save(log);
    }
}
