package me.cullycross.weather.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import me.cullycross.kpimapstest.DynamicFragment;
import me.cullycross.weather.R;
import me.cullycross.weather.network.RestClient;
import me.cullycross.weather.pojos.Day;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static me.cullycross.weather.utils.TemperatureUtils.ftoC;

/**
 * Created by: Anton Shkurenko (cullycross)
 * Project: KPImapsTest
 * Date: 11/3/15
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public class OutputFragment extends DynamicFragment {
  private static final String TAG = InputFragment.class.getSimpleName();

  @Bind(R.id.result_recycler_view) RecyclerView mResultRecyclerView;

  private static final String ARGS_LATITUDE = "don_t_you_wanna_know_how_we_keep_starting_fires";
  private static final String ARGS_LONGITUDE = "it_s_my_desire_it_s_my_desire";

  private DayAdapter mAdapter;
  private ProgressDialog mProgressDialog;
  private RestClient.WeatherApi mWeatherApi;

  public static OutputFragment newInstance(double lat, double lng) {
    final OutputFragment frag = new OutputFragment();
    final Bundle args = new Bundle();

    args.putDouble(ARGS_LATITUDE, lat);
    args.putDouble(ARGS_LONGITUDE, lng);

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
    final View view = inflater.inflate(R.layout.fragment_output, null);
    ButterKnife.bind(this, view);
    initRecycler();

    mWeatherApi = new RestClient().createWeatherApi();
    return view;
  }

  @Override public void onResume() {
    super.onResume();

    loadData();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  private void initRecycler() {
    final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mResultRecyclerView.setLayoutManager(layoutManager);
    mResultRecyclerView.setHasFixedSize(true);
    mAdapter = new DayAdapter();
    mResultRecyclerView.setAdapter(mAdapter);
    mResultRecyclerView.setItemAnimator(new DefaultItemAnimator());
  }

  private void loadData() {

    final Bundle args = getArguments();

    if (args != null) {
      double lat = args.getDouble(ARGS_LATITUDE);
      double lng = args.getDouble(ARGS_LONGITUDE);

      mProgressDialog = ProgressDialog.show(getContext(), "Wait...", "Loading results");

      mWeatherApi.getWeather(lat, lng)
          .subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread())
          .flatMap(Observable::from)
          .doOnError(t -> Toast.makeText(getContext(),
              t.getMessage() != null ? t.getMessage() : "Something wrong happened",
              Toast.LENGTH_SHORT).show())
          .onErrorResumeNext(Observable.empty())
          .finallyDo(mProgressDialog::dismiss)
          .subscribe(mAdapter::add);
    }
  }

  public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayItem> {

    private final String TAG = DayAdapter.class.getCanonicalName();

    private List<Day> mData;
    private final SimpleDateFormat WEEKDAY_FORMAT =
        new SimpleDateFormat("EEEE", Locale.getDefault());
    private final SimpleDateFormat DATE_FORMAT =
        new SimpleDateFormat("MMMM dd yyyy", Locale.getDefault());

    public DayAdapter() {
      super();
      mData = new ArrayList<>();
    }

    public void add(Day object) {
      mData.add(object);
      notifyItemInserted(mData.indexOf(object));
    }

    public void addAll(Collection<? extends Day> collection) {
      mData.addAll(collection);
      notifyItemRangeInserted(mData.indexOf(collection.iterator().next()), mData.size());
    }

    @Override public DayItem onCreateViewHolder(ViewGroup viewGroup, int viewType) {

      View v = LayoutInflater.from(viewGroup.getContext())
          .inflate(R.layout.day_list_item_layout, viewGroup, false);

      return new DayItem(v);
    }

    @Override public void onBindViewHolder(DayItem holder, final int position) {
      final Day day = mData.get(position);
      Log.wtf(TAG, day.getDate() + "");
      final Date date = new Date(day.getDate());

      holder.mWeekday.setText(WEEKDAY_FORMAT.format(date));
      holder.mDate.setText(DATE_FORMAT.format(date));
      holder.mTemperature.setText(
          String.format(getContext().getString(R.string.temperature), ftoC(day.getMin()),
              ftoC(day.getMax())));
    }

    @Override public int getItemCount() {
      return mData.size();
    }

    public class DayItem extends RecyclerView.ViewHolder {

      @Bind(R.id.weekday) TextView mWeekday;
      @Bind(R.id.date) TextView mDate;
      @Bind(R.id.temperature) TextView mTemperature;

      public DayItem(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
      }
    }
  }
}
