package me.cullycross.kpimapstest;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public abstract class NavigationActivity extends AppCompatActivity {

  private static final String TAG = NavigationActivity.class.getSimpleName();

  protected List<DynamicFragment> mFragments = new ArrayList<>();

  @IdRes private int mLastAddedId;

  protected void showFragment(@IdRes int id, Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .replace(id, fragment)
        .commit();
    getSupportFragmentManager().executePendingTransactions();
  }

  @Override public void onBackPressed() {
    if (!pop()) {
      super.onBackPressed();
    }
  }

  protected void push(@IdRes int id, DynamicFragment fragment) {
    mFragments.add(fragment);
    showFragment(id, fragment);
    fragment.onPush();
    mLastAddedId = id;
  }

  protected boolean pop() {
    if (mFragments.size() > 0) {

      final DynamicFragment fragment = mFragments.get(mFragments.size() - 1);
      fragment.onPop();
      mFragments.remove(fragment);
      try {
        showFragment(mLastAddedId, mFragments.get(mFragments.size() - 1));
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
    showFragment(mLastAddedId, new Fragment());
  }
}
