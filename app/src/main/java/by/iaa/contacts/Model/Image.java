package by.iaa.contacts.Model;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Image {
    private static final Image INSTANCE = new Image();

    public static Image getInstance() {
        return INSTANCE;
    }

    public String saveToInternalStorage(Bitmap bitmapImage, AppCompatActivity app, String name) throws IOException {
        ContextWrapper cw = new ContextWrapper(app.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Создаем imageDir
        File mypath = new File(directory, name + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Используем метод сжатия BitMap объекта для записи в OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fos.close();
        }
        return directory.getAbsolutePath() + "/" + name;
    }

    public void loadImageFromStorage(ImageView imageView, String name) {
        try {
            File f = new File(name + ".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            imageView.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
