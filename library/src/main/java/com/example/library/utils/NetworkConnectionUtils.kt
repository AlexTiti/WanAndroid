package com.example.library.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiConfiguration.KeyMgmt
import android.net.wifi.WifiManager
import android.os.Build
import java.io.IOException


/**
 * Created by Horrarndoo on 2017/8/31.
 *
 *
 * Wifi连接工具类
 */
object NetworkConnectionUtils {

    /**
     * 连接指定
     *
     * @param manager
     * @param wifiSSID
     * @return
     */
    fun connectToSocketWifi(manager: WifiManager, wifiSSID: String): Boolean {
        val wifiConfiguration = WifiConfiguration()
        wifiConfiguration.SSID = "\"" + wifiSSID + "\""
        wifiConfiguration.allowedKeyManagement.set(KeyMgmt.NONE)
        //小米手机MIUI7/华为EMUI4.1 需要webKey
        wifiConfiguration.wepKeys[0] = "\"" + "\""

        var networkId = manager.addNetwork(wifiConfiguration)

        if (networkId != -1) {
            manager.enableNetwork(networkId, true)
            return true
        } else {
            val wifiConfiguration2 = WifiConfiguration()
            wifiConfiguration2.SSID = "\"" + wifiSSID + "\""
            //wifiConfiguration.wepKeys[0] = "\"" + "\"";//去掉webKey  //小米手机MIUI8不能有webKey
            wifiConfiguration2.allowedKeyManagement.set(KeyMgmt.NONE)
            networkId = manager.addNetwork(wifiConfiguration2)
            if (networkId != -1) {
                manager.enableNetwork(networkId, true)
                return true
            }
        }
        return false
    }

    /**
     * 获取要连接的wifi节点各个配置选项的加密类型
     *
     * @param ssid
     * @return wifiConfiguration
     */
    fun getWifiConfiguration(manager: WifiManager, ssid: String, password: String): WifiConfiguration {
        val wifiConfiguration = WifiConfiguration()
        wifiConfiguration.SSID = "\"" + ssid + "\""

        val list = manager.scanResults
        for (scResult in list) {
            if (ssid == scResult.SSID) {
                val capabilities = scResult.capabilities
                if (capabilities.contains("WEP") || capabilities.contains("wep")) {
                    wifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_EAP)
                    wifiConfiguration.preSharedKey = "\"" + password + "\""
                } else if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
                    wifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_PSK)
                    wifiConfiguration.preSharedKey = "\"" + password + "\""
                } else {
                    wifiConfiguration.allowedKeyManagement.set(KeyMgmt.NONE)
                }
            }
        }
        return wifiConfiguration
    }

    /**
     * 给温控器成功发送联网命令后，连接温控器连接的wifi节点
     *
     * @param context  上下文对象
     * @param ssid     ssid
     * @param password 密码
     */
    fun connectWifiSSID(context: Context, manager: WifiManager, ssid: String, password: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            WifiAutoConnectManager(manager).connect(ssid, password, WifiAutoConnectManager
                    .getCipherType(context, ssid))
        } else {
            val networkId = manager.addNetwork(getWifiConfiguration(manager, ssid, password))
            if (networkId != -1) {
                manager.enableNetwork(networkId, true)
            }
        }
    }

    /**
     * 格式化RouterSSID
     *
     * @param strRouterSSID 要格式化的当前连接的路由ssid
     * @return 去除"\"后的RouterSSID字符串
     */
    fun formatRouterSSID(strRouterSSID: String): String {
        var strRouterSSID = strRouterSSID
        if (strRouterSSID.contains("\"")) {
            strRouterSSID = strRouterSSID.replace("\"".toRegex(), "")
        }
        return strRouterSSID
    }

    /**
     * Ping
     * 用于确定手机是否已经连接上指定设备ip地址
     */
    fun pingTest(IPOrDomainName: String): Boolean {

        var isSuccess = false
        val status: Int
        var result = "failed"
        val p: Process
        try {
            p = Runtime.getRuntime().exec("ping -c 1 $IPOrDomainName")
            // m_strForNetAddress是输入的网址或者Ip地址
            status = p.waitFor()

            if (status == 0) {
                result = "success"
                isSuccess = true
            }

        } catch (e: IOException) {

        } catch (e: InterruptedException) {
        }

        return isSuccess
    }

    /**
     * 判断网络是否连接
     */
    fun isConnected(context: Context): Boolean {
        val cm = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        if (null != info && info.isConnected) {
            if (info.state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }

    /**
     * 判断是否有网络
     *
     * @return 返回值
     */
    fun isNetworkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager.activeNetworkInfo

            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }

    /**
     * 判断是否是wifi连接
     */
    fun isWifi(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return false

        val info = cm.activeNetworkInfo
        if (null != info) {
            if (info.type == ConnectivityManager.TYPE_WIFI) {
                return true
            }
        }
        return false

    }


    /**
     * 打开网络设置界面
     */
    fun openSetting(activity: Activity, requestCode: Int) {
        val intent = Intent("/")
        val cm = ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings")
        intent.component = cm
        intent.action = Intent.ACTION_VIEW
        activity.startActivityForResult(intent, requestCode)
    }
}
