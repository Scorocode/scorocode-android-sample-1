package com.peterstaranchuk.cleaningservice.dagger2modules;

import com.peterstaranchuk.cleaningservice.model.RegisterScreenModel;
import com.peterstaranchuk.cleaningservice.presenter.RegisterScreenPresenter;
import com.peterstaranchuk.cleaningservice.view.RegisterScreenView;

import dagger.Module;
import dagger.Provides;
import rx.functions.Action1;

/**
 * Created by Peter Staranchuk.
 */

@Module
public class RegisterScreenMVPModule {
    private RegisterScreenView view;

    public RegisterScreenMVPModule(RegisterScreenView view) {
        this.view = view;
    }

    @Provides
    public RegisterScreenPresenter presenter() {
        return new RegisterScreenPresenter(view, new RegisterScreenModel(view.getContext()));
    }

    @Provides
    public Action1<CharSequence> inputCheckCallback(RegisterScreenPresenter presenter) {
        return presenter.getInputCheckCallback();
    }

}
