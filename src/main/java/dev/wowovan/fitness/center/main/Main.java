package dev.wowovan.fitness.center.main;

import io.quarkus.runtime.StartupEvent;
import kong.unirest.Unirest;

import javax.enterprise.event.Observes;

public class Main {
    void onStart(@Observes StartupEvent ev) throws Exception {
        // untuk taruh param / init yang harus di jalankan sewaktu app jalan
    }
}
//