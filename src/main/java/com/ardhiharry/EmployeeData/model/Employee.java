package com.ardhiharry.EmployeeData.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee {
  @Id
  @Column(name = "nik", nullable = false, unique = true)
  private long nik;

  @Column( name = "nama_lengkap", nullable = false, length = 100)
  private String namaLengkap;

  @Enumerated(EnumType.STRING)
  @Column(name = "jenis_kelamin")
  private JenisKelamin jenisKelamin;

  @Column(name = "tanggal_lahir")
  @Temporal(TemporalType.DATE)
  private Date tanggalLahir;

  @Column(name = "alamat", columnDefinition = "TEXT")
  private String alamat;

  @Column(name = "negara", length = 50)
  private String negara;

  @Column(name = "createdAt", nullable = false, updatable = false)
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  @Column(name = "updatedAt")
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  public long getNik() {
    return nik;
  }

  public void setNik(long nik) {
    this.nik = nik;
  }

  public String getNamaLengkap() {
    return namaLengkap;
  }

  public void setNamaLengkap(String namaLengkap) {
    this.namaLengkap = namaLengkap;
  }

  public JenisKelamin getJenisKelamin() {
    return jenisKelamin;
  }

  public void setJenisKelamin(JenisKelamin jenisKelamin) {
    this.jenisKelamin = jenisKelamin;
  }

  public Date getTanggalLahir() {
    return tanggalLahir;
  }

  public void setTanggalLahir(Date tanggalLahir) {
    this.tanggalLahir = tanggalLahir;
  }

  public String getAlamat() {
    return alamat;
  }

  public void setAlamat(String alamat) {
    this.alamat = alamat;
  }

  public String getNegara() {
    return negara;
  }

  public void setNegara(String negara) {
    this.negara = negara;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
