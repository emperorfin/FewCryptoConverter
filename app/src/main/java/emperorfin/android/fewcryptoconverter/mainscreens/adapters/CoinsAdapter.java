package emperorfin.android.fewcryptoconverter.mainscreens.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import emperorfin.android.fewcryptoconverter.R;
import emperorfin.android.fewcryptoconverter.mainscreens.models.CoinModel;

//import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.LayoutRes;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.ViewHolder> {
    private final List<CoinModel> mCoinsDetails;
    private final Context mContext;
    @LayoutRes
    private final int mLayoutResourceId;

    public CoinsAdapter(Context context, List<CoinModel> coinsDetails, @LayoutRes int layoutResourceId) {
        super();
        mCoinsDetails = coinsDetails;
        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    //@NotNull
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutResourceId, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CoinModel cardDetails = mCoinsDetails.get(position);
        holder.setupListeners(position, cardDetails);
        holder.bindDataToWidgets(position, cardDetails);
    }

    @Override
    public int getItemCount() {
        return mCoinsDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parentLayout;

        private ImageView ivCoinIcon;

        private TextView tvCoinName;
        private TextView tvCoinPrice;
        private TextView tvCoinChange;

        private LineChart lineChartTopCoins;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.rootLayout);

            ivCoinIcon = itemView.findViewById(R.id.ivNewsIcon);

            tvCoinName = itemView.findViewById(R.id.tvName);
            tvCoinPrice = itemView.findViewById(R.id.tvPrice);
            tvCoinChange = itemView.findViewById(R.id.tvChange);

            lineChartTopCoins = itemView.findViewById(R.id.line_chart);
        }

        public void bindDataToWidgets(int position, CoinModel coinDetails){
            Glide.with(mContext)
                    .asBitmap()
                    .load(coinDetails.getCoinIcon())
                    .into(ivCoinIcon);

            tvCoinName.setText(coinDetails.getCoinName());
            tvCoinPrice.setText(coinDetails.getCoinPrice());

            if(coinDetails.isHasGained()){
                tvCoinChange.setTextColor(mContext.getResources().getColor(R.color.green_700));
//                tvCoinChange.setCompoundDrawablesWithIntrinsicBounds(R.drawable.widget_coin_price_change_up_bg, 0, 0, 0);
                tvCoinChange.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_drop_up_24, 0, 0, 0);
            }else{
                tvCoinChange.setTextColor(mContext.getResources().getColor(R.color.red_700));
                tvCoinChange.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_arrow_drop_down_24, 0, 0, 0);
            }

            tvCoinChange.setText(coinDetails.getCoinChange());

            {
                lineChartTopCoins.setData(coinDetails.getCoinChartData());
                //TODO: Uncomment this
                //lineChartTopCoins.setVisibility(View.VISIBLE);
                lineChartTopCoins.setBackgroundColor(Color.YELLOW);
                lineChartTopCoins.getDescription().setEnabled(false);

                XAxis xAxis;
                {
                    xAxis = lineChartTopCoins.getXAxis();
                    xAxis.enableAxisLineDashedLine(10f, 10f, 0f);
                }

                YAxis yAxis;
                {
                    yAxis = lineChartTopCoins.getAxisLeft();
                    lineChartTopCoins.getAxisRight().setEnabled(false);
                    yAxis.enableGridDashedLine(10f, 10f, 0f);
                    yAxis.setAxisMaximum(100f);
                    yAxis.setAxisMinimum(-50f);
                }
            }
        }

        public void setupListeners(int position, CoinModel coinDetails){
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, coinDetails.getCoinName() + " coin details coming soon.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
