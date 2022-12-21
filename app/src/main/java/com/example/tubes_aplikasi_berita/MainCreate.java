package com.example.tubes_aplikasi_berita;

public class MainCreate extends AppCompatActivity {
    private MyDatabase db;
    private EditText Enama, Ealamat, Etelpon, Edeskripsi, Ekoleksi;
    private String Snama, Salamat, Stelpon, Sdeskripsi, Skoleksi;
    private byte[] Simage;

    ImageView mImageView;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        db = new MyDatabase(this);

        Enama = (EditText) findViewById(R.id.create_nama);
        Ealamat = (EditText) findViewById(R.id.create_alamat);
        Etelpon = (EditText) findViewById(R.id.create_telpon);
        Edeskripsi = (EditText) findViewById(R.id.create_deskripsi);
        Ekoleksi = (EditText) findViewById(R.id.create_koleksi);
        mImageView = findViewById(R.id.gambar);


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        MainCreate.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        Button btnCreate = (Button) findViewById(R.id.create_btn);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Simage =  imageViewToByte(mImageView);
                Snama = String.valueOf(Enama.getText());
                Salamat = String.valueOf(Ealamat.getText());
                Stelpon = String.valueOf(Etelpon.getText());
                Sdeskripsi = String.valueOf(Edeskripsi.getText());
                Skoleksi = String.valueOf(Ekoleksi.getText());


                if (Snama.equals("")){
                    Enama.requestFocus();
                    Toast.makeText(MainCreate.this, "Silahkan isi nama hotel", Toast.LENGTH_SHORT).show();
                } else if (Salamat.equals("")){
                    Ealamat.requestFocus();
                    Toast.makeText(MainCreate.this, "Silahkan isi alamat", Toast.LENGTH_SHORT).show();
                } else if (Stelpon.equals("")){
                    Etelpon.requestFocus();
                    Toast.makeText(MainCreate.this, "Silahkan isi no. telpon", Toast.LENGTH_SHORT).show();
                } else if (Sdeskripsi.equals("")){
                    Edeskripsi.requestFocus();
                    Toast.makeText(MainCreate.this, "Silahkan isi deskripsi", Toast.LENGTH_SHORT).show();
                } else if (Sdeskripsi.equals("")){
                    Ekoleksi.requestFocus();
                    Toast.makeText(MainCreate.this, "Silahkan isi koleksi", Toast.LENGTH_SHORT).show();
                } else {
                    mImageView.setImageResource(R.drawable.ic_image);
                    Enama.setText("");
                    Ealamat.setText("");
                    Etelpon.setText("");
                    Edeskripsi.setText("");
                    Ekoleksi.setText("");
                    Toast.makeText(MainCreate.this, "Data telah ditambah", Toast.LENGTH_SHORT).show();
                    db.CreateMuseum(new Museum(null, Simage, Snama, Salamat, Stelpon, Sdeskripsi, Skoleksi));
                    Intent a = new Intent(MainCreate.this, MainAdmin.class);
                    startActivity(a);
                }

            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private byte[] imageViewToByte(ImageView mImageView) {
        Bitmap bitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, stream);
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
                //set image choosed from gallery to image view
                mImageView.setImageURI(resultUri);
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
