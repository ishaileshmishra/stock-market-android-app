/**
 *
 * Copyright © 2012-2020 [Shailesh Mishra](https://github.com/mshaileshr/). All Rights Reserved
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
