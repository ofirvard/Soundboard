package com.example.soundboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NameDialog.NoticeDialogListener
{
    SoundHandler soundHandler;
    RecyclerView recyclerView;
    Bundle savedInstanceState;
    SoundItemAdapter adapter;
    ArrayList<SoundItem> soundItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.savedInstanceState = savedInstanceState;
        recyclerView = findViewById(R.id.sound_list);
        soundHandler = new SoundHandler();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            soundItems = JasonReadWrite.read(this);
            adapter = new SoundItemAdapter(soundItems, soundHandler, this);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void addSound(MenuItem menuItem)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                Uri uri = data.getData();
                String path = uri.getPath();
                showNoticeDialog(path);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showNoticeDialog(String path)
    {
        Bundle bundle = new Bundle();
        bundle.putString("path", path);

        FragmentManager fragmentManager = getSupportFragmentManager();

        NameDialog nameDialog = new NameDialog();
        nameDialog.setArguments(bundle);

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(nameDialog, "dialog");
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name, String path)
    {
        soundItems.add(new SoundItem(name, path, soundItems));
        JasonReadWrite.write(this, soundItems);

        ArrayList<SoundItem> a = JasonReadWrite.read(this);

        dialog.dismiss();
        adapter.notifyDataSetChanged();
    }

    public void stop(MenuItem menuItem)
    {
        soundHandler.reset();
    }

    @Override
    protected void onDestroy()
    {
        soundHandler.reset();
        JasonReadWrite.write(this, soundItems);
        super.onDestroy();
    }
}
