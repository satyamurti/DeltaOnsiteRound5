package com.mrspd.deltaonsiteround5

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrspd.deltaonsiteround5.adapters.MyAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {
    private val STORAGE_PERMISSION_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val filesKaList = ArrayList<String>()
            val base =
                File(Environment.getExternalStorageDirectory().absolutePath)
            val files = base.listFiles()
            filesKaList.clear()
            if (files != null) {
                for (file in files) {
                    filesKaList.add(file.path)
                }
                mMyAdapter = MyAdapter(applicationContext)
                mMyAdapter!!.addAllFiles(filesKaList)
                recyclerView1.apply {
                    setHasFixedSize(true)
                    layoutManager =  LinearLayoutManager(applicationContext)
                    adapter = mMyAdapter
                }

            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.w("QWERT", "FRT 1")
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.w("QWERT", "FRT")
            val filesKaList = ArrayList<String>()
            val base =
                File(Environment.getExternalStorageDirectory().absolutePath)
            val files = base.listFiles()
            filesKaList.clear()
            if (files != null) {
                for (file in files) {
                    filesKaList.add(file.path)
                }
                mMyAdapter = MyAdapter(applicationContext)
                mMyAdapter!!.addAllFiles(filesKaList)
                recyclerView1.apply {
                    setHasFixedSize(true)
                    layoutManager =  LinearLayoutManager(applicationContext)
                    adapter = mMyAdapter
                }

            }
        }
    }
    private var mMyAdapter: MyAdapter? = null
    interface ITemClickListener {
        fun OnClick(view: View?, position: Int)
    }
    companion object {
        private const val REQUEST_CODE = 2
    }

}