// IOnUserAddListener.aidl
package com.example.assistantapp;

// Declare any non-default types here with import statements
import com.example.assistantapp.User;

interface IOnUserAddedListener {
    void onUserAdded(in User user);
}
