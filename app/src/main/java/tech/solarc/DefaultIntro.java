package tech.solarc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

import tech.solarc.adapter.appliancesFragment;
import tech.solarc.adapter.panelsFragment;

/**
 * Created by NIKHIL on 30-04-2017.
 */

public class DefaultIntro extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new panelsFragment());
        addSlide(new appliancesFragment());

        setProgressButtonEnabled(false);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

    }

    public void b()
    {
        setProgressButtonEnabled(true);
    }
}
