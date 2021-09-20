package emperorfin.android.fewcryptoconverter.mainscreens.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import emperorfin.android.fewcryptoconverter.R;
import emperorfin.android.fewcryptoconverter.mainscreens.models.NewsModel;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    private final List<NewsModel> mNewsDetails;
    private final Context mContext;

    public NewsAdapter(Context context, List<NewsModel> newsDetails) {
        super();
        mNewsDetails = newsDetails;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_news_section, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsModel newsDetails = mNewsDetails.get(position);
        holder.setupListeners(position, newsDetails);
        holder.bindDataToWidgets(position, newsDetails);
    }

    @Override
    public int getItemCount() {
        return mNewsDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout parentLayout;

        private ImageView ivNewsIcon;

        private TextView tvNewsTitle;
        private TextView tvNewsAuthor;
        private TextView tvNewsPublishTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.rootLayout);

            ivNewsIcon = itemView.findViewById(R.id.ivNewsIcon);

            tvNewsTitle = itemView.findViewById(R.id.tvNewsTitle);
            tvNewsAuthor = itemView.findViewById(R.id.tvAuthor);
            tvNewsPublishTime = itemView.findViewById(R.id.tvPublishTime);
        }

        public void bindDataToWidgets(int position, NewsModel newsDetails){
            Glide.with(mContext)
                    .asBitmap()
//                    .load(newsDetails.getIconDrawable())
                    .load(newsDetails.getIconUrl())
                    .into(ivNewsIcon);

            tvNewsTitle.setText(newsDetails.getTitle());
            tvNewsAuthor.setText(newsDetails.getAuthor());
            tvNewsPublishTime.setText(newsDetails.getPublishTime());
        }

        public void setupListeners(int position, NewsModel newsDetails){
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(newsDetails.getNewsUrl()));
                    mContext.startActivity(i);
                }
            });
        }
    }
}
