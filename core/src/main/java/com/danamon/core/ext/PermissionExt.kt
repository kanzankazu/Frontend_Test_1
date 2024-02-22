package com.danamon.core.ext

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
fun isTiramisuAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun isSAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.M)
fun isMarshmelloAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

fun Context.isPermissions(permission: PermissionEnum) = permission.let {
    ActivityCompat.checkSelfPermission(this, it.permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.isPermissions(permissions: Array<PermissionEnum>) = permissions.all {
    ActivityCompat.checkSelfPermission(this, it.permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.isPermissions(permissions: PermissionEnumArray) = permissions.permissions.all {
    ActivityCompat.checkSelfPermission(this, it.permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.isPermissions(permissions: Array<PermissionEnumArray>) =
    permissions.toArrayString().all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

fun FragmentActivity.checkPermissions(
    permissions: PermissionEnumArray,
    activityResultLauncher: ActivityResultLauncher<Array<String>>,
    isWithDialog: Boolean = true,
) {
    checkPermissions(permissions.permissions, activityResultLauncher, isWithDialog)
}

fun FragmentActivity.checkPermissions(
    permissions: Array<PermissionEnum>,
    activityResultLauncher: ActivityResultLauncher<Array<String>>,
    isWithDialog: Boolean = true,
) {
    checkPermissions(permissions.toArrayString(), activityResultLauncher, isWithDialog)
}

fun FragmentActivity.checkPermissions(
    permissions: Array<String>,
    activityResultLauncher: ActivityResultLauncher<Array<String>>,
    isWithDialog: Boolean = true,
) {
    val isRationale =
        permissions.all { ActivityCompat.shouldShowRequestPermissionRationale(this, it) }

    if (isRationale) {
        if (isWithDialog) {
            val message = when {
                permissions.contentEquals(PermissionEnumArray.POST_NOTIFICATIONS.permissions.toArrayString()) -> "We need notification permission to download to notify you of new notifications"
                permissions.contentEquals(PermissionEnumArray.CAMERA.permissions.toArrayString()) -> "We need camera permission to open photos"
                permissions.contentEquals(PermissionEnumArray.FILE_ACCESS.permissions.toArrayString()) -> "We need storage permission to place the data download"
                permissions.contentEquals(PermissionEnumArray.CAMERA_FILE_ACCESS.permissions.toArrayString()) -> "We need camera and storage permissions to open photos and save photos"
                permissions.contentEquals(PermissionEnumArray.LOCATION.permissions.toArrayString()) -> "We need location permission to find your location to provide data around you"
                else -> ""
            }

            val builder = AlertDialog.Builder(this)
            builder.setMessage(message)
            builder.setPositiveButton("OK") { _, _ -> activityResultLauncher.launch(permissions) }
            builder.setNegativeButton("BATAL") { _, _ -> }
            builder.show()
        } else activityResultLauncher.launch(permissions)
    } else activityResultLauncher.launch(permissions)
}

fun FragmentActivity.resultMultiplePermissions(defaultHandle: (Map<String, Boolean>) -> Unit = {}) =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val isGranted = permissions.entries.all { it.value }

        if (isGranted) {
            defaultHandle(permissions)
        } else {
            simpleToast("Harap izinkan akses penyimpanan untuk mengunduh file")
        }
    }

fun Array<PermissionEnumArray>.toArrayString(): Array<String> {
    val hasil = mutableListOf<PermissionEnum>()
    for (array in this) {
        hasil.addAll(array.permissions)
    }
    return hasil.toTypedArray().toArrayString()
}

fun Array<PermissionEnum>.toArrayString() = this.map { it.permission }.toTypedArray()

enum class PermissionEnum(val permission: String) {
    CAMERA("android.permission.CAMERA"),
    INTERNET("android.permission.INTERNET"),
    READ_PHONE_STATE("android.permission.READ_PHONE_STATE"),
    READ_CONTACTS("android.permission.READ_CONTACTS"),
    ACCESS_FINE_LOCATION("android.permission.ACCESS_FINE_LOCATION"),
    ACCESS_COARSE_LOCATION("android.permission.ACCESS_COARSE_LOCATION"),
    WRITE_EXTERNAL_STORAGE("android.permission.WRITE_EXTERNAL_STORAGE"),
    READ_EXTERNAL_STORAGE("android.permission.READ_EXTERNAL_STORAGE"),
    POST_NOTIFICATIONS("android.permission.POST_NOTIFICATIONS"),
    READ_MEDIA_AUDIO("android.permission.READ_MEDIA_AUDIO"),
    READ_MEDIA_IMAGES("android.permission.READ_MEDIA_IMAGES"),
    READ_MEDIA_VIDEO("android.permission.READ_MEDIA_VIDEO"),
    RECORD_AUDIO("android.permission.RECORD_AUDIO"),
    READ_SMS("android.permission.READ_SMS"),
}

enum class PermissionEnumArray(val permissions: Array<PermissionEnum>) {
    POST_NOTIFICATIONS(
        arrayOf(
            PermissionEnum.POST_NOTIFICATIONS
        )
    ),
    CAMERA(
        arrayOf(
            PermissionEnum.CAMERA
        )
    ),
    CAMERA_FILE_ACCESS(
        if (isTiramisuAbove()) {
            arrayOf(
                PermissionEnum.CAMERA,
                PermissionEnum.READ_MEDIA_IMAGES,
                PermissionEnum.READ_MEDIA_VIDEO
            )
        } else {
            arrayOf(
                PermissionEnum.CAMERA,
                PermissionEnum.WRITE_EXTERNAL_STORAGE,
                PermissionEnum.READ_EXTERNAL_STORAGE
            )
        }
    ),
    LOCATION(
        arrayOf(
            PermissionEnum.ACCESS_FINE_LOCATION,
            PermissionEnum.ACCESS_COARSE_LOCATION
        )
    ),
    FILE_ACCESS(
        if (isTiramisuAbove()) {
            arrayOf(
                PermissionEnum.READ_MEDIA_AUDIO,
                PermissionEnum.READ_MEDIA_IMAGES,
                PermissionEnum.READ_MEDIA_VIDEO
            )
        } else {
            arrayOf(
                PermissionEnum.WRITE_EXTERNAL_STORAGE,
                PermissionEnum.READ_EXTERNAL_STORAGE
            )
        }
    ),
}
