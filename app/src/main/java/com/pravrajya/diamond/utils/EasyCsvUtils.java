package com.pravrajya.diamond.utils;

import android.app.Activity;
import android.widget.Toast;

import net.ozaydin.serkan.easy_csv.EasyCsv;
import net.ozaydin.serkan.easy_csv.FileCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EasyCsvUtils {

    private Activity context;
    EasyCsv easyCsv;
    public final int WRITE_PERMISSON_REQUEST_CODE = 1;


    public EasyCsvUtils(Activity context) {
        this.context = context;
        easyCsv = new EasyCsv(context);

        loadCSVFile();
    }


    private void loadCSVFile(){

        List<String> headerList = new ArrayList<>();
        headerList.add("Name.Surname.Age.Adress.Location.Education-");


        List<String> dataList = new ArrayList<>();
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");
        dataList.add("Serkan.Ozaydin.23.Fatih.Turkey.University-");


        easyCsv.setSeparatorColumn(".");
        easyCsv.setSeperatorLine("-");

        String fileName="EasyCsv";

        easyCsv.createCsvFile(fileName, headerList, dataList, WRITE_PERMISSON_REQUEST_CODE, new FileCallback() {
            @Override
            public void onSuccess(File file) {
                Toast.makeText(context,"Saved file",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String err) {
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
