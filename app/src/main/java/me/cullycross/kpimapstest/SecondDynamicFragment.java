package me.cullycross.kpimapstest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;

/**
 * Created by: Anton Shkurenko (cullycross)
 * Project: KPImapsTest
 * Date: 11/3/15
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public class SecondDynamicFragment extends DynamicFragment {

  public static SecondDynamicFragment newInstance(String str) {
    final SecondDynamicFragment frag = new SecondDynamicFragment();
    final Bundle args = new Bundle();
    args.putString(ARGS_STRING, str);
    frag.setArguments(args);
    return frag;
  }

  @Override public void onPush() {

  }

  @Override public void onPop() {

  }

  @Override public String getText() {
    return null;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_second_layout, null);
    ((TextView) view).setText(getArguments().getString(ARGS_STRING, "Use factory method!"));
    return view;
  }
}
