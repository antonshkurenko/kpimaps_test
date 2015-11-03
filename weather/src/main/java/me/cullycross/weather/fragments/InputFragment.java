package me.cullycross.weather.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cullycross.kpimapstest.DynamicFragment;
import me.cullycross.weather.R;

/**
 * Created by: Anton Shkurenko (cullycross)
 * Project: KPImapsTest
 * Date: 11/3/15
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public class InputFragment extends DynamicFragment {

  private static final String TAG = InputFragment.class.getSimpleName();

  @Bind(R.id.latitude) EditText mLatitude;
  @Bind(R.id.longtitude) EditText mLongtitude;

  private OnFragmentInteractionListener mInteractionListener;

  public static InputFragment newInstance() {
    final InputFragment frag = new InputFragment();
    final Bundle args = new Bundle();

    frag.setArguments(args);
    return frag;
  }

  @Override public void onPush() {
    Log.d(TAG, "::onPush");
  }

  @Override public void onPop() {
    Log.d(TAG, "::onPop");
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_input, null);

    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void onAttach(Context activity) {
    super.onAttach(activity);
    try {
      mInteractionListener = (OnFragmentInteractionListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(
          activity.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override public void onDetach() {
    super.onDetach();
    mInteractionListener = null;
  }

  @OnClick(R.id.search) void search() {
    mInteractionListener.onSearchButtonClick(Double.parseDouble(mLatitude.getText().toString()),
        Double.parseDouble(mLongtitude.getText().toString()));
  }

  public interface OnFragmentInteractionListener {
    void onSearchButtonClick(double latitude, double longitude);
  }
}
