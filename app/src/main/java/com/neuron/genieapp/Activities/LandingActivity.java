package com.neuron.genieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.neuron.genieapp.Adapters.GridViewAdapter;
import com.neuron.genieapp.Adapters.ImageAdapter;
import com.neuron.genieapp.Adapters.MyRecyclerViewAdapter;
import com.neuron.genieapp.DataModels.Cards;
import com.neuron.genieapp.R;

import java.util.ArrayList;
import java.util.List;

import static android.widget.GridLayout.HORIZONTAL;
import static com.neuron.genieapp.R.id.gridview;

public class LandingActivity extends AppCompatActivity {

    RelativeLayout layout1, layout2, layout3;

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<Cards> cardList;
    private List<Cards> iconList;
    private Cards cards;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    PopupWindow popupWindowDogs;
    String popUpContents[];
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        layout1 = (RelativeLayout) findViewById(R.id.layout_buttons);
        layout2 = (RelativeLayout) findViewById(R.id.layout_images);
        layout3 = (RelativeLayout) findViewById(R.id.layout_cards);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels / 2;

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GridView gridView = (GridView) findViewById(gridview);

        // Instance of ImageAdapter Class
        gridView.setAdapter(new GridViewAdapter(this, width));

        cardList = new ArrayList<>();
        iconList = new ArrayList<>();

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPageAndroid);
        ImageAdapter adapterView = new ImageAdapter(this);
        mViewPager.setAdapter(adapterView);

        adapter = new MyRecyclerViewAdapter(this, cardList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.getLayoutManager().scrollToPosition(Integer.MAX_VALUE/2);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        int fixedHeight = (height * 30) / 100 ;
        int fixedHeight1 = (fixedHeight * 50) / 100 ;
        int w =  (width * 45) / 100;

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout1.getLayoutParams();
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) layout2.getLayoutParams();
        RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) layout3.getLayoutParams();


        params.height =  fixedHeight;
        params2.height = fixedHeight;
        params3.height = fixedHeight;

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (position == 0) {
                    intent = new Intent(LandingActivity.this, ChatBotActivity.class);
                    startActivity(intent);
                }
                else if (position == 1){
                    Toast.makeText(LandingActivity.this, "Calling", Toast.LENGTH_SHORT).show();
                }
                else if (position == 2){
                    Toast.makeText(LandingActivity.this, "Coming Soon!!", Toast.LENGTH_SHORT).show();
                }
                else if (position == 4){
                    Toast.makeText(LandingActivity.this, "Coming Soon!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //layout 3
        prepareCards();
    }


    private void prepareCards() {
        int[] covers = new int[]{
                R.drawable.image_1,
                R.drawable.image_2,
                R.drawable.image_3};

        Cards a = new Cards( covers[0]);
        cardList.add(a);

        a = new Cards(covers[1]);
        cardList.add(a);

        a = new Cards(covers[2]);
        cardList.add(a);

        adapter.notifyDataSetChanged();
    }

    private void prepareIcons() {
        int[] icons = new int[]{
                R.drawable.ic_call_black_24dp,
                R.drawable.ic_call_black_24dp,
                R.drawable.ic_call_black_24dp,
                R.drawable.ic_call_black_24dp};

        Cards a = new Cards(icons[0]);
        iconList.add(a);

        a = new Cards(icons[1]);
        iconList.add(a);

        a = new Cards(icons[2]);
        iconList.add(a);

        a = new Cards(icons[3]);
        iconList.add(a);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

}
