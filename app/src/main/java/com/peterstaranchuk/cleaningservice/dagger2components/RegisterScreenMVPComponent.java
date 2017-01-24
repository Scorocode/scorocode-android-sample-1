package com.peterstaranchuk.cleaningservice.dagger2components;

import com.peterstaranchuk.cleaningservice.activities.RegisterActivity;
import com.peterstaranchuk.cleaningservice.dagger2modules.RegisterScreenMVPModule;
import com.peterstaranchuk.cleaningservice.presenter.RegisterScreenPresenter;

import dagger.Component;

/**
 * Created by Peter Staranchuk.
 */

@Component(modules = RegisterScreenMVPModule.class)
public interface RegisterScreenMVPComponent {

    void inject(RegisterActivity view);

    RegisterScreenPresenter presenter();

    class Injector {
        public static void inject(RegisterActivity view) {
            DaggerRegisterScreenMVPComponent.builder()
                    .registerScreenMVPModule(new RegisterScreenMVPModule(view))
                    .build()
                    .inject(view);
        }
    }
}
