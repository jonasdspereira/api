package com.api.cadastro.controller;

import com.api.cadastro.model.Endereco;
import com.api.cadastro.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> alterarEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
        endereco.setId(id);
        Endereco enderecoAlterado = enderecoService.alterarEndereco(endereco);
        return new ResponseEntity<>(enderecoAlterado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEndereco(@PathVariable Long id) {
        enderecoService.excluirEndereco(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> exibirEndereco(@PathVariable Long id) {
        Endereco endereco = enderecoService.exibirEndereco(id);
        return new ResponseEntity<>(endereco, HttpStatus.OK);
    }
}
