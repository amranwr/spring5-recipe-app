package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Note {
    @Lob //this is for large objects.....
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @OneToOne
    private Recipe recipe;

}
