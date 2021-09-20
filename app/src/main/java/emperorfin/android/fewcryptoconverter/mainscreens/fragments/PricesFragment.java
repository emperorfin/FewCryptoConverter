package emperorfin.android.fewcryptoconverter.mainscreens.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import emperorfin.android.fewcryptoconverter.R;
import emperorfin.android.fewcryptoconverter.utils.NetworkUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PricesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PricesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String FRAGMENT_TAG = PricesFragment.class.getCanonicalName();

    private static final String URL_BASE_ENDPOINT = "https://api.coingecko.com/api/v3/simple/price";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String mFromCoin = "bitcoin", mToCoin = "eth";

    private double mRate;

    private boolean isConversionRatesFetched = false;
    private boolean isEtFromCoin = false;
    private boolean isEtToCoin = false;
    private boolean isToCoinRateLessThanZero = false;

    private ProgressBar pbFetchingConversionRates;

    private RequestQueue rQueue;

    private View view;

    private TextView tvFromCoin, tvToCoin, tvRateAndConversion;

    private EditText etFromCoin, etToCoin;

    public PricesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PricesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PricesFragment newInstance(String param1, String param2) {
        PricesFragment fragment = new PricesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static PricesFragment newInstance() {
        PricesFragment fragment = new PricesFragment();
        return fragment;
    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(final Context context, final Intent intent) {

            int status = NetworkUtil.getConnectivityStatusString(context);

            if (NetworkUtil.ACTION_CONNECTIVITY_CHANGE.equals(intent.getAction())) {
                if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
//                    Toast.makeText(getContext(), "Network connectivity turned off.", Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(getContext(), "Network connectivity turned on.", Toast.LENGTH_LONG).show();

                    if(!isConversionRatesFetched && isNetworkConnected()){
                        pbFetchingConversionRates.setVisibility(View.VISIBLE);
                        tvRateAndConversion.setText("Fetching conversion rates");

                        lookupCoinConversionRate();
                    }
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_prices, container, false);

        initWidgets();

        setupListeners();

        initOthers();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(networkChangeReceiver);

        super.onStop();
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void getFragmentArguments(){
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void initWidgets(){
        tvFromCoin = view.findViewById(R.id.tv_from_coin);
        tvToCoin = view.findViewById(R.id.tv_to_coin);
        tvRateAndConversion = view.findViewById(R.id.conversionInfoTV);

        etFromCoin = view.findViewById(R.id.et_from_coin);
        etToCoin = view.findViewById(R.id.et_to_coin);

        pbFetchingConversionRates = view.findViewById(R.id.progressBar);
    }

    private void initOthers(){
        if(isNetworkConnected()){
            //No need for these since a BroadcastReceiver that would execute the following would be registered.
//            pbFetchingConversionRates.setVisibility(View.VISIBLE);
//            tvRateAndConversion.setText("Fetching conversion rates");
//            lookupCoinConversionRate();
        }else{
            Toast.makeText(getContext(), "Could not get rates, please check your internet connection and retry.", Toast.LENGTH_LONG).show();
        }
    }

    private void setupListeners(){
        etFromCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etFromCoin.getText().toString().equals("0")){
                    etFromCoin.setText("");
                }

                isEtFromCoin = true;
                isEtToCoin = false;

//                if(etFromCoin.getText().toString().equals("0")){
//                    etFromCoin.setText("");
//
//                    if(etToCoin.getText().toString().isEmpty()){
//                        etToCoin.setText("0");
//                    }
//                }
//
//                isEtFromCoin = true;
//                isEtToCoin = false;
            }
        });

        etToCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etToCoin.getText().toString().equals("0")){
                    etToCoin.setText("");
                }

                isEtToCoin = true;
                isEtFromCoin = false;

