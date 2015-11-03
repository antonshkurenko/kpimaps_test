package me.cullycross.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import me.cullycross.kpimapstest.DynamicFragment;

/**
 * Created by: Anton Shkurenko (cullycross)
 * Project: KPImapsTest
 * Date: 11/3/15
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public class FirstDynamicFragment extends DynamicFragment {

  private static final String TAG = FirstDynamicFragment.class.getSimpleName();

  private String mString;

  public static FirstDynamicFragment newInstance(String str) {
    final FirstDynamicFragment frag = new FirstDynamicFragment();
    final Bundle args = new Bundle();
    args.putString(MainActivity.ARGS_STRING, str);
    frag.setArguments(args);
    return frag;
  }

  @Override public void onPush() {
    Log.d(TAG, "::onPush, text is " + mString);
    Toast.makeText(getContext(), "onPush() with " + mString, Toast.LENGTH_SHORT).show();
  }

  @Override public void onPop() {
    Log.d(TAG, "::onPop, text is " + mString);
    Toast.makeText(getContext(), "onPop() with " + mString, Toast.LENGTH_SHORT).show();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_first_layout, null);
    mString = getArguments().getString(MainActivity.ARGS_STRING, "Use factory method!");
    ((TextView) view).setText(mString);
    return view;
  }
}
