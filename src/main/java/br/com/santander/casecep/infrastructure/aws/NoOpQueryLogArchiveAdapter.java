package br.com.santander.casecep.infrastructure.aws;

import br.com.santander.casecep.domain.CepQueryLog;
import br.com.santander.casecep.domain.port.QueryLogArchivePort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "aws.archive.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpQueryLogArchiveAdapter implements QueryLogArchivePort {
    @Override
    public void archive(CepQueryLog log) {
        // no-op para ambientes sem AWS
    }
}
