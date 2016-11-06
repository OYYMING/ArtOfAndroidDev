// IUserManager.aidl
package com.example.assistantapp;

// Declare any non-default types here with import statements
import com.example.assistantapp.User;
import com.example.assistantapp.IOnUserAddedListener;

interface IUserManager {
    List<User> getUserList();
    void addUser(in User user);
    void addUserInOut(inout Bundle user);
    void registerListener (IOnUserAddedListener listener);
    void unregisterListener (IOnUserAddedListener listener);
}
