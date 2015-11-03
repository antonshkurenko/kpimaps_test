package me.cullycross.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;
import me.cullycross.kpimapstest.DynamicFragment;
import me.cullycross.kpimapstest.NavigationActivity;

public class MainActivity extends NavigationActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  public static final String ARGS_STRING = "the_grass_was_greener"; // the light was brighter

  protected static final String FIRST = "First";
  protected static final String SECOND = "Second";
  protected static final String THIRD = "Third";

  @Bind(R.id.fragment_text) TextView mFragmentText;
  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.count) TextView mCount;

  Spinner mSpinner;

  private SpinnerAdapter mAdapter = new SpinnerAdapter();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    initSpinner();
    mCount.setText(mFragments.size() + "");
  }

  @OnClick(R.id.push_button) void pushClick() {

    DynamicFragment fragment;
    switch (((String) mSpinner.getSelectedItem())) {
      case FIRST:
        fragment = FirstDynamicFragment.newInstance(mFragmentText.getText().toString());
        break;
      case SECOND:
        fragment = SecondDynamicFragment.newInstance(mFragmentText.getText().toString());
        break;
      case THIRD:
        fragment = ThirdDynamicFragment.newInstance(mFragmentText.getText().toString());
        break;
      default:
        Log.w(TAG, "Smth wrong");
        return;
    }
    push(R.id.fragment_layout, fragment);
    mCount.setText(mFragments.size() + "");
  }

  @OnClick(R.id.pop_button) void popClick() {
    if (pop()) {
      mCount.setText(mFragments.size() + "");
    }
  }

  private void initSpinner() {
    final View spinnerContainer =
        LayoutInflater.from(this).inflate(R.layout.spinner_layout, mToolbar, false);
    final ActionBar.LayoutParams lp =
        new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    mToolbar.addView(spinnerContainer, lp);

    mSpinner = ButterKnife.findById(spinnerContainer, R.id.toolbar_spinner);
    mSpinner.setAdapter(mAdapter);
  }

  private class SpinnerAdapter extends BaseAdapter {

    private List<String> mItems = new ArrayList<String>() {{
      add(FIRST);
      add(SECOND);
      add(THIRD);
    }};

    @Override public int getCount() {
      return mItems.size();
    }

    @Override public String getItem(int position) {
      return mItems.get(position);
    }

    @Override public long getItemId(int position) {
      return position;
    }

    @Override public View getDropDownView(int position, View view, ViewGroup parent) {
      if (view == null || !view.getTag().toString().equals("DROPDOWN")) {
        view = getLayoutInflater().inflate(R.layout.spinner_dropdown_item, parent, false);
        view.setTag("DROPDOWN");
      }

      TextView textView = ButterKnife.findById(view, android.R.id.text1);
      textView.setText(getTitle(position));

      return view;
    }

    @Override public View getView(int position, View view, ViewGroup parent) {
      if (view == null || !view.getTag().toString().equals("NON_DROPDOWN")) {
        view = getLayoutInflater().inflate(R.layout.
            spinner_item_toolbar, parent, false);
        view.setTag("NON_DROPDOWN");
      }

      TextView textView = ButterKnife.findById(view, android.R.id.text1);
      textView.setText(getTitle(position));

      return view;
    }

    private String getTitle(int position) {
      return position >= 0 && position < mItems.size() ? mItems.get(position) : "";
    }
  }
}
