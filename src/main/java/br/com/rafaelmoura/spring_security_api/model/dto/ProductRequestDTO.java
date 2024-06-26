package br.com.rafaelmoura.spring_security_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ProductRequestDTO", description = "classe de requisicao para cadastro de produto")
public class ProductRequestDTO {

    @Schema(name = "product", description = "nome do produto", example = "Notebook")
    private String product;
    @Schema(name = "serialNumber", description = "codigo de serie", example = "AABCJJSH22")
    private String serialNumber;
    @Schema(name = "price", description = "valor do produto", example = "10.00")
    private BigDecimal price;
    @Schema(name = "quantity", description = "quantidade do produto", example = "10")
    private int quantity;
}