//                if(etToCoin.getText().toString().equals("0")){
//                    etToCoin.setText("");
//
//                    if(etFromCoin.getText().toString().isEmpty()){
//                        etFromCoin.setText("0");
//                    }
//                }
//
//                isEtToCoin = true;
//                isEtFromCoin = false;
            }
        });

        etFromCoin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isEtFromCoin = true;
                isEtToCoin = false;

                return false;
            }
        });

        etToCoin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isEtToCoin = true;
                isEtFromCoin = false;

                return false;
            }
        });

        etFromCoin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etFromCoin.getText().toString().equals("0")){
                    etFromCoin.setText("");

//                    if(etToCoin.getText().toString().isEmpty()){
//                        etToCoin.setText("0");
//                    }
                }

                isEtFromCoin = true;
                isEtToCoin = false;
            }
        });

        etToCoin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(etToCoin.getText().toString().equals("0")){
                    etToCoin.setText("");

//                    if(etFromCoin.getText().toString().isEmpty()){
//                        etFromCoin.setText("0");
//                    }
                }

                isEtToCoin = true;
                isEtFromCoin = false;
            }
        });

        etFromCoin.addTextChangedListener(new TextWatcher() {
            boolean ignore = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(!s.toString().trim().isEmpty() && ){
//
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(!isEtToCoin){
//                    return;
//                }

                if(!isEtFromCoin){
                    return;
                }


                if(ignore)
                    return;

                ignore = true;

                etFromCoin.removeTextChangedListener(this);

                if(!etFromCoin.getText().toString().trim().isEmpty()){
                    if(isConversionRatesFetched && !etFromCoin.getText().toString().equals(".")){

                        //rem1
//                        String inputOriginal = s.toString();
//
//                        long inputLong;
//
//                        if(inputOriginal.contains(",")){
//                            inputOriginal = inputOriginal.replaceAll(",", "");
//                        }
//
//                        inputLong = Long.parseLong(inputOriginal);
//
//                        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//                        formatter.applyPattern("#,###,###,###");
//                        String formattedString = formatter.format(inputLong);
//
//                        etFromCoin.setText(formattedString);
//                        etFromCoin.setSelection(etFromCoin.getText().length());

//                        double fundingUsdAmount = Double.parseDouble(etFromCoin.getText().toString().replaceAll(",", ""));
//                        mVirtualCardFundingAmountInUsd = fundingUsdAmount;
//
//                        if(fundingUsdAmount < USD_5){
//                            tvAmountError.setVisibility(View.VISIBLE);
//                            tvRateAndConversion.setVisibility(View.GONE);
//                            tvAmountError.setText("Amount can't be less than $5.00.");
//                        }else if(fundingUsdAmount > USD_10000){
//                            tvAmountError.setVisibility(View.VISIBLE);
//                            tvRateAndConversion.setVisibility(View.GONE);
//                            tvAmountError.setText("Amount can't be more than $10,000.00.");
//                        }else{
//                            tvRateAndConversion.setVisibility(View.VISIBLE);
//                            tvAmountError.setVisibility(View.GONE);
//
//                            mVirtualCardFundingAmountInNgn = mVirtualCardFundingAmountInUsd * mNgnRatesFlexappAndFlutterwave;
//
//                            double fundingNgnAmount = (fundingUsdAmount * mNgnRatesFlexappAndFlutterwave);
//
//                            DecimalFormat formatterFundingNgnAmount = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//                            formatterFundingNgnAmount.applyPattern("#,###,###,###");
//                            String formattedFundingNgnAmountString = formatterFundingNgnAmount.format(fundingNgnAmount);
//
//                            String rateAndAmount = "\u20A6" + formattedFundingNgnAmountString + " at " + "\u20A6" + mNgnRatesFlexappAndFlutterwave + " to $1";
//
//                            tvRateAndConversion.setText(rateAndAmount);

                        //rem2
//                        double conversionAmount = Double.parseDouble(etFromCoin.getText().toString().replaceAll(",", ""));
//
//                        double converted = conversionAmount * mRate;
//
//                        Toast.makeText(getActivity(), "" + converted, Toast.LENGTH_SHORT).show();
//                        etToCoin.setText(String.valueOf(converted));

                        double conversionAmount = Double.parseDouble(etFromCoin.getText().toString());
                        double converted;

                        if(isToCoinRateLessThanZero){
                            converted = conversionAmount / mRate;
                        }else{
                            converted = conversionAmount * mRate;
                        }

                        Toast.makeText(getActivity(), "" + converted, Toast.LENGTH_SHORT).show();
                        etToCoin.setText(String.valueOf(converted));

//                        }
                    }
                }else if(etFromCoin.getText().toString().trim().isEmpty()){
                    etToCoin.setText("0");

                    tvRateAndConversion.setVisibility(View.VISIBLE);
//                    tvAmountError.setVisibility(View.GONE);
                    tvRateAndConversion.setText("");
//                    tvAmountError.setText("");//No need for this since invisible but just in case.
                }

                etFromCoin.addTextChangedListener(this);

                ignore = false;
            }
        });

        etToCoin.addTextChangedListener(new TextWatcher() {
            boolean ignore = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(!s.toString().trim().isEmpty() && ){
//
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                if(!isEtFromCoin){
//                    return;
//                }

                if(!isEtToCoin){
                    return;
                }



                if(ignore)
                    return;

                ignore = true;

                etToCoin.removeTextChangedListener(this);

                if(!etToCoin.getText().toString().trim().isEmpty()){
                    if(isConversionRatesFetched && !etToCoin.getText().toString().equals(".")){

                        //rem1
//                        String inputOriginal = s.toString();
//
//                        long inputLong;
//
//                        if(inputOriginal.contains(",")){
//                            inputOriginal = inputOriginal.replaceAll(",", "");
//                        }
//
//                        inputLong = Long.parseLong(inputOriginal);
//
//                        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//                        formatter.applyPattern("#,###,###,###");
//                        String formattedString = formatter.format(inputLong);
//
//                        etToCoin.setText(formattedString);
//                        etToCoin.setSelection(etToCoin.getText().length());
//
//                        double conversionAmount = Double.parseDouble(etToCoin.getText().toString().replaceAll(",", ""));
//
//                        double converted = conversionAmount * mRate;
//
//                        Toast.makeText(getActivity(), "" + converted, Toast.LENGTH_SHORT).show();

//                        }


                        double conversionAmount = Double.parseDouble(etToCoin.getText().toString());
//                        double converted = conversionAmount / mRate;
                        double converted;

                        if(isToCoinRateLessThanZero){
                            converted = conversionAmount * mRate;
                        }else{
                            converted = conversionAmount / mRate;
                        }

                        Toast.makeText(getActivity(), "" + converted, Toast.LENGTH_SHORT).show();
                        etFromCoin.setText(String.valueOf(converted));
                    }
                }else if(etToCoin.getText().toString().trim().isEmpty()){
                    etFromCoin.setText("0");

                    tvRateAndConversion.setVisibility(View.VISIBLE);
//                    tvAmountError.setVisibility(View.GONE);
                    tvRateAndConversion.setText("");
//                    tvAmountError.setText("");//No need for this since invisible but just in case.
                }

                etToCoin.addTextChangedListener(this);

                ignore = false;
            }
        });
    }

    public void lookupCoinConversionRate(){
        final String URL_ENDPOINT = URL_BASE_ENDPOINT + "?ids=" + mFromCoin + "&vs_currencies=" + mToCoin;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        rQueue.getCache().clear();
                        //Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        try {
                            parseDataForLookedupCoinConversionRate(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
//                        constraintLayConversionGroup.setVisibility(View.GONE);
                        pbFetchingConversionRates.setVisibility(View.GONE);
                        tvRateAndConversion.setText("");

                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
//                params.put("key_bank_account_number", mBankAccountNumber);

                return params;
            }
        };

        //:~
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(stringRequest);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Processing....");
//        progressDialog.setCanceledOnTouchOutside(false);//:~
//        progressDialog.show();
    }

    private void parseDataForLookedupCoinConversionRate(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);

