package space.samatov.smellslikebacon;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListFragment.OnRecipeSelectedInterface,GridFragment.OnRecipeSelectedInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isTablet=getResources().getBoolean(R.bool.is_tablet);
        if(!isTablet) {
            ListFragment savedFragmentInstance = (ListFragment) getSupportFragmentManager().findFragmentByTag("list_fragment");
            if (savedFragmentInstance == null) {
                ListFragment fragment = new ListFragment();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.placeholder, fragment, "list_fragment");
                fragmentTransaction.commit();
            }
        }
        else
        {
            GridFragment savedFragmentInstance = (GridFragment) getSupportFragmentManager().findFragmentByTag("list_fragment");
            if (savedFragmentInstance == null) {
                GridFragment fragment = new GridFragment();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.placeholder, fragment, "list_fragment");
                fragmentTransaction.commit();
            }
        }
    }

    @Override
    public void onListRecipeSelected(int index) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX,index);
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, fragment,"view_pager_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onGridRecipeSelected(int index) {
        DualPaneFragment dualPaneFragment=new DualPaneFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle=new Bundle();
        bundle.putInt(ViewPagerFragment.KEY_RECIPE_INDEX,index);
        dualPaneFragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, dualPaneFragment,"dualpane_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
