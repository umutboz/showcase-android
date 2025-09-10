package com.agesa.showcase.app;

import androidx.annotation.Nullable;

import java.io.Serializable;

final class Discount implements Serializable {

  @Nullable final String title;
  @Nullable final String description;

  public Discount(@Nullable String title, @Nullable String description) {
    this.title = title;
    this.description = description;
  }
}
