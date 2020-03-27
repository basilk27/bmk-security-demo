package com.mbsystems.bmksecuritydemo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Student {

    private final Integer   id;
    private final String    name;
}
