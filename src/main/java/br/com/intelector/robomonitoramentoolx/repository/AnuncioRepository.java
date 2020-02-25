package br.com.intelector.robomonitoramentoolx.repository;

import br.com.intelector.robomonitoramentoolx.model.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
}
