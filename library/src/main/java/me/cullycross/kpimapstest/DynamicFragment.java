package me.cullycross.kpimapstest;

import android.support.v4.app.Fragment;

/**
 * Created by: Anton Shkurenko (cullycross)
 * Project: KPImapsTest
 * Date: 11/3/15
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public abstract class DynamicFragment extends Fragment {

  public static final String ARGS_STRING = "the_grass_was_greener"; // the light was brighter

  public abstract void onPush();

  public abstract void onPop();
}
