import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import play.inject.ApplicationLifecycle;
import services.ApplicationTimer;
import services.AtomicCounter;
import services.Counter;

import java.time.Clock;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        // Use the system clock as the default implementation of Clock
        bind(Clock.class).toInstance(Clock.systemDefaultZone());
        // Ask Guice to create an instance of ApplicationTimer when the
        // application starts.
        bind(ApplicationTimer.class).asEagerSingleton();
        // Set AtomicCounter as the implementation for Counter.
        bind(Counter.class).to(AtomicCounter.class);
    }

    @Provides
    public Datastore provideDatastore(ApplicationLifecycle lifecycle) {
        String DB_NAME = "userDB";
        MongoClient mongoClient = new MongoClient(Arrays.asList(
              //  new ServerAddress("rs1", 27017),
              //  new ServerAddress("rs2", 27017),
                new ServerAddress("localhost", 27017)));
        Morphia morphia = new Morphia();
        MongoClient finalMongoClient = mongoClient;
        lifecycle.addStopHook(() -> {
            finalMongoClient.close();
            return CompletableFuture.completedFuture(null);
        });
        return morphia.createDatastore(mongoClient, DB_NAME);
    }
}
