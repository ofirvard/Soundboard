package com.example.soundboard;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

public class SoundItem
{
    private String name;
    private int color;
    private String path;
    private int id;

    public SoundItem(String name, String path, ArrayList<SoundItem> items)
    {
        Random rnd = new Random();
        this.name = name;
        this.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        this.path = path;
        this.id = getUniqueId(rnd, items);
    }

    private int getUniqueId(Random rnd, ArrayList<SoundItem> items)
    {
        boolean isUnique;

        while (true)
        {
            int id = rnd.nextInt();
            isUnique = true;

            for (SoundItem item : items)
            {
                if (item.id == id)
                {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique)
                return id;
        }
    }

    public void randomizeColor()
    {
        Random rnd = new Random();
        this.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public String getName()
    {
        return name;
    }

    public int getColor()
    {
        return color;
    }

    public String getPath()
    {
        return path;
    }

    public int getId()
    {
        return id;
    }
}
