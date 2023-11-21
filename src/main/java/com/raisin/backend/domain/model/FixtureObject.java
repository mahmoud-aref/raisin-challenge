package com.raisin.backend.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FixtureObject {
  private String id;
  private String kind;
}
