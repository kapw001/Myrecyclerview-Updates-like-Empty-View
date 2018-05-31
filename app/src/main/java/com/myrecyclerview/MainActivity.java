package com.myrecyclerview;

import android.content.ClipData;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.customrecyclerview.EmptyRecyclerView;
import com.customrecyclerview.divider.DividerItemDecoration;
import com.customrecyclerview.helper.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import commonadapter.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, Filterable {

    private EmptyRecyclerView emptyRecyclerView;
    private RecyclerViewAdapter myAdapeter;
    private List mList;
    private List filterList;

    private RelativeLayout mRoot;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRoot = (RelativeLayout) findViewById(R.id.mRoot);
        mList = new ArrayList<>();


        searchView = (SearchView) findViewById(R.id.searchview);

        myAdapeter = new RecyclerViewAdapter(mList);

        emptyRecyclerView = findViewById(R.id.myView);
        emptyRecyclerView.setLinearLayoutLayout(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        emptyRecyclerView.setSwipeRefreshListener(this);
//        emptyRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));

        emptyRecyclerView.setAdapter(myAdapeter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);

        emptyRecyclerView.setItemTouchHelper(itemTouchHelperCallback);


//        emptyRecyclerView.setSwipeRefreshEnabled(false);
        emptyRecyclerView.setRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadData();
            }
        });
        LoadDataEmpty();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                getFilter().filter(newText);

                return false;
            }
        });


    }

    private void LoadDataEmpty() {

        if (emptyRecyclerView.getNetworkstats()) {

            Toast.makeText(this, "Internet", Toast.LENGTH_SHORT).show();

            emptyRecyclerView.setLoading();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    emptyRecyclerView.setSwipeRefreshing(false);
                    myAdapeter.updateData(new ArrayList<String>());
                }
            }, 3000);


        } else {
            emptyRecyclerView.setNoInternetErrorMsg();
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();

        }


    }

    private void LoadData() {
        if (emptyRecyclerView.getNetworkstats()) {

            emptyRecyclerView.setLoading();
            Toast.makeText(this, "Internet", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    List<ModelTest> list = new ArrayList<>();
                    for (int i = 0; i < 100; i++) {

                        ModelTest modelTest = new ModelTest();
                        modelTest.setName("H " + i);
                        list.add(modelTest);
                    }
                    emptyRecyclerView.setSwipeRefreshing(false);
                    mList.clear();
                    mList.addAll(list);
                    myAdapeter.updateData(mList);

                }
            }, 3000);

        } else {


            emptyRecyclerView.setNoInternetErrorMsg();
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();


        }


//        myAdapeter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, Object o, int position) {
//
//                if (o instanceof ModelTest) {
//
//                    if (((ModelTest) o).isChecked()) {
//                        ((ModelTest) o).setChecked(false);
//                    } else {
//                        ((ModelTest) o).setChecked(true);
//                    }
//
//                }
//
//                myAdapeter.notifyItemChanged(position);
//
//                Log.e(TAG, "onItemClick: " + ((ModelTest) o).getName() + "  " + ((ModelTest) o).isChecked());
//
//            }
//        });

        myAdapeter.setOnChangeLongClickListener(new RecyclerViewAdapter.OnRecyclerViewCheckBoxChangeClickListener() {
            @Override
            public void onChangeListener(View view, int position, Object o, boolean isChecked) {

                ((ModelTest) o).setChecked(isChecked);

//                myAdapeter.notifyItemChanged(position);

                Log.e(TAG, "onItemLongClick: " + ((ModelTest) o).getName() + "  " + ((ModelTest) o).isChecked());

            }
        });


        myAdapeter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int position) {


                int count = 0;
                int maleCount = 0;

                for (int i = 0; i < mList.size(); i++) {


                    ModelTest modelTest = (ModelTest) mList.get(i);

                    if (modelTest.isChecked()) {

                        count++;

                    }

                    if (modelTest.isMaleSelected()) {

                        maleCount++;

                    }


                }

                Log.e(TAG, "onItemClick: " + count + "   male count " + maleCount);

            }
        });


        myAdapeter.setOnCheckedChangeListener(new RecyclerViewAdapter.OnRecyclerViewCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId, View view, int position, Object o) {

                ModelTest modelTest = (ModelTest) mList.get(position);
                switch (checkedId) {
                    case R.id.radioMale:

//                        radioMale.setChecked(true);

                        modelTest.setMaleSelected(true);

                        break;
                    case R.id.radioFemale:

//                        radioFemale.setChecked(true);
                        modelTest.setMaleSelected(false);
                        break;
                }

//                myAdapeter.notifyItemChanged(position);
            }
        });


    }

    private static final String TAG = "MainActivity";

    @Override
    public Filter getFilter() {


        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = mList;
                } else {
                    List filteredList = new ArrayList<>();
                    for (Object row : mList) {


                        if (row instanceof ModelTest) {

                            ModelTest modelTest = (ModelTest) row;

                            if (modelTest.getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }

                        }

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
//                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getPhone().contains(charSequence)) {
//                            filteredList.add(row);
//                        }
                    }

                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList) filterResults.values;
                myAdapeter.updateData(filterList);
            }
        };


    }

    @Override
    public void onRefresh() {
        LoadDataEmpty();
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof RecyclerViewAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            ModelTest name = (ModelTest) mList.get(viewHolder.getAdapterPosition());

            // backup of removed item for undo purpose
            final ModelTest deletedItem = (ModelTest) mList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            myAdapeter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(mRoot, name.getName() + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    myAdapeter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }

    }
}
