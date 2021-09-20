package emperorfin.android.fewcryptoconverter.mainscreens.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.vinay.ticker.lib.TickerView;

import java.util.ArrayList;
import java.util.List;

import emperorfin.android.fewcryptoconverter.R;
import emperorfin.android.fewcryptoconverter.mainscreens.adapters.CoinsAdapter;
import emperorfin.android.fewcryptoconverter.mainscreens.adapters.NewsAdapter;
import emperorfin.android.fewcryptoconverter.mainscreens.models.CoinModel;
import emperorfin.android.fewcryptoconverter.mainscreens.models.NewsModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String FRAGMENT_TAG = MainFragment.class.getCanonicalName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<CoinModel> mDetailsTopCoins = new ArrayList<>();
    private List<CoinModel> mDetailsGainersAndLosers = new ArrayList<>();

    private List<NewsModel> mDetailsNews = new ArrayList<>();

    private CoinsAdapter mAdapterTopCoins, mAdapterGainersAndLosers;

    private NewsAdapter mAdapterNews;

    private View view;

    private TickerView tickerView;

    private RecyclerView rvTopCoins, rvGainersAndLosers, rvNews;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);

        initCoinsDetailsListWithTestDataForTopCoins();

        initCoinsDetailsListWithTestDataForGainersAndLosers();

        initNewsDetailsListWithTestData();

        initWidgets();

        bindDataToWidgets();

        return view;
    }

    private void getFragmentArguments(){
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void initWidgets(){
        rvTopCoins = view.findViewById(R.id.rvTopCoin);
        rvGainersAndLosers = view.findViewById(R.id.rvGainersLosers);
        rvNews = view.findViewById(R.id.rvNews);

//        tickerView = view.findViewById(R.id.tickerView);
        //test
        // Add multiple views to be shown in the ticker
//        for (int index = 0; index < 50; index++) {
//            // childViews
//            TextView tv = new TextView(getActivity());
//            tv.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
//            tv.setText(String.valueOf(index + 1));
////            tv.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.white));
//            tv.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.black));
////            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
////            tv.setPadding(10, 5, 10, 5);
//
//            tickerView.addChildView(tv);
//        }
//        // Call the showTickers() to show them on the screen
//        tickerView.showTickers();
    }

    private void bindDataToWidgets(){
        initRecyclerViewTopCoins();

        initRecyclerViewGainersAndLosers();

        initRecyclerViewNews();
    }

    private void initRecyclerViewTopCoins(){
        mAdapterTopCoins = new CoinsAdapter(getActivity(), mDetailsTopCoins, R.layout.item_home_top_coin_section);

        rvTopCoins.setAdapter(mAdapterTopCoins);
    }

    private void initRecyclerViewGainersAndLosers(){
        mAdapterGainersAndLosers = new CoinsAdapter(getActivity(), mDetailsGainersAndLosers, R.layout.item_home_gainer_loser_section);

        rvGainersAndLosers.setAdapter(mAdapterGainersAndLosers);
    }

    private void initRecyclerViewNews(){
        mAdapterNews = new NewsAdapter(getActivity(), mDetailsNews);

        rvNews.setAdapter(mAdapterNews);
    }

    private void initCoinsDetailsListWithTestDataForTopCoins(){
        List<Entry> listEntryBitcoin = new ArrayList<>();
        Entry entryBitcoin = new Entry(0f, 60f);
        listEntryBitcoin.add(entryBitcoin);

        entryBitcoin = new Entry(1f, 50f);
        listEntryBitcoin.add(entryBitcoin);

        entryBitcoin = new Entry(2f, 40f);
        listEntryBitcoin.add(entryBitcoin);

        entryBitcoin = new Entry(3f, 30f);
        listEntryBitcoin.add(entryBitcoin);

        entryBitcoin = new Entry(4f, 20f);
        listEntryBitcoin.add(entryBitcoin);

        entryBitcoin = new Entry(5f, 10f);
        listEntryBitcoin.add(entryBitcoin);

        LineDataSet lineDataSetBitcoin = new LineDataSet(listEntryBitcoin, "Bitcoin");
        lineDataSetBitcoin.setAxisDependency(YAxis.AxisDependency.LEFT);

        List<ILineDataSet> listILineDataSetBitcoin = new ArrayList<>();
        listILineDataSetBitcoin.add(lineDataSetBitcoin);

        LineData lineDataBitCoin = new LineData(listILineDataSetBitcoin);

        //////////

        CoinModel coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_btc)
                .setCoinName("Bitcoin")
                .setCoinPrice("US$48,028.86")
                .setCoinChange("1.17%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsTopCoins.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_ethereum_eth)
                .setCoinName("Ethereum")
                .setCoinPrice("US$3,406.64")
                .setCoinChange("3.45%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsTopCoins.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_cardano_ada)
                .setCoinName("Cardano")
                .setCoinPrice("US$2.364")
                .setCoinChange("2.111%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsTopCoins.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_tether_usdt)
                .setCoinName("Tether")
                .setCoinPrice("US$0.9989")
                .setCoinChange("0.160%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsTopCoins.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_xrp)
                .setCoinName("XRP")
                .setCoinPrice("US$1.075")
                .setCoinChange("1.103%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsTopCoins.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_solana_sol)
                .setCoinName("Solana")
                .setCoinPrice("US$159.74")
                .setCoinChange("0.38%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsTopCoins.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_polkadot_dot)
                .setCoinName("Polkadot")
                .setCoinPrice("US$33.86")
                .setCoinChange("3.46%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsTopCoins.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_dogecoin_doge)
                .setCoinName("Dogecoin")
                .setCoinPrice("US$0.2394")
                .setCoinChange("2.825%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsTopCoins.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_avalanche)
                .setCoinName("Avalanche")
                .setCoinPrice("US$72.67")
                .setCoinChange("1.44%")
                .setHasGained(true)
                .setCoinChartData(lineDataBitCoin);

        mDetailsTopCoins.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_terra)
                .setCoinName("Terra")
                .setCoinPrice("US$35.39")
                .setCoinChange("0.99%")
                .setHasGained(true)
                .setCoinChartData(lineDataBitCoin);

        mDetailsTopCoins.add(coinDetails);
    }

    private void initCoinsDetailsListWithTestDataForGainersAndLosers(){
        List<Entry> listEntryBitcoin = new ArrayList<>();
        Entry entryBitcoin = new Entry(0f, 5000f);
        listEntryBitcoin.add(entryBitcoin);

        entryBitcoin = new Entry(1f, 4000f);
        listEntryBitcoin.add(entryBitcoin);

        entryBitcoin = new Entry(2f, 3000f);
        listEntryBitcoin.add(entryBitcoin);

        entryBitcoin = new Entry(3f, 2000f);
        listEntryBitcoin.add(entryBitcoin);

        entryBitcoin = new Entry(4f, 1000f);
        listEntryBitcoin.add(entryBitcoin);

        entryBitcoin = new Entry(5f, 0f);
        listEntryBitcoin.add(entryBitcoin);

        LineDataSet lineDataSetBitcoin = new LineDataSet(listEntryBitcoin, "Bitcoin");
        lineDataSetBitcoin.setAxisDependency(YAxis.AxisDependency.LEFT);

        List<ILineDataSet> listILineDataSetBitcoin = new ArrayList<>();
        listILineDataSetBitcoin.add(lineDataSetBitcoin);

        LineData lineDataBitCoin = new LineData(listILineDataSetBitcoin);

        //////////

        CoinModel coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_omg_network)
                .setCoinName("OMG Network")
                .setCoinPrice("US$9.699")
                .setCoinChange("12.978%")
                .setHasGained(true)
                .setCoinChartData(lineDataBitCoin);

        mDetailsGainersAndLosers.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_cosmos_atom)
                .setCoinName("Cosmos")
                .setCoinPrice("US$39.20")
                .setCoinChange("9.57%")
                .setHasGained(true)
                .setCoinChartData(lineDataBitCoin);

        mDetailsGainersAndLosers.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_celo)
                .setCoinName("Celo")
                .setCoinPrice("US$5.585")
                .setCoinChange("7.66%")
                .setHasGained(true)
                .setCoinChartData(lineDataBitCoin);

        mDetailsGainersAndLosers.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_revain_r)
                .setCoinName("Revain")
                .setCoinPrice("US$0.02159")
                .setCoinChange("6.71%")
                .setHasGained(true)
                .setCoinChartData(lineDataBitCoin);

        mDetailsGainersAndLosers.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_near_protocol_logo_logotyp_us)
                .setCoinName("NEAR Protocol")
                .setCoinPrice("US$9.228")
                .setCoinChange("5.45%")
                .setHasGained(true)
                .setCoinChartData(lineDataBitCoin);

        mDetailsGainersAndLosers.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_thorchain)
                .setCoinName("THORChain")
                .setCoinPrice("US$9.607")
                .setCoinChange("-9.34%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsGainersAndLosers.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_synthetix_snx)
                .setCoinName("Synthetix")
                .setCoinPrice("US$12.54")
                .setCoinChange("-7.34%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsGainersAndLosers.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_ren)
                .setCoinName("Ren")
                .setCoinPrice("US$0.94636")
                .setCoinChange("-6.87%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsGainersAndLosers.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_harmony)
                .setCoinName("Harmoney")
                .setCoinPrice("US$0.1615")
                .setCoinChange("-6.78%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsGainersAndLosers.add(coinDetails);

        coinDetails = new CoinModel();
        coinDetails
                .setCoinIcon(R.drawable.ic_shiba_inu_shib)
                .setCoinName("SHIBA INU")
                .setCoinPrice("US$0.00000752")
                .setCoinChange("-5.66%")
                .setHasGained(false)
                .setCoinChartData(lineDataBitCoin);

        mDetailsGainersAndLosers.add(coinDetails);
    }

    private void initNewsDetailsListWithTestData(){
        NewsModel newsDetails = new NewsModel();
        newsDetails
                .setIconDrawable(R.drawable.icon_home_tab_discover)
                .setTitle("Major New York Real Estate Company Now Accepting BTC as Payment")
                .setAuthor("BelnCrypto")
                .setPublishTime("1h ago")
                .setNewsUrl("https://beincrypto.com/major-new-york-real-estate-company-now-accepting-btc-as-payment/")
                .setIconUrl("https://s32659.pcdn.co/wp-content/uploads/2021/03/BIC_tokenization_real_estate.jpg");

        mDetailsNews.add(newsDetails);

        newsDetails = new NewsModel();
        newsDetails
                .setIconDrawable(R.drawable.icon_home_tab_discover)
                .setTitle("Bitcoin Now Targeting $100,000, Predicts Vetran Trader Tone Vays - Here's His Timeline")
                .setAuthor("The Daily Hodl")
                .setPublishTime("2h ago")
                .setNewsUrl("https://dailyhodl.com/2021/09/19/bitcoin-now-targeting-100000-predicts-veteran-trader-tone-vays-heres-his-timeline/")
                .setIconUrl("https://i0.wp.com/dailyhodl.com/wp-content/uploads/2021/09/bitcoin-institutional-grade-asset.jpg?fit=1365%2C800&ssl=1");

        mDetailsNews.add(newsDetails);

        newsDetails = new NewsModel();
        newsDetails
                .setIconDrawable(R.drawable.icon_home_tab_discover)
                .setTitle("ALGO Enjoys 27-Month High as Skybridge Capital Announces $100M Fund")
                .setAuthor("BelnCrypto")
                .setPublishTime("3h ago")
                .setNewsUrl("https://beincrypto.com/algo-enjoys-27-month-high-as-skybridge-capital-announces-100m-fund/")
                .setIconUrl("https://s32659.pcdn.co/wp-content/uploads/2021/07/bic_artwork_Fundamentals.jpg");

        mDetailsNews.add(newsDetails);

        newsDetails = new NewsModel();
        newsDetails
                .setIconDrawable(R.drawable.icon_home_tab_discover)
                .setTitle("Bitcoin needs to overcome this hurdle to make big strides in coming days")
                .setAuthor("AMBCrypto")
                .setPublishTime("4h ago")
                .setNewsUrl("https://ambcrypto.com/bitcoin-needs-to-overcome-this-hurdle-to-make-big-strides-in-coming-days/")
                .setIconUrl("https://files.ambcrypto.com/wp-content/uploads/2021/09/18154214/cryptocurrency-3123849_1280-2-1024x576.jpg");

        mDetailsNews.add(newsDetails);

        newsDetails = new NewsModel();
        newsDetails
                .setIconDrawable(R.drawable.icon_home_tab_discover)
                .setTitle("Cardano's 'Hydra' Upgrade Aims To Supercharge Blockchain Scalability, Powering Enterprise Use Cases and DeFi")
                .setAuthor("The Daily Hodl")
                .setPublishTime("4h ago")
                .setNewsUrl("https://dailyhodl.com/2021/09/19/cardanos-hydra-upgrade-aims-to-supercharge-blockchain-scalability-powering-enterprise-use-cases-and-defi/")
                .setIconUrl("https://i2.wp.com/dailyhodl.com/wp-content/uploads/2021/05/cardano-target.jpg?fit=1365%2C800&ssl=1");

        mDetailsNews.add(newsDetails);
    }
}