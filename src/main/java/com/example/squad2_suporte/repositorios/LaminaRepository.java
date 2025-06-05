package com.example.squad2_suporte.repositorios;

   import com.example.squad2_suporte.Lamina.Lamina;
   import com.example.squad2_suporte.enuns.StatusAmostra;
   import org.springframework.data.jpa.repository.JpaRepository;
   import org.springframework.stereotype.Repository;

   import java.time.LocalDateTime;
   import java.util.List;

   @Repository
   public interface LaminaRepository extends JpaRepository<Lamina, Long> {
       Lamina findByProtocolo(String protocolo);

       List<Lamina> findAllByProtocoloIn(List<String> protocolos);

       List<Lamina> findByDataBeforeAndStatusIn(LocalDateTime cutoffDate, List<StatusAmostra> statuses);
   }