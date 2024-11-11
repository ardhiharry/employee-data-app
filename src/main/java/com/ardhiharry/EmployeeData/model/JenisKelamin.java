package com.ardhiharry.EmployeeData.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum JenisKelamin {
  @JsonProperty("Laki-Laki")
  LAKI_LAKI,

  @JsonProperty("Perempuan")
  PEREMPUAN
}
