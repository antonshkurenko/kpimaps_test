package me.cullycross.kpimapstest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {

  private static final String TAG = NavigationActivity.class.getSimpleName();

  @Bind(R.id.fragment_text) TextView mFragmentText;
  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.count) TextView mCount;

  Spinner mSpinner;

  private static final String FIRST = "First";
  private static final String SECOND = "Second";
  private static final String THIRD = "Third";

  private List<DynamicFragment> mFragments = new ArrayList<>();
  private SpinnerAdapter mAdapter = new SpinnerAdapter();

  @Override public void onBackPressed() {
    if (!pop()) {
      super.onBackPressed();
    }
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_navigation);
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
    fragment.onPush();
    mFragments.add(fragment);
    showFragment(fragment);

    mCount.setText(mFragments.size() + "");
  }

  @OnClick(R.id.pop_button) void popClick() {
    pop();
  }

  private boolean pop() {
    if (mFragments.size() > 0) {

      final DynamicFragment fragment = mFragments.get(mFragments.size() - 1);
      fragment.onPop();
      mFragments.remove(fragment);
      try {
        showFragment(mFragments.get(mFragments.size() - 1));
      } catch (IndexOutOfBoundsException e) {
        Toast.makeText(this, "There's nothing to show", Toast.LENGTH_SHORT).show();
        clear();
      }

      mCount.setText(mFragments.size() + "");
      return true;
    } else {
      Toast.makeText(this, "Stack is empty", Toast.LENGTH_SHORT).show();
      return false;
    }
  }

  private void showFragment(Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .replace(R.id.fragment_layout, fragment)
        .commit();
  }

  private void clear() {
    showFragment(new Fragment());
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
