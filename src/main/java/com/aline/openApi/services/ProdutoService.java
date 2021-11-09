package com.aline.openApi.services;

import com.aline.openApi.models.ProdutoEntity;

import java.util.List;

public interface ProdutoService {

    ProdutoEntity create(ProdutoEntity produtoEntity);
    ProdutoEntity findById(Long id);
    List<ProdutoEntity> findAll();

}