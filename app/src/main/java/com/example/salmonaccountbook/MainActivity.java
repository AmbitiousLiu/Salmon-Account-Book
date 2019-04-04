package com.example.salmonaccountbook;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static MainActivity instance_main = null;
    static int flag=0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        /*-----------------------------------------------------------主活动碎片跳转事件代码--------------------------------------------------------*/
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.layout_fm_content,ie_fragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragmentTransaction.replace(R.id.layout_fm_content,type_fragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.layout_fm_content,date_fragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };




    final Ie_Fragment ie_fragment = new Ie_Fragment();
    final Type_Fragment type_fragment = new Type_Fragment();
    final Date_Fragment date_fragment = new Date_Fragment();

    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private ImageView userimage;
//    private ImageView muserimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        instance_main = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }





        /*-----------------------------------------------------------头像点击事件代码--------------------------------------------------------*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        userimage = navigationView.inflateHeaderView(R.layout.nav_header_main).findViewById(R.id.imageView);
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
            }
        });



        /*-----------------------------------------------------------登录逻辑代码--------------------------------------------------------*/
        String denglu = "yes";
        List<Person> people = DataSupport.where("denglu = ?",denglu).find(Person.class);
        if(people.isEmpty()){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            LoginActivity.username = people.get(0).getUsername();
        }

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        if(fragmentTransaction.isEmpty()){
//            fragmentTransaction.add(R.id.layout_fm_content,ie_fragment);
//            fragmentTransaction.commit();
//        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }




    /*-----------------------------------------------------------动态切换背景代码------------------------------------------------------*/
    protected void onStart(){
        super.onStart();
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        if(LoginActivity.username!=null){
            List<Person> people = DataSupport.where("username = ?",LoginActivity.username).find(Person.class);
            String beijing = people.get(0).getBeijing();
//            StringBuffer image = people.get(0).getImage();
            if(beijing.toString().equals("beijing1")){
                drawerLayout.setBackgroundResource(R.drawable.beijing1);
            }
            else if(beijing.toString().equals("beijing2")){
                drawerLayout.setBackgroundResource(R.drawable.beijing2);
            }
            else if(beijing.toString().equals("beijing3")){
                drawerLayout.setBackgroundResource(R.drawable.nvsheng);

            }
            else if(beijing.toString().equals("beijing4")){
                drawerLayout.setBackgroundResource(R.drawable.cat);
            }
            else if(beijing.toString().equals("beijing5")){
                drawerLayout.setBackgroundResource(R.drawable.hehua);
            }
            else if(beijing.toString().equals("beijing6")){
                drawerLayout.setBackgroundResource(R.drawable.pugongying);
            }
            else if(beijing.toString().equals("beijing7")){
                drawerLayout.setBackgroundResource(R.drawable.yezi);
            }
            else if(beijing.toString().equals("beijing8")){
                drawerLayout.setBackgroundResource(R.drawable.haidi);
            }
//            if(image==null){
//                userimage.setImageResource(R.drawable.nobody);
//            }else{
////读取二进制文件转换为头像
//            }
        }

    }

    protected void onResume(){
        super.onResume();
        CoordinatorLayout layout_app_bar_main = findViewById(R.id.layout_app_bar_main);
        if(MainActivity.flag==0){
            layout_app_bar_main.setBackgroundColor(Color.parseColor("#00ffffff"));
        }else if(MainActivity.flag==1){
            layout_app_bar_main.setBackgroundColor(Color.parseColor("#58000000"));
        }
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    /*-----------------------------------------------------------主活动右上角查看个人信息代码------------------------------------------------------*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            List<Person> people = DataSupport.where("username = ?",LoginActivity.username).find(Person.class);
            Person person = people.get(0);
            final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("个人信息")
                    .setMessage("用户名："+LoginActivity.username+"\n生日："+person.getBirthday())
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    /*-----------------------------------------------------------菜单部分点击跳转事件代码--------------------------------------------------------*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_background) {
            Intent intent = new Intent(MainActivity.this,BeijingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_night) {
            if(flag==0){
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
                Toast.makeText(MainActivity.this,"夜间模式",Toast.LENGTH_LONG).show();
                flag=1;
            }else if(flag == 1){
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
                Toast.makeText(MainActivity.this,"日间模式",Toast.LENGTH_LONG).show();
                flag =0;
            }
        } else if (id == R.id.nav_lock) {
            Toast.makeText(MainActivity.this,"手势解锁功能正在更新中...",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_setting) {
            final View view = getLayoutInflater().inflate(R.layout.dialog,null);
            final EditText password = (EditText) view.findViewById(R.id.et_dialog_password);
            final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this)
            .setView(view)
            .setIcon(R.drawable.ic_menu_lock)
            .setTitle("修改密码");
            Log.d("password","password"+password);
            dialog.setPositiveButton("SURE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    List<Person> people = DataSupport.where("username = ?",LoginActivity.username).find(Person.class);
                    Person person = people.get(0);

                    if(password.getText().toString().equals(person.getPassword())){
                        Intent intent = new Intent(MainActivity.this,PersonalActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this,"密码错误.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this,"CANCEL",Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();


        } else if (id == R.id.nav_deleteaccount) {
            final AlertDialog dialog2 = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示")
                    .setMessage("确认要删除个人账目信息吗？删除后不可恢复！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DataSupport.deleteAll(Data.class,"username = ?",LoginActivity.username);
                            Toast.makeText(MainActivity.this,"您的账目已删除！",Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog2.show();
        } else if (id == R.id.nav_deleteall) {
            final AlertDialog dialog1 = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示")
                    .setMessage("确认要删除个人信息吗？包括个人账号信息和个人账目信息，并且删除后不可恢复！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DataSupport.deleteAll(Person.class,"username = ?",LoginActivity.username);
                            DataSupport.deleteAll(Data.class,"username = ?",LoginActivity.username);
                            Toast.makeText(MainActivity.this,"用户信息已删除！",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                            LoginActivity.username = null;
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog1.show();
        } else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {
            final AlertDialog dialog1 = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示")
                    .setMessage("您确定要退出登录吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Person person = new Person();
                            person.setDenglu("no");
                            person.updateAll("username = ?",LoginActivity.username);
                            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                            LoginActivity.username = null;
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog1.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    /*-------------------------------------------点击头像的底部弹出选择事件动画及内部点击操作代码----------------------------------------------*/
    private void setDialog() {
        final Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.bottom_dialog, null);
        //初始化视图
        ////////////////////////////////////////底部框弹出动画/////////////////////////////////////////
        mCameraDialog.setContentView(root);
        final Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();

        //////////////////////////////////点击事件//////////////////////////////////////////////////
// 打开相机
        root.findViewById(R.id.btn_open_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "即将打开相机", Toast.LENGTH_SHORT).show();
                File outputImage = new File(getExternalCacheDir(),"ouput_image.jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24){
                    imageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.salmonaccountbook.fileprovider",outputImage);
                }else{
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
                mCameraDialog.dismiss();
            }
        });
