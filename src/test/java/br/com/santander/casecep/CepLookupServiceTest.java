package br.com.santander.casecep;

import br.com.santander.casecep.application.CepLookupService;
import br.com.santander.casecep.application.CepResponse;
import br.com.santander.casecep.domain.CepClient;
import br.com.santander.casecep.domain.CepQueryLogRepository;
import br.com.santander.casecep.domain.port.QueryLogArchivePort;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CepLookupServiceTest {
    private final CepClient client = mock(CepClient.class);
    private final CepQueryLogRepository repository = mock(CepQueryLogRepository.class);
    private final QueryLogArchivePort archivePort = mock(QueryLogArchivePort.class);
    private final CepLookupService service = new CepLookupService(client, repository, archivePort);

    @Test
    void shouldSearchAndPersistLog() {
        when(client.fetchByCep("01001000")).thenReturn(new CepResponse("01001-000", "Praça da Sé", "Sé", "São Paulo", "SP"));

        CepResponse response = service.execute("01001000");

        assertThat(response.uf()).isEqualTo("SP");
        verify(repository, times(1)).save(any());
        verify(archivePort, times(1)).archive(any());
    }

    @Test
    void shouldRejectInvalidCep() {
        assertThatThrownBy(() -> service.execute("abc"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("8 dígitos");
    }
}
