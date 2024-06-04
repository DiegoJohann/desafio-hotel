package br.com.johann.desafiohotel.resources;

import br.com.johann.desafiohotel.domain.entities.dto.CheckInDto;
import br.com.johann.desafiohotel.domain.entities.dto.CheckInListagem;
import br.com.johann.desafiohotel.domain.services.CheckInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/checkin")
public class CheckInResource {

    private final CheckInService checkInService;

    public CheckInResource(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<List<CheckInListagem>> listar(@RequestParam String filtro) {
        return ResponseEntity.ok(this.checkInService.listaCheckIns(filtro));
    }

    @PostMapping(value = "/novo")
    public ResponseEntity<CheckInDto> novo(@RequestBody CheckInDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.checkInService.novoCheckIn(dto));
    }

    @PatchMapping(value = "/checkout")
    public ResponseEntity<CheckInDto> checkout(@RequestBody CheckInDto dto) {
        return ResponseEntity.ok(this.checkInService.realizarCheckout(dto));
    }

}
