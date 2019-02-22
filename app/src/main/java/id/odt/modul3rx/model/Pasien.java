package id.odt.modul3rx.model;

public class Pasien {
    public String nama;
    public String pekerjaan;
    public String jk;

    public Pasien() {
    }

    public Pasien(String nama, String pekerjaan, String jk) {
        this.nama = nama;
        this.pekerjaan = pekerjaan;
        this.jk = jk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }
}
