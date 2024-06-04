package br.com.johann.desafiohotel.resources;

import br.com.johann.desafiohotel.domain.entities.dto.HospedeDto;
import br.com.johann.desafiohotel.domain.services.HospedeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/hospede")
public class HospedeResource {

    private final HospedeService hospedeService;

    public HospedeResource(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<List<HospedeDto>> listar() {
        return ResponseEntity.ok(this.hospedeService.listarTodos());
    }

    @PostMapping(value = "/novo")
    public ResponseEntity<HospedeDto> novo(@RequestBody HospedeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.hospedeService.novoHospede(dto));
    }

    @GetMapping(value = "/buscaporid")
    public ResponseEntity<HospedeDto> buscaPorId(@RequestParam("id") Long hospedeId) {
        return ResponseEntity.ok(this.hospedeService.buscaPorId(hospedeId));
    }
}
