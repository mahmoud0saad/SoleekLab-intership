package com.example.android.soleeklapintership;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List>{
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    MainAdapter adapter;
    List<String> mListCountries;
    String URL_COUNTRIES_API=" https://api.printful.com/countries";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //associate with xml
        recyclerView=findViewById(R.id.recycler_view);

        //get instance from firebase
        firebaseAuth=FirebaseAuth.getInstance();

        //check if no accont login
        if (firebaseAuth.getCurrentUser()==null){
            //if no account go to login activity
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }

        //init list countries
        mListCountries=new ArrayList();

        //create adatper
        adapter=new MainAdapter(this,mListCountries);

        //create grid layout
        GridLayoutManager mGridLayoutManager=new GridLayoutManager(this,1);

        //set adapter
        recyclerView.setAdapter(adapter);

        //set layout manager
        recyclerView.setLayoutManager(mGridLayoutManager);

        if (hasNetwork()) {

            //intial the loader if have network
            getLoaderManager().initLoader(1, null, this);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu
      getMenuInflater().inflate(R.menu.main,menu);
      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get id for item clicker
        int idItem=item.getItemId();

        if (idItem==R.id.menu_item_logout){
            //sign out from user
            firebaseAuth.signOut();

            //terminate this activity
            finish();

            //go to login activity
            startActivity(new Intent(MainActivity.this,LoginActivity.class));

        }

        return true;
    }

        public boolean hasNetwork() {
        ConnectivityManager cM = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cM.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public Loader<List> onCreateLoader(int i, Bundle bundle) {
        return new CountriesLoader(this,URL_COUNTRIES_API);
    }

    @Override
    public void onLoadFinished(Loader<List> loader, List list) {
        mListCountries.clear();
        mListCountries.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List> loader) {

    }
}
