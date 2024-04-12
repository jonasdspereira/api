package com.api.cadastro.controller;

import com.api.cadastro.model.Pessoa;
import com.api.cadastro.model.Endereco;
import com.api.cadastro.service.PessoaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pessoas", produces = {"application/json"})
@Tag(name = "open-api")
public class PessoaController {

    //Criar pessoa e enderecos, listar, alterar e deletar pessoa e endereços

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Pessoa> adicionarPessoa(@RequestBody Pessoa novaPessoa) {
        Pessoa pessoa = pessoaService.adicionarPessoa(novaPessoa);
        return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/enderecos")
    public ResponseEntity<Pessoa> adicionarEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
        Pessoa pessoa = pessoaService.associarEndereco(id, endereco);
        return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> exibirPessoa(@PathVariable Long id) {
    Pessoa pessoa = pessoaService.exibirPessoa(id);
    return new ResponseEntity<>(pessoa, HttpStatus.OK);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Pessoa>> exibirPessoas() {
        List<Pessoa> pessoas = pessoaService.exibirPessoas();
        return new ResponseEntity<>(pessoas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> alterarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        pessoa.setId(id);

        if (pessoa == null) {
            throw new IllegalArgumentException("O objeto Pessoa no corpo da requisição não pode ser nulo.");
        }

        Pessoa pessoaAlterada = pessoaService.alterarPessoa(pessoa);
        return new ResponseEntity<>(pessoaAlterada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
    pessoaService.excluirPessoa(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
