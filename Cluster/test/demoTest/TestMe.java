package demoTest;

import com.google.inject.Inject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.api.Environment;
import play.api.inject.guice.GuiceApplicationBuilder;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.test.*;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static play.test.Helpers.*;
/**
 * Created by prasad on 22/10/16.
 */
public class TestMe extends WithServer{



    private WSClient ws = FakeAppLoader.provideWS();


    @Test
    public void testMe() throws ExecutionException, InterruptedException {
        int port = play.api.test.Helpers.testServerPort();
        System.out.println("----------------------------");
        System.out.println(ws);
        //System.out.println(ws);//.url("http://localhost:"+port+"/").get().toCompletableFuture().get().getStatus());
        System.out.println(ws.url("http://localhost:"+port).get().toCompletableFuture().get().getStatus());
    }

}
