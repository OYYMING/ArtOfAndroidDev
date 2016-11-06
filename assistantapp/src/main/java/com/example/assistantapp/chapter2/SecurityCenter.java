package com.example.assistantapp.chapter2;

import android.os.RemoteException;

import com.example.assistantapp.ISecurityCenter;

/**
 * Created by ouyangym on 2016/11/03.
 */
public class SecurityCenter extends ISecurityCenter.Stub {

    @Override
    public String encrypt(String content) throws RemoteException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return content + " is encrypted";
    }

    @Override
    public String decrypt(String encStr) throws RemoteException {
        return encStr + " is encrypted";
    }
}
