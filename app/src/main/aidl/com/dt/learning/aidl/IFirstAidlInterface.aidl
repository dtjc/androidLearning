// IFirstAidlInterface.aidl
package com.dt.learning.aidl;
import com.dt.learning.aidl.User;   //即使在同一个包内也要import
// Declare any non-default types here with import statements

interface IFirstAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    User createUser(in String name,in int age);//in表示输入型参数，out表示输出型参数，inout两者均可
}
