package com.curso.onmessage.GroupActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.curso.onmessage.CustomAdapter.RecycleContactinGroup;
import com.curso.onmessage.CustomAdapter.RecycleContactsGroupNamed;
import com.curso.onmessage.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by PC-PRAF on 14/1/2017.
 */

public class GroupNameActivity extends AppCompatActivity {

    private  ImageView AddImage;
    private  int VisibleDeleteImageProfile = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.groupName_Toolbar);
        SetToolBar(toolbar);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.MembersInGroup);
        RecycleContactsGroupNamed recycleContactsGroupNamed = new RecycleContactsGroupNamed(this.getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recycleContactsGroupNamed);

        EmojiconEditText emojiconEditText = (EmojiconEditText) findViewById(R.id.TextGroupName);
        ImageView btnEmoji = (ImageView)findViewById(R.id.Add_Emoji_Btn);
        ViewGroup rootview = (ViewGroup)findViewById(R.id.rootview);
        EmojIconActions emojIconActions = new EmojIconActions(this, rootview,emojiconEditText , btnEmoji);
        emojIconActions.ShowEmojIcon();

        TextView textView =(TextView) findViewById(R.id.MembersCount);
        textView.setText(String.valueOf( RecycleContactinGroup.ContactsSelected.size() )+getString(R.string.Members));

        AddImage = (ImageView)findViewById(R.id.add_image);
        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = getLayoutInflater().inflate(R.layout.popup_layout, null);

                TextView OpenCamera = (TextView) view.findViewById(R.id.camera_up);
                TextView OpenGallery = (TextView) view.findViewById(R.id.gallery_up);
                TextView DeleteProfileImage = (TextView) view.findViewById(R.id.delete_up);

                if(VisibleDeleteImageProfile == 1){
                    DeleteProfileImage.setVisibility(View.VISIBLE);
                }

                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

                OpenGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext() , SearchImageGroupProfile.class);
                        v.getContext().startActivity(intent);
                        dialog.dismiss();
                    }
                });

                DeleteProfileImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddImage.setImageDrawable(v.getContext().getResources().getDrawable(R.drawable.add_group_image));
                        dialog.dismiss();
                        VisibleDeleteImageProfile = -1;
                    }
                });
                OpenCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent IntentOpenCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(IntentOpenCamera.resolveActivity(getPackageManager())!= null){
                            startActivityForResult(IntentOpenCamera , 1);
                            dialog.dismiss();
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(SearchImageGroupProfile.ImageSelectedToProfile !=""){

            File image = new File(SearchImageGroupProfile.ImageSelectedToProfile);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap imageBitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Bitmap b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Bitmap bitmap = Bitmap.createScaledBitmap(b, AddImage.getHeight(), AddImage.getWidth(), false);

            RoundedBitmapDrawable roundedBitmapDrawable = null;
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(),bitmap);
            roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());

            AddImage.setImageDrawable(roundedBitmapDrawable);
            VisibleDeleteImageProfile = 1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Bitmap b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            Bitmap bitmap = Bitmap.createScaledBitmap(b, AddImage.getHeight(), AddImage.getWidth(), false);

            RoundedBitmapDrawable roundedBitmapDrawable = null;
            roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(),bitmap);
            roundedBitmapDrawable.setCornerRadius(bitmap.getHeight());

            AddImage.setImageDrawable(roundedBitmapDrawable);
            VisibleDeleteImageProfile = 1;
        }
    }


    private void SetToolBar(Toolbar toolbar)
    {
        ImageView imageBack = (ImageView)findViewById(R.id.arrow_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);
        LinearLayout NextStep = (LinearLayout) findViewById(R.id.GroupCreated);
        NextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

}
