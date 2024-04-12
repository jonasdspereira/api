package com.api.cadastro.service;

import com.api.cadastro.repository.EnderecoRepository;
import com.api.cadastro.model.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    public Endereco alterarEndereco(Endereco novoEndereco) {
        if (novoEndereco.getId() == null) {
            throw new IllegalArgumentException("O ID do endereço não pode ser nulo para realizar a alteração.");
        }

        Optional<Endereco> enderecoExistenteOptional = enderecoRepository.findById(novoEndereco.getId());
        if (enderecoExistenteOptional.isPresent()) {
            Endereco enderecoExistente = enderecoExistenteOptional.get();
            enderecoExistente.setRua(novoEndereco.getRua());
            enderecoExistente.setNumero(novoEndereco.getNumero());
            enderecoExistente.setBairro(novoEndereco.getBairro());
            enderecoExistente.setCidade(novoEndereco.getCidade());
            enderecoExistente.setEstado(novoEndereco.getEstado());
            enderecoExistente.setCep(novoEndereco.getCep());

            enderecoExistente.setPessoa(enderecoExistente.getPessoa());

            return enderecoRepository.save(enderecoExistente);
        } else {
            throw new RuntimeException("Endereço não encontrado com o ID: " + novoEndereco.getId());
        }
    }

    public void excluirEndereco(Long id) {
        if (!enderecoRepository.existsById(id)) {
            throw new RuntimeException("Endereço não encontrado com o ID: " + id);
        }
        enderecoRepository.deleteById(id);
    }
    public Endereco exibirEndereco(Long id) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if (enderecoOptional.isPresent()) {
            return enderecoOptional.get();
        }
        throw new RuntimeException("Endereço não encontrado com o ID: " + id);
    }
}
