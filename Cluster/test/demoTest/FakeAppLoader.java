package demoTest;
/*
 * Copyright (C) 2009-2016 Lightbend Inc. <https://www.lightbend.com>
 */


import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.ActorMaterializerSettings;
import akka.stream.Materializer;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import play.Application;
import play.libs.ws.WSClient;
import play.libs.ws.ahc.AhcWSClient;
import play.test.Helpers;

import java.util.HashMap;

/**
 * Provides an application for JUnit tests. Make your test class extend this class and an application will be started before each test is invoked.
 * You can setup the application to use by overriding the provideApplication method.
 * Within a test, the running application is available through the app field.
 */
public class FakeAppLoader {

    protected static Application app;

    /*private Application fakeApp = Helpers.fakeApplication();*/

    /*Application fakeAppWithGlobal = fakeApplication(new GlobalSettings() {
        @Override
        public void onStart(Application app) {
            System.out.println("Starting FakeApplication");
        }
    });*/

    //Application fakeAppWithMemoryDb = fakeApplication(inMemoryDatabase("test"));

    /**
     * The application's Akka streams Materializer.
     */
    protected static Materializer mat;

    private static int port;

    protected static Application provideApplication() {

        new new java.io.File("."), Helpers.class.getClassLoader(), new HashMap<String,Object>(), null);
        return Helpers.fakeApplication();
    }

    public static void startPlay() {
        app = provideApplication();
        Helpers.start(app);
        port = play.api.test.Helpers.testServerPort();
        System.out.println("PORT::"+port);
        System.out.println("/////////////////");
        mat = app.getWrappedApplication().materializer();
    }

    public static int getPort(){
        return port;
    }

    public static void stopPlay() {
        if (app != null) {
            Helpers.stop(app);
            app = null;
        }
    }

    public static WSClient provideWS(){
        AsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder()
                .setMaxRequestRetry(0)
                .setShutdownQuietPeriod(0)
                .setShutdownTimeout(0).build();

        String name = "wsclient";
        ActorSystem system = ActorSystem.create(name);
        ActorMaterializerSettings settings = ActorMaterializerSettings.create(system);
        ActorMaterializer materializer = ActorMaterializer.create(settings, system, name);

        WSClient client = new AhcWSClient(config, materializer);
        return client;
    }

}
