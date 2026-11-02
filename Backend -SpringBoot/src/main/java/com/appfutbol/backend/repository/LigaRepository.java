package com.appfutbol.backend.repository;
import com.appfutbol.backend.entity.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface LigaRepository extends JpaRepository<Liga, Integer> {
    // Al extender de JpaRepository, ya tenemos acceso invisible a métodos como save(), findAll(), findById() y delete().
}
