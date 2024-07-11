package br.com.rafaelmoura.spring_security_api.controller;

import br.com.rafaelmoura.spring_security_api.exceptions.ProductNotFoundException;
import br.com.rafaelmoura.spring_security_api.exceptions.GenericException;
import br.com.rafaelmoura.spring_security_api.model.dto.PageableResponseDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.ProductRequestDTO;
import br.com.rafaelmoura.spring_security_api.model.dto.ProductResponseDTO;
import br.com.rafaelmoura.spring_security_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/products")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "ProductController")
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/v1")
    @Operation(summary = "Realiza o cadastro de um produto no sistema", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto incluido com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema. Tente novamente em alguns instantes ou contate um administrador.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GenericException.class))}
            ),
    })
    public ResponseEntity<ProductResponseDTO> insertProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        log.info("Iniciando fluxo para inserir [{}] unidades do produto [{}] com serialNumber [{}]",
                productRequestDTO.getQuantity(), productRequestDTO.getProduct(), productRequestDTO.getSerialNumber());

        ProductResponseDTO productResponseDTO = productService.insertProduct(productRequestDTO);

        log.info("Finalizando fluxo para inserir o produto [{}] com serialNumber [{}]",
                productRequestDTO.getProduct(), productRequestDTO.getSerialNumber());

        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/v1/{serialNumber}")
    @Operation(summary = "Busca um produto no sistema com base em seu serialNumber", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca do produto realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado com os valores fornecidos, verifique os parâmetros informados e tente novamente.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductNotFoundException.class))}
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema. Tente novamente em alguns instantes ou contate um administrador.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GenericException.class))}
            ),
    })
    public ResponseEntity<ProductResponseDTO> findProductBySerialNumber(@PathVariable String serialNumber) throws ProductNotFoundException {
        log.info("Iniciando fluxo para buscar o produto com serialNumber [{}]",
                serialNumber);

        ProductResponseDTO productResponseDTO = productService.findProductBySerialNumber(serialNumber);

        log.info("Finalizando fluxo para buscar o produto com serialNumber [{}]",
                serialNumber);

        return new ResponseEntity<>(productResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/v1")
    @Operation(summary = "Busca todos os produtos no sistema", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema. Tente novamente em alguns instantes ou contate um administrador.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GenericException.class))}
            ),
    })
    @PageableAsQueryParam
    public ResponseEntity<PageableResponseDTO> findAllProducts(@ParameterObject Pageable pageable) {
        log.info("Iniciando fluxo para recuperar [{}] produtos da pagina [{}]", pageable.getPageSize(),
                pageable.getPageNumber());

        PageableResponseDTO pageableResponseDTO = productService.findAllProducts(pageable);

        log.info("Finalizando fluxo para recuperar produtos. Total de paginas [{}], total de elementos [{}]",
                pageableResponseDTO.getTotalPages(), pageableResponseDTO.getTotalRecords());

        return new ResponseEntity<>(pageableResponseDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/v1/{serialNumber}")
    @Operation(summary = "Faz a atualização de um produto no sistema com base em seu serialNumber", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Busca do produto realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado com os valores fornecidos, verifique os parâmetros informados e tente novamente.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductNotFoundException.class))}
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema. Tente novamente em alguns instantes ou contate um administrador.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GenericException.class))}
            ),
    })
    public ResponseEntity<ProductResponseDTO> updateProductBySerialNumber(@PathVariable String serialNumber,
                                                                          @RequestBody ProductRequestDTO productRequestDTO) throws ProductNotFoundException {
        log.info("Iniciando fluxo para buscar o produto com serialNumber [{}]",
                serialNumber);

        ProductResponseDTO productResponseDTO = productService.updateProductBySerialNumber(serialNumber, productRequestDTO);

        log.info("Finalizando fluxo para buscar o produto com serialNumber [{}]",
                serialNumber);

        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/v1/{serialNumber}")
    @Operation(summary = "Faz a delecao de um produto no sistema com base em seu serialNumber", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delecao do produto realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado com os valores fornecidos, verifique os parâmetros informados e tente novamente.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductNotFoundException.class))}
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno do sistema. Tente novamente em alguns instantes ou contate um administrador.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GenericException.class))}
            ),
    })
    public ResponseEntity<ProductResponseDTO> deleteProductBySerialNumber(@PathVariable String serialNumber) throws ProductNotFoundException {
        log.info("Iniciando fluxo para deletar o produto com serialNumber [{}]",
                serialNumber);

        productService.deleteProductBySerialNumber(serialNumber);

        log.info("Finalizando fluxo para deletar o produto com serialNumber [{}]",
                serialNumber);

        return ResponseEntity.noContent().build();
    }
}
