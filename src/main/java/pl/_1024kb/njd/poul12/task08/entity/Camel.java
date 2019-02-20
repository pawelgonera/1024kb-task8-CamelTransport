package pl._1024kb.njd.poul12.task08.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Camel
{
    private Long id;
    private String name;
    private Integer age;
    private Double capacity;
    private Gender gender;
}
