package android.example.homeguard;

public class urlGetter {

    private String url;
    private static urlGetter instance = null;

    private urlGetter(String NameAndIP) {

        String[] parts = NameAndIP.split(" : ");
        this.url = "http://" + parts[1] + ":8082/";;
    }

    public static urlGetter getInstance(String nameAndIP){
        if (instance == null){
            instance = new urlGetter(nameAndIP);
        }
        return instance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String ip) {
        this.url = "http://" + ip + ":8082/";
    }
}
