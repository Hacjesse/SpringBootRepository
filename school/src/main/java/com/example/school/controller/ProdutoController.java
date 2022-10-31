package com.example.school.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.school.model.Produto;
import com.example.school.repository.ProdutoRepository;


@RestController
@RequestMapping("/{produto}")
public class ProdutoController {
    @Autowired
    private ProdutoRepository _produtoRepository;

    @GetMapping
    public List<Produto> findAll() {
        return _produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable(value = "id") Integer id) {
        Optional<Produto> produto = _produtoRepository.findById(id);
        if(produto.isPresent()) {
            return new ResponseEntity<Produto>(produto.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto post(@RequestBody Produto produto) {
        return _produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> put(@PathVariable(value = "id") Integer id, @RequestBody Produto newProduto) {
        Optional<Produto> oldProduto = _produtoRepository.findById(id);
        if(oldProduto.isPresent()){
            Produto produto = oldProduto.get();
            produto.setNome(newProduto.getNome());
            produto.setPreco(newProduto.getPreco());
            produto.setQuantidade(newProduto.getQuantidade());
            _produtoRepository.save(produto);
            return new ResponseEntity<Produto>(produto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Integer id) {
        Optional<Produto> produto = _produtoRepository.findById(id);
        if(produto.isPresent()) {
            _produtoRepository.delete(produto.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
