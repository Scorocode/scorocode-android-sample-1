package com.peterstaranchuk.cleaningservice.dagger2components;

import com.peterstaranchuk.cleaningservice.activities.OrderActivity;
import com.peterstaranchuk.cleaningservice.dagger2modules.OrderScreenActionModule;

import dagger.Component;

/**
 * Created by Peter Staranchuk.
 */

@Component(modules = OrderScreenActionModule.class)
public interface OrderScreenActionsComponent {

    void inject(OrderActivity orderActivity);

    class Injector {
        public static void inject(OrderActivity activity) {
            DaggerOrderScreenActionsComponent.builder()
                    .orderScreenActionModule(new OrderScreenActionModule(activity))
                    .build()
                    .inject(activity);
        }
    }
}
