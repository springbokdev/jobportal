package space.springbok.jobportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_location")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobLocation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String city;
  private String state;
  private String country;


}