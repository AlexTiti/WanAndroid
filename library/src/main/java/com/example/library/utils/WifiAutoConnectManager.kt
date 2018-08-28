package com.example.library.utils

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager

/**
 *
 *
 * 兼容Android 6.0以上手机连接wifi
 */

class WifiAutoConnectManager(internal var wifiManager: WifiManager) {

    /**
     * 定义几种加密方式，一种是WEP，一种是WPA，还有没有密码的情况
     */
    enum class WifiCipherType {
        WIFICIPHER_WEP, WIFICIPHER_WPA, WIFICIPHER_NOPASS, WIFICIPHER_INVALID
    }

    fun connect(ssid: String, password: String, type: WifiCipherType) {
        val thread = Thread(ConnectRunnable(ssid, password, type))
        thread.start()
    }

    /**
     * 查看以前是否也配置过这个网络
     *
     * @param SSID
     * @return
     */
    private fun isExsits(SSID: String): WifiConfiguration? {
        val existingConfigs = wifiManager
                .configuredNetworks
        for (existingConfig in existingConfigs) {
            if (existingConfig.SSID == "\"" + SSID + "\"") {
                return existingConfig
            }
        }
        return null
    }

    private fun createWifiInfo(SSID: String, Password: String,
                               Type: WifiCipherType): WifiConfiguration {
        val config = WifiConfiguration()
        config.allowedAuthAlgorithms.clear()
        config.allowedGroupCiphers.clear()
        config.allowedKeyManagement.clear()
        config.allowedPairwiseCiphers.clear()
        config.allowedProtocols.clear()
        config.SSID = "\"" + SSID + "\""

        if (Type == WifiCipherType.WIFICIPHER_NOPASS) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
        }
        if (Type == WifiCipherType.WIFICIPHER_WEP) {
            if (!StringUtils.isEmpty(Password)) {
                if (isHexWepKey(Password)) {
                    config.wepKeys[0] = Password
                } else {
                    config.wepKeys[0] = "\"" + Password + "\""
                }
            }
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            config.wepTxKeyIndex = 0
        }
        // wpa
        if (Type == WifiCipherType.WIFICIPHER_WPA) {
            config.preSharedKey = "\"" + Password + "\""
            config.hiddenSSID = true
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP)
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP)
            config.status = WifiConfiguration.Status.ENABLED

        }
        return config
    }

    /**
     * 打开wifi功能
     * @return
     */
    private fun openWifi(): Boolean {
        var bRet = true
        if (!wifiManager.isWifiEnabled) {
            bRet = wifiManager.setWifiEnabled(true)
        }
        return bRet
    }

    /**
     * 关闭WIFI
     */
    private fun closeWifi() {
        if (wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = false
        }
    }

    internal inner class ConnectRunnable(private val ssid: String, private val password: String, private val type: WifiCipherType) : Runnable {

        override fun run() {

            openWifi()
            // 开启wifi功能需要一段时间(我在手机上测试一般需要1-3秒左右)，所以要等到wifi
            // 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
            while (wifiManager.wifiState == WifiManager.WIFI_STATE_ENABLING) {
                try {
                    // 为了避免程序一直while循环，让它睡个100毫秒检测……
                    Thread.sleep(100)

                } catch (ie: InterruptedException) {

                }

            }

            val tempConfig = isExsits(ssid)

            if (tempConfig != null) {
                val b = wifiManager.enableNetwork(tempConfig.networkId,
                        true)
            } else {
                val wifiConfig = createWifiInfo(ssid, password,
                        type) ?: return

                val netID = wifiManager.addNetwork(wifiConfig)
                val enabled = wifiManager.enableNetwork(netID, true)
                val connected = wifiManager.reconnect()
            }

        }
    }

    companion object {

        private val TAG = WifiAutoConnectManager::class.java
                .simpleName

        private fun isHexWepKey(wepKey: String): Boolean {
            val len = wepKey.length

            // WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
            return if (len != 10 && len != 26 && len != 58) {
                false
            } else isHex(wepKey)

        }

        private fun isHex(key: String): Boolean {
            for (i in key.length - 1 downTo 0) {
                val c = key[i]
                val b = !(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f')
                if (b) {
                    return false
                }
            }

            return true
        }

        /**
         * 获取ssid的加密方式
         *
         * @param context Context
         * @param ssid    String
         * @return
         */
        fun getCipherType(context: Context, ssid: String): WifiCipherType {
            val wifiManager = context
                    .getSystemService(Context.WIFI_SERVICE) as WifiManager

            val list = wifiManager.scanResults

            for (scResult in list) {

                if (!StringUtils.isEmpty(scResult.SSID) && scResult.SSID == ssid) {
                    val capabilities = scResult.capabilities

                    if (!StringUtils.isEmpty(capabilities)) {

                        return if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
                            WifiCipherType.WIFICIPHER_WPA
                        } else if (capabilities.contains("WEP") || capabilities.contains("wep")) {
                            WifiCipherType.WIFICIPHER_WEP
                        } else {
                            WifiCipherType.WIFICIPHER_NOPASS
                        }
                    }
                }
            }
            return WifiCipherType.WIFICIPHER_INVALID
        }
    }
}