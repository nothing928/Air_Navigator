/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hanke.navi.skyair.achart;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartDemoActivity extends ListActivity {
  private IDemoChart mCharts = new SalesComparisonChart();

  private String mMenuText;

  private String mMenuSummary;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


      mMenuText = mCharts.getName();
      mMenuSummary = mCharts.getDesc();

    setListAdapter(new SimpleAdapter(this, getListValues(), android.R.layout.simple_list_item_2,
        new String[] { IDemoChart.NAME, IDemoChart.DESC }, new int[] { android.R.id.text1,
            android.R.id.text2 }));
  }

  private List<Map<String, String>> getListValues() {
    List<Map<String, String>> values = new ArrayList<Map<String, String>>();

    Map<String, String> v = new HashMap<String, String>();
    v.put(IDemoChart.NAME, mMenuText);
    v.put(IDemoChart.DESC, mMenuSummary);
    values.add(v);

    return values;
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    Intent intent = mCharts.execute(this);
    startActivity(intent);
  }
}