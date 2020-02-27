package br.com.intelector.robomonitoramentoolx.repository;

import br.com.intelector.robomonitoramentoolx.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

    @Query("SELECT a FROM Anuncio a WHERE a.dhAtu <= :data")
    List<Anuncio> findByDataRetroativa(Date data);

}
