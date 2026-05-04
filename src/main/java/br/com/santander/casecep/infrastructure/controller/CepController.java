package br.com.santander.casecep.infrastructure.controller;

import br.com.santander.casecep.application.CepLookupService;
import br.com.santander.casecep.application.CepResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ceps")
public class CepController {
    private final CepLookupService service;

    public CepController(CepLookupService service) {
        this.service = service;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<CepResponse> findByCep(@PathVariable String cep) {
        return ResponseEntity.ok(service.execute(cep));
    }
}
