package com.example.soundboard;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JasonReadWrite
{
    static ArrayList<SoundItem> read(Context context)
    {
        ArrayList<SoundItem> soundItems = new ArrayList<>();
        String data;

        try
        {
            FileInputStream in = context.openFileInput("sounds.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            data = read(reader);
            JSONArray list = new JSONArray(data);
            Gson gson = new Gson();
            for (int i = 0; i < list.length(); i++)
                soundItems.add(gson.fromJson(list.getJSONObject(i).toString(), SoundItem.class));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        checkFileStillExist(soundItems);

        return soundItems;
    }

    private static void checkFileStillExist(ArrayList<SoundItem> soundItems)
    {
        for (int i = 0; i < soundItems.size(); i++)
        {
            SoundItem item = soundItems.get(i);

            File file = new File(item.getPath());
            if (!file.exists())
            {
                soundItems.remove(item);
                i--;
            }
        }
    }

    static void write(Context context, ArrayList<SoundItem> soundItems)
    {
        try
        {
            Gson gson = new Gson();
            String data = gson.toJson(soundItems);
            FileOutputStream out = context.openFileOutput("sounds.json", Context.MODE_PRIVATE);
            out.write(data.getBytes());
            out.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String read(BufferedReader reader)
    {
        try
        {
            StringBuilder builder = new StringBuilder();
            String aux;
            while ((aux = reader.readLine()) != null)
            {
                builder.append(aux);
            }
            return builder.toString();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

}
