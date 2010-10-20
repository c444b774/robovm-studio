/*
 * Copyright 2000-2010 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.android.sdk;

import com.android.sdklib.IAndroidTarget;
import com.android.sdklib.SdkManager;
import org.jetbrains.annotations.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Eugene.Kudelevsky
 * Date: Jun 2, 2009
 * Time: 2:37:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class AndroidSdkImpl extends AndroidSdk {
  private final SdkManager mySdkManager;

  public AndroidSdkImpl(@NotNull SdkManager sdkManager) {
    mySdkManager = sdkManager;
  }

  @NotNull
  public String getLocation() {
    return mySdkManager.getLocation();
  }

  @NotNull
  @Override
  public String getName() {
    return "Android SDK 1.5";
  }

  @NotNull
  public IAndroidTarget[] getTargets() {
    return mySdkManager.getTargets();
  }

  @NotNull
  public SdkManager getSdkManager() {
    return mySdkManager;
  }
}
