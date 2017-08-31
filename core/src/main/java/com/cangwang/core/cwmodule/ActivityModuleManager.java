package com.cangwang.core.cwmodule;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by cangwang on 2016/12/26.
 */

public class ActivityModuleManager extends ModuleManager{
    private static final String TAG = "ActivityModuleManager";



    public void initModules(Bundle saveInstance, Activity activity,ArrayMap<String,ArrayList<Integer>> modules){
        if (activity == null || modules == null) return;
        moduleConfig(modules);
        initModules(saveInstance,activity);
    }

    public void initModules(Bundle saveInstance, Activity activity){
        if (getModules() ==null) return;

        ELModuleContext moduleContext =new ELModuleContext();
        moduleContext.setActivity(activity);
        moduleContext.setSaveInstance(saveInstance);

        //获取配置
        for(String moduleName: getModules().keySet()){
            ELAbsModule module = ELModuleFactory.newModuleInstance(moduleName);
            Log.d(TAG,"ActivityModuleManager init module name: "+ moduleName);

            if (module!=null){
                //关联视图
                SparseArrayCompat<ViewGroup> viewGroups =new SparseArrayCompat<>();
                ArrayList<Integer> mViewIds = getModules().get(moduleName);
                if (mViewIds !=null &&mViewIds.size()>0) {
                    for (int i = 0; i < mViewIds.size(); i++) {
                        viewGroups.put(i, (ViewGroup) activity.findViewById(mViewIds.get(i).intValue()));
                    }
                }
                moduleContext.setViewGroups(viewGroups);
                module.init(moduleContext,"");

                allModules.put(moduleName,module);
            }
        }
    }
}
