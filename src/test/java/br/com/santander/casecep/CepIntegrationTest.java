package br.com.santander.casecep;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CepIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @RegisterExtension
    static WireMockExtension wm = WireMockExtension.newInstance().options(wireMockConfig().port(8089)).build();

    @Test
    void shouldQueryCepUsingExternalMock() throws Exception {
        wm.stubFor(WireMock.get(WireMock.urlEqualTo("/01001000/json")).willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                        {"cep":"01001-000","logradouro":"Praça da Sé","bairro":"Sé","localidade":"São Paulo","uf":"SP"}
                        """)));

        mockMvc.perform(get("/api/ceps/01001000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.localidade").value("São Paulo"));
    }
}
