package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by yaodh on 2016/7/6.
 */
public class SectionAdapter extends ArrayAdapter<String> implements SectionIndexer {

    HashMap<Character, Integer> mapIndex;
    Character[] sections;
    List<String> mList;

    public SectionAdapter(Context context, List<String> list) {
        super(context, android.R.layout.simple_list_item_1, list);

        mList = list;
        mapIndex = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            String fruit = list.get(i);
            char ch = fruit.charAt(0);
            mapIndex.put(ch, i);
        }

        Set<Character> sectionLetters = mapIndex.keySet();
        ArrayList<Character> sectionList = new ArrayList<>(sectionLetters);
        Collections.sort(sectionList);
        sections = new Character[sectionList.size()];
        sectionList.toArray(sections);
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mapIndex.get(sections[sectionIndex]);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }
}
