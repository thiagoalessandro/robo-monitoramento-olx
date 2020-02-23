package br.com.intelector.robomonitoramentoolx.service;

import br.com.intelector.robomonitoramentoolx.dto.AnuncioDTO.DbDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DbService {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Value("file:/home/thiago/db/dbolx.json")
    Resource resource;

    public DbDTO loadDb(){
        try {
            String db = new String(Files.readAllBytes(resource.getFile().toPath()));
            return OBJECT_MAPPER.readValue(db, DbDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void atualizar(DbDTO dbDTO){
        try {
            salvar(dbDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void salvar(DbDTO dbDTO){
        Path path;
        try {
            path = Paths.get(resource.getFile().getPath());
            Files.write(path, toJson(dbDTO).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toJson(DbDTO dbDTO) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(dbDTO);
    }

}
