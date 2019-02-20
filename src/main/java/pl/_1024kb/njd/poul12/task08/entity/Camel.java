package pl._1024kb.njd.poul12.task08.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Camel
{
    private Long id;
    private String name;
    private Integer age;
    private Double capacity;
    private Gender gender;
}
