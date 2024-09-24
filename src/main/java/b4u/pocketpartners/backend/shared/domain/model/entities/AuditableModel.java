package b4u.pocketpartners.backend.shared.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass

/*para auditoría automática de las entidades. Al ser un MappedSuperclass, otras
entidades pueden heredar de esta clase y automáticamente tendrán los campos 
createdAt y updatedAt gestionados sin necesidad de agregar lógica adicional en las 
clases hijas.
*/





public class AuditableModel {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;
}
