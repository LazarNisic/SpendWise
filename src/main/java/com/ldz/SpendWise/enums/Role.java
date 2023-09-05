package com.ldz.SpendWise.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Role {
    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(3, "ROLE_CLIENT");

    private final int id;
    private final String code;

    Role(int id, String code) {
        this.id = id;
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static Role of(int id) {
        return Stream.of(Role.values())
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
