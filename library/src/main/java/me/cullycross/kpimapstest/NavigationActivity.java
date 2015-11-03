package me.cullycross.kpimapstest;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public abstract class NavigationActivity extends AppCompatActivity {

  private static final String TAG = NavigationActivity.class.getSimpleName();

  protected List<DynamicFragment> mFragments = new ArrayList<>();

  protected abstract void showFragment(Fragment fragment);

  @Override public void onBackPressed() {
    if (!pop()) {
      super.onBackPressed();
    }
  }

  protected void push(DynamicFragment fragment) {
    mFragments.add(fragment);
    showFragment(fragment);
    fragment.onPush();
  }

  protected boolean pop() {
    if (mFragments.size() > 0) {

      final DynamicFragment fragment = mFragments.get(mFragments.size() - 1);
      fragment.onPop();
      mFragments.remove(fragment);
      try {
        showFragment(mFragments.get(mFragments.size() - 1));
      } catch (IndexOutOfBoundsException e) {
        Log.w(TAG, "There's nothing to show");
        clear();
      }
      return true;
    } else {
      Log.d(TAG, "Stack is empty");
      return false;
    }
  }

  protected void clear() {
    showFragment(new Fragment());
  }
}
