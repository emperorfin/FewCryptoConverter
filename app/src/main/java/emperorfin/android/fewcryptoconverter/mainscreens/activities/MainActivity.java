package emperorfin.android.fewcryptoconverter.mainscreens.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import emperorfin.android.fewcryptoconverter.R;
import emperorfin.android.fewcryptoconverter.mainscreens.fragments.DiscoverFragment;
import emperorfin.android.fewcryptoconverter.mainscreens.fragments.MainFragment;
import emperorfin.android.fewcryptoconverter.mainscreens.fragments.PortfolioFragment;
import emperorfin.android.fewcryptoconverter.mainscreens.fragments.PricesFragment;
import emperorfin.android.fewcryptoconverter.mainscreens.fragments.SettingsFragment;
import emperorfin.android.fewcryptoconverter.mainscreens.interfaces.IMainActivity;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        setupListeners();

        initFragment();
    }

    private void initViews(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation_bar);
    }

    private void setupListeners(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_home:
                        inflateFragment(MainFragment.FRAGMENT_TAG);
                        return true;
                    case R.id.tab_market:
                        inflateFragment(PricesFragment.FRAGMENT_TAG);

                        Toast.makeText(MainActivity.this, getString(R.string.toast_coming_soon), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.tab_portfolio:
                        inflateFragment(PortfolioFragment.FRAGMENT_TAG);

                        Toast.makeText(MainActivity.this, getString(R.string.toast_coming_soon), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.tab_discover:
                        inflateFragment(DiscoverFragment.FRAGMENT_TAG);

                        Toast.makeText(MainActivity.this, getString(R.string.toast_coming_soon), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.tab_more:
                        inflateFragment(SettingsFragment.FRAGMENT_TAG);

                        Toast.makeText(MainActivity.this, getString(R.string.toast_coming_soon), Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
    }

    private void initFragment(){
        MainFragment mainFragment = MainFragment.newInstance();
//        doFragmentTransaction(mainFragment, MainFragment.FRAGMENT_TAG, false, "");
        doFragmentTransaction(mainFragment, MainFragment.FRAGMENT_TAG, false);
    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container, fragment, tag);

        if(addToBackStack){
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }

    public void inflateFragment(String fragmentTag) {
        if(fragmentTag.equals(MainFragment.FRAGMENT_TAG)){
            MainFragment fragment = new MainFragment();
            doFragmentTransaction(fragment, fragmentTag, false);
        }else if(fragmentTag.equals(PricesFragment.FRAGMENT_TAG)){
            PricesFragment fragment = PricesFragment.newInstance();
            doFragmentTransaction(fragment, fragmentTag, false);
        }else if(fragmentTag.equals(PortfolioFragment.FRAGMENT_TAG)){
            PortfolioFragment fragment = PortfolioFragment.newInstance();
            doFragmentTransaction(fragment, fragmentTag, false);
        }else if(fragmentTag.equals(DiscoverFragment.FRAGMENT_TAG)){
            DiscoverFragment fragment = DiscoverFragment.newInstance();
            doFragmentTransaction(fragment, fragmentTag, false);
        }else if(fragmentTag.equals(SettingsFragment.FRAGMENT_TAG)){
            SettingsFragment fragment = SettingsFragment.newInstance();
            doFragmentTransaction(fragment, fragmentTag, false);
        }
    }

    //Meant to be used in fragments
    @Override
    public void inflateFragment(String fragmentTag, String message) {
        if(fragmentTag.equals(PricesFragment.FRAGMENT_TAG)){
            PricesFragment fragment = new PricesFragment();
            doFragmentTransaction(fragment, fragmentTag, true);
        }else if(fragmentTag.equals(PortfolioFragment.FRAGMENT_TAG)){
//            PortfolioFragment fragment = PortfolioFragment.newInstance(message);
            PortfolioFragment fragment = PortfolioFragment.newInstance();
            doFragmentTransaction(fragment, fragmentTag, true);
        }else if(fragmentTag.equals(DiscoverFragment.FRAGMENT_TAG)){
            DiscoverFragment fragment = DiscoverFragment.newInstance();
            doFragmentTransaction(fragment, fragmentTag, true);
        }else if(fragmentTag.equals(SettingsFragment.FRAGMENT_TAG)){
            SettingsFragment fragment = SettingsFragment.newInstance();
            doFragmentTransaction(fragment, fragmentTag, true);
        }
    }
}