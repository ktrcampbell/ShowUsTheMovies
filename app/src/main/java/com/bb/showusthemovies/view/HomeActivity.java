package com.bb.showusthemovies.view;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;


import com.bb.showusthemovies.R;
import com.bb.showusthemovies.adapter.MovieAdapter;
import com.bb.showusthemovies.model.MoviePageResult;
import com.bb.showusthemovies.model.Result;
import com.bb.showusthemovies.network.MovieRetrofitInstance;
import com.bb.showusthemovies.util.Constants;
import com.bb.showusthemovies.util.DebugLogger;
import com.bb.showusthemovies.viewmodel.MovieViewModel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;

    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(movieAdapter != null){
                movieAdapter.getFilter().filter(newText);}
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
