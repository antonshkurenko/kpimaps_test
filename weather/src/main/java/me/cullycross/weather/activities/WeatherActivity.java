package me.cullycross.weather.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.cullycross.kpimapstest.NavigationActivity;
import me.cullycross.weather.R;
import me.cullycross.weather.fragments.InputFragment;
import me.cullycross.weather.fragments.InputFragment.OnFragmentInteractionListener;
import me.cullycross.weather.fragments.OutputFragment;

public class WeatherActivity extends NavigationActivity implements OnFragmentInteractionListener {

  @Bind(R.id.toolbar) Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weather);
    ButterKnife.bind(this);

    setSupportActionBar(mToolbar);
    mToolbar.setTitle(R.string.app_name);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      mToolbar.setTitleTextColor(getColor(android.R.color.white));
    } else {
      //noinspection deprecation
      mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    push(R.id.fragment_layout, InputFragment.newInstance());
  }

  @Override public void onSearchButtonClick(double latitude, double longitude) {
    push(R.id.fragment_layout, OutputFragment.newInstance(latitude, longitude));
  }
}
