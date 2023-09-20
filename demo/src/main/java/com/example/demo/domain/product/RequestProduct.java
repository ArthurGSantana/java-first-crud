package com.example.demo.domain.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RequestProduct(@NotBlank String name, @NotNull Double price) {
}
