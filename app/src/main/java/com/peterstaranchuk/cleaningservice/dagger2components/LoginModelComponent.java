package com.peterstaranchuk.cleaningservice.dagger2components;

import com.peterstaranchuk.cleaningservice.activities.LoginActivity;
import com.peterstaranchuk.cleaningservice.dagger2modules.LoginCallbackModule;
import com.peterstaranchuk.cleaningservice.dagger2modules.LoginScreenModule;
import com.peterstaranchuk.cleaningservice.model.LoginScreenModel;

import dagger.Component;

/**
 * Created by Peter Staranchuk.
 */

@Component(modules = LoginCallbackModule.class)
public interface LoginModelComponent {
    public void inject(LoginScreenModel model);

    public class Injector {
        public static void inject(LoginActivity activity) {


            DaggerLoginScreenComponent.builder()
                    .loginScreenModule(new LoginScreenModule(activity))
                    .build()
                    .inject(activity);
        }
    }
}
