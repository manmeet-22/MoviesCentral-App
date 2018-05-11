package com.manmeet.moviescentralbeta.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manmeet.moviescentralbeta.R;
import com.manmeet.moviescentralbeta.Pojos.movie_details.Cast;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by manmeet on 29/3/18.
 */

/**
 * This is a custom adapter which shows the cast information of a movie or tv show in a recycler view.
 */

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder> {

    private Context context;
    private List<Cast> castList;

    public CastAdapter(Context context, List<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    @Override
    public CastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CastHolder(LayoutInflater.from(context).inflate(R.layout.cast_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CastHolder holder, int position) {
        Cast cast = castList.get(position);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500" + cast.getProfilePath()).into(holder.imageView);
        holder.castName.setText(cast.getName());
        holder.castRoleName.setText(cast.getCharacter());
    }

    @Override
    public int getItemCount() {
        if (castList != null)
            return castList.size();
        else
            return 0;
    }

    public class CastHolder extends RecyclerView.ViewHolder {
        //private CircleImageView imageView;
        private ImageView imageView;
        private TextView castName;
        private TextView castRoleName;

        public CastHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.circular_ImageView);
            castName = itemView.findViewById(R.id.item_cast_name);
            castRoleName = itemView.findViewById(R.id.item_cast_role_name);
        }
    }

}
