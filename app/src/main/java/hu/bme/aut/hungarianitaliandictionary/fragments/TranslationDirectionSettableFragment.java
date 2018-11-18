package hu.bme.aut.hungarianitaliandictionary.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;

import hu.bme.aut.hungarianitaliandictionary.R;
import hu.bme.aut.hungarianitaliandictionary.data.TranslationDirection;

import static hu.bme.aut.hungarianitaliandictionary.data.TranslationDirection.*;

public abstract class TranslationDirectionSettableFragment extends Fragment {

    private AppCompatImageButton translationDirectionSwitchButton;
    protected TranslationDirection selectedTranslationDirection = HUNGARIAN_TO_ITALIAN;


    private void onTranslationDirectionSwitchButtonPressed() {
        if (selectedTranslationDirection == ITALIAN_TO_HUNGARIAN) {
            flipTranslationDirectionSwitchButton(false);
            selectedTranslationDirection = HUNGARIAN_TO_ITALIAN;
        } else {
            flipTranslationDirectionSwitchButton(true);
            selectedTranslationDirection = ITALIAN_TO_HUNGARIAN;
        }
    }

    private void flipTranslationDirectionSwitchButton(boolean right) {
        translationDirectionSwitchButton.setScaleX(right ? -1 : 1);
    }

    protected void initTranslationDirectionSwitchButton(View rootView) {
        translationDirectionSwitchButton = rootView.findViewById(R.id.translationDirectionSwitchButton);

        translationDirectionSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTranslationDirectionSwitchButtonPressed();
            }
        });
    }
}
