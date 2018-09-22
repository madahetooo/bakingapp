package com.eslam.du.bakingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.eslam.du.bakingapp.aragments.stepDetailFragment;
import com.eslam.du.bakingapp.modules.Steps;
import com.eslam.du.bakingapp.R;

import java.util.ArrayList;
public class stepListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private Intent intent;
    private ArrayList<Steps> steps=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        intent=getIntent();
        if(intent.getExtras() != null){
            String RECIPE_KEY=getResources().getString(R.string.recipe_extras);
            steps=intent.getParcelableArrayListExtra(RECIPE_KEY);
        }

        if (findViewById(R.id.step_detail_container) != null) {
            mTwoPane = true;
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        }
        View recyclerView = findViewById(R.id.step_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, steps, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final stepListActivity mParentActivity;
        private final ArrayList<Steps> mSteps;
        private final boolean mTwoPane;
        private Context context;

        SimpleItemRecyclerViewAdapter(stepListActivity parent, ArrayList<Steps> steps, boolean twoPane) {
            mSteps = steps;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context=parent.getContext();
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(mSteps.get(position).getId())+" ");
            holder.mStepView.setText(mSteps.get(position).getShortDescription());
            holder.itemView.setTag(mSteps.get(position).getShortDescription());
        }

        @Override
        public int getItemCount() {
            return mSteps.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            final TextView mIdView;
            final TextView mStepView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mStepView = view.findViewById(R.id.content);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(context.getResources().getString(R.string.step_detail_fragment), mSteps.get(getAdapterPosition()));
                    stepDetailFragment fragment = new stepDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.step_detail_container, fragment)
                            .commit();
                } else {

                    Intent intent = new Intent(context, stepDetailActivity.class);
                    intent.putParcelableArrayListExtra(context.getResources().getString(R.string.step_detail_activity),mSteps);
                    intent.putExtra("position",getAdapterPosition());

                    context.startActivity(intent);
                }
            }
        }
    }
}