// IDictionaryManager.aidl
package com.study.yaodh.androidstudy.aidl;

// Declare any non-default types here with import statements

interface IDictionaryManager {
    void add(String source, String translation);
    String query(String source);
}
