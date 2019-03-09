package com.pravrajya.diamond.views.users.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.pravrajya.diamond.R;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;


public class ExpandableDrawerAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> _listDataHeader;
    private LinkedHashMap<String, List<String>> _listDataChild;
    private int selectedPosition = -1;

    public ExpandableDrawerAdapter(Context context, List<String> listDataHeader, LinkedHashMap<String, List<String>> listDataChild) {
        this.context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return  this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) { convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_list_item, null); }
        TextView textViewChild = convertView.findViewById(R.id.textViewChild);
        textViewChild.setText(expandedListText.toUpperCase());

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return Objects.requireNonNull(this._listDataChild.get(this._listDataHeader.get(listPosition))).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) { LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.header_layout, null);
        }

        TextView textViewHeader =  convertView.findViewById(R.id.textViewHeader);
        textViewHeader.setText(headerTitle);

        //Expand all groups
        //ExpandableListView mExpandableListView = (ExpandableListView) parent;
        //mExpandableListView.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


