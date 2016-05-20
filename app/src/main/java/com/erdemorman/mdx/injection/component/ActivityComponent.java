package com.erdemorman.mdx.injection.component;

import com.erdemorman.mdx.injection.PerActivity;
import com.erdemorman.mdx.injection.module.ActivityModule;
import com.erdemorman.mdx.ui.icons.IconsFragment;
import com.erdemorman.mdx.ui.main.MainActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(IconsFragment iconsFragment);
}
