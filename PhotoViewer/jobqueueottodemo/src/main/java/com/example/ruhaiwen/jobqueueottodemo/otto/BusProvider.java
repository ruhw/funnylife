package com.example.ruhaiwen.jobqueueottodemo.otto;


import com.squareup.otto.Bus;

/**
 * Created by ruhaiwen on 15-4-2.
 */
public class BusProvider {

    private static final FileManagerBus BUS = new FileManagerBus();


    public static Bus getInstance(){
        return BUS;
    }

}
