package net.atmode.lizt.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.atmode.lizt.R;
import net.atmode.lizt.adapter.CustomAdapter;
import net.atmode.lizt.entity.Lizt;
import net.atmode.lizt.viewmodel.LiztViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnItemClickListener{

    private LiztViewModel liztViewModel;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.listsView);
        customAdapter = new CustomAdapter(this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter.setOnItemClickListener(this);

        liztViewModel = ViewModelProviders.of(this).get(LiztViewModel.class);

        liztViewModel.getAllLizts().observe(this, new Observer<List<Lizt>>() {
            @Override
            public void onChanged(@Nullable List<Lizt> lizts) {
                customAdapter.setLizts(lizts);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                liztViewModel.delete(customAdapter.getLiztAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Record deleted!", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogClass customDialogClass = new CustomDialogClass(MainActivity.this);
                customDialogClass.show();
                customDialogClass.setmDialogResult(new CustomDialogClass.OnMyDialogResult() {
                    @Override
                    public void finish(String result) {
                        addToList(result);
                    }
                });
            }
        });
    }

    public void addToList(String name) {
        Lizt lizt = new Lizt();
        lizt.setName(name);
        liztViewModel.insert(lizt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(final Lizt lizt) {
        CustomDialogClass customDialogClass = new CustomDialogClass(MainActivity.this);
        customDialogClass.showDialog(lizt.getName());
        customDialogClass.setmDialogResult(new CustomDialogClass.OnMyDialogResult() {
            @Override
            public void finish(String result) {
                lizt.setName(result);
                liztViewModel.update(lizt);
            }
        });
    }
}
