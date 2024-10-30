package me.hd.hookgg.ui.fragment.nav

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.hook.factory.prefs
import me.hd.hookgg.MyApp
import me.hd.hookgg.R
import me.hd.hookgg.data.AppData
import me.hd.hookgg.data.SetPrefsData
import me.hd.hookgg.databinding.FragmentFuncPageBinding
import me.hd.hookgg.ui.adapter.FuncAdapter
import me.hd.hookgg.ui.fragment.base.FragmentBase

class FuncPageFragment : FragmentBase<FragmentFuncPageBinding, ViewModel>(
    FragmentFuncPageBinding::inflate
) {
    private var funcList = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        if (YukiHookAPI.Status.isModuleActive) {
            binding.funcPageToolbar.subtitle = getString(R.string.module_active)
        }
        initFuncAdapter()
    }

    private fun initFuncAdapter() {
        binding.funcPageRvFunc.apply {
            layoutManager = FlexboxLayoutManager(context)
            adapter = FuncAdapter(funcList)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        funcList.apply {
            clear()
            addAll(
                AppData.getFunctionList(
                    MyApp.context.prefs().get(SetPrefsData.VERSION_NAME)
                ).toMutableList()
            )
        }
        val adapter = binding.funcPageRvFunc.adapter
        adapter?.notifyDataSetChanged()
    }
}