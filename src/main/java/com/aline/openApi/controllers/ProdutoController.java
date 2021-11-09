package com.aline.openApi.controllers;

import com.aline.openApi.models.DTOs.ProdutoDTO;
import com.aline.openApi.models.ProdutoEntity;
import com.aline.openApi.services.ProdutoService;
import com.aline.openApi.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@Log4j2
@RestController
@RequestMapping(value = "/v1/produto", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "produto", description = "Apis relacionadas ao produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    @Operation(summary = "Cadastra um produto",
            description = "Cadastra um novo produto com os dados recebidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CREATED,
                    description = "Produto cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = Constants.BAD_REQUEST,
                    description = "Bad Request",
                    content = @Content(schema = @Schema(
                            implementation = Exception.class
                    )))
    })
    @ResponseStatus(CREATED)
    public ProdutoDTO create(@RequestBody ProdutoDTO produtoDTO) {
        log.info("Start method create={}", produtoDTO);

        ProdutoEntity produtoEntity = mapper.map(produtoDTO, ProdutoEntity.class);
        ProdutoEntity produtoCriado = service.create(produtoEntity);

        return mapper.map(produtoCriado, ProdutoDTO.class);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get produto por id",
            description = "Busca um produto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK,
                    description = "Produto localizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND,
                    description = "Produto não localizado",
                    content = @Content(schema = @Schema(
                            implementation = EntityNotFoundException.class
                    )))
    })
    @ResponseStatus(HttpStatus.OK)
    public ProdutoDTO findById(@PathVariable Long id) {
        try {
            ProdutoEntity produtoEntity = service.findById(id);

            return mapper.map(produtoEntity, ProdutoDTO.class);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não localizado", e);
        }
    }

    @GetMapping
    @Operation(summary = "Listagem de produtos",
            description = "Exibe a lista de todos os produtos cadastrados",
            tags = "produto"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK,
                    description = "produtos listados com sucesso",
                    content = @Content(schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = Constants.BAD_REQUEST,
                    description = "Bad request",
                    content = @Content(schema = @Schema(implementation = Exception.class)))
    })
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoDTO> findAll() {
        List<ProdutoEntity> produtos = service.findAll();
        List<ProdutoDTO> produtosDtos = produtos
                .stream()
                .map(user -> mapper.map(user, ProdutoDTO.class))
                .collect(Collectors.toList());

        return produtosDtos;
    }
}