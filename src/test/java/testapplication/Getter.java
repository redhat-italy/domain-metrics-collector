/**
 * 
 */
package testapplication;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrea Battaglia (Red Hat)
 *
 */
public class Getter implements Runnable {
    /**
     * Logger for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(Getter.class);

    private final int id;
    private final String url;

    public Getter(final int id, final String url) {
        super();
        this.id = id;
        this.url = url;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        // http://172.16.88.141:8080/test/hello
        // http://172.16.88.141:8230/test/hello
        CloseableHttpResponse response = null;
        CloseableHttpClient httpclient = null;
        HttpGet httpGet = null;
        try {
            httpclient = HttpClients.createDefault();
            httpGet = new HttpGet(url);
            for (int i = 0; i < 10; i++) {
                try {
                    response = httpclient.execute(httpGet);
                    /*
                     * The underlying HTTP connection is still held by the
                     * response object to allow the response content to be
                     * streamed directly from the network socket. In order to
                     * ensure correct deallocation of system resources the user
                     * MUST call CloseableHttpResponse#close() from a finally
                     * clause. Please note that if response content is not fully
                     * consumed the underlying connection cannot be safely
                     * re-used and will be shut down and discarded by the
                     * connection manager.
                     */
                    try {
                        System.out.println(response.getStatusLine());
                        HttpEntity entity = response.getEntity();
                        // do something useful with the response body
                        // and ensure it is fully consumed
//                        EntityUtils.consume(entity);
                        System.out.println("threadId: " + id + " - counter: "
                                + i);
                    } finally {
                        try {
                            response.close();
                        } catch (IOException e) {
                            LOG.error("", e);
                        }
                    }
                } catch (IOException e) {
                    LOG.error("", e);
                }

            }
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                LOG.error("", e);
            }
        }

    }

}