//        Toast.makeText(getActivity(), jsonObject.getString("recovered"), Toast.LENGTH_SHORT).show();

        JSONObject jsonObjectRate = jsonObject.getJSONObject(mFromCoin);
        String stringRate = jsonObjectRate.getString(mToCoin);

        mRate = Double.parseDouble(stringRate);

        if(mRate < 0){
            isToCoinRateLessThanZero = true;
        }else{
            isToCoinRateLessThanZero = false;
        }

        Toast.makeText(getActivity(), stringRate, Toast.LENGTH_SHORT).show();

        pbFetchingConversionRates.setVisibility(View.GONE);
        tvRateAndConversion.setText("");
        isConversionRatesFetched = true;

        /*
        if (jsonObject.optString("status").equals("true")){
//            progressDialog.dismiss();
//            constraintLayConversionGroup.setVisibility(View.GONE);
            pbFetchingConversionRates.setVisibility(View.GONE);
            tvRateAndConversion.setText("");

            isConversionRatesFetched = true;

            Toast.makeText(getActivity(), "Conversion rate fetched", Toast.LENGTH_SHORT).show();

        }else{//if(jsonObject.optString("status").equals("false"))
//            progressDialog.dismiss();
//            constraintLayConversionGroup.setVisibility(View.GONE);
            pbFetchingConversionRates.setVisibility(View.GONE);
            tvRateAndConversion.setText("");

//            ToastUtil.showTestToast(this, response, Toast.LENGTH_SHORT);
//            ToastUtil.showLiveToast(this, dataObjFlw.getString("message"), Toast.LENGTH_SHORT);
        }*/
    }
}