package by.iaa.contacts.Model;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONHelper<E> {
    public static final String FILE_NAME = ".json";
    public static FileOutputStream fileOutputStream= null;
    public static InputStreamReader streamReader = null;
    public static FileInputStream fileInputStream = null;


    public boolean exportToJSON(Context context, List<E> dataList, String fileName, Type typeClass) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(dataList, typeClass);
        try {
            fileOutputStream = context.openFileOutput(fileName + FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public List<E> importFromJSON(Context context, String fileName, Type typeClass) {

        try {
            File file = new File(context.getFileStreamPath(fileName + FILE_NAME).toString());
            if(!file.exists()) return new ArrayList<>();
            fileInputStream = context.openFileInput(fileName + FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();

            List<E> dataItems = gson.fromJson(streamReader, typeClass);

            return dataItems;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

}
