package br.com.rafaelmoura.spring_security_api.model.dto;

import br.com.rafaelmoura.spring_security_api.model.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(name = "PageableResponseDTO", description = "classe de retorno da listagem com paginacao")
public class PageableResponseDTO {

    @Schema(name = "content", description = "campo com o conteudo retornado")
    private List<Product> content;
    @Schema(name = "pageNumber", description = "numero da pagina atual", example = "1")
    private int pageNumber;
    @Schema(name = "totalPages", description = "total de paginas disponiveis")
    private int totalPages;
    @Schema(name = "totalRecords", description = "total de elementos")
    private Long totalRecords;
    @Schema(name = "firstPage", description = "indica se a pagina atual e a primeira pagina")
    private boolean firstPage;
    @Schema(name = "lastPage", description = "indica se a pagina atual e a ultima pagina")
    private boolean lastPage;

}
