/**
 * 
 */
package testapplication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public class CallEnterpriseTestApplicationTest {
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(CallEnterpriseTestApplicationTest.class);

    public static void main(String[] args) {
        new CallEnterpriseTestApplicationTest().doGet();
    }

    // http://172.16.88.141:8080/test/hello
    // http://172.16.88.141:8230/test/hello

    public void doGet() {
        int numThreads = 10;
        String[] urls = { "http://172.16.88.141:8080/test/hello",
                "http://172.16.88.141:8230/test/hello" };
        ExecutorService executorService = Executors
                .newFixedThreadPool(numThreads);
        for (int i = 0; i < numThreads; i++) {
            executorService.execute(new Getter(i, urls[i % 2]));
        }
        executorService.shutdown();
    }

    // private void post() {
    // HttpPost httpPost = new HttpPost("http://targethost/login");
    // List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    // nvps.add(new BasicNameValuePair("username", "vip"));
    // nvps.add(new BasicNameValuePair("password", "secret"));
    // try {
    // httpPost.setEntity(new UrlEncodedFormEntity(nvps));
    // } catch (UnsupportedEncodingException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // CloseableHttpResponse response = null;
    // try {
    // response = httpclient.execute(httpPost);
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    //
    // try {
    // System.out.println(response.getStatusLine());
    // HttpEntity entity2 = response.getEntity();
    // // do something useful with the response body
    // // and ensure it is fully consumed
    // EntityUtils.consume(entity2);
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // } finally {
    // response.close();
    // }
    // }

}
