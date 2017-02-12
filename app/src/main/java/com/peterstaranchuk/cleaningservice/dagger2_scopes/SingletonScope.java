package com.peterstaranchuk.cleaningservice.dagger2_scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Peter Staranchuk.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface SingletonScope {

}