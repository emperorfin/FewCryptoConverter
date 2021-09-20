package emperorfin.android.fewcryptoconverter.mainscreens.models;

import androidx.annotation.DrawableRes;

import com.github.mikephil.charting.data.LineData;

public class CoinModel {
    @DrawableRes
    private int coinIcon;

    private LineData coinChartData;

    private String coinName;
    private String coinPrice;
    private String coinChange;

    private boolean hasGained;

    public CoinModel() {
    }

    public CoinModel(@DrawableRes int coinIcon, LineData coinChartData, String coinName, String coinPrice, String coinChange, boolean hasGained) {
        this.coinIcon = coinIcon;
        this.coinChartData = coinChartData;
        this.coinName = coinName;
        this.coinPrice = coinPrice;
        this.coinChange = coinChange;
        this.hasGained = hasGained;
    }

    @DrawableRes
    public int getCoinIcon() {
        return coinIcon;
    }

    public CoinModel setCoinIcon(@DrawableRes int coinIcon) {
        this.coinIcon = coinIcon;
        return this;
    }

    public LineData getCoinChartData() {
        return coinChartData;
    }

    public CoinModel setCoinChartData(LineData coinChartData) {
        this.coinChartData = coinChartData;
        return this;
    }

    public String getCoinName() {
        return coinName;
    }

    public CoinModel setCoinName(String coinName) {
        this.coinName = coinName;
        return this;
    }

    public String getCoinPrice() {
        return coinPrice;
    }

    public CoinModel setCoinPrice(String coinPrice) {
        this.coinPrice = coinPrice;
        return this;
    }

    public String getCoinChange() {
        return coinChange;
    }

    public CoinModel setCoinChange(String coinChange) {
        this.coinChange = coinChange;
        return this;
    }

    public boolean isHasGained() {
        return hasGained;
    }

    public CoinModel setHasGained(boolean hasGained) {
        this.hasGained = hasGained;
        return this;
    }
}
