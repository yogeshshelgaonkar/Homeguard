package android.example.homeguard;

public class Devices {
    public String camName;
    public String ip;

    public Devices(String camName, String ip) {
        this.camName = camName;
        this.ip = ip;
    }

    public Devices() {
    }

    public String getCamName() {
        return camName;
    }

    public void setCamName(String camName) {
        this.camName = camName;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
