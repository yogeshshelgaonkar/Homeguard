package android.example.homeguard;

import java.util.HashMap;

public class IpHashMap {
    private String ip;
    private String ipAddress;
    private String cameraName;

    IpHashMap(String address) {
        this.ip = "IP";
        this.ipAddress = address;
        this.cameraName = "Unknown";
    }
    IpHashMap(String cameraName,String address) {
        this.ip = "IP";
        this.ipAddress = address;
        this.cameraName = cameraName;
    }

    public String getIpAddress() {return this.ipAddress;}
    public String getCameraName() {return this.cameraName;}
    public String getIp() {return this.ip;}

    public HashMap getMap() {
        HashMap<String,Object> map = new HashMap<>();
        map.put(getCameraName(),getIpAddress());
        return map;
    }


}
