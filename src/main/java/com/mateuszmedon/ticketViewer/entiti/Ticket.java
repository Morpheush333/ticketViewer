package com.mateuszmedon.ticketViewer.entiti;

        import lombok.Data;
        import lombok.NoArgsConstructor;

        import javax.persistence.*;
        import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int count;
    private String url;
    private String subject;
    private String status;

    @Lob
    private String description;


}

