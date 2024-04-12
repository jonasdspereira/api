package com.api.cadastro.service;
import com.api.cadastro.model.Endereco;
import com.api.cadastro.model.Pessoa;
import com.api.cadastro.repository.PessoaRepository;
import com.api.cadastro.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    @Autowired
    PessoaRepository pessoaRepository;
    @Autowired
    EnderecoRepository enderecoRepository;


    public Pessoa adicionarPessoa(Pessoa novaPessoa) {
        if (pessoaRepository.existsByCpf(novaPessoa.getCpf())) {
            throw new RuntimeException("Já existe um usuário com este CPF.");
        }
        return pessoaRepository.save(novaPessoa);
    }

    public Pessoa associarEndereco(Long id, Endereco endereco) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            endereco.setPessoa(pessoa);
            pessoa.getEnderecos().add(endereco);
            return pessoaRepository.save(pessoa);
        }
        throw new RuntimeException("Não encontramos o usuário com o ID: " + id);
    }

    public List<Pessoa> exibirPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        for (Pessoa pessoa : pessoas) {
            int idade = pessoa.calcularIdade();
            pessoa.setIdade(idade);
        }
        return pessoas;
    }

    public Pessoa exibirPessoa(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            pessoa.setIdade(pessoa.calcularIdade());
            return pessoa;
        }
        throw new RuntimeException("Não encontramos o usuário com o ID: " + id);
    }
    public Pessoa alterarPessoa(Pessoa pessoa, Endereco endereco) {
        if (pessoa.getId() == null) {
            throw new IllegalArgumentException("O ID da pessoa não pode ser nulo para realizar a alteração.");
        }
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(pessoa.getId());
        if (pessoaOptional.isPresent()) {
            Pessoa pessoaSalva = pessoaOptional.get();
            pessoaSalva.setNome(pessoa.getNome());
            pessoaSalva.setDataNascimento(pessoa.getDataNascimento());
            pessoaSalva.setCpf(pessoa.getCpf());

            if (endereco != null && endereco.getId() != null) {
                Optional<Endereco> enderecoOptional = enderecoRepository.findById(endereco.getId());
                if (enderecoOptional.isPresent()) {
                    Endereco enderecoSalvo = enderecoOptional.get();
                    enderecoSalvo.setRua(enderecoSalvo.getRua());
                    enderecoSalvo.setNumero(enderecoSalvo.getNumero());
                    enderecoSalvo.setBairro(enderecoSalvo.getBairro());
                    enderecoSalvo.setCidade(enderecoSalvo.getCidade());
                    enderecoSalvo.setEstado(enderecoSalvo.getEstado());
                    enderecoSalvo.setCep(enderecoSalvo.getCep());

                    pessoaSalva.getEnderecos().add(enderecoSalvo);
                }else {
                    throw new RuntimeException("Endereço não encontrado com o ID: " + endereco.getId());
                }
            }
        }
        throw new RuntimeException("Não foi possível encontrar a pessoa com ID: " + pessoa.getId());
    }
    public void excluirPessoa(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);
        if (pessoaOptional.isPresent()) {
            Pessoa pessoa = pessoaOptional.get();
            pessoaRepository.delete(pessoa);
        } else {
            throw new RuntimeException("Não encontramos o usuário com o ID: " + id);
        }
    }
}
