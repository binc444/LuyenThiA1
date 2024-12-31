package ntu.hieutm.appluyenthia1.models.entities;

public class NguoiDung {
  private String loaiGPLX;
  private String hoTen;
  private String ngaySinh;
  private String soCCCD;
  private String diaChi;

  // Constructors
  public NguoiDung(String loaiGPLX, String hoTen, String ngaySinh, String soCCCD, String diaChi) {
    this.loaiGPLX = loaiGPLX;
    this.hoTen = hoTen;
    this.ngaySinh = ngaySinh;
    this.soCCCD = soCCCD;
    this.diaChi = diaChi;
  }

  // Getters and Setters
  public String getLoaiGPLX() {
    return loaiGPLX;
  }

  public void setLoaiGPLX(String loaiGPLX) {
    this.loaiGPLX = loaiGPLX;
  }

  public String getHoTen() {
    return hoTen;
  }

  public void setHoTen(String hoTen) {
    this.hoTen = hoTen;
  }

  public String getNgaySinh() {
    return ngaySinh;
  }

  public void setNgaySinh(String ngaySinh) {
    this.ngaySinh = ngaySinh;
  }

  public String getSoCCCD() {
    return soCCCD;
  }

  public void setSoCCCD(String soCCCD) {
    this.soCCCD = soCCCD;
  }

  public String getDiaChi() {
    return diaChi;
  }

  public void setDiaChi(String diaChi) {
    this.diaChi = diaChi;
  }
}
