package com.example.tubes_aplikasi_berita;

import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainUpdateDelete extends AppCompatActivity {
    private MyDatabase db;
    private String Sid, Snama, Salamat, Stelpon, Sdeskripsi, Skoleksi;
    private EditText Enama, Ealamat, Etelpon, Edeskripsi, Ekoleksi;
    private byte[] Simage;

    ImageView mImageView;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updel);
        db = new MyDatabase(this);

        Intent i = this.getIntent();
        Sid = i.getStringExtra("Iid");
        Simage = i.getByteArrayExtra("Igambar");
        Snama = i.getStringExtra("Inama");
        Salamat = i.getStringExtra("Ialamat");
        Stelpon  = i.getStringExtra("Itelpon");
        Sdeskripsi = i.getStringExtra("Ideskripsi");
        Skoleksi = i.getStringExtra("Ikoleksi");

        Enama = (EditText) findViewById(R.id.update_nama);
        Ealamat = (EditText) findViewById(R.id.update_alamat);
        Etelpon = (EditText) findViewById(R.id.update_telpon);
        Edeskripsi = (EditText) findViewById(R.id.update_deskripsi);
        Ekoleksi = (EditText) findViewById(R.id.update_koleksi);
        mImageView = (ImageView) findViewById(R.id.gambar);


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainUpdel.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        Enama.setText(Snama);
        Ealamat.setText(Salamat);
        Etelpon.setText(Stelpon);
        Edeskripsi.setText(Sdeskripsi);
        Ekoleksi.setText(Skoleksi);
        Bitmap bitmap = BitmapFactory.decodeByteArray(Simage, 0, Simage.length);
        mImageView.setImageBitmap(bitmap);

        Button btnUpdate = (Button) findViewById(R.id.btn_up);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snama = String.valueOf(Enama.getText());
                Salamat = String.valueOf(Ealamat.getText());
                Stelpon = String.valueOf(Etelpon.getText());
                Sdeskripsi = String.valueOf(Edeskripsi.getText());
                Skoleksi = String.valueOf(Ekoleksi.getText());
                Simage = imageViewToByte(mImageView);
                if (Snama.equals("")){
                    Enama.requestFocus();
                    Toast.makeText(MainUpdel.this, "Silahkan isi nama hotel", Toast.LENGTH_SHORT).show();
                } else if (Salamat.equals("")){
                    Ealamat.requestFocus();
                    Toast.makeText(MainUpdel.this, "Silahkan isi alamat", Toast.LENGTH_SHORT).show();
                } else if (Stelpon.equals("")){
                    Etelpon  .requestFocus();
                    Toast.makeText(MainUpdel.this, "Silahkan isi no. telpon", Toast.LENGTH_SHORT).show();
                } else if (Sdeskripsi.equals("")){
                    Edeskripsi  .requestFocus();
                    Toast.makeText(MainUpdel.this, "Silahkan isi deskripsi", Toast.LENGTH_SHORT).show();
                } else if (Skoleksi.equals("")){
                    Ekoleksi  .requestFocus();
                    Toast.makeText(MainUpdel.this, "Silahkan isi koleksi", Toast.LENGTH_SHORT).show();
                } else {
                    db.UpdateMuseum(new Museum(Sid, Simage, Snama, Salamat, Stelpon, Sdeskripsi, Skoleksi));
                    Toast.makeText(MainUpdel.this, "Data telah diupdate", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


        Button btnDelete = (Button) findViewById(R.id.btn_del);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle("Apakah data akan dihapus?");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        db.DeleteMuseum(new Museum(Sid, Simage, Snama, Salamat, Stelpon, Sdeskripsi, Skoleksi));
                        Toast.makeText(MainUpdel.this, "Data telah dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private byte[] imageViewToByte(ImageView mImageView) {
        Bitmap bitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int back = item.getItemId();
        if(back == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //gallery intent
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON) //enable image guidlines
                    .setAspectRatio(1,1)// image will be square
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                // set image choosed from gallery to image view
                mImageView.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
