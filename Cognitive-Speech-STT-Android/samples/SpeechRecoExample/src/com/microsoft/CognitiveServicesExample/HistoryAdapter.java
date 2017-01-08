package com.microsoft.CognitiveServicesExample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic.SpeechAnalysis;

import java.util.List;

/**
 * Created by Akshay on 2017-01-08.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View relativeContainerView;
        public TextView scoreTv;

        public ViewHolder(View itemView) {
            super(itemView);
            relativeContainerView = (RelativeLayout) itemView;
            scoreTv = (TextView)itemView.findViewById(R.id.score_item_tv);
        }
    }

    private List<SpeechAnalysis> mList;
    Context mContext;

    public HistoryAdapter(Context context, List<SpeechAnalysis> list){
        mList = list;
        mContext = context;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, null);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        holder.scoreTv.setText(mList.get(position).getmPercentScore()+"%");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