//从相册选择
        root.findViewById(R.id.btn_choose_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    mCameraDialog.dismiss();
                }else{
                    openAlbum();
                    mCameraDialog.dismiss();
                }
            }
        });
//查看头像
        root.findViewById(R.id.btn_see).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "查看头像功能正在更新中...", Toast.LENGTH_SHORT).show();
                mCameraDialog.dismiss();
            }
        });
//取消事件
        root.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCameraDialog.dismiss();
            }
        });

    }






    /*-------------------------------------------相机与相册返回结果代码----------------------------------------------*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try{
                        //将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        userimage.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机版本号
                    if(Build.VERSION.SDK_INT >= 19){
                        try {
                            handleImageOnKitkat(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            handleImageBeforeKitkat(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitkat(Intent data) throws IOException {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private  void handleImageBeforeKitkat(Intent data) throws IOException {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
//拍照保存头像
    private void displayImage(String imagePath) throws IOException {
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            userimage.setImageBitmap(bitmap);

//            File file = new File(imagePath);
//            FileInputStream fis = new FileInputStream(file);
//            byte[] buf = new byte[fis.available()];
//            StringBuffer str2 = new StringBuffer("");
//            fis.read(buf);
//            for (byte bs : buf) {
//                str2.append(Integer.toBinaryString(bs));
//            }
//
//            Person person = new Person();
//            person.setImage(str2);
//            person.updateAll("username = ?",LoginActivity.username);

            Toast.makeText(MainActivity.this,"头像修改成功！",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }


    public void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(MainActivity.this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}















