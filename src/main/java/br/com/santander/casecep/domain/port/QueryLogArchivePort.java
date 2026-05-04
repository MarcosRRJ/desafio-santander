package br.com.santander.casecep.domain.port;

import br.com.santander.casecep.domain.CepQueryLog;

public interface QueryLogArchivePort {
    void archive(CepQueryLog log);
}
