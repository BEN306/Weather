package com.example.ben.weather.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ben.weather.R;
import com.example.ben.weather.deo.Wether;
import com.example.ben.weather.fragment.WeatherFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    AppCompatActivity context;
    private List<Wether.ListWether> listWetherList;
    String city;
    public WeatherAdapter(AppCompatActivity context,  List<Wether.ListWether> listWetherList,String city)
    {
        this.context=context;
        this.listWetherList=listWetherList;
        this.city=city;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int textSize6 = context.getResources().getDimensionPixelSize(R.dimen.text_size_5);
        double mhigh = Double.parseDouble(listWetherList.get(position).getMain().getTemp_max());
        SpannableString span1 = new SpannableString(String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize6), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence finalText = TextUtils.concat(span1);
        holder.max.setText(finalText);

        Drawable img = context.getResources().getDrawable(R.drawable.ic_arrow_drop_up_android);
        img.setBounds(0, 0, 60, 60);
        holder.max.setCompoundDrawables(img, null, null, null);

        mhigh = Double.parseDouble(listWetherList.get(position).getMain().getTemp_min());
        span1 = new SpannableString(String.valueOf(Math.round(mhigh)) + (char) 0x00B0 + "C");
        span1.setSpan(new AbsoluteSizeSpan(textSize6), 0, span1.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalText = TextUtils.concat(span1);
        holder.min.setText(finalText);

        img = context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_android);
        img.setBounds(0, 0, 60, 60);
        holder.min.setCompoundDrawables(img, null, null, null);
        holder.time.setText(getDate(listWetherList.get(position).getDt_txt()));

    }

    String  getDate(String dateStr) {
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat postFormater = new SimpleDateFormat("dd, hh:mm a");
        return postFormater.format(dateObj);
    }

    @Override
    public int getItemCount() {
        return listWetherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_max)
        TextView max;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.tv_min)
        TextView min;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = context.getSupportFragmentManager();
                    WeatherFragment editNameDialog = WeatherFragment.getInstance(listWetherList.get(getAdapterPosition()),city);
                    editNameDialog.show(manager, "Weather");
                }
            });
        }
    }
}
