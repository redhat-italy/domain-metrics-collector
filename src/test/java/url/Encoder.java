package url;

import java.net.URLEncoder;

public class Encoder {

    public static void main(String[] args) {
        // http://localhost:7779/dmc/configuration?configuration=
        System.out.println(URLEncoder.encode("{\"metricType\":\"APP\",\"id\":\"ISATEST\",\"exportDestinationType\":\"FILE\",\"exportFormatType\":\"CSV\",\"hostname\":\"10.100.12.19\",\"port\":9999,\"username\":\"isaadmin\",\"password\":\"isa@dm1n\",\"realm\":null,\"regexpHostname\":\".+\",\"regexpServer\":\".+\",\"scanInterval\":30,\"start\":true,\"apps\":[\"ebaasservice-1.8.4.ear\"],\"regexpSubdeployment\":\".+\\.jar\",\"subsystem\":\"ejb3\",\"regexpSubsystemComponent\":\".+-bean\",\"regexpAppObjectName\":\"\\w+\"}"));
    }

}
