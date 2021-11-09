package com.aline.openApi.services;

import com.aline.openApi.models.ProdutoEntity;
import com.aline.openApi.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Override
    public ProdutoEntity create(ProdutoEntity produtoEntity) {
        return repository.save(produtoEntity);
    }

    @Override
    public ProdutoEntity findById(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<ProdutoEntity> findAll() {
        return repository.findAll();
    }


}