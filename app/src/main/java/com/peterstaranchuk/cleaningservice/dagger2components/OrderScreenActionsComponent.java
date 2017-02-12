package com.peterstaranchuk.cleaningservice.dagger2components;

import com.peterstaranchuk.cleaningservice.activities.OrderActivity;
import com.peterstaranchuk.cleaningservice.dagger2_scopes.SingletonScope;
import com.peterstaranchuk.cleaningservice.dagger2modules.OrderScreenActionModule;

import dagger.Component;

/**
 * Created by Peter Staranchuk.
 */

@SingletonScope
@Component(modules = OrderScreenActionModule.class)
public interface OrderScreenActionsComponent {

    public void inject(OrderActivity orderActivity);

    class Injector {
        public static void inject(OrderActivity activity) {
            DaggerOrderScreenActionsComponent.builder()
                    .orderScreenActionModule(new OrderScreenActionModule(activity))
                    .build()
                    .inject(activity);
        }
    }
}
