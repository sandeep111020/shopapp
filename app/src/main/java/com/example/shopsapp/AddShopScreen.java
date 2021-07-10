package com.example.shopsapp;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;



import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddShopScreen extends AppCompatActivity {
    ImageView itemimage;
    Button neworders;
    EditText itemname,itemdes,number,city,items,offers;
    ImageView extraimg1;

    ProgressDialog loadingbar;
    private Uri ImageUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    public static final int GalleryPick = 1;
    Button submit,menu;
    String Sitemimage,Sitemname,Sitemdes,Snumber,Scity,Sitems,Soffers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_screen);
        itemimage=findViewById(R.id.itemimage);
        itemname=findViewById(R.id.itemname);
        number=findViewById(R.id.itempnumber);
        city=findViewById(R.id.itemcity);
        items=findViewById(R.id.items);
        loadingbar=new ProgressDialog(this);
        itemdes=findViewById(R.id.itemdesc);
        offers=findViewById(R.id.offers);



        submit=findViewById(R.id.submititem);
        storageReference = FirebaseStorage.getInstance().getReference("Images").child("Shops");
        databaseReference = FirebaseDatabase.getInstance().getReference("Shops");


        itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }

    private void ValidateProductData()
    {
        Sitemname=itemname.getText().toString();
        Sitemdes=itemdes.getText().toString();
        Snumber=number.getText().toString();
        Scity=city.getText().toString();
        Sitems=items.getText().toString();
        Soffers=offers.getText().toString();


        if (ImageUri==null)
        {
            Toast.makeText(this, "Product Image is Required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Snumber)){
            Toast.makeText(this, "Product number is Required", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(items.getText().toString())){
            Toast.makeText(this, "Products are Required", Toast.LENGTH_SHORT).show();

        }

        else if (TextUtils.isEmpty(Sitemname))
        {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Soffers)){
            Toast.makeText(this, "Offers Are required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sitemdes))
        {
            Toast.makeText(this, "Item Description is required", Toast.LENGTH_SHORT).show();
        }

        else
        {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation()
    {
        loadingbar.setMessage("Please Wait");
        loadingbar.setTitle("Adding New Product");
        loadingbar.setCancelable(false);
        loadingbar.show();
        UploadImage();


    }
    public void UploadImage() {

        if (ImageUri != null) {

            loadingbar.setTitle("Image is Uploading...");
            loadingbar.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(ImageUri));
            storageReference2.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String TempItemName = itemname.getText().toString().trim();

                            String TempNumber=number.getText().toString().trim();
                            String TempItemdesc = itemdes.getText().toString().trim();
                            String TempCity =city.getText().toString().trim();
                            String TempItems =items.getText().toString().trim();
                            String TempOffers= offers.getText().toString().trim();

                            loadingbar.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    shopmodel userProfileInfo = new shopmodel(TempItemName,TempItemdesc,TempNumber,TempCity,TempItems,TempOffers, url);
                                    String ImageUploadId = databaseReference.push().getKey();
                                    databaseReference.child(ImageUploadId).setValue(userProfileInfo);




                                }
                            });

                        }
                    });
        }
        else {

            Toast.makeText(AddShopScreen.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri=data.getData();
            itemimage.setImageURI(ImageUri);
        }
    }

    private void OpenGallery()
    {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GalleryPick);
    }

}