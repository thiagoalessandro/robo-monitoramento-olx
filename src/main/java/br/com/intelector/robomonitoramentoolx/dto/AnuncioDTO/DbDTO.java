package br.com.intelector.robomonitoramentoolx.dto.AnuncioDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DbDTO implements Serializable {

    @JsonProperty(value = "id_fiat_argo_2018")
    private String idFiatArgo2018;

}
