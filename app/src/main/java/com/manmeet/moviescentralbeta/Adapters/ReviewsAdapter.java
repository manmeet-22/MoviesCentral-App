package com.manmeet.moviescentralbeta.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.manmeet.moviescentralbeta.R;
import com.manmeet.moviescentralbeta.Pojos.movie_details.ReviewResults;

import java.util.List;

/**
 * Created by manmeet on 28/3/18.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private Context context;
    private List<ReviewResults> mList;

    public ReviewsAdapter(Context context, List<ReviewResults> list) {
        this.context = context;
        mList = list;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewHolder(LayoutInflater.from(context).inflate(R.layout.review_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        ReviewResults review = mList.get(position);
        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        if(mList != null)
            return mList.size();
        else
            return 0;
    }

    public class ReviewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        TextView author ;
        TextView content ;
        CardView review_cardview;
        int flag =0;
        public ReviewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.textView_review_author);
            content = itemView.findViewById(R.id.textView_review_content);
            review_cardview = itemView.findViewById(R.id.cardView_review);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(flag ==0 ){
                ViewGroup.LayoutParams params = review_cardview.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                review_cardview.setLayoutParams(params);
                flag =1;
                Log.i("Review OnClick", Integer.toString(flag));
            }
            else{
                ViewGroup.LayoutParams params = review_cardview.getLayoutParams();
                params.height = 500;
                review_cardview.setLayoutParams(params);
                flag = 0;
            }
        }
    }
}
