package com.example.administrator.materialtest1;

import android.graphics.drawable.DrawableWrapper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private List<Fruit> fruitList=new ArrayList<>();
    private  FruitAdapter adapter;
    private Fruit[] fruits={new Fruit("Apple",R.drawable.apple),
            new Fruit("orange",R.drawable.orange),new Fruit("watermelon",R.drawable.watermelon),
            new Fruit("pear",R.drawable.pear),new Fruit("grape",R.drawable.grape),
            new Fruit("pineapple",R.drawable.pineapple),new Fruit("strawberry",R.drawable.strawberry),
            new Fruit("cherry",R.drawable.cherry),new Fruit("mango",R.drawable.mango)};
    private SwipeRefreshLayout swipeRefreshLayout;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(MainActivity.this,"Fab clicked",Toast.LENGTH_LONG).show();
                Snackbar.make(v,"Data delete",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
        getFruits();
         RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycle_view);
         GridLayoutManager layout=new GridLayoutManager(this,2);
         recyclerView.setLayoutManager(layout);
          adapter=new FruitAdapter(fruitList);
         recyclerView.setAdapter(adapter);

         swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
         swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
         swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 refreshFruits();
             }
         });
    }
    private void refreshFruits(){
         new Thread(new Runnable() {
             @Override
             public void run() {
                 try{
                     Thread.sleep(2000);
                 }catch (InterruptedException e){
                     e.printStackTrace();
                 }
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         getFruits();
                         adapter.notifyDataSetChanged();
                         swipeRefreshLayout.setRefreshing(false);
                     }
                 });
             }
         }).start();
    }
    private void getFruits(){
       fruitList.clear();
       for (int i=0;i<50;i++){
           Random random=new Random();
           int index=random.nextInt(fruits.length);
           fruitList.add(fruits[index]);
       }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this,"you clicked backup",Toast.LENGTH_LONG).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"you clicked delete",Toast.LENGTH_LONG).show();
                break;
            case R.id.setting:
                Toast.makeText(this,"you clicked setting",Toast.LENGTH_LONG).show();
                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }
}
