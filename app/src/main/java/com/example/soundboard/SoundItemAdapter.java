package com.example.soundboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SoundItemAdapter extends RecyclerView.Adapter<SoundItemAdapter.ViewHolder>
{
    private ArrayList<SoundItem> soundItems;
    private SoundHandler soundHandler;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        ImageView imageView;
        ConstraintLayout constraintLayout;

        ViewHolder(View itemView)
        {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.name);
            this.imageView = (ImageView) itemView.findViewById(R.id.color);
            this.constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.sound);
        }
    }

    SoundItemAdapter(ArrayList<SoundItem> soundItems, SoundHandler soundHandler, Context context)
    {
        this.soundItems = soundItems;
        this.soundHandler = soundHandler;
        this.context = context;
    }

    @NonNull
    @Override
    public SoundItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sound_button, parent, false);

        return new SoundItemAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundItemAdapter.ViewHolder holder, int position)
    {
        final SoundItem item = soundItems.get(position);
        holder.textView.setText(item.getName());
        holder.imageView.setBackgroundColor(item.getColor());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                soundHandler.play(item.getPath());
            }
        });

        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                item.randomizeColor();
                JasonReadWrite.write(context, soundItems);
                notifyDataSetChanged();
                return false;
            }
        });

        // TODO: 4/8/2019 add multi select for delete, check if name goes over
    }

    @Override
    public int getItemCount()
    {
        return soundItems.size();
    }
}
